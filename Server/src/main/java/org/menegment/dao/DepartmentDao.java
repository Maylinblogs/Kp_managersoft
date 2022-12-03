package org.menegment.dao;

import org.menegment.dao.connection.JDBC;
import org.menegment.interfeses.DAO;
import org.menegment.models.Department;
import org.menegment.models.Vacancies;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDao implements DAO<Department> {
    private boolean isTrue;

    @Override
    public Department findEntity(Integer id) {
        Department department = new Department();
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("SELECT * FROM departments as d left join vacancies v on d.department_id = v.id_department where d.department_id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                department.setDepartment_id(resultSet.getInt("department_id"));
                department.setName_department(resultSet.getString("name_department"));
                department.setAddress(resultSet.getString("address"));
                department.getVacanciesList().add(new Vacancies(resultSet.getInt("vacancies_id"),
                        resultSet.getString("name_vacancies"),
                        resultSet.getString("info"), null));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBC.close();
        }
        return department;
    }

    @Override
    public Department findEntity(String id) {
        Department department = new Department();
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("SELECT * FROM departments as d left join vacancies v on d.department_id = v.id_department where d.name_department = ?");
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                department.setDepartment_id(resultSet.getInt("department_id"));
                department.setName_department(resultSet.getString("name_department"));
                department.setAddress(resultSet.getString("address"));
                department.getVacanciesList().add(new Vacancies(resultSet.getInt("vacancies_id"),
                        resultSet.getString("name_vacancies"),
                        resultSet.getString("info"), null));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBC.close();
        }
        return department;
    }

    @Override
    public boolean saveEntity(Department entity) {
        isTrue = false;
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("INSERT INTO departments (name_department,address,id_user)" +
                            " values (?,?,?) ");
            preparedStatement.setString(1, entity.getName_department());
            preparedStatement.setString(2, entity.getAddress());
            preparedStatement.setInt(3, entity.getId_user());
            preparedStatement.executeUpdate();
            isTrue = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBC.close();
        }
        return isTrue;
    }

    @Override
    public boolean updateEntity(Department entity) {
        isTrue = false;
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("UPDATE departments SET name_department =?,address =?,id_user = ? where department_id = ?");
            preparedStatement.setString(1, entity.getName_department());
            preparedStatement.setString(2, entity.getAddress());
            preparedStatement.setInt(3, entity.getId_user());
            preparedStatement.setInt(4, entity.getDepartment_id());
            preparedStatement.executeUpdate();
            isTrue = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBC.close();
        }
        return isTrue;
    }

    @Override
    public boolean deleteEntityId(Integer id) {
        isTrue = false;
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("DELETE FROM departments Where department_id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            isTrue = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBC.close();
        }
        return isTrue;
    }

    @Override
    public boolean deleteEntityId(String id) {

         return false;
    }

    @Override
    public List<Department> findAllEntity() {
        List<Department> vacancies = new ArrayList<>();
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("SELECT * from departments");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                vacancies.add(new Department(resultSet.getInt("department_id"),
                        resultSet.getString("name_department"),
                        resultSet.getString("address"),
                        null));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBC.close();
        }
        return vacancies;
    }
}
