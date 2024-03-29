package project.batdongsan.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import project.batdongsan.model.dto.HouseDTO;
import project.batdongsan.model.entity.House;


@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseSpecification implements Specification<House> {
    private HouseDTO houseDTO;
    @Override
    public Predicate toPredicate(Root<House> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction(); // Tạo một Predicate rỗng để bắt đầu

        if (StringUtils.isNotEmpty(houseDTO.getIsSearchAddress())) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("address"), "%" + houseDTO.getAddress() + "%"));
        }

        if (StringUtils.isNotEmpty(houseDTO.getIsSearchPrice())) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("price"), houseDTO.getMinPrice(), houseDTO.getMaxPrice()));
        }

        if (StringUtils.isNotEmpty(houseDTO.getIsSearchArea())) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("area"), houseDTO.getMinArea(), houseDTO.getMaxArea()));
        }

        if (StringUtils.isNotEmpty(houseDTO.getIsSearchBedRoom())) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("bedRoom"), houseDTO.getBedRoom()));
        }

        if (StringUtils.isNotEmpty(houseDTO.getIsSearchLivingRoom())) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("livingRoom"), houseDTO.getLivingRoom()));
        }

        return predicate;
    }

}
