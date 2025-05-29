package com.janith.checkersgame;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bord extends PrintColor{

    final private String[][] bordColors = { {"white", "black", "white", "black", "white", "black", "white", "black", "white", "black", "white", "black"},
                                            {"black", "white", "black", "white", "black", "white", "black", "white", "black", "white", "black", "white"},
                                            {"white", "black", "white", "black", "white", "black", "white", "black", "white", "black", "white", "black"},
                                            {"black", "white", "black", "white", "black", "white", "black", "white", "black", "white", "black", "white"},
                                            {"white", "black", "white", "black", "white", "black", "white", "black", "white", "black", "white", "black"},
                                            {"black", "white", "black", "white", "black", "white", "black", "white", "black", "white", "black", "white"},
                                            {"white", "black", "white", "black", "white", "black", "white", "black", "white", "black", "white", "black"},
                                            {"black", "white", "black", "white", "black", "white", "black", "white", "black", "white", "black", "white"},
                                            {"white", "black", "white", "black", "white", "black", "white", "black", "white", "black", "white", "black"},
                                            {"black", "white", "black", "white", "black", "white", "black", "white", "black", "white", "black", "white"},
                                            {"white", "black", "white", "black", "white", "black", "white", "black", "white", "black", "white", "black"},
                                            {"black", "white", "black", "white", "black", "white", "black", "white", "black", "white", "black", "white"}
    };
    public List<List<String>> bordPosition = new ArrayList<>();
    public List<List<Piece>> arrangedBord = new ArrayList<>();
    public String player1 ; // computer
    public String player2 ;
    public String freeMovementBordSavePath = "freeSavedBord.json";
    public int[] lastMovementEndPosition ;
    public int[] lastMovementPiecePosition;
    public  String lastMovementDid;
    public boolean lastMovementIsCapture;
    public String printMassage = "";
    public String previousBordJson;
    public String saveBordJson;
    public List<int[]> lastDeletedPieces;

    public Bord(String player1, String player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.lastMovementDid = player1;

        this.bordPosition.add(Arrays.asList("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8","a9","a10","a11","a12"));
        this.bordPosition.add(Arrays.asList("b1", "b2", "b3", "b4", "b5", "b6", "b7", "b8","b9","b10","b11","b12"));
        this.bordPosition.add(Arrays.asList("c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8","c9","c10","c11","c12"));
        this.bordPosition.add(Arrays.asList("d1", "d2", "d3", "d4", "d5", "d6", "d7", "d8","d9","d10","d11","d12"));
        this.bordPosition.add(Arrays.asList("e1", "e2", "e3", "e4", "e5", "e6", "e7", "e8","e9","e10","e11","e12"));
        this.bordPosition.add(Arrays.asList("f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8","f9","f10","f11","f12"));
        this.bordPosition.add(Arrays.asList("g1", "g2", "g3", "g4", "g5", "g6", "g7", "g8","g9","g10","g11","g12"));
        this.bordPosition.add(Arrays.asList("h1", "h2", "h3", "h4", "h5", "h6", "h7", "h8","h9","h10","h11","h12"));
        this.bordPosition.add(Arrays.asList("i1", "i2", "i3", "i4", "i5", "i6", "i7", "i8","i9","i10","i11","i12"));
        this.bordPosition.add(Arrays.asList("j1", "j2", "j3", "j4", "j5", "j6", "j7", "j8","j9","j10","j11","j12"));
        this.bordPosition.add(Arrays.asList("k1", "k2", "k3", "k4", "k5", "k6", "k7", "k8","k9","k10","k11","k12"));
        this.bordPosition.add(Arrays.asList("l1", "l2", "l3", "l4", "l5", "l6", "l7", "l8","l9","l10","l11","l12"));

       // Arrange the bord
       int count = 0;
       for(String[] row : bordColors) {
           count++;
           List<Piece> pieces = new ArrayList<>();

               for (String rect : row) {
                   if (count == 6 || count == 7){
                       pieces.add(new EmptyPiece());
                   }
                   else if (rect.equals("white")){
                       if(count < 6 ) pieces.add(new Piece(player1));
                       else pieces.add(new Piece(player2));
                   }else{
                       pieces.add(new EmptyPiece());
                   }
               }
           arrangedBord.add(pieces);
       }
    }

     public MyCenter<String, Integer, String, String> centerString = (str, range, repeatCharactor ) -> {
        // Define the range here
        return repeatCharactor.repeat((range - str.length()) / 2) + str + repeatCharactor.repeat((range - str.length()) / 2 + (range - str.length()) % 2);
    };

    public StringCenterWithClolors<String, Integer, String, String, String, String,String> centerStringwithColor = (str, range, repeatCharactor,strcolor,strBackground,rCBackground ) -> {
        return rCBackground + repeatCharactor.repeat((range - str.length()) / 2)+ RESET + strcolor + strBackground + str+ RESET + rCBackground + repeatCharactor.repeat((range - str.length()) / 2 + (range - str.length()) % 2 ) + RESET ;
    };

    public int[] findPositionIndex(String position) {
        for (int i = 0; i < bordPosition.size(); i++) {
            List<String> row = bordPosition.get(i);
            for (int j = 0; j < row.size(); j++) {
                if (row.get(j).equals(position)) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1}; // Position not found
    }
    public void printBord() {
        int rectWidth = 8;
        int count = 0;
        System.out.println("+"+"-".repeat(rectWidth*12+11)+"+");
        for (String[] row : bordColors) {
            for(int i = 0; i < 2 ; i++){
                System.out.print("|");
                int count1 = 0;
                for (String rect : row) {
                    if(i == 0) {
                        if(rect.equals("white")) System.out.print(BLUE+centerString.applay(bordPosition.get(count).get(count1),rectWidth," ")+RESET+"|");
                        else System.out.print(centerString.applay(bordPosition.get(count).get(count1),rectWidth," ")+"|");
                    }
                    else if(i == 1) {
                        if(arrangedBord.get(count).get(count1).team.equals(this.player1)) {
                            if (arrangedBord.get(count).get(count1).superPrivilege)
                                System.out.print(PURPLE + centerString.applay(arrangedBord.get(count).get(count1).toString(), rectWidth, " ") + RESET + "|");
                            else
                                System.out.print(RED + centerString.applay(arrangedBord.get(count).get(count1).toString(), rectWidth, " ") + RESET + "|");
                        }
                        else if(arrangedBord.get(count).get(count1).team.equals(this.player2)) {
                            if (arrangedBord.get(count).get(count1).superPrivilege)
                                System.out.print(CYAN + centerString.applay(arrangedBord.get(count).get(count1).toString(), rectWidth, " ") + RESET + "|");
                            else
                                System.out.print(GREEN + centerString.applay(arrangedBord.get(count).get(count1).toString(), rectWidth, " ") + RESET + "|");
                        }
                        else
                            System.out.print(centerString.applay(arrangedBord.get(count).get(count1).toString(),rectWidth," ")+"|");
                    }
                    count1++;
                }
                System.out.println();
            }
            System.out.println("+"+"-".repeat(rectWidth*12+11)+"+");
            count++;
        }
    }

    public void printPlayBord(){
        System.out.println(GREEN_BACKGROUND+"    "+ RESET);
        int rectWidth = 9;
        int scoreBordAndPlayBordGap = 20;
        int scoreBordHeadingWidth = 40;
        int count = 0;
        for (String[] row : bordColors) {
            for(int i = 0; i < 3 ; i++){
                int count1 = 0;
                for(String rect : row) {
                    if(rect.equals("white")) {
                        String pieceColor = "";
                        final int c = count;
                        final int c1 = count1;
                        if(arrangedBord.get(count).get(count1).team.equals(player1)) {
                            if (arrangedBord.get(count).get(count1).superPrivilege) pieceColor = PURPLE_BACKGROUND;
                            else pieceColor = RED_BACKGROUND;
                            if (i == 1) {
                                System.out.print(centerStringwithColor.applay("     ", rectWidth, " ", BLACK, pieceColor, WHITE_BACKGROUND));
                            } else if (i == 2) {
                                System.out.print(centerStringwithColor.applay(centerString.applay(bordPosition.get(count).get(count1), 5, " "), rectWidth, " ", BLACK, pieceColor, WHITE_BACKGROUND));
                            } else {
                                System.out.print(centerStringwithColor.applay("", rectWidth, " ", "", "", WHITE_BACKGROUND));
                            }
                        }
                        else if(!arrangedBord.get(count).get(count1).team.equals("none")){
                            if(arrangedBord.get(count).get(count1).superPrivilege) pieceColor = GREEN_BACKGROUND;
                            else pieceColor = YELLOW_BACKGROUND;
                            if (i == 1) {
                                System.out.print(centerStringwithColor.applay("     ", rectWidth, " ", BLACK, pieceColor, WHITE_BACKGROUND));
                            } else if (i == 2) {
                                System.out.print(centerStringwithColor.applay(centerString.applay(bordPosition.get(count).get(count1), 5, " "), rectWidth, " ", BLACK, pieceColor, WHITE_BACKGROUND));
                            } else
                                System.out.print(centerStringwithColor.applay("", rectWidth, " ", "", "", WHITE_BACKGROUND));
                        }
                        else {
                            if (lastDeletedPieces.stream().anyMatch(array -> Arrays.equals(array, new int[]{c, c1}))) {
                                if (i == 1) {
                                    System.out.print(centerStringwithColor.applay("|" + centerString.applay(" ", 5, " ") + "|", rectWidth, " ", BLACK, WHITE_BACKGROUND, WHITE_BACKGROUND));
                                } else if (i == 2) {
                                    System.out.print(centerStringwithColor.applay("|" + centerString.applay(bordPosition.get(count).get(count1), 5, " ") + "|", rectWidth, " ", BLACK, WHITE_BACKGROUND, WHITE_BACKGROUND));
                                } else {

                                    System.out.print(centerStringwithColor.applay("+" + centerString.applay("-", 5, "-") + "+", rectWidth, " ", BLACK, WHITE_BACKGROUND, WHITE_BACKGROUND));
                                }
                            }else if (i == 2)
                                System.out.print(centerStringwithColor.applay(bordPosition.get(count).get(count1), rectWidth, " ",BLACK, WHITE_BACKGROUND, WHITE_BACKGROUND));
                            else
                                System.out.print(centerStringwithColor.applay("", rectWidth, " ", "", "", WHITE_BACKGROUND));
                        }
                    }
                    else System.out.print(centerString.applay("", rectWidth, " "));
                    count1++;
                }
                // score bord
                if (count == 0 ){
                    if (i == 0) {
                        System.out.print(" ".repeat(scoreBordAndPlayBordGap));
                        System.out.print(centerStringwithColor.applay("Live Score", scoreBordHeadingWidth, " ", BLUE,"",""));
                    }else if(i ==1){
                        System.out.print(" ".repeat(scoreBordAndPlayBordGap));
                        System.out.print(centerStringwithColor.applay(player1,20," ",RED,"","")+centerStringwithColor.applay(player2,20," ",YELLOW,"",""));
                    }else if(i == 2){
                        System.out.print(" ".repeat(scoreBordAndPlayBordGap));
                        System.out.print(centerStringwithColor.applay(String.valueOf(getScore(player1)),20," ",RED,"","")+centerStringwithColor.applay(String.valueOf(getScore(player2)),20," ",YELLOW,"",""));
                    }
                }else if (count == 1){
                    if(i== 0){
                        System.out.print(" ".repeat(scoreBordAndPlayBordGap));
                        System.out.print(GREEN+printMassage+RESET);
                    }
                }
                System.out.println();
            }
            count++;
        }
    }

    public int getScore(String player){
        int pCount = 0;
        for(List<Piece> row : arrangedBord){
            for(Piece piece : row){
                if ((!piece.team.equals(player)) && (!piece.team.equals("none"))){
                    pCount++;
                }
            }
        }
        int score = 30 - pCount;
        return score;
    }

    public void saveBord(String filePath){
        // Convert ArrayList to JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(arrangedBord);
        // Write JSON string to file
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(json);
            //System.out.println("Bord Saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String generateBordJsonFromString() {
        Gson gson = new GsonBuilder().create();
        Type arrayListType = new TypeToken<List<List<List<Piece>>>>() {}.getType(); // Replace Piece with the actual type
        // Parse the existing JSON string
        List<List<List<Piece>>> existingBord = gson.fromJson(this.previousBordJson, arrayListType); // Replace Piece with the actual type
        if (existingBord == null) {
            existingBord = new ArrayList<>();
        }
        // Append new list to the existing data
        existingBord.add(arrangedBord);
        if(existingBord.size() > 5){
            existingBord.remove(0);
        }
        // Convert the updated list to JSON
        Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
        String updatedJson = gsonPretty.toJson(existingBord);
        // Update previousBordJson with the latest JSON string
        this.previousBordJson = updatedJson;
        // Return the updated JSON string
        return updatedJson;
    }

    public String getBordJsonFromString() {
        Gson gson = new GsonBuilder().create();
        Type arrayListType = new TypeToken<List<List<List<Piece>>>>() {}.getType(); // Replace Piece with the actual type
        // Parse the existing JSON string
        List<List<List<Piece>>> existingBord = gson.fromJson(this.previousBordJson, arrayListType); // Replace Piece with the actual type

        if (existingBord == null) {
            existingBord = new ArrayList<>();
        }

        int count= 0;
        for (List<Piece> row : existingBord.get(existingBord.size()-1)){
            int count1 = 0;
            for(Piece piece : row){
                if(piece.team.equals("none")){
                    this.arrangedBord.get(count).set(count1 , new EmptyPiece());
                }else {
                    this.arrangedBord.get(count).set(count1 , new Piece(piece.team));
                    this.arrangedBord.get(count).get(count1).superPrivilege = piece.superPrivilege;
                }
                count1++;
            }
            count++;
        }
        existingBord.remove(existingBord.size()-1);
        // Convert the updated list to JSON
        Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
        String updatedJson = gsonPretty.toJson(existingBord);
        // Update previousBordJson with the latest JSON string
        this.previousBordJson = updatedJson;
        // Return the updated JSON string
        return updatedJson;
    }

    public String savaBordJson() {
        // Convert the updated list to JSON
        Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
        String updatedJson = gsonPretty.toJson(arrangedBord);
        // Return the updated JSON string
        return updatedJson;
    }

    public void loadBordFromJson(String saveBordJson) {
        Gson gson = new GsonBuilder().create();
        Type arrayListType = new TypeToken<List<List<Piece>>>() {}.getType(); // Replace Piece with the actual type
        List<List<Piece>> bord = gson.fromJson(saveBordJson, arrayListType);
        int count= 0;
        for (List<Piece> row : bord){
            int count1 = 0;
            for(Piece piece : row){
                if(piece.team.equals("none")){
                    this.arrangedBord.get(count).set(count1 , new EmptyPiece());
                }else {
                    this.arrangedBord.get(count).set(count1 , new Piece(piece.team));
                    this.arrangedBord.get(count).get(count1).superPrivilege = piece.superPrivilege;
                }
                count1++;
            }
            count++;
        }
    }

    public void loadBord(String filePath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder jsonStringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonStringBuilder.append(line);
            }
            // Convert JSON string
            Gson gson = new GsonBuilder().create();
            Type arrayListType = new com.google.gson.reflect.TypeToken<List<List<Piece>>>(){}.getType();
            List<List<Piece>> newList = gson.fromJson(jsonStringBuilder.toString(), arrayListType);
            int count= 0;
            for (List<Piece> row : newList){
                int count1 = 0;
                for(Piece piece : row){
                    if(piece.team.equals("none")){
                        this.arrangedBord.get(count).set(count1 , new EmptyPiece());
                    }else {
                        this.arrangedBord.get(count).set(count1 , new Piece(piece.team));
                        this.arrangedBord.get(count).get(count1).superPrivilege = piece.superPrivilege;
                    }
                    count1++;
                }
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("bord loding incomplete !!!");
        }
    }

    public void clearConsole() {
        // This command will clear the console
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}



