package kienaiyo.model;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import kienaiyo.util.Tools;
import kienaiyo.view.mainpageController;

public class Board {
	
	public static int numOfLine=8;
	public static int numOfCol=8;
	public static double picWidth=45;
	public static double picHeight=45;
	public static double space=5;
	private double hMargin;
	private double upMargin;
	public static double animeDuration=400;
	private static String imgPathPref="file:imgs/";
	public static String[] imgRsc={"kaban","sa-baru","lucky","toki","fenekku","raion"}; 
	public static String imgSuff=".jpg";
	private static String sameImg="S";
	public static String imgSuffGIF=".gif";
	private static String[] imgSpec={"_H","_V","_B"};
	private static String[] ids={"noEffect","hElim","vElim","bElim","sElim"};
	private static String[] deathcauses={"l","b","s","ll","lb","bb","ss"};
	private static String chosenstyle=
			"-fx-effect:dropshadow(gaussian,#00ff00,4,4,0,0)";
	private static String normstyle=
			"-fx-effect:innershadow(three-pass-box,rgba(225,225,225,1),6,0.5,3,3)";
	private mainpageController mpCtr;
	public ImageView[][] ivs,ivsCopy;
	
	private boolean elimJudged,elimed,timesChanged,violent,chuizi;
	private boolean singleSelected=false;
	public boolean finished=true;
	private int lianji=0;
	private Pos p1=null,p2=null;
	public int[] num=new int[7];//分别对应deathcauses的7个
	
	public void init(){
		ivs=Tools.initIvs(numOfLine, numOfCol, imgPathPref, imgRsc, imgSuff, picWidth,picHeight);
		ivsCopy=new ImageView[numOfLine][numOfCol];
		for(int i=0;i<numOfLine;i++){
			for(int k=0;k<numOfCol;k++){
				ivsCopy[i][k]=new ImageView();
				ivsCopy[i][k].setFitHeight(picHeight);
				ivsCopy[i][k].setFitWidth(picWidth);
				
				double x=(picWidth+space)*k;
				double y=(picHeight+space)*i;
				ivs[i][k].setX(x);
				ivs[i][k].setY(y);
				
				ivsCopy[i][k].setX(ivs[i][k].getX());
				ivsCopy[i][k].setY(ivs[i][k].getY());
				
				ivs[i][k].setId(ids[0]);
//				ivs[i][k].setStyle(MainApp.class.getResource("styles/imageview.css").toExternalForm());
				mpCtr.main.getChildren().add(ivs[i][k]);
			}
		}
	}

	public void clickHandle(double x,double y,int gadgetMode){
		if(finished){
			switch(gadgetMode){
			case 0:{normHandle(x,y);break;}
			case 1:{chuiziHandle(x,y);break;}
			case 2:{violentExchHandle(x,y);break;}
			case 3:{randomChngHandle(x,y);break;}
			case 4:{randomEffHandle(x,y);break;}
			case 5:{sameEffHandle(x,y);break;}
			}
		}	
	}
	
	/**
	 * 无道具情况下的handle
	 */
	private void normHandle(double x,double y){
		if(singleSelected){
			ivs[p1.a][p1.b].setStyle(normstyle);
			p2=Tools.getPos(x, y, hMargin, upMargin, picWidth, picHeight,space);
			if(p1.isNextTo(p2)){
				singleSelected=false;
				toNorm();
				exchAnime(p1,p2);	
			}else if(p1.isSameAs(p2)){
				p1=null;p2=null;
				singleSelected=false;
			}else{
				p1=p2;p2=null;
				ivs[p1.a][p1.b].setStyle(chosenstyle);
				ivs[p1.a][p1.b].toFront();
			}
		}else{
			mpCtr.copy();
			p1=Tools.getPos(x, y, hMargin, upMargin, picWidth, picHeight,space);
			singleSelected=true;
			ivs[p1.a][p1.b].setStyle(chosenstyle);
			ivs[p1.a][p1.b].toFront();
		}
	}

	private void chuiziHandle(double x,double y){
		toNorm();
		timesChanged=true;
		chuizi=true;
		mpCtr.copy();
		ArrayList<Pos> ps=new ArrayList<Pos>();
		ps.add(Tools.getPos(x, y, hMargin, upMargin, picWidth, picHeight, space));
		elimAnime(ps);
	}
	
