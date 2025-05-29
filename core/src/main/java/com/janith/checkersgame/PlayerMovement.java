package com.janith.checkersgame;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;



public class PlayerMovement extends PieceMaker{
    ListTools listTools = new ListTools();
    static List<int[]> movement = new ArrayList<>();
    public Vector2 superPieceSet = new Vector2();
    public Bezier<Vector2> bezierCurve;
    public long startTime;
    public float duration = 0.5f; // Duration in seconds
    public float removeDuration = 0.5f;
    public int pieceToMoveIndex;
    public static boolean movingPiece;
    public PieceMovement pieceMovement ;

    public boolean lastSuperPieceMovementIsCapture = false;
    public boolean thisIsTheFirstSuperPieceMovement = true;
    public Vector2 lastSuperEndPosition = new Vector2();

    public String lastSuperCameDirection = "";



    public void startMove(Vector2 start, Vector2 end, int pieceToMoveIndex){
        Checkers.playerMovementDone= true;
        this.bezierCurve = new Bezier<>(start, end);
        this.startTime = TimeUtils.nanoTime();
        this.duration = 1.0f;
        PlayerMovement.movingPiece = true;
        this.pieceToMoveIndex = pieceToMoveIndex;
        Checkers.computerMovementComplete = false;


        // check super position pass
        if(0 == listTools.findPosition3D(end,Checkers.bordPositions)[0] ) { // nextNextPosition
            superPieceSet = end;
        }


    }

    public void playerMove(){
        float elapsedTime = (TimeUtils.nanoTime() - startTime) / 1000000000.0f;
        float t = Math.min(1.0f, elapsedTime / duration);
        Vector2 newPosition = new Vector2();
        bezierCurve.valueAt(newPosition, t);
        Checkers.piecePositions.get(pieceToMoveIndex).set(newPosition.x, newPosition.y);
        if (t >= 1.0f) {
            PlayerMovement.movingPiece = false;

            // set super piece
            if(!superPieceSet.equals(new Vector2())){
                int index = listTools.findPosition2D(superPieceSet,Checkers.piecePositions);
                Checkers.pieces.set(index, new PieceMaker().pieceMaker("black_super_piece.png", "black_super"));
                superPieceSet = new Vector2();
            }


            // check have  arrested piece for remove or not
            if(Checkers.lastMovementIsACapture) {
//                Checkers.pieces.add(Checkers.pieces.remove(Checkers.lastCapturePieceIndex));
//                Checkers.piecePositions.add(Checkers.piecePositions.remove(Checkers.lastCapturePieceIndex));
//                Checkers.lastCapturePieceIndex = Checkers.pieces.size() - 1;
                System.out.println("startRemoveForPlayer");
                pieceMovement.startRemoveForPlayer(Checkers.lastCapturePieceIndex, 0.5f);

            }else {
                if(Checkers.arrestedPiecePositionForPlayer.length != 0){
                    int index = listTools.findPosition2D(Checkers.bordPositions.get(Checkers.arrestedPiecePositionForPlayer[0]).get(Checkers.arrestedPiecePositionForPlayer[1]),Checkers.piecePositions);
                    pieceMovement.startRemoveForPlayerArrestedPieces(index,removeDuration);
                    Checkers.arrestedPiecePositionForPlayer = new int[2];
                }else {

                    Checkers.playerMovementDone = true;
                    Checkers.computerMovementDone = false;
                    Checkers.computerMovementComplete = true;
                }

            }
        }


    }


    public List<int[]> move(Vector2 piecePos, Vector2 touchPos, NamedSprite piece, Vector2 draggedPosition, List<Vector2> piecePositions,List<Sprite> pieces,List<List<Vector2>> bordPositions ,PieceMovement pieceMovement){
        this.pieceMovement = pieceMovement;
        if(piece.getName().equals("black")) {
            if (draggedPosition.y < piecePos.y & draggedPosition.x > piecePos.x) {
                frontLeft(piecePos, touchPos, piece, draggedPosition, piecePositions, pieces, bordPositions, pieceMovement);
            } else if (draggedPosition.y < piecePos.y & draggedPosition.x < piecePos.x) {
                frontRight(piecePos, touchPos, piece, draggedPosition, piecePositions, pieces, bordPositions, pieceMovement);
            } else if (draggedPosition.y > piecePos.y & draggedPosition.x < piecePos.x) {
                backRight(piecePos, touchPos, piece, draggedPosition, piecePositions, pieces, bordPositions, pieceMovement);
            } else if (draggedPosition.y > piecePos.y & draggedPosition.x > piecePos.x) {
                backLeft(piecePos, touchPos, piece, draggedPosition, piecePositions, pieces, bordPositions, pieceMovement);
            }else {
                piecePos.set(draggedPosition);
            }
        }else if(piece.getName().equals("black_super")){
            if (draggedPosition.y < piecePos.y & draggedPosition.x > piecePos.x) {
                frontLeftSuper(piecePos, touchPos, piece, draggedPosition, piecePositions, pieces, bordPositions, pieceMovement);
            } else if (draggedPosition.y < piecePos.y & draggedPosition.x < piecePos.x) {
                frontRightSuper(piecePos, touchPos, piece, draggedPosition, piecePositions, pieces, bordPositions, pieceMovement);
            } else if (draggedPosition.y > piecePos.y & draggedPosition.x < piecePos.x) {
                backRightSuper(piecePos, touchPos, piece, draggedPosition, piecePositions, pieces, bordPositions, pieceMovement);
            } else if (draggedPosition.y > piecePos.y & draggedPosition.x > piecePos.x) {
                backLeftSuper(piecePos, touchPos, piece, draggedPosition, piecePositions, pieces, bordPositions, pieceMovement);
            }else {
                piecePos.set(draggedPosition);
            }
        }else{
            piecePos.set(draggedPosition);
        }
        System.out.println(movement);
        return movement;

    }



