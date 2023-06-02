package com.example.lab10.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class Modal1Controller {
    @FXML
    private Text displayText;

    @FXML
    private Button Button1, Button2;

    static String textMsg = "";

    @FXML
    public void initialize() {
        displayText.setText(textMsg);
    }

    public void init(String mainMsg, String button1, String button2, Runnable callback1, Runnable callback2) {
        textMsg = mainMsg;
        displayText.setText(textMsg);
        Button1.setText(button1);
        Button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                callback1.run();
            }
        });
        Button2.setText(button2);
        Button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                callback2.run();
            }
        });
    }
}
