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
    /*chuyển đổi sang base64 để lưu vô database*/
    private String imageBase64;
    private String priceFormatVN;
    private Double sale;
    private Double area;
    private Integer bedRoom;
    private Integer livingRoom;

    /*search nằm trong khoảng max min*/
    private String isSearchPrice;
    private Integer minPrice;
    private Integer maxPrice;

    /*search theo LIKE*/
    private String isSearchAddress;
    private String address;

    /*search nằm trong khoảng max min*/
    private String isSearchArea;
    private Integer minArea;
    private Integer maxArea;

    /*search theo điều kiện bằng*/
    private String isSearchBedRoom;

    /*search theo điều kiện bằng*/
    private String isSearchLivingRoom;
}
