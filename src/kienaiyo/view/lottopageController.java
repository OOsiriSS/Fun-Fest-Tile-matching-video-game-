package kienaiyo.view;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import kienaiyo.MainApp;
import kienaiyo.model.Gadget;
import kienaiyo.save.Save;
import kienaiyo.util.Tools;

public class lottopageController {

	@FXML
	private AnchorPane boss;
	@FXML
	private Label coin;
	
	private MainApp mainApp;
	private Save save;
	private Text prompt;
	
	public void setMainApp(MainApp mainApp){
		this.mainApp=mainApp;
	}
	
	public void setSave(Save s){
		save=s;
	}

	public void init(){
		coin.setText(save.coins+"");
	}
	
	public void toShopPage(){
		mainApp.loadshopPage(save);
	}
	
	private Gadget getSingle(){
		switch((int)(Math.random()*3)){
		case 0:{
			String name=Gadget.names[(int)(Math.random()*Gadget.names.length)];
			Gadget g=new Gadget(name);
			g.number=(int)(Math.random()*5)+1;
			return g;
		}
		case 1:{
			String name=Gadget.otherNames[(int)(Math.random()*Gadget.otherNames.length)];
			Gadget g=new Gadget(name);
			g.number=(int)(Math.random()*5)+1;
			return g;
		}
		default:{
			String name=Gadget.fracNames[(int)(Math.random()*Gadget.fracNames.length)];
			Gadget g=new Gadget(name);
			g.number=(int)(Math.random()*20)+1;
			return g;
		}
		}
	}

	private Gadget[] get10(){
		Gadget[] gs=new Gadget[10];
		for(int i=0;i<10;i++){
			switch((int)(Math.random()*3)){
			case 0:{
				int rd=(int)(Math.random()*10);
				if(rd<2){
					if((int)(Math.random()*2)>0)
						gs[i]=new Gadget(Gadget.names[2]);
					else gs[i]=new Gadget(Gadget.names[6]);
					gs[i].number=(int)(Math.random()*5)+3;
				}else if(rd<4){
					if((int)(Math.random()*2)>0)
						gs[i]=new Gadget(Gadget.names[0]);
					else gs[i]=new Gadget(Gadget.names[1]);
					gs[i].number=(int)(Math.random()*5)+3;
				}else if(rd<7){
					if((int)(Math.random()*2)>0)
						gs[i]=new Gadget(Gadget.names[5]);
					else gs[i]=new Gadget(Gadget.names[7]);
					gs[i].number=(int)(Math.random()*5)+3;
				}else{
					if((int)(Math.random()*2)>0)
						gs[i]=new Gadget(Gadget.names[3]);
					else gs[i]=new Gadget(Gadget.names[4]);
					gs[i].number=(int)(Math.random()*5)+3;
				}
				break;
			}
			case 1:{
				int rd=(int)(Math.random()*10);
				if(rd<2){
					gs[i]=new Gadget(Gadget.otherNames[0]);
					gs[i].number=(int)(Math.random()*5)+3;
				}else if(rd<4){
					gs[i]=new Gadget(Gadget.otherNames[1]);
					gs[i].number=(int)(Math.random()*5)+3;
				}else if(rd<7){
					gs[i]=new Gadget(Gadget.otherNames[2]);
					gs[i].number=(int)(Math.random()*5)+3;
				}else{
					gs[i]=new Gadget(Gadget.otherNames[3]);
					gs[i].number=(int)(Math.random()*5)+3;
				}
				break;
			}
			default:{
				int index=(int)(Math.random()*Gadget.fracNames.length);
				gs[i]=new Gadget(Gadget.fracNames[index]);
				gs[i].number=(int)(Math.random()*20)+10;
				break;
			}
			}
		}
		return modify(gs);
	}
	
	private Gadget[] modify(Gadget[] gs){
		ArrayList<Gadget> result=new ArrayList<Gadget>();
		for(Gadget g:gs){
			Gadget gt=Tools.findGgt(result, g.name);
			if(gt==null) result.add(g);
			else gt.number+=g.number;
		}
		Gadget[] ggts=new Gadget[result.size()];
		for(int i=0;i<ggts.length;i++) ggts[i]=result.get(i); 
		result=null;
		return ggts;
	}

	public void getSingleHandle(MouseEvent e){
		Group group=(Group)e.getSource();
		Label price=null;
		for(Node n:group.getChildren()) if(n.getClass().equals(Label.class)) price=(Label)n;
		int p=(price==null)?0:Integer.parseInt(price.getText());
		
		if(p>save.coins)coinNotEnough();
		else {
			save.coins-=p;
			coin.setText(save.coins+"");
			Gadget g=getSingle();
			save.addGadgets(g.name, g.number);
			
			String s="恭喜"+save.playerName+"获得："+System.lineSeparator()+Tools.outName(g.name)+"*"+g.number+"!";
			mainApp.loadLottoResultPage(s,save);
		}
		
	}
	
	private void coinNotEnough() {
		if(prompt==null){
			prompt=new Text("您的加帕里币不够哦~");
			prompt.setFill(Color.RED);prompt.setX(60);prompt.setY(460);
			prompt.setStyle("-fx-font-size:15px");
			boss.getChildren().add(prompt);
			Tools.fadeout(prompt);
		}else{
			prompt.setOpacity(1);
			Tools.fadeout(prompt);
		}
	}

	public void get10Handle(MouseEvent e){
		Group group=(Group)e.getSource();
		Label price=null;
		for(Node n:group.getChildren()) if(n.getClass().equals(Label.class)) price=(Label)n;
		int p=(price==null)?0:Integer.parseInt(price.getText());
		
		if(p>save.coins)coinNotEnough();
		else {
			save.coins-=p;
			coin.setText(save.coins+"");
			Gadget[] gs=get10();
			StringBuffer sb=new StringBuffer("恭喜"+save.playerName+"获得："+System.lineSeparator());
			for(Gadget g:gs){
				save.addGadgets(g.name, g.number);
				sb.append(Tools.outName(g.name));
				sb.append("*");
				sb.append(g.number);
				sb.append(System.lineSeparator());
			}
			mainApp.loadLottoResultPage(sb.toString(),save);
		}
	}
	
}
