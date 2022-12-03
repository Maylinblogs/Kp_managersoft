package org.menegment.models;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class Claim implements Serializable {

    private Integer claim_id;

    private String name;

    private Integer id_worker;

    private Integer status_claim;

    private Date date_claim;

    private String info;

    public Claim() {

    }

    @Builder
    public Claim(Integer claim_id, String name, Integer id_worker, Integer status_claim, String info, Date date) {
        this.claim_id = claim_id;
        this.name = name;
        this.id_worker = id_worker;
        this.status_claim = status_claim;
        this.info = info;
        this.date_claim = date;
    }

    //  @Override
    //  public String toString() {
    //      return ", info='" + info;
    //  }
}
