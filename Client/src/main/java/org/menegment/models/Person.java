package org.menegment.models;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
public class Person implements Serializable {

    private Integer person_id;

    private String name_person;

    private String sur_name;

    private String last_name;


    public Person(Integer person_id, String name_person, String sur_name, String last_name) {
        this.person_id = person_id;
        this.name_person = name_person;
        this.sur_name = sur_name;
        this.last_name = last_name;
    }

    public Person() {

    }
}
