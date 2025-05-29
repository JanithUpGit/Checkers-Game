package com.janith.checkersgame;
import com.badlogic.gdx.ApplicationAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayBord extends ApplicationAdapter {
    // Movements
    private Bord moveFrontRight(Bord bord,String player,int[] piecePosition ,int[] endPosition) throws WrongMovement, PrivilegeError {
        // Find end position hava correct path
        List<int[]> delPieces = new ArrayList<>();
        int displacement = 1;
        int countY = piecePosition[0] - 1;
        int countX = piecePosition[1] + 1;
        boolean endPositionWasFound = false;
        for(;countY >= 0 && countX < bord.arrangedBord.size() ;){
            if(displacement <= 1 || bord.arrangedBord.get(piecePosition[0]).get(piecePosition[1]).superPrivilege){
                //Find del pieces
                if(bord.arrangedBord.get(countY).get(countX).toString().equals("Piece")){
                    if(bord.arrangedBord.get(countY).get(countX).team.equals(player)){
                        throw new WrongMovement("You cant capture your piece !!!");
                    }
                    else{
                        int[] delPiece = {countY,countX};
                        delPieces.add(delPiece);
                    }
                }
                // Find end position
                if((countY == endPosition[0]) && (countX == endPosition[1])) {
                    endPositionWasFound =true;
                    if(bord.arrangedBord.get(countY).get(countX).toString().equals("Piece")) {
                        if (displacement <= 1) {
                            try {
                                if (bord.arrangedBord.get(countY - 1).get(countX + 1).toString().equals("Empty")) {
                                    endPosition[0] = countY - 1;
                                    endPosition[1] = countX + 1;
                                    bord.arrangedBord.get(endPosition[0]).set(endPosition[1], bord.arrangedBord.get(piecePosition[0]).get(piecePosition[1]));
                                    bord.arrangedBord.get(piecePosition[0]).set(piecePosition[1], new EmptyPiece());
                                    // set super privilege
                                    if( endPosition[0] == 0 && bord.arrangedBord.get(endPosition[0]).get(endPosition[1]).team.equals(bord.player2))
                                        bord.arrangedBord.get(endPosition[0]).get(endPosition[1]).superPrivilege = true;
                                    if(!delPieces.isEmpty()){
                                        for(int[] delpiece: delPieces){
                                            bord.arrangedBord.get(delpiece[0]).set(delpiece[1], new EmptyPiece());
                                            bord.lastDeletedPieces.add(delpiece);
                                        }
                                        bord.lastMovementIsCapture = true; // for polices
                                    }
                                    bord.lastMovementPiecePosition = piecePosition;
                                    bord.lastMovementEndPosition = endPosition;  // for polices
                                    bord.lastMovementDid = player;  // for polices
                                }else throw new WrongMovement("wrong movement !!"); // <<
                            }catch (IndexOutOfBoundsException e){
                                throw new WrongMovement("Wrong movement !!");
                            }
                        }
                    }else {
                        bord.arrangedBord.get(endPosition[0]).set(endPosition[1], bord.arrangedBord.get(piecePosition[0]).get(piecePosition[1]));
                        bord.arrangedBord.get(piecePosition[0]).set(piecePosition[1], new EmptyPiece());
                        // set super privilege
                        if( endPosition[0] == 0 && bord.arrangedBord.get(endPosition[0]).get(endPosition[1]).team.equals(bord.player2))
                            bord.arrangedBord.get(endPosition[0]).get(endPosition[1]).superPrivilege = true;
                        if(!delPieces.isEmpty()){
                            for(int[] delpiece: delPieces){
                                bord.arrangedBord.get(delpiece[0]).set(delpiece[1], new EmptyPiece());
                                bord.lastDeletedPieces.add(delpiece);
                            }
                            bord.lastMovementIsCapture = true; // for polices
                        }else bord.lastMovementIsCapture = false; // for polices
                        bord.lastMovementPiecePosition = piecePosition;
                        bord.lastMovementEndPosition = endPosition;  // for polices
                        bord.lastMovementDid = player;  // for polices
                    }
                    break;
                }
            }else {
                throw new PrivilegeError("This one has no super privilege");
            }
            countY--;
            countX++;
            displacement++;
        }
        if(endPositionWasFound)return bord;
        else throw new WrongMovement("Wrong movement !!");
    }
    private Bord moveBackRight(Bord bord,String player,int[] piecePosition ,int[] endPosition) throws WrongMovement, PrivilegeError {
        // Find end position hava correct path
        List<int[]> delPieces = new ArrayList<>();
        int displacement = 1;
        boolean endPositionWasFound = false;
        int countY = piecePosition[0]+1; // **
        int countX = piecePosition[1]+1; // **
        for(;countY < bord.arrangedBord.size() && countX < bord.arrangedBord.size();){ // **
            if(displacement <= 1 || bord.arrangedBord.get(piecePosition[0]).get(piecePosition[1]).superPrivilege){
                //Find del pieces
                if(bord.arrangedBord.get(countY).get(countX).toString().equals("Piece")){
                    if(bord.arrangedBord.get(countY).get(countX).team.equals(player)){
                        throw new WrongMovement("You cant capture your piece !!!");
                    }
                    else{
                        int[] delPiece = {countY,countX};
                        delPieces.add(delPiece);
                    }
                }
                // Find end position
                if((countY == endPosition[0]) && (countX == endPosition[1])) {
                    endPositionWasFound =true;
                    if(bord.arrangedBord.get(countY).get(countX).toString().equals("Piece")) {
                        if (displacement <= 1) {
                            try {
                                if (bord.arrangedBord.get(countY + 1).get(countX + 1).toString().equals("Empty")) { // **
                                    endPosition[0] = countY + 1; // **
                                    endPosition[1] = countX + 1; // **
                                    bord.arrangedBord.get(endPosition[0]).set(endPosition[1], bord.arrangedBord.get(piecePosition[0]).get(piecePosition[1]));
                                    bord.arrangedBord.get(piecePosition[0]).set(piecePosition[1], new EmptyPiece());
                                    // set super privilege
                                    if( endPosition[0] == 11 && bord.arrangedBord.get(endPosition[0]).get(endPosition[1]).team.equals(bord.player1))
                                        bord.arrangedBord.get(endPosition[0]).get(endPosition[1]).superPrivilege = true;
                                    if(!delPieces.isEmpty()){
                                        for(int[] delpiece: delPieces){
                                            bord.arrangedBord.get(delpiece[0]).set(delpiece[1], new EmptyPiece());
                                            bord.lastDeletedPieces.add(delpiece);
                                        }
                                        bord.lastMovementIsCapture = true; // for polices
                                    }
                                    bord.lastMovementPiecePosition = piecePosition;
                                    bord.lastMovementEndPosition = endPosition;  // for polices
                                    bord.lastMovementDid = player;  // for polices
                                }else throw new WrongMovement("wrong movement !!");  // <<
                            }catch (IndexOutOfBoundsException e){
                                throw new WrongMovement("Wrong movement !!");
                            }
                        }
                    }else {
                        bord.arrangedBord.get(endPosition[0]).set(endPosition[1], bord.arrangedBord.get(piecePosition[0]).get(piecePosition[1]));
                        bord.arrangedBord.get(piecePosition[0]).set(piecePosition[1], new EmptyPiece());
                        // set superprivilege
                        if( endPosition[0] == 11 && bord.arrangedBord.get(endPosition[0]).get(endPosition[1]).team.equals(bord.player1))
                            bord.arrangedBord.get(endPosition[0]).get(endPosition[1]).superPrivilege = true;
                        if(!delPieces.isEmpty()){
                            for(int[] delpiece: delPieces){
                                bord.arrangedBord.get(delpiece[0]).set(delpiece[1], new EmptyPiece());
                                bord.lastDeletedPieces.add(delpiece);
                            }
                            bord.lastMovementIsCapture = true;
                        }else bord.lastMovementIsCapture = false; // for polices
                        bord.lastMovementPiecePosition = piecePosition;
                        bord.lastMovementEndPosition = endPosition;  // for polices
                        bord.lastMovementDid = player;  // for polices
                    }
                    break;
                }
            }else {
                throw new PrivilegeError("This one has no super privilege");
            }
            countY++; // **
            countX++; // **
            displacement++;
        }
        if(endPositionWasFound)return bord;
        else throw new WrongMovement("Wrong movement !!");
    }

    private Bord moveFrontLeft(Bord bord,String player,int[] piecePosition ,int[] endPosition) throws WrongMovement, PrivilegeError {
        // Find end position hava correct path
        List<int[]> delPieces = new ArrayList<>();
        int displacement = 1;
        boolean endPositionWasFound = false;
        int countY = piecePosition[0]-1; // **
        int countX = piecePosition[1]-1; // **
        for(;countY >= 0 && countX >= 0;){ // **
            if(displacement <= 1 || bord.arrangedBord.get(piecePosition[0]).get(piecePosition[1]).superPrivilege){
                //Find del pieces
                if(bord.arrangedBord.get(countY).get(countX).toString().equals("Piece")){
                    if(bord.arrangedBord.get(countY).get(countX).team.equals(player)){
                        throw new WrongMovement("You cant capture your piece !!!");
                    }
                    else{
                        int[] delPiece = {countY,countX};
                        delPieces.add(delPiece);
                    }
                }
                // Find end position
                if((countY == endPosition[0]) && (countX == endPosition[1])) {
                    endPositionWasFound =true;
                    if(bord.arrangedBord.get(countY).get(countX).toString().equals("Piece")) {
                        if (displacement <= 1) {
                            try {
                                if (bord.arrangedBord.get(countY - 1).get(countX - 1).toString().equals("Empty")){ // **
                                    endPosition[0] = countY - 1; // **
                                    endPosition[1] = countX - 1; // **
                                    bord.arrangedBord.get(endPosition[0]).set(endPosition[1], bord.arrangedBord.get(piecePosition[0]).get(piecePosition[1]));
                                    bord.arrangedBord.get(piecePosition[0]).set(piecePosition[1], new EmptyPiece());
                                    // set super privilege
                                    if( endPosition[0] == 0 && bord.arrangedBord.get(endPosition[0]).get(endPosition[1]).team.equals(bord.player2)) {
                                        bord.arrangedBord.get(endPosition[0]).get(endPosition[1]).superPrivilege = true;
                                    }
                                    if(!delPieces.isEmpty()){
                                        for(int[] delpiece: delPieces){
                                            bord.arrangedBord.get(delpiece[0]).set(delpiece[1], new EmptyPiece());
                                            bord.lastDeletedPieces.add(delpiece);
                                        }
                                        bord.lastMovementIsCapture = true; // for polices
                                    }
                                    bord.lastMovementPiecePosition = piecePosition;
                                    bord.lastMovementEndPosition = endPosition;  // for polices
                                    bord.lastMovementDid = player;  // for polices
                                }else throw new WrongMovement("wrong movement !!"); // <<\
                            }catch (IndexOutOfBoundsException e){
                                throw new WrongMovement("Wrong movement !!");
                            }
                        }
                    }else {
                        bord.arrangedBord.get(endPosition[0]).set(endPosition[1], bord.arrangedBord.get(piecePosition[0]).get(piecePosition[1]));
                        bord.arrangedBord.get(piecePosition[0]).set(piecePosition[1], new EmptyPiece());
                        // set super privilege
                        if( endPosition[0] == 0 && bord.arrangedBord.get(endPosition[0]).get(endPosition[1]).team.equals(bord.player2))
                            bord.arrangedBord.get(endPosition[0]).get(endPosition[1]).superPrivilege = true;
                        if(!delPieces.isEmpty()){
                            for(int[] delpiece: delPieces){
                                bord.arrangedBord.get(delpiece[0]).set(delpiece[1], new EmptyPiece());
                                bord.lastDeletedPieces.add(delpiece);
                            }
                            bord.lastMovementIsCapture = true;
                        }else bord.lastMovementIsCapture = false; // for polices
                        bord.lastMovementPiecePosition = piecePosition;
                        bord.lastMovementEndPosition = endPosition;  // for polices
                        bord.lastMovementDid = player;  // for polices
                    }
                    break;
                }
            }else {
                throw new PrivilegeError("This one has no super privilege");
            }
            countY--;
            countX--;
            displacement++;
        }
        if(endPositionWasFound)return bord;
        else throw new WrongMovement("Wrong movement !!");
    }

    private Bord moveBackLeft(Bord bord,String player,int[] piecePosition ,int[] endPosition) throws WrongMovement, PrivilegeError {
        // Find end position hava correct path
        List<int[]> delPieces = new ArrayList<>();
        int displacement = 1;
        boolean endPositionWasFound = false;
        int countY = piecePosition[0]+1; // **
        int countX = piecePosition[1]-1; // **
        for(;countY < bord.arrangedBord.size() && countX >= 0;){ // **
            if(displacement <= 1 || bord.arrangedBord.get(piecePosition[0]).get(piecePosition[1]).superPrivilege){
                //Find del pieces
                if(bord.arrangedBord.get(countY).get(countX).toString().equals("Piece")){
                    if(bord.arrangedBord.get(countY).get(countX).team.equals(player)) {
                        throw new WrongMovement("You cant capture your piece !!!");
                    }
                    else{
                        int[] delPiece = {countY,countX};
                        delPieces.add(delPiece);
                    }
                }
                // Find end position
                if((countY == endPosition[0]) && (countX == endPosition[1])) {
                    endPositionWasFound =true;
                    if(bord.arrangedBord.get(countY).get(countX).toString().equals("Piece")) {
                        if (displacement <= 1) {
                            try {
                                if (bord.arrangedBord.get(countY + 1).get(countX - 1).toString().equals("Empty")) { // **
                                    endPosition[0] = countY + 1; // **
                                    endPosition[1] = countX - 1; // **
                                    bord.arrangedBord.get(endPosition[0]).set(endPosition[1], bord.arrangedBord.get(piecePosition[0]).get(piecePosition[1]));
                                    bord.arrangedBord.get(piecePosition[0]).set(piecePosition[1], new EmptyPiece());
                                    // set super privilege
                                    if( endPosition[0] == 11 && bord.arrangedBord.get(endPosition[0]).get(endPosition[1]).team.equals(bord.player1))
                                        bord.arrangedBord.get(endPosition[0]).get(endPosition[1]).superPrivilege = true;
                                    if (!delPieces.isEmpty()) {
                                        for (int[] delpiece : delPieces) {
                                            bord.arrangedBord.get(delpiece[0]).set(delpiece[1], new EmptyPiece());
                                            bord.lastDeletedPieces.add(delpiece);
                                        }
                                        bord.lastMovementIsCapture = true; // for polices
                                    }
                                    bord.lastMovementPiecePosition = piecePosition;
                                    bord.lastMovementEndPosition = endPosition;  // for polices
                                    bord.lastMovementDid = player;  // for polices
                                }else throw new WrongMovement("wrong movement !!"); // <<
                            }catch (IndexOutOfBoundsException e){
                                throw new WrongMovement("Wrong movement !!");
                            }
                        }
                    }else {
                        bord.arrangedBord.get(endPosition[0]).set(endPosition[1],bord.arrangedBord.get(piecePosition[0]).get(piecePosition[1]));
                        bord.arrangedBord.get(piecePosition[0]).set(piecePosition[1], new EmptyPiece());
                        if( endPosition[0] == 11 && bord.arrangedBord.get(endPosition[0]).get(endPosition[1]).team.equals(bord.player1))
                            bord.arrangedBord.get(endPosition[0]).get(endPosition[1]).superPrivilege = true;
                        if(!delPieces.isEmpty()){
                            for(int[] delpiece: delPieces){
                                bord.arrangedBord.get(delpiece[0]).set(delpiece[1], new EmptyPiece());
                                bord.lastDeletedPieces.add(delpiece);
                            }
                            bord.lastMovementIsCapture = true;
                        }else bord.lastMovementIsCapture = false; // for polices
                        bord.lastMovementPiecePosition = piecePosition;
                        bord.lastMovementEndPosition = endPosition; // for polices
                        bord.lastMovementDid = player; // for polices
                    }
                    break;
                }
            }else {
                throw new PrivilegeError("This one has no super privilege");
            }
            countY++; // **
            countX--; // **
            displacement++;
        }
        if(endPositionWasFound)return bord;
        else throw new WrongMovement("Wrong movement !!");
    }

    public Bord move(Bord bord,String player, int[] piecePosition, int[] endPosition) throws Exception {
        //Check Position and error handle
        if (!((0 <= endPosition[0] && endPosition[0] <= 11) && (0 <= endPosition[1] && endPosition[1]<= 11))) throw new WrongMovement("Wrong direction !");
        if (!((0 <= piecePosition[0] && piecePosition[0] <= 11) && (0 <= piecePosition[1] && piecePosition[1]<= 11))) throw new WrongMovement("Wrong direction !");
        if(!bord.arrangedBord.get(piecePosition[0]).get(piecePosition[1]).toString().equals("Empty")){
            // find the access piece is a player property
            if (bord.arrangedBord.get(piecePosition[0]).get(piecePosition[1]).team.equals(player)){
                // Find moving Direction
                // Front
                if (piecePosition[0] > endPosition[0]){
                    //Right
                    if (piecePosition[1] < endPosition[1]) {
                        if(bord.arrangedBord.get(piecePosition[0]).get(piecePosition[1]).superPrivilege || bord.arrangedBord.get(piecePosition[0]).get(piecePosition[1]).team.equals(bord.player2) || ( !bord.arrangedBord.get(piecePosition[0]- 1).get(piecePosition[1] + 1).toString().equals("Empty"))){
                            bord = moveFrontRight(bord, player, piecePosition, endPosition);
                        }else throw new WrongMovement("Wrong direction !");
                    }
                    //Left
                    if(piecePosition[1] > endPosition[1]) {
                        if(bord.arrangedBord.get(piecePosition[0]).get(piecePosition[1]).superPrivilege || bord.arrangedBord.get(piecePosition[0]).get(piecePosition[1]).team.equals(bord.player2) || ( !bord.arrangedBord.get(piecePosition[0]- 1).get(piecePosition[1] - 1).toString().equals("Empty"))) {
                            bord = moveFrontLeft(bord, player, piecePosition,endPosition);
                        }else throw new WrongMovement("Wrong direction !");
                    }
                }
                //Back
                else if (piecePosition[0] < endPosition[0]){
                    //Right
                    if (piecePosition[1] < endPosition[1]){
                        if(bord.arrangedBord.get(piecePosition[0]).get(piecePosition[1]).superPrivilege || bord.arrangedBord.get(piecePosition[0]).get(piecePosition[1]).team.equals(bord.player1) || ( !bord.arrangedBord.get(piecePosition[0]+ 1).get(piecePosition[1] + 1).toString().equals("Empty"))) {
                            bord = moveBackRight(bord, player, piecePosition, endPosition);
                        }else throw new WrongMovement("Wrong direction !");
                    }
                    //Left
                    if(piecePosition[1] > endPosition[1]) {
                        if(bord.arrangedBord.get(piecePosition[0]).get(piecePosition[1]).superPrivilege || bord.arrangedBord.get(piecePosition[0]).get(piecePosition[1]).team.equals(bord.player1) || ( !bord.arrangedBord.get(piecePosition[0]+ 1).get(piecePosition[1] - 1).toString().equals("Empty"))) {
                            bord = moveBackLeft(bord, player, piecePosition,endPosition);
                        }else throw new WrongMovement("Wrong direction !");
                    }
                }else throw new WrongMovement("Wrong direction !");
            }else throw new AccessWrongProperty("This Piece not your");
        }else throw new EmptyPiecesAcces("Empty position !");

    return bord;
    }

    public void arrayMove(Bord bord ,String player, List<int[]> best) throws Exception{
        bord.lastDeletedPieces = new ArrayList<>();
        int[] pP = best.get(0);
        best.remove(0);
        int count = 0;
        if(!bord.arrangedBord.get(pP[0]).get(pP[1]).superPrivilege){
            for( int[] position : best) {
                if(!bord.lastMovementIsCapture && count > 0)
                    throw new CaptureError("You can't do multiple movement without capture !!");
                move(bord, player, pP, Arrays.copyOf(position,2)); // fucking error ******* :( :(
                pP = new int[]{(2*position[0])-pP[0],(2*position[1]) - pP[1]};
                count++;
            }
        }else {
            for( int[] position : best) {
                if (!bord.lastMovementIsCapture && count > 0)
                    throw new CaptureError("You can't do multiple movement without capture !!");
                //System.out.println(pP[0]+"-"+pP[1]+"  "+position[0]+"-"+position[1]);
                move(bord, player, pP, Arrays.copyOf(position, 2)); // fucking error ******* :( :(
                pP = position;
                count++;
            }
        }
    }

}
