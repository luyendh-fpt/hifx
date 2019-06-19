package spring.hero.bank;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.AccountModel;

public class TransactionStage extends Stage {

    private VBox vBox; // 3 tầng.
    private HBox hBox; // label action.
    private GridPane gridPane; // số lượng và nút bấm.
    private HBox btnHbox; // chứa 2 nút bấm.

    private int action; // 1. withdraw | 2. deposit
    private Label lblAction;
    private Label lblAmount;
    private Label lblReceiverAccountNumber;
    private TextField txtAmount;
    private TextField txtReceiverAccountNumber;
    private Button btnSubmit;
    private Button btnCancel;

    private Scene scene;
    private Stage stage;
    private MainApplication mainApplication;
    private AccountModel accountModel = new AccountModel();

    public TransactionStage() {

    }

    public TransactionStage(int action, MainApplication mainApplication) {
        this.action = action;
        this.stage = this;
        this.mainApplication = mainApplication;
        initComponent();
        if (this.action == 3) {
            this.scene = new Scene(this.vBox, 330, 220);
        } else {
            this.scene = new Scene(this.vBox, 270, 170);
        }
        this.setScene(this.scene);
        this.initModality(Modality.APPLICATION_MODAL);
    }

    private void initComponent() {
        if (this.action == 1) {
            this.lblAction = new Label("Withdraw");
        } else if (this.action == 2) {
            this.lblAction = new Label("Deposit");
        } else if (this.action == 3) {
            this.lblAction = new Label("Transfer");
            this.lblReceiverAccountNumber = new Label("Receiver Account");
            this.txtReceiverAccountNumber = new TextField();
        }
        this.hBox = new HBox();
        this.hBox.setSpacing(10);
        this.hBox.setPadding(new Insets(10));
        this.hBox.setAlignment(Pos.TOP_LEFT);
        this.hBox.getChildren().add(this.lblAction);

        this.gridPane = new GridPane();
        this.gridPane.setHgap(10);
        this.gridPane.setVgap(10);
        this.gridPane.setPadding(new Insets(10));
        this.gridPane.setAlignment(Pos.CENTER);
        this.lblAmount = new Label("Amount");
        this.txtAmount = new TextField();
        this.btnSubmit = new Button("Submit");
        this.btnCancel = new Button("Cancel");
        this.btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int amount = 0;
                try {
                    amount = Integer.parseInt(txtAmount.getText());
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    // tự mà làm label lỗi.
                    // tự mà check số lượng.
                }
                // truy vấn db một lần nữa.
                double currentBalance = 0;
                switch (action) {
                    case 1:
                        accountModel.withdraw(mainApplication.getAccountName(), amount);
                        currentBalance = mainApplication.getAccountBalance() - amount;
                        break;
                    case 2:
                        accountModel.deposit(mainApplication.getAccountName(), amount);
                        currentBalance = mainApplication.getAccountBalance() + amount;
                        break;
                    case 3:
                        accountModel.transfer(mainApplication.getAccountName(), txtReceiverAccountNumber.getText(), amount);
                        currentBalance = mainApplication.getAccountBalance() - amount;
                        break;
                }
                mainApplication.getLblBalanceValue().setText(String.valueOf(currentBalance));
                mainApplication.setAccountBalance(currentBalance);
                stage.close();
            }
        });
        this.btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });

        if (this.action == 3) {
            this.gridPane.add(this.lblReceiverAccountNumber, 0, 0);
            this.gridPane.add(this.txtReceiverAccountNumber, 1, 0);
            this.gridPane.add(this.lblAmount, 0, 1);
            this.gridPane.add(this.txtAmount, 1, 1);
        } else {
            this.gridPane.add(this.lblAmount, 0, 0);
            this.gridPane.add(this.txtAmount, 1, 0);
        }

        this.btnHbox = new HBox();
        this.btnHbox.setPadding(new Insets(10));
        this.btnHbox.setSpacing(10);
        this.btnHbox.getChildren().addAll(this.btnSubmit, this.btnCancel);
        if (this.action == 3) {
            this.gridPane.add(this.btnHbox, 1, 2);
        } else {
            this.gridPane.add(this.btnHbox, 1, 1);
        }

        this.vBox = new VBox();
        this.vBox.setPadding(new Insets(10));
        this.vBox.setSpacing(10);
        this.vBox.getChildren().addAll(this.hBox, this.gridPane);
        this.vBox.setAlignment(Pos.CENTER);
    }
}
