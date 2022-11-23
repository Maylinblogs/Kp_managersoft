package org.menegment.service;

import org.menegment.dao.PersonDao;
import org.menegment.interfeses.SERVICE;
import org.menegment.models.Person;

import java.util.ArrayList;

public class PersonService implements SERVICE<Person> {

    private PersonDao personDao = new PersonDao();
    private boolean isTrue;

    public Person correctFindEntityInt(Integer str) {
        Person person1 = null;
        try {
            person1 = personDao.findEntity(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return person1;
    }

    public boolean correctSaveEntity(Person person) {
        isTrue = false;
        try {
            isTrue = personDao.saveEntity(person);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTrue;
    }

    public ArrayList<Person> correctAllEntity() {
        ArrayList<Person> people = new ArrayList<>();
        try {
            people = (ArrayList<Person>) personDao.findAllEntity();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return people;
    }

    public boolean correctDeleteEntity(Person person) {
        isTrue = false;
        try {
            isTrue = personDao.deleteEntityId(person.getPerson_id());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTrue;
    }

    public boolean correctUpdateEntity(Person user) {
        isTrue = false;
        try {
            isTrue = personDao.updateEntity(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTrue;
    }
}
