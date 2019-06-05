package spring.hero.bank;

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
import javafx.stage.Stage;

public class RegisterStage extends Stage {

    private GridPane gridPane;
    private Label labelUsername;
    private Label labelPassword;
    private TextField txtUsername;
    private PasswordField pwdField;
    private Button btnLogin;
    private Button btnReset;

    public RegisterStage() {
        initComponent();
    }

    private void initComponent() {
        this.gridPane = new GridPane();
        this.labelUsername = new Label("Username");
        this.labelPassword = new Label("Password");
        this.txtUsername = new TextField();
        this.pwdField = new PasswordField();
        this.btnLogin = new Button("Register");
        this.btnReset = new Button("Reset");
        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(labelUsername, 0, 0);
        gridPane.add(txtUsername, 1, 0);
        gridPane.add(labelPassword, 0, 1);
        gridPane.add(pwdField, 1, 1);
        HBox hBox = new HBox();
        hBox.getChildren().addAll(btnLogin, btnReset);
        hBox.setSpacing(10);
        gridPane.add(hBox, 1, 2);
        Scene scene = new Scene(gridPane, 600, 300);
        this.setScene(scene);
    }

}

