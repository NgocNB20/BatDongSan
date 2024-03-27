package project.batdongsan.repositoty;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.batdongsan.model.dto.HouseDTO;
import project.batdongsan.model.entity.House;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House, Integer> {

    @Query("SELECT h FROM House h " +
            "WHERE 1 = 1 AND (:isSearchPrice = 'on' AND h.price BETWEEN :minPrice AND :maxPrice) AND " +
            "(:isSearchAddress = 'on' AND h.address LIKE %:address%) AND " +
            "(:isSearchArea = 'on' AND h.area BETWEEN :minArea AND :maxArea) AND " +
            "(:isSearchBedRoom = 'on' AND h.bedRoom = :bedRoom) AND " +
            "(:isSearchLivingRoom = 'on' AND h.livingRoom = :livingRoom)")
    Page<House> findByCondition(@Param("isSearchPrice") String isSearchPrice,
                                @Param("minPrice") Integer minPrice,
                                @Param("maxPrice") Integer maxPrice,
                                @Param("isSearchAddress") String isSearchAddress,
                                @Param("address") String address,
                                @Param("isSearchArea") String isSearchArea,
                                @Param("minArea") Integer minArea,
                                @Param("maxArea") Integer maxArea,
                                @Param("isSearchBedRoom") String isSearchBedRoom,
                                @Param("bedRoom") Integer bedRoom,
                                @Param("isSearchLivingRoom") String isSearchLivingRoom,
                                @Param("livingRoom") Integer livingRoom,
                                Pageable pageable);
}
