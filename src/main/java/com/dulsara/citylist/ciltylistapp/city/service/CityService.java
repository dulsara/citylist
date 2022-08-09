package com.dulsara.citylist.ciltylistapp.city.service;

import com.dulsara.citylist.ciltylistapp.exception.ResourceNotFoundException;
import com.dulsara.citylist.ciltylistapp.city.model.City;
import com.dulsara.citylist.ciltylistapp.city.repository.CityRepository;
import com.dulsara.citylist.ciltylistapp.util.GlobalConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional
public class CityService {

    private final CityRepository cityRepository;

    public City update(City city) throws Exception {
        City validatedCity = validateCity(city,GlobalConstant.UPDATE);
        return cityRepository.save(validatedCity);
    }
    public Page<City> findAll(Pageable pageable) {
        return cityRepository.findAll(pageable);
    }

    public Page<City> findAll(String name, Pageable pageable) {
        return cityRepository.findAll(pageable);
    }

    public Map<String,Object> findAllWithName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<City> pageTuts;
        pageTuts = cityRepository.findAllWithName(name, pageable);

        List<City> cities = new ArrayList<City>();

        cities = pageTuts.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("cities", cities);
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalItems", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());
        return response;
    }

    public Optional <City> findById(Long id) {
        return cityRepository.findById(id);
    }

    public Optional<City> findByCityName(String name) {
        return cityRepository.findCityByName(name);
    }

    public Optional<City> findByCityId(Long id) {
        return cityRepository.findById(id);
    }

    private City validateCity(City city, String updateMode) throws Exception {
        City validatedCity = null;
        if (city.getName() == null || city.getName().isEmpty()) {
            throw new Exception("City Name is Mandatory");
        }
        if (city.getImageURL() == null || city.getImageURL().isEmpty()) {
            throw new Exception("City Image URL  is Mandatory");
        }
        if ((city.getId() == null || city.getId() < 1) && GlobalConstant.UPDATE.equals(updateMode)){
            throw new Exception("City Id is Mandatory or greater than 0");
        }
        if (Pattern.matches(GlobalConstant.urlRegex, city.getImageURL())){
            throw new Exception("City Image URL is not in standard Pattern");
        }
        if (Pattern.matches(GlobalConstant.cityNameRegex, city.getName())){
            throw new Exception("City Name is not in standard Pattern");
        }
        if ((city.getId() == null || city.getId() < 1) && GlobalConstant.UPDATE.equals(updateMode)){
            throw new Exception("City Id is Mandatory or greater than 0");
        }
        Optional<City> existingCityOptional = cityRepository.findCityByName(city.getName());
        if (existingCityOptional.isPresent() ) {
            throw new Exception("Given City Name is already existing");
        }
        if (GlobalConstant.UPDATE.equals(updateMode)) {
            validatedCity = cityRepository.getById(city.getId());
            if (validatedCity == null) {
                throw new Exception("No City for given Id");
            }
        } else {
            validatedCity = new City();
        }
        validatedCity.setName(city.getName());
        validatedCity.setImageURL(city.getImageURL());
        return validatedCity;
    }

}
