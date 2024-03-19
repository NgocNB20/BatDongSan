package project.batdongsan.model.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HouseRequest {

    private String title;
    private Double price;
    private String description;
    private Double houseLength;
    private Double houseWidth;
    private MultipartFile image;
}
