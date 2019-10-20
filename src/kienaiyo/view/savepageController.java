package kienaiyo.view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import kienaiyo.MainApp;
import kienaiyo.save.Save;
import kienaiyo.save.SaveLoader;

public class savepageController {
	
	@FXML
	private VBox main;
	@FXML
	private AnchorPane outside;
	
	private static double width=500,height=80;
	private static String sep=System.lineSeparator();
	private MainApp mainApp;
	private Save[] saves;
	private Text[] txts;
	private Text chosen;
	private Text prompt;
	private String normStyle="-fx-fill:#000000",chosenStyle="-fx-fill:#0000ff";
	
	
	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
	}

	public void loadAllSaves(){
		SaveLoader sl=new SaveLoader();
		saves=sl.loadAll();
		Text txt=new Text("Nothing.");
		txt.setX(0);txt.setY(15);txt.setStyle("-fx-font-size:15px");
		if(saves!=null&&saves.length!=0){
			main.getChildren().remove(txt);
			
			txts=new Text[saves.length];
			for(int i=0;i<txts.length;i++){
				txts[i]=new Text();
				txts[i].setId("txts");
				txts[i].setX(10);txts[i].setY(i*height+15);
				txts[i].setOnMouseClicked(new EventHandler<MouseEvent>(){
					@Override
					public void handle(MouseEvent e){
						outside.getChildren().remove(prompt);
						if(chosen!=null) chosen.setStyle(normStyle);
						chosen=(Text)e.getTarget();
						chosen.setStyle(chosenStyle);
					}
				});
				txts[i].setText(saves[i].saveName+sep+
								"Japari coins: "+saves[i].coins+sep+
								"Last played at: "+saves[i].lastTime+sep+
								"Total playtime: "+saves[i].totalTime);
				main.getChildren().add(txts[i]);
			}
		}else{
			main.getChildren().add(txt);
		}
	}

	public void newSave(){
		String[] names=new String[saves.length];
		for(int i=0;i<names.length;i++)
			names[i]=saves[i].saveName;
		mainApp.loadNewSavePage(names);
	}

	public void playSave(){
		if(chosen!=null){
			SaveLoader sl=new SaveLoader();
			Save save=sl.loadSave(chosen.getText().split(sep)[0]);
			save.setStartTime();
			mainApp.loadhomePage(save);
		}else{
			prompt=new Text("No save has been chosen.");
			prompt.setX(220);prompt.setY(445);prompt.setStyle("-fx-font-size:15;-fx-fill:#ff0000");
			outside.getChildren().add(prompt);
		}
	}
	
	public void delete(){
		if(chosen!=null){
			SaveLoader sl=new SaveLoader();
			sl.deleteSave(chosen.getText().split(sep)[0]);
			mainApp.loadsavePage();
		}
	}
	
	public void back(){
		mainApp.loadStartingPage();
	}
	
	
}