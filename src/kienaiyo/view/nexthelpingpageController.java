package kienaiyo.view;

import kienaiyo.MainApp;

public class nexthelpingpageController {
	private MainApp mainApp;
	
	public void setMainApp(MainApp mainApp){
		this.mainApp=mainApp;
	}
	
	public void backtohome(){
		mainApp.backToHomePage();
	}
	public void back(){
		mainApp.loadHelpingPage();
	}
}
