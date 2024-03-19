package project.batdongsan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.batdongsan.model.entity.House;
import project.batdongsan.repositoty.HouseRepository;
import project.batdongsan.service.HouseService;

@Service
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;

    @Autowired
    public HouseServiceImpl(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Override
    public void save(House house) {
        houseRepository.save(house);
    }
}
