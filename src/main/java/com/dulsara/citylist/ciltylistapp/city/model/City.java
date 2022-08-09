package com.dulsara.citylist.ciltylistapp.city.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "city")
@Data
public class City {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
    String name;
    @Column(name="IMAGEURL", length=1000, nullable=false, unique=false)
    String imageURL;

}
