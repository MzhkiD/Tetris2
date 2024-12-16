package mino;

import java.awt.*;

public class Mino_L extends Mino {

    public Mino_L (){
        create(Color.orange);
    }

    public void setXY (int x, int y){
        // o
        // 0
        // o o
        block[0].x = x;
        block[0].y = y;
        block[1].x = block[0].x;
        block[1].y = block[0].y - Block.SIZE;
        block[2].x = block[0].x;
        block[2].y = block[0].y + Block.SIZE;
        block[3].x = block[0].x + Block.SIZE;
        block[3].y = block[0].y + Block.SIZE;
    }

    public void getDirection1(){
        // o
        // 0
        // o o

        tempB[0].x = block[0].x;
        tempB[0].y = block[0].y;
        tempB[1].x = block[0].x;
        tempB[1].y = block[0].y - Block.SIZE;
        tempB[2].x = block[0].x;
        tempB[2].y = block[0].y + Block.SIZE;
        tempB[3].x = block[0].x + Block.SIZE;
        tempB[3].y = block[0].y + Block.SIZE;

        updateXY(1);
    }
    public void getDirection2(){
        //
        // o 0 o
        // o

        tempB[0].x = block[0].x;
        tempB[0].y = block[0].y;
        tempB[1].x = block[0].x + Block.SIZE;
        tempB[1].y = block[0].y;
        tempB[2].x = block[0].x - Block.SIZE;
        tempB[2].y = block[0].y;
        tempB[3].x = block[0].x - Block.SIZE;
        tempB[3].y = block[0].y + Block.SIZE;

        updateXY(2);
    }
    public void getDirection3(){
        // o o
        //   0
        //   o

        tempB[0].x = block[0].x;
        tempB[0].y = block[0].y;
        tempB[1].x = block[0].x;
        tempB[1].y = block[0].y + Block.SIZE;
        tempB[2].x = block[0].x;
        tempB[2].y = block[0].y - Block.SIZE;
        tempB[3].x = block[0].x - Block.SIZE;
        tempB[3].y = block[0].y - Block.SIZE;

        updateXY(3);
    }
    public void getDirection4(){
        //    o
        //o 0 o
        //

        tempB[0].x = block[0].x;
        tempB[0].y = block[0].y;
        tempB[1].x = block[0].x - Block.SIZE;
        tempB[1].y = block[0].y;
        tempB[2].x = block[0].x + Block.SIZE;
        tempB[2].y = block[0].y;
        tempB[3].x = block[0].x + Block.SIZE;
        tempB[3].y = block[0].y - Block.SIZE;

        updateXY(4);
    }
}
