package main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import views.LoginStage;
import views.RegisterStage;

public class MyApplication extends Application {

    private MyApplication application;
    private Stage currentStage;
    private LoginStage loginStage;
    private RegisterStage registerStage;
    private Scene defaultScene;
    private Label loginStatus;

    @Override
    public void start(Stage stage) throws Exception {
        this.application = this;
        this.currentStage = stage;
        initComponent();
        this.currentStage.show();
    }

    private void initComponent() {
        this.loginStage = new LoginStage(application);
        this.registerStage = new RegisterStage();
        Button btnLogin = new Button("Login Stage");
        Button btnRegister = new Button("Register Stage");
        this.loginStatus = new Label("Not logged in.");
        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loginStage.getTxtUsername().setText("Please enter your username.");
                loginStage.showAndWait();
            }
        });
        btnRegister.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                registerStage.show();
            }
        });
        HBox hBox = new HBox();
        hBox.getChildren().addAll(btnLogin, btnRegister, loginStatus);
        this.defaultScene = new Scene(hBox, 300, 200);
        this.currentStage.setScene(this.defaultScene);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public MyApplication getApplication() {
        return application;
    }

    public void setApplication(MyApplication application) {
        this.application = application;
    }

    public LoginStage getLoginStage() {
        return loginStage;
    }

    public void setLoginStage(LoginStage loginStage) {
        this.loginStage = loginStage;
    }

    public RegisterStage getRegisterStage() {
        return registerStage;
    }

    public void setRegisterStage(RegisterStage registerStage) {
        this.registerStage = registerStage;
    }

    public Scene getDefaultScene() {
        return defaultScene;
    }

    public void setDefaultScene(Scene defaultScene) {
        this.defaultScene = defaultScene;
    }

    public Label getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Label loginStatus) {
        this.loginStatus = loginStatus;
    }
}
