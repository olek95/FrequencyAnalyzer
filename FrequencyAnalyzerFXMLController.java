package frequencyanalyzer;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class FrequencyAnalyzerFXMLController implements Initializable {
    private boolean analyzed; 
    @FXML
    private Button openButton, saveButton, exitButton;
    @FXML
    private TextArea fileContentTextArea, resultTextArea;
    @FXML
    private RadioButton analysisRadioButton, fileContentRadioButton;
    @FXML
    private void analyze(ActionEvent event) {
        fileContentTextArea.setVisible(false);
        resultTextArea.setVisible(true);
        if(!analyzed){
            LinkedHashMap<Character, Integer> charactersOccurence = new LinkedHashMap();
            for(char c = ' '; c <= '}'; c++)
                charactersOccurence.put(c, 0);
            String text = fileContentTextArea.getText(); 
            for(int i = 0; i < text.length(); i++){
                char character = text.charAt(i);
                if(charactersOccurence.containsKey(character))
                    charactersOccurence.replace(character, charactersOccurence.get(character) + 1);
            }
            resultTextArea.setText("Znak = ilość wystąpień\n");
            for(Map.Entry<Character, Integer> entry : charactersOccurence.entrySet()){
                resultTextArea.appendText(entry.getKey() + " = " + entry.getValue() + "\n");
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resultTextArea.setVisible(false);
        fileContentRadioButton.setOnAction(event -> {
            resultTextArea.setVisible(false);
            fileContentTextArea.setVisible(true);
        });
        exitButton.setOnAction(event -> {
            System.exit(0);
        });
        openButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser(); 
            fileChooser.setTitle("Odczyt plików tesktowych");
            fileChooser.getExtensionFilters().add(new ExtensionFilter("Pliki tekstowe", "*.txt"));
            try{
                fileContentTextArea.setText(new String(Files.readAllBytes(fileChooser.showOpenDialog(null).toPath())));
            }catch(IOException e){
                Logger.getLogger(FrequencyAnalyzerFXMLController.class.getName()).log(Level.SEVERE, null, e);
            }
        });
    }    
}
