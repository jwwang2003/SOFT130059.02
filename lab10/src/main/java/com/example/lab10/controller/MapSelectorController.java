package com.example.lab10.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.example.lab10.gamelogic.Map;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MapSelectorController {
    private boolean hasValidInput = false;

    @FXML
    TextArea textArea;

    @FXML
    Button inputFile, confirm;

    @FXML
    public void initialize() {
        if(Map.getHasValidMap()) {
            String mapString = Map.defaultPosition2String();
            textArea.setText(mapString);
            setTextAreaSuccess();
            hasValidInput = true;
        }

        textArea.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (newPropertyValue) {}
            else {
                textAreaOnChange();
            }
        });
    }

    public void inputFile() throws IOException {
        Stage stage = (Stage) textArea.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(stage);

        Scanner read = new Scanner(file, StandardCharsets.UTF_8);
//        read.useDelimiter(" ");

        String text = "";
        List<List<Integer>> map = new ArrayList<>();
        text = Map.readMapFromScanner(read, map);

        read.close();
        textArea.setText(text);

        if(Map.setDefaultPositions(map))
            setTextAreaSuccess();
        else
            setTextAreaError();
    }

    public void textAreaOnChange() {
        if(Map.setDefaultPositions(textArea.getText()))
            setTextAreaSuccess();
        else
            setTextAreaError();
    }

    public void confirm() throws IOException {
        if(Map.getHasValidMap() && hasValidInput) {
            Stage stage = (Stage) confirm.getScene().getWindow();
            stage.close();
        }
    }

    private void setTextAreaSuccess() {
        hasValidInput = true;
        textArea.setStyle("-fx-background-color: #08A936; -fx-border-color: #08A936;");
    }

    private void setTextAreaError() {
        hasValidInput = false;
        textArea.setStyle("-fx-background-color: #E0102F; -fx-border-color: #E0102F;");
    }
}
