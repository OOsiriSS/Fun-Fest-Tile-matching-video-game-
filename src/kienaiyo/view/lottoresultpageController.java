package kienaiyo.view;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import kienaiyo.MainApp;
import kienaiyo.save.Save;

public class lottoresultpageController {

	@FXML
	private Text prompt;
	
	private MainApp mainApp;
	private Stage stage;
	private Save save;
	
	public void setMainApp(MainApp m){
		this.mainApp=m;
	}
	
	public void setStage(Stage s){
		this.stage=s;
	}
	
	public void setSave(Save s){
		this.save=s;
	}
	
	public void init(String s){
		prompt.setText(s);
	}
	
	public void OKhandle(){
		stage.close();
	}
	
}
