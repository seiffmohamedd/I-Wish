package wishserver;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WishServer extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AdminView.fxml"));
        Parent root = loader.load();

       
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("WishServer - Admin View");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args); 
    }
}
