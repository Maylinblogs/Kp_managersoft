package org.menegment.service;

import org.menegment.dao.WorkerDao;
import org.menegment.models.Worker;
import org.menegment.models.Worker;

import java.util.ArrayList;
import java.util.List;

public class WorkerService {

    private WorkerDao workerDao = new WorkerDao();
    private boolean isTrue;

    public Worker correctFindEntityString(String str) {
        Worker worker = null;
        try {
            worker = workerDao.findEntity(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return worker;
    }

    public boolean isEntityToOne(Worker user) {
        isTrue = false;
        List<Worker> users = workerDao.findAllEntity();
        if (users != null) {
            for (Worker u : users) {
                if (u.getPassport().equals(user.getPassport())) {
                    isTrue = true;
                    break;
                }
            }
        }
        return isTrue;
    }

    public Worker correctFindEntityInt(Integer str) {
        Worker worker = null;
        try {
            worker = workerDao.findEntity(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return worker;
    }

    public Worker correctFindEntityIntID(Integer str) {
        Worker worker = null;
        try {
            worker = workerDao.findEntityIDWORKER(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return worker;
    }

    public boolean correctSaveEntity(Worker worker) {
        isTrue = false;
        try {
            isTrue = workerDao.saveEntity(worker);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTrue;
    }

    public ArrayList<Worker> correctAllEntity() {
        ArrayList<Worker> workers = new ArrayList<>();
        try {
            workers = (ArrayList<Worker>) workerDao.findAllEntity();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workers;
    }

    public boolean correctDeleteEntity(Worker worker) {
        isTrue = false;
        try {
            isTrue = workerDao.deleteEntityId(worker.getWorker_id());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTrue;
    }

    public boolean correctUpdateEntity(Worker worker) {
        isTrue = false;
        try {
            isTrue = workerDao.updateEntity(worker);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTrue;
    }
}
