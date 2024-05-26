package lk.ijse.helloShoesManagementSystem.service.impl;

import lk.ijse.helloShoesManagementSystem.dto.AdminPanelDTO;
import lk.ijse.helloShoesManagementSystem.entity.ItemEntity;
import lk.ijse.helloShoesManagementSystem.repository.CustomerRepo;
import lk.ijse.helloShoesManagementSystem.repository.ItemRepo;
import lk.ijse.helloShoesManagementSystem.repository.SaleRepo;
import lk.ijse.helloShoesManagementSystem.service.AdminPanelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminPanelServiceImpl implements AdminPanelService {

    private final SaleRepo saleRepo;
    private final ItemRepo itemRepo;
    private final CustomerRepo customerRepo;

    @Override
    public AdminPanelDTO getPanelData() {
        AdminPanelDTO adminPanelDTO = new AdminPanelDTO();
        adminPanelDTO.setTotalProfit(saleRepo.findTotalProfit());
        adminPanelDTO.setTotalSales(saleRepo.findTotalSales());
        adminPanelDTO.setVerityTypeQuantities(itemRepo.getVerityTypeQuantity());
        adminPanelDTO.setTotalCustomers((int) customerRepo.count());

        List<ItemEntity> itemEntities = itemRepo.findMostSoldItems();
        if (!itemEntities.isEmpty()) {
            ItemEntity itemEntity = itemEntities.get(0);
            adminPanelDTO.setMostSaleItem(itemEntity.getItemDesc());
            adminPanelDTO.setMostSaleItemPic(itemEntity.getItemPic());
        }

        return adminPanelDTO;
    }
}
