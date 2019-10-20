package kienaiyo.util;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;
import kienaiyo.model.Board;
import kienaiyo.model.Gadget;
import kienaiyo.model.Pos;

public class Tools {

	private static int duration=500;
	
	
	public static ImageView[][] initIvs(int numOfLine,int numOfCol,String imgPathPref,String[] imgRsc,String imgSuff,double picWidth,double picHeight){
		Image[] imgs=new Image[imgRsc.length];
		for(int i=0;i<imgRsc.length;i++){
			imgs[i]=new Image(imgPathPref+imgRsc[i]+imgSuff);
		}
		ImageView[][] ivs=new ImageView[numOfLine][numOfCol];
		for(int i=0;i<numOfLine;i++){
			for(int k=0;k<numOfCol;k++){
				if(i>=2&&k>=2){
					Image img1=ivs[i-2][k].getImage();
					Image img2=ivs[i-1][k].getImage();
					Image img3=ivs[i][k-2].getImage();
					Image img4=ivs[i][k-1].getImage();
					ArrayList<Image> imgTemp=new ArrayList<Image>();
					for(int j=0;j<imgRsc.length;j++) imgTemp.add(imgs[j]); 
					
					if(img1.equals(img2)) imgTemp.remove(img1);
					if(img3.equals(img4)) imgTemp.remove(img3);
					ivs[i][k]=new ImageView(imgTemp.get((int)(Math.random()*imgTemp.size())));
				}else if(i>=2){
					Image img1=ivs[i-2][k].getImage();
					Image img2=ivs[i-1][k].getImage();
					ArrayList<Image> imgTemp=new ArrayList<Image>();
					for(int j=0;j<imgRsc.length;j++) imgTemp.add(imgs[j]); 
					if(img1.equals(img2)) imgTemp.remove(img1);
					ivs[i][k]=new ImageView(imgTemp.get((int)(Math.random()*imgTemp.size())));
				}else if(k>=2){
					Image img1=ivs[i][k-2].getImage();
					Image img2=ivs[i][k-1].getImage();
					ArrayList<Image> imgTemp=new ArrayList<Image>();
					for(int j=0;j<imgRsc.length;j++) imgTemp.add(imgs[j]); 
					if(img1.equals(img2)) imgTemp.remove(img1);
					ivs[i][k]=new ImageView(imgTemp.get((int)(Math.random()*imgTemp.size())));
				}else {
					ivs[i][k]=new ImageView(imgs[(int)(Math.random()*imgs.length)]);
				}
			}
		}
		
		for(int i=0;i<numOfLine;i++){
			for(int k=0;k<numOfCol;k++){
				ivs[i][k].setFitWidth(picWidth);
				ivs[i][k].setFitHeight(picHeight);
			}
		}
		
		return ivs;
	}
	
	public static Pos getPos(double x,double y,double hMargin,double upMargin,double picWidth,double picHeight,double space){
		return new Pos((int)((y-upMargin)/(picHeight+space)),(int)((x-hMargin)/(picWidth+space)));
	}
	
	public static Image[] getImages(int x,int y,ImageView[][] ivs,int numOfLine,int numOfCol) {
		Image[] imgs=new Image[9];
		imgs[0]=ivs[x][y].getImage();
		imgs[1]=(y<2)?null:ivs[x][y-2].getImage();
		imgs[2]=(y<1)?null:ivs[x][y-1].getImage();
		imgs[3]=(y+1<numOfCol)?ivs[x][y+1].getImage():null;
		imgs[4]=(y+2<numOfCol)?ivs[x][y+2].getImage():null;
		imgs[5]=(x<2)?null:ivs[x-2][y].getImage();
		imgs[6]=(x<1)?null:ivs[x-1][y].getImage();
		imgs[7]=(x+1<numOfLine)?ivs[x+1][y].getImage():null;
		imgs[8]=(x+2<numOfLine)?ivs[x+2][y].getImage():null;
		return imgs;
	}

	public static boolean in(Pos p,ArrayList<Pos> ps){
		for(Pos pos:ps){
			if(p.isSameAs(pos)) return true;
		}
		return false;
	}

