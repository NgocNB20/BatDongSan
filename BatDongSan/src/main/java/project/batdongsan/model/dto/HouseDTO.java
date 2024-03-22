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

    public static void main(String[] args) {
        int id = 1001;
        String title = "BLV Quang Tùng: Indonesia không hay nhưng tuyển Việt Nam dở";
        String url = generateUrl(title, id);
        System.out.println(url);
    }

    public static String generateUrl(String title, int id) {
        String slug = makeSlug(title);
        return slug + "---" + id;
    }

    public static String makeSlug(String input) {
        String nowhitespace = input.replaceAll("\\s", "-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("")
                .replaceAll("[^\\p{Alnum}-]", "")
                .toLowerCase();
    }

}
