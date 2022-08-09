package com.dulsara.citylist.ciltylistapp.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_role")
@Data
public class UserRole implements Serializable {

    @Id
    private Long user_id;

    @Id
    private Long role_id;

}
