package kienaiyo.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import kienaiyo.MainApp;
import kienaiyo.save.Save;
import kienaiyo.util.Tools;

public class shoppageController {
	
	@FXML
	AnchorPane boss;
	@FXML
	Label coin;
	
	Text prompt1,prompt2,prompt3;
	boolean valid=true;
	MainApp mainApp;
	Save save;
	
	public void init(){
		if(save!=null){
			coin.setText(save.coins+"");
		}
	}
	
	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
	}
	public void backtohome(){
		mainApp.backToHomePage();
	}
	public void shophelp(){
		mainApp.loadshophelpingPage();
	}
	
	public void setSave(Save save){
		this.save=save;
	}

	public void checkInput(KeyEvent e){
		try{
			TextField tf=(TextField)e.getTarget();
			if(tf.getText().length()!=0)Integer.parseInt(tf.getText());
			boss.getChildren().remove(prompt1);
			if(tf.getText().length()>=5){
				tooLong();
				valid=false;
			}else {
				boss.getChildren().remove(prompt2);
				valid=true;
			}
		}catch(NumberFormatException ex){
			valid=false;
			if(!boss.getChildren().contains(prompt2)){
				if(prompt1==null){
					prompt1=new Text("please input only numbers");
					prompt1.setFill(Color.RED);prompt1.setX(24);prompt1.setY(85);
					prompt1.setStyle("-fx-font-size:15");
					boss.getChildren().add(prompt1);
				}else if(!boss.getChildren().contains(prompt1))
					boss.getChildren().add(prompt1);
			}
		}
	}
	
	private void tooLong() {
		if(prompt2==null){
			prompt2=new Text("the number is too long");
			prompt2.setFill(Color.RED);prompt2.setX(24);prompt2.setY(85);
			prompt2.setStyle("-fx-font-size:15");
			boss.getChildren().add(prompt2);
		}else if(!boss.getChildren().contains(prompt2))
			boss.getChildren().add(prompt2);
	}

	public void minus1(ActionEvent e){
		if(valid){
			Group g=(Group)((Button)e.getTarget()).getParent();
			TextField tf=null;
			Label price=null,totalPrice=null;
			for(Node n:g.getChildren()) {
				if(n.getClass().equals(TextField.class)) tf=(TextField)n;
				else if(n.getId()!=null&&n.getId().equals("price")) price=(Label)n;
				else if(n.getId()!=null&&n.getId().equals("totalPrice")) totalPrice=(Label)n;
			}
			int num=Integer.parseInt(tf.getText()),p=Integer.parseInt(price.getText());
			if(num>0) num--;
			int tp=num*p;
			tf.setText(num+"");
			totalPrice.setText(tp+"");
		}
	}

	public void add1(ActionEvent e){
		if(valid){
			Group g=(Group)((Button)e.getTarget()).getParent();
			TextField tf=null;
			Label price=null,totalPrice=null;
			for(Node n:g.getChildren()) {
				if(n.getClass().equals(TextField.class)) tf=(TextField)n;
				else if(n.getId()!=null&&n.getId().equals("price")) price=(Label)n;
				else if(n.getId()!=null&&n.getId().equals("totalPrice")) totalPrice=(Label)n;
			}
			int num=Integer.parseInt(tf.getText()),p=Integer.parseInt(price.getText());
			num++;
			int tp=num*p;
			tf.setText(num+"");
			totalPrice.setText(tp+"");
		}
	}
	
	public void buy(ActionEvent e){
		if(valid){
			Group g=(Group)((Button)e.getTarget()).getParent();
			TextField tf=null;
			Label totalPrice=null;
			String name=null;
			for(Node n:g.getChildren()) {
				if(n.getClass().equals(TextField.class)) tf=(TextField)n;
				else if(n.getId()!=null&&n.getId().equals("totalPrice")) totalPrice=(Label)n;
				else if(n.getClass().equals(ImageView.class)) name=n.getId();
			}
			int tp=Integer.parseInt(totalPrice.getText()),
				num=Integer.parseInt(tf.getText());
			if(tp>save.coins){
				coinsNotEnough();
			}else{
				boss.getChildren().remove(prompt3);
				save.coins-=tp;
				coin.setText(save.coins+"");
				if(name!=null) save.addGadgets(name, num);
				tf.setText("0");totalPrice.setText("0");
			}
		}
	}

	private void coinsNotEnough() {
		if(prompt3==null){
			prompt3=new Text("your Japari coins are not enough. Go to earn more please~");
			prompt3.setFill(Color.RED);prompt3.setX(24);prompt3.setY(85);
			prompt3.setStyle("-fx-font-size:15");
			boss.getChildren().add(prompt3);
			Tools.fadeout(prompt3);
		}else{
			prompt3.setOpacity(1);
			Tools.fadeout(prompt3);
		}
	}
	
	public void toLottoPage(){
		mainApp.loadlottoPage(save);
	}
	
}
