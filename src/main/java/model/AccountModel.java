package model;

import entity.Account;
import entity.SpringTransaction;
import error.SpringHeroException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public class AccountModel {

    private String withdrawMessage = "Withdraw at ATM with amount %.2f";
    private String depositMessage = "Deposit at Bank with amount %.2f";
    private String transferMessage = "Transfer from account number '%s' to account number '%s' with amount: %.2f.";

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
    public boolean withdraw(String accountNumber, double amount) {
        Connection connection = null;
        try {
            connection = ConnectionHelper.getConnection();
            connection.setAutoCommit(false);
            //1. Lấy thông tin tài khoản mới nhất ra, kiểm tra số dư.
            //1.1. Lấy thông tin tài khoản mới nhất từ trong database ra.
            String checkAccountCmd = "select * from accounts where account = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(checkAccountCmd);
            preparedStatement.setString(1, accountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            Account account = null;
            if (resultSet.next()) {
                String rsAccount = resultSet.getString("account");
                double rsBalance = resultSet.getDouble(3);
                int rsStatus = resultSet.getInt(4); // thậm chí check trạng thái tài khoản.
                account = new Account();
                account.setAccount(rsAccount);
                account.setBalance(rsBalance);
                account.setStatus(rsStatus);
            }
            //1.2. Kiểm tra thông tin tài khoản cũng như số dư.
            if (account == null || account.isNotActive()) {
                throw new SpringHeroException("Tài khoản không tồn tại hoặc đang ở trạng thái không hoạt động!");
            }

            if (account.getBalance() < amount) {
                throw new SpringHeroException("Tài khoản không đủ tiền. Vui lòng về xin bố mẹ hoặc vợ...");
            }

            //2. Trừ tiền tài khoản.
            double updateBalance = account.getBalance() - amount;
            String updateAccountCmd = "update accounts set balance = ? where account = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(updateAccountCmd);
            preparedStatement1.setDouble(1, updateBalance);
            preparedStatement1.setString(2, accountNumber);
            preparedStatement1.execute();

            //3. Lưu thông tin transaction.
            //3.1. Tạo ra một đối tượng transaction.
            SpringTransaction springTransaction = new SpringTransaction();
            springTransaction.setSenderId(accountNumber);
            springTransaction.setReceiverId(accountNumber);
            springTransaction.setType(SpringTransaction.Type.WITHDRAW);
            springTransaction.setAmount(amount);
            springTransaction.setMessage(String.format(withdrawMessage, amount));
            springTransaction.setStatus(SpringTransaction.Status.COMPLETED);
            //3.2. Lưu vào database.
            String createTransactionCmd = "insert into transactions (id, senderId, receiverId, amount, message, createdAtMLS, updatedAtMLS, status, type) values (?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(createTransactionCmd);
            ps.setLong(1, springTransaction.getId());
            ps.setString(2, springTransaction.getSenderId());
            ps.setString(3, springTransaction.getReceiverId());
            ps.setDouble(4, springTransaction.getAmount());
            ps.setString(5, springTransaction.getMessage());
            ps.setLong(6, springTransaction.getCreatedAtMLS());
            ps.setLong(7, springTransaction.getUpdatedAtMLS());
            ps.setInt(8, springTransaction.getStatus());
            ps.setInt(9, springTransaction.getType());
            ps.execute();

            //4.1 Thực hiện commit transaction trong trường hợp không xảy ra bất kỳ lỗi gì.
            connection.commit();
            //4.2 Set auto commit về mặc định.
            connection.setAutoCommit(true);
            return true;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
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

    public boolean deposit(String accountNumber, double amount) {
        Connection connection = null;
        try {
            connection = ConnectionHelper.getConnection();
            connection.setAutoCommit(false);
            //1. Lấy thông tin tài khoản mới nhất ra, kiểm tra số dư.
            //1.1. Lấy thông tin tài khoản mới nhất từ trong database ra.
            String checkAccountCmd = "select * from accounts where account = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(checkAccountCmd);
            preparedStatement.setString(1, accountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            Account account = null;
            if (resultSet.next()) {
                String rsAccount = resultSet.getString("account");
                double rsBalance = resultSet.getDouble(3);
                int rsStatus = resultSet.getInt(4); // thậm chí check trạng thái tài khoản.
                account = new Account();
                account.setAccount(rsAccount);
                account.setBalance(rsBalance);
                account.setStatus(rsStatus);
            }
            //1.2. Kiểm tra thông tin tài khoản.
            if (account == null || account.isNotActive()) {
                throw new SpringHeroException("Tài khoản không tồn tại hoặc đang ở trạng thái không hoạt động!");
            }

            //2. Cộng tiền tài khoản.
            double updateBalance = account.getBalance() + amount;
            String updateAccountCmd = "update accounts set balance = ? where account = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(updateAccountCmd);
            preparedStatement1.setDouble(1, updateBalance);
            preparedStatement1.setString(2, accountNumber);
            preparedStatement1.execute();

            //3. Lưu thông tin transaction.
            //3.1. Tạo ra một đối tượng transaction.
            SpringTransaction springTransaction = new SpringTransaction();
            springTransaction.setSenderId(accountNumber);
            springTransaction.setReceiverId(accountNumber);
            springTransaction.setType(SpringTransaction.Type.DEPOSIT);
            springTransaction.setAmount(amount);
            springTransaction.setMessage(String.format(depositMessage, amount));
            springTransaction.setStatus(SpringTransaction.Status.COMPLETED);
            //3.2. Lưu vào database.
            String createTransactionCmd = "insert into transactions (id, senderId, receiverId, amount, message, createdAtMLS, updatedAtMLS, status, type) values (?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(createTransactionCmd);
            ps.setLong(1, springTransaction.getId());
            ps.setString(2, springTransaction.getSenderId());
            ps.setString(3, springTransaction.getReceiverId());
            ps.setDouble(4, springTransaction.getAmount());
            ps.setString(5, springTransaction.getMessage());
            ps.setLong(6, springTransaction.getCreatedAtMLS());
            ps.setLong(7, springTransaction.getUpdatedAtMLS());
            ps.setInt(8, springTransaction.getStatus());
            ps.setInt(9, springTransaction.getType());
            ps.execute();

            //4.1 Thực hiện commit transaction trong trường hợp không xảy ra bất kỳ lỗi gì.
            connection.commit();
            //4.2 Set auto commit về mặc định.
            connection.setAutoCommit(true);
            return true;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
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

    public boolean transfer(String senderAccountNumber, String receiverAccountNumber, double amount) {
        Connection connection = null;
        try {
            connection = ConnectionHelper.getConnection();
            connection.setAutoCommit(false);
            // 1. Lấy và kiểm tra thông tin tài khoản người gửi.
            String checkSenderAccountCmd = "select * from accounts where account = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(checkSenderAccountCmd);
            preparedStatement.setString(1, senderAccountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            Account senderAccount = null;
            if (resultSet.next()) {
                String rsAccount = resultSet.getString("account");
                double rsBalance = resultSet.getDouble(3);
                int rsStatus = resultSet.getInt(4); // thậm chí check trạng thái tài khoản.
                senderAccount = new Account();
                senderAccount.setAccount(rsAccount);
                senderAccount.setBalance(rsBalance);
                senderAccount.setStatus(rsStatus);
            }
            if (senderAccount == null || senderAccount.isNotActive()) {
                throw new SpringHeroException("Tài khoản gửi không tồn tại hoặc đang ở trạng thái không hoạt động!");
            }
            if (senderAccount.getBalance() < amount) {
                throw new SpringHeroException("Tài khoản gửi không đủ tiền. Vui lòng về xin bố mẹ hoặc vợ...");
            }

            // 2. Lấy và kiểm tra thông tin tài khoản nhận
            String checkReceiverAccountCmd = "select * from accounts where account = ?";
            PreparedStatement preparedStatementReceiver = connection.prepareStatement(checkReceiverAccountCmd);
            preparedStatementReceiver.setString(1, receiverAccountNumber);
            ResultSet resultSetReceiver = preparedStatementReceiver.executeQuery();
            Account receiverAccount = null;
            if (resultSetReceiver.next()) {
                String rsAccount = resultSet.getString("account");
                double rsBalance = resultSet.getDouble(3);
                int rsStatus = resultSet.getInt(4); // thậm chí check trạng thái tài khoản.
                receiverAccount = new Account();
                receiverAccount.setAccount(rsAccount);
                receiverAccount.setBalance(rsBalance);
                receiverAccount.setStatus(rsStatus);
            }
            if (receiverAccount == null || receiverAccount.isNotActive()) {
                throw new SpringHeroException("Tài khoản nhận không tồn tại hoặc đang ở trạng thái không hoạt động!");
            }

            // 3. Tiến hành trừ tiền tài khoản gửi.
            double senderUpdateBalance = senderAccount.getBalance() - amount;
            String updateAccountSenderCmd = "update accounts set balance = ? where account = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(updateAccountSenderCmd);
            preparedStatement1.setDouble(1, senderUpdateBalance);
            preparedStatement1.setString(2, senderAccountNumber);
            preparedStatement1.execute();

            // 4. Cộng tiền cho tài khoản nhận.
            double receiverUpdateBalance = receiverAccount.getBalance() + amount;
            String updateAccountReceiverCmd = "update accounts set balance = ? where account = ?";
            PreparedStatement preparedStatement2 = connection.prepareStatement(updateAccountReceiverCmd);
            preparedStatement2.setDouble(1, receiverUpdateBalance);
            preparedStatement2.setString(2, receiverAccountNumber);
            preparedStatement2.execute();

            // 5. Lưu thông tin transaction.
            // 5.1. Tạo ra đối tượng transaction.
            SpringTransaction springTransaction = new SpringTransaction();
            springTransaction.setSenderId(senderAccountNumber);
            springTransaction.setReceiverId(receiverAccountNumber);
            springTransaction.setType(SpringTransaction.Type.TRANSFER);
            springTransaction.setAmount(amount);
            springTransaction.setMessage(String.format(transferMessage, senderAccountNumber, receiverAccountNumber, amount));
            springTransaction.setStatus(SpringTransaction.Status.COMPLETED);
            // 5.2. Lưu vào database.
            String createTransactionCmd = "insert into transactions (id, senderId, receiverId, amount, message, createdAtMLS, updatedAtMLS, status, type) values (?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(createTransactionCmd);
            ps.setLong(1, springTransaction.getId());
            ps.setString(2, springTransaction.getSenderId());
            ps.setString(3, springTransaction.getReceiverId());
            ps.setDouble(4, springTransaction.getAmount());
            ps.setString(5, springTransaction.getMessage());
            ps.setLong(6, springTransaction.getCreatedAtMLS());
            ps.setLong(7, springTransaction.getUpdatedAtMLS());
            ps.setInt(8, springTransaction.getStatus());
            ps.setInt(9, springTransaction.getType());
            ps.execute();

            // 6.1. Thực hiện commit transaction trong trường hợp không xảy ra bất kỳ lỗi gì.
            connection.commit();
            // 6.2. Set auto commit về mặc định.
            connection.setAutoCommit(true);
            return true;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
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
        model.transfer("xuanhung", "xuanhung1", 50000);
    }

}
