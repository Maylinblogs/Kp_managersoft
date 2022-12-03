package org.menegment.dao;

import org.menegment.dao.connection.JDBC;
import org.menegment.interfeses.DAO;
import org.menegment.models.Resume;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ResumeDao implements DAO<Resume> {
    private boolean isTrue;

    @Override
    public Resume findEntity(Integer id) {
        Resume resume = new Resume();
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("SELECT * FROM resumes where resume_id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resume.setResume_id(resultSet.getInt("resume_id"));
                resume.setExperience(resultSet.getString("experience"));
                resume.setSpecialization(resultSet.getString("specialization"));
                resume.setEducation(resultSet.getString("education"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBC.close();
        }
        return resume;
    }

    @Override
    public Resume findEntity(String id) {
        Resume resume = new Resume();
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("SELECT * FROM resumes where specialization = ?");
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resume.setResume_id(resultSet.getInt("resume_id"));
                resume.setExperience(resultSet.getString("experience"));
                resume.setSpecialization(resultSet.getString("specialization"));
                resume.setEducation(resultSet.getString("education"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBC.close();
        }
        return resume;
    }

    @Override
    public boolean saveEntity(Resume entity) {
        isTrue = false;
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("INSERT INTO resumes (resume_id ,specialization,experience,education)" +
                            " values (?,?,?,?) ");
            preparedStatement.setInt(1, entity.getResume_id());
            preparedStatement.setString(3, entity.getSpecialization());
            preparedStatement.setString(2, entity.getExperience());
            preparedStatement.setString(4, entity.getEducation());
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
    public boolean updateEntity(Resume entity) {
        isTrue = false;
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("UPDATE resumes SET specialization =?,experience =?,education = ? where resume_id = ?");
            preparedStatement.setString(1, entity.getSpecialization());
            preparedStatement.setString(2, entity.getExperience());
            preparedStatement.setString(3, entity.getEducation());
            preparedStatement.setInt(4, entity.getResume_id());
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
    public List<Resume> findAllEntity() {
        return null;
    }
}
