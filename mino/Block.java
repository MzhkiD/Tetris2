package mino;

import java.awt.*;

public class Block extends Rectangle {

    public int x, y;
    public static final int SIZE = 30;
    public Color color;

    public Block(Color color) {
        this.color = color;
    }
    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fillRect(x, y, SIZE, SIZE);
    }

}
