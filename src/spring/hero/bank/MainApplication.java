package spring.hero.bank;

import javafx.application.Application;
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
    private int accountBalance = 50000;

    private VBox vBox;
    private HBox titleBox;
    private HBox btnBox;
    private GridPane gridPaneContent;

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

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.mainApplication = this;
        this.mainStage = primaryStage;
        this.vBox = new VBox(); // layout
        this.titleBox = new HBox();
        this.lblTitle = new Label("Spring Hero Bank");
        this.lblTitle.setFont(Font.font(17));
        this.lblTitle.setTextFill(Color.web("#f9e70b"));
        this.titleBox.getChildren().add(lblTitle);
        this.titleBox.setAlignment(Pos.TOP_LEFT);
        this.titleBox.setSpacing(10);

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

        this.btnBox = new HBox();
        this.btnWithdraw = new Button("Withdraw");
        this.btnDeposit = new Button("Deposit");
        this.btnBox.getChildren().addAll(this.btnWithdraw, this.btnDeposit);
        this.btnBox.setAlignment(Pos.CENTER);
        this.btnBox.setSpacing(10);

        this.vBox.getChildren().addAll(this.titleBox, this.gridPaneContent, this.btnBox);
        this.vBox.setAlignment(Pos.CENTER);
        this.vBox.setSpacing(30);
        this.vBox.setPadding(new Insets(10, 10, 10, 10));

        this.scene = new Scene(this.vBox, 300, 200);
        this.mainStage.setScene(this.scene);
        this.mainStage.show();
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

    public int getAccountBalance() {
        return accountBalance;
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
}
