package project.batdongsan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.batdongsan.model.entity.House;
import project.batdongsan.repositoty.HouseRepository;
import project.batdongsan.service.HouseService;

import java.io.IOException;
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
        model.addAttribute("houses", houses);
        return "house/index";
    }

    @PostMapping("/upload")
    public String uploadImage(Model model,House house) throws IOException {
        houseService.save(house);
        return "redirect:/index";
    }



}
