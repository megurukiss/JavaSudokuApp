package com.meguru.maven.mysudoku.userinterface;

import com.meguru.maven.mysudoku.gamelogic.GameState;
import com.meguru.maven.mysudoku.gamelogic.SudokuGame;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Background;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.meguru.maven.mysudoku.userinterface.Coordinate;
import com.meguru.maven.mysudoku.userinterface.SudokuTextField;
import com.meguru.maven.mysudoku.userinterface.InterfaceContract;
import java.util.HashMap;
import javafx.scene.paint.Color;

public class UserInterface implements InterfaceContract.View{

    private final Stage stage;
    private final Group root;
    private HashMap<Coordinate, SudokuTextField> textFieldCoordinates;
    private KeyEventHandler handler;

    private static final double WINDOW_Y = 732;
    private static final double WINDOW_X = 668;
    private static final double BOARD_PADDING = 50;
    private static final double BOARD_Width = 576;
    private static final Color WINDOW_BACKGROUND_COLOR = Color.rgb(0, 150, 136);
    private static final Color BOARD_BACKGROUND_COLOR = Color.rgb(224, 242, 241);
    private static final String SUDOKU = "Sudoku";

    public UserInterface(Stage stage) {
        this.stage = stage;
        this.root = new Group();
        this.textFieldCoordinates = new HashMap<>();
        this.handler = new KeyEventHandler(this);
        initializeUserInterface();
    }

    private void initializeUserInterface() {
        drawBackground(root);
        drawSudokuBoard(root);
        drawTitle(root);
        drawTextField(root);
        drawGridLines(root);
        drawBorders(root);
        stage.show();
    }

    private void drawBackground(Group root) {
        Scene scene = new Scene(root, WINDOW_X, WINDOW_Y);
        scene.setFill(WINDOW_BACKGROUND_COLOR);
        stage.setScene(scene);
    }

    private void drawSudokuBoard(Group root){
        Rectangle boardBackground = new Rectangle();
        boardBackground.setX(BOARD_PADDING);
        boardBackground.setY(BOARD_PADDING);
        boardBackground.setWidth(BOARD_Width);
        boardBackground.setHeight(BOARD_Width);
        boardBackground.setFill(BOARD_BACKGROUND_COLOR);
        root.getChildren().add(boardBackground);
    }

    private void drawTitle(Group root) {
        Text title = new Text(235, 690, SUDOKU);
        title.setFill(Color.WHITE);
        Font titleFont = new Font(43);
        title.setFont(titleFont);
        root.getChildren().add(title);
    }

    private void drawTextField(Group root){
        final int xOrigin = 50;
        final int yOrigin = 50;
        final int spaceWithin=64;

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                int x= xOrigin + i*spaceWithin;
                int y= yOrigin + j*spaceWithin;
                SudokuTextField tile = new SudokuTextField(i,j);

                Font numberFont = new Font(32);
                tile.setFont(numberFont);
                tile.setAlignment(Pos.CENTER);
                tile.setLayoutX(x);
                tile.setLayoutY(y);
                tile.setPrefHeight(spaceWithin);
                tile.setPrefWidth(spaceWithin);
                tile.setBackground(Background.EMPTY);

                tile.setOnKeyPressed(handler);
                Coordinate coordinate = new Coordinate(i,j);
                textFieldCoordinates.put(coordinate, tile);
                root.getChildren().add(tile);
            }
        }
    }

    private void drawGridLines(Group root){

        int xyStart= 50+64;
        int index=0;

        while(index<8){
            int thickness;
            if(index==2 || index==5){
                thickness=3;
            }else{
                thickness=2;
            }
            Rectangle verticalLine = getLine(xyStart+64*index, BOARD_PADDING, BOARD_Width, thickness);
            Rectangle horizontalLine = getLine(BOARD_PADDING, xyStart+64*index,thickness,BOARD_Width );
            root.getChildren().addAll(verticalLine, horizontalLine);
            index++;
        }

    }
    private void drawBorders(Group root){
        Rectangle verticalLeft = getLine(BOARD_PADDING, BOARD_PADDING, BOARD_Width, 3);
        Rectangle verticalRight = getLine(BOARD_PADDING+BOARD_Width, BOARD_PADDING, BOARD_Width, 3);
        Rectangle horizontalTop = getLine(BOARD_PADDING, BOARD_PADDING,3, BOARD_Width);
        Rectangle horizontalBottom = getLine(BOARD_PADDING, BOARD_PADDING+BOARD_Width,3, BOARD_Width);
        root.getChildren().addAll(verticalLeft, verticalRight, horizontalTop, horizontalBottom);
    }

    private Rectangle getLine(double x, double y, double height, double width){
        Rectangle line = new Rectangle();
        line.setX(x);
        line.setY(y);
        line.setHeight(height);
        line.setWidth(width);
        line.setFill(Color.BLACK);
        return line;
    }

    @Override
    public int[][] getBoard(){
        int[][] board = new int[9][9];
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                SudokuTextField tile = textFieldCoordinates.get(new Coordinate(i,j));
                try{
                    board[i][j] = Integer.parseInt(tile.getText());
                }catch (NumberFormatException e){
                    board[i][j] = 0;
                }
            }
        }
        return board;
    }

    @Override
    public void updateSquare(int x, int y, int input) {
        SudokuTextField tile = textFieldCoordinates.get(new Coordinate(x, y));
        String value = Integer.toString(
                input
        );
        if (value.equals("0")) value = "";
        tile.textProperty().setValue(value);
    }

    @Override
    public void updateBoard(SudokuGame game) {
        int[][] board = game.getGridState();
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                SudokuTextField tile = textFieldCoordinates.get(new Coordinate(i,j));
                String value = Integer.toString(board[i][j]);

                if (value.equals("0")) value = "";
                tile.setText(value);
                if(game.getGameState()== GameState.NEW){
                    if(value.equals("")){
                        tile.setStyle("-fx-opacity: 1;");
                    }else{
                        tile.setStyle("-fx-opacity: 0.5;");
                        tile.setDisable(true);
                    }
                }
            }
        }
    }

    @Override
    public void displayMessage(String message) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        dialog.showAndWait();

        if (dialog.getResult() == ButtonType.OK) handler.onDialogClick();
    }

    @Override
    public void displayError(String message) {
        Alert dialog = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        dialog.showAndWait();
    }
}
