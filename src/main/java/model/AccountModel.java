package model;

import entity.Account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountModel {

    // Tìm theo account và password. Ps: không bao giờ được viết như thế này khi làm thật.
    public Account findByAccountAndPassword(String account, String password) {
        try {
            String sqlCommand = "select * from accounts where account = ? and password = ?";
            PreparedStatement preparedStatement = ConnectionHelper.getConnection().prepareStatement(sqlCommand);
            preparedStatement.setString(1, account);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String rsAccount = resultSet.getString(1);
                String rsPassword = resultSet.getString(2);
                int rsBalance = resultSet.getInt(3);
                int rsStatus = resultSet.getInt(4);
                Account existAccount = new Account();
                existAccount.setAccount(rsAccount);
                existAccount.setPassword(rsPassword);
                existAccount.setBalance(rsBalance);
                existAccount.setStatus(rsStatus);
                return existAccount;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean save(Account account) {
        try {
            String sqlCommand = "insert into accounts (account, password, balance, status) values (?,?,?,?)";
            PreparedStatement preparedStatement = ConnectionHelper.getConnection().prepareStatement(sqlCommand);
            preparedStatement.setString(1, account.getAccount());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.setInt(3, account.getBalance());
            preparedStatement.setInt(4, account.getStatus());
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        AccountModel model = new AccountModel();
        Account account = new Account();
        account.setAccount("xuanhung1234");
        account.setPassword("123");
        account.setBalance(20000);
        account.setStatus(1);
        model.save(account);
    }

}
