package entity;

public class MyDemo {

    public static void main(String[] args) {
        int choosedStatus = -2;
        SpringTransaction.Status status = SpringTransaction.Status.findByValue(choosedStatus);
        System.out.println(status.name());
        System.out.println(status.getValue());
    }


}
