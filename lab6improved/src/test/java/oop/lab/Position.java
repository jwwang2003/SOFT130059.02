package oop.lab.gamelogic;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import oop.lab.gamelogic.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PositionTest {
  @Test
  void positionTest() {
    Position p = new Position(1, 1);

    assertEquals(p.toString(), "Position [x=1 y=1]");
  }
}