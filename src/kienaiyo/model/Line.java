package kienaiyo.model;

import java.util.ArrayList;

import kienaiyo.util.Tools;

public class Line {

		public ArrayList<Pos> linePos=new ArrayList<Pos>();
		public int lineIndex;
		
		public Line(int n){
			lineIndex=n;
		}
		
		public Pos getCrossPoint(Col col){
			for(Pos p:linePos){
				if(col.contains(p)) return p;
			}
			return null;
		}
		
		public boolean contains(Pos p){
			return Tools.in(p, linePos);
		}
	
}
