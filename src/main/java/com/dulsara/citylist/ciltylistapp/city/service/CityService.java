package com.dulsara.citylist.ciltylistapp.city.service;

import com.dulsara.citylist.ciltylistapp.city.model.City;
import com.dulsara.citylist.ciltylistapp.city.repository.CityRepository;
import com.dulsara.citylist.ciltylistapp.util.GlobalConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    public Map<String,Object> findAllWithName(String name, Pageable pageable) {
        Page<City> pageTuts;
        pageTuts = cityRepository.findAllWithName(name==null?null:name.toLowerCase(), pageable);

        List<City> cities = new ArrayList<City>();

        cities = pageTuts.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("cities", cities);
        response.put("totalPages", pageTuts.getTotalPages());
        return response;
    }
    public Optional <City> findById(Long id) {
        return cityRepository.findById(id);
    }

    private City validateCity(City city, String updateMode) throws Exception {
        City validatedCity = null;
        if (city.getName() == null || city.getName().isEmpty()) {
            throw new Exception(GlobalConstant.CityErrors.CITY_NAME_MANDATORY_ERROR);
        }
        if (city.getImageURL() == null || city.getImageURL().isEmpty()) {
            throw new Exception(GlobalConstant.CityErrors.IMAGE_URL_MANDATORY_ERROR);
        }
        if (!Pattern.matches(GlobalConstant.urlRegex, city.getImageURL())){
            throw new Exception(GlobalConstant.CityErrors.IMAGE_URL_STANDARD_ERROR);
        }
        if ((city.getId() == null || city.getId() < 1) && GlobalConstant.UPDATE.equals(updateMode)){
            throw new Exception(GlobalConstant.CityErrors.CITY_ID_MANDATORY_ERROR);
        }
        long cityId = city.getId()==null?0L:city.getId();
        Optional<City> existingCityOptional = cityRepository.findCityByNameExcludingId(city.getName(),cityId);
        if (existingCityOptional.isPresent() ) {
            throw new Exception(GlobalConstant.CityErrors.CITY_NAME_EXISTING_ERROR);
        }
        if (GlobalConstant.UPDATE.equals(updateMode)) {
            validatedCity = cityRepository.getById(city.getId());
            if (validatedCity == null) {
                throw new Exception(GlobalConstant.CityErrors.CITY_ID_EXISTING_ERROR);
            }
        } else {
            validatedCity = new City();
        }
        validatedCity.setName(city.getName());
        validatedCity.setImageURL(city.getImageURL());
        return validatedCity;
    }

}
