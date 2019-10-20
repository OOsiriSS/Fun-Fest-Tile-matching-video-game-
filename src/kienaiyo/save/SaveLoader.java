package kienaiyo.save;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import kienaiyo.model.Gadget;

public class SaveLoader {

	private static String lineSep=System.lineSeparator();
	private static String subSep=";";
	private static String colon=": ";
	private static String innerSep=",";
	private static String pref="saves/";
	private static String suff=".friends";
	
	public Save[] loadAll(){
		File root=new File("saves");
		File[] saveFiles=root.listFiles();
		if(saveFiles!=null){
			Save[] saves=new Save[saveFiles.length];
			for(int i=0;i<saves.length;i++){
				saves[i]=loadSave(saveFiles[i]);
			}
			return saves;
		}
		return null;
	}
	
	public Save loadSave(File file){
		try {
			Save save=new Save(file.getName().split("\\.")[0]);
			FileReader fr=new FileReader(file);
			BufferedReader br=new BufferedReader(fr);
			String temp=br.readLine();
			save.saveName=temp.split(colon)[1];
			temp=br.readLine();
			save.playerName=temp.split(colon)[1];
			temp=br.readLine();
			save.coins=Integer.parseInt(temp.split(colon)[1]);
			temp=br.readLine();
			save.progress=Integer.parseInt(temp.split(colon)[1]);
			temp=br.readLine();
			save.totalTime=temp.split(colon)[1];
			temp=br.readLine();
			save.lastTime=temp.split(colon)[1];
			temp=br.readLine();
			if(temp==null);
			else{
				String[] items=temp.split(colon)[1].split(subSep);
				for(int i=0;i<items.length;i++){
					Gadget g=new Gadget(items[i].split(innerSep)[0]);
					g.number=Integer.parseInt(items[i].split(innerSep)[1]);
					save.gadgets.add(g);
				}
			}
			br.close();
			return save;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public Save loadSave(String name){
		File file=new File(pref+name+suff);
		return loadSave(file);
	}
	
	public void setSave(Save save){
		try {
			File file=new File(pref+save.saveName+suff);
			FileWriter fw=new FileWriter(file);
			fw.write("Save name"+colon+save.saveName+lineSep);
			fw.write("Player name"+colon+save.playerName+lineSep);
			fw.write("Coins"+colon+save.coins+lineSep);
			fw.write("Game progress"+colon+save.progress+lineSep);
			fw.write("Total time"+colon+save.totalTime+lineSep);
			fw.write("Last played at"+colon+save.lastTime+lineSep);
			if(save.gadgets.size()==0);
			else{
				StringBuffer sb=new StringBuffer("Gadgets"+colon);
				for(Gadget gt:save.gadgets){
					sb.append(gt.name+innerSep+gt.number+subSep);
				}
				sb.deleteCharAt(sb.length()-1);
				sb.append(lineSep);
				String gadgetsInput=sb.toString();
				fw.write(gadgetsInput);
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteSave(String name){
		File file=new File(pref+name+suff);
		if(file.exists()) file.delete();
	}
	
}
