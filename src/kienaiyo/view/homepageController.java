package kienaiyo.view;

import kienaiyo.MainApp;
import kienaiyo.save.Save;
import kienaiyo.save.SaveLoader;

public class homepageController {
	private MainApp mainApp;
	private Save save;
	
	public void setSave(Save save){
		this.save=save;
	}
	
	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
	}
	
	public void enterSavePage(){
		save.setEndTime();
		save.setLastTime();
		save.updateTotalTime();
		SaveLoader sl=new SaveLoader();
		sl.setSave(save);
		mainApp.loadsavePage();
	}
	
	public void enterShopPage(){
		mainApp.loadshopPage(save);
	}
	
	public void play(){
		mainApp.loadMainPage(save);
	}
	
	public void exit(){
		save.setEndTime();
		save.setLastTime();
		save.updateTotalTime();
		SaveLoader sl=new SaveLoader();
		sl.setSave(save);
		System.exit(1);
	}
	
	public void enterhelpPage(){
		mainApp.loadHelpingPage();
	}
	
}