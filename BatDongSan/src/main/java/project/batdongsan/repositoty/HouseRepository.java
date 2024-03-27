package project.batdongsan.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.batdongsan.model.dto.HouseDTO;
import project.batdongsan.model.entity.House;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House, Integer> {

    @Query("SELECT h FROM  houseDTO h")
    List<House> searchByCondition(HouseDTO houseDTO);
}
