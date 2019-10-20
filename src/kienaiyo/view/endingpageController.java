package kienaiyo.view;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import kienaiyo.MainApp;
import kienaiyo.save.Save;
import kienaiyo.save.SaveLoader;

public class endingpageController {
	private MainApp mainapp;
	private Save save;
	@FXML
	Label score;
	
	public void showScore(int scores){
		score.setText(score.getText()+scores);
	}
	
	
	public void setMainApp(MainApp mainapp){
		this.mainapp = mainapp;
	}
	
	public void setSave(Save save){
		this.save=save;
	}
	
	public void saveandexit(){
		save.setEndTime();
		save.setLastTime();
		save.updateTotalTime();
		SaveLoader sl=new SaveLoader();
		sl.setSave(save);
		System.exit(1);
	}
	
	public void saveandbacktohome(){
		mainapp.loadhomePage(save);
	}
	public void saveandreplay(){
		mainapp.loadMainPage(save);
	}
}

