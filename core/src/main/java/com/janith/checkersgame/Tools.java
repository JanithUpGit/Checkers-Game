package com.janith.checkersgame;

import java.util.*;

public class Tools extends PlayBord{
    public List<List<int[]>> autoCaptures ;
    public List<int[]> cap ;
    List<List<int[]>> freeMovement ;

    public void autoCapture(Bord bord , int[] pPosition,String attacker,String victim){

        int positionY = pPosition[0];
        int positionX = pPosition[1];
        boolean privelege ;
        try {
            // front left
            boolean find = false;
            if (positionY - 2 >= 0 && positionX - 2 >= 0) {
                if (bord.arrangedBord.get(positionY - 1).get(positionX - 1).team.equals(victim)) {
                    if (bord.arrangedBord.get(positionY - 2).get(positionX - 2).toString().equals("Empty")) {
                        //move(bord,attacker,new int[]{positionY,positionX},new int[]{positionY - 1,positionX - 1});
                        privelege = bord.arrangedBord.get(positionY - 1).get(positionX - 1).superPrivilege;
                        bord.arrangedBord.get(positionY - 1).set(positionX - 1,new EmptyPiece());
                        bord.arrangedBord.get(positionY - 2).set(positionX - 2,bord.arrangedBord.get(positionY).get(positionX));
                        bord.arrangedBord.get(positionY).set(positionX,new EmptyPiece());
                        cap.add(new int[]{positionY - 1,positionX - 1});
                        find = true;
                        autoCapture(bord,new int[]{positionY -2,positionX - 2},attacker,victim);
                        bord.arrangedBord.get(positionY - 1).set(positionX - 1,new Piece(victim));
                        bord.arrangedBord.get(positionY - 1).get(positionX - 1).superPrivilege = privelege;
                        bord.arrangedBord.get(positionY).set(positionX,bord.arrangedBord.get(positionY - 2).get(positionX - 2));
                        bord.arrangedBord.get(positionY - 2).set(positionX - 2 ,new EmptyPiece());
                        cap.remove(cap.size()-1);
                    }
                }
            }
            // front right
            if (positionY - 2 >= 0 && positionX + 2 <= 11) {
                if (bord.arrangedBord.get(positionY - 1).get(positionX + 1).team.equals(victim)) {
                    if (bord.arrangedBord.get(positionY - 2).get(positionX + 2).toString().equals("Empty")) {
                        privelege = bord.arrangedBord.get(positionY - 1).get(positionX + 1).superPrivilege;
                        bord.arrangedBord.get(positionY - 1).set(positionX + 1,new EmptyPiece());
                        bord.arrangedBord.get(positionY - 2).set(positionX + 2,bord.arrangedBord.get(positionY).get(positionX));
                        bord.arrangedBord.get(positionY).set(positionX,new EmptyPiece());
                        cap.add(new int[]{positionY - 1,positionX + 1});
                        find= true;
                        autoCapture(bord,new int[]{positionY -2,positionX + 2},attacker,victim);
                        bord.arrangedBord.get(positionY - 1).set(positionX + 1,new Piece(victim));
                        bord.arrangedBord.get(positionY - 1).get(positionX + 1).superPrivilege = privelege;
                        bord.arrangedBord.get(positionY).set(positionX,bord.arrangedBord.get(positionY - 2).get(positionX + 2));
                        bord.arrangedBord.get(positionY - 2).set(positionX + 2 ,new EmptyPiece());
                        cap.remove(cap.size()-1);
                    }
                }
            }
            // back left
            if (positionY + 2 <= 11 && positionX - 2 >= 0) {
                if (bord.arrangedBord.get(positionY + 1).get(positionX - 1).team.equals(victim)) {
                    if (bord.arrangedBord.get(positionY + 2).get(positionX - 2).toString().equals("Empty")) {
                        privelege = bord.arrangedBord.get(positionY + 1).get(positionX - 1).superPrivilege;
                        bord.arrangedBord.get(positionY + 1).set(positionX - 1,new EmptyPiece());
                        bord.arrangedBord.get(positionY + 2).set(positionX - 2,bord.arrangedBord.get(positionY).get(positionX));
                        bord.arrangedBord.get(positionY).set(positionX,new EmptyPiece());
                        cap.add(new int[]{positionY + 1,positionX - 1});
                        find = true;
                        autoCapture(bord,new int[]{positionY +2,positionX - 2},attacker,victim);
                        bord.arrangedBord.get(positionY + 1).set(positionX - 1,new Piece(victim));
                        bord.arrangedBord.get(positionY + 1).get(positionX - 1).superPrivilege = privelege;
                        bord.arrangedBord.get(positionY).set(positionX,bord.arrangedBord.get(positionY + 2).get(positionX - 2));
                        bord.arrangedBord.get(positionY + 2).set(positionX - 2 ,new EmptyPiece());
                        cap.remove(cap.size()-1);
                    }
                }
            }
            // back right
            if (positionY + 2 <= 11 && positionX + 2 <= 11) {
                if (bord.arrangedBord.get(positionY + 1).get(positionX + 1).team.equals(victim)) {
                    if (bord.arrangedBord.get(positionY + 2).get(positionX + 2).toString().equals("Empty")) {
                        privelege = bord.arrangedBord.get(positionY + 1).get(positionX + 1).superPrivilege;
                        bord.arrangedBord.get(positionY + 1).set(positionX + 1,new EmptyPiece());
                        bord.arrangedBord.get(positionY + 2).set(positionX + 2,bord.arrangedBord.get(positionY).get(positionX));
                        bord.arrangedBord.get(positionY).set(positionX,new EmptyPiece());
                        cap.add(new int[]{positionY + 1,positionX + 1});
                        find = true;
                        autoCapture(bord,new int[]{positionY +2,positionX + 2},attacker,victim);
                        bord.arrangedBord.get(positionY + 1).set(positionX + 1,new Piece(victim));
                        bord.arrangedBord.get(positionY + 1).get(positionX + 1).superPrivilege = privelege;
                        bord.arrangedBord.get(positionY).set(positionX,bord.arrangedBord.get(positionY + 2).get(positionX + 2));
                        bord.arrangedBord.get(positionY + 2).set(positionX + 2 ,new EmptyPiece());
                        cap.remove(cap.size()-1);
                    }
                }
            }
            if(!find) {
                List<int[]> copiedCap = new ArrayList<>();
                // Iterate over the original list and copy each int[] array
                for (int[] array : cap) {
                    int[] newArray = Arrays.copyOf(array, array.length);
                    copiedCap.add(newArray);
                }
                autoCaptures.add(copiedCap);
            };
        } catch (IndexOutOfBoundsException e) {
            System.out.println("out of range");
        }

    }
    public void superAutoCapture(Bord bord , int[] pPosition , String came , String attacker, String victim){

        int positionY = pPosition[0];
        int positionX = pPosition[1];
        //bord.printBord();
        boolean privelege ;


        boolean find = false;
        int countY;
        int countX;

        // front left
        if(!came.equals("bR")) {
            countY = positionY - 1;
            countX = positionX - 1;
            for (; countY >= 0 && countX >= 0; ) {
                if (bord.arrangedBord.get(countY).get(countX).team.equals(attacker)) {
                    break;
                } else if (bord.arrangedBord.get(countY).get(countX).team.equals(victim)) {
                    if (countY - 1 >= 0 && countX - 1 >= 0) {
                        if (bord.arrangedBord.get(countY - 1).get(countX - 1).toString().equals("Empty")) {
                            privelege = bord.arrangedBord.get(countY).get(countX).superPrivilege;
                            bord.arrangedBord.get(countY).set(countX, new EmptyPiece());
                            bord.arrangedBord.get(countY - 1).set(countX - 1, bord.arrangedBord.get(positionY).get(positionX));
                            bord.arrangedBord.get(positionY).set(positionX, new EmptyPiece());
                            cap.add(new int[]{countY - 1, countX - 1});
                            find = true;
                            superAutoCapture(bord, new int[]{countY - 1, countX - 1},"fL", attacker,victim);
                            //find = true;
                            bord.arrangedBord.get(countY).set(countX, new Piece(victim));
                            bord.arrangedBord.get(countY).get(countX).superPrivilege = privelege;
                            bord.arrangedBord.get(positionY).set(positionX, bord.arrangedBord.get(countY - 1).get(countX - 1));
                            bord.arrangedBord.get(countY - 1).set(countX - 1, new EmptyPiece());
                            cap.remove(cap.size()-1);
                        }else break;
                    }else break;
                }
                countY--;
                countX--;
            }
        }

        // front right
        if(!came.equals("bL")) {
            countY = positionY - 1;
            countX = positionX + 1;
            for (; countY >= 0 && countX <= 11; ) { // **
                if (bord.arrangedBord.get(countY).get(countX).team.equals(attacker)) {
                    break;
                } else if (bord.arrangedBord.get(countY).get(countX).team.equals(victim)) {
                    if (countY - 1 >= 0 && countX + 1 <= 11) { // **
                        if (bord.arrangedBord.get(countY - 1).get(countX + 1).toString().equals("Empty")) { //**
                            privelege = bord.arrangedBord.get(countY).get(countX).superPrivilege;
                            bord.arrangedBord.get(countY).set(countX, new EmptyPiece());
                            bord.arrangedBord.get(countY - 1).set(countX + 1, bord.arrangedBord.get(positionY).get(positionX));
                            bord.arrangedBord.get(positionY).set(positionX, new EmptyPiece());
                            cap.add(new int[]{countY - 1, countX + 1});
                            find = true;
                            superAutoCapture(bord, new int[]{countY - 1, countX + 1},"fR", attacker,victim);
                            bord.arrangedBord.get(countY).set(countX, new Piece(victim));
                            bord.arrangedBord.get(countY).get(countX).superPrivilege = privelege;
                            bord.arrangedBord.get(positionY).set(positionX, bord.arrangedBord.get(countY - 1).get(countX + 1));
                            bord.arrangedBord.get(countY - 1).set(countX + 1, new EmptyPiece());
                            cap.remove(cap.size()-1);

                        }else break;
                    }else break;
                }
                countY--;
                countX++;
            }
        }

        // back left
        if(!came.equals("fR")) {
            countY = positionY + 1;
            countX = positionX - 1;
            for (; countY <= 11 && countX >= 0; ) {
                if (bord.arrangedBord.get(countY).get(countX).team.equals(attacker)) {
                    //System.out.println("back hi");
                    break;
                } else if (bord.arrangedBord.get(countY).get(countX).team.equals(victim)) {
                    if (countY + 1 <= 11 && countX - 1 >= 0) {
                        if (bord.arrangedBord.get(countY + 1).get(countX - 1).toString().equals("Empty")) {
                            privelege = bord.arrangedBord.get(countY).get(countX).superPrivilege;
                            bord.arrangedBord.get(countY).set(countX, new EmptyPiece());
                            bord.arrangedBord.get(countY + 1).set(countX - 1, bord.arrangedBord.get(positionY).get(positionX));
                            bord.arrangedBord.get(positionY).set(positionX, new EmptyPiece());
                            cap.add(new int[]{countY + 1, countX - 1});
                            find = true;
                            superAutoCapture(bord, new int[]{countY + 1, countX - 1},"bL", attacker,victim);
                            bord.arrangedBord.get(countY).set(countX, new Piece(victim));
                            bord.arrangedBord.get(countY).get(countX).superPrivilege = privelege;
                            bord.arrangedBord.get(positionY).set(positionX, bord.arrangedBord.get(countY + 1).get(countX - 1));
                            bord.arrangedBord.get(countY + 1).set(countX - 1, new EmptyPiece());
                            cap.remove(cap.size()-1);
                        }else break;
                    }else break;
                }
                countY++;
                countX--;
            }
        }

        // back right
        if(!came.equals("fL")) {
            countY = positionY + 1;
            countX = positionX + 1;
            for (; countY <= 11 && countX <= 11; ) { // **
                if (bord.arrangedBord.get(countY).get(countX).team.equals(attacker)) {
                    break;
                } else if (bord.arrangedBord.get(countY).get(countX).team.equals(victim)) {
                    if (countY + 1 <= 11 && countX + 1 <= 11) { // **
                        if (bord.arrangedBord.get(countY + 1).get(countX + 1).toString().equals("Empty")) { //**
                            privelege = bord.arrangedBord.get(countY).get(countX).superPrivilege;
                            bord.arrangedBord.get(countY).set(countX, new EmptyPiece());
                            bord.arrangedBord.get(countY + 1).set(countX + 1, bord.arrangedBord.get(positionY).get(positionX));
                            bord.arrangedBord.get(positionY).set(positionX, new EmptyPiece());
                            cap.add(new int[]{countY + 1, countX + 1});
                            find = true;
                            superAutoCapture(bord, new int[]{countY + 1, countX + 1},"bR", attacker,victim);
                            bord.arrangedBord.get(countY).set(countX, new Piece(victim));
                            bord.arrangedBord.get(countY).get(countX).superPrivilege = privelege;
                            bord.arrangedBord.get(positionY).set(positionX, bord.arrangedBord.get(countY + 1).get(countX + 1));
                            bord.arrangedBord.get(countY + 1).set(countX + 1, new EmptyPiece());
                            cap.remove(cap.size()-1);
                        }else break;
                    }else break;
                }
                countY++;
                countX++;
            }
        }

        if(came.equals("fL")) {
            if (positionY - 1 >= 0 && positionX - 1 >= 0)
                if (bord.arrangedBord.get(positionY - 1).get(positionX - 1).toString().equals("Empty")){
                    cap.remove(cap.size()-1);
                    cap.add(new int[]{positionY - 1, positionX - 1});
                    superAutoCapture(bord, new int[]{positionY - 1, positionX - 1},"fL", attacker,victim);
                    cap.remove(cap.size()-1);
                    cap.add(new int[]{positionY, positionX});
                }
        }
        if(came.equals("fR")) {
            if (positionY - 1 >= 0 && positionX + 1 <= 11)
                if (bord.arrangedBord.get(positionY - 1).get(positionX + 1).toString().equals("Empty")) {
                    cap.remove(cap.size()-1);
                    cap.add(new int[]{positionY - 1, positionX + 1});
                    superAutoCapture(bord, new int[]{positionY - 1, positionX + 1}, "fR", attacker,victim);
                    cap.remove(cap.size()-1);
                    cap.add(new int[]{positionY, positionX});
                }
        }
        if(came.equals("bL")) {
            if (positionY + 1 <= 11 && positionX - 1 >= 0)
                if (bord.arrangedBord.get(positionY + 1).get(positionX - 1).toString().equals("Empty")) {
                    cap.remove(cap.size()-1);
                    cap.add(new int[]{positionY + 1, positionX - 1});
                    superAutoCapture(bord, new int[]{positionY + 1, positionX - 1}, "bL", attacker,victim);
                    cap.remove(cap.size()-1);
                    cap.add(new int[]{positionY, positionX});
                }
        }
        if(came.equals("bR")) {
            if (positionY + 1 <= 11 && positionX + 1 <= 11)
                if (bord.arrangedBord.get(positionY + 1).get(positionX + 1).toString().equals("Empty")) {
                    cap.remove(cap.size()-1);
                    cap.add(new int[]{positionY + 1, positionX + 1});
                    superAutoCapture(bord, new int[]{positionY + 1, positionX + 1}, "bR", attacker,victim);
                    cap.remove(cap.size()-1);
                    cap.add(new int[]{positionY, positionX});
                }
        }

        if(!find) {


            List<int[]> copiedCap = new ArrayList<>();
            // Iterate over the original list and copy each int[] array
            for (int[] array : cap) {
                int[] newArray = Arrays.copyOf(array, array.length);
                copiedCap.add(newArray);

            }
            //copiedCap.removeLast();
            autoCaptures.add(copiedCap);
        }


    }
    public boolean findCaptureForNormalPiece(Bord bord , int[] piecePosition,String victim){
        boolean capture = false;
        int positionY = piecePosition[0] ;
        int positionX = piecePosition[1] ;

        while(true) {
            try {
                if (positionY - 2 >= 0 && positionX - 2 >= 0) {
                    if (bord.arrangedBord.get(positionY - 1).get(positionX - 1).team.equals(victim)) {
                        if (bord.arrangedBord.get(positionY - 2).get(positionX - 2).toString().equals("Empty")) {
                            capture = true;
                            break;
                        }
                    }
                }
                // front right
                if (positionY - 2 >= 0 && positionX + 2 <= 11) {
                    if (bord.arrangedBord.get(positionY - 1).get(positionX + 1).team.equals(victim)) {
                        if (bord.arrangedBord.get(positionY - 2).get(positionX + 2).toString().equals("Empty")) {
                            capture = true;
                            break;
                        }
                    }
                }
                // back left
                if (positionY + 2 <= 11 && positionX - 2 >= 0) {
                    if (bord.arrangedBord.get(positionY + 1).get(positionX - 1).team.equals(victim)) {
                        if (bord.arrangedBord.get(positionY + 2).get(positionX - 2).toString().equals("Empty")) {
                            capture = true;
                            break;
                        }
                    }
                }
                // back right
                if (positionY + 2 <= 11 && positionX + 2 <= 11) {

                    if (bord.arrangedBord.get(positionY + 1).get(positionX + 1).team.equals(victim)) {

                        if (bord.arrangedBord.get(positionY + 2).get(positionX + 2).toString().equals("Empty")) {
                            capture = true;
                            break;
                        }
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("out of range");
            }
            break;
        }
        return capture;
    }
    public boolean findFrontLeft(Bord bord ,int positionY,int positionX,String attacker,String victim,String came){
        // front left
        int countY = positionY - 1;
        int countX = positionX - 1;
        boolean capture = false;
        for (; countY >= 0 && countX >= 0; ){
            if (bord.arrangedBord.get(countY).get(countX).team.equals(attacker)) {
                break;
            } else if (bord.arrangedBord.get(countY).get(countX).team.equals(victim)) {
                if (countY - 1 >= 0 && countX - 1 >= 0) {
                    if (bord.arrangedBord.get(countY - 1).get(countX - 1).toString().equals("Empty")) {
                        capture = true;
                        break;
                    }else break;
                }else break;
            }
            if (came.equals("bR")) {
                capture = findBackLeft(bord, countY, countX, attacker, victim,"");
                if (capture) break;
                capture = findFrontRight(bord, countY, countX, attacker, victim,"");
                if (capture) break;
            }
            countY--;
            countX--;
        }
        return  capture;
    }
    public boolean findFrontRight(Bord bord ,int positionY,int positionX,String attacker,String victim,String came){
        // front right
        int countY = positionY - 1;// **
        int countX = positionX + 1; //**
        boolean capture = false;
        for (; countY >= 0 && countX <= 11; ){ // **
            if (bord.arrangedBord.get(countY).get(countX).team.equals(attacker)) {
                break;
            } else if (bord.arrangedBord.get(countY).get(countX).team.equals(victim)) {
                if (countY - 1  >= 0 && countX + 1 <= 11) { // **
                    if (bord.arrangedBord.get(countY - 1).get(countX + 1).toString().equals("Empty")) { //**
                        capture = true;
                        break;
                    }else break;
                }else break;

            }
            if (came.equals("bL")) {
                capture = findFrontLeft(bord, countY, countX, attacker, victim,"");
                if (capture) break;
                capture = findBackRight(bord, countY, countX, attacker, victim,"");
                if (capture) break;
            }
            countY--;
            countX++;
        }
        return  capture;
    }
    public boolean findBackLeft(Bord bord ,int positionY,int positionX,String attacker,String victim,String came){
        // back left
        boolean capture = false;
        int countY = positionY + 1;
        int countX = positionX - 1;
        for (; countY <= 11 && countX >= 0; ){
            if (bord.arrangedBord.get(countY).get(countX).team.equals(attacker)) {
                break;
            } else if (bord.arrangedBord.get(countY).get(countX).team.equals(victim)) {
                if (countY + 1 <= 11 && countX - 1 >= 0) {
                    if (bord.arrangedBord.get(countY + 1).get(countX - 1).toString().equals("Empty")) {
                        capture = true;
                        break;
                    }else break;
                }else break;
            }
            if (came.equals("fR")) {
                capture = findBackRight(bord, countY, countX, attacker, victim,"");
                if (capture) break;
                capture = findFrontLeft(bord, countY, countX, attacker, victim,"");
                if (capture) break;
            }
            countY++;
            countX--;
        }
        return  capture;
    }
    public boolean findBackRight(Bord bord ,int positionY,int positionX,String attacker,String victim,String came){
        boolean capture = false;
        // back right
        int countY = positionY + 1;// **
        int countX = positionX + 1; //**
        for (; countY <= 11 && countX <= 11; ){ // **
            if (bord.arrangedBord.get(countY).get(countX).team.equals(attacker)) {
                break;
            } else if (bord.arrangedBord.get(countY).get(countX).team.equals(victim)) {
                if (countY + 1  <= 11 && countX + 1 <= 11) { // **
                    if (bord.arrangedBord.get(countY + 1).get(countX + 1).toString().equals("Empty")) { //**
                        capture = true;
                        break;
                    }else break;
                }else break;
            }
            if (came.equals("fL")) {
                capture = findFrontRight(bord, countY, countX, attacker, victim,"");
                if (capture) break;
                capture = findBackLeft(bord, countY, countX, attacker, victim,"");
                if (capture) break;
            }
            countY++;
            countX++;
        }

        return  capture;
    }
    public boolean findCaptureForSuperPiece(Bord bord , int[] piecePosition,String attacker,String victim,String came){
        boolean capture;
        int positionY = piecePosition[0] ;
        int positionX = piecePosition[1] ;

        switch (came) {
            case "bR":
                capture = findFrontLeft(bord, positionY, positionX, attacker, victim, came);
                break;
            case "bL":
                capture = findFrontRight(bord, positionY, positionX, attacker, victim, came);
                break;
            case "fR":
                capture = findBackLeft(bord, positionY, positionX, attacker, victim, came);
                break;
            case "fL":
                capture = findBackRight(bord, positionY, positionX, attacker, victim, came);
                break;
            default:
                capture = findFrontLeft(bord, positionY, positionX, attacker, victim, came);
                if (!capture)
                    capture = findFrontRight(bord, positionY, positionX, attacker, victim, came);
                if (!capture)
                    capture = findFrontLeft(bord, positionY, positionX, attacker, victim, came);
                if (!capture)
                    capture = findBackLeft(bord, positionY, positionX, attacker, victim, came);
                if (!capture)
                    capture = findBackRight(bord, positionY, positionX, attacker, victim, came);
                break;
        }

        return capture;
    }
    public List<int[]> findCapturePieces(Bord bord,String attacker,String victim){

        List<int[]> captures = new ArrayList<>();
        int count = 0;
        for (List<Piece> row : bord.arrangedBord) {
            int count1 = 0;
            for(Piece piece : row){
                if(piece.team.equals(attacker)){
                    if(piece.superPrivilege) {
                        if (findCaptureForSuperPiece(bord, new int[]{count, count1}, attacker, victim, "")) {
                            captures.add(new int[]{count, count1});
                        }
                    }
                    else {
                        if (findCaptureForNormalPiece(bord, new int[]{count, count1},victim)) {
                            captures.add(new int[]{count, count1});
                        }
                    }
                }
                count1++;
            }
            count++;

        }
        return captures;
    }
    public List<int[]> findMaxLenList(List<List<int[]>> list,Bord bord){
        if (!list.isEmpty()) {
            Random random = new Random();
            int maxLength = 0;
            List<List<int[]>> maxSubLists = new ArrayList<>();
            // Iterate through each ArrayList and find the maximum length
            for (List<int[]> arrayList : list) {
                int currentLength = arrayList.size();
                // protect super piece
                for(int[] piece : arrayList){

                    if (    (bord.arrangedBord.get(piece[0]).get(piece[1]).superPrivilege && piece != arrayList.get(0))
                            || ((piece[0] <= 2 || piece[0] >= 9) && piece != arrayList.get(arrayList.size()-1))

                    ){
                        // super piece value
                        System.out.println("find super piece capture");
                        currentLength += 2;
                    }
                }
                if (currentLength > maxLength) {
                    maxLength = currentLength;
                    maxSubLists.clear(); // Clear previous max sublists
                    maxSubLists.add(arrayList); // Add current sublist
                } else if (currentLength == maxLength) {
                    maxSubLists.add(arrayList); // Add current sublist to max sublists
                }
            }
            int randomNumber = random.nextInt(maxSubLists.size());
            return maxSubLists.get(randomNumber);
        }else {
            System.out.println("findMaxLenListCompleted");
            return new ArrayList<>();

        }
    }
    public List<List<int[]>> findMaxLenListOrLists(List<List<int[]>> list){
        if (!list.isEmpty()) {
            int maxLength = 0;
            List<List<int[]>> maxSubLists = new ArrayList<>();
            // Iterate through each ArrayList and find the maximum length
            for (List<int[]> arrayList : list) {
                int currentLength = arrayList.size();
                if (currentLength > maxLength) {
                    maxLength = currentLength;
                    maxSubLists.clear(); // Clear previous max sublists
                    maxSubLists.add(arrayList); // Add current sublist
                } else if (currentLength == maxLength) {
                    maxSubLists.add(arrayList); // Add current sublist to max sublists
                }
            }
            return maxSubLists;
        }else
            return new ArrayList<>();
    }
    public List<List<int[]>> collectAllCapture(Bord bord, List<int[]> captures, String attacker, String victim){
        List<List<int[]>> movements = new ArrayList<>();
        autoCaptures = new ArrayList<>();
        for(int[] capture : captures){
            cap = new ArrayList<>();
            cap.add(capture);
            if (!bord.arrangedBord.get(capture[0]).get(capture[1]).superPrivilege){
                //autoCaptures = new ArrayList<>(); // refresh autocapture
                autoCapture(bord,capture,attacker,victim);
            }else {
                //autoCaptures = new ArrayList<>();
                superAutoCapture(bord,capture,"", attacker,victim);
            }

        }
        if(!autoCaptures.isEmpty()){
            Collections.sort(autoCaptures, (list1, list2) -> Integer.compare(list2.size(), list1.size()));
            // print autoCaptures
//            for (List<int[]> list : autoCaptures) {
//                System.out.print(attacker);
//                for (int[] array : list) {
//
//                    System.out.print("[");
//                    for (int i = 0; i < array.length; i++) {
//                        System.out.print(array[i]);
//                        if (i < array.length - 1) {
//                            System.out.print(", ");
//                        }
//                    }
//                    System.out.print("] ");
//                }
//                System.out.println();
//            }
//            System.out.println("---------------------");

            return autoCaptures;
        }else{

            return new ArrayList<>();
        }


    }
    public void findPieceAbleToMove(Bord bord , int[] piecePosition,String attacker){
        int positionY = piecePosition[0] ;
        int positionX = piecePosition[1] ;
        boolean superPrivilege = bord.arrangedBord.get(positionY).get(positionX).superPrivilege;
        List<int[]> movements = new ArrayList<>();
        int countY;
        int countX;

        try {

            // front
            if(superPrivilege || attacker.equals(bord.player2) ) {

                // left
                countY = positionY - 1;
                countX = positionX - 1;
                for(;countY >= 0 && countX >= 0; ) {
                    if (bord.arrangedBord.get(countY).get(countX).toString().equals("Empty")) {
                        movements.add(new int[]{positionY, positionX});
                        movements.add(new int[]{countY, countX});
                        this.freeMovement.add(movements);
                        movements = new ArrayList<>();
                    }else break;
                    if(!superPrivilege) break;
                    countY--;
                    countX--;
                }

                // right
                countY = positionY - 1;
                countX = positionX + 1;
                for(;countY >= 0 && countX <= 11; ) {
                    if (bord.arrangedBord.get(countY).get(countX).toString().equals("Empty")) {
                        movements.add(new int[]{positionY, positionX});
                        movements.add(new int[]{countY, countX});
                        this.freeMovement.add(movements);
                        movements = new ArrayList<>();
                    }else break;
                    if(!superPrivilege) break;
                    countY--;
                    countX++;
                }

            }
            // back
            if(superPrivilege || attacker.equals(bord.player1) ) {

                // left
                countY = positionY + 1;
                countX = positionX - 1;
                for(;countY <= 11 && countX >= 0; ) {
                    if (bord.arrangedBord.get(countY).get(countX).toString().equals("Empty")) {
                        movements.add(new int[]{positionY, positionX});
                        movements.add(new int[]{countY, countX});
                        this.freeMovement.add(movements);
                        movements = new ArrayList<>();
                    }else break;
                    if(!superPrivilege) break;
                    countY++;
                    countX--;
                }

                // right
                countY = positionY + 1;
                countX = positionX + 1;
                for(;countY <= 11 && countX <= 11; ) {
                    if (bord.arrangedBord.get(countY).get(countX).toString().equals("Empty")) {
                        movements.add(new int[]{positionY, positionX});
                        movements.add(new int[]{countY, countX});
                        this.freeMovement.add(movements);
                        movements = new ArrayList<>();
                    }else break;
                    if(!superPrivilege) break;
                    countY++;
                    countX++;
                }

            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("out of range m");
        }
    }
    public List<List<int[]>> scanMoveablePieces(Bord bord , String attacker){
        this.freeMovement = new ArrayList<>();
        int count = 0;
        for (List<Piece> row : bord.arrangedBord) {
            int count1 = 0;
            for(Piece piece : row){
                if(piece.team.equals(attacker)){
                    findPieceAbleToMove(bord, new int[]{count, count1},attacker);
                }
                count1++;
            }
            count++;

        }
        //new DevlopmentTools().print3DimList(freeMovement);
        return this.freeMovement;

    }

    public int captureValueCount(Bord bord,List<int[]> captures){
        int count = 0;
        if(!captures.isEmpty()) {
            for (int[] piece : captures) {
                if ((bord.arrangedBord.get(piece[0]).get(piece[1]).superPrivilege && piece != captures.get(0))
                        || ((piece[0] <= 2 || piece[0] >= 9) && piece != captures.get(captures.size()-1))
                ) {
                    count += 2; // super piece value
                } else {
                    count += 1;
                }

            }
        }
        System.out.println("capture value count : "+count);
        return count;
    }


}
