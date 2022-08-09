package com.dulsara.citylist.ciltylistapp.city.repository;

import com.dulsara.citylist.ciltylistapp.city.model.City;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CityRepositoryTest {

    @Autowired
    private CityRepository cityRepository;

    @Test
    @Order(1)
    void save() {

        String walletName = "TestRepo1";
        City city = new City();
        city.setBalance(500D);
        city.setName(walletName);
        City city1 =  cityRepository.save(city);

        Assertions.assertNotNull(city1.getId());
        Assertions.assertEquals(walletName, city1.getName(), "check Name  ");
    }

    @Test
    @Order(2)
    void findById() {

        City city = new City();
        String walletName = "TestRepo2";
        city.setBalance(1000D);
        city.setName(walletName);
        City city1 =  cityRepository.save(city);
        Optional<City> walletOptional = cityRepository.findById(city1.getId());
        Assertions.assertNotNull(walletOptional.get());
        Assertions.assertEquals(walletName,walletOptional.get().getName()," check equality of Object taken by name");

    }

    @Test
    @Order(3)
    void findAll() {

        City city = new City();
        String walletName = "TestRepo3";
        city.setBalance(1000D);
        city.setName(walletName);
        cityRepository.save(city);
        List<City> cityList = cityRepository.findAll();
        Assertions.assertNotNull(cityList);
        Assertions.assertEquals(1, cityList.size(),"check array size");
        assertEquals(true, cityList.stream().anyMatch((s)-> walletName.equalsIgnoreCase(s.getName())),"check weather object are in find all");

    }

    @Test
    @Order(4)
    void findWalletByName() {
        String walletName = "TestRepo4";
        City city = new City();
        city.setBalance(500D);
        city.setName(walletName);
        cityRepository.save(city);
        Optional<City> walletOptional = cityRepository.findCityByName(walletName);
        Assertions.assertNotNull(walletOptional.get());
        Assertions.assertEquals(walletName,walletOptional.get().getName()," check equality of Object taken by name");
    }
}