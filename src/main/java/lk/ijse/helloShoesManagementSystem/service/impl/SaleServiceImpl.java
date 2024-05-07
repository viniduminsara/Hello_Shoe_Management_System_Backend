package lk.ijse.helloShoesManagementSystem.service.impl;

import lk.ijse.helloShoesManagementSystem.dto.OrderItem;
import lk.ijse.helloShoesManagementSystem.dto.SaleDTO;
import lk.ijse.helloShoesManagementSystem.entity.*;
import lk.ijse.helloShoesManagementSystem.exception.NotFoundException;
import lk.ijse.helloShoesManagementSystem.repository.*;
import lk.ijse.helloShoesManagementSystem.service.SaleService;
import lk.ijse.helloShoesManagementSystem.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final Mapper mapper;

    @Override
    public void saveSale(SaleDTO saleDTO) {
        saleDTO.setOrderId(UUID.randomUUID().toString());
        UserEntity userEntity = userRepo.findById(saleDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        CustomerEntity customerEntity = customerRepo.findById(saleDTO.getCustomerId())
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        SaleEntity saleEntity = mapper.toSaleEntity(saleDTO);
        saleEntity.setCustomer(customerEntity);
        saleEntity.setUser(userEntity);
        saleRepo.save(saleEntity);

        List<OrderItem> orderItems = saleDTO.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            ItemSizeEntity itemSizeEntity = itemSizeRepo.findByItemCodeAndSizeId(orderItem.getItemCode(), orderItem.getSizeId())
                    .orElseThrow(() -> new NotFoundException("Item not found"));

            SaleDetailsEntity saleDetailsEntity = getSaleDetailsEntity(orderItem, saleEntity, itemSizeEntity);
            saleDetailsRepo.save(saleDetailsEntity);
        }

    }

    private static SaleDetailsEntity getSaleDetailsEntity(OrderItem orderItem, SaleEntity saleEntity, ItemSizeEntity itemSizeEntity) {
        SaleDetailsKey saleDetailsKey = new SaleDetailsKey();
        saleDetailsKey.setOrderId(saleEntity.getOrderId());
        saleDetailsKey.setItemSizeId(itemSizeEntity.getItemSizeId());

        SaleDetailsEntity saleDetailsEntity = new SaleDetailsEntity();
        saleDetailsEntity.setId(saleDetailsKey);
        saleDetailsEntity.setSaleEntity(saleEntity);
        saleDetailsEntity.setItemSizeEntity(itemSizeEntity);
        saleDetailsEntity.setQty(orderItem.getItemQty());
        saleDetailsEntity.setUnitPrice(orderItem.getUnitPrice());
        return saleDetailsEntity;
    }
}

