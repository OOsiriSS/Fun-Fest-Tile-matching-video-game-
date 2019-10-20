package kienaiyo.save;

import java.util.ArrayList;

import kienaiyo.model.Gadget;
import kienaiyo.util.TimeAndDate;

public class Save {

	public String saveName;//存档名
	public String playerName;//玩家名称
	public int coins;//金币数量
	public int progress;//游戏进度
	/**
	 * 游戏总时间，格式为"**d**h**min**s"
	 */
	public String totalTime="0d0h0min0s";
	/**
	 * 上次游戏时间，格式为"yyyy-MM-dd E hh:mm:ss"
	 */
	public String lastTime;
	public ArrayList<Gadget> gadgets=new ArrayList<Gadget>();//道具
	
	private TimeAndDate startTad,endTad;
	
	public Save(String name){
		saveName=name;
	}
	
	public Save(String saveName,String playerName){
		this.saveName=saveName;this.playerName=playerName;
	}
	
	public void setStartTime(){
		startTad=new TimeAndDate();
	}

	public void setEndTime(){
		endTad=new TimeAndDate();
	}

	public void updateTotalTime(){
		totalTime=TimeAndDate.addTime(totalTime, startTad.getDistance(endTad));
	}

	public void setLastTime(){
		lastTime=startTad.getFullTime();
	}
	
	public void addGadgets(String name,int num){
		Gadget g=get(name);
		if(g!=null) g.number+=num;
		else{
			g=new Gadget(name);
			g.number=num;
			gadgets.add(g);
		}
	}
	
	private Gadget get(String name){
		for(Gadget ggt:gadgets) if(ggt.name.equals(name)) return ggt;
		return null;
	}
	
}
