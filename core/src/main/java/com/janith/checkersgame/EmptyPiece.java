package com.janith.checkersgame;

public class EmptyPiece extends Piece {

    public EmptyPiece() {
        super("none");
    }

    @Override
    public String toString() {
        return "Empty";
    }
}
