package org.lab.gamelogic.vectorspace;

public abstract class ThreeComponents implements XComponent, YComponent, ZComponent {
  public abstract int getX();
  public abstract int getY();
  public abstract int getZ();
}