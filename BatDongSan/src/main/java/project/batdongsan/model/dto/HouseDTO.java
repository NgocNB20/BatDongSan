package project.batdongsan.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HouseDTO {

    private Integer id;
    private String title;
    private Double price;
    private String description;
    private Double houseLength;
    private Double houseWidth;
    private MultipartFile image;


}
