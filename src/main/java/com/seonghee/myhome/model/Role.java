package com.seonghee.myhome.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    //User 매핑
    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
