package com.example.lab10.controller;

import com.example.lab10.Constants;
import com.example.lab10.Main;
import com.example.lab10.gamelogic.Entity;
import com.example.lab10.gamelogic.Map;
import com.example.lab10.gamelogic.PlayerSession;
import com.example.lab10.gamelogic.Position;
import com.example.lab10.gamelogic.entities.Obstacle;
import com.example.lab10.gamelogic.entities.Wall;
import com.example.lab10.gamelogic.movement.Direction;
import com.example.lab10.model.GameHolder;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML
    private BorderPane mainBorderContainer;

    @FXML
    private AnchorPane  centerAnchorContainer;
    @FXML
    private AnchorPane gameModeSelectionContainer;

    @FXML
    private ScrollPane scrollCanvasContainer;

    @FXML AnchorPane gameCanvasAnchorContainer;

    @FXML
    private Canvas gameCanvas;

    @FXML
    private VBox topBar, bottomBar;

    @FXML
    public void initialize() {

//        Stage stage = (Stage) mainBorderContainer.getScene().getWindow();
        GameHolder holder = GameHolder.getInstance();

        // Set main background for canvas area
        // Retrieve blue sky wallpaper from resource folder and set it as the background
        Image canvasBackground = new Image(Main.class.getResourceAsStream("images/backgroundSky.jpeg"));
        BackgroundImage bImg = new BackgroundImage(canvasBackground,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        mainBorderContainer.setStyle("-fx-background-color: #5eb1f7;");
        gameCanvasAnchorContainer.setBackground(bGround);


    }

    @FXML
    private void singlePlayerMode() throws IOException {
        Stage thisStage = (Stage) mainBorderContainer.getScene().getWindow();
        GameHolder holder = GameHolder.getInstance();

        Stage confirmExitModal = showMapSelectionModal(thisStage);
        confirmExitModal.showAndWait();

        Stage player = playerInitialize(thisStage);
        player.showAndWait();

        holder.setPlayState(true);

        gameModeSelectionContainer.setVisible(false);
        scrollCanvasContainer.setVisible(true);

        gameCanvas.setWidth(75 * Constants.canvasColCount);
        gameCanvas.setHeight(75 * Constants.canvasRowCount);
        final GraphicsContext gc = gameCanvas.getGraphicsContext2D();

        PlayerSession pS = new PlayerSession("Test", Color.RED);
        holder.playerSessionList.add(pS);

        Scene scene = mainBorderContainer.getScene();
        scene.setOnKeyPressed(e -> {
            gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
            if (e.getCode() == KeyCode.A) {
                holder.playerSessionList.get(0).move(Direction.left);
                drawCanvas();
            }
            if (e.getCode() == KeyCode.W) {
                holder.playerSessionList.get(0).move(Direction.up);
                drawCanvas();
            }
            if (e.getCode() == KeyCode.S) {
                holder.playerSessionList.get(0).move(Direction.down);
                drawCanvas();
            }if (e.getCode() == KeyCode.D) {
                holder.playerSessionList.get(0).move(Direction.right);
                drawCanvas();
            }

        });
    }

    @FXML
    private void twoPlayerMode() {

    }

    @FXML
    private void safeExitGame(ActionEvent event) throws IOException {
        Stage thisStage = (Stage) mainBorderContainer.getScene().getWindow();
        // 1. Ask if user actually wants to exit the game
        //  2yes. Check if game in session
        //  2no. Return and close modal window
        // 3. Popup window, where to save game file
        // 4. Closing procedures
        // 5. Exit from the program

        Stage confirmExitModal = showConfirmExitModal(thisStage);
        confirmExitModal.show();
    }

    public Stage showMapSelectionModal(Stage owner) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MapSelector.fxml"));
        Stage stage = initModal(fxmlLoader);
        stage.initOwner(owner);
        MapSelectorController controller = fxmlLoader.getController();

        stage.setTitle("Input a map");

        return stage;
    }

    public Stage playerInitialize(Stage owner) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PlayerName.fxml"));
        Stage stage = initModal(fxmlLoader);
        stage.initOwner(owner);
        PlayerNameController controller = fxmlLoader.getController();

        stage.setTitle("Player creation");

        return stage;
    }

    public Stage showConfirmExitModal(Stage owner) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Modal1.fxml"));
        Stage stage = initModal(fxmlLoader);
        stage.initOwner(owner);
        Modal1Controller controller = fxmlLoader.getController();
        controller.init("Confirm exit?", "Yes", "No",
                () -> {
                    // TODO: Check game status, ask if user wants to save if game is mid-session
                    // NOTE: Saving a mid-session game, saves a game ROM, it has nothing to do with the game map

                    GameHolder holder = GameHolder.getInstance();
                    if(holder.getPlayState()) {
                        Stage showSaveGameModal = null;
                        try {
                            showSaveGameModal = showSaveGameModal(stage);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        showSaveGameModal.show();
                    } else {
                        Platform.exit();
                        System.exit(0);
                    }
                },
                stage::close);

        return stage;
    }

    public Stage showSaveGameModal(Stage owner) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Modal1.fxml"));
        Stage stage = initModal(fxmlLoader);
        stage.initOwner(owner);
        Modal1Controller controller = fxmlLoader.getController();

        controller.init("Save game file?", "Yes", "No",
                stage::close,
                () -> {
                    Platform.exit();
                    System.exit(0);
                });

        return stage;
    }

    public Stage initModal(FXMLLoader fxmlLoader)  throws IOException {
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(
                new Scene(fxmlLoader.load())
        );

        return stage;
    }

    public void drawCanvas() {
        drawStaticLayer();
        drawDynamicLayer();
    }

    public void drawStaticLayer() {
        final GraphicsContext gc = gameCanvas.getGraphicsContext2D();

        List<Position> map = GameHolder.getInstance().playerSessionList.get(0).getGameMap().getMapPositions();
        List<Position> _map = GameHolder.getInstance().playerSessionList.get(0).getGameMap()._mapPositions;

        for(Position pos: _map) {
            if(pos.peak() instanceof Obstacle && GameHolder.getInstance().hasPlayer(pos)) {
                gc.drawImage(pos.peak().getImage(), pos.getCol()*75, pos.getRow()*75 + (75-75/6), 75, 75/6);
            } else {
                gc.drawImage(pos.peak().getImage(), pos.getCol()*75, pos.getRow()*75, 75, 75);
            }
        }
    }

    public void drawDynamicLayer() {
        final GraphicsContext gc = gameCanvas.getGraphicsContext2D();

        List<Color>[][] stackColor = new ArrayList[Constants.canvasRowCount][Constants.canvasColCount];
        List<Entity>[][] stack = new ArrayList[Constants.canvasRowCount][Constants.canvasColCount];
        GameHolder gameHolder = GameHolder.getInstance();

        for (int i = 0; i < Constants.canvasRowCount; i++) {
            for (int j = 0; j < Constants.canvasColCount; j++) {
                stackColor[i][j] = new ArrayList<>();
                stack[i][j] = new ArrayList<>();
            }
        }

        for(PlayerSession pS: gameHolder.playerSessionList) {
            for(Position pos : pS.getGameMap().getMapPositions()) {
                stackColor[pos.getRow()][pos.getCol()].add(pS.getPlayerColor());
                stack[pos.getRow()][pos.getCol()].add(pos.peak());
            }
        }

        for(int i = 0; i < stack.length; ++i) {
            List<Entity>[] row = stack[i];
            List<Color>[] colorRow = stackColor[i];

            for(int j = 0; j < row.length; ++j) {
                List<Entity> entityList = row[j];
                List<Color> colorList = colorRow[j];

                if(entityList != null) {
                    for (int z = 0; z < entityList.size(); ++z) {
                        Entity entity = entityList.get(z);
                        if(entity == null) continue;
                        gc.drawImage(entity.getImage(), entity.getPosition().getCol() * 75, entity.getPosition().getRow() * 75, 75, 75);

                        if(colorList != null) {
                            Color[] colorArr = colorList.toArray(new Color[0]);
                            drawBorderBox(gc, entity.getPosition().getCol()*75, entity.getPosition().getRow()*75, colorArr);
                        }
                    }
                }
            }
        }
    }

    void drawBorderBox(GraphicsContext gc, double x, double y, Color... colors) {
        Stop[] stops = new Stop[colors.length];

        double lineWidth = 3.0;
        gc.setLineWidth(lineWidth);

        int i = 0;
        for(Color c : colors) stops[i] = new Stop((1.0/(colors.length - 1))*((i++)*1.0), c);

        LinearGradient linearGradient = new LinearGradient(x, y, x + 75,
                y, false, null, stops);

        gc.setStroke(linearGradient);
        gc.strokeLine(x, y, x + 75, y);
        gc.setStroke(linearGradient);
        gc.strokeLine(x + 75, y, x + 75, y + 75);
        gc.setStroke(linearGradient);
        gc.strokeLine(x + 75, y + 75, x, y + 75);
        gc.setStroke(linearGradient);
        gc.strokeLine(x, y + 75, x, y);
    }
}