	public static Pos[] getNearPos(Pos p,int numOfLine,int numOfCol){
		Pos[] ps=new Pos[8];
		int x=p.a,y=p.b;
		ps[0]=new Pos(x-1,y-1,numOfLine,numOfCol);
		ps[1]=new Pos(x-1,y,numOfLine,numOfCol);
		ps[2]=new Pos(x-1,y+1,numOfLine,numOfCol);
		ps[3]=new Pos(x,y-1,numOfLine,numOfCol);
		ps[4]=new Pos(x,y+1,numOfLine,numOfCol);
		ps[5]=new Pos(x+1,y-1,numOfLine,numOfCol);
		ps[6]=new Pos(x+1,y,numOfLine,numOfCol);
		ps[7]=new Pos(x+1,y+1,numOfLine,numOfCol);
		return ps;
	}
	
	public static ImageView[] produceRandomIvs(int num,String imgPathPref,String[] imgRsc,String imgSuff,double picWidth,double picHeight){
		ImageView[] ivs=new ImageView[num];
		for(int i=0;i<num;i++){
			int randomNum=(int)(Math.random()*imgRsc.length);
			Image img=new Image(imgPathPref+imgRsc[randomNum]+imgSuff);
			ivs[i]=new ImageView(img);
			ivs[i].setFitWidth(picWidth);
			ivs[i].setFitHeight(picHeight);
		}
		return ivs;
	}
	
	public static Pos getSpecificPos(ArrayList<Pos> ps,int x,int y){
		for(Pos p:ps){
			if(p.a==x&&p.b==y) return p;
		}
		return null;
	}
	
	public static KeyValue[] merge(KeyValue[] kvs1,KeyValue[] kvs2){
		KeyValue[] kvs=new KeyValue[kvs1.length+kvs2.length];
		int num=0;
		for(num=0;num<kvs1.length;num++) kvs[num]=kvs1[num];
		for(int i=0;i<kvs2.length;i++) kvs[num+i]=kvs2[i];
		kvs1=null;kvs2=null;
		return kvs;
	}
	
	@SuppressWarnings("deprecation")
	public static boolean isSameImg(Image img1,Image img2){
		String name1=img1.impl_getUrl().split("/|_|\\.")[1],
				name2=img2.impl_getUrl().split("/|_|\\.")[1];
		return name1.equals(name2);
	}
	
	public static void merge(ArrayList<Pos> destiny,ArrayList<Pos> src){
		for(Pos p:src){
			if(!Tools.in(p, destiny)) destiny.add(p);
		}
	}

	public static Image getRandomImg(String imgPathPref,String[] imgRsc,String imgSuff){
		int index=(int)(Math.random()*imgRsc.length);
		return new Image(imgPathPref+imgRsc[index]+imgSuff);
	}
	
