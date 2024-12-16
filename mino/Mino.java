package mino;

import main.GamePanel;
import main.KeyHandler;
import main.PlayManager;

import java.awt.*;

public abstract class Mino {

    public Block[] block = new Block[4];
    public Block[] tempB = new Block[4];
    int autoDropCounter = 0;
    public int direction = 1;
    boolean leftCollision, rightCollision,bottomCollision;
    public boolean active = true;
    public boolean deactivating;
    int deactivateCounter = 0;


    public void create(Color color) {
        block[0] = new Block(color);
        block[1] = new Block(color);
        block[2] = new Block(color);
        block[3] = new Block(color);
        tempB[0] = new Block(color);
        tempB[1] = new Block(color);
        tempB[2] = new Block(color);
        tempB[3] = new Block(color);
    }

    public void setXY(int x, int y) {}
    public void updateXY(int direction){

        checkRotationCollision();

        if(!leftCollision && !rightCollision && !bottomCollision){
            this.direction = direction;
            block[0].x = tempB[0].x;
            block[0].y = tempB[0].y;
            block[1].x = tempB[1].x;
            block[1].y = tempB[1].y;
            block[2].x = tempB[2].x;
            block[2].y = tempB[2].y;
            block[3].x = tempB[3].x;
            block[3].y = tempB[3].y;
        }

    }
    public void getDirection1(){}
    public void getDirection2(){}
    public void getDirection3(){}
    public void getDirection4(){}
    public void checkMovementCollision(){
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        checkBlockCollision();

        //Left
        for (Block value : block) {
            if (value.x == PlayManager.left_x) {
                leftCollision = true;
                break;
            }
        }
        //Right
        for (Block value : block) {
            if (value.x + Block.SIZE == PlayManager.right_x) {
                rightCollision = true;
                break;
            }
        }
        //Bottom
        for (Block value : block) {
            if (value.y + Block.SIZE == PlayManager.bottom_y) {
                bottomCollision = true;
                break;
            }
        }

    }
    public void checkRotationCollision(){
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        checkBlockCollision();

        //Left
        for (int i = 0; i < block.length; i++) {
            if (tempB[i].x < PlayManager.left_x) {
                leftCollision = true;
                break;
            }
        }
        //Right
        for (int i = 0; i < block.length; i++) {
            if (tempB[i].x + Block.SIZE > PlayManager.right_x) {
                rightCollision = true;
                break;
            }
        }
        //Bottom
        for (int i = 0; i < block.length; i++) {
            if (tempB[i].y + Block.SIZE > PlayManager.bottom_y) {
                bottomCollision = true;
                break;
            }
        }
    }
    public void checkBlockCollision() {

        for (int i = 0; i < PlayManager.staticBlocks.size(); i++) {

            int targetX = PlayManager.staticBlocks.get(i).x;
            int targetY = PlayManager.staticBlocks.get(i).y;

            for (Block value : block) {
                if (value.y + Block.SIZE == targetY && value.x == targetX) {
                    bottomCollision = true;
                    break;
                }
            }

            for (Block value : block) {
                if (value.x - Block.SIZE == targetX && value.y == targetY) {
                    leftCollision = true;
                    break;
                }
            }

            for (Block value : block) {
                if (value.x + Block.SIZE == targetX && value.y == targetY) {
                    rightCollision = true;
                    break;
                }
            }

        }
    }
    public void update(){

        if (deactivating) {
            deactivating();
        }
        //Move the mino
        if (KeyHandler.upPressed){
            switch (direction){
                case 1:
                    getDirection2(); break;
                case 2:
                    getDirection3(); break;
                case 3:
                    getDirection4(); break;
                case 4:
                    getDirection1(); break;
            }
            KeyHandler.upPressed = false;
            GamePanel.sound.play(3,false);
        }
       if (KeyHandler.zPressed){
            if (direction == 1) getDirection4();
            else if (direction == 2) getDirection1();
            else if (direction == 3) getDirection2();
            else if (direction == 4) getDirection3();


            KeyHandler.zPressed = false;
            GamePanel.sound.play(3,false);
        }


        checkMovementCollision();

        if (KeyHandler.downPressed){
            if (!bottomCollision){
                block[0].y += Block.SIZE;
                block[1].y += Block.SIZE;
                block[2].y += Block.SIZE;
                block[3].y += Block.SIZE;

                autoDropCounter = 0;
            }

            KeyHandler.downPressed = false;
        }
        if (KeyHandler.leftPressed){
            if (!leftCollision){
                block[0].x -= Block.SIZE;
                block[1].x -= Block.SIZE;
                block[2].x -= Block.SIZE;
                block[3].x -= Block.SIZE;

                autoDropCounter = 0;
            }

            KeyHandler.leftPressed = false;
        }
        if (KeyHandler.rightPressed){
            if (!rightCollision){
                block[0].x += Block.SIZE;
                block[1].x += Block.SIZE;
                block[2].x += Block.SIZE;
                block[3].x += Block.SIZE;

                autoDropCounter = 0;
            }

            KeyHandler.rightPressed = false;
        }

        if (bottomCollision){
            if (!deactivating){
                GamePanel.sound.play(4,false);
            }
            deactivating = true;
        }
        else {
            autoDropCounter++;
            if(autoDropCounter == PlayManager.dropInterval){

                block[0].y += Block.SIZE;
                block[1].y += Block.SIZE;
                block[2].y += Block.SIZE;
                block[3].y += Block.SIZE;

                autoDropCounter = 0;
        }

            KeyHandler.upPressed = false;
        }
    }
    private void deactivating(){
        deactivateCounter++;
        if(deactivateCounter == 45){
            deactivateCounter = 0;
            checkMovementCollision();

            if (bottomCollision){
                active = false;
            }
        }
    }

    public boolean canMoveDown() {
        for (Block block : block) {
            if (block.y + Block.SIZE >= PlayManager.bottom_y || isBlockBelow(block)) {
                return false;
            }
        }
        return true;
    }

    public void moveDown() {
        for (Block block : block) {
            block.y += Block.SIZE;
        }
    }

    private boolean isBlockBelow(Block block) {
        for (Block staticBlock : PlayManager.staticBlocks) {
            if (staticBlock.x == block.x && staticBlock.y == block.y + Block.SIZE) {
                return true;
            }
        }
        return false;
    }

    public void draw(Graphics2D g2){

        int margin = 2;
        g2.setColor(block[0].color);
        g2.fillRect(block[0].x + margin, block[0].y + margin, Block.SIZE - (margin*2), Block.SIZE - (margin*2));
        g2.fillRect(block[1].x + margin, block[1].y + margin, Block.SIZE - (margin*2), Block.SIZE - (margin*2));
        g2.fillRect(block[2].x + margin, block[2].y + margin, Block.SIZE - (margin*2), Block.SIZE - (margin*2));
        g2.fillRect(block[3].x + margin, block[3].y + margin, Block.SIZE - (margin*2), Block.SIZE - (margin*2));
    }

}
