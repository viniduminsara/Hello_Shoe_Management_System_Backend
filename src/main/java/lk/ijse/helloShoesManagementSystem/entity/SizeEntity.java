package lk.ijse.helloShoesManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "size")
public class SizeEntity {

    @Id
    private String sizeId;
    @Column(unique = true)
    private Integer size;

    @OneToMany(mappedBy = "sizeEntity")
    @JsonManagedReference
    private List<ItemSizeEntity> itemSizeEntities;

}
