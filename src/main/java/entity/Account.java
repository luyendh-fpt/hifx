package entity;

public class Account {

    private String account;
    private String password;
    private double balance;
    private int status;

    public enum Status {

        ACTIVE(1), DEACTIVE(0), DELETED(-1);

        private int value;

        Status(int value) {
            this.value = value;
        }

        public static SpringTransaction.Status findByValue(int value) {
            for (int i = 0; i < SpringTransaction.Status.values().length; i++) {
                if (SpringTransaction.Status.values()[i].getValue() == value) {
                    return SpringTransaction.Status.values()[i];
                }
            }
            return SpringTransaction.Status.UNDEFINED;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public Account() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
