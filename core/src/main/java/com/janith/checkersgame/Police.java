package com.janith.checkersgame;

import java.util.ArrayList;
import java.util.List;

public class Police extends Tools{
    List<int[]>blast = new ArrayList<>();

    public void checkPoint1(Bord bord,String lastMovementDid ) {

        String victim;
        if (lastMovementDid.equals(bord.player1)) victim = bord.player2;
        else victim = bord.player1;
        collectAllCapture(bord, findCapturePieces(bord, lastMovementDid, victim), bord.lastMovementDid, victim);
    }

    public int[] checkPoint2(Bord bord,int[] lastMovementEndPosition,String lastMovementDid,boolean lastMovementIsCapture ){
        String victim;
        String came = "";
        if (lastMovementDid.equals(bord.player1)) victim = bord.player2;
        else victim = bord.player1;
        int[] delPiece = new int[0];
        if (!this.autoCaptures.isEmpty()){

            // last movement is not a capture
            if(!lastMovementIsCapture){
                List<int[]> bestCapture = findMaxLenList(autoCaptures,bord);
                int[] piecePosition = bestCapture.get(0);

                // if missing the capture then after delete the missed one
                if (bord.arrangedBord.get(piecePosition[0]).get(piecePosition[1]).toString().equals("Empty")){
                    piecePosition = lastMovementEndPosition;
                    bord.arrangedBord.get(piecePosition[0]).set(piecePosition[1], new EmptyPiece());
                    delPiece = piecePosition;
                    blast.add(delPiece);
                }
                else {
                    bord.arrangedBord.get(piecePosition[0]).set(piecePosition[1], new EmptyPiece());
                    delPiece = piecePosition;
                    blast.add(delPiece);
                }

            // last movement is a capture

            }else {

                boolean capture = false;

                // front
                if(bord.lastMovementPiecePosition[0] > bord.lastMovementEndPosition[0]) {
                    // left
                    if (bord.lastMovementPiecePosition[1] > bord.lastMovementEndPosition[1]) came = "bR";
                    // right
                    if (bord.lastMovementPiecePosition[1] < bord.lastMovementEndPosition[1]) came = "bL";
                }
                // back
                else  if(bord.lastMovementPiecePosition[0] < bord.lastMovementEndPosition[0]) {
                    // left
                    if (bord.lastMovementPiecePosition[1] > bord.lastMovementEndPosition[1]) came = "fR";
                    // right
                    if (bord.lastMovementPiecePosition[1] < bord.lastMovementEndPosition[1]) came = "fL";
                }
                int[] piecePosition = lastMovementEndPosition;

                // super piece

                if(bord.arrangedBord.get(piecePosition[0]).get(piecePosition[1]).superPrivilege)
                    capture = findCaptureForSuperPiece(bord, lastMovementEndPosition, lastMovementDid, victim,came);
                else
                    capture = findCaptureForNormalPiece(bord, lastMovementEndPosition,victim);
                if (capture) {
                    bord.arrangedBord.get(piecePosition[0]).set(piecePosition[1], new EmptyPiece());
                    delPiece = piecePosition;
                    blast.add(delPiece);
                }
            }
        }
        return delPiece;
    }
}
