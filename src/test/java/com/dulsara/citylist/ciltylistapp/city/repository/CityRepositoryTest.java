package com.dulsara.citylist.ciltylistapp.city.repository;

import com.dulsara.citylist.ciltylistapp.city.model.City;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@DataJpaTest
class CityRepositoryTest {

    @Autowired
    private CityRepository cityRepository;

    @Test
    void save() {
        City city = new City();
        String cityName = "TestCountry";
        String imageURL = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/IN-DL.svg/439px-IN-DL.svg.png";
        city.setName(cityName);
        city.setImageURL(imageURL);
        City city1 =  cityRepository.save(city);
        Assertions.assertNotNull(city1.getId());
        Assertions.assertEquals(cityName, city1.getName(), "check equality of Object taken by name  ");
    }
    @Test
    void findById() {
        City city = new City();
        String cityName = "TestCountry";
        String imageURL = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/IN-DL.svg/439px-IN-DL.svg.png";
        city.setName(cityName);
        city.setImageURL(imageURL);
        City city1 =  cityRepository.save(city);
        Optional<City> cityOptional = cityRepository.findById(city1.getId());
        Assertions.assertNotNull(cityOptional.get());
        Assertions.assertEquals(cityName,cityOptional.get().getName()," check equality of Object taken by name");
    }

    @Test
    void findAllWithName() {
        City city = new City();
        String cityName = "TestCountry";
        String imageURL = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/IN-DL.svg/439px-IN-DL.svg.png";
        city.setName(cityName);
        city.setImageURL(imageURL);
        City cityResponse =  cityRepository.save(city);
        City city1 = new City();
        String cityName1 = "TestCountry1";
        String imageURL1 = "https://upload.wikimedia.org/wikipedia/commons/thumb/51/55/IN-DL.svg/439px-IN-DL.svg.png";
        city1.setName(cityName1);
        city1.setImageURL(imageURL1);
        City cityResponse1 =  cityRepository.save(city1);
        City city2 = new City();
        String cityName2 = "TestCountry2";
        String imageURL2 = "https://upload.wikimedia.org/wikipedia/commons/thumb/52/55/IN-DL.svg/439px-IN-DL.svg.png";
        city2.setName(cityName2);
        city2.setImageURL(imageURL2);
        City cityResponse2 =  cityRepository.save(city2);
        Pageable pageable =  PageRequest.of(0, 2);
        Page<City> cityPage = cityRepository.findAllWithName(null,pageable);
        Assertions.assertNotNull(cityPage.getContent());
        Assertions.assertEquals(2,cityPage.getContent().size()," check the size of return page");
        Assertions.assertEquals(true,cityPage.getContent().contains(cityResponse) || cityPage.getContent().contains(cityResponse1)||cityPage.getContent().contains(cityResponse2) ,"check given city is available in the paging result");

        Pageable pageable1 =  PageRequest.of(1, 2);
        Page<City> cityPage1 = cityRepository.findAllWithName(null,pageable1);
        Assertions.assertNotNull(cityPage1.getContent());
        Assertions.assertEquals(1,cityPage1.getContent().size()," check the size of return page");
        Assertions.assertEquals(true,cityPage1.getContent().contains(cityResponse) || cityPage1.getContent().contains(cityResponse1)||cityPage1.getContent().contains(cityResponse2) ,"check given city is available in the paging result");

        Pageable pageable2 =  PageRequest.of(0, 2);
        Page<City> cityPage2 = cityRepository.findAllWithName(cityName1.toLowerCase(),pageable2);
        Assertions.assertNotNull(cityPage2.getContent());
        Assertions.assertEquals(1,cityPage2.getContent().size()," check the size of return page");
        Assertions.assertEquals(true,cityPage2.getContent().contains(cityResponse1) ,"check given city is available in the paging result");

    }

    @Test
    void findCityByName() {
        City city = new City();
        String cityName = "TestCountry100";
        String imageURL = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/IN-DL.svg/439px-IN-DL.svg.png";
        city.setName(cityName);
        city.setImageURL(imageURL);
        city.setId(1L);
        cityRepository.save(city);

        // when passing id=1, nothing should pop up
        Optional<City> cityOptional = cityRepository.findCityByNameExcludingId(cityName,1L);

        Assertions.assertEquals(false,cityOptional.isPresent());

        // given unavailable id and check the result is coming
        Optional<City> cityOptional1 = cityRepository.findCityByNameExcludingId(cityName,1000L);

        Assertions.assertNotNull(cityOptional1.get());
        Assertions.assertEquals(cityName,cityOptional1.get().getName()," check equality of Object taken by name");
    }
}