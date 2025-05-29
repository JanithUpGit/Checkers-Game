package com.janith.checkersgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
public class PieceMaker extends ApplicationAdapter {



    public List<List<Vector2>> getBordPositions() {
        return bordPositions;
    }

    private List<List<Vector2>> bordPositions = new ArrayList<>();
    private List<Sprite> pieces = new ArrayList<>();
    private List<Vector2> piecePositions = new ArrayList<>();




    public NamedSprite pieceMaker(String piece,String color) {
        Texture texture = new Texture(piece);
        NamedSprite sprite = new NamedSprite(texture);
        sprite.setSize(66, 66);
        sprite.setName(color);
        return sprite;
    }

    public  List<Vector2> PositionArrayMaker(){
        List<Vector2> row;
        int spaceY = 1000-116;
        int count = 12;
        boolean space = false;
        for (int y = 0; y < count;y++) {
            int spaceX = 49;
            row = new ArrayList<>();
            for (int x = 0; x < count;x++) {
                row.add( new Vector2(spaceX,spaceY ));
                if(!(y == 5 | y == 6)) {
                    if (y % 2 == 0 ) {
                        if( x % 2 == 0) piecePositions.add(new Vector2(spaceX, spaceY));
                    }else {
                        if( x % 2 != 0)piecePositions.add(new Vector2(spaceX, spaceY));
                    }
                }
                spaceX += 76;
            }
            bordPositions.add(row);

            spaceY-= 76;

        }
        return piecePositions;
    }

    public List<Sprite> piecesArrayMaker(){
        int count =60;
        for (int x = 0; x < count;x++ ) {
            if (x < count / 2) {
                pieces.add(pieceMaker("white_piece.png","white"));
            } else {
                pieces.add(pieceMaker("black_piece.png","black"));
            }
        }

        return pieces;
    }


}
