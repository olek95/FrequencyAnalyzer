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
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class FrequencyAnalyzerFXMLController implements Initializable {
    private boolean analyzed = false; 
    private LinkedHashMap<Character, Integer> charactersOccurence;
    @FXML
    private Button openButton, saveButton, exitButton;
    @FXML
    private TextArea fileContentTextArea, resultTextArea;
    @FXML
    private RadioButton analysisRadioButton, fileContentRadioButton, alphabeticallyRadioButton, notAlphabeticallyRadioButton;
    @FXML
    private CheckBox onlyOccurringCheckBox;
    @FXML
    private void analyze(ActionEvent event) {
        setAnalysisMode(true);
        if(!analyzed){
            charactersOccurence = new LinkedHashMap();
            for(char c = ' '; c <= '}'; c++)
                charactersOccurence.put(c, 0);
            String text = fileContentTextArea.getText(); 
            for(int i = 0; i < text.length(); i++){
                char character = text.charAt(i);
                if(charactersOccurence.containsKey(character))
                    charactersOccurence.replace(character, charactersOccurence.get(character) + 1);
            }
            writeResult();
            analyzed = true;
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EventHandler writeHandler = (EventHandler) (Event event) -> {
            writeResult();
        };
        setAnalysisMode(false);
        fileContentRadioButton.setOnAction(event -> {
            setAnalysisMode(false);
        });
        openButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser(); 
            fileChooser.setTitle("Odczyt plików tekstowych");
            fileChooser.getExtensionFilters().add(new ExtensionFilter("Pliki tekstowe", "*.txt"));
            try{
                fileContentTextArea.setText(new String(Files.readAllBytes(fileChooser.showOpenDialog(null).toPath())));
            }catch(IOException e){
                Logger.getLogger(FrequencyAnalyzerFXMLController.class.getName()).log(Level.SEVERE, null, e);
            }
        });
        saveButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser(); 
            fileChooser.setTitle("Zapis wyniku");
            fileChooser.getExtensionFilters().add(new ExtensionFilter("Pliki tekstowe", "*.txt"));
            try{
                Files.write(fileChooser.showSaveDialog(null).toPath(), resultTextArea.getText().getBytes());
            }catch(IOException e){
                Logger.getLogger(FrequencyAnalyzerFXMLController.class.getName()).log(Level.SEVERE, null, e);
            }
        });
        fileContentTextArea.textProperty().addListener(listener -> {
            analyzed = false;
        });
        onlyOccurringCheckBox.setOnAction(writeHandler);
        alphabeticallyRadioButton.setOnAction(writeHandler);
        notAlphabeticallyRadioButton.setOnAction(writeHandler);
        exitButton.setOnAction(event -> {
            System.exit(0);
        });
    }    
    private void writeResult(){
        resultTextArea.setText("Znak = ilość wystąpień\n");
        if(!onlyOccurringCheckBox.isSelected()){
            for(Map.Entry<Character, Integer> entry : charactersOccurence.entrySet()){
                resultTextArea.appendText(entry.getKey() + " = " + entry.getValue() + "\n");
            }
        }else{
            for(Map.Entry<Character, Integer> entry : charactersOccurence.entrySet()){
                int number = entry.getValue(); 
                if(number != 0)
                    resultTextArea.appendText(entry.getKey() + " = " + number + "\n");
            }
        }
        if(notAlphabeticallyRadioButton.isSelected()){
            String text = resultTextArea.getText().substring("Znak = ilość wystąpień\n".length());
            resultTextArea.setText("Znak = ilość wystąpień" + new StringBuilder(text).reverse().toString());
        }
    }
    private void setAnalysisMode(boolean mode){
        fileContentTextArea.setVisible(!mode);
        resultTextArea.setVisible(mode);
        alphabeticallyRadioButton.setVisible(mode); 
        notAlphabeticallyRadioButton.setVisible(mode); 
        onlyOccurringCheckBox.setVisible(mode); 
        saveButton.setDisable(!mode);
        openButton.setDisable(mode);
    }
}
