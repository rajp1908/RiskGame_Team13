package com.risk6441.controller;
import java.io.File;
import java.io.IOException;

import com.risk6441.configuration.Configuration;
import com.risk6441.entity.Map;
import com.risk6441.exception.InvalidMap;
import com.risk6441.main.Main;
import com.risk6441.maputilities.CommonMapUtilities;
import com.risk6441.maputilities.MapReader;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This class controls the behavior of the main screen and allow users to select:
 * <ul>
 * <li>Play Game</li>
 * <li>Edit Map</li>
 * <li>Exit</li> 
 * </ul>
 * @author Hardik
 * @author Raj
 * @author Jemish
 *
 */
public class MainController {

    @FXML 
    private Button btnPlayGame;

    @FXML 
    private Button btnMapEditor; 

    @FXML 
    private Button btnExit; 
    
    /**
     * This method handles the case when user clicks the Map Edit Button.
     * @param event event object for the javafx 
     * @throws IOException Produces an IOException.
     */
    @FXML
    void editMap(ActionEvent event) throws IOException {
    	Stage primaryStage = (Stage) btnExit.getScene().getWindow();    	
    	Pane mainPane = (Pane) FXMLLoader.load(Main.class.getResource("/mapeditorsplash.fxml"));
    	Stage stage = new Stage();
    	stage.setScene(new Scene(mainPane));
    	stage.setX(primaryStage.getX() + 200);
    	stage.setY(primaryStage.getY() + 200);
    	stage.show();
    }
    
    /**
     * This method handles the case when user clicks the exit button
     * @param event event object for the javafx 
     */
    @FXML
    void exit(ActionEvent event) {
    	//get scene of that button and close it
    	Stage stage = (Stage) btnExit.getScene().getWindow();
    	stage.close();
    	stage.setOnCloseRequest(e -> Platform.exit());
    }

    /**
     * This method handles the case when user clicks the Play Game button
     * @param event event event object for the javafx 
     * @throws InvalidMapException Throws invalid map exception.
     * @throws IOException Throws IOException.
     */
    @FXML
    void playGame(ActionEvent event) throws InvalidMap, IOException {
    	
    	File file= CommonMapUtilities.openMapFile();
    	//get map object by reading file
    	Configuration.isPopUpShownInAutoMode = true;
    	
    	MapReader mapReader = new MapReader();
    	Map map = null;
    	try {
    		map = mapReader.readMapFile(file);
    		System.out.println(map);
    	}catch (InvalidMap e) {
    		e.printStackTrace();
    		CommonMapUtilities.alertBox("Error", e.getMessage(), "Map is not valid.");
    		return;
    	}
    	Stage primaryStage = (Stage) btnExit.getScene().getWindow();
    	
    	PlayGameController controller = new PlayGameController(map);
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("gameplay.fxml"));
		loader.setController(controller);
		
		Parent root = null;
		try {
			root = (Parent) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage = new Stage();
		Scene scene = new Scene(root);
		stage.setScene(scene);
    	stage.setX(primaryStage.getX() + 200);
    	stage.setY(primaryStage.getY() + 200);
		stage.show();
    }
}
