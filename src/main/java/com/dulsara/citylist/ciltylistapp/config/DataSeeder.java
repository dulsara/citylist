package com.dulsara.citylist.ciltylistapp.config;
;
import com.dulsara.citylist.ciltylistapp.city.model.City;
import com.dulsara.citylist.ciltylistapp.city.repository.CityRepository;
import com.dulsara.citylist.ciltylistapp.user.model.Role;
import com.dulsara.citylist.ciltylistapp.user.model.User;
import com.dulsara.citylist.ciltylistapp.user.model.UserRole;
import com.dulsara.citylist.ciltylistapp.user.repository.RoleRepository;
import com.dulsara.citylist.ciltylistapp.user.repository.UserRepository;
//import com.dulsara.citylist.ciltylistapp.user.repository.UserRoleRepository;
import com.dulsara.citylist.ciltylistapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {



    private final UserService userService;
    private final CityRepository cityRepository;
//    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;


    @Override
    public void run(ApplicationArguments args) {
        var dul = new User(1L,"dulsara", "dulsara123");
        var manu = new User(2L, "manu","manu123");
        userService.save(dul);
        userService.save(manu);
        var role = new Role(1L, "ROLE_ALLOW_EDIT");

//        var userRole = new UserRole(1L, 1L);
        userService.save(dul);
        userService.save(manu);
        roleRepository.save(role);
//        userRoleRepository.save(userRole);

        List<City> books = new ArrayList<>();
        try {
        InputStream resource = new ClassPathResource(
                "cities.csv").getInputStream();
        // using try with resource, Java 7 feature to close resources
        BufferedReader br = new BufferedReader(
                new InputStreamReader(resource));
                // read the first line from the text file
            String line = br.readLine(); // loop until all lines are read
            line = br.readLine();
             while (line != null) { // use string.split to load a string array with the values from // each line of // the file, using a comma as the delimiter
                  String[] attributes = line.split(",");
                  City city = new City(Long.parseLong(attributes[0]),attributes[1],attributes[2]); // adding book into ArrayList
                  books.add(city); // read next line before looping // if end of file reached, line would be null
                  line = br.readLine();
                  cityRepository.save(city);
             }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

}
