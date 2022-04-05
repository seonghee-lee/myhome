package com.seonghee.myhome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private List<User> users;
}
