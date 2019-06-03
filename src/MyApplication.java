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

    private Stage currentStage;
    private Stage loginStage;
    private Stage registerStage;
    private Scene defaultScene;
    private Label loginStatus;

    @Override
    public void start(Stage stage) throws Exception {
        this.currentStage = stage;
        initComponent();
        this.currentStage.show();
    }

    private void initComponent() {
        Button btnLogin = new Button("Login Stage");
        Button btnRegister = new Button("Register Stage");
        this.loginStatus = new Label("Not logged in.");
        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (loginStage == null) {
                    loginStage = new LoginStage();
                }
                loginStage.showAndWait();
            }
        });
        btnRegister.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(registerStage == null){
                    registerStage = new RegisterStage();
                }
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
}
