package org.menegment.models;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
public class Department implements Serializable {

    private Integer department_id;

    private String name_department;
    private String address;

    private Integer id_user;

    private Set<Vacancies> vacanciesList = new HashSet<>();

    @Builder
    public Department(Integer department_id, String name_department, String address, Set<Vacancies> vacanciesList) {
        this.department_id = department_id;
        this.name_department = name_department;
        this.address = address;
        this.vacanciesList = vacanciesList;
    }

    public Department() {

    }
}
