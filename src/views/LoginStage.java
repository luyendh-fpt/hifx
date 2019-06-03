package views;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.MyApplication;

public class LoginStage extends Stage {

    private MyApplication application;
    private Label loginStatus;
    private GridPane gridPane;
    private Label labelUsername;
    private Label labelPassword;
    private TextField txtUsername;
    private PasswordField pwdField;
    private Button btnLogin;
    private Button btnReset;

    public LoginStage(MyApplication application) {
        this.application = application;
        initComponent();
    }

    public LoginStage(Label loginStatus) {
        this.loginStatus = loginStatus;
        initComponent();
    }

    private void initComponent() {
        this.initModality(Modality.APPLICATION_MODAL);
        this.gridPane = new GridPane();
        this.labelUsername = new Label("Username");
        this.labelPassword = new Label("Password");
        this.txtUsername = new TextField();
        this.pwdField = new PasswordField();
        this.btnLogin = new Button("Login");
        this.btnReset = new Button("Reset");
        this.btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String username = txtUsername.getText();
                application.getLoginStatus().setText("Logged in: " + username);
//                loginStatus.setText(username);
            }
        });
        this.gridPane.setPadding(new Insets(10, 10, 10, 10));
        this.gridPane.setVgap(10);
        this.gridPane.setHgap(10);
        this.gridPane.setAlignment(Pos.CENTER);
        this.gridPane.add(this.labelUsername, 0, 0);
        this.gridPane.add(this.txtUsername, 1, 0);
        this.gridPane.add(this.labelPassword, 0, 1);
        this.gridPane.add(this.pwdField, 1, 1);
        HBox hBox = new HBox();
        hBox.getChildren().addAll(this.btnLogin, this.btnReset);
        hBox.setSpacing(10);
        this.gridPane.add(hBox, 1, 2);
        Scene scene = new Scene(this.gridPane, 600, 300);
        this.setScene(scene);
    }

    public MyApplication getApplication() {
        return application;
    }

    public void setApplication(MyApplication application) {
        this.application = application;
    }

    public Label getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Label loginStatus) {
        this.loginStatus = loginStatus;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public Label getLabelUsername() {
        return labelUsername;
    }

    public void setLabelUsername(Label labelUsername) {
        this.labelUsername = labelUsername;
    }

    public Label getLabelPassword() {
        return labelPassword;
    }

    public void setLabelPassword(Label labelPassword) {
        this.labelPassword = labelPassword;
    }

    public TextField getTxtUsername() {
        return txtUsername;
    }

    public void setTxtUsername(TextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    public PasswordField getPwdField() {
        return pwdField;
    }

    public void setPwdField(PasswordField pwdField) {
        this.pwdField = pwdField;
    }

    public Button getBtnLogin() {
        return btnLogin;
    }

    public void setBtnLogin(Button btnLogin) {
        this.btnLogin = btnLogin;
    }

    public Button getBtnReset() {
        return btnReset;
    }

    public void setBtnReset(Button btnReset) {
        this.btnReset = btnReset;
    }
}
