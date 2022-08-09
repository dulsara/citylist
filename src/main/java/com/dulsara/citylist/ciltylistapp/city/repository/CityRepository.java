package com.dulsara.citylist.ciltylistapp.city.repository;

import com.dulsara.citylist.ciltylistapp.city.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findCityByName(String name);

    @Query(
            value = "SELECT * FROM city c where  (:lookup is null) or (c.name  LIKE %:lookup% ) ",
            countQuery = "SELECT count(1) FROM city c2  where  (:lookup is null) or (c2.name  LIKE %:lookup% )",
            nativeQuery = true)
    Page<City> findAllWithName(@Param("lookup") String lookup, Pageable pageable);

}
