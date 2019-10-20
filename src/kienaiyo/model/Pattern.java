package kienaiyo.model;

import java.util.ArrayList;

import javafx.scene.image.ImageView;
import kienaiyo.util.Tools;

public class Pattern {

	public ArrayList<Pos> ptn=new ArrayList<Pos>();
	public Pos center;
	
	public ArrayList<Pos> elim=new ArrayList<Pos>();
	public ArrayList<Pos> ptnCopy;
	public ArrayList<Pos> h=new ArrayList<Pos>();
	public ArrayList<Pos> v=new ArrayList<Pos>();
	public ArrayList<Pos> b=new ArrayList<Pos>();
	public ArrayList<Pos> s=new ArrayList<Pos>();
	public ArrayList<Line> lines;
	public ArrayList<Col> cols;
	
	public void getResult(){
		ptnCopy=new ArrayList<Pos>();
		for(Pos p:ptn) ptnCopy.add(p);
		getLinesAndCols();
		sPtn();
		bPtn();
		vPtn();
		hPtn();
		for(Pos p:ptnCopy) if(!spec(p)) elim.add(p);
	}

	public void getLinesAndCols(){
		lines=new ArrayList<Line>();
		cols=new ArrayList<Col>();
		System.gc();
		for(Pos p:ptn){
			boolean lineContains=false;
			l1:for(Line l:lines) if(l.contains(p)) {lineContains=true;break l1;}
			
			if(!lineContains){
				ArrayList<Pos> sameLine=new ArrayList<Pos>();
				for(Pos pos:ptn) if(pos.a==p.a) sameLine.add(pos);
				Pos pTemp=p;
				boolean connected=true;
				
				Line line=new Line(p.a);
				//to the left
				while(connected){
					Pos pNext=null;
					l2:for(Pos posTemp:sameLine) 
							if(posTemp.isLeftTo(pTemp)){
								pNext=posTemp;break l2;
							}
					if(pNext!=null) {
						pTemp=pNext;
						line.linePos.add(pTemp);
					}
					else connected=false;
				}
				//to the right
				connected=true;
				pTemp=p;
				while(connected){
					Pos pNext=null;
					l3:for(Pos posTemp:sameLine) 
						if(posTemp.isRightTo(pTemp)) {
							pNext=posTemp;break l3;
						}
					if(pNext!=null){
						pTemp=pNext;
						line.linePos.add(pTemp);
					}else connected=false;
				}
				if(line.linePos.size()>1){
					line.linePos.add(p);
					lines.add(line);
				}
				
//				System.gc();
			}
			
			boolean colContains=false;
			l4:for(Col c:cols) if(c.contains(p)) {colContains=true;break l4;}
			
			if(!colContains){
				ArrayList<Pos> sameCol=new ArrayList<Pos>();
				for(Pos pos:ptn) if(pos.b==p.b) sameCol.add(pos);
				Pos pTemp=p;
				boolean connected=true;
				
				Col col=new Col(p.b);
				//to the up
				while(connected){
					Pos pNext=null;
					l5:for(Pos posTemp:sameCol) 
						if(posTemp.isUpTo(pTemp)) {
							pNext=posTemp;break l5;
						}
					if(pNext!=null){
						pTemp=pNext;
						col.colPos.add(pTemp);
					}else connected=false;
				}
				//to the down
				pTemp=p;
				connected=true;
				while(connected){
					Pos pNext=null;
					l6:for(Pos posTemp:sameCol) 
						if(posTemp.isDownTo(pTemp)){
							pNext=posTemp;break l6;
						}
					if(pNext!=null){
						pTemp=pNext;
						col.colPos.add(pTemp);
					}else connected=false;
				}
				
				if(col.colPos.size()>1){
					col.colPos.add(p);
					cols.add(col);
				}
				
//				System.gc();
			}
			
		}
	}
	
