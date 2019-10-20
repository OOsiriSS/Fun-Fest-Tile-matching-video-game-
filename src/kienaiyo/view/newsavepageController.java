package kienaiyo.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import kienaiyo.MainApp;
import kienaiyo.save.Save;

public class newsavepageController {

	@FXML
	private TextField saveName;
	@FXML
	private TextField playerName;
	@FXML
	private AnchorPane main;
	
	private MainApp mainApp;
	private Stage newStage;
	private String[] names;
	
	public void setMainApp(MainApp mainApp){
		this.mainApp=mainApp;
	}
	
	public void setNames(String[] names){
		this.names=names;
	}
	
	public void setStage(Stage newStage){
		this.newStage=newStage;
	}
	
	public void okHandle(){
		if(duplicated(saveName.getText())){
			Text prompt=new Text("The save name has been taken.");
			prompt.setX(30);prompt.setY(70);prompt.setStyle("-fx-font-size:15px;-fx-fill:#ff0000");
			main.getChildren().add(prompt);
		}else{
			String sn,pn;
			if(saveName.getText()==null||saveName.getText().length()==0)
				sn="New Save";
			else sn=saveName.getText();
			if(playerName.getText()==null||playerName.getText().length()==0)
				pn="Player";
			else pn=playerName.getText();
			Save newSave=new Save(sn,pn);
			newStage.close();
			newSave.setStartTime();
			mainApp.loadhomePage(newSave);
		}
	}

	public void cancelHandle(){
		newStage.close();
	}
	
	public void enterPlayerName(KeyEvent e){
		if(e.getCode().equals(KeyCode.ENTER)){
			playerName.requestFocus();
		}
	}
	
	public void enterGame(KeyEvent e){
		if(e.getCode().equals(KeyCode.ENTER))
			okHandle();
	}
	
	private boolean duplicated(String text) {
		for(String s:names) if(s.equals(text)) return true;
		return false;
	}
	
}
