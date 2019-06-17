package spring.hero.bank;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainApplication extends Application {

    private String accountName = "xuanhung";
    private double accountBalance = 50000;

    private VBox vBox; // bao ngoài cả 3 phần header, body, footer.
    private HBox titleBox; // header chứa logo - text.
    private GridPane gridPaneContent; // body chứa thông tin tài khoản.
    private HBox btnBox; // footer chứa các button.

    private Label lblTitle;
    private Label lblAccount;
    private Label lblBalance;
    private Label lblAccountName;
    private Label lblBalanceValue;
    private Button btnWithdraw;
    private Button btnDeposit;

    private Scene scene;
    private Stage mainStage;
    private MainApplication mainApplication;
    private TransactionStage transactionStage;
    private LoginStage loginStage;

    private static boolean isLoggedIn = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.mainApplication = this;
        this.mainStage = primaryStage;
        initComponent();
        if (isLoggedIn) {
            // show stage.
            this.mainStage.show();
        } else {
            this.loginStage = new LoginStage(this);
            this.loginStage.show();
        }
    }

    private void initComponent() {
        this.vBox = new VBox(); // layout

        // xử lý phần header, logo.
        this.titleBox = new HBox();
        this.lblTitle = new Label("Spring Hero Bank");
        this.lblTitle.setFont(Font.font(17));
        this.lblTitle.setTextFill(Color.web("#f9e70b"));
        this.titleBox.getChildren().add(lblTitle);
        this.titleBox.setAlignment(Pos.TOP_LEFT);
        this.titleBox.setSpacing(10);

        // xử lý phần body content.
        this.gridPaneContent = new GridPane();
        this.lblAccount = new Label("Account:");
        this.lblBalance = new Label("Balance:");
        this.lblAccountName = new Label(this.accountName);
        this.lblBalanceValue = new Label(String.valueOf(this.accountBalance));
        this.gridPaneContent.add(this.lblAccount, 0, 0);
        this.gridPaneContent.add(this.lblAccountName, 1, 0);
        this.gridPaneContent.add(this.lblBalance, 0, 1);
        this.gridPaneContent.add(this.lblBalanceValue, 1, 1);
        this.gridPaneContent.setAlignment(Pos.CENTER);
        this.gridPaneContent.setPadding(new Insets(10, 10, 10, 10));
        this.gridPaneContent.setVgap(10);
        this.gridPaneContent.setHgap(10);

        // xử lý phần footer button.
        this.btnBox = new HBox();
        this.btnWithdraw = new Button("Withdraw");
        this.btnDeposit = new Button("Deposit");
        this.btnWithdraw.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                transactionStage = new TransactionStage(1, mainApplication);
                transactionStage.showAndWait();
            }
        });
        this.btnDeposit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                transactionStage = new TransactionStage(2, mainApplication);
                transactionStage.showAndWait();
            }
        });
        this.btnBox.getChildren().addAll(this.btnWithdraw, this.btnDeposit);
        this.btnBox.setAlignment(Pos.CENTER);
        this.btnBox.setSpacing(10);

        // ghép các phần với nhau, đưa tất cả và vbox.
        this.vBox.getChildren().addAll(this.titleBox, this.gridPaneContent, this.btnBox);
        this.vBox.setAlignment(Pos.CENTER);
        this.vBox.setSpacing(30);
        this.vBox.setPadding(new Insets(10, 10, 10, 10));

        // nhồi vbox và scene.
        this.scene = new Scene(this.vBox, 300, 200);
        // nhồi scene vào stage.
        this.mainStage.setScene(this.scene);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Label getLblBalanceValue() {
        return lblBalanceValue;
    }

    public void setLblBalanceValue(Label lblBalanceValue) {
        this.lblBalanceValue = lblBalanceValue;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }

    public VBox getvBox() {
        return vBox;
    }

    public void setvBox(VBox vBox) {
        this.vBox = vBox;
    }

    public HBox getTitleBox() {
        return titleBox;
    }

    public void setTitleBox(HBox titleBox) {
        this.titleBox = titleBox;
    }

    public HBox getBtnBox() {
        return btnBox;
    }

    public void setBtnBox(HBox btnBox) {
        this.btnBox = btnBox;
    }

    public GridPane getGridPaneContent() {
        return gridPaneContent;
    }

    public void setGridPaneContent(GridPane gridPaneContent) {
        this.gridPaneContent = gridPaneContent;
    }

    public Label getLblTitle() {
        return lblTitle;
    }

    public void setLblTitle(Label lblTitle) {
        this.lblTitle = lblTitle;
    }

    public Label getLblAccount() {
        return lblAccount;
    }

    public void setLblAccount(Label lblAccount) {
        this.lblAccount = lblAccount;
    }

    public Label getLblBalance() {
        return lblBalance;
    }

    public void setLblBalance(Label lblBalance) {
        this.lblBalance = lblBalance;
    }

    public Label getLblAccountName() {
        return lblAccountName;
    }

    public void setLblAccountName(Label lblAccountName) {
        this.lblAccountName = lblAccountName;
    }

    public Button getBtnWithdraw() {
        return btnWithdraw;
    }

    public void setBtnWithdraw(Button btnWithdraw) {
        this.btnWithdraw = btnWithdraw;
    }

    public Button getBtnDeposit() {
        return btnDeposit;
    }

    public void setBtnDeposit(Button btnDeposit) {
        this.btnDeposit = btnDeposit;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public MainApplication getMainApplication() {
        return mainApplication;
    }

    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    public TransactionStage getTransactionStage() {
        return transactionStage;
    }

    public void setTransactionStage(TransactionStage transactionStage) {
        this.transactionStage = transactionStage;
    }

    public LoginStage getLoginStage() {
        return loginStage;
    }

    public void setLoginStage(LoginStage loginStage) {
        this.loginStage = loginStage;
    }

    public static boolean isIsLoggedIn() {
        return isLoggedIn;
    }

    public static void setIsLoggedIn(boolean isLoggedIn) {
        MainApplication.isLoggedIn = isLoggedIn;
    }
}
