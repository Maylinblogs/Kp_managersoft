package org.menegment.dao;

import org.menegment.dao.connection.JDBC;
import org.menegment.interfeses.DAO;
import org.menegment.models.Person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersonDao implements DAO<Person> {
    private boolean isTrue;


    @Override
    public Person findEntity(Integer id) {
        Person person = new Person();
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("SELECT * FROM persons where person_id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                person.setPerson_id(resultSet.getInt("person_id"));
                person.setName_person(resultSet.getString("name_person"));
                person.setSur_name(resultSet.getString("surname"));
                person.setLast_name(resultSet.getString("lastname"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBC.close();
        }
        return person;
    }

    @Override
    public Person findEntity(String id) {
        Person person = new Person();
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("SELECT * FROM persons where surname=?");
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                person.setPerson_id(resultSet.getInt("person_id"));
                person.setName_person(resultSet.getString("name_person"));
                person.setSur_name(resultSet.getString("surname"));
                person.setLast_name(resultSet.getString("lastname"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBC.close();
        }
        return person;
    }

    @Override
    public boolean saveEntity(Person entity) {
        isTrue = false;
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("INSERT INTO persons (name_person,surname,lastname)" +
                            " values (?,?,?) ");
            preparedStatement.setString(1, entity.getName_person());
            preparedStatement.setString(2, entity.getSur_name());
            preparedStatement.setString(3, entity.getLast_name());
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
    public boolean updateEntity(Person entity) {
        isTrue = false;
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("UPDATE persons SET name_person =?,surname =?,lastname = ? where person_id =?");
            preparedStatement.setString(1, entity.getName_person());
            preparedStatement.setString(2, entity.getSur_name());
            preparedStatement.setString(3, entity.getLast_name());
            preparedStatement.setInt(4, entity.getPerson_id());
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
                    .prepareStatement("DELETE FROM users Where user_id = ?");
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
    public List<Person> findAllEntity() {
        return null;
    }
}
