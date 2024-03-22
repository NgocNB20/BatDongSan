package project.batdongsan.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.batdongsan.model.dto.HouseDTO;
import project.batdongsan.service.HouseService;

import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final HouseService houseService;

    @Autowired
    public AdminController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping("/index")
    public String showPageAdmin(@ModelAttribute HouseDTO houseDTO) throws IOException {
        return "admin/house/index";
    }

    @PostMapping("/save-house")
    public String saveHouse(@ModelAttribute HouseDTO houseDTO) throws IOException {
        houseService.save(houseDTO);
        return "redirect:/admin/index";
    }
}
