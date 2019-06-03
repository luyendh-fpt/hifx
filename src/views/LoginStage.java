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

public class LoginStage extends Stage {

    private GridPane gridPane;
    private Label labelUsername;
    private Label labelPassword;
    private TextField txtUsername;
    private PasswordField pwdField;
    private Button btnLogin;
    private Button btnReset;

    public LoginStage() {
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

}
