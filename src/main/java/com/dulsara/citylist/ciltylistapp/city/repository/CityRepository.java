package com.dulsara.citylist.ciltylistapp.city.repository;

import com.dulsara.citylist.ciltylistapp.city.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query(
            value = "SELECT * FROM city c where c.name =:name and c.id != :id ",
            nativeQuery = true)
    Optional<City> findCityByNameExcludingId(@Param("name") String name , @Param("id") Long id);

    @Query(
            value = "SELECT * FROM city c where  (:lookup is null) or (LOWER( c.name )   LIKE %:lookup% ) ",
            countQuery = "SELECT count(1) FROM city c2  where  (:lookup is null) or (LOWER( c2.name )  LIKE %:lookup% )",
            nativeQuery = true)
    Page<City> findAllWithName(@Param("lookup") String lookup, Pageable pageable);

}
