package project.batdongsan.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import project.batdongsan.model.dto.HouseDTO;
import project.batdongsan.model.entity.House;

import java.util.List;

@RequestMapping
public interface HouseRepository extends JpaRepository<House, Integer> {


    @Query("SELECT h FROM House h WHERE h.price = :#{#houseDTO.price}")
    List<House> search (HouseDTO houseDTO);

}
