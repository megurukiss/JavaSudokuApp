package com.meguru.maven.mysudoku.userinterface;

import com.meguru.maven.mysudoku.gamelogic.GameState;
import com.meguru.maven.mysudoku.gamelogic.SudokuGame;
import com.meguru.maven.mysudoku.gamelogic.SudokuGenerator;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.io.*;
import java.util.Arrays;

public class ButtonHandler implements EventHandler {
    private SudokuGame game;
    private UserInterface ui;
    private final File file = new File(System.getProperty("user.dir"),"game.txt");
    public ButtonHandler(UserInterface ui) {
        super();
        this.ui = ui;
    }

    private void setGame(UserInterface ui) {
        this.game = ui.getGameFromBoard();
    }

    @Override
    public void handle(Event event) {
        Button source = (Button) event.getSource();
        String buttonText = source.getText();
        setGame(ui);
        if(buttonText.equals("New Game")) {
            createNewGame();
        } else if (buttonText.equals("Save")) {
            saveGame();
        } else if (buttonText.equals("Load")) {
            loadGame();
        }
    }

    private void createNewGame() {
        game = new SudokuGame(GameState.NEW, SudokuGenerator.generateGame());
        ui.updateBoard(game);
    }

    private void saveGame() {
        // Save the game to file
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(game);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadGame() {
        // Load the game from file
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            SudokuGame game = (SudokuGame) ois.readObject();
            ois.close();
            ui.updateBoard(game);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
