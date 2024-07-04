package project.batdongsan.service.impl;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.batdongsan.model.dto.HouseDTO;
import project.batdongsan.model.entity.House;
import project.batdongsan.repositoty.HouseRepository;
import project.batdongsan.service.HouseService;
import project.batdongsan.specification.HouseSpecification;

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
        String priceFormatVN = format.format(house.getPrice() == null ? 0 : house.getPrice());

        houseDTO.setId(house.getId());
        houseDTO.setDescription(house.getDescription());
        houseDTO.setPrice(house.getPrice());
        houseDTO.setTitle(house.getTitle());
        houseDTO.setHouseLength(house.getHouseLength());
        houseDTO.setHouseWidth(house.getHouseWidth());
        houseDTO.setImageBase64(house.getImage());
        houseDTO.setPriceFormatVN(priceFormatVN + " VND");
        houseDTO.setAddress(house.getAddress());
        houseDTO.setBedRoom(house.getBedRoom());
        houseDTO.setLivingRoom(house.getLivingRoom());
        houseDTO.setArea(house.getArea());

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
            String descriptionCustom = houseDTO.getDescription().replaceAll("../download/assets/img", "/download/assets/img");
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

    @Override
    public Page<HouseDTO> findByCondition(HouseDTO houseDTO, int page, int pageSize) {
        Specification<House> spec = new HouseSpecification(houseDTO);
        Page<House> housePage = houseRepository.findAll(spec, PageRequest.of(page, pageSize));
//        List<House> houses = houseRepository.findAll();
//        if (StringUtils.isNotEmpty(houseDTO.getIsSearchAddress())) {
//            houses = houses.stream().filter(h -> h.getAddress().contains(houseDTO.getAddress())).collect(Collectors.toList());
//        }
//        if (StringUtils.isNotEmpty(houseDTO.getIsSearchPrice())) {
//            houses = houses.stream().filter(h -> h.getPrice() >= houseDTO.getMinPrice() && h.getPrice() <= houseDTO.getMaxPrice()).collect(Collectors.toList());
//        }
//        if (StringUtils.isNotEmpty(houseDTO.getIsSearchAddress())) {
//            houses = houses.stream().filter(h -> h.getAddress().contains(houseDTO.getAddress())).collect(Collectors.toList());
//        }
//        if (StringUtils.isNotEmpty(houseDTO.getIsSearchArea())) {
//            houses = houses.stream().filter(h -> h.getArea() >= houseDTO.getMinArea() && h.getArea() <= houseDTO.getMinArea()).collect(Collectors.toList());
//        }
//        if (StringUtils.isNotEmpty(houseDTO.getIsSearchBedRoom())) {
//            houses = houses.stream().filter(h -> houseDTO.getBedRoom() == h.getBedRoom()).collect(Collectors.toList());
//        }
//        if (StringUtils.isNotEmpty(houseDTO.getIsSearchLivingRoom())) {
//            houses = houses.stream().filter(h -> houseDTO.getLivingRoom() == h.getLivingRoom()).collect(Collectors.toList());
//        }
//
//        int startItem = (page -1) * pageSize;
//        List<HouseDTO> houseDTOList = houses.stream()
//                .map(this::toHouseDto)
//                .toList();
//        List<HouseDTO> pageData = houseDTOList.stream()
//                .skip(startItem)
//                .limit(pageSize)
//                .toList();
//
        List<HouseDTO> houseDTOList = new ArrayList<>();
        for (House house : housePage.getContent()) {
            HouseDTO houseDto = toHouseDto(house);
            houseDTOList.add(houseDto);
        }
        return new PageImpl<>(houseDTOList, housePage.getPageable(), housePage.getTotalElements());

    }
}
