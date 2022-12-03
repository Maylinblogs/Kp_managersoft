package org.menegment.service;

import org.menegment.dao.ClaimDao;
import org.menegment.models.Claim;

import java.util.ArrayList;
import java.util.List;

public class ClaimService {

    private boolean isTrue;

    private ClaimDao claimDao = new ClaimDao();

    private int i = 0;


    public boolean isEntityToOne(Claim user) {
        isTrue = false;
        List<Claim> users = claimDao.findAllEntity();
        if (users != null) {
            for (Claim u : users) {
                if (u.getName().equals(user.getName())) {
                    isTrue = true;
                    break;
                }
            }
        }
        return isTrue;
    }

    public Claim correctFindEntityInt(Claim vacancies) {
        Claim user1 = null;
        try {
            user1 = claimDao.findEntity(vacancies.getClaim_id());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user1;
    }

    public boolean correctSaveEntity(Claim user) {
        isTrue = false;
        try {
            isTrue = claimDao.saveEntity(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTrue;
    }

    public ArrayList<Claim> correctAllEntity() {
        ArrayList<Claim> vacanciesArrayList = new ArrayList<>();
        try {
            vacanciesArrayList = (ArrayList<Claim>) claimDao.findAllEntity();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vacanciesArrayList;
    }

    public boolean correctDeleteEntity(Claim vacancies) {
        isTrue = false;
        try {
            isTrue = claimDao.deleteEntityId(vacancies.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTrue;
    }

    public boolean correctUpdateEntity(Claim vacancies) {
        isTrue = false;
        try {
            isTrue = claimDao.updateEntity(vacancies);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTrue;
    }



    public Claim poisk(Claim vacancies) {
        ArrayList<Claim> vacanciesArrayList;
        vacanciesArrayList = correctAllEntity();
        for (int i = 0; i < vacanciesArrayList.size(); i++) {
            if (vacanciesArrayList.get(i).getName().equals(vacancies.getName())) {
                vacancies.setClaim_id(i);
                return vacancies;
            }
        }
        return null;
    }

}
