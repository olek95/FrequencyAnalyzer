package frequencyanalyzer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FrequencyAnalyzer extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FrequencyAnalyzerFXML.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setResizable(false);
        stage.setTitle("Program liczący ilość wystąpień znaków w pliku.");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