	private void violentExchHandle(double x,double y){
		if(singleSelected){
			ivs[p1.a][p1.b].setStyle(normstyle);
			p2=Tools.getPos(x, y, hMargin, upMargin, picWidth, picHeight,space);
			if(p1.isNextTo(p2)){
				singleSelected=false;
				toNorm();
				violent=true;
				exchAnime(p1,p2);	
			}else if(p1.isSameAs(p2)){
				p1=null;p2=null;
				singleSelected=false;
			}else{
				p1=p2;p2=null;
				ivs[p1.a][p1.b].setStyle(chosenstyle);
				ivs[p1.a][p1.b].toFront();
			}
		}else{
			mpCtr.copy();
			p1=Tools.getPos(x, y, hMargin, upMargin, picWidth, picHeight, space);
			singleSelected=true;
			ivs[p1.a][p1.b].setStyle(chosenstyle);
			ivs[p1.a][p1.b].toFront();
		}
	}
	
	private void randomChngHandle(double x,double y){
		toNorm();
		timesChanged=true;
		mpCtr.copy();
		Pos p=Tools.getPos(x, y, hMargin, upMargin, picWidth, picHeight, space);
		
		KeyValue kv1=new KeyValue(ivs[p.a][p.b].opacityProperty(),0.5);
		KeyValue kv2=new KeyValue(ivs[p.a][p.b].rotateProperty(),ivs[p.a][p.b].getRotate()+180);
		KeyFrame kf1=new KeyFrame(Duration.millis(animeDuration),kv1,kv2);
		Timeline tl=new Timeline();
		tl.getKeyFrames().add(kf1);
		tl.setOnFinished(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				getRamdomImg(ivs[p.a][p.b]);
				KeyValue kv3=new KeyValue(ivs[p.a][p.b].opacityProperty(),1);
				KeyValue kv4=new KeyValue(ivs[p.a][p.b].rotateProperty(),ivs[p.a][p.b].getRotate()+180);
				KeyFrame kf2=new KeyFrame(Duration.millis(animeDuration),kv3,kv4);
				Timeline tl1=new Timeline();
				tl1.getKeyFrames().add(kf2);
				tl1.setOnFinished(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent e){
						ArrayList<Pos> ps=new ArrayList<Pos>();
						ps.add(p);
						elimJudge(ps);
					}
				});
				tl1.play();
			}

			private void getRamdomImg(ImageView iv) {
				if(iv.getId().equals(ids[4]));
				else{
					int index=(int)(Math.random()*imgRsc.length);
					
					if(iv.getId().equals(ids[0])) 
						iv.setImage(new Image(imgPathPref+imgRsc[index]+imgSuff));
					else{
						String spec=iv.getImage().impl_getUrl().split("_")[1];
						iv.setImage(new Image(imgPathPref+imgRsc[index]+"_"+spec));
					}
						
				}
			}
		});
		tl.play();
	}

	private void randomEffHandle(double x,double y){
		finished=false;
		mpCtr.copy();
		Pos p=Tools.getPos(x, y, hMargin, upMargin, picWidth, picHeight, space);
		
		KeyValue kv1=new KeyValue(ivs[p.a][p.b].opacityProperty(),0.5);
		KeyValue kv2=new KeyValue(ivs[p.a][p.b].rotateProperty(),ivs[p.a][p.b].getRotate()+180);
		KeyFrame kf1=new KeyFrame(Duration.millis(animeDuration),kv1,kv2);
		Timeline tl=new Timeline();
		tl.getKeyFrames().add(kf1);
		tl.setOnFinished(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				setRandomEff(ivs[p.a][p.b]);
				
				KeyValue kv3=new KeyValue(ivs[p.a][p.b].opacityProperty(),1);
				KeyValue kv4=new KeyValue(ivs[p.a][p.b].rotateProperty(),ivs[p.a][p.b].getRotate()+180);
				KeyFrame kf2=new KeyFrame(Duration.millis(animeDuration),kv3,kv4);
				Timeline tl1=new Timeline();
				tl1.getKeyFrames().add(kf2);
				tl1.setOnFinished(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent e){
						finished=true;
					}
				});
				tl1.play();
			}

			private void setRandomEff(ImageView iv) {
				if(iv.getId().equals(ids[4]));
				else{
					int index=(int)(Math.random()*imgSpec.length);
					iv.setId(ids[1+index]);
					iv.setImage(new Image(iv.getImage().impl_getUrl().split("_|\\.")[0]+
											imgSpec[index]+imgSuff));
				}
			}
		});
		tl.play();
	}
	
	private void sameEffHandle(double x,double y){
		finished=false;
		mpCtr.copy();
		
		Pos p=Tools.getPos(x, y, hMargin, upMargin, picWidth, picHeight, space);
		
		KeyValue kv1=new KeyValue(ivs[p.a][p.b].opacityProperty(),0.5);
		KeyValue kv2=new KeyValue(ivs[p.a][p.b].rotateProperty(),ivs[p.a][p.b].getRotate()+180);
		KeyFrame kf1=new KeyFrame(Duration.millis(animeDuration),kv1,kv2);
		Timeline tl=new Timeline();
		tl.getKeyFrames().add(kf1);
		tl.setOnFinished(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				ivs[p.a][p.b].setId(ids[4]);
				ivs[p.a][p.b].setImage(new Image(imgPathPref+sameImg+imgSuffGIF));
				KeyValue kv3=new KeyValue(ivs[p.a][p.b].opacityProperty(),1);
				KeyValue kv4=new KeyValue(ivs[p.a][p.b].rotateProperty(),ivs[p.a][p.b].getRotate()+180);
				KeyFrame kf2=new KeyFrame(Duration.millis(animeDuration),kv3,kv4);
				Timeline tl1=new Timeline();
				tl1.getKeyFrames().add(kf2);
				tl1.setOnFinished(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent e){
						finished=true;
					}
				});
				tl1.play();
			}
		});
		tl.play();
		
	}
	
	public void refresh(){
		finished=false;
		mpCtr.copy();
		Tools.refresh(ivs,this);
	}
	
	public void copy(){
		for(int i=0;i<numOfLine;i++)
			for(int k=0;k<numOfCol;k++){
				ivsCopy[i][k].setImage(ivs[i][k].getImage());
				ivsCopy[i][k].setId(ivs[i][k].getId());
			}
	}

	public void backOneStep(){
		for(int i=0;i<numOfLine;i++)
			for(int k=0;k<numOfCol;k++)
				mpCtr.main.getChildren().remove(ivs[i][k]);
		for(int i=0;i<numOfLine;i++)
			for(int k=0;k<numOfCol;k++){
				ivs[i][k].setImage(ivsCopy[i][k].getImage());
				ivs[i][k].setId(ivsCopy[i][k].getId());
			}
		for(int i=0;i<numOfLine;i++)
			for(int k=0;k<numOfCol;k++)
				mpCtr.main.getChildren().add(ivs[i][k]);
	}
	
	/**
	 * 棋盘复原
	 * 当玩家在点击图片后，若点击了其他位置（非图片的位置），则认为玩家取消了对第一张图片的选择，并将棋盘复原
	 */
	public void restore(){
		singleSelected=false;
		if(p1!=null){
			ivs[p1.a][p1.b].setStyle(normstyle);
			p1=null;
		}
	}
	
	private void toNorm(){
		finished=false;
		elimJudged=false;
		elimed=false;
		violent=false;
		timesChanged=false;
		chuizi=false;
		lianji=0;
	}
	
	private void exchange(Pos p1, Pos p2) {
		ImageView ivTemp1=ivs[p1.a][p1.b];
		ImageView ivTemp2=ivs[p2.a][p2.b];
		ivs[p1.a][p1.b]=ivTemp2;ivs[p2.a][p2.b]=ivTemp1;
		if(!elimJudged){
			if(!violent&&
					(ivs[p1.a][p1.b].getId().equals(ids[4])||ivs[p2.a][p2.b].getId().equals(ids[4])))
				sameElimJudge(p1,p2);
			else{
				ArrayList<Pos> ps=new ArrayList<Pos>();
				ps.add(p1);ps.add(p2);
				elimJudge(ps);
			}
		}else {
			finished=true;
			System.gc();
		}
	}

	/**
	 * 该方法中更新了游戏分数
	 */
	private void elim(ArrayList<Pos> ps){
		for(int n=0;n<ps.size();n++){
			int x=ps.get(n).a,y=ps.get(n).b;
			String id=ivs[x][y].getId();
			if(id.equals(ids[0])||id.equals(ids[4])){
				mpCtr.main.getChildren().remove(ivs[x][y]);
				ivs[x][y]=null;
			} 
			else if(id.equals(ids[1])){
				mpCtr.main.getChildren().remove(ivs[x][y]);
				ivs[x][y]=null;
				num[0]++;
			}else if(id.equals(ids[2])){
				mpCtr.main.getChildren().remove(ivs[x][y]);
				ivs[x][y]=null;
				num[0]++;
			}else{
				mpCtr.main.getChildren().remove(ivs[x][y]);
				ivs[x][y]=null;
				num[1]++;	
			}
		}
		if(!chuizi)mpUpdateScores(ps);
		else chuizi=false;
		elimed=true;
		dropJudge(ps);
	}
	
	private void mpUpdateTimes() {	
		mpCtr.remainingTimes--;
		mpCtr.setTimesText();	
	}
	
	private void mpUpdateScores(ArrayList<Pos> ps){
		for(Pos p:ps){
			if(p.deathCause==null) 
				mpCtr.score+=(mainpageController.nScore*lianji);
			else if(p.deathCause.equals(deathcauses[0]))
				mpCtr.score+=(mainpageController.nScore*mainpageController.addition[0]);
			else if(p.deathCause.equals(deathcauses[1]))
				mpCtr.score+=(mainpageController.nScore*mainpageController.addition[1]);
			else if(p.deathCause.equals(deathcauses[2]))
				mpCtr.score+=(mainpageController.nScore*mainpageController.addition[2]);
			else if(p.deathCause.equals(deathcauses[3]))
				mpCtr.score+=(mainpageController.nScore*mainpageController.addition[3]);
			else if(p.deathCause.equals(deathcauses[4]))
				mpCtr.score+=(mainpageController.nScore*mainpageController.addition[4]);
			else if(p.deathCause.equals(deathcauses[5]))
				mpCtr.score+=(mainpageController.nScore*mainpageController.addition[5]);
			else if(p.deathCause.equals(deathcauses[6]))
				mpCtr.score+=(mainpageController.nScore*mainpageController.addition[6]);
		}
		mpCtr.score+=(num[0]*mainpageController.scoreGet[0]+num[1]*mainpageController.scoreGet[1]+
					num[2]*mainpageController.scoreGet[2]+num[3]*mainpageController.scoreGet[3]+
					num[4]*mainpageController.scoreGet[4]+num[5]*mainpageController.scoreGet[5]+
					num[6]*mainpageController.scoreGet[6]);
		mpCtr.setScoresText();
	}

	public void setMargins(double hMargin,double upMargin){
		this.hMargin=hMargin;
		this.upMargin=upMargin;
	}
	
	public void setMainPageController(mainpageController mpCtr){
		this.mpCtr=mpCtr;
	}

	private Pattern getPattern(Pos p){
		Pattern ptn=new Pattern();
		ptn.center=p;
		ptn.ptn.add(p);
		for(int i=0;i<ptn.ptn.size();i++){
			int x=ptn.ptn.get(i).a,y=ptn.ptn.get(i).b;
			Image[] imgs=Tools.getImages(x, y, ivs, numOfLine, numOfCol);
			
			int lineSameNum=0,colSameNum=0;
			ArrayList<Pos> lineTemp=new ArrayList<Pos>();
			ArrayList<Pos> colTemp=new ArrayList<Pos>();
			
			if(imgs[2]!=null&&Tools.isSameImg(imgs[0],imgs[2])){
				lineSameNum++;
				lineTemp.add(new Pos(x,y-1));
				
				if(imgs[1]!=null&&Tools.isSameImg(imgs[1],imgs[2])){
					lineSameNum++;
					lineTemp.add(new Pos(x,y-2));
				}
			}
			if(imgs[3]!=null&&Tools.isSameImg(imgs[0],imgs[3])){
				lineSameNum++;
				lineTemp.add(new Pos(x,y+1));
				if(imgs[4]!=null&&Tools.isSameImg(imgs[3],imgs[4])){
					lineSameNum++;
					lineTemp.add(new Pos(x,y+2));
				}
			}
			if(lineSameNum==1) lineTemp.remove(lineTemp.size()-1);
			Tools.merge(ptn.ptn,lineTemp);
			
			if(imgs[6]!=null&&Tools.isSameImg(imgs[0],imgs[6])){
				colSameNum++;
				colTemp.add(new Pos(x-1,y));
				if(imgs[5]!=null&&Tools.isSameImg(imgs[6],imgs[5])){
					colSameNum++;
					colTemp.add(new Pos(x-2,y));
				}
			}
			if(imgs[7]!=null&&Tools.isSameImg(imgs[0],imgs[7])){
				colSameNum++;
				colTemp.add(new Pos(x+1,y));
				if(imgs[8]!=null&&Tools.isSameImg(imgs[7],imgs[8])){
					colSameNum++;
					colTemp.add(new Pos(x+2,y));
				}
			}
			if(colSameNum==1) colTemp.remove(colTemp.size()-1);
			Tools.merge(ptn.ptn,colTemp);
			
			if(ptn.ptn.size()==1){ptn.ptn.remove(0);break;}
			
		}
		return ptn;
	}

	private void exchAnime(Pos p1,Pos p2){
		
		double x1=ivs[p1.a][p1.b].getX(),y1=ivs[p1.a][p1.b].getY(),
				x2=ivs[p2.a][p2.b].getX(),y2=ivs[p2.a][p2.b].getY();
		KeyValue kv1=new KeyValue(ivs[p1.a][p1.b].xProperty(),x2);
		KeyValue kv2=new KeyValue(ivs[p1.a][p1.b].yProperty(),y2);
		KeyValue kv3=new KeyValue(ivs[p2.a][p2.b].xProperty(),x1);
		KeyValue kv4=new KeyValue(ivs[p2.a][p2.b].yProperty(),y1);
		KeyFrame kf=new KeyFrame(Duration.millis(animeDuration),kv1,kv2,kv3,kv4);
		Timeline tl=new Timeline();
		tl.getKeyFrames().add(kf);
		tl.setOnFinished(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				exchange(p1,p2);
			}
		});
		tl.play();
	}

	/**
	 * 该方法中更新了剩余步数，并进行了结束条件判断，还更新了连击次数
	 */
	private void elimJudge(ArrayList<Pos> ps){
		clearData();
		System.gc();
		ArrayList<Pos> elim1=new ArrayList<Pos>();
		ArrayList<Pos> elim2=new ArrayList<Pos>();
		
		if(!elimed&&ps.size()==2) doubleSpecialJudge(elim1);
		
		ArrayList<Pattern> ptns=new ArrayList<Pattern>();
		for(Pos p:ps){
			boolean contains=false;
			l1:for(Pattern ptn:ptns){
				if(ptn.contains(p)) {
					contains=true;
					break l1;
				}
			}
			if(!contains){
				Pattern ptn=getPattern(p);
				if(ptn.ptn.size()!=0){
					ptns.add(ptn);
					ptn.getResult();
					for(Pos pos:ptn.h){
						if(!Tools.in(pos, elim1)){
							int x=pos.a,y=pos.b;
							ivs[x][y].setId(ids[1]);
							String url=ivs[x][y].getImage().impl_getUrl().split("_|\\.")[0]+imgSpec[0]+imgSuff;
							ivs[x][y].setImage(new Image(url));
						}
					}
					for(Pos pos:ptn.v){
						if(!Tools.in(pos, elim1)){
							int x=pos.a,y=pos.b;
							ivs[x][y].setId(ids[2]);
							String url=ivs[x][y].getImage().impl_getUrl().split("_|\\.")[0]+imgSpec[1]+imgSuff;
							ivs[x][y].setImage(new Image(url));
						}
					}
					for(Pos pos:ptn.b){
						if(!Tools.in(pos, elim1)){
							int x=pos.a,y=pos.b;
							ivs[x][y].setId(ids[3]);
							String url=ivs[x][y].getImage().impl_getUrl().split("_|\\.")[0]+imgSpec[2]+imgSuff;
							ivs[x][y].setImage(new Image(url));
						}
					}
					for(Pos pos:ptn.s){
						if(!Tools.in(pos, elim1)){
							int x=pos.a,y=pos.b;
							ivs[x][y].setId(ids[4]);
							String url=imgPathPref+sameImg+imgSuffGIF;
							ivs[x][y].setImage(new Image(url));
						}
					}
					Tools.merge(elim2,ptn.elim);
				}
			}
		}
		if(ptns.size()!=0) lianji++;
		Tools.merge(elim1, elim2);
		elimComplete(elim1);
		
		if(elim1.size()!=0) {
			if(!timesChanged){
				mpUpdateTimes();
				timesChanged=true;
			}
			elimAnime(elim1);
		}
		else if(!elimed&&ps.size()==2) {
			if(!violent)exchAnime(ps.get(0),ps.get(1));
			else {
				finished=true;
			}
		}else {
			mpEndingJudge();
			finished=true;
			System.gc();
		}
		elimJudged=true;
	}
	
	private void mpEndingJudge() {
		if(mpCtr.remainingTimes==0) mpCtr.end();
	}

	/**
	 * 该方法中同样更新了剩余步数
	 */
	private void sameElimJudge(Pos p1,Pos p2){
		clearData();
		ArrayList<Pos> ps=new ArrayList<Pos>();
		if(ivs[p1.a][p1.b].getId().equals(ids[4])&&ivs[p2.a][p2.b].getId().equals(ids[4])){
			num[6]++;
			for(int i=0;i<numOfLine;i++){
				for(int k=0;k<numOfCol;k++)ps.add(new Pos(i,k,deathcauses[6]));
			}
		}else{
			num[2]++;
			
			Image img;
			String id;
			if(ivs[p1.a][p1.b].getId().equals(ids[4])){
				img=ivs[p2.a][p2.b].getImage();
				id=ivs[p2.a][p2.b].getId();
				ps.add(p1);
			}
			else{
				img=ivs[p1.a][p1.b].getImage();
				id=ivs[p1.a][p1.b].getId();
				ps.add(p2);
			}
			for(int i=0;i<numOfLine;i++){
				for(int k=0;k<numOfCol;k++){
					if(Tools.isSameImg(ivs[i][k].getImage(),img)){
						ps.add(new Pos(i,k,deathcauses[2]));
						if(!id.equals(ids[0]))ivs[i][k].setId(id);
					}
				}
			}
			elimComplete(ps);
		}
		mpUpdateTimes();
		timesChanged=true;
		elimJudged=true;
		elimAnime(ps);
	}

	private void elimAnime(ArrayList<Pos> ps){
		KeyValue[] kvs=new KeyValue[ps.size()*4];
		for(int i=0;i<ps.size();i++){
			int x=ps.get(i).a,y=ps.get(i).b;
			kvs[4*i]=new KeyValue(ivs[x][y].opacityProperty(),0);
			kvs[4*i+1]=new KeyValue(ivs[x][y].rotateProperty(),720);
			kvs[4*i+2]=new KeyValue(ivs[x][y].scaleXProperty(),0);
			kvs[4*i+3]=new KeyValue(ivs[x][y].scaleYProperty(),0);
		}
		KeyFrame kf=new KeyFrame(Duration.millis(animeDuration),kvs);
		Timeline tl=new Timeline();
		tl.getKeyFrames().add(kf);
		tl.setOnFinished(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				elim(ps);
			}
		});
		tl.play();
	}
	
	private void dropJudge(ArrayList<Pos> ps){
		ArrayList<Pos> origin=new ArrayList<Pos>();
		ArrayList<Integer> destiny=new ArrayList<Integer>();
		ArrayList<Integer> col=new ArrayList<Integer>();
		ArrayList<Integer> nums=new ArrayList<Integer>();
		ArrayList<Pos> firstNulls=new ArrayList<Pos>();
		for(Pos p:ps){
			if(!col.contains(p.b)) col.add(p.b);
		}
		for(int k:col){
			int num=0,firstNull=-1;
			boolean found=false;
			for(int i=numOfLine-1;i>=0;i--){
				if(!found&&ivs[i][k]==null){
					num++;
					found=true;
					firstNull=i;
				}else if(ivs[i][k]==null)
					num++;
				else if(num>0){
					origin.add(new Pos(i,k));
					destiny.add(num);
				}
			}
			nums.add(num);firstNulls.add(new Pos(firstNull,k));
		}
		dropAnime(origin,destiny,col,nums,firstNulls);
	}
	
	private void dropAnime(ArrayList<Pos> origin,ArrayList<Integer> destiny,
			ArrayList<Integer> col,ArrayList<Integer> nums,ArrayList<Pos> firstNulls){
		
		KeyValue[] kvs1=new KeyValue[origin.size()];
		for(int i=0;i<origin.size();i++){
			int x=origin.get(i).a,y=origin.get(i).b;
			double offset=destiny.get(i)*(picHeight+space);
			kvs1[i]=new KeyValue(ivs[x][y].yProperty(),ivs[x][y].getY()+offset);
		}
		int sum=0;
		for(int num:nums) sum+=num;
		KeyValue[] kvs2=new KeyValue[sum];
		ImageView[] ivsTemp=Tools.produceRandomIvs(sum, imgPathPref,imgRsc, imgSuff, picWidth, picHeight);
		sum=0;
		for(int n=0;n<col.size();n++){
			int k=col.get(n),num=nums.get(n);
			for(int i=0;i<num;i++){
				ivsTemp[sum+i].setId(ids[0]);
				ivsTemp[sum+i].setX(k*(picWidth+space));
				ivsTemp[sum+i].setY(-(num-i)*(picHeight+space));
				mpCtr.main.getChildren().add(ivsTemp[sum+i]);
				kvs2[sum+i]=new KeyValue(ivsTemp[sum+i].yProperty(),i*(picHeight+space));
			}
			sum+=num;
		}
		mpCtr.main.toBack();
		KeyValue[] kvs=Tools.merge(kvs1, kvs2);
		
		KeyFrame kf=new KeyFrame(Duration.millis(animeDuration),kvs);
		Timeline tl=new Timeline();
		tl.getKeyFrames().add(kf);
		tl.setOnFinished(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				drop(origin,destiny,firstNulls,col,nums,ivsTemp);
			}
		});
		tl.play();
	}
	
	private void drop(ArrayList<Pos> origin,ArrayList<Integer> destiny,ArrayList<Pos> firstNulls,
						ArrayList<Integer> col,ArrayList<Integer> nums,ImageView[] ivsTemp){
		for(int i=0;i<origin.size();i++){
			int x=origin.get(i).a,y=origin.get(i).b;
			ivs[x+destiny.get(i)][y]=ivs[x][y];
		}
		int sum=0;
		for(int n=0;n<col.size();n++){
			int k=col.get(n),num=nums.get(n);
			for(int i=0;i<num;i++){
				ivs[i][k]=ivsTemp[sum+i];
			}
			sum+=num;
		}
		
		ArrayList<Pos> ps=new ArrayList<Pos>();
		for(Pos p:firstNulls){
			for(int i=0;i<=p.a;i++) ps.add(new Pos(i,p.b));
		}
		origin=null;
		destiny=null;
		firstNulls=null;
		col=null;
		nums=null;
		ivsTemp=null;
		elimJudge(ps);
	}

	private void clearData(){
		for(int i=0;i<num.length;i++) num[i]=0;
	}
	
	private void elimComplete(ArrayList<Pos> ps){
		for(int n=0;n<ps.size();n++){
			int x=ps.get(n).a,y=ps.get(n).b;
			String id=ivs[x][y].getId();
			if(id.equals(ids[0]));
			else if(id.equals(ids[1])){
				for(int i=0;i<numOfCol;i++){
					Pos p=new Pos(x,i,deathcauses[0]);
					if(!Tools.in(p, ps)) ps.add(p);
				}
			}else if(id.equals(ids[2])){
				for(int i=0;i<numOfLine;i++){
					Pos p=new Pos(i,y,deathcauses[0]);
					if(!Tools.in(p, ps)) ps.add(p);
				}
			}else if(id.equals(ids[3])){
				Pos[] psTemp=Tools.getNearPos(ps.get(n), numOfLine, numOfCol);
				for(Pos p:psTemp){
					p.deathCause=deathcauses[1];
					if(p.valid&&!Tools.in(p, ps)) ps.add(p);
				}	
			}else{
				if(n==0);
				else{
					int index=(int)(Math.random()*imgRsc.length);
					Image rdmImg=new Image(imgPathPref+imgRsc[index]+imgSuff);
					for(int i=0;i<numOfLine;i++){
						for(int k=0;k<numOfCol;k++){
							if(Tools.isSameImg(rdmImg, ivs[i][k].getImage())){
								Pos p=new Pos(i,k,deathcauses[2]);
								if(!Tools.in(p, ps))ps.add(p);
							}
						}
					}
				}
			}
		}
	}
	
	private void doubleSpecialJudge(ArrayList<Pos> elim){
		String id1=ivs[p1.a][p1.b].getId(),id2=ivs[p2.a][p2.b].getId();
		if(!id1.equals(ids[0])&&!id2.equals(ids[0])){
			if(id1!=ids[3]&&id2!=ids[3]){
				if(!Tools.in(p1, elim))elim.add(p1);
				if(!Tools.in(p2, elim))elim.add(p2);
				num[3]++;
				elimComplete(elim);
				for(Pos p:elim)
					if(!p.isSameAs(p1)&&!p.isSameAs(p2))
						p.deathCause=deathcauses[3];
			}else if(id1.equals(ids[1])){
				num[4]++;
				for(int i=p2.a-1;i<=p2.a+1;i++){
					for(int k=0;k<numOfCol;k++){
						Pos p=new Pos(i,k,numOfLine,numOfCol,deathcauses[4]);
						if(p.valid&&!Tools.in(p, elim))elim.add(p);
					}
				}
			}else if(id1.equals(ids[2])){
				num[4]++;
				for(int i=0;i<numOfLine;i++){
					for(int k=p2.b-1;k<=p2.b+1;k++){
						Pos p=new Pos(i,k,numOfLine,numOfCol,deathcauses[4]);
						if(p.valid&&!Tools.in(p, elim))elim.add(p);
					}
				}
			}else if(id2.equals(ids[1])){
				num[4]++;
				for(int i=p1.a-1;i<=p1.a+1;i++){
					for(int k=0;k<numOfCol;k++){
						Pos p=new Pos(i,k,numOfLine,numOfCol,deathcauses[4]);
						if(p.valid&&!Tools.in(p, elim)) elim.add(p);
					}
				}
			}else if(id2.equals(ids[2])){
				num[4]++;
				for(int i=0;i<numOfLine;i++){
					for(int k=p1.b-1;k<=p1.b+1;k++){
						Pos p=new Pos(i,k,numOfLine,numOfCol,deathcauses[4]);
						if(p.valid&&!Tools.in(p, elim))elim.add(p);
					}
				}
			}else{
				num[5]++;
				if(p1.a==p2.a){
					for(int i=p1.a-2;i<=p1.a+2;i++){
						int start=p1.b-2,end=p2.b+2;
						if(p1.b>p2.b) {start=p2.b-2;end=p1.b+2;}
						for(int k=start;k<=end;k++){
							Pos p=new Pos(i,k,numOfLine,numOfCol,deathcauses[5]);
							if(p.valid&&!Tools.in(p, elim)) elim.add(p);
						}
					}
				}else{
					int start=p1.a-2,end=p2.a+2;
					if(p1.a>p2.a){start=p2.a-2;end=p1.a+2;}
					for(int i=start;i<=end;i++)
						for(int k=p1.b-2;k<=p1.b+2;k++){
							Pos p=new Pos(i,k,numOfLine,numOfCol,deathcauses[5]);
							if(p.valid&&!Tools.in(p, elim))elim.add(p);
						}
				}
			}
		}
	}
	
}
