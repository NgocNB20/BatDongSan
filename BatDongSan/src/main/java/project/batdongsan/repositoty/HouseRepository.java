package project.batdongsan.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import project.batdongsan.model.entity.House;

@RequestMapping
public interface HouseRepository extends JpaRepository<House, Integer> {

}
