package project.batdongsan.service.impl;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.batdongsan.model.dto.HouseDTO;
import project.batdongsan.model.entity.House;
import project.batdongsan.repositoty.HouseRepository;
import project.batdongsan.service.HouseService;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;

@Service
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;

    @Autowired
    public HouseServiceImpl(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Override
    public HouseDTO findById(Integer id) {
        Optional<House> houseOtp = houseRepository.findById(id);
        if (houseOtp.isPresent()) {
            return toHouseDto(houseOtp.get());
        }
        return new HouseDTO();
    }

    @Override
    public void save(HouseDTO houseDTO) {
        House house = toHouseEntity(houseDTO);
        houseRepository.save(house);
    }

    @Override
    public List<HouseDTO> findAll() {
        List<House> houseList = houseRepository.findAll();
        List<HouseDTO> houseDTOList = new ArrayList<>();

        if (ObjectUtils.isEmpty(houseList) || StringUtils.join(houseList).isEmpty()) {
            return new ArrayList<>();
        }
        houseList.stream().forEach(house -> {
            houseDTOList.add(toHouseDto(house));
        });

        return houseDTOList;
    }

    @Override
    public HouseDTO toHouseDto(House house) {

        HouseDTO houseDTO = new HouseDTO();
        NumberFormat format = NumberFormat.getInstance(new Locale("vi", "VN"));
        String priceFormatVN = format.format(house.getPrice());

        houseDTO.setId(house.getId());
        houseDTO.setDescription(house.getDescription());
        houseDTO.setPrice(house.getPrice());
        houseDTO.setTitle(house.getTitle()) ;
        houseDTO.setHouseLength(house.getHouseLength());
        houseDTO.setHouseWidth(house.getHouseWidth());
        houseDTO.setImageBase64(house.getImage());
        houseDTO.setPriceFormatVN(priceFormatVN+" VND");

        return houseDTO;
    }
    public String uploadImage(MultipartFile image) {
        // Save the image file to the server
        String fileName = image.getOriginalFilename();
        String filePath = "/path/to/upload/directory/" + fileName;
        try {
            image.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Return the URL of the uploaded image
        return "/static/assets/img" + fileName;
    }

//    <img th:src="'data:image/png;base64,' + ${base64Image}" alt="Base64 Image"/>
    @Override
    public House toHouseEntity(HouseDTO houseDTO) {

        House house = new House();
        if (StringUtils.isNotEmpty(houseDTO.getDescription())) {
            String descriptionCustom = houseDTO.getDescription().replaceAll("../download/assets/img","/download/assets/img");
            house.setDescription(descriptionCustom);
        }
        house.setId(house.getId());
        house.setPrice(houseDTO.getPrice());
        house.setTitle(houseDTO.getTitle());
        house.setHouseLength(houseDTO.getHouseLength());
        house.setHouseWidth(houseDTO.getHouseWidth());
        if (houseDTO.getImage() != null) {
            try {
                byte[] imageData = houseDTO.getImage().getBytes();
                String fileContent = Base64.getEncoder().encodeToString(imageData);
                house.setImage(fileContent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return house;
    }
}
