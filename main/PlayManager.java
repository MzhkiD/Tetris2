package main;

import mino.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class PlayManager {

    //Main play area
    final int WIDTH = 300;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    // Mino
    Mino current_mino;
    final int mino_start_x;
    final int mino_start_y;
    Mino new_mino;
    Mino new_mino2;
    Mino new_mino3;
    final int new_mino_x;
    final int new_mino_y;
    final int new_mino2_x;
    final int new_mino2_y;
    final int new_mino3_x;
    final int new_mino3_y;

    public static ArrayList<Block> staticBlocks = new ArrayList<>();

    // others
    public static int dropInterval = 48;
    boolean game_over;

    // Hold mino
    public boolean canHold = true;
    public Mino heldMino;


    //Effect
    boolean effectCounterOn;
    int effectCounter;
    ArrayList<Integer> effectY = new ArrayList<>();

    //Score
    private int level = 1;
    private int lines = 0;
    private long score = 0;

    private int totalBlocksInRow = 10;


    public PlayManager() {

        //Main Play Area Frame
        left_x = (GamePanel.WIDTH/2) - (WIDTH/2);
        right_x = left_x +WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        mino_start_x = left_x + (WIDTH/2) - Block.SIZE;
        mino_start_y = top_y + Block.SIZE;

        new_mino_x = right_x + 175;
        new_mino_y = top_y + 100;
        new_mino2_x = right_x + 175;
        new_mino2_y = top_y + 200;
        new_mino3_x = right_x + 175;
        new_mino3_y = top_y + 300;

        // Set starting mino
        current_mino = pickMino();
        current_mino.setXY(mino_start_x, mino_start_y);
        new_mino = pickMino();
        new_mino2 = pickMino();
        new_mino3 = pickMino();
        new_mino.setXY(new_mino_x, new_mino_y);
        new_mino2.setXY(new_mino2_x, new_mino2_y);
        new_mino3.setXY(new_mino3_x, new_mino3_y);
    }

   public void setDropInterval() {
        dropInterval = 48;
   }

    private Mino pickMino(){

        Mino mino = null;
        int i = new Random().nextInt(7);

        mino = switch (i) {
            case 0 -> new Mino_L();
            case 1 -> new Mino_J();
            case 2 -> new Mino_O();
            case 3 -> new Mino_S();
            case 4 -> new Mino_Z();
            case 5 -> new Mino_I();
            case 6 -> new Mino_T();
            default -> mino;
        };
        return mino;
    }

    public void update() {
        if (KeyHandler.holdPressed) {
            holdMino();
            KeyHandler.holdPressed = false; // Reset the key state to prevent repeated holds
        }

        if (KeyHandler.hardDropPressed) {
            hardDrop();
            KeyHandler.hardDropPressed = false; // Reset the key state to prevent repeated hard drops
        }

        if(!current_mino.active) {
            staticBlocks.add(current_mino.block[0]);
            staticBlocks.add(current_mino.block[1]);
            staticBlocks.add(current_mino.block[2]);
            staticBlocks.add(current_mino.block[3]);

            if (current_mino.block[0].x == mino_start_x && current_mino.block[0].y == mino_start_y) {
                game_over = true;
                GamePanel.music.stop();
                GamePanel.sound.play(2, false);
                System.out.println(game_over);
            }

            current_mino.deactivating = false;

            current_mino = new_mino;
            current_mino.setXY(mino_start_x, mino_start_y);
            new_mino = new_mino2;
            new_mino.setXY(new_mino_x, new_mino_y);
            new_mino2 = new_mino3;
            new_mino2.setXY(new_mino2_x, new_mino2_y);
            new_mino3 = pickMino();
            new_mino3.setXY(new_mino3_x, new_mino3_y);

            checkDelete();
            canHold = true;
        } else
            current_mino.update();
    }

    public void checkDelete(){
        int x = left_x;
        int y = top_y;
        int blockCount = 0;
        int lineCount = 0;

        while (x <= right_x && y < bottom_y) {

            for (Block staticBlock : staticBlocks) {
                if (staticBlock.x == x && staticBlock.y == y) {
                    blockCount++;
                }
            }

            x += Block.SIZE;

            if(x == right_x){

                if (blockCount == totalBlocksInRow){ // Define totalBlocksInRow elsewhere
                    effectCounterOn = true;
                    effectY.add(y);

                    int currentY = y;
                    staticBlocks.removeIf(block -> block.y == currentY);

                    lineCount++;
                    lines++;

                    for (Block staticBlock : staticBlocks) {
                        if (staticBlock.y < y) {
                            staticBlock.y += Block.SIZE;
                        }
                    }
                }
                blockCount = 0;
                x = left_x;
                y += Block.SIZE;
            }
        }

        int[] scoreMultiplier = {0, 100, 300, 500, 800};
        if (lineCount >= 1 && lineCount <= 4) {
            score += (long) scoreMultiplier[lineCount] * level * lineCount;
        }

        if ( lines!= 0 && lines % 10 == 0){
            level++;

            if(level <= 8) dropInterval-=5;
            else if (level == 9) dropInterval = 6;
            else if (level == 10) dropInterval = 5;
            else if (level == 13) dropInterval = 4;
            else if (level == 16) dropInterval = 3;
            else if (level == 19) dropInterval = 2;
            else if (level == 29) dropInterval = 1;

        }
    }

    public void holdMino(){
        if (canHold) {
            if (heldMino == null) {
                // If no Mino is held, store the current one and get a new one
                heldMino = current_mino;
                current_mino = new_mino;
                current_mino.setXY(mino_start_x, mino_start_y);
                new_mino = pickMino();
                new_mino.setXY(new_mino_x, new_mino_y);
            } else {
                // Swap the held Mino with the current one
                Mino temp = current_mino;
                current_mino = heldMino;
                heldMino = temp;
                current_mino.setXY(mino_start_x, mino_start_y);
            }
            // Disallow holding until the next drop
            canHold = false;
        }
    }

    public void hardDrop() {
        while (current_mino.canMoveDown()) {
            current_mino.moveDown();
        }
        // Lock the Mino in place
        current_mino.active = false;
        canHold = true; // Allow holding again after a hard drop
    }

    public void draw(Graphics2D g2) {

        // Draw Play Area Frame
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x-4, top_y-4, WIDTH+8, HEIGHT+8);

        //Draw the Next Mino Frame
        int x1 = right_x + 100;
        int y1 = top_y;
        g2.drawRect(x1, y1, 200, 400);
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
        g2.drawString("NEXT", x1+60, y1+60);

        // Draw the hold Mino frame
        int x2 = left_x - 300;
        int y2 = bottom_y - 200;
        g2.drawRect(x2, y2, 200, 200);
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
        g2.drawString("HELD", x2+60, y2+60);


        //Draw Score Frame
        x1 = left_x - 300;
        y1 = top_y;
        g2.drawRect(x1, y1, 200, 325);

        g2.drawString("LEVEL: ", x1+30, y1 + 250);
        g2.drawString(String.valueOf(level), x1+30, y1 + 300);
        g2.drawString("LINES: ", x1+30, y1 + 150);
        g2.drawString(String.valueOf(lines), x1+30, y1 + 200);
        g2.drawString("SCORE: ", x1+30, y1 + 50);
        g2.drawString(String.valueOf(score), x1+30, y1 + 100);



        //Draw current mino
        if (current_mino != null) {
            current_mino.draw(g2);
        }

        //Draw next mino
        new_mino.draw(g2);
        new_mino2.draw(g2);
        new_mino3.draw(g2);

        //Draw static blocks
        for (Block staticBlock : staticBlocks) {
            staticBlock.draw(g2);
        }

        //Draw effect
        if (effectCounterOn) {
            effectCounter++;

            g2.setColor(Color.red);
            for (Integer integer : effectY) {
                g2.fillRect(left_x, integer, WIDTH, Block.SIZE);
            }
            if (effectCounter == 10) {
                effectCounter = 0;
                effectY.clear();
                effectCounterOn = false;
            }
        }

        //Draw Pause game
        g2.setColor(Color.yellow);
        g2.setFont(g2.getFont().deriveFont(45f));
        if (game_over){
            x1 = left_x +10;
            y1 = top_y +320;
            g2.drawString("GAME OVER", x1, y1);
        }
        if (KeyHandler.pausePressed){
            x1 = left_x +50;
            y1 = top_y +320;
            g2.drawString("PAUSED", x1, y1);
        }

        if (heldMino != null) {
            heldMino.setXY(left_x - 225, bottom_y - 100); // Position inside the "HELD" frame
            heldMino.draw(g2);
        }

        //Draw the game title
        x1 = right_x + 100;
        y1 = bottom_y - 100;
        g2.setColor(Color.white);
        g2.setFont(new Font("Times New Roman", Font.ITALIC, 60));
        g2.drawString("Nikola's Tetris", x1, y1);
    }
}
