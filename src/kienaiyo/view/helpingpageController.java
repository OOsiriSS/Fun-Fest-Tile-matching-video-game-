package kienaiyo.view;

import kienaiyo.MainApp;
import kienaiyo.save.Save;

public class helpingpageController {
	private MainApp mainApp;
	
	
	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
	}
	
	public void back(){
		mainApp.backToHomePage();
	}
	public void next(){
		mainApp.loadNextHelpingPage();
	}
}
