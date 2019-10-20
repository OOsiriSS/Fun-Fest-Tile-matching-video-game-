package kienaiyo.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import kienaiyo.MainApp;

public class startingpageController {

	@FXML
	private Button start;
	@FXML
	private Button help;
	@FXML
	private Button exit;
	
	private MainApp mainApp;
	
	/**
	 * the original constructor
	 */
	public startingpageController(){
		
	}

	public void startGame(){
		mainApp.loadsavePage();
	}
	
	public void setMainapp(MainApp mainApp){
		this.mainApp=mainApp;
	}
	
	public void exitgame(){
		System.exit(1);
	}
	
}
