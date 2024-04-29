package lk.ijse.helloShoesManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@Table(name = "size")
public class SizeEntity {

    @Id
    private String sizeId;
    private String sizeDesc;

    @OneToMany(mappedBy = "sizeEntity", cascade = CascadeType.ALL)
    private List<StockItemEntity> stockItemEntities;

}
