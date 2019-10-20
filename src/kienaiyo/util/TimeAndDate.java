package kienaiyo.util;

import java.util.Date;

/**
 * 该类用于获取当前日期和时间以及进行时间方面的运算
 * 全格式为"yyyy-MM-dd E hh:mm:ss"
 */
public class TimeAndDate {

	private String y,M,d,E,h,m,s;
	private Date date;
	
	public TimeAndDate(){
		date=new Date();
		String[] temp=date.toString().split(" ");
		String[] time=temp[3].split(":");
		y=temp[5];d=temp[2];
		if(temp[1].equals("Jan"))M="1";
		else if(temp[1].equals("Feb"))M="2";
		else if(temp[1].equals("Mar"))M="3";
		else if(temp[1].equals("Apr"))M="4";
		else if(temp[1].equals("May"))M="5";
		else if(temp[1].equals("Jun"))M="6";
		else if(temp[1].equals("Jul"))M="7";
		else if(temp[1].equals("Aug"))M="8";
		else if(temp[1].equals("Sep"))M="9";
		else if(temp[1].equals("Oct"))M="10";
		else if(temp[1].equals("Nov"))M="11";
		else if(temp[1].equals("Dec"))M="12";
		E=temp[0];
		h=time[0];m=time[1];s=time[2];
	}
	
	/**
	 * 返回格式为全格式
	 */
	public String getFullTime(){
		return y+"-"+M+"-"+d+" "+E+" "+h+":"+m+":"+s;
	}
	
	/**
	 * 返回格式为"**d**h**min**s"
	 */
	public String getDistance(TimeAndDate tad){
		long milli=date.getTime()-tad.date.getTime();
		milli=(milli>=0)?milli:(-milli);
		int temp=(int)(milli/1000);
		int dayRatio=24*60*60,hourRatio=60*60,minRatio=60;
		int day=temp/dayRatio;
		temp%=dayRatio;
		int hour=temp/hourRatio;
		temp%=hourRatio;
		int min=temp/minRatio;
		temp%=minRatio;
		int sec=temp;
		return day+"d"+hour+"h"+min+"min"+sec+"s";
	}
	
	/**
	 * 将两段时间段相加，得到新的时间段
	 * 时间段的格式为"**d**h**min**s"
	 */
	public static String addTime(String time1,String time2){
		String[] oldStr=time1.split("d|h|min|s");
		int[] oldValues=new int[4];
		for(int i=0;i<4;i++) oldValues[i]=Integer.parseInt(oldStr[i]);
		oldStr=null;
		String[] newStr=time2.split("d|h|min|s");
		int[] newValues=new int[4];
		for(int i=0;i<4;i++) newValues[i]=Integer.parseInt(newStr[i])+oldValues[i];
		newStr=null;oldValues=null;
		int temp=newValues[3]/60;
		newValues[3]%=60;
		newValues[2]+=temp;
		temp=newValues[2]/60;
		newValues[2]%=60;
		newValues[1]+=temp;
		temp=newValues[1]/24;
		newValues[1]%=24;
		newValues[0]+=temp;
		return newValues[0]+"d"+newValues[1]+"h"+newValues[2]+"min"+newValues[3]+"s";
	}
	
}
