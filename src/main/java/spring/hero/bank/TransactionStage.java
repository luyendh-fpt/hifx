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

public class TransactionStage extends Stage {

    private VBox vBox; // 3 tầng.
    private HBox hBox; // label action.
    private GridPane gridPane; // số lượng và nút bấm.
    private HBox btnHbox; // chứa 2 nút bấm.

    private int action; // 1. withdraw | 2. deposit
    private Label lblAction;
    private Label lblAmount;
    private TextField txtAmount;
    private Button btnSubmit;
    private Button btnCancel;

    private Scene scene;
    private Stage stage;
    private MainApplication mainApplication;

    public TransactionStage() {

    }

    public TransactionStage(int action, MainApplication mainApplication) {
        this.action = action;
        this.stage = this;
        this.mainApplication = mainApplication;
        initComponent();
        this.scene = new Scene(this.vBox, 270, 170);
        this.setScene(this.scene);
        this.initModality(Modality.APPLICATION_MODAL);
    }

    private void initComponent() {
        if (this.action == 1) {
            this.lblAction = new Label("Withdraw");
        } else {
            this.lblAction = new Label("Deposit");
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
                int currentBalance = (action == 1) ? (mainApplication.getAccountBalance() - amount) : (mainApplication.getAccountBalance() + amount);
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

        this.gridPane.add(this.lblAmount, 0, 0);
        this.gridPane.add(this.txtAmount, 1, 0);
        this.btnHbox = new HBox();
        this.btnHbox.setPadding(new Insets(10));
        this.btnHbox.setSpacing(10);
        this.btnHbox.getChildren().addAll(this.btnSubmit, this.btnCancel);
        this.gridPane.add(this.btnHbox, 1, 1);

        this.vBox = new VBox();
        this.vBox.setPadding(new Insets(10));
        this.vBox.setSpacing(10);
        this.vBox.getChildren().addAll(this.hBox, this.gridPane);
        this.vBox.setAlignment(Pos.CENTER);
    }
}
