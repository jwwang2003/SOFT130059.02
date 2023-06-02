package com.example.lab10;

import com.example.lab10.gamelogic.Map;
import com.example.lab10.gamelogic.entities.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main extends Application {
    private Scene scene;

    private void initEntityIcons() {
        GameBoy.setIcon(getResourcePath("images/entities/Character1.png"), 1200, 1200);
        Box.setIcon(getResourcePath("images/entities/Box.jpeg"), 300, 300);
        Goal.setIcon(getResourcePath("images/entities/Goal.png"), 570, 700);
        Obstacle.setIcon(getResourcePath("images/entities/Obstacle.png"), 200, 200);
        Wall.setIcon(getResourcePath("images/entities/Wall.png"), 1753, 1753);
    }

    private void initReadDefaultMap() {
        try {
            Scanner read = new Scanner(new File(
                    Main.class.getResource("defaultMap.txt").toURI()
            ), StandardCharsets.UTF_8);

            String text = "";
            List<List<Integer>> map = new ArrayList<>();
            text = Map.readMapFromScanner(read, map);

            if(Map.setDefaultPositions(map)) {

            }
            else
                throw new Exception("Unable to interpret default map data");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public String getResourcePath(String file) {
        return Main.class.getResource(file).toExternalForm();
    }

    @Override
    public void start(Stage stage) throws IOException {
        initEntityIcons();
        initReadDefaultMap();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GUI.fxml"));
        scene = new Scene((fxmlLoader.load()));

        stage.setTitle("Sokoban");
        stage.setScene(scene);

        Font.loadFont(Main.class.getResourceAsStream("SuperMario256.ttf"), 12);
        // Debugging
//        System.out.println(Main.class.getResource("SuperMario256.ttf").toExternalForm());
//        System.out.println(Font.loadFont(Main.class.getResourceAsStream("SuperMario256.ttf"), 12));
//        Font.getFamilies().forEach(System.out::println);

        stage.setWidth(900);
        stage.setHeight(600);

        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
