package project.batdongsan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.batdongsan.model.dto.HouseDTO;
import project.batdongsan.model.entity.House;
import project.batdongsan.repositoty.HouseRepository;
import project.batdongsan.service.HouseService;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;

    @Autowired
    public HouseServiceImpl(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Override
    public void save(HouseDTO houseDTO) {
        House house = toHouseEntity(houseDTO);
        houseRepository.save(house);
    }

    @Override
    public List<HouseDTO> findAll() {

        return null;
    }

    @Override
    public HouseDTO toHouseDto(House house) {
        return null;
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

    @Override
    public House toHouseEntity(HouseDTO houseDTO) {
        House house = new House();
        if (houseDTO.getId()!=null) {
            house.setId(house.getId());
        }
        if (houseDTO.getDescription() != null) {
            house.setDescription(houseDTO.getDescription());
        }
        if (houseDTO.getPrice() != null) {
            house.setPrice(house.getPrice());
        }
        if (houseDTO.getImage() != null) {
            house.setTitle(house.getTitle());
        }
        if (houseDTO.getHouseLength() != null) {
            house.setHouseLength(houseDTO.getHouseLength());
        }
        if (houseDTO.getHouseWidth() != null) {
            house.setHouseWidth(houseDTO.getHouseWidth());
        }
        if (houseDTO.getImage() != null) {
            byte[] fileContent = new byte[0];
            try {
                fileContent = houseDTO.getImage().getBytes();
                house.setImage(Base64.getEncoder().encode(fileContent));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return house;
    }
}
