package org.menegment.service;

import org.menegment.dao.DepartmentDao;
import org.menegment.models.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {


    private boolean isTrue;

    private DepartmentDao departmentDao = new DepartmentDao();




    public Department correctFindEntityString(Department user) {
        Department user1 = null;
        try {
            user1 = departmentDao.findEntity(user.getName_department());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user1;
    }

    public boolean correctSaveEntity(Department user) {
        isTrue = false;
        try {
            isTrue = departmentDao.saveEntity(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTrue;
    }

    public ArrayList<Department> correctAllEntity() {
        ArrayList<Department> users = new ArrayList<>();
        try {
            users = (ArrayList<Department>) departmentDao.findAllEntity();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean correctDeleteEntity(Department user) {
        isTrue = false;
        try {
            isTrue = departmentDao.deleteEntityId(user.getDepartment_id());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTrue;
    }

    public boolean correctUpdateEntity(Department user) {
        isTrue = false;
        try {
            isTrue = departmentDao.updateEntity(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTrue;
    }

}
