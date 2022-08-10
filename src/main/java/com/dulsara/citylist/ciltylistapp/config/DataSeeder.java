package com.dulsara.citylist.ciltylistapp.config;
;
import com.dulsara.citylist.ciltylistapp.city.model.City;
import com.dulsara.citylist.ciltylistapp.city.repository.CityRepository;
import com.dulsara.citylist.ciltylistapp.user.model.Role;
import com.dulsara.citylist.ciltylistapp.user.model.User;
import com.dulsara.citylist.ciltylistapp.user.model.UserRole;
import com.dulsara.citylist.ciltylistapp.user.repository.RoleRepository;
import com.dulsara.citylist.ciltylistapp.user.repository.UserRoleRepository;
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
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private final UserService userService;
    private final CityRepository cityRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;


    @Override
    public void run(ApplicationArguments args) {
        var jhon = new User(1L,"jhon", "jhon123");
        var carter = new User(2L, "carter","carter123");
        var rog = new User(3L, "rog","rog123");

        userService.save(jhon);
        userService.save(carter);
        var role = new Role(4L, "ROLE_ALLOW_EDIT");
        var role2 = new Role(5L, "ROLE_ALLOW_CUSTOMER");

        var userRole = new UserRole(1L, 4L);
        var userRole2 = new UserRole(2L, 5L);

        userService.save(jhon);
        userService.save(carter);
        userService.save(rog);
        roleRepository.save(role);
        roleRepository.save(role2);
        userRoleRepository.save(userRole);
        userRoleRepository.save(userRole2);


        List<City> cities = new ArrayList<>();
        try {
        InputStream resource = new ClassPathResource(
                "cities.csv").getInputStream();
        BufferedReader br = new BufferedReader(
                new InputStreamReader(resource));
                // read the first line from the text file
            String line = br.readLine(); // loop until all lines are read
            line = br.readLine();
             while (line != null) { // use string.split to load a string array with the values from // each line of // the file, using a comma as the delimiter
                  String[] attributes = line.split(",");
                  City city = new City(Long.parseLong(attributes[0]),attributes[1],attributes[2]); // adding cities into ArrayList
                  cities.add(city); // read next line before looping // if end of file reached, line would be null
                  line = br.readLine();
                  cityRepository.save(city);
             }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

}
