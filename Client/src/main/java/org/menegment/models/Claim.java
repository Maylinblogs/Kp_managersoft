package org.menegment.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class Claim implements Serializable {

    private Integer claim_id;

    private String name;

    private Integer id_worker;

    private Integer status_claim;


    private String info;

    public Claim() {

    }

    public Claim(Integer claim_id, String name, Integer id_worker, Integer status_claim, String info) {
        this.claim_id = claim_id;
        this.name = name;
        this.id_worker = id_worker;
        this.status_claim = status_claim;
        this.info = info;
    }

   @Override
   public String toString() {
       return ", info='" + info;
   }
}
