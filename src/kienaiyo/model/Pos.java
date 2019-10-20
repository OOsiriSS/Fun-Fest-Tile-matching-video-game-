package kienaiyo.model;

import java.util.ArrayList;

public class Pos {

	public int a,b;
	public boolean valid=true;
	public String deathCause;

	public Pos(int a,int b){
		this.a=a;this.b=b;
	}
	
	public Pos(int a,int b,String c){
		this.a=a;this.b=b;deathCause=c;
	}
	
	public Pos(int a,int b,int numOfLine,int numOfCol,String c){
		if(a>=0&&a<numOfLine&&b>=0&&b<numOfCol){
			this.a=a;this.b=b;deathCause=c;
		}else valid=false;
	}
	
	public Pos(int a,int b,int numOfLine,int numOfCol){
		super();
		if(a>=0&&a<numOfLine&&b>=0&&b<numOfCol){
			this.a=a;this.b=b;
		}else valid=false;
	}
	
	public boolean isNextTo(Pos p){
		return (Math.abs(a-p.a)==1&&b==p.b)||(Math.abs(b-p.b)==1&&a==p.a);
	}
	
	public boolean isSameAs(Pos p){
		return a==p.a&&b==p.b;
	}
	
	public boolean isLeftTo(Pos p){
		return a==p.a&&p.b-b==1;
	}

	public boolean isRightTo(Pos p){
		return a==p.a&&b-p.b==1;
	}
	
	public boolean isUpTo(Pos p){
		return b==p.b&&p.a-a==1;
	}
	
	public boolean isDownTo(Pos p){
		return b==p.b&&a-p.a==1;
	}
	
	
}
