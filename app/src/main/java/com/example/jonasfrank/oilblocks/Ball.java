package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.Random;
import java.util.logging.Handler;

//import static com.example.jonasfrank.oilblocks.BlockBoostR.getBallChangeDirection;


public class Ball extends ImageView {

    public int screenWidth;
    public float blockSize;
    public Board board;
    public float startBallX;
    public float startBallY;
    public float moveX = 0;
    public float moveY = 0;

    public boolean notCollision;

    

    public Ball(Context context){
        super(context);

        setImageResource(R.drawable.ball);
    }

    public void setBall(int startScreenWidth, int blockNumberInRow, Board startBoard){

        screenWidth = startScreenWidth;
        blockSize = (float) startScreenWidth / blockNumberInRow;
        moveY = blockSize / 20;
        board = startBoard;
        setLayoutParams(new FrameLayout.LayoutParams((int)blockSize, (int) blockSize));
        startBallX = board.startPosX;
        startBallY = board.startPosY;

        setX(startBallX);
        setY(startBallY);
    }

    public void restartBall(){
        setX(startBallX);
        setY(startBallY);
    }

    public void ballMove() {
        float ballX;
        float ballY;
        boolean ifInsideboundarys = true;
        boolean notCollision = true;

        ballX = getX();
        ballY = getY();


        for(int i = 0; i < board.blockList.size(); i++) {
            Block thisBlock = board.blockList.get(i);

            Level.B thisBlockType = thisBlock.getBlockType();
            float thisBlockX = thisBlock.getX();
            float thisBlockY = thisBlock.getY();

            notCollision = blockCheck(ballX, ballY, thisBlockType, thisBlockX, thisBlockY, i);
            boosterCheck(ballX, ballY, thisBlockType, thisBlockX, thisBlockY, i);
            rampCheck(ballX, ballY, thisBlockType, thisBlockX, thisBlockY, i);
            if(notCollision == true){
                ifInsideboundarys = boundaryCheck(ballX, ballY);
            }
        }



        //Log.d("tag", "ball if if" + ifInsideboundarys + notCollision );


            setX(ballX + moveX);
            setY(ballY + moveY);

    }

    public boolean boundaryCheck(float x, float y){
        boolean insideboundarys;

        if(x + moveX > screenWidth - blockSize || x + moveX < 0 ){
            insideboundarys = false;
            moveX = moveX * -1;

            Log.d("tag", "ball krok kant" );

        }
        else if (y + moveY > screenWidth - blockSize || y + moveY < 0 ) {
            moveY = moveY * -1;
            insideboundarys = false;
        }
        else{
            insideboundarys = true;
        }

        return insideboundarys;
    }



    public boolean blockCheck(float x, float y, Level.B thisBlockType, float thisBlockX, float thisBlockY, int i){

            if(thisBlockType == Level.B.SOFT || thisBlockType == Level.B.SOLID) {
                if (x + blockSize > thisBlockX && x < thisBlockX + blockSize && y + blockSize > thisBlockY && y < thisBlockY + blockSize) {
                    Log.d("tag", "krock");
                    moveX = moveX * -1;
                    moveY = moveY * -1;
                    /*if (x + blockSize + 1 == thisBlockX) {
                        moveX = moveX * -1;
                        Log.d("tag", "krock uträkning");
                    }*/
                    notCollision = false;
                    //float lastMove = screenWidth - blockSize - y;
                    //setY(y + lastMove);
                    //break outerLoop;

                } else {
                    notCollision = true;
                }
            }
        return notCollision;
    }

    public void boosterCheck(float x, float y, Level.B thisBlockType, float thisBlockX, float thisBlockY, int i){
        Block thisBlock = board.blockList.get(i);

        if(thisBlockType == Level.B.BOOSTR) {
            if (x >= thisBlockX && x  <= thisBlockX && y >= thisBlockY && y <= thisBlockY) {

                Log.d("tag", "ball boost" + i + " " + thisBlock.getBallChangeDirection());
                moveX =  (blockSize / thisBlock.getBallChangeDirection());
            }
        }
    }

    public void rampCheck(float x, float y, Level.B thisBlockType, float thisBlockX, float thisBlockY, int i){
        Block thisBlock = board.blockList.get(i);

        if(thisBlockType == Level.B.RAMPUL || thisBlockType == Level.B.RAMPUR || thisBlockType == Level.B.RAMPDL || thisBlockType == Level.B.RAMPDR) {
            //Log.d("tag", "ball test" + i + " " + thisBlock.getBallChangeDirection() + " " + thisBlockX + " " + thisBlockY);
            if (x >= thisBlockX && x  <= thisBlockX && y >= thisBlockY && y <= thisBlockY) {
                //if (x > thisBlockX && x  < thisBlockX + blockSize && y > thisBlockY && y < thisBlockY + blockSize ) {
                //Log.d("tag", "ball test2" + i + " " + thisBlock.getBallChangeDirection());


                switch (thisBlockType){
                    case RAMPUL:
                        Log.d("tag", "ball UL" + i + " " + thisBlock.getBallChangeDirection() + moveX + " " + moveY);
                        if(moveX < moveY){

                            Log.d("tag", "ball UL X" + (thisBlock.getBallChangeDirection()));
                            moveX = moveX + (blockSize / thisBlock.getBallChangeDirection()) * -1;
                            Log.d("tag", "ball UL X" + moveX);
                            moveY = 0;
                        }else{
                            moveX = 0;
                            moveY = moveY + (blockSize / thisBlock.getBallChangeDirection()) * -1;
                        }
                        break;

                    case RAMPUR:
                        Log.d("tag", "ball UR" + i + " " + thisBlock.getBallChangeDirection());
                        if( moveX < moveY){
                            moveX = moveX + (blockSize / thisBlock.getBallChangeDirection());
                            moveY = 0;
                        }else{
                            moveX = 0;
                            moveY = moveY + (blockSize / thisBlock.getBallChangeDirection()) * -1;
                        }
                        break;

                    case RAMPDL:
                        Log.d("tag", "ball DL" + i + " " + thisBlock.getBallChangeDirection());
                        if(moveX > moveY){
                            moveX = 0;
                            moveY = moveY + (blockSize / thisBlock.getBallChangeDirection());
                        }else{
                            moveX = moveX + (blockSize / thisBlock.getBallChangeDirection()) * -1;
                            moveY = 0;
                        }
                        break;

                    case RAMPDR:
                        Log.d("tag", "ball DR" + i + " " + thisBlock.getBallChangeDirection());
                        if(moveX > moveY){
                            moveX = moveX + (blockSize / thisBlock.getBallChangeDirection());
                            moveY = 0;
                        }else{
                            moveX = 0;
                            moveY = moveY + (blockSize / thisBlock.getBallChangeDirection());
                        }
                        break;

                }
            }
        }
    }
}

