package org.menegment.dao;

import org.menegment.dao.connection.JDBC;
import org.menegment.enums.Roles;
import org.menegment.interfeses.DAO;
import org.menegment.models.Department;
import org.menegment.models.Person;
import org.menegment.models.User;
import org.menegment.models.Worker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements DAO<User> {

    private boolean isTrue;

    @Override
    public User findEntity(Integer id) {
        User user = new User();
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("SELECT *FROM users as u left join persons p on u.user_id = p.person_id left join  (SELECT * FROM workers as w inner join departments d on w.id_user_worker = d.id_user ) as wd on u.user_id= wd.id_user_worker where u.user_id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Integer inte = 10;
            while (resultSet.next()) {
                inte = resultSet.getInt("role_user");
                if (inte == 0) {
                    user.setUser_id(resultSet.getInt("user_id"));
                    user.setLogin(resultSet.getString("login"));
                    user.setPassword(resultSet.getString("password_"));
                    user.setRole_user(Roles.ADMIN);
                    user.setPerson(new Person(resultSet.getInt("person_id")
                            , resultSet.getString("name_person"), resultSet.getString("surname"),
                            resultSet.getString("lastname")));
                    user.getDepartmentList().add(new Department(resultSet.getInt("department_id"), resultSet.getString("name_department"), resultSet.getString("address"), null));
                    user.getWorkerList().add(new Worker(resultSet.getInt("worker_id"), resultSet.getString("name_worker"), resultSet.getString("sur_name"), resultSet.getString("last_name"), resultSet.getString("passport"), null));
                } else {
                    if (inte == 1) {
                        user.setUser_id(resultSet.getInt("user_id"));
                        user.setLogin(resultSet.getString("login"));
                        user.setPassword(resultSet.getString("password_"));
                        user.setRole_user(Roles.USER);
                        user.setPerson(new Person(resultSet.getInt("person_id")
                                , resultSet.getString("name_person"), resultSet.getString("surname"),
                                resultSet.getString("lastname")));
                        user.getDepartmentList().add(new Department(resultSet.getInt("department_id"), resultSet.getString("name_department"), resultSet.getString("address"), null));
                        user.getWorkerList().add(new Worker(resultSet.getInt("worker_id"), resultSet.getString("name_worker"), resultSet.getString("sur_name"), resultSet.getString("last_name"), resultSet.getString("passport"), null));
                    } else {
                        user.setUser_id(resultSet.getInt("user_id"));
                        user.setLogin(resultSet.getString("login"));
                        user.setPassword(resultSet.getString("password_"));
                        user.setRole_user(Roles.WORKER);
                        user.setPerson(new Person(resultSet.getInt("person_id")
                                , resultSet.getString("name_person"), resultSet.getString("surname"),
                                resultSet.getString("lastname")));
                        user.getDepartmentList().add(new Department(resultSet.getInt("department_id"), resultSet.getString("name_department"), resultSet.getString("address"), null));
                        user.getWorkerList().add(new Worker(resultSet.getInt("worker_id"), resultSet.getString("name_worker"), resultSet.getString("sur_name"), resultSet.getString("last_name"), resultSet.getString("passport"), null));
                    }

                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBC.close();
        }
        return user;
    }

    @Override
    public User findEntity(String id) {
        User user = new User();
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("SELECT *FROM users as u left join persons p on u.user_id = p.person_id left join  (SELECT * FROM workers as w inner join departments d on w.id_user_worker = d.id_user ) as wd on u.user_id= wd.id_user_worker where u.login = ?");
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Integer inte = 10;
            while (resultSet.next()) {
                inte = resultSet.getInt("role_user");
                if (inte == 0) {
                    user.setUser_id(resultSet.getInt("user_id"));
                    user.setLogin(resultSet.getString("login"));
                    user.setPassword(resultSet.getString("password_"));
                    user.setRole_user(Roles.ADMIN);
                    user.setPerson(new Person(resultSet.getInt("person_id")
                            , resultSet.getString("name_person"), resultSet.getString("surname"),
                            resultSet.getString("lastname")));
                    user.getDepartmentList().add(new Department(resultSet.getInt("department_id"), resultSet.getString("name_department"), resultSet.getString("address"), null));
                    user.getWorkerList().add(new Worker(resultSet.getInt("worker_id"), resultSet.getString("name_worker"), resultSet.getString("sur_name"), resultSet.getString("last_name"), resultSet.getString("passport"), null));
                } else {
                    if (inte == 1) {
                        user.setUser_id(resultSet.getInt("user_id"));
                        user.setLogin(resultSet.getString("login"));
                        user.setPassword(resultSet.getString("password_"));
                        user.setRole_user(Roles.USER);
                        user.setPerson(new Person(resultSet.getInt("person_id")
                                , resultSet.getString("name_person"), resultSet.getString("surname"),
                                resultSet.getString("lastname")));
                        user.getDepartmentList().add(new Department(resultSet.getInt("department_id"), resultSet.getString("name_department"), resultSet.getString("address"), null));
                        user.getWorkerList().add(new Worker(resultSet.getInt("worker_id"), resultSet.getString("name_worker"), resultSet.getString("sur_name"), resultSet.getString("last_name"), resultSet.getString("passport"), null));
                    } else {
                        user.setUser_id(resultSet.getInt("user_id"));
                        user.setLogin(resultSet.getString("login"));
                        user.setPassword(resultSet.getString("password_"));
                        user.setRole_user(Roles.WORKER);
                        user.setPerson(new Person(resultSet.getInt("person_id")
                                , resultSet.getString("name_person"), resultSet.getString("surname"),
                                resultSet.getString("lastname")));
                        user.getDepartmentList().add(new Department(resultSet.getInt("department_id"), resultSet.getString("name_department"), resultSet.getString("address"), null));
                        user.getWorkerList().add(new Worker(resultSet.getInt("worker_id"), resultSet.getString("name_worker"), resultSet.getString("sur_name"), resultSet.getString("last_name"), resultSet.getString("passport"), null));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBC.close();
        }
        return user;
    }

    @Override
    public boolean saveEntity(User entity) {
        isTrue = false;
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("INSERT INTO users (login,password_,role_user)" +
                            " values (?,?,?) ");
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getPassword());
            switch (entity.getRole_user()) {
                case USER: {
                    preparedStatement.setInt(3, 1);
                    break;
                }
                case ADMIN: {
                    preparedStatement.setInt(3, 0);
                    break;
                }
                case WORKER: {
                    preparedStatement.setInt(3, 2);
                    break;
                }

            }
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
    public boolean updateEntity(User entity) {
        isTrue = false;
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("UPDATE users SET login =?,password_ =?,role_user = ? where user_id = ?");
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getPassword());
            switch (entity.getRole_user()) {
                case USER: {
                    preparedStatement.setInt(3, 1);
                    break;
                }
                case ADMIN: {
                    preparedStatement.setInt(3, 0);
                    break;
                }
                case WORKER: {
                    preparedStatement.setInt(3, 2);
                    break;
                }

            }

            preparedStatement.setInt(4, entity.getUser_id());
            System.out.println(entity);
            preparedStatement.executeUpdate();
            isTrue = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBC.close();
        }
        return isTrue;
    }


    public boolean updateEntityString(User entity) {
        isTrue = false;
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("UPDATE users SET login =?,password_ =?,role_user = ? where login = ?");
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setInt(3, entity.getRole_user().ordinal());
            preparedStatement.setString(4, entity.getLogin());
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
    public List<User> findAllEntity() {

        List<User> users = new ArrayList<>();
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("SELECT * from users");
            ResultSet resultSet = preparedStatement.executeQuery();
            Integer inte = 10;
            while (resultSet.next()) {
                inte = resultSet.getInt("role_user");
                if (inte == 0) {
                    users.add(new User(resultSet.getInt("user_id"),
                            resultSet.getString("login"),
                            resultSet.getString("password_"), Roles.ADMIN, null, null, null));
                } else {
                    if (inte == 1) {
                        users.add(new User(resultSet.getInt("user_id"),
                                resultSet.getString("login"),
                                resultSet.getString("password_"), Roles.USER, null, null, null));
                    }
                    users.add(new User(resultSet.getInt("user_id"),
                            resultSet.getString("login"),
                            resultSet.getString("password_"), Roles.WORKER, null, null, null));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBC.close();
        }
        return users;
    }
}
