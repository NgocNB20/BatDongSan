package project.batdongsan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.batdongsan.model.dto.HouseDTO;
import project.batdongsan.repositoty.HouseRepository;
import project.batdongsan.service.HouseService;
import java.io.IOException;


@Controller
@RequestMapping("/house")
public class HouseController {

    private final HouseService houseService;

    @Autowired
    public HouseController(HouseService houseService, HouseRepository houseRepository) {
        this.houseService = houseService;
    }

    @GetMapping("/index")
    public String showUploadForm() {
        return "/index";
    }

    @PostMapping("/upload")
    public String uploadImage(@ModelAttribute HouseDTO houseDTO) throws IOException {
        houseService.save(houseDTO);
        return "redirect:/house/index";
    }
}
