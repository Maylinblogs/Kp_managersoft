package org.menegment.dao;

import org.menegment.dao.connection.JDBC;
import org.menegment.interfeses.DAO;
import org.menegment.models.Resume;
import org.menegment.models.Worker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkerDao implements DAO<Worker> {
    private boolean isTrue;


    public Worker findEntityIDWORKER(Integer id) {
        Worker worker = new Worker();
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("SELECT *FROM workers as w  left join resumes r on w.worker_id = r.resume_id where worker_id =?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                worker.setWorker_id(resultSet.getInt("worker_id"));
                worker.setName_worker(resultSet.getString("name_worker"));
                worker.setSur_name(resultSet.getString("sur_name"));
                worker.setLast_name(resultSet.getString("last_name"));
                worker.setPassport(resultSet.getString("passport"));
                worker.setResume(new Resume(resultSet.getInt("resume_id"), resultSet.getString("specialization"),
                        resultSet.getString("experience"), resultSet.getString("education")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBC.close();
        }
        return worker;
    }


    @Override
    public Worker findEntity(Integer id) {
        Worker worker = new Worker();
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("SELECT *FROM workers as w  left join resumes r on w.worker_id = r.resume_id where id_user_worker =?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                worker.setWorker_id(resultSet.getInt("worker_id"));
                worker.setName_worker(resultSet.getString("name_worker"));
                worker.setSur_name(resultSet.getString("sur_name"));
                worker.setLast_name(resultSet.getString("last_name"));
                worker.setPassport(resultSet.getString("passport"));
                worker.setResume(new Resume(resultSet.getInt("resume_id"), resultSet.getString("specialization"),
                        resultSet.getString("experience"), resultSet.getString("education")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBC.close();
        }
        return worker;
    }

    @Override
    public Worker findEntity(String id) {
        Worker worker = new Worker();
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("SELECT *FROM workers as w  left join resumes r on w.worker_id = r.resume_id where passport =?");
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                worker.setWorker_id(resultSet.getInt("worker_id"));
                worker.setName_worker(resultSet.getString("name_worker"));
                worker.setSur_name(resultSet.getString("sur_name"));
                worker.setLast_name(resultSet.getString("last_name"));
                worker.setPassport(resultSet.getString("passport"));
                worker.setResume(new Resume(resultSet.getInt("resume_id"), resultSet.getString("specialization"),
                        resultSet.getString("experience"), resultSet.getString("education")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBC.close();
        }
        return worker;
    }

    @Override
    public boolean saveEntity(Worker entity) {
        isTrue = false;
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("INSERT INTO workers (name_worker,sur_name,last_name,passport,id_user_worker)" +
                            " values (?,?,?,?,?)");
            preparedStatement.setString(1, entity.getName_worker());
            preparedStatement.setString(2, entity.getSur_name());
            preparedStatement.setString(3, entity.getLast_name());
            preparedStatement.setString(4, entity.getPassport());
            preparedStatement.setInt(5, entity.getUser_id());
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
    public boolean updateEntity(Worker entity) {
        isTrue = false;
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("UPDATE workers SET name_worker =?,sur_name =?,last_name = ?,passport =? where worker_id =?");
            preparedStatement.setString(1, entity.getName_worker());
            preparedStatement.setString(2, entity.getSur_name());
            preparedStatement.setString(3, entity.getLast_name());
            preparedStatement.setString(4, entity.getPassport());
            preparedStatement.setInt(5, entity.getWorker_id());
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
                    .prepareStatement("DELETE FROM workers Where worker_id = ?");
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
    public List<Worker> findAllEntity() {

        List<Worker> workers = new ArrayList<>();
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("SELECT * from workers");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                workers.add(new Worker(resultSet.getInt("worker_id"),
                        resultSet.getString("name_worker"),
                        resultSet.getString("sur_name"), resultSet.getString("last_name"), resultSet.getInt("id_user_worker"),
                        resultSet.getString("passport"), null, null));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBC.close();
        }
        return workers;
    }
}

