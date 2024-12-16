package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public static boolean upPressed, downPressed, leftPressed, rightPressed, pausePressed, holdPressed, hardDropPressed, restartPressed, zPressed, startPressed, mimePressed;

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            if(pausePressed){
                pausePressed = false;
                GamePanel.music.play(0,true);
                GamePanel.music.loop();
            }
            else{
                pausePressed = true;
                GamePanel.music.stop();
            }
        }
        if (keyCode == KeyEvent.VK_C) {
            holdPressed = true;
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            hardDropPressed = true;
            GamePanel.sound.play(4,true);
        }
        if (keyCode == KeyEvent.VK_Z) {
            zPressed = true;
        }
        if (keyCode == KeyEvent.VK_R) {
            restartPressed = true;

            System.out.println("restart");
        }
        if (keyCode == KeyEvent.VK_ENTER) {
            startPressed = true;
        }
        if (keyCode == KeyEvent.VK_M) {
            mimePressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
