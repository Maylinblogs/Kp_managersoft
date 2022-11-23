package org.menegment.dao;

import org.menegment.dao.connection.JDBC;
import org.menegment.enums.Roles;
import org.menegment.interfeses.DAO;
import org.menegment.models.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VacanciesDao implements DAO<Vacancies> {

    private boolean isTrue;

    @Override
    public Vacancies findEntity(Integer id) {
        Vacancies vacancies = new Vacancies();
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("SELECT * FROM vacancies left join departments d on d.department_id = vacancies.id_department where vacancies_id =?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                vacancies.setVacancies_id(resultSet.getInt("vacancies_id"));
                vacancies.setDepartment_id(resultSet.getInt("id_department"));
                vacancies.setInfo(resultSet.getString("info"));
                vacancies.setName_vacancies(resultSet.getString("name_vacancies"));
                vacancies.setDepartment(new Department(resultSet.getInt("department_id"), resultSet.getString("name_department"),
                        resultSet.getString("address"), null));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBC.close();
        }
        return vacancies;
    }

    @Override
    public Vacancies findEntity(String id) {
        Vacancies vacancies = new Vacancies();
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("SELECT * FROM vacancies left join departments d on d.department_id = vacancies.id_department where vacancies.name_vacancies =?");
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                vacancies.setVacancies_id(resultSet.getInt("vacancies_id"));
                vacancies.setDepartment_id(resultSet.getInt("id_department"));
                vacancies.setInfo(resultSet.getString("info"));
                vacancies.setName_vacancies(resultSet.getString("name_vacancies"));
                vacancies.setDepartment(new Department(resultSet.getInt("department_id"), resultSet.getString("name_department"),
                        resultSet.getString("address"), null));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBC.close();
        }
        return vacancies;
    }

    @Override
    public boolean saveEntity(Vacancies entity) {
        isTrue = false;
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("INSERT INTO vacancies (name_vacancies,info,id_department)" +
                            " values (?,?,?) ");
            preparedStatement.setString(1, entity.getName_vacancies());
            preparedStatement.setString(2, entity.getInfo());
            preparedStatement.setInt(3, entity.getDepartment_id());
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
    public boolean updateEntity(Vacancies entity) {
        isTrue = false;
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("UPDATE vacancies SET name_vacancies =?,info =? where vacancies_id=?");
            preparedStatement.setString(1, entity.getName_vacancies());
            preparedStatement.setString(2, entity.getInfo());
            preparedStatement.setInt(3, entity.getVacancies_id());
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
                    .prepareStatement("DELETE FROM vacancies Where vacancies_id = ?");
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
    public List<Vacancies> findAllEntity() {
        List<Vacancies> vacancies = new ArrayList<>();
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("SELECT * from vacancies");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                vacancies.add(new Vacancies(resultSet.getInt("vacancies_id"),
                        resultSet.getString("name_vacancies"),
                        resultSet.getString("info"), resultSet.getInt("id_department"),
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

