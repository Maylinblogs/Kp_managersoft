package org.menegment.models;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class Worker implements Serializable {

    private Integer worker_id;
    private String name_worker;
    private String sur_name;
    private String last_name;

    private  Integer user_id;
    private String passport;
    private Resume resume;

    private Set<Claim> claims;

    public Worker(Integer worker_id, String name_worker, String sur_name, String last_name, String passport, Resume resume) {
        this.worker_id = worker_id;
        this.name_worker = name_worker;
        this.sur_name = sur_name;
        this.last_name = last_name;
        this.passport = passport;
        this.resume = resume;
    }

    public Worker(Integer worker_id, String name_worker, String sur_name, String last_name, Integer user_id, String passport, Resume resume, Set<Claim> claims) {
        this.worker_id = worker_id;
        this.name_worker = name_worker;
        this.sur_name = sur_name;
        this.last_name = last_name;
        this.user_id = user_id;
        this.passport = passport;
        this.resume = resume;
        this.claims = claims;
    }

    public Worker() {

    }


}
