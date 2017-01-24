package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;


public class BlockSoft extends Block {

    public Level.B blockType = Level.B.SOFT;

    public BlockSoft(Context context){
        super(context);

        setImageResource(R.drawable.groundrost1);
    }

    public void blockKlicked(){

    }

    public Level.B getBlockType(){
        return blockType;
    }

}
