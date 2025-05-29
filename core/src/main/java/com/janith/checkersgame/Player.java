package com.janith.checkersgame;

import java.util.List;

public class Player extends PlayBord{
    String playerName ;

    public Player(String playerName) {
        this.playerName = playerName;
    }

    public void input(List<int[]> movement,Bord bord) throws Exception {
        try {
            arrayMove(bord,bord.player2,movement);
        }catch (Exception e){
            bord.loadBordFromJson(bord.saveBordJson);
            throw e;
        }
    }
}
