package kienaiyo;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import kienaiyo.save.Save;
import kienaiyo.view.endingpageController;
import kienaiyo.view.helpingpageController;
import kienaiyo.view.homepageController;
import kienaiyo.view.lottopageController;
import kienaiyo.view.lottoresultpageController;
import kienaiyo.view.mainpageController;
import kienaiyo.view.newsavepageController;
import kienaiyo.view.nexthelpingpageController;
import kienaiyo.view.savepageController;
import kienaiyo.view.shophelpingpageController;
import kienaiyo.view.shoppageController;
import kienaiyo.view.startingpageController;

public class MainApp extends Application {

	private Stage primaryStage;
	private Scene homescene;
	private Scene shopscene;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage=primaryStage;
		primaryStage.setWidth(678);primaryStage.setHeight(570);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Fun Fest");
		primaryStage.show();
		//to get the icon
		//primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));
		loadStartingPage();
	}

	public void loadStartingPage(){
		try {
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/StartingPage.fxml"));
			AnchorPane startingPage=(AnchorPane)loader.load();
			
			Scene scene=new Scene(startingPage);
			scene.getStylesheets().add(MainApp.class.getResource("styles/other.css").toExternalForm());
			primaryStage.setScene(scene);
			
			startingpageController strCtr=loader.getController();
			strCtr.setMainapp(this);
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadMainPage(Save save){
		try{
			System.gc();
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/MainPage.fxml"));
			AnchorPane mainPage=(AnchorPane)loader.load();
			Scene scene=new Scene(mainPage);
			primaryStage.setScene(scene);
			scene.getStylesheets().add(MainApp.class.getResource("styles/imageview.css").toExternalForm());
			
			mainpageController mpCtr=loader.getController();
			mpCtr.setMainApp(this);
			mpCtr.setSave(save);
			mpCtr.init();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void loadHelpingPage(){
		try{
			System.gc();
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/HelpingPage.fxml"));
			AnchorPane helpingPage=(AnchorPane)loader.load();
			Scene scene=new Scene(helpingPage);
			scene.getStylesheets().add(MainApp.class.getResource("styles/other.css").toExternalForm());
			primaryStage.setScene(scene);
			helpingpageController hcCtr=loader.getController();
			hcCtr.setMainApp(this);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void loadEndingPage(int score,Save save){
		try{
			System.gc();
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/EndingPage.fxml"));
			AnchorPane endingPage=(AnchorPane)loader.load();
			Scene scene=new Scene(endingPage);
			scene.getStylesheets().add(MainApp.class.getResource("styles/other.css").toExternalForm());
			primaryStage.setScene(scene);
			endingpageController ecCtr=loader.getController();
			ecCtr.setMainApp(this);
			ecCtr.setSave(save);
			ecCtr.showScore(score);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	public Stage getPrimaryStage(){
		return primaryStage;
	}
	
	public void loadNextHelpingPage(){
		try{
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/NextHelpingPage.fxml"));
			AnchorPane nextHelpingPage=(AnchorPane)loader.load();
			Scene scene=new Scene(nextHelpingPage);
			primaryStage.setScene(scene);
			scene.getStylesheets().add(MainApp.class.getResource("styles/other.css").toExternalForm());
				
			nexthelpingpageController nhCtr=loader.getController();
			nhCtr.setMainApp(this);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void loadhomePage(Save save){
		try{
			System.gc();
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/HomePage.fxml"));
			AnchorPane homePage=(AnchorPane)loader.load();
			homescene=new Scene(homePage);
			primaryStage.setScene(homescene);
			homescene.getStylesheets().add(MainApp.class.getResource("styles/other.css").toExternalForm());
				
			homepageController hpCtr=loader.getController();
			hpCtr.setSave(save);
			hpCtr.setMainApp(this);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void loadsavePage(){
		try{
			System.gc();
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SavePage.fxml"));
			AnchorPane savePage=(AnchorPane)loader.load();
			Scene scene=new Scene(savePage);
			primaryStage.setScene(scene);
			scene.getStylesheets().add(MainApp.class.getResource("styles/savePage.css").toExternalForm());
			
			savepageController spCtr=loader.getController();
			spCtr.setMainApp(this);
			spCtr.loadAllSaves();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void loadshopPage(Save save){
		try{
			System.gc();
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ShopPage.fxml"));
			AnchorPane shopPage=(AnchorPane)loader.load();
			shopscene=new Scene(shopPage);
			primaryStage.setScene(shopscene);
			shopscene.getStylesheets().add(MainApp.class.getResource("styles/other.css").toExternalForm());
				
			shoppageController shpCtr=loader.getController();
			shpCtr.setSave(save);
			shpCtr.setMainApp(this);
			shpCtr.init();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void loadshophelpingPage() {
			try{
				FXMLLoader loader=new FXMLLoader();
				loader.setLocation(MainApp.class.getResource("view/ShopHelpingPage.fxml"));
				AnchorPane shopHelpingPage=(AnchorPane)loader.load();
				Scene scene=new Scene(shopHelpingPage);
				primaryStage.setScene(scene);
				scene.getStylesheets().add(MainApp.class.getResource("styles/other.css").toExternalForm());
				
				shophelpingpageController sphCtr=loader.getController();
				sphCtr.setMainApp(this);
			}catch(IOException e){
				e.printStackTrace();
			}
	
	}

	public void loadlottoPage(Save save){
		try{
			System.gc();
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/lottoPage.fxml"));
			AnchorPane lottoPage=(AnchorPane)loader.load();
			Scene scene=new Scene(lottoPage);
			primaryStage.setScene(scene);
			scene.getStylesheets().add(MainApp.class.getResource("styles/lottopage.css").toExternalForm());
			
			lottopageController lpCtr=loader.getController();
			lpCtr.setMainApp(this);
			lpCtr.setSave(save);
			lpCtr.init();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void backToHomePage(){
		primaryStage.setScene(homescene);
	}
	
	public void backToShopPage(){
		primaryStage.setScene(shopscene);
	}
	
	public void loadNewSavePage(String[] names){
		try{
			Stage newStage=new Stage();
			newStage.setResizable(false);newStage.setTitle("Fun Fest-new save");
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/NewSavePage.fxml"));
			AnchorPane newSavePage=(AnchorPane)loader.load();
			Scene scene=new Scene(newSavePage);
			newStage.setScene(scene);
			newStage.show();
			
			newsavepageController nspCtr=loader.getController();
			nspCtr.setMainApp(this);
			nspCtr.setStage(newStage);
			nspCtr.setNames(names);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void loadLottoResultPage(String s,Save save){
		Stage result=new Stage();
		try{
			System.gc();
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/lottoresultPage.fxml"));
			AnchorPane lottoresultPage=(AnchorPane)loader.load();
			Scene scene=new Scene(lottoresultPage);
			result.setScene(scene);
			result.show();
			scene.getStylesheets().add(MainApp.class.getResource("styles/lottopage.css").toExternalForm());
			
			lottoresultpageController lrpCtr=loader.getController();
			lrpCtr.setMainApp(this);
			lrpCtr.setSave(save);
			lrpCtr.setStage(result);
			lrpCtr.init(s);
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
}
