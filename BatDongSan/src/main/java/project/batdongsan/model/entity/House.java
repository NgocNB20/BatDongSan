package project.batdongsan.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_house")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private Double price;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(name="house_length")
    private Double houseLength;
    @Column(name="house_width")
    private Double houseWidth;
    private String address;
    @Lob
    @Column(name = "house_image")
    private String image;
    private Double sale;
    private Double area;
    @Column(name = "bed_room")
    private Integer bedRoom;
    @Column(name = "livingRoom")
    private Integer livingRoom;

}
