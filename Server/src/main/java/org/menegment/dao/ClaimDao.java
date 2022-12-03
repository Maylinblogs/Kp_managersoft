package org.menegment.dao;

import org.menegment.dao.connection.JDBC;
import org.menegment.interfeses.DAO;
import org.menegment.models.Claim;
import org.menegment.models.Claim;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClaimDao implements DAO<Claim> {
    @Override
    public Claim findEntity(Integer id) {
        Claim claim = new Claim();
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("SELECT * FROM claims where claim_id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                claim.setClaim_id(resultSet.getInt("claim_id"));
                claim.setName(resultSet.getString("name_claim"));
                claim.setInfo(resultSet.getString("info"));
                claim.setStatus_claim(resultSet.getInt("status_claim"));
                claim.setId_worker(resultSet.getInt("id_worker"));
                claim.setDate_claim(resultSet.getDate("date_claim"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBC.close();
        }
        return claim;
    }

    @Override
    public Claim findEntity(String id) {
        Claim claim = new Claim();
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("SELECT * FROM claims where name_claim = ?");
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                claim.setClaim_id(resultSet.getInt("claim_id"));
                claim.setName(resultSet.getString("name_claim"));
                claim.setInfo(resultSet.getString("info"));
                claim.setStatus_claim(resultSet.getInt("status_claim"));
                claim.setId_worker(resultSet.getInt("id_worker"));
                claim.setDate_claim(resultSet.getDate("date_claim"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBC.close();
        }
        return claim;
    }

    @Override
    public boolean saveEntity(Claim entity) {
        boolean isTrue = false;
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("INSERT INTO claims (id_worker,name_claim,status_claim,info,date_claim)" +
                            " values (?,?,?,?,?) ");
            preparedStatement.setInt(1, entity.getId_worker());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setInt(3, entity.getStatus_claim());
            preparedStatement.setString(4, entity.getInfo());
            preparedStatement.setDate(5,entity.getDate_claim());

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
    public boolean updateEntity(Claim entity) {
        boolean isTrue = false;
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("UPDATE claims SET status_claim =? , date_claim = ? where claim_id = ?");
            preparedStatement.setInt(1, entity.getStatus_claim());
            preparedStatement.setDate(2, entity.getDate_claim());
            preparedStatement.setInt(3, entity.getClaim_id());
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
        return false;
    }

    @Override
    public boolean deleteEntityId(String id) {
        boolean isTrue = false;
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("DELETE FROM claims Where name_claim = ?");
            preparedStatement.setString(1, id);
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
    public List<Claim> findAllEntity() {
        List<Claim> claims = new ArrayList<>();
        try {
            JDBC.connect();
            PreparedStatement preparedStatement = JDBC.connection
                    .prepareStatement("SELECT * from claims");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                claims.add(new Claim(resultSet.getInt("claim_id"),
                        resultSet.getString("name_claim"),
                        resultSet.getInt("id_worker"), resultSet.getInt("status_claim"),
                        resultSet.getString("info"),resultSet.getDate("date_claim")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBC.close();
        }
        return claims;
    }
}

