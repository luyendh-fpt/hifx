package entity;

import java.util.Calendar;

public class SpringTransaction {

    private long id;
    private String senderId;
    private String receiverId;
    private int type; // 1. withdraw | 2. deposit | 3. transfer
    private double amount;
    private String message;
    private long createdAtMLS;
    private long updatedAtMLS;
    private int status; // 0. pending | 1. completed | -1. deleted

    public SpringTransaction() {
        this.id = Calendar.getInstance().getTimeInMillis();
        this.createdAtMLS = Calendar.getInstance().getTimeInMillis();
        this.updatedAtMLS = Calendar.getInstance().getTimeInMillis();
    }

    public enum Status {

        COMPLETED(1), PENDING(0), CANCEL(-1), UNDEFINED(-2);

        private int value;

        Status(int value) {
            this.value = value;
        }

        public static Status findByValue(int value) {
            for (int i = 0; i < Status.values().length; i++) {
                if (Status.values()[i].getValue() == value) {
                    return Status.values()[i];
                }
            }
            return Status.UNDEFINED;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public enum Type {

        WITHDRAW(1), DEPOSIT(2), TRANSFER(3), UNDEFINED(0);

        private int value;

        Type(int value) {
            this.value = value;
        }

        public static Type findByValue(int value) {
            for (int i = 0; i < Type.values().length; i++) {
                if (Status.values()[i].getValue() == value) {
                    return Type.values()[i];
                }
            }
            return Type.UNDEFINED;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getCreatedAtMLS() {
        return createdAtMLS;
    }

    public void setCreatedAtMLS(long createdAtMLS) {
        this.createdAtMLS = createdAtMLS;
    }

    public long getUpdatedAtMLS() {
        return updatedAtMLS;
    }

    public void setUpdatedAtMLS(long updatedAtMLS) {
        this.updatedAtMLS = updatedAtMLS;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(Status transactionStatus) {
        this.status = transactionStatus.getValue();
    }

    public int getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type.getValue();
    }
}
