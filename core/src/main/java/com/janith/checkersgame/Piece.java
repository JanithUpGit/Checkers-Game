package com.janith.checkersgame;

public class Piece {
    public String team ;
    public boolean superPrivilege ;
    public Piece(String team) {
        this.team = team;
    }
    @Override
    public String toString() {
        return "Piece";
    }
}
