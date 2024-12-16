package main;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    final int FPS = 60;
    Thread gameThread;
    PlayManager playManager;
    public static Sound music = new Sound();
    public static Sound sound = new Sound();

    public static final int STATE_START = 0;
    public static final int STATE_PLAYING = 1;
    public static final int STATE_GAME_OVER = 2;
    public static final int STATE_MIME = 3;
    private int gameState = STATE_START;

    public GamePanel(){

        //Panel settings
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);
        //Implement KeyListener
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);

        playManager = new PlayManager();
    }

    public void launchGame(){
        gameThread = new Thread(this);
            gameThread.start();

            music.play(0, true);
            music.loop();

    }

    @Override
    public void run() {

        //GameLoop
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    private void update(){
        if (gameState == STATE_START) {
            if (KeyHandler.startPressed) {
                gameState = STATE_PLAYING;
                KeyHandler.startPressed = false;
                music.play(0, true);
                music.loop();
            }
        } else if (gameState == STATE_PLAYING) {
            if (!KeyHandler.pausePressed && !playManager.game_over) {
                playManager.update();
            }
            if (playManager.game_over) {
                gameState = STATE_GAME_OVER;
            }
        } else if ((gameState == STATE_GAME_OVER && KeyHandler.restartPressed)) {
            restartGame();
        }
        if (KeyHandler.mimePressed) {
            gameState = STATE_MIME;
        }
    }

    public void restartGame() {
        playManager = new PlayManager();
        PlayManager.staticBlocks = new ArrayList<>();
        KeyHandler.restartPressed = false;
        gameState = STATE_PLAYING;
        playManager.setDropInterval();
        launchGame();
    }



    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (gameState == STATE_START) {
            music.stop();
            drawStartScreen(g2);
        } else if (gameState == STATE_PLAYING) {
            playManager.draw(g2);
        } else if (gameState == STATE_GAME_OVER) {
            playManager.draw(g2);
            drawGameOverScreen(g2);
        }else if (gameState == STATE_MIME) {
            drawMimeScreen(g2);
        }
    }

    private void drawStartScreen(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.setFont(new Font("Arial", Font.BOLD, 50));
        g2.drawString("Welcome to Nikola's Tetris!", WIDTH / 2 - 300, HEIGHT / 2 - 50);
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.drawString("Press ENTER to Start", WIDTH / 2 - 150, HEIGHT / 2 + 50);
    }

    private void drawGameOverScreen(Graphics2D g2) {
        g2.setColor(Color.black);
        g2.fillRect((WIDTH - PlayManager.right_x + PlayManager.left_x)/2, HEIGHT / 2 - 100, PlayManager.right_x - PlayManager.left_x, 175);
        g2.setColor(Color.red);
        g2.setFont(new Font("Arial", Font.BOLD, 49));
        g2.drawString("GAME OVER", WIDTH / 2 - 150, HEIGHT / 2 - 50);
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.drawString("Press R to Restart", WIDTH / 2 - 125, HEIGHT / 2 + 50);
    }
    private void drawMimeScreen(Graphics2D g2) {
        Color mimePink = new Color(255, 0, 156, 255);
        g2.setColor(mimePink);
        g2.setFont(new Font("Arial", Font.BOLD, 200));
        g2.drawString("MIME", WIDTH / 2 - 200, HEIGHT / 2 +100);
    }
}