    public static Vector2 findNearestPoint(List<Vector2> points, Vector2 targetPoint) {
        Vector2 nearestPoint = null;
        float minDistance = Float.MAX_VALUE;

        for (Vector2 point : points) {
            float distance = targetPoint.dst(point);
            if (distance < minDistance) {
                minDistance = distance;
                nearestPoint = point;
            }
        }

        return nearestPoint;
    }



    public void frontLeftSuper(Vector2 piecePos, Vector2 touchPos, NamedSprite piece, Vector2 draggedPosition, List<Vector2> piecePositions, List<Sprite> pieces, List<List<Vector2>> bordPositions , PieceMovement pieceMovement){

        if(listTools.findPositionCheck3D(draggedPosition,bordPositions)) {
            List<int[]> movePositions = new ArrayList<>();
            int[] positionIndex = listTools.findPosition3D(draggedPosition, bordPositions);
            List<Vector2> movePiecePosition = new ArrayList<>();

            int countY = positionIndex[0] - 1; // **
            int countX = positionIndex[1] - 1; // **
            for (; countY >= 0 && countX >= 0; ) {
                movePositions.add(new int[]{countY, countX});
                movePiecePosition.add(bordPositions.get(countY).get(countX));
                countY--;
                countX--;
            }
            List<Vector2>captures = new ArrayList<>();
            Vector2 nextPosition = findNearestPoint(movePiecePosition, piecePos);
            boolean findWhitePiece = false;


            try{
                if(!lastSuperCameDirection.equals("backRight")){
                    if(!listTools.findPositionCheck2D(nextPosition,piecePositions)) {
                        for (Vector2 position : movePiecePosition) {
                            if (!position.equals(nextPosition)) {
                                if (listTools.findPositionCheck2D(position, piecePositions)) {
                                    NamedSprite p = (NamedSprite) pieces.get(listTools.findPosition2D(position, piecePositions));
                                    if (!(p.getName().equals("black") | p.getName().equals("black_super"))) {
                                        if (p.getName().equals("white") | p.getName().equals("white_super")) {  // update
                                            if (!findWhitePiece) {
                                                findWhitePiece = true;
                                                captures.add(position);
                                            } else {
                                                piecePos.set(draggedPosition);
                                                captures.clear();
                                                break;
                                            }
                                        }
                                    } else {
                                        piecePos.set(draggedPosition);
                                        captures.clear();
                                        break;
                                    }
                                } else {
                                    findWhitePiece = false;
                                }
                                //move
                            } else {

                                if(lastSuperEndPosition.equals(new Vector2())){
                                    System.out.println("new super movement");

                                    // pass the argument to main bord
                                    int[] index = listTools.findPosition3D(nextPosition,bordPositions);
                                    movement.add(positionIndex);
                                    movement.add(index);


                                    // capture
                                    if (!captures.isEmpty()) {

                                        // move piece
                                        piecePos.set(nextPosition);

                                        pieceMovement.removeArray = captures;
                                        pieceMovement.startRemoveArrayForPlayer(removeDuration);


                                        // stop auto switch
                                        Checkers.lastMovementIsACapture = true;
                                        Checkers.lastCaptureDonePiece = nextPosition;
                                        //Checkers.lastCapturePieceIndex = nextPiecePositionIndex;

                                        lastSuperEndPosition = nextPosition; //***
                                        lastSuperCameDirection = "frontLeft";

                                    }else{
                                        // Super free movement

                                        System.out.println("new super free movement check");

        //                              Checkers.pieces.add(Checkers.pieces.remove(Checkers.lastCapturePieceIndex));
        //                              Checkers.piecePositions.add(Checkers.piecePositions.remove(Checkers.lastCapturePieceIndex));
        //                              Checkers.lastCapturePieceIndex = Checkers.pieces.size() - 1;

                                        //piecePos.set(nextPosition);

                                        startMove(piecePos,nextPosition,listTools.findPosition2D(piecePos,piecePositions));
                                        lastSuperEndPosition = new Vector2();
                                        lastSuperCameDirection = "";

                                    }


                                    break;

                                }else if(lastSuperEndPosition.equals(draggedPosition)){
                                    System.out.println("not new super movement");
                                    if(!captures.isEmpty()){

                                        // move piece
                                        piecePos.set(nextPosition);

                                        // pass the argument to main bord
                                        int[] index = listTools.findPosition3D(nextPosition,bordPositions);
                                        movement.add(index);

                                        //capture
                                        pieceMovement.removeArray = captures;
                                        pieceMovement.startRemoveArrayForPlayer(removeDuration);
                                        Checkers.lastMovementIsACapture = true;
                                        Checkers.lastCaptureDonePiece = nextPosition;
                                        //Checkers.lastCapturePieceIndex = nextPiecePositionIndex;

                                        lastSuperEndPosition = nextPosition; //***
                                        lastSuperCameDirection = "frontLeft";

                                        break;
                                    }else {
                                        System.out.println("cant do free movement only capture");
                                        piecePos.set(draggedPosition);
                                        break;
                                    }
                                }
                                else {
                                    piecePos.set(draggedPosition);

                                    break;
                                }


                                // last nextpositon = now draggedposition ...

                            }
                        }

                    }else {
                        piecePos.set(draggedPosition);
                    }
                }else {
                    piecePos.set(draggedPosition);

                }
            }catch (Exception e){
                System.out.println("dragging error = "+e);
                piecePos.set(draggedPosition);
            }


        }else {
            Vector2 nearestPoint = null;
            float minDistance = Float.MAX_VALUE;


            for(List<Vector2>row : bordPositions){
                for (Vector2 point : row) {
                    float distance = draggedPosition.dst(point);
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearestPoint = point;
                    }
                }
            }
            piecePos.set(nearestPoint);
        }


    }

    public void frontRightSuper(Vector2 piecePos, Vector2 touchPos, NamedSprite piece, Vector2 draggedPosition, List<Vector2> piecePositions, List<Sprite> pieces, List<List<Vector2>> bordPositions , PieceMovement pieceMovement){

        if(listTools.findPositionCheck3D(draggedPosition,bordPositions)) {
            List<int[]> movePositions = new ArrayList<>();
            int[] positionIndex = listTools.findPosition3D(draggedPosition, bordPositions);
            List<Vector2> movePiecePosition = new ArrayList<>();

            int countY = positionIndex[0] - 1; // **
            int countX = positionIndex[1] + 1; // **
            for (; countY >= 0 && countX < bordPositions.size();) {
                movePositions.add(new int[]{countY, countX});
                movePiecePosition.add(bordPositions.get(countY).get(countX));
                countY--;
                countX++;
            }
            List<Vector2>captures = new ArrayList<>();
            Vector2 nextPosition = findNearestPoint(movePiecePosition, piecePos);
            boolean findWhitePiece = false;

            try{
                if(!lastSuperCameDirection.equals("backLeft")){
                    if(!listTools.findPositionCheck2D(nextPosition,piecePositions)) {
                        for (Vector2 position : movePiecePosition) {
                            if (!position.equals(nextPosition)) {
                                if (listTools.findPositionCheck2D(position, piecePositions)) {
                                    NamedSprite p = (NamedSprite) pieces.get(listTools.findPosition2D(position, piecePositions));
                                    if (!(p.getName().equals("black") | p.getName().equals("black_super"))) {
                                        if (p.getName().equals("white") | p.getName().equals("white_super")) {  // update
                                            if (!findWhitePiece) {
                                                findWhitePiece = true;
                                                captures.add(position);
                                            } else {
                                                piecePos.set(draggedPosition);
                                                captures.clear();
                                                break;
                                            }
                                        }
                                    } else {
                                        piecePos.set(draggedPosition);
                                        captures.clear();
                                        break;
                                    }
                                } else {
                                    findWhitePiece = false;
                                }
                                //move
                            } else {

                                if(lastSuperEndPosition.equals(new Vector2())){
                                    System.out.println("new super movement");

                                    // pass the argument to main bord
                                    int[] index = listTools.findPosition3D(nextPosition,bordPositions);
                                    movement.add(positionIndex);
                                    movement.add(index);


                                    // capture
                                    if (!captures.isEmpty()) {

                                        // move piece
                                        piecePos.set(nextPosition);

                                        pieceMovement.removeArray = captures;
                                        pieceMovement.startRemoveArrayForPlayer(removeDuration);

                                        // stop auto switch
                                        Checkers.lastMovementIsACapture = true;
                                        Checkers.lastCaptureDonePiece = nextPosition;
                                        //Checkers.lastCapturePieceIndex = nextPiecePositionIndex;

                                        lastSuperEndPosition = nextPosition; //***
                                        lastSuperCameDirection = "frontRight";

                                    }else{
                                        // Super free movement

                                        System.out.println("new super free movement check");
                                        startMove(piecePos,nextPosition,listTools.findPosition2D(piecePos,piecePositions));
                                        lastSuperEndPosition = new Vector2();
                                        lastSuperCameDirection = "";

                                    }


                                    break;

                                }else if(lastSuperEndPosition.equals(draggedPosition)){
                                    System.out.println("not new super movement");
                                    if(!captures.isEmpty()){

                                        // move piece
                                        piecePos.set(nextPosition);

                                        // pass the argument to main bord
                                        int[] index = listTools.findPosition3D(nextPosition,bordPositions);
                                        movement.add(index);

                                        //capture
                                        pieceMovement.removeArray = captures;
                                        pieceMovement.startRemoveArrayForPlayer(removeDuration);
                                        Checkers.lastMovementIsACapture = true;
                                        Checkers.lastCaptureDonePiece = nextPosition;

                                        lastSuperEndPosition = nextPosition; //***
                                        lastSuperCameDirection = "frontRight";

                                        break;
                                    }else {
                                        System.out.println("cant do free movement only capture");

                                        piecePos.set(draggedPosition);
                                        break;
                                    }
                                }
                                else {
                                    piecePos.set(draggedPosition);




                                    break;
                                }


                                // last nextpositon = now draggedposition ...

                            }
                        }

                    }else {
                        piecePos.set(draggedPosition);
                    }
                }else {
                    piecePos.set(draggedPosition);

                }
            }catch (Exception e){
                System.out.println("dragging error = "+e);
                piecePos.set(draggedPosition);
            }


        }else {
            Vector2 nearestPoint = null;
            float minDistance = Float.MAX_VALUE;


            for(List<Vector2>row : bordPositions){
                for (Vector2 point : row) {
                    float distance = draggedPosition.dst(point);
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearestPoint = point;
                    }
                }
            }
            piecePos.set(nearestPoint);
        }

    }

    public void backRightSuper(Vector2 piecePos, Vector2 touchPos, NamedSprite piece, Vector2 draggedPosition, List<Vector2> piecePositions, List<Sprite> pieces, List<List<Vector2>> bordPositions , PieceMovement pieceMovement){

        if(listTools.findPositionCheck3D(draggedPosition,bordPositions)) {
            List<int[]> movePositions = new ArrayList<>();
            int[] positionIndex = listTools.findPosition3D(draggedPosition, bordPositions);
            List<Vector2> movePiecePosition = new ArrayList<>();

            int countY = positionIndex[0] + 1; // **
            int countX = positionIndex[1] + 1; // **
            for (; countY <bordPositions.size() && countX < bordPositions.size(); ) { // **
                movePositions.add(new int[]{countY, countX});
                movePiecePosition.add(bordPositions.get(countY).get(countX));
                countY++; //**
                countX++; //**
            }
            List<Vector2>captures = new ArrayList<>();
            Vector2 nextPosition = findNearestPoint(movePiecePosition, piecePos);
            boolean findWhitePiece = false;


            try{
                if(!lastSuperCameDirection.equals("frontLeft")){
                    if(!listTools.findPositionCheck2D(nextPosition,piecePositions)) {
                        for (Vector2 position : movePiecePosition) {
                            if (!position.equals(nextPosition)) {
                                if (listTools.findPositionCheck2D(position, piecePositions)) {
                                    NamedSprite p = (NamedSprite) pieces.get(listTools.findPosition2D(position, piecePositions));
                                    if (!(p.getName().equals("black") | p.getName().equals("black_super"))) {
                                        if (p.getName().equals("white") | p.getName().equals("white_super")) {  // update
                                            if (!findWhitePiece) {
                                                findWhitePiece = true;
                                                captures.add(position);
                                            } else {
                                                piecePos.set(draggedPosition);
                                                captures.clear();
                                                break;
                                            }
                                        }
                                    } else {
                                        piecePos.set(draggedPosition);
                                        captures.clear();
                                        break;
                                    }
                                } else {
                                    findWhitePiece = false;
                                }
                                //move
                            } else {

                                if(lastSuperEndPosition.equals(new Vector2())){
                                    System.out.println("new super movement");

                                    // pass the argument to main bord
                                    int[] index = listTools.findPosition3D(nextPosition,bordPositions);
                                    movement.add(positionIndex);
                                    movement.add(index);


                                    // capture
                                    if (!captures.isEmpty()) {

                                        // move piece
                                        piecePos.set(nextPosition);

                                        pieceMovement.removeArray = captures;
                                        pieceMovement.startRemoveArrayForPlayer(removeDuration);

                                        // stop auto switch
                                        Checkers.lastMovementIsACapture = true;
                                        Checkers.lastCaptureDonePiece = nextPosition;
                                        //Checkers.lastCapturePieceIndex = nextPiecePositionIndex;

                                        lastSuperEndPosition = nextPosition; //***
                                        lastSuperCameDirection = "backRight";

                                    }else{
                                        // Super free movement

                                        System.out.println("new super free movement check");
                                        startMove(piecePos,nextPosition,listTools.findPosition2D(piecePos,piecePositions));
                                        lastSuperEndPosition = new Vector2();
                                        lastSuperCameDirection = "";

                                    }


                                    break;

                                }else if(lastSuperEndPosition.equals(draggedPosition)){
                                    System.out.println("not new super movement");
                                    if(!captures.isEmpty()){

                                        // move piece
                                        piecePos.set(nextPosition);

                                        // pass the argument to main bord
                                        int[] index = listTools.findPosition3D(nextPosition,bordPositions);
                                        movement.add(index);

                                        //capture
                                        pieceMovement.removeArray = captures;
                                        pieceMovement.startRemoveArrayForPlayer(removeDuration);
                                        Checkers.lastMovementIsACapture = true;
                                        Checkers.lastCaptureDonePiece = nextPosition;
                                        //Checkers.lastCapturePieceIndex = nextPiecePositionIndex;

                                        lastSuperEndPosition = nextPosition; //***
                                        lastSuperCameDirection = "backRight";

                                        break;
                                    }else {
                                        System.out.println("cant do free movement only capture");

                                        piecePos.set(draggedPosition);
                                        break;
                                    }
                                }
                                else {
                                    piecePos.set(draggedPosition);




                                    break;
                                }


                                // last nextpositon = now draggedposition ...

                            }
                        }

                    }else {
                        piecePos.set(draggedPosition);
                    }
                }else {
                    piecePos.set(draggedPosition);

                }
            }catch (Exception e){
                System.out.println("dragging error = "+e);
                piecePos.set(draggedPosition);
            }



        }else {
            Vector2 nearestPoint = null;
            float minDistance = Float.MAX_VALUE;


            for(List<Vector2>row : bordPositions){
                for (Vector2 point : row) {
                    float distance = draggedPosition.dst(point);
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearestPoint = point;
                    }
                }
            }
            piecePos.set(nearestPoint);
        }

    }

    public void backLeftSuper(Vector2 piecePos, Vector2 touchPos, NamedSprite piece, Vector2 draggedPosition, List<Vector2> piecePositions, List<Sprite> pieces, List<List<Vector2>> bordPositions , PieceMovement pieceMovement){

        if(listTools.findPositionCheck3D(draggedPosition,bordPositions)) {
            List<int[]> movePositions = new ArrayList<>();
            int[] positionIndex = listTools.findPosition3D(draggedPosition, bordPositions);
            List<Vector2> movePiecePosition = new ArrayList<>();

            int countY = positionIndex[0] + 1; // **
            int countX = positionIndex[1] - 1; // **
            for (; countY < bordPositions.size() && countX >= 0; ) { // **
                movePositions.add(new int[]{countY, countX});
                movePiecePosition.add(bordPositions.get(countY).get(countX));
                countY++; //**
                countX--; //**
            }
            List<Vector2>captures = new ArrayList<>();
            Vector2 nextPosition = findNearestPoint(movePiecePosition, piecePos);
            boolean findWhitePiece = false;


            try{
                if(!lastSuperCameDirection.equals("frontRight")){
                    if(!listTools.findPositionCheck2D(nextPosition,piecePositions)) {
                        for (Vector2 position : movePiecePosition) {
                            if (!position.equals(nextPosition)) {
                                if (listTools.findPositionCheck2D(position, piecePositions)) {
                                    NamedSprite p = (NamedSprite) pieces.get(listTools.findPosition2D(position, piecePositions));
                                    if (!(p.getName().equals("black") | p.getName().equals("black_super"))) {
                                        if (p.getName().equals("white") | p.getName().equals("white_super")) {  // update
                                            if (!findWhitePiece) {
                                                findWhitePiece = true;
                                                captures.add(position);
                                            } else {
                                                piecePos.set(draggedPosition);
                                                captures.clear();
                                                break;
                                            }
                                        }
                                    } else {
                                        piecePos.set(draggedPosition);
                                        captures.clear();
                                        break;
                                    }
                                } else {
                                    findWhitePiece = false;
                                }
                                //move
                            } else {

                                if(lastSuperEndPosition.equals(new Vector2())){
                                    System.out.println("new super movement");

                                    // pass the argument to main bord
                                    int[] index = listTools.findPosition3D(nextPosition,bordPositions);
                                    movement.add(positionIndex);
                                    movement.add(index);


                                    // capture
                                    if (!captures.isEmpty()) {

                                        // move piece
                                        piecePos.set(nextPosition);

                                        pieceMovement.removeArray = captures;
                                        pieceMovement.startRemoveArrayForPlayer(removeDuration);

                                        // stop auto switch
                                        Checkers.lastMovementIsACapture = true;
                                        Checkers.lastCaptureDonePiece = nextPosition;
                                        //Checkers.lastCapturePieceIndex = nextPiecePositionIndex;

                                        lastSuperEndPosition = nextPosition; //***
                                        lastSuperCameDirection = "backLeft";

                                    }else{
                                        // Super free movement

                                        System.out.println("new super free movement check");
                                        startMove(piecePos,nextPosition,listTools.findPosition2D(piecePos,piecePositions));
                                        lastSuperEndPosition = new Vector2();
                                        lastSuperCameDirection = "";

                                    }


                                    break;

                                }else if(lastSuperEndPosition.equals(draggedPosition)){
                                    System.out.println("not new super movement");
                                    if(!captures.isEmpty()){

                                        // move piece
                                        piecePos.set(nextPosition);

                                        // pass the argument to main bord
                                        int[] index = listTools.findPosition3D(nextPosition,bordPositions);
                                        movement.add(index);

                                        //capture
                                        pieceMovement.removeArray = captures;
                                        pieceMovement.startRemoveArrayForPlayer(removeDuration);
                                        Checkers.lastMovementIsACapture = true;
                                        Checkers.lastCaptureDonePiece = nextPosition;
                                        lastSuperEndPosition = nextPosition; //***
                                        lastSuperCameDirection = "backLeft";

                                        break;
                                    }else {
                                        System.out.println("cant do free movement only capture");

                                        piecePos.set(draggedPosition);
                                        break;
                                    }
                                }
                                else {
                                    piecePos.set(draggedPosition);




                                    break;
                                }


                                // last nextpositon = now draggedposition ...

                            }
                        }

                    }else {
                        piecePos.set(draggedPosition);
                    }
                }else {
                    piecePos.set(draggedPosition);

                }
            }catch (Exception e){
                System.out.println("dragging error = "+e);
                piecePos.set(draggedPosition);
            }

        }else {
            Vector2 nearestPoint = null;
            float minDistance = Float.MAX_VALUE;


            for(List<Vector2>row : bordPositions){
                for (Vector2 point : row) {
                    float distance = draggedPosition.dst(point);
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearestPoint = point;
                    }
                }
            }
            piecePos.set(nearestPoint);
        }

    }


    public void frontLeft(Vector2 piecePos, Vector2 touchPos, NamedSprite piece, Vector2 draggedPosition, List<Vector2> piecePositions,List<Sprite> pieces,List<List<Vector2>> bordPositions ,PieceMovement pieceMovement){


        if(listTools.findPositionCheck3D(draggedPosition,bordPositions)){

            int[] positionIndex = listTools.findPosition3D(draggedPosition,bordPositions);
            int[] nextPositionIndex = new int[]{positionIndex[0] - 1 ,positionIndex[1]-1};

            // check range
            if(nextPositionIndex[0] >= 0 & nextPositionIndex[1] >= 0) {
                Vector2 nextPosition = new Vector2(bordPositions.get(nextPositionIndex[0]).get(nextPositionIndex[1]));
                // move
                if (!listTools.findPositionCheck2D(nextPosition, piecePositions)) {

                    if(!Checkers.lastMovementIsACapture) {
                        startMove(piecePos,nextPosition,listTools.findPosition2D(piecePos,piecePositions));
                        movement.add(positionIndex);
                        movement.add(nextPositionIndex);


                    }else {
                        piecePos.set(draggedPosition);
                    }

                } else {
                    int nextPiecePositionIndex = listTools.findPosition2D(nextPosition, piecePositions);
                    NamedSprite nextPiece = (NamedSprite) pieces.get(nextPiecePositionIndex);
                    // capture
                    if (!piece.getName().equals(nextPiece.getName()) & !nextPiece.getName().equals("black_super")) {
                        int[] nextNextPositionIndex = new int[]{positionIndex[0] - 2 ,positionIndex[1] - 2};
                        // check range
                        if(nextNextPositionIndex[0] >= 0 & nextNextPositionIndex[1] >= 0) {
                            Vector2 nextNextPosition = bordPositions.get(nextNextPositionIndex[0]).get(nextNextPositionIndex[1]);
                            if(!listTools.findPositionCheck2D(nextNextPosition,piecePositions)){

                                startMove(piecePos,nextNextPosition,listTools.findPosition2D(piecePos,piecePositions));
                                //piecePos.set(nextPosition);

                                if(Checkers.lastMovementIsACapture){
                                    movement.add(nextPositionIndex);
                                }else {
                                    movement.add(positionIndex);
                                    movement.add(nextPositionIndex);
                                }

                                Checkers.lastMovementIsACapture = true;
                                Checkers.lastCaptureDonePiece = nextNextPosition;
                                Checkers.lastCapturePieceIndex = nextPiecePositionIndex;

                            }else {
                                piecePos.set(draggedPosition);
                            }

                        }else{
                            piecePos.set(draggedPosition);
                        }

                    } else {
                        piecePos.set(draggedPosition);
                    }
                }
            }else {
                piecePos.set(draggedPosition);
            }
        }else {
            Vector2 nearestPoint = null;
            float minDistance = Float.MAX_VALUE;


            for(List<Vector2>row : bordPositions){
                for (Vector2 point : row) {
                    float distance = draggedPosition.dst(point);
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearestPoint = point;
                    }
                }
            }
            piecePos.set(nearestPoint);
        }

    }

    public void frontRight(Vector2 piecePos, Vector2 touchPos, NamedSprite piece, Vector2 draggedPosition, List<Vector2> piecePositions,List<Sprite> pieces,List<List<Vector2>> bordPositions ,PieceMovement pieceMovement){

        if(listTools.findPositionCheck3D(draggedPosition,bordPositions)){

            int[] positionIndex = listTools.findPosition3D(draggedPosition,bordPositions);
            int[] nextPositionIndex = new int[]{positionIndex[0] - 1 ,positionIndex[1]+1};  // fix

            // check range
            if(nextPositionIndex[0] >= 0 & nextPositionIndex[1] < bordPositions.size()) {  // fix
                Vector2 nextPosition = new Vector2(bordPositions.get(nextPositionIndex[0]).get(nextPositionIndex[1]));

                if (!listTools.findPositionCheck2D(nextPosition, piecePositions)) {

                    if(!Checkers.lastMovementIsACapture) {

                        startMove(piecePos,nextPosition,listTools.findPosition2D(piecePos,piecePositions));
                        //piecePos.set(nextPosition);
                        movement.add(positionIndex);
                        movement.add(nextPositionIndex);


                    }else {
                        piecePos.set(draggedPosition);
                    }

                } else {
                    int nextPiecePositionIndex = listTools.findPosition2D(nextPosition, piecePositions);
                    NamedSprite nextPiece = (NamedSprite) pieces.get(nextPiecePositionIndex);
                    // capture
                    if (!piece.getName().equals(nextPiece.getName()) & !nextPiece.getName().equals("black_super")) {   // fix
                        int[] nextNextPositionIndex = new int[]{positionIndex[0] - 2 ,positionIndex[1] + 2}; // fix
                        // check range
                        if(nextNextPositionIndex[0] >= 0 & nextNextPositionIndex[1] < bordPositions.size()) {
                            Vector2 nextNextPosition = bordPositions.get(nextNextPositionIndex[0]).get(nextNextPositionIndex[1]);
                            if(!listTools.findPositionCheck2D(nextNextPosition,piecePositions)){

                                startMove(piecePos,nextNextPosition,listTools.findPosition2D(piecePos,piecePositions));
                                //piecePos.set(nextPosition);

                                if(Checkers.lastMovementIsACapture){
                                    movement.add(nextPositionIndex);
                                }else {
                                    movement.add(positionIndex);
                                    movement.add(nextPositionIndex);
                                }

                                Checkers.lastMovementIsACapture = true;
                                Checkers.lastCaptureDonePiece = nextNextPosition;
                                Checkers.lastCapturePieceIndex = nextPiecePositionIndex;

                            }else {
                                piecePos.set(draggedPosition);
                            }

                        }else{
                            piecePos.set(draggedPosition);
                        }

                    } else {
                        piecePos.set(draggedPosition);
                    }
                }
            }else {
                piecePos.set(draggedPosition);
            }
        }else {
            Vector2 nearestPoint = null;
            float minDistance = Float.MAX_VALUE;


            for(List<Vector2>row : bordPositions){
                for (Vector2 point : row) {
                    float distance = draggedPosition.dst(point);
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearestPoint = point;
                    }
                }
            }
            piecePos.set(nearestPoint);
        }

    }

    public void backRight(Vector2 piecePos, Vector2 touchPos, NamedSprite piece, Vector2 draggedPosition, List<Vector2> piecePositions,List<Sprite> pieces,List<List<Vector2>> bordPositions ,PieceMovement pieceMovement){

        if(listTools.findPositionCheck3D(draggedPosition,bordPositions)){

            int[] positionIndex = listTools.findPosition3D(draggedPosition,bordPositions);
            int[] nextPositionIndex = new int[]{positionIndex[0] + 1 ,positionIndex[1]+1};  // fix

            // check range
            if(nextPositionIndex[0] < bordPositions.size() & nextPositionIndex[1] < bordPositions.size()) {  // fix
                Vector2 nextPosition = new Vector2(bordPositions.get(nextPositionIndex[0]).get(nextPositionIndex[1]));

                if (listTools.findPositionCheck2D(nextPosition, piecePositions)) {
                    int nextPiecePositionIndex = listTools.findPosition2D(nextPosition, piecePositions);
                    NamedSprite nextPiece = (NamedSprite) pieces.get(nextPiecePositionIndex);
                    // capture
                    if (!piece.getName().equals(nextPiece.getName())& !nextPiece.getName().equals("black_super")) {   // fix ***
                        System.out.println(nextPiece.getName()+" -- "+nextPiece.getName());
                        int[] nextNextPositionIndex = new int[]{positionIndex[0] + 2 ,positionIndex[1] + 2}; // fix
                        // check range
                        if(nextNextPositionIndex[0]< bordPositions.size() & nextNextPositionIndex[1] < bordPositions.size()) {
                            Vector2 nextNextPosition = bordPositions.get(nextNextPositionIndex[0]).get(nextNextPositionIndex[1]);

                            if(!listTools.findPositionCheck2D(nextNextPosition,piecePositions)){
                                startMove(piecePos,nextNextPosition,listTools.findPosition2D(piecePos,piecePositions));

                                if(Checkers.lastMovementIsACapture){
                                    movement.add(nextPositionIndex);
                                }else {
                                    movement.add(positionIndex);
                                    movement.add(nextPositionIndex);
                                }

                                Checkers.lastMovementIsACapture = true;
                                Checkers.lastCaptureDonePiece = nextNextPosition;
                                Checkers.lastCapturePieceIndex = nextPiecePositionIndex;

                            }else {
                                piecePos.set(draggedPosition);
                            }
                        }else{
                            piecePos.set(draggedPosition);
                        }
                    } else {
                        piecePos.set(draggedPosition);
                    }
                }else {
                    piecePos.set(draggedPosition);
                }
            }else {
                piecePos.set(draggedPosition);
            }
        }else {
            Vector2 nearestPoint = null;
            float minDistance = Float.MAX_VALUE;


            for(List<Vector2>row : bordPositions){
                for (Vector2 point : row) {
                    float distance = draggedPosition.dst(point);
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearestPoint = point;
                    }
                }
            }
            piecePos.set(nearestPoint);
        }


    }

    public void backLeft(Vector2 piecePos, Vector2 touchPos, NamedSprite piece, Vector2 draggedPosition, List<Vector2> piecePositions,List<Sprite> pieces,List<List<Vector2>> bordPositions ,PieceMovement pieceMovement){

        if(listTools.findPositionCheck3D(draggedPosition,bordPositions)){
            int[] positionIndex = listTools.findPosition3D(draggedPosition,bordPositions);
            int[] nextPositionIndex = new int[]{positionIndex[0] + 1 ,positionIndex[1]-1};  // fix

            // check range
            if(nextPositionIndex[0] < bordPositions.size() & nextPositionIndex[1] >= 0) {  // fix
                Vector2 nextPosition = new Vector2(bordPositions.get(nextPositionIndex[0]).get(nextPositionIndex[1]));

                if (listTools.findPositionCheck2D(nextPosition, piecePositions)) {
                    int nextPiecePositionIndex = listTools.findPosition2D(nextPosition, piecePositions);
                    NamedSprite nextPiece = (NamedSprite) pieces.get(nextPiecePositionIndex);
                    // capture
                    if (!piece.getName().equals(nextPiece.getName())& !nextPiece.getName().equals("black_super")) {   // fix ***
                        System.out.println(nextPiece.getName()+" -- "+nextPiece.getName());
                        int[] nextNextPositionIndex = new int[]{positionIndex[0] + 2 ,positionIndex[1] - 2}; // fix
                        // check range
                        if(nextNextPositionIndex[0] < bordPositions.size() & nextNextPositionIndex[1] >= 0) { // fix
                            Vector2 nextNextPosition = bordPositions.get(nextNextPositionIndex[0]).get(nextNextPositionIndex[1]);
                            if(!listTools.findPositionCheck2D(nextNextPosition,piecePositions)){
                                startMove(piecePos,nextNextPosition,listTools.findPosition2D(piecePos,piecePositions));

                                if(Checkers.lastMovementIsACapture){
                                    movement.add(nextPositionIndex);
                                }else {
                                    movement.add(positionIndex);
                                    movement.add(nextPositionIndex);
                                }

                                Checkers.lastMovementIsACapture = true;
                                Checkers.lastCaptureDonePiece = nextNextPosition;
                                Checkers.lastCapturePieceIndex = nextPiecePositionIndex;

                            }else {
                                piecePos.set(draggedPosition);
                            }
                        }else{
                            piecePos.set(draggedPosition);
                        }
                    } else {
                        piecePos.set(draggedPosition);
                    }
                }else {
                    piecePos.set(draggedPosition);
                }
            }else {
                piecePos.set(draggedPosition);
            }
        }else {
            Vector2 nearestPoint = null;
            float minDistance = Float.MAX_VALUE;


            for(List<Vector2>row : bordPositions){
                for (Vector2 point : row) {
                    float distance = draggedPosition.dst(point);
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearestPoint = point;
                    }
                }
            }
            piecePos.set(nearestPoint);
        }

    }



}
