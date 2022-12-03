package org.menegment.service;

import org.menegment.dao.VacanciesDao;
import org.menegment.models.Vacancies;

import java.util.ArrayList;

public class VacanciesService {

    private boolean isTrue;

    private VacanciesDao vacanciesDao = new VacanciesDao();

    private int i = 0;

    public Vacancies correctFindEntityInt(Vacancies vacancies) {
        Vacancies user1 = null;
        try {
            user1 = vacanciesDao.findEntity(vacancies.getVacancies_id());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user1;
    }

    public boolean correctSaveEntity(Vacancies user) {
        isTrue = false;
        try {
            isTrue = vacanciesDao.saveEntity(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTrue;
    }

    public ArrayList<Vacancies> correctAllEntity() {
        ArrayList<Vacancies> vacanciesArrayList = new ArrayList<>();
        try {
            vacanciesArrayList = (ArrayList<Vacancies>) vacanciesDao.findAllEntity();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vacanciesArrayList;
    }

    public boolean correctDeleteEntity(Vacancies vacancies) {
        isTrue = false;
        try {
            isTrue = vacanciesDao.deleteEntityId(vacancies.getVacancies_id());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTrue;
    }

    public boolean correctUpdateEntity(Vacancies vacancies) {
        isTrue = false;
        try {
            isTrue = vacanciesDao.updateEntity(vacancies);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTrue;
    }


    public Vacancies poisk(Vacancies vacancies) {
        ArrayList<Vacancies> vacanciesArrayList;
        vacanciesArrayList = correctAllEntity();
        for (int i = 0; i < vacanciesArrayList.size(); i++) {
            if (vacanciesArrayList.get(i).getName_vacancies().equals(vacancies.getName_vacancies())) {
                vacancies.setVacancies_id(i);
                return vacancies;
            }
        }
        return null;
    }

    public Double f1(Double c1, Double c2, Double c3) {
        return (c1 / c2) * c3;
    }

    public Double f2(Double c1, Double c2, Double c3) {
        return c1 * c2 * c3;
    }
}
