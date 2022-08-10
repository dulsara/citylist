package com.dulsara.citylist.ciltylistapp.city.controller;

import com.dulsara.citylist.ciltylistapp.city.model.City;
import com.dulsara.citylist.ciltylistapp.city.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("api/cities")
@RequiredArgsConstructor

public class CityController {

    private final CityService cityService;

    @PreAuthorize("hasAuthority('ROLE_ALLOW_EDIT')")
    @PutMapping
    public ResponseEntity<City> update(@RequestBody City city ) throws Exception{
        return ResponseEntity.ok().body(cityService.update(city));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAllByCity(@RequestParam(required = false) String name, Pageable pageable) {
        Map<String, Object> response = cityService.findAllWithName(name,pageable);
        return  ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> findCityById(@PathVariable Long id) {
        return ResponseEntity.ok().body(cityService.findById(id).orElse(new City()));
    }

}
