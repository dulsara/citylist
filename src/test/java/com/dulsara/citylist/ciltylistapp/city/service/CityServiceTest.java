package com.dulsara.citylist.ciltylistapp.city.service;

import com.dulsara.citylist.ciltylistapp.city.model.City;
import com.dulsara.citylist.ciltylistapp.city.repository.CityRepository;
import com.dulsara.citylist.ciltylistapp.util.GlobalConstant;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;
    @SneakyThrows
    @Test
    void update() {
        City city = new City();
        city.setId(1L);
        city.setName("TestCity");
        city.setImageURL("https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/IN-DL.svg/439px-IN-DL.svg.png");
        Mockito.when(cityRepository.findCityByNameExcludingId("TestCity",1L)).thenReturn(Optional.ofNullable(null));
        Mockito.when(cityRepository.getById(1L)).thenReturn(city);
        Mockito.when(cityRepository.save(city)).thenReturn(city);
        City cityResponse = cityService.update(city);
        Assertions.assertNotNull(cityResponse);
        Assertions.assertEquals(city, cityResponse, "Checking that Equal Wallet should be there");
        // check the exception

        City city2 = new City();
        city2.setId(2L);
        city2.setName("TestCity2");
        city2.setImageURL("https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/IN-DL.svg/439px-IN-DL.svg.png");
        Mockito.when(cityRepository.findById(2L)).thenReturn(Optional.of(city2));
        // update name as null
        city2.setName(null);


        Exception exception = assertThrows(Exception.class, () -> {
           City city1= cityService.update(city2);
        });

        String expectedMessage = GlobalConstant.CityErrors.CITY_NAME_MANDATORY_ERROR;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        city2.setName("TestCity3");
        city2.setImageURL(null);
        Exception exception1 = assertThrows(Exception.class, () -> {
            City city1= cityService.update(city2);
        });
        String expectedMessage1 = GlobalConstant.CityErrors.IMAGE_URL_MANDATORY_ERROR;
        String actualMessage1 = exception1.getMessage();
        assertTrue(actualMessage1.contains(expectedMessage1));

        city2.setName("TestCity3");
        city2.setImageURL("https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/IN-DL.svg/439px-IN-DL.svg.png");
        city2.setId(0L);
        Exception exception2 = assertThrows(Exception.class, () -> {
            City city1= cityService.update(city2);
        });
        String expectedMessage2 = GlobalConstant.CityErrors.CITY_ID_MANDATORY_ERROR;
        String actualMessage2 = exception2.getMessage();
        assertTrue(actualMessage2.contains(expectedMessage2));

        city2.setName("TestCity3");
        city2.setImageURL("abc");
        city2.setId(2L);
        Exception exception3 = assertThrows(Exception.class, () -> {
            City city1= cityService.update(city2);
        });
        String expectedMessage3 = GlobalConstant.CityErrors.IMAGE_URL_STANDARD_ERROR;
        String actualMessage3 = exception3.getMessage();
        assertTrue(actualMessage3.contains(expectedMessage3));

        city2.setName("TestCity4");
        city2.setImageURL("https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/IN-DL.svg/439px-IN-DL.svg.png");
        city2.setId(100L);
        Mockito.when(cityRepository.findById(100L)).thenReturn(Optional.ofNullable(null));

        Exception exception4 = assertThrows(Exception.class, () -> {
            City city1= cityService.update(city2);
        });
        String expectedMessage4 = GlobalConstant.CityErrors.CITY_ID_EXISTING_ERROR;
        String actualMessage4 = exception4.getMessage();
        assertTrue(actualMessage4.contains(expectedMessage4));

        city2.setName("TestCity4");
        city2.setImageURL("https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/IN-DL.svg/439px-IN-DL.svg.png");
        city2.setId(2L);
        Mockito.when(cityRepository.findCityByNameExcludingId("TestCity4",2L)).thenReturn(Optional.of(city2));

        Exception exception5 = assertThrows(Exception.class, () -> {
            City city1= cityService.update(city2);
        });
        String expectedMessage5 = GlobalConstant.CityErrors.CITY_NAME_EXISTING_ERROR;
        String actualMessage5= exception5.getMessage();
        assertTrue(actualMessage5.contains(expectedMessage5));
    }

    @Test
    void findAllWithName() {

        City city = new City();
        city.setId(2L);
        city.setName("TestCity2");
        city.setImageURL("https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/IN-DL.svg/439px-IN-DL.svg.png");
        Pageable pageable =  PageRequest.of(0, 2);
        Mockito.when(cityRepository.findAllWithName("TestCity2".toLowerCase(),pageable)).thenReturn(new PageImpl<City>(Arrays.asList(city), pageable,  1L));
        Map<String,Object> map = cityService.findAllWithName("TestCity2".toLowerCase(),pageable);

        Assertions.assertNotNull(map);
        List<City> cities = (List<City>) map.get("cities");
        Assertions.assertEquals(1, cities.size(), "check array size");
        assertEquals(true, cities.stream().anyMatch((s) -> "TestCity2".equalsIgnoreCase(s.getName())), "check weather object are in find all");
    }

    @Test
    void findById() {
        String cityName = "TestCity3";
        City city = new City();
        city.setId(3L);
        city.setName(cityName);
        city.setImageURL("https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/IN-DL.svg/439px-IN-DL.svg.png");
        Mockito.when(cityRepository.findById(3L)).thenReturn(Optional.of(city));
        Optional<City> cityOptional = cityService.findById(3L);
        Assertions.assertNotNull(cityOptional.get());
        Assertions.assertEquals(3L, cityOptional.get().getId(), " check equality of Object taken by Id");
    }
}