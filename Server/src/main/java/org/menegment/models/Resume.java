package org.menegment.models;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
public class Resume  implements Serializable {

    private Integer resume_id;
    private String specialization;
    private String experience;
    private String education;


    public Resume(Integer resume_id, String specialization, String experience, String education) {
        this.resume_id = resume_id;
        this.specialization = specialization;
        this.experience = experience;
        this.education = education;
    }

    public Resume() {

    }
}
