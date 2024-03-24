package project.batdongsan.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.text.Normalizer;
import java.util.regex.Pattern;

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
    private String imageBase64;
    private String priceFormatVN;
    private Double sale;

}
