package kienaiyo.model;

public class Gadget {

	public static String[] names={"chuizi","violentExch","randomChng","randomEff",
								"sameEff","add5times","refresh","backOneStep"};
	public static String[] otherNames={"ochya","ryouri","pan","sandstar"};
	public static String[] fracNames={"kaban","sa-baru","lucky","fenekku","toki","raion"};
	
	public String name;
	public int number;
	
	public Gadget(String name){
		boolean valid=false;
		for(String s:names) if(s.equals(name)){
			valid=true;break;
		}
		if(!valid) for(String s:otherNames) 
			if(s.equals(name)) {valid=true;break;}
		if(!valid) for(String s:fracNames) 
			if(s.equals(name)) {valid=true;break;}
		if(valid)this.name=name;
	}
	
	
	
}
