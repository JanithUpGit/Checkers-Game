package com.janith.checkersgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Computer extends Tools{
    int captureTopRate;
    int trapTopRate;
    boolean captureFind ;
    public String saveBordJsonForFreeMovement;
    Police newpolice = new Police();

    public boolean lastMovementIsTrap = false;

    public List<int[]> findBestCapture(Bord bord , List<List<int[]>> autoCaptures,String attacker,String victim)throws Exception{
        this.captureTopRate = -1;
        List<int[]> topOne = new ArrayList<>();
        this.captureFind = false;
        if(!autoCaptures.isEmpty()) {
            bord.saveBordJson = bord.savaBordJson();
            Tools newTools = new Tools();
            List<int[]> cCapture;
            int numOfCapturedPieces;
            int numOfLostPieces;
            int topCapture = 0;
            for (int i = 0 ; i < autoCaptures.size();i++) {
                newpolice.checkPoint1(bord,attacker); // police

                // fist attack

                arrayMove(bord, attacker, autoCaptures.get(i).stream().map(array -> Arrays.copyOf(array, array.length)).collect(Collectors.toList()));
                newpolice.checkPoint2(bord,bord.lastMovementEndPosition,bord.lastMovementDid,bord.lastMovementIsCapture); // police

                // return attack

                List<int[]>pCapture = findMaxLenList( newTools.collectAllCapture(bord, newTools.findCapturePieces(bord, victim, attacker), victim, attacker),bord);
                numOfLostPieces = captureValueCount(bord,pCapture); // ***
                newpolice.checkPoint1(bord,victim); // police

                if (numOfLostPieces > 0) {
                    numOfLostPieces -= 1;

                    arrayMove(bord, victim, pCapture);
                }else {
                    arrayMove(bord, victim,findBestfreemovement(bord,newTools.scanMoveablePieces(bord,victim),victim,attacker));
                }

                newpolice.checkPoint2(bord,bord.lastMovementEndPosition,bord.lastMovementDid,bord.lastMovementIsCapture); // police

                // second attack

                cCapture = findMaxLenList(newTools.collectAllCapture(bord, newTools.findCapturePieces(bord, attacker, victim), attacker, victim),bord);
                numOfCapturedPieces = (captureValueCount(bord,cCapture)) + (captureValueCount(bord, autoCaptures.get(i)) - 1);// ***
                if (!cCapture.isEmpty()) numOfCapturedPieces -= 1;
                if(captureTopRate < numOfCapturedPieces - numOfLostPieces) {
                    captureFind = true;
                    topCapture = i;
                    captureTopRate = numOfCapturedPieces - numOfLostPieces;
                } else if (captureTopRate  == numOfCapturedPieces - numOfLostPieces) {
                    if(autoCaptures.get(i).get(autoCaptures.get(i).size() - 1)[0] > autoCaptures.get(topCapture).get(autoCaptures.get(topCapture).size() - 1)[0]){
                        topCapture = i;
                    }
                }
                bord.loadBordFromJson(bord.saveBordJson);
            }
            if ((!autoCaptures.isEmpty()) && (!captureFind)){
                this.captureTopRate += 1;
            }
            topOne = autoCaptures.get(topCapture);
        }
        return topOne;
    }

    public List<int[]> findBestfreemovement(Bord bord , List<List<int[]>> freeMovements,String attacker,String victim)throws Exception{
        List<int[]> topOne = new ArrayList<>();
        List<List<int[]>> topmovements = new ArrayList<>();
        if(!freeMovements.isEmpty()) {
            saveBordJsonForFreeMovement = bord.savaBordJson();

            Tools newTools = new Tools();
            int numOfLostPieces;
            int minCapture = 0;
            for (int i = 0 ; i < freeMovements.size();i++) {
                newpolice.checkPoint1(bord,bord.player1); // police

                // first attack

                arrayMove(bord, attacker, freeMovements.get(i).stream().map(array -> Arrays.copyOf(array, array.length)).collect(Collectors.toList()));
                newpolice.checkPoint2(bord,bord.lastMovementEndPosition,bord.lastMovementDid,bord.lastMovementIsCapture); // police

                // return attack

                List<List<int[]>> pCaptures = newTools.collectAllCapture(bord, newTools.findCapturePieces(bord, victim, attacker),victim,attacker);
                numOfLostPieces = captureValueCount(bord,findMaxLenList(pCaptures,bord)); //


                if ( i == 0 || numOfLostPieces < minCapture) {
                    minCapture = numOfLostPieces;
                    topmovements = new ArrayList<>();
                }
                if(numOfLostPieces == minCapture) {
                    topmovements.add(freeMovements.get(i));
                }
                bord.loadBordFromJson(saveBordJsonForFreeMovement);

            }
            topOne = frontPieceMoveFirst(bord,topmovements,attacker);

        }
        return topOne;
    }

    public List<int[]> findATrap(Bord bord , List<List<int[]>> movements,String player1, String player2)throws Exception{
        List<int[]> topOne = new ArrayList<>();
        this.trapTopRate = -1;
        if(!movements.isEmpty()) {
            bord.saveBordJson = bord.savaBordJson();
            Tools newTools = new Tools();
            int topTrap = -1;
            List<int[]> cCapture;
            List<int[]> pCapture;
            int numOfCapturedPieces;
            int numOfLostPieces ;
            for (int i = 0 ; i < movements.size();) {
                newpolice.checkPoint1(bord,player1); // police
                arrayMove(bord,player1, movements.get(i).stream().map(array -> Arrays.copyOf(array, array.length)).collect(Collectors.toList()));
                newpolice.checkPoint2(bord,bord.lastMovementEndPosition,bord.lastMovementDid,bord.lastMovementIsCapture); // police
                List<List<int[]>> maxLenLists = findMaxLenListOrLists( newTools.collectAllCapture(bord, newTools.findCapturePieces(bord,player2, player1),player2,player1));
                //pCapture = findMaxLenListOrLists( newTools.collectAllCapture(bord, newTools.findCapturePieces(bord, bord.player2, bord.player1), bord.player2, bord.player1));
                if (maxLenLists.size() == 1){
                    pCapture = maxLenLists.get(0);
                }
                else {
                    pCapture = new ArrayList<>();
                }
                numOfLostPieces = captureValueCount(bord,pCapture); // ***
                newpolice.checkPoint1(bord,player1); // police
                if (numOfLostPieces > 0) {
                    numOfLostPieces -= 1; // **
                    arrayMove(bord,player2, pCapture);
                }else {
                    List<int[]>best = findBestfreemovement(bord,newTools.scanMoveablePieces(bord,player2),player2,player1);
                    arrayMove(bord,player2,best);
                }
                newpolice.checkPoint2(bord,bord.lastMovementEndPosition,bord.lastMovementDid,bord.lastMovementIsCapture); // police
                cCapture = findMaxLenList(newTools.collectAllCapture(bord,findCapturePieces(bord,player1,player2),player1,player2),bord);
                numOfCapturedPieces = captureValueCount(bord,cCapture); // ***;
                if(!cCapture.isEmpty()) numOfCapturedPieces -=1; // **
                if (captureFind) numOfLostPieces += 1;
                if (numOfCapturedPieces > numOfLostPieces)
                    if (this.trapTopRate < numOfCapturedPieces - numOfLostPieces) {
                        topTrap = i;
                        this.trapTopRate = numOfCapturedPieces - numOfLostPieces;
                    }
                bord.loadBordFromJson(bord.saveBordJson);
                i++;
            }
            if(topTrap != -1){
                topOne = movements.get(topTrap);
            }
        }
        return topOne;
    }

    public List<int[]> getRandomMovement(List<List<int[]>> movements){
        Random random = new Random();
        int randomNumber = random.nextInt(movements.size());
        return movements.get(randomNumber);
    }

    public List<int[]> frontPieceMoveFirst(Bord bord ,List<List<int[]>> movements,String attacker){
        List<List<int[]>> bestMovement = new ArrayList<>();
        if(attacker.equals(bord.player1)) {
            int min = 8;
            for (List<int[]> movement : movements) {
                if (movement.get(0)[0] > min) {
                    min = movement.get(0)[0];
                    bestMovement = new ArrayList<>();
                    bestMovement.add(movement);
                } else if (movement.get(0)[0] == min) {
                    bestMovement.add(movement);
                }
            }
        }
        else {
            int min = 3;
            for (List<int[]> movement : movements) {
                if (movement.get(0)[0] < min) {
                    min = movement.get(0)[0];
                    bestMovement = new ArrayList<>();
                    bestMovement.add(movement);
                } else if (movement.get(0)[0] == min) {
                    bestMovement.add(movement);
                }
            }
        }

        if(bestMovement.isEmpty()){
           return getRandomMovement(movements);
        }else{
            return getRandomMovement(bestMovement);
        }

    }

    public List<int[]> play(Bord bord,String player1,String player2) throws Exception{
        List<int[]> bestOne;
        List<int[]>bestCapture = findBestCapture(bord,collectAllCapture(bord,findCapturePieces(bord,player1,player2),player1,player2),player1,player2);
        System.out.println("bestCapture = "+bestCapture);
        scanMoveablePieces(bord,player1);
        List<int[]>bestTrap = findATrap(bord,this.freeMovement,player1,player2);
        System.out.println("bestTrap = "+bestTrap);

        System.out.println("captureTopRate = "+captureTopRate);
        System.out.println("trapTopRate = "+trapTopRate);

        if(captureTopRate >= 0 || trapTopRate >= 0){
            if((captureTopRate >= trapTopRate )|| lastMovementIsTrap){
                bestOne = new ArrayList<>(bestCapture);
                arrayMove(bord,player1,bestCapture);
                lastMovementIsTrap = false;
            }else {
                bestOne = new ArrayList<>(bestTrap);
                arrayMove(bord,player1, bestTrap);
                lastMovementIsTrap = true;


            }
        }else {
            List<int[]>best = findBestfreemovement(bord,this.freeMovement,player1,player2);
            System.out.println("best = "+best);
            bestOne = new ArrayList<>(best);
            arrayMove(bord,player1,best);
            lastMovementIsTrap = false;

        }
        return bestOne;

    }


}