	public static void refresh(ImageView[][] ivs,Board board){
		try{
			int numOfLine=ivs.length,numOfCol=ivs[0].length;
			ImageView[][] ivsTemp=new ImageView[numOfLine][numOfCol];
			ArrayList<Pos> ps=new ArrayList<Pos>();
			for(int i=0;i<numOfLine;i++)
				for(int k=0;k<numOfCol;k++)
					ps.add(new Pos(i,k));
			
			KeyValue[] kvs=new KeyValue[2*numOfLine*numOfCol];
			for(int i=0;i<numOfLine;i++)
				for(int k=0;k<numOfCol;k++){
					if(i>=2&&k>=2){
						Image img1=ivsTemp[i-2][k].getImage();
						Image img2=ivsTemp[i-1][k].getImage();
						Image img3=ivsTemp[i][k-2].getImage();
						Image img4=ivsTemp[i][k-1].getImage();
						ArrayList<Pos> psTemp=new ArrayList<Pos>();
						boolean ok=false;
						while(!ok){
							int index=(int)(Math.random()*ps.size());
							Pos p=ps.get(index);
							Image img=ivs[p.a][p.b].getImage();
							if((isSameImg(img,img1)&&isSameImg(img,img2))||
									(isSameImg(img,img3)&&isSameImg(img,img4))){
								ps.remove(index);
								psTemp.add(p);
							}
							else {
								ok=true;
								ivsTemp[i][k]=ivs[p.a][p.b];
								kvs[2*(i*numOfCol+k)]=new KeyValue(ivsTemp[i][k].xProperty(),ivs[i][k].getX());
								kvs[2*(i*numOfCol+k)+1]=new KeyValue(ivsTemp[i][k].yProperty(),ivs[i][k].getY());
								ps.remove(index);
								merge(ps,psTemp);
								psTemp=null;
							}
						}
					}else if(i>=2){
						Image img1=ivsTemp[i-2][k].getImage();
						Image img2=ivsTemp[i-1][k].getImage();
						ArrayList<Pos> psTemp=new ArrayList<Pos>();
						boolean ok=false;
						while(!ok){
							int index=(int)(Math.random()*ps.size());
							Pos p=ps.get(index);
							Image img=ivs[p.a][p.b].getImage();
							if(isSameImg(img,img1)&&isSameImg(img,img2)){
								ps.remove(index);
								psTemp.add(p);
							}else {
								ok=true;
								ivsTemp[i][k]=ivs[p.a][p.b];
								kvs[2*(i*numOfCol+k)]=new KeyValue(ivsTemp[i][k].xProperty(),ivs[i][k].getX());
								kvs[2*(i*numOfCol+k)+1]=new KeyValue(ivsTemp[i][k].yProperty(),ivs[i][k].getY());
								ps.remove(index);
								merge(ps,psTemp);
								psTemp=null;
							}
						}
					}else if(k>=2){
						Image img1=ivsTemp[i][k-2].getImage();
						Image img2=ivsTemp[i][k-1].getImage();
						ArrayList<Pos> psTemp=new ArrayList<Pos>();
						boolean ok=false;
						while(!ok){
							int index=(int)(Math.random()*ps.size());
							Pos p=ps.get(index);
							Image img=ivs[p.a][p.b].getImage();
							if(isSameImg(img,img1)&&isSameImg(img,img2)){
								ps.remove(index);
								psTemp.add(p);
							}else {
								ok=true;
								ivsTemp[i][k]=ivs[p.a][p.b];
								kvs[2*(i*numOfCol+k)]=new KeyValue(ivsTemp[i][k].xProperty(),ivs[i][k].getX());
								kvs[2*(i*numOfCol+k)+1]=new KeyValue(ivsTemp[i][k].yProperty(),ivs[i][k].getY());
								ps.remove(index);
								merge(ps,psTemp);
								psTemp=null;
							}
						}
					} else{
						int index=(int)(Math.random()*ps.size());
						Pos p=ps.get(index);
						ivsTemp[i][k]=ivs[p.a][p.b];
						kvs[2*(i*numOfCol+k)]=new KeyValue(ivsTemp[i][k].xProperty(),ivs[i][k].getX());
						kvs[2*(i*numOfCol+k)+1]=new KeyValue(ivsTemp[i][k].yProperty(),ivs[i][k].getY());
						ps.remove(index);
					}
				}
			KeyFrame kf=new KeyFrame(Duration.millis(duration),kvs);
			Timeline tl=new Timeline();
			tl.getKeyFrames().add(kf);
			tl.setOnFinished(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent e){
					for(int i=0;i<numOfLine;i++)
						for(int k=0;k<numOfCol;k++)
							ivs[i][k]=ivsTemp[i][k];
					board.finished=true;
				}
			});
			tl.play();
		}catch(IndexOutOfBoundsException e){
			refresh(ivs,board);
		}
		
	}
	
	public static Gadget findGgt(ArrayList<Gadget> ggts,String name){
		for(Gadget ggt:ggts) if(ggt.name.equals(name)) return ggt;
		return null;
	}
	
	public static String outName(String s){
		String[] names={"锤子","强势交换","随机变换","随机特效","万能特效","加五步","刷新","回溯"};
		String[] otherNames={"润喉茶","料理","加帕里面包","砂之星"};
		String[] fracNames={"小包碎片","小薮猫碎片","lucky桑碎片","耳廓狐碎片","朱鹮碎片","狮子碎片"};
		for(int i=0;i<Gadget.names.length;i++)
			if(s.equals(Gadget.names[i])) return names[i];
		for(int i=0;i<Gadget.otherNames.length;i++)
			if(s.equals(Gadget.otherNames[i])) return otherNames[i];
		for(int i=0;i<Gadget.fracNames.length;i++)
			if(s.equals(Gadget.fracNames[i])) return fracNames[i];
		return null;
	}
	
	public static void fadeout(Text prompt){
		KeyValue kv=new KeyValue(prompt.opacityProperty(),0);
		KeyFrame kf=new KeyFrame(Duration.millis(1000),kv);
		Timeline t=new Timeline();
		t.getKeyFrames().add(kf);
		t.play();
	}
	
}

