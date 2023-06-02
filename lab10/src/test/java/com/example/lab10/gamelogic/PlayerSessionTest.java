package com.example.lab10.gamelogic;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class PlayerSessionTest {
    @Test
    public void testSerialize() throws IOException, ClassNotFoundException {
        Scanner read = new Scanner(new File(
                "defaultMap.txt"
        ), StandardCharsets.UTF_8);
//        read.useDelimiter(" ");

        String text = "";
        List<List<Integer>> map = new ArrayList<>();
        text = Map.readMapFromScanner(read, map);
        Map.setDefaultPositions(text);

        PlayerSession playerSession = new PlayerSession("Test", Color.RED);

        FileOutputStream fileOutputStream
                = new FileOutputStream("./testSerialize.txt");
        ObjectOutputStream objectOutputStream
                = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(playerSession);
        objectOutputStream.flush();
        objectOutputStream.close();

        FileInputStream fileInputStream
                = new FileInputStream("./testSerialize.txt");
        ObjectInputStream objectInputStream
                = new ObjectInputStream(fileInputStream);

        PlayerSession playerSession1 = (PlayerSession) objectInputStream.readObject();
        objectInputStream.close();

        assertEquals(playerSession.getGameMap().toString(), playerSession1.getGameMap().toString());
    }


}