	public void sPtn(){
		boolean changed=false;
		for(Line line:lines){
			if(line.linePos.size()>=5){
				int min=line.linePos.get(0).b;
				for(Pos p:line.linePos) if(p.b<min) min=p.b;
				int index=min+line.linePos.size()/2;
				s.add(Tools.getSpecificPos(ptn, line.lineIndex, index));
				
				ArrayList<Pos> ptnTemp=new ArrayList<Pos>();
				for(Pos p:ptn) if(!line.contains(p)) ptnTemp.add(p);
				ptn=ptnTemp;
				changed=true;
//				System.gc();
			}
		}
		if(changed){getLinesAndCols();changed=false;}
		for(Col col:cols){
			if(col.colPos.size()>=5){
				int min=col.colPos.get(0).a;
				for(Pos p:col.colPos) if(p.a<min) min=p.a;
				int index=min+col.colPos.size()/2;
				s.add(Tools.getSpecificPos(ptn, index, col.colIndex));
				
				ArrayList<Pos> ptnTemp=new ArrayList<Pos>();
				for(Pos p:ptn) if(!col.contains(p)) ptnTemp.add(p);
				ptn=ptnTemp;
				changed=true;
//				System.gc();
			}
		}
		if(changed)getLinesAndCols();
	}
	
	public void bPtn(){
		boolean formable=true;
		if(lines.size()==0||cols.size()==0) formable=false;
		while(formable){
			Line lineTemp=null;
			Col colTemp=null;
			l1:for(Line line:lines){
				Pos p;
				for(Col col:cols){
					p=line.getCrossPoint(col);
					if(p!=null) {
						b.add(p);
						lineTemp=line;
						colTemp=col;
						break l1;
					}
					
				}
			}
			if(lineTemp==null||colTemp==null) formable=false;
			else{
				ArrayList<Pos> ptnTemp=new ArrayList<Pos>();
				for(Pos p:ptn) 
					if(!lineTemp.contains(p)&&!colTemp.contains(p)) 
						ptnTemp.add(p);
				ptn=ptnTemp;
				getLinesAndCols();
				if(lines.size()==0||cols.size()==0) formable=false;
			}
//			System.gc();
		}
	}
	
	public void vPtn(){
		boolean changed=false;
		for(Line line:lines){
			if(line.linePos.size()==4){
				int min=line.linePos.get(0).b;
				for(Pos p:line.linePos) if(p.b<min) min=p.b;
				if(center.b>min&&center.b<min+3) v.add(center);
				else{
					int index=min+1;
					v.add(Tools.getSpecificPos(ptn, line.lineIndex, index));
				}
				ArrayList<Pos> ptnTemp=new ArrayList<Pos>();
				for(Pos p:ptn)
					if(!line.contains(p)) ptnTemp.add(p);
				ptn=ptnTemp;
				changed=true;
			}
		}
		if(changed) getLinesAndCols();
	}
	
	public void hPtn(){
		boolean changed=false;
		for(Col col:cols){
			if(col.colPos.size()==4){
				int min=col.colPos.get(0).a;
				for(Pos p:col.colPos) if(p.a<min) min=p.a;
				if(center.a>min&&center.a<min+3) h.add(center);
				else{
					int index=min+1;
					h.add(Tools.getSpecificPos(ptn, index, col.colIndex));
				}
				ArrayList<Pos> ptnTemp=new ArrayList<Pos>();
				for(Pos p:ptn)
					if(!col.contains(p)) ptnTemp.add(p);
				ptn=ptnTemp;
				changed=true;
//				System.gc();
			}
		}
		if(changed)getLinesAndCols();
	}
	
	private boolean spec(Pos p){
		return Tools.in(p, h)||Tools.in(p, v)||Tools.in(p, b)||Tools.in(p, s);
	} 

	public boolean contains(Pos p){
		for(Pos pos:ptnCopy){
			if(pos.isSameAs(p)) return true;
		}
		return false;
	}
	
}
