package com.dulsara.citylist.ciltylistapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class CityListAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CityListAppApplication.class, args);
	}

}
