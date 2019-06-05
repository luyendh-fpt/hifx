package spring.hero.bank;

import entity.Account;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.AccountModel;

public class LoginStage extends Stage {

    private static final int LOGIN_STAGE_WIDTH = 300;
    private static final int LOGIN_STAGE_HEIGHT = 200;
    private VBox vBox;
    private HBox titleBox;
    private GridPane gridPane;
    private HBox btnBox;

    private Label lblTitle;
    private Label lblAccount;
    private Label lblPassword;
    private TextField txtAccount;
    private PasswordField pwdPassword;
    private Button btnLogin;
    private Button btnReset;

    private Scene scene;
    private Stage stage;
    private MainApplication mainApplication;
    private AccountModel model;

    public LoginStage(MainApplication mainApplication) {
        this.model = new AccountModel();
        this.mainApplication = mainApplication;
        this.initComponent();
        this.scene = new Scene(this.vBox, LOGIN_STAGE_WIDTH, LOGIN_STAGE_HEIGHT);
        this.stage = this;
        this.stage.setScene(this.scene);
    }

    public void initComponent() {
        this.titleBox = new HBox();
        this.titleBox.setAlignment(Pos.TOP_LEFT);
        this.titleBox.setSpacing(10);
        this.lblTitle = new Label("Spring Hero Bank");
        this.lblTitle.setFont(Font.font(17));
        this.lblTitle.setTextFill(Color.web("#f9e70b"));
        this.titleBox.getChildren().add(lblTitle);

        this.gridPane = new GridPane();
        this.gridPane.setPadding(new Insets(10));
        this.gridPane.setVgap(10);
        this.gridPane.setHgap(10);
        this.lblAccount = new Label("Account");
        this.lblPassword = new Label("Password");
        this.txtAccount = new TextField();
        this.pwdPassword = new PasswordField();
        this.btnLogin = new Button("Login");
        this.btnReset = new Button("Reset");
        this.btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Account account = model.findByAccountAndPassword(txtAccount.getText(), pwdPassword.getText());
                if (account != null) {
                    mainApplication.setAccountName(account.getAccount());
                    mainApplication.setAccountBalance(account.getBalance());
                    mainApplication.getLblAccountName().setText(account.getAccount());
                    mainApplication.getLblBalanceValue().setText(String.valueOf(account.getBalance()));
                    mainApplication.setIsLoggedIn(true);
                    mainApplication.getMainStage().show();
                    stage.close();
                } else {
                    // show lỗi bằng label màu đỏ.
                }
            }
        });
        this.btnReset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txtAccount.clear();
                pwdPassword.clear();
            }
        });
        this.btnBox = new HBox();
        this.btnBox.setPadding(new Insets(10));
        this.btnBox.setSpacing(10);
        this.btnBox.setAlignment(Pos.TOP_LEFT);
        this.btnBox.getChildren().addAll(btnLogin, btnReset);
        this.gridPane.add(this.lblAccount, 0, 0);
        this.gridPane.add(this.lblPassword, 0, 1);
        this.gridPane.add(this.txtAccount, 1, 0);
        this.gridPane.add(this.pwdPassword, 1, 1);
        this.gridPane.add(this.btnBox, 1, 2);

        this.vBox = new VBox();
        this.vBox.setPadding(new Insets(10));
        this.vBox.setSpacing(10);
        this.vBox.setAlignment(Pos.CENTER);
        this.vBox.getChildren().addAll(this.titleBox, this.gridPane);
    }
}
