package lk.ijse.helloShoesManagementSystem.entity;

import jakarta.persistence.*;
import lk.ijse.helloShoesManagementSystem.entity.enums.Gender;
import lk.ijse.helloShoesManagementSystem.entity.enums.OccasionType;
import lk.ijse.helloShoesManagementSystem.entity.enums.VerityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@Table(name = "item")
public class ItemEntity {

    @Id
    private String itemCode;
    private String desc;
    private String itemPic;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private OccasionType occasionType;
    @Enumerated(EnumType.STRING)
    private VerityType verityType;

}
