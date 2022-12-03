package org.menegment.service;

import org.menegment.dao.ResumeDao;
import org.menegment.interfeses.SERVICE;
import org.menegment.models.Resume;
import org.menegment.models.Resume;

import java.util.ArrayList;
import java.util.List;

public class ResumeService implements SERVICE<Resume> {
    private boolean isTrue;

    private ResumeDao resumeDao = new ResumeDao();


    public Resume correctFindEntity(Resume user) {
        Resume user1 = null;
        try {
            user1 = resumeDao.findEntity(user.getResume_id());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user1;
    }

    public boolean correctSaveEntity(Resume user) {
        isTrue = false;
        try {
            isTrue = resumeDao.saveEntity(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTrue;
    }

    public ArrayList<Resume> correctAllEntity() {
        ArrayList<Resume> users = new ArrayList<>();
        try {
            users = (ArrayList<Resume>) resumeDao.findAllEntity();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean correctDeleteEntity(Resume user) {
        isTrue = false;
        try {
            isTrue = resumeDao.deleteEntityId(user.getResume_id());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTrue;
    }

    public boolean correctUpdateEntity(Resume user) {
        isTrue = false;
        try {
            isTrue = resumeDao.updateEntity(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTrue;
    }
}
