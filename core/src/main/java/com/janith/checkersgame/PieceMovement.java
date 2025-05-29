package com.janith.checkersgame;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class PieceMovement extends Checkers {

    private List<int[]> movements;
    ListTools listTools = new ListTools();

    // remove
    public Bezier<Vector2> bezierCurveR;
    public long startTimeR;
    public int pieceToMoveIndexR;
    public boolean removingPiece;
    public float durationR = 0.5f;
    public boolean removingPieceForComputer = false;
    public boolean removingPieceForPlayer = false;
    public boolean removingPieceForPlayerArrested = false;
    public boolean removingPieceForComputerArrested = false;



    // remove array
    public List<Vector2>removeArray;
    private int countRA = 0;
    public boolean removingPieceRA;


    // move
    public Bezier<Vector2> bezierCurve;
    public Vector2 endPosition;
    public long startTime;
    public float duration = 0.5f; // Duration in seconds
    public int pieceToMoveIndex;
    int[] endPositionIndex;
    int[] startPositionIndex;
    int delPositionIndex = -1;
    int count = 0;
    public boolean movingPiece;

    List<int[]>lastDelPieces;

    // remove method for player (no auto switch)
    public void startRemoveForPlayer(int pieceIndex,float duration) {
        Vector2 startPositionR = piecePositions.get(pieceIndex);
        this.bezierCurveR = new Bezier<>(startPositionR, new Vector2(startPositionR.x - 100, startPositionR.y - 200 ), new Vector2(startPositionR.x, 0));
        this.startTimeR = TimeUtils.nanoTime();
        this.pieceToMoveIndexR = pieceIndex;
        this.durationR = duration;
        this.removingPieceForPlayer = true;
        pieces.add(pieces.remove(pieceToMoveIndexR));
        piecePositions.add(piecePositions.remove(pieceToMoveIndexR));
        pieceToMoveIndexR = pieces.size() - 1;

    }
    public void removeForPlayer() {
        System.out.println("StartRemveingForPlayer");
        float elapsedTime = (TimeUtils.nanoTime() - startTimeR) / 1000000000.0f;
        float t = Math.min(1.0f, elapsedTime / durationR);
        Vector2 newPosition = new Vector2();
        bezierCurveR.valueAt(newPosition, t);
        piecePositions.get(pieceToMoveIndexR).set(newPosition.x, newPosition.y);
        if (t >= 1.0f) {
            // Movement finished, remove the piece
            pieces.remove(pieceToMoveIndexR);
            piecePositions.remove(pieceToMoveIndexR);
            removingPieceForPlayer = false;

            // to active player switch
            Checkers.computerMovementComplete = true;
            Checkers.SwitchClick = true;
        }
    }


    //remove method for computer (no auto switch)
    public void startRemoveForComputer(int pieceIndex,float duration) {

        Vector2 startPositionR = piecePositions.get(pieceIndex);
        this.bezierCurveR = new Bezier<>(startPositionR, new Vector2(startPositionR.x - 100, startPositionR.y + 200 ), new Vector2(startPositionR.x, 1000));
        this.startTimeR = TimeUtils.nanoTime();
        this.pieceToMoveIndexR = pieceIndex;
        this.duration = duration;
        this.removingPieceForComputer = true;
        pieces.add(pieces.remove(pieceToMoveIndexR));
        piecePositions.add(piecePositions.remove(pieceToMoveIndexR));
        pieceToMoveIndexR = pieces.size() - 1;

    }
    public void removeForComputer() {

        float elapsedTime = (TimeUtils.nanoTime() - startTimeR) / 1000000000.0f;
        float t = Math.min(1.0f, elapsedTime / duration);
        Vector2 newPosition = new Vector2();
        bezierCurveR.valueAt(newPosition, t);
        piecePositions.get(pieceToMoveIndexR).set(newPosition.x, newPosition.y);
        if (t >= 1.0f) {
            // Movement finished, remove the piece
            pieces.remove(pieceToMoveIndexR);
            piecePositions.remove(pieceToMoveIndexR);
            removingPieceForComputer = false;
            if(piecePositions.size()-1 < pieceToMoveIndex){
                pieceToMoveIndex -=1;
            }
            this.movingPiece = true;


        }
    }


    // remove method for player arrested piece remove (With auto switch)
    public void startRemoveForPlayerArrestedPieces(int pieceIndex,float duration) {

        //Checkers.playerMovementDone = true;

        Vector2 startPositionR = piecePositions.get(pieceIndex);
        this.bezierCurveR = new Bezier<>(startPositionR, new Vector2(startPositionR.x - 100, startPositionR.y - 200 ), new Vector2(startPositionR.x, 0));
        this.startTimeR = TimeUtils.nanoTime();
        this.pieceToMoveIndexR = pieceIndex;
        this.durationR = duration;
        this.removingPieceForPlayerArrested = true;
        pieces.add(pieces.remove(pieceToMoveIndexR));
        piecePositions.add(piecePositions.remove(pieceToMoveIndexR));
        pieceToMoveIndexR = pieces.size() - 1;

    }
    public void removeForPlayerArrestedPiece() {
        float elapsedTime = (TimeUtils.nanoTime() - startTimeR) / 1000000000.0f;
        float t = Math.min(1.0f, elapsedTime / durationR);
        Vector2 newPosition = new Vector2();
        bezierCurveR.valueAt(newPosition, t);
        piecePositions.get(pieceToMoveIndexR).set(newPosition.x, newPosition.y);
        if (t >= 1.0f) {
            // Movement finished, remove the piece
            pieces.remove(pieceToMoveIndexR);
            piecePositions.remove(pieceToMoveIndexR);
            removingPieceForPlayerArrested = false;

            Checkers.computerMovementDone = false;
            //Checkers.playerMovementDone = true;
        }
    }



    // remove method for computer arrested piece remove (With auto switch)
    public void startRemoveForComputerArrestedPieces(int pieceIndex,float duration) {

        Vector2 startPositionR = piecePositions.get(pieceIndex);
        this.bezierCurveR = new Bezier<>(startPositionR, new Vector2(startPositionR.x - 100, startPositionR.y - 200 ), new Vector2(startPositionR.x, 0));
        this.startTimeR = TimeUtils.nanoTime();
        this.pieceToMoveIndexR = pieceIndex;
        this.durationR = duration;
        this.removingPieceForComputerArrested = true;
        pieces.add(pieces.remove(pieceToMoveIndexR));
        piecePositions.add(piecePositions.remove(pieceToMoveIndexR));
        pieceToMoveIndexR = pieces.size() - 1;

    }
    public void removeForComputerArrestedPiece() {

        float elapsedTime = (TimeUtils.nanoTime() - startTimeR) / 1000000000.0f;
        float t = Math.min(1.0f, elapsedTime / durationR);
        Vector2 newPosition = new Vector2();
        bezierCurveR.valueAt(newPosition, t);
        piecePositions.get(pieceToMoveIndexR).set(newPosition.x, newPosition.y);
        if (t >= 1.0f) {
            // Movement finished, remove the piece
            System.out.println("Movement finished, remove the piece");
            pieces.remove(pieceToMoveIndexR);
            piecePositions.remove(pieceToMoveIndexR);
            removingPieceForComputerArrested = false;

            Checkers.playerMovementDone = false;
            Checkers.computerMovementDone = true;
            Checkers.lastMovementIsACapture = false;
            Checkers.computerMovementComplete = true;

        }
    }


    // remove method for common use (no auto switch)v
    public void startRemove(int pieceIndex,float duration) {
        Vector2 startPositionR = piecePositions.get(pieceIndex);
        this.bezierCurveR = new Bezier<>(startPositionR, new Vector2(startPositionR.x - 100, startPositionR.y - 200 ), new Vector2(startPositionR.x, 0));
        this.startTimeR = TimeUtils.nanoTime();
        this.pieceToMoveIndexR = pieceIndex;
        this.durationR = duration;
        this.removingPiece = true;
        pieces.add(pieces.remove(pieceToMoveIndexR));
        piecePositions.add(piecePositions.remove(pieceToMoveIndexR));
        pieceToMoveIndexR = pieces.size() - 1;

    }
    public void remove() {
        float elapsedTime = (TimeUtils.nanoTime() - startTimeR) / 1000000000.0f;
        float t = Math.min(1.0f, elapsedTime / durationR);
        Vector2 newPosition = new Vector2();
        bezierCurveR.valueAt(newPosition, t);
        piecePositions.get(pieceToMoveIndexR).set(newPosition.x, newPosition.y);
        if (t >= 1.0f) {
            // Movement finished, remove the piece
            pieces.remove(pieceToMoveIndexR);
            piecePositions.remove(pieceToMoveIndexR);
            removingPiece = false;

            Checkers.computerMovementComplete = true; // ??
        }
    }



    // remove array method for player
    public void startRemoveArrayForPlayer(float duration) {
        //Checkers.playerMovementDone = true;
        // make piece on top


        Vector2 capture = removeArray.get(countRA);

        Checkers.lastCapturePieceIndex = listTools.findPosition2D(capture, piecePositions);

        Vector2 startPositionR = piecePositions.get(Checkers.lastCapturePieceIndex);

        this.bezierCurveR = new Bezier<>(startPositionR, new Vector2(startPositionR.x - 100, startPositionR.y - 200), new Vector2(startPositionR.x, 0));

        this.startTimeR = TimeUtils.nanoTime();
        this.pieceToMoveIndexR = Checkers.lastCapturePieceIndex;

        this.duration = duration;
        this.removingPieceRA = true;

        pieces.add(pieces.remove(pieceToMoveIndexR));

        piecePositions.add(piecePositions.remove(pieceToMoveIndexR));

        pieceToMoveIndexR = pieces.size() - 1;

        countRA++;



    }
    public void removeArrayForPlayer() {
        float elapsedTime = (TimeUtils.nanoTime() - startTimeR) / 1000000000.0f;
        float t = Math.min(1.0f, elapsedTime / duration);
        Vector2 newPosition = new Vector2();
        bezierCurveR.valueAt(newPosition, t);
        piecePositions.get(pieceToMoveIndexR).set(newPosition.x, newPosition.y);
        if (t >= 1.0f) {
            // Movement finished, remove the piece

            pieces.remove(pieceToMoveIndexR);
            piecePositions.remove(pieceToMoveIndexR);

            this.removingPieceRA = false;
            if(countRA < removeArray.size()) {
                startRemoveArrayForPlayer(duration);
            }else {

                removeArray = new ArrayList<>();
                countRA = 0;
                System.out.println("time"+t);

            }
//
            Checkers.computerMovementComplete = true;
            Checkers.SwitchClick = true;

        }
    }



    // move arrays method for computer
    public void startMove(List<int[]> movements,List<int[]>lastDelPieces ,float duration) {

//        Checkers.playerMovementDone = true;
        this.movements = movements;
        this.lastDelPieces = lastDelPieces;
        System.out.println(lastDelPieces);
        if(count == 0) {
            this.startPositionIndex = movements.get(0); // Changed from getFirst() to get(0)
        }
        Vector2 startPosition = bordPositions.get(startPositionIndex[0]).get(startPositionIndex[1]);
        pieceToMoveIndex = listTools.findPosition2D(startPosition, piecePositions);

        pieces.add(pieces.remove(pieceToMoveIndex));
        piecePositions.add(piecePositions.remove(pieceToMoveIndex));
        pieceToMoveIndex = pieces.size() - 1;


        this.endPositionIndex = movements.get(count + 1);
        endPosition = bordPositions.get(endPositionIndex[0]).get(endPositionIndex[1]);
        if (listTools.findPositionCheck2D(endPosition, piecePositions)) {

            this.delPositionIndex = listTools.findPosition2D(endPosition, piecePositions);
            this.endPositionIndex = new int[]{
                (2 * endPositionIndex[0]) - startPositionIndex[0],
                (2 * endPositionIndex[1]) - startPositionIndex[1]
            };
            this.endPosition = bordPositions.get(endPositionIndex[0]).get(endPositionIndex[1]);

        }else if (!lastDelPieces.isEmpty()) {

            int[] ps = lastDelPieces.get(0);
            Vector2 delPiecePosition = bordPositions.get(ps[0]).get(ps[1]);
            this.delPositionIndex = listTools.findPosition2D(delPiecePosition, piecePositions);
            lastDelPieces.remove(0);
        }
        this.bezierCurve = new Bezier<>(startPosition,
            new Vector2(startPosition.x - 50, startPosition.y + 50),
            this.endPosition);

        this.startTime = TimeUtils.nanoTime();
        this.duration = duration;
        this.movingPiece = true;
        this.count++;

        System.out.println("startMove method done ");


    }

    public void move() {

        //System.out.println(pieceToMoveIndex+ " -"+ (piecePositions.size()-1));
        try {

            float elapsedTime = (TimeUtils.nanoTime() - startTime) / 1000000000.0f;
            float t = Math.min(1.0f, elapsedTime / duration);
            Vector2 newPosition = new Vector2();
            bezierCurve.valueAt(newPosition, t);
            piecePositions.get(pieceToMoveIndex).set(newPosition.x, newPosition.y);


            if (t >= 1.0f) {
                System.out.println("1.0f time completed");
                if(bordPositions.size()-1 == listTools.findPosition3D(newPosition,bordPositions)[0] ) {
                    int index = listTools.findPosition2D(newPosition, piecePositions);
                    Checkers.pieces.set(index, new PieceMaker().pieceMaker("white_super_piece.png", "white"));
                }
                if (delPositionIndex >= 0) {
                    System.out.println("restart remove");
                    startRemoveForComputer(delPositionIndex, 0.5f);
                    delPositionIndex = -1; // Avoid restarting removal if it has been started

                    this.movingPiece = false;
                } else {

                    if (movements.size() - 1 > count) {
                        System.out.println("move method start remove");
                        this.startPositionIndex = new int[]{endPositionIndex[0], endPositionIndex[1]};
                        startMove(movements,lastDelPieces, 0.5f);

                    } else {
                        System.out.println("move method check arrested piece");
                        this.count = 0;
                        this.movingPiece = false;

                        if(Checkers.arrestedPiecePositionForComputer.length != 0){
                            int index = listTools.findPosition2D(Checkers.bordPositions.get(Checkers.arrestedPiecePositionForComputer[0]).get(Checkers.arrestedPiecePositionForComputer[1]),Checkers.piecePositions);
                            startRemoveForComputerArrestedPieces(index,0.5f);
                            System.out.println("arrested piece remove call computer");
                            Checkers.arrestedPiecePositionForComputer = new int[2];


                        }else {
                            System.out.println("move method arrested piece not found");
                            Checkers.computerMovementComplete = true;
                            Checkers.computerMovementDone = true;
                            Checkers.playerMovementDone = false;
                            Checkers.lastMovementIsACapture = false;

                        }
                    }
                }
                System.out.println("move method done");
            }
        }catch (Exception e){
            System.out.println(e);

        }
    }

}
