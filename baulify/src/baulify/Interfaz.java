
package baulify;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Interfaz extends Application {
    
    public static Stage current_stage;
    
    @Override
    public void start(Stage stage) throws Exception {
        current_stage = stage;
        stage.setTitle("Baulify"); 
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));     
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
      */
    public static void main(String[] args) {
        launch(args);
    }
    
     public static Stage getCurrentStage() {
         return current_stage;
    }
   
}
