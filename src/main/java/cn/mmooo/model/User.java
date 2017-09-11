package cn.mmooo.model;


import lombok.Data;

import javax.persistence.*;

@Table(name = "user233")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

}
