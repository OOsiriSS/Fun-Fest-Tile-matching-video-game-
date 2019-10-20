package kienaiyo.model;

import java.util.ArrayList;

import kienaiyo.util.Tools;

public class Col {

	public ArrayList<Pos> colPos=new ArrayList<Pos>();
	public int colIndex;
	
	public Col(int n){
		colIndex=n;
	}

	public Pos getCrossPoint(Line line){
		for(Pos p:colPos){
			if(line.contains(p)) return p;
		}
		return null;
	}
	
	public boolean contains(Pos p){
		return Tools.in(p, colPos);
	}
	
	
}
