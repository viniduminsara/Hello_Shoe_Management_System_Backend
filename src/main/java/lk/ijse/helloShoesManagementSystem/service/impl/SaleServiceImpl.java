package lk.ijse.helloShoesManagementSystem.service.impl;

import lk.ijse.helloShoesManagementSystem.dto.OrderItem;
import lk.ijse.helloShoesManagementSystem.dto.RefundDTO;
import lk.ijse.helloShoesManagementSystem.dto.SaleDTO;
import lk.ijse.helloShoesManagementSystem.dto.SaleDetailsDTO;
import lk.ijse.helloShoesManagementSystem.entity.*;
import lk.ijse.helloShoesManagementSystem.exception.NotFoundException;
import lk.ijse.helloShoesManagementSystem.repository.*;
import lk.ijse.helloShoesManagementSystem.service.SaleService;
import lk.ijse.helloShoesManagementSystem.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepo saleRepo;
    private final UserRepo userRepo;
    private final CustomerRepo customerRepo;
    private final ItemSizeRepo itemSizeRepo;
    private final SaleDetailsRepo saleDetailsRepo;
    private final SizeRepo sizeRepo;
    private final RefundRepo refundRepo;
    private final Mapper mapper;

    @Override
    public void saveSale(SaleDTO saleDTO) {
        saleDTO.setOrderId(generateNextOrderId());
        UserEntity userEntity = userRepo.findByEmployeeId(saleDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        CustomerEntity customerEntity = null;
        if (saleDTO.getCustomerId() != null) {
            customerEntity = customerRepo.findById(saleDTO.getCustomerId())
                    .orElseThrow(() -> new NotFoundException("Customer not found"));
        }

        SaleEntity saleEntity = mapper.toSaleEntity(saleDTO);
        saleEntity.setCustomer(customerEntity);
        saleEntity.setUser(userEntity);
        saleRepo.save(saleEntity);

        List<OrderItem> orderItems = saleDTO.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            SizeEntity sizeEntity = sizeRepo.findBySize(orderItem.getSize())
                    .orElseThrow(() -> new NotFoundException("Size not found"));
            ItemSizeEntity itemSizeEntity = itemSizeRepo.findByItemCodeAndSizeId(orderItem.getItemCode(), sizeEntity.getSizeId())
                    .orElseThrow(() -> new NotFoundException("Item not found"));

            SaleDetailsEntity saleDetailsEntity = getSaleDetailsEntity(orderItem, saleEntity, itemSizeEntity);
            saleDetailsRepo.save(saleDetailsEntity);

            itemSizeEntity.setQty(itemSizeEntity.getQty() - orderItem.getItemQty());
        }

    }

    @Override
    public List<SaleDTO> getAllSales() {
        return mapper.toSaleDTOList(saleRepo.findAll());
    }

    @Override
    public List<SaleDetailsDTO> getSelectedSale(String id) {
        List<SaleDetailsEntity> saleDetailsEntities = saleDetailsRepo.findSaleDetailsByOrderId(id);
        if (saleDetailsEntities.isEmpty()) throw new NotFoundException("Sale Details not found");
        List<SaleDetailsDTO> saleDetailsDTOS = new ArrayList<>();
        for (SaleDetailsEntity saleDetailsEntity : saleDetailsEntities) {
            SaleDetailsDTO saleDetailsDTO = new SaleDetailsDTO();
            saleDetailsDTO.setItemCode(saleDetailsEntity.getItemSizeEntity().getItemEntity().getItemCode());
            saleDetailsDTO.setItemDesc(saleDetailsEntity.getItemSizeEntity().getItemEntity().getItemDesc());
            saleDetailsDTO.setItemPic(saleDetailsEntity.getItemSizeEntity().getItemEntity().getItemPic());
            saleDetailsDTO.setSaleDetailsId(saleDetailsEntity.getSaleDetailsId());
            saleDetailsDTO.setSize(saleDetailsEntity.getItemSizeEntity().getSizeEntity().getSize());
            saleDetailsDTO.setQty(saleDetailsEntity.getQty());
            saleDetailsDTO.setUnitPrice(saleDetailsEntity.getUnitPrice());
            saleDetailsDTOS.add(saleDetailsDTO);
        }
        return saleDetailsDTOS;
    }

    @Override
    public void saveRefund(RefundDTO refundDTO) {
        SaleDetailsEntity saleDetailsEntity = saleDetailsRepo.findById(refundDTO.getSaleDetailsId())
                .orElseThrow(() -> new NotFoundException("Sale Details not found"));
        refundDTO.setRefundId(UUID.randomUUID().toString());
        refundDTO.setDate(Timestamp.valueOf(LocalDateTime.now()));
        RefundEntity refundEntity = mapper.toRefundEntity(refundDTO);
        refundEntity.setSaleDetailsEntity(saleDetailsEntity);
        refundRepo.save(refundEntity);

        saleDetailsEntity.getItemSizeEntity().setQty(saleDetailsEntity.getItemSizeEntity().getQty() + refundDTO.getQty());
    }

    private static SaleDetailsEntity getSaleDetailsEntity(OrderItem orderItem, SaleEntity saleEntity, ItemSizeEntity itemSizeEntity) {
        SaleDetailsEntity saleDetailsEntity = new SaleDetailsEntity();
        saleDetailsEntity.setSaleDetailsId(UUID.randomUUID().toString());
        saleDetailsEntity.setSaleEntity(saleEntity);
        saleDetailsEntity.setItemSizeEntity(itemSizeEntity);
        saleDetailsEntity.setQty(orderItem.getItemQty());
        saleDetailsEntity.setUnitPrice(orderItem.getUnitPrice());
        return saleDetailsEntity;
    }

    public String generateNextOrderId() {
        String maxOrderId = saleRepo.findLastOrderId();
        if (maxOrderId == null) {
            return "ORD-000001";
        }

        int lastNumber = Integer.parseInt(maxOrderId.substring(4));
        int nextNumber = lastNumber + 1;
        return String.format("ORD-%06d", nextNumber);
    }
}

