package kienaiyo.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import kienaiyo.MainApp;
import kienaiyo.model.Board;
import kienaiyo.model.Gadget;
import kienaiyo.save.Save;
import kienaiyo.util.Tools;

public class mainpageController {
	
	@FXML
	public AnchorPane main;
	@FXML
	private Label times;
	@FXML
	private Text task;
	@FXML
	private Text scores;
	@FXML
	private Label num0,num1,num2,num3,num4,num5,num6,num7;
	private Label[] nums=new Label[8];
	
	private MainApp mainApp;
	private Save save;
	private static double hMargin=130;
	private static double upMargin=80;

	public int remainingTimes=25,remainingTimesCopy;
	public static int[] scoreGet={100,150,200,500,1000,2000,5000};
	public static int nScore=10;
	public static int addition[]={2,3,4,5,6,7,8};
	public int score=0,scoreCopy;
	private Board board;
	private int gadgetMode;//0：无道具，1：锤子，2：强势交换，3：随机变换，4：随机特效，5：万能方块
	private int gadgetFlag;
	
	public void init(){
		nums[0]=num0;nums[1]=num1;nums[2]=num2;nums[3]=num3;
		nums[4]=num4;nums[5]=num5;nums[6]=num6;nums[7]=num7;
		Gadget g;
		for(int i=0;i<Gadget.names.length;i++){
			if((g=Tools.findGgt(save.gadgets, Gadget.names[i]))!=null)
				nums[i].setText(g.number+"");
		}
		board=new Board();
		board.setMainPageController(this);
		board.setMargins(hMargin, upMargin);
		board.init();
		scores.setText(scores.getText()+score);
		times.setText(times.getText()+remainingTimes);
	}

	public void clickHandle(MouseEvent e){
		double x=e.getX(),y=e.getY();
		if(inBound(x,y)){
			board.clickHandle(x, y, gadgetMode);
			switch(gadgetMode){
			case 0:break;
			case 1:{gadgetMode=0;break;}
			case 2:{
				if(++gadgetFlag==2){
					gadgetMode=0;gadgetFlag=0;
				}break;
			}
			case 3:{gadgetMode=0;break;}
			case 4:{gadgetMode=0;break;}
			case 5:{gadgetMode=0;break;}
			}
		}else {
			board.restore();
		}
	}

	public void setMainApp(MainApp mainApp){
		this.mainApp=mainApp;
	}

	public void setSave(Save save){
		this.save=save;
	}
	
	public void back(){
		mainApp.backToHomePage();
	}

	public void setScoresText(){
		scores.setText(scores.getText().split(":")[0]+": "+score);
	}

	public void setTimesText(){
		times.setText(times.getText().split(":")[0]+": "+remainingTimes);
	}
	
	public void end(){
		save.coins+=(score/10);
		
		mainApp.loadEndingPage(score,save);
	}
	
	public void copy(){
		remainingTimesCopy=remainingTimes;
		scoreCopy=score;
		
		board.copy();
	}
	
	private boolean inBound(double x,double y){
		double xMax=hMargin+(Board.picWidth+Board.space)*Board.numOfCol-Board.space;
		double yMax=upMargin+(Board.picHeight+Board.space)*Board.numOfLine-Board.space;
		return x>hMargin&&x<xMax&&y>upMargin&&y<yMax;
	}
	
	/**
	 * 锤子道具
	 */
	public void chuizi(){
		if(board.finished){
			Gadget g;
			if((g=Tools.findGgt(save.gadgets,Gadget.names[0]))!=null){
				if(gadgetMode!=1){
					gadgetMode=1;
					if(--g.number==0){
						save.gadgets.remove(g);
						nums[0].setText("0");
					}else nums[0].setText(g.number+"");
					board.restore();
				}
				else {
					gadgetMode=0;
				}
			}
		}
	}
	
	/**
	 * 强势交换道具
	 */
	public void violentExch(){
		if(board.finished){
			Gadget g;
			if((g=Tools.findGgt(save.gadgets, Gadget.names[1]))!=null){
				if(gadgetMode!=2){
					gadgetMode=2;
					if(--g.number==0) save.gadgets.remove(g);
					nums[1].setText(g.number+"");
					gadgetFlag=0;
					board.restore();
				}
				else if(gadgetFlag!=0){
					gadgetMode=0;
					board.restore();
				}else {
					gadgetMode=0;
				}
			}
		}
	}
	
	/**
	 * 随机变换道具
	 */
	public void randomChng(){
		if(board.finished){
			Gadget g;
			if((g=Tools.findGgt(save.gadgets, Gadget.names[2]))!=null){
				if(gadgetMode!=3){
					gadgetMode=3;
					if(--g.number==0) save.gadgets.remove(g);
					nums[2].setText(g.number+"");
					board.restore();
				}
				else{
					gadgetMode=0;
				}
			}
		}
	}

	/**
	 * 随机特效道具(不包含万能方块)
	 */
	public void randomEff(){
		if(board.finished){
			Gadget g;
			if((g=Tools.findGgt(save.gadgets, Gadget.names[3]))!=null){
				if(gadgetMode!=4){
					gadgetMode=4;
					if(--g.number==0) save.gadgets.remove(g);
					nums[3].setText(g.number+"");
					board.restore();
				}
				else{
					gadgetMode=0;
				}
			}
		}
	}
	
	public void sameEff(){
		if(board.finished){
			Gadget g;
			if((g=Tools.findGgt(save.gadgets, Gadget.names[4]))!=null){
				if(gadgetMode!=5){
					gadgetMode=5;
					if(--g.number==0) save.gadgets.remove(g);
					nums[4].setText(g.number+"");
					board.restore();
				}
				else{
					gadgetMode=0;
				}
			}
		}
	}
	
	public void plus5times(){
		Gadget g;
		if((g=Tools.findGgt(save.gadgets, Gadget.names[5]))!=null){
			gadgetMode=0;
			remainingTimes+=5;
			if(--g.number==0) save.gadgets.remove(g);
			nums[5].setText(g.number+"");
			setTimesText();
		}
	}

	public void refresh(){
		if(board.finished){
			Gadget g;
			if((g=Tools.findGgt(save.gadgets, Gadget.names[6]))!=null){
				gadgetMode=0;
				if(--g.number==0) save.gadgets.remove(g);
				nums[6].setText(g.number+"");
				board.refresh();
			}
		}
	}

	public void backOneStep(){
		if(board.finished){
			Gadget g;
			if((g=Tools.findGgt(save.gadgets, Gadget.names[7]))!=null){
				gadgetMode=0;
				if(--g.number==0) save.gadgets.remove(g);
				nums[7].setText(g.number+"");
				remainingTimes=remainingTimesCopy;
				setTimesText();
				score=scoreCopy;
				setScoresText();
				board.backOneStep();
			}
		}
	}
	
}
