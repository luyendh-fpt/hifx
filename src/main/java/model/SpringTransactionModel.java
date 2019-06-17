package model;

import entity.Account;
import entity.SpringTransaction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SpringTransactionModel {

    public boolean save(SpringTransaction obj) {
        try {
            String sqlCommand = "insert into transactions (id, senderId, receiverId, amount, message, createdAtMLS, updatedAtMLS, status) values (?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = ConnectionHelper.getConnection().prepareStatement(sqlCommand);
            preparedStatement.setLong(1, obj.getId());
            preparedStatement.setString(2, obj.getSenderId());
            preparedStatement.setString(3, obj.getReceiverId());
            preparedStatement.setDouble(4, obj.getAmount());
            preparedStatement.setString(5, obj.getMessage());
            preparedStatement.setLong(6, obj.getCreatedAtMLS());
            preparedStatement.setLong(7, obj.getUpdatedAtMLS());
            preparedStatement.setInt(8, obj.getStatus());
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

}
