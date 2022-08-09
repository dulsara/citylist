package com.dulsara.citylist.ciltylistapp.city.service;

import com.dulsara.citylist.ciltylistapp.exception.ResourceNotFoundException;
import com.dulsara.citylist.ciltylistapp.city.model.City;
import com.dulsara.citylist.ciltylistapp.city.repository.CityRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;


    @Test
    @Order(1)
    void save() {
//        City city = new City();
//        city.setId(1L);
//        city.setBalance(500D);
//        city.setName("Test1");
//        Mockito.when(cityRepository.save(city)).thenReturn(city);
//        City city1 = cityService.save(city);
//        Assertions.assertNotNull(city1);
//        Assertions.assertEquals(city, city1, "Checking that Equal Wallet should be there");


    }

    @Test
    @Order(2)
    void findAll() {

        City city = new City();
        city.setId(2L);
        city.setName("Test2");
        Mockito.when(cityRepository.findAll()).thenReturn(Arrays.asList(city));
        List<City> cityList = cityService.findAll();

        Assertions.assertNotNull(cityList);
        Assertions.assertEquals(1, cityList.size(), "check array size");
        assertEquals(true, cityList.stream().anyMatch((s) -> "Test2".equalsIgnoreCase(s.getName())), "check weather object are in find all");

    }

    @Test
    @Order(3)
    void findByWalletName() {

        String walletName = "Test3";
        City city = new City();
        city.setId(3L);
        ;
        city.setName(walletName);
        Mockito.when(cityRepository.findCityByName(walletName)).thenReturn(Optional.of(city));
        Optional<City> walletOptional = cityService.findByCityName(walletName);
        Assertions.assertNotNull(walletOptional.get());
        Assertions.assertEquals(walletName, walletOptional.get().getName(), " check equality of Object taken by name");
    }

    @Test
    @Order(4)
    void findByWalletId() {
        String walletName = "Test4";
        City city = new City();
        city.setId(4L);
        ;
        city.setName(walletName);
        Mockito.when(cityRepository.findById(4L)).thenReturn(Optional.of(city));
        Optional<City> walletOptional = cityService.findByCityId(4L);
        Assertions.assertNotNull(walletOptional.get());
        Assertions.assertEquals(4L, walletOptional.get().getId(), " check equality of Object taken by Id");
    }

//    @SneakyThrows
//    @Test
//    @Order(5)
//    void addMoneyToWallet() {
//        City city = new City();
//        city.setId(5L);
//        ;
//        city.setName("Test5");
//        Mockito.when(cityRepository.findById(5L)).thenReturn(Optional.of(city));
//        Optional<City> walletOptional = cityService.addMoneyToWallet(5L, 100D);
//        Assertions.assertEquals(1100D, walletOptional.get().getBalance(), " check the added amount");
//
//        Mockito.when(cityRepository.findById(100L)).thenReturn(Optional.ofNullable(null));
//
//        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
//            Optional<City> walletOptional1 = cityService.addMoneyToWallet(100L, 100D);
//        });
//
//        String expectedMessage = "Wallet not found for this id :: 100";
//        String actualMessage = exception.getMessage();
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//    @SneakyThrows
//    @Test
//    @Order(6)
//    void removeMoneyFromWallet() {
//        City city = new City();
//        city.setId(6L);
//        ;
//        city.setName("Test6");
//        Mockito.when(cityRepository.findById(6L)).thenReturn(Optional.of(city));
//        Optional<City> walletOptional = cityService.removeMoneyFromWallet(6L, 100D);
//        Assertions.assertEquals(900D, walletOptional.get().getBalance(), " check the remove amount");
//
//        Mockito.when(cityRepository.findById(100L)).thenReturn(Optional.ofNullable(null));
//
//        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
//            Optional<City> walletOptional1 = cityService.removeMoneyFromWallet(100L, 100D);
//        });
//        String expectedMessage = "Wallet not found for this id :: 100";
//        String actualMessage = exception.getMessage();
//        assertTrue(actualMessage.contains(expectedMessage));
//
//        exception = assertThrows(Exception.class, () -> {
//            Optional<City> walletOptional1 = cityService.removeMoneyFromWallet(6L, 1100D);
//        });
//
//        expectedMessage = "Wallet : Test6 has not enough money to withdraw given amount : 1100";
//        actualMessage = exception.getMessage();
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//
//    @SneakyThrows
//    @Test
//    @Order(7)
//    void walletToWalletTranFer() {
//        City city = new City();
//        city.setId(7L);
//        ;
//        city.setName("Test7");
//
//        City city1 = new City();
//        city1.setId(8L);
//        city1.setBalance(2000D);
//        city1.setName("Test8");
//
//        Mockito.when(cityRepository.findById(7L)).thenReturn(Optional.of(city));
//        Mockito.when(cityRepository.findById(8L)).thenReturn(Optional.of(city1));
//        Optional<City> walletOptional = cityService.walletToWalletTranFer(7L, 8L, 100D);
//        Assertions.assertEquals(900D, walletOptional.get().getBalance(), " check the transfer amount");
//
//        Mockito.when(cityRepository.findById(100L)).thenReturn(Optional.ofNullable(null));
//
//        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
//            Optional<City> walletOptional1 = cityService.walletToWalletTranFer(100L, 7L, 100D);
//        });
//        String expectedMessage = "Wallet not found for these ids :: 7 & 100";
//        String actualMessage = exception.getMessage();
//        assertTrue(actualMessage.contains(expectedMessage));
//
//        exception = assertThrows(Exception.class, () -> {
//            Optional<City> walletOptional1 = cityService.walletToWalletTranFer(7L, 8L, 1100D);
//        });
//
//        expectedMessage = "Wallet : Test7 has not enough money to transfer given amount : 1100";
//        actualMessage = exception.getMessage();
//        assertTrue(actualMessage.contains(expectedMessage));
//    }


//    @Test
//    void checkValidBalanceAvailable() {
//        assertTrue(cityService.checkValidBalanceAvailable(100D, 50D));
//        assertTrue(cityService.checkValidBalanceAvailable(100D, 100D));
//        assertFalse(cityService.checkValidBalanceAvailable(100D, 101D));
//        assertFalse(cityService.checkValidBalanceAvailable(100D, 100.01));
//    }
}