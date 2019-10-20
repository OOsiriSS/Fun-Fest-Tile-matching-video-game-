package kienaiyo.view;

import kienaiyo.MainApp;

public class shophelpingpageController {
	private MainApp mainApp;
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	public void backtoshop(){
		mainApp.backToShopPage();
	}
}
