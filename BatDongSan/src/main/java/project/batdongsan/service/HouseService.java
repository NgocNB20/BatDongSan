package project.batdongsan.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import project.batdongsan.model.dto.HouseDTO;
import project.batdongsan.model.entity.House;

import java.util.List;

public interface HouseService {
    HouseDTO findById(Integer id);
    void save(HouseDTO houseDTO);

    List<HouseDTO> findAll();

    HouseDTO toHouseDto(House house);

    String uploadImage(MultipartFile multipartFile);

    House toHouseEntity(HouseDTO houseDTO);

    Page<HouseDTO> findByCondition(HouseDTO houseDTO,int page, int size);

}
