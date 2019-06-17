package model;

import entity.Account;
import entity.SpringTransaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

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
                double rsBalance = resultSet.getDouble(3);
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
            preparedStatement.setDouble(3, account.getBalance());
            preparedStatement.setInt(4, account.getStatus());
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // đoạn code này chỉ dùng để demo transaction.
    public boolean withdraw(Account account, double amount) {
        Connection connection = null;
        try {
            connection = ConnectionHelper.getConnection();
            connection.setAutoCommit(false);

            String checkAccount = "select * from accounts where account = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(checkAccount);
            preparedStatement.setString(1, account.getAccount());
            ResultSet resultSet = preparedStatement.executeQuery();
            Account existAccount = null;
            if (resultSet.next()) {
                String rsAccount = resultSet.getString(1);
                String rsPassword = resultSet.getString(2);
                double rsBalance = resultSet.getDouble(3);
                int rsStatus = resultSet.getInt(4);
                existAccount = new Account();
                existAccount.setAccount(rsAccount);
                existAccount.setPassword(rsPassword);
                existAccount.setBalance(rsBalance);
                existAccount.setStatus(rsStatus);
            }

            if (existAccount == null) {
                return false;
            }

            if (existAccount.getBalance() < amount) {
                return false;
            }

            existAccount.setBalance(existAccount.getBalance() - amount);
            String sqlCommand = "update accounts set balance = ? where account = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sqlCommand);
            preparedStatement1.setDouble(1, existAccount.getBalance());
            preparedStatement1.setString(2, existAccount.getAccount());
            preparedStatement1.execute();

            SpringTransaction springTransaction = new SpringTransaction();
            springTransaction.setId(Calendar.getInstance().getTimeInMillis()); // tạm thôi.
            springTransaction.setSenderId(account.getAccount());
            springTransaction.setReceiverId(account.getAccount());
            springTransaction.setAmount(amount);
            springTransaction.setMessage("With draw " + amount);
            springTransaction.setCreatedAtMLS(Calendar.getInstance().getTimeInMillis());
            springTransaction.setUpdatedAtMLS(Calendar.getInstance().getTimeInMillis());
            springTransaction.setStatus(SpringTransaction.Status.COMPLETED);

            String txtCmd = "insert into transactions (id, senderId, receiverId, amount, message, createdAtMLS, updatedAtMLS, status) values (?,?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(txtCmd);
            ps.setLong(1, springTransaction.getId());
            ps.setString(2, springTransaction.getSenderId());
            ps.setString(3, springTransaction.getReceiverId());
            ps.setDouble(4, springTransaction.getAmount());
            ps.setString(5, springTransaction.getMessage());
            ps.setLong(6, springTransaction.getCreatedAtMLS());
            ps.setLong(7, springTransaction.getUpdatedAtMLS());
            ps.setInt(8, springTransaction.getStatus());
            ps.execute();
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.rollback();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void main(String[] args) {
        AccountModel model = new AccountModel();
        Account account = model.findByAccountAndPassword("xuanhung2401", "123");
        System.out.println(model.withdraw(account, 20000));
    }

}
