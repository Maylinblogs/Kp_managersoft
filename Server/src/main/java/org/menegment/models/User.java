package org.menegment.models;


import org.menegment.enums.Roles;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Data
public class User  implements Serializable {

    private Integer user_id;

    private String login;

    private String password;

    private Roles role_user;

    private Person Person;

    private Set<Worker> workerList = new HashSet<>();

    private Set<Department> departmentList = new HashSet<>();

    public User(Integer user_id, String login, String password, Roles role_user, Person person, Set<Worker> workerList, Set<Department> departmentList) {
        this.user_id = user_id;
        this.login = login;
        this.password = password;
        this.role_user = role_user;
        Person = person;
        this.workerList = workerList;
        this.departmentList = departmentList;
    }

    public User() {

    }
}