package com.iisoft.unquitube.backend.model;

import com.iisoft.unquitube.backend.dto.UserDTO;

public class User {
    private Integer id;
    private String name;
    private String password;
    private Integer age;

    public User(){
    }

    public User(String name, String password, Integer age){
        this.name = name;
        this.password = password;
        this.age = age;
    }

    public User(UserDTO userDTO){
        this.id = userDTO.getId();
        this.name = userDTO.getName();
        this.password = userDTO.getPassword();
        this.age = userDTO.getAge();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
