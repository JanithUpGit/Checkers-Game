package com.janith.checkersgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;



/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Checkers extends PlayerMovement {
    public static int lastCapturePieceIndex;
    private OrthographicCamera camera;
    public float VIEWPORT_WIDTH = 1300f;
    public float VIEWPORT_HEIGHT = 1000f;
    public SpriteBatch batch;
    private Texture bordTexture;
    private Texture computerSwitchTexture;
    private Texture playerSwitchTexture;
    private Texture playerClickSwitchTexture;
    private Texture youWinTexture;
    private Texture youLostTexture;

    float width ;
    float height ;
    float pieceSize = 80;
    private Viewport viewport;
    private boolean dragging;
    private Vector2 dragOffset;
    Sprite blackPieceSprite;
    public static List<Vector2> piecePositions;
    static List<List<Vector2>> bordPositions;
    public static List<Sprite> pieces;
    PieceMaker maker;
    private int draggingPieceIndex;
    public Vector2 draggedPosition;
    public ListTools listTools;
    public PieceMovement pieceMovement ;
    public static boolean computerMovementDone = false;
    public static boolean playerMovementDone = false;
    public static boolean computerMovementComplete = false;
    public Bord bord;
    Player gamePlayer;
    Computer computer;
    Police police;
    static int[] arrestedPiecePositionForComputer;
    static int[] arrestedPiecePositionForPlayer = new int[0];

    List<int[]>playerMovement;

    public static Vector2  lastCaptureDonePiece ;

    public static boolean lastMovementIsACapture = false;

    public static boolean SwitchClick = false;
    public static boolean gameEnded = false;
    Vector2 lastTouchPos;

    public Tools bordTools;

    @Override
    public void create() {

        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(VIEWPORT_WIDTH,VIEWPORT_HEIGHT* height/width);
        camera.position.set(500,500,0);
        camera.update();
        viewport = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, camera);
        batch = new SpriteBatch();
        bordTexture = new Texture(Gdx.files.internal("bord.png"));
        computerSwitchTexture = new Texture(Gdx.files.internal("computer_switch.png"));
        playerSwitchTexture = new Texture(Gdx.files.internal("player_switch.png"));
        playerClickSwitchTexture = new Texture(Gdx.files.internal("click_switch.png"));
        youWinTexture = new Texture(Gdx.files.internal("you_win.png"));
        youLostTexture = new Texture(Gdx.files.internal("you_lose.png"));
        dragging = false;
        draggingPieceIndex= -1;
        dragOffset = new Vector2();

        maker = new PieceMaker();
        pieces = maker.piecesArrayMaker();
        // good luck !!!
        piecePositions = maker.PositionArrayMaker();
        bordPositions = maker.getBordPositions();
        listTools = new ListTools();
        pieceMovement = new PieceMovement();

        bord = new Bord("Computer","janith");
        gamePlayer = new Player("janith");
        computer = new Computer();
        police = new Police();
        lastTouchPos = new Vector2();
        bordTools = new Tools();


//        bord.arrangedBord.get(7).get(1).superPrivilege = true;
//        Checkers.pieces.set(30, new PieceMaker().pieceMaker("black_super_piece.png", "black_super"));
//
//
//        bord.arrangedBord.get(7).get(3).superPrivilege = true;
//        Checkers.pieces.set(31, new PieceMaker().pieceMaker("black_super_piece.png", "black_super"));
//
//        bord.arrangedBord.get(7).get(5).superPrivilege = true;
//        Checkers.pieces.set(32, new PieceMaker().pieceMaker("black_super_piece.png", "black_super"));
//
//        bord.arrangedBord.get(7).get(7).superPrivilege = true;
//        Checkers.pieces.set(33, new PieceMaker().pieceMaker("black_super_piece.png", "black_super"));




        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if(computerMovementComplete) {

                    Vector2 touchPos = new Vector2(screenX, screenY);
                    touchPos = viewport.unproject(touchPos);

                    if (1050 <= touchPos.x && touchPos.x <= 1150 && 400 <= touchPos.y && touchPos.y <= 630) {
                        System.out.println("computerSwitch clicked!");
                        SwitchClick = false;
                        if(lastMovementIsACapture) {
                            try {
                                if (!playerMovement.isEmpty()) {
                                    // check bord for find current have to captures
                                    police.checkPoint1(bord, bord.player2);
                                    System.out.println("player checkPoint1 done");
                                    gamePlayer.input(playerMovement, bord);
                                    System.out.println("player input pass to backend done");
                                    if(bord.getScore(bord.player1) >= 30 ){
                                        gameEnded = true;
                                    }
                                    arrestedPiecePositionForPlayer = police.checkPoint2(bord, bord.lastMovementEndPosition, bord.lastMovementDid, bord.lastMovementIsCapture);
                                    System.out.println("player checkPoint2 done");
                                    PlayerMovement.movement = new ArrayList<>();

                                }

                            } catch (Exception e) {
                                System.out.println("input Method error");
                                System.out.println(e);
                            }
                            if(arrestedPiecePositionForPlayer.length != 0){
                                int index = listTools.findPosition2D(Checkers.bordPositions.get(Checkers.arrestedPiecePositionForPlayer[0]).get(Checkers.arrestedPiecePositionForPlayer[1]),Checkers.piecePositions);
                                pieceMovement.startRemoveForPlayerArrestedPieces(index,removeDuration);
                                Checkers.arrestedPiecePositionForPlayer = new int[2];
                            }else {

                                Checkers.playerMovementDone = true;
                                Checkers.computerMovementDone = false;
                                Checkers.lastMovementIsACapture = false;
                            }

                            // last super end position set to null
                            lastSuperEndPosition = new Vector2();
                            lastSuperCameDirection = "" ;  // super came direction set to null




                        }

                        // Add your desired action here
                    }

                    for (int count = 0; count < piecePositions.size(); count++) {
                        Vector2 piecePos = piecePositions.get(count);
                        Sprite piece = pieces.get(count);
                        if (piecePos.x <= touchPos.x && touchPos.x <= piecePos.x + piece.getWidth() &&
                            piecePos.y <= touchPos.y && touchPos.y <= piecePos.y + piece.getHeight()) {

                            if(lastMovementIsACapture) {

                                if(piecePos.equals(lastCaptureDonePiece)) {
                                    dragging = true;
                                    draggingPieceIndex = count;
                                    draggedPosition = piecePos.cpy();
                                    dragOffset.set(touchPos.x - piecePos.x, touchPos.y - piecePos.y);

                                    pieces.add(pieces.remove(draggingPieceIndex));
                                    piecePositions.add(piecePositions.remove(draggingPieceIndex));
                                    draggingPieceIndex = pieces.size() - 1;

                                    break;
                                }else {
                                    break;
                                }

                            }else {

                                dragging = true;
                                draggingPieceIndex = count;
                                draggedPosition = piecePos.cpy();
                                dragOffset.set(touchPos.x - piecePos.x, touchPos.y - piecePos.y);

                                pieces.add(pieces.remove(draggingPieceIndex));
                                piecePositions.add(piecePositions.remove(draggingPieceIndex));
                                draggingPieceIndex = pieces.size() - 1;

                                break;
                            }
                        }
                        if (dragging) break;
                    }



                }


                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (dragging) {
                    Vector2 piecePos = piecePositions.get(draggingPieceIndex);
                    Vector2 touchPos = new Vector2(screenX, screenY);
                    NamedSprite piece = (NamedSprite) pieces.get(draggingPieceIndex);
                    playerMovement = move(piecePos,touchPos,piece,draggedPosition,piecePositions,pieces,bordPositions,pieceMovement);

                    if(!lastMovementIsACapture){
                        try {
                            if (!playerMovement.isEmpty()) {
                                // check bord for find current have to captures
                                police.checkPoint1(bord, bord.player2);
                                gamePlayer.input(playerMovement, bord);

                                if(bord.getScore(bord.player1) >= 30 ){
                                    gameEnded = true;
                                }

                                arrestedPiecePositionForPlayer = police.checkPoint2(bord, bord.lastMovementEndPosition, bord.lastMovementDid, bord.lastMovementIsCapture);
                                PlayerMovement.movement = new ArrayList<>();
                                bord.printPlayBord();
                                //playerMovementDone = true;
                                //computerMovementDone = false;
                            }

                        } catch (Exception e) {
                            System.out.println("input Method error");
                        }
                    }

                    dragging = false;
                    draggingPieceIndex = -1;
                    draggedPosition = new Vector2();

                }
                return true;
            }
            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (dragging) {
                    Vector2 touchPos = new Vector2(screenX, screenY);
                    touchPos = viewport.unproject(touchPos);
                    piecePositions.get(draggingPieceIndex).set(touchPos.x - dragOffset.x, touchPos.y - dragOffset.y);
                }
                return true;
            }

        });
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = VIEWPORT_WIDTH;
        camera.viewportHeight = VIEWPORT_HEIGHT * height / width;
        camera.update();
        //changeWindowSize(width,height);
        viewport.update(width,height);
        System.out.println(pieceSize);
        System.out.println("w "+width +" H "+height);

    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(bordTexture,0,0,1000,1000);

        if(computerMovementComplete) {
            if(SwitchClick){
                batch.draw(playerClickSwitchTexture, 1050, 400, 100, 230);
            }else {
                batch.draw(computerSwitchTexture, 1050, 400, 100, 230);
            }
        }else {
            batch.draw(playerSwitchTexture, 1050, 400, 100, 230);
        }



        for (int count = 0; count < pieces.size(); count++) {
            Sprite piece = pieces.get(count);
            Vector2 position = piecePositions.get(count);
            piece.setPosition(position.x, position.y);
            piece.draw(batch);
        }


        if(gameEnded){
            if(bord.getScore(bord.player2) >= 30){
                batch.draw(youWinTexture, 200, 250, 600, 400);
            }else {
                batch.draw(youLostTexture, 250, 250, 500, 500);
            }
        }

        batch.end();
    // player move and remove piece

        // Computer

        if (pieceMovement.movingPiece) {
            pieceMovement.move();
        }

        if (pieceMovement.removingPieceForComputer) {
            pieceMovement.removeForComputer();
        }

        if (pieceMovement.removingPieceForComputerArrested) {
            pieceMovement.removeForComputerArrestedPiece();
        }


        // Player

        if (PlayerMovement.movingPiece) {
            playerMove();
        }

        if (pieceMovement.removingPieceForPlayer) {
            pieceMovement.removeForPlayer();
        }


        if (pieceMovement.removingPieceRA) {
            pieceMovement.removeArrayForPlayer();
        }

        if (pieceMovement.removingPieceForPlayerArrested) {
            pieceMovement.removeForPlayerArrestedPiece();
        }



        // common

        if (pieceMovement.removingPiece) {
            pieceMovement.remove();
        }



        if(!computerMovementDone){

                computerMovementComplete = false;
                computerMove();
                computerMovementDone = true;
            
        }

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        // Dispose of all textures
        if (batch != null) {
            batch.dispose();
        }
        if (bordTexture != null) {
            bordTexture.dispose();
        }
        if (computerSwitchTexture != null) {
            computerSwitchTexture.dispose();
        }
        if (playerSwitchTexture != null) {
            playerSwitchTexture.dispose();
        }
        if (blackPieceSprite != null && blackPieceSprite.getTexture() != null) {
            blackPieceSprite.getTexture().dispose();
        }
        if (youWinTexture != null) {
            youWinTexture.dispose();
        }
        if (youLostTexture != null) {
            youLostTexture.dispose();
        }
        if(playerClickSwitchTexture != null){
            playerClickSwitchTexture.dispose();
        }

    }
    public void changeWindowSize(int newWidth, int newHeight) {
        float aspectRatio = VIEWPORT_WIDTH / VIEWPORT_HEIGHT;
        int adjustedHeight = (int) (newWidth / aspectRatio);

        if (adjustedHeight > newHeight) {
            newWidth = (int) (newHeight * aspectRatio);
            adjustedHeight = newHeight;
        }

        Gdx.graphics.setWindowedMode(newWidth, adjustedHeight);
    }

    public void computerMove(){

        if(bord.getScore(bord.player2) >= 30 ){
            gameEnded = true;
        }else {
            // first computer movement
            System.out.println("chance to computer to play ");

            try {
                police.checkPoint1(bord, bord.player1);
                List<int[]> computerMovement = computer.play(bord, bord.player1, bord.player2);
                System.out.println("backend computer move done");
                arrestedPiecePositionForComputer = police.checkPoint2(bord, bord.lastMovementEndPosition, bord.lastMovementDid, bord.lastMovementIsCapture);
                System.out.println("arrestedPiecePositionForComputer done");
                pieceMovement.startMove(computerMovement, bord.lastDeletedPieces, 0.5f);
                System.out.println("frontend computer move done");


                police.blast = new ArrayList<>();


            } catch (Exception e) {
                System.out.println("Computer - " + e);
                //System.out.println("Computer second try to play");
                System.out.println("GameEnded");
                    gameEnded = true;

            }
            bord.printPlayBord();
            System.out.println("Checkers class computer movement done");
        }
    }

}
