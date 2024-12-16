package mino;

import java.awt.*;

public class Mino_I extends Mino {
    public Mino_I (){
        create(Color.cyan);
    }

    public void setXY (int x, int y){
        //
        // o 0 o o
        //
        block[0].x = x;
        block[0].y = y;
        block[1].x = block[0].x - Block.SIZE;
        block[1].y = block[0].y;
        block[2].x = block[0].x + Block.SIZE;
        block[2].y = block[0].y;
        block[3].x = block[0].x + Block.SIZE *2;
        block[3].y = block[0].y;
    }

    public void getDirection1(){
        //
        //  o 0 o o
        //

        tempB[0].x = block[0].x;
        tempB[0].y = block[0].y;
        tempB[1].x = block[0].x - Block.SIZE;
        tempB[1].y = block[0].y;
        tempB[2].x = block[0].x + Block.SIZE;
        tempB[2].y = block[0].y;
        tempB[3].x = block[0].x + Block.SIZE *2;
        tempB[3].y = block[0].y;

        updateXY(1);
    }
    public void getDirection2(){
        //  o
        //  0
        //  o
        //  o

        tempB[0].x = block[0].x;
        tempB[0].y = block[0].y;
        tempB[1].x = block[0].x;
        tempB[1].y = block[0].y - Block.SIZE;
        tempB[2].x = block[0].x;
        tempB[2].y = block[0].y + Block.SIZE;
        tempB[3].x = block[0].x;
        tempB[3].y = block[0].y + Block.SIZE*2;

        updateXY(2);
    }
    public void getDirection3(){
        getDirection1();
    }
    public void getDirection4(){
        getDirection2();
    }
}
