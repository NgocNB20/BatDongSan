package project.batdongsan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.batdongsan.model.entity.House;
import project.batdongsan.model.request.HouseRequest;
import project.batdongsan.repositoty.HouseRepository;
import project.batdongsan.service.HouseService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/house")
public class HouseController {

    private final HouseService houseService;

    private final HouseRepository houseRepository;

    @Autowired
    public HouseController(HouseService houseService, HouseRepository houseRepository) {
        this.houseService = houseService;
        this.houseRepository = houseRepository;
    }

    @GetMapping("/index")
    public String showUploadForm(Model model) {
        List<House> houses = houseRepository.findAll();
//        model.addAttribute("houseRequest",new HouseRequest());
        List<String> images = new ArrayList<>();
        houses.forEach(house -> {
            images.add(Base64.getEncoder().encodeToString(house.getImage()));
        });
        model.addAttribute("images",images);
        return "house/index";
    }

    @PostMapping("/upload")
    public String uploadImage(@ModelAttribute HouseRequest houseRequest) throws IOException {
        House house = new House();
        house.setImage(houseRequest.getImage().getBytes());
        houseService.save(house);
        return "redirect:/house/index";
    }
}
