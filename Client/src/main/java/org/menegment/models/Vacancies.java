package org.menegment.models;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
public class Vacancies implements Serializable {
    private Integer vacancies_id;

    private String name_vacancies;

    private String info;

    private Integer department_id;
    private Department department = new Department();

    private Double price ;

    @Builder
    public Vacancies(Integer vacancies_id, String name_vacancies, String info, Department department) {
        this.vacancies_id = vacancies_id;
        this.name_vacancies = name_vacancies;
        this.info = info;
        this.department = department;
    }

    public Vacancies(Integer vacancies_id, String name_vacancies, String info, Integer department_id, Department department) {
        this.vacancies_id = vacancies_id;
        this.name_vacancies = name_vacancies;
        this.info = info;
        this.department_id = department_id;
        this.department = department;
    }

    public Vacancies(Integer vacancies_id, String name_vacancies, String info, Integer department_id, Department department, Double price) {
        this.vacancies_id = vacancies_id;
        this.name_vacancies = name_vacancies;
        this.info = info;
        this.department_id = department_id;
        this.department = department;
        this.price = price;
    }

    public Vacancies() {

    }
}
