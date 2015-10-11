package com.mclama;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Campaign {
	
	private App app;
	
	private String name;
	private String proficiency;
	
	private ArrayList<String> characters;
	private ArrayList<String> towns;



	public Campaign(App app, String name){
		this.app = app;
		this.name = name;
		this.proficiency = "5,9,13,17"; //5e by default
		
		//reset character list
		getCharacterList(name);
		getTownList(name);
	}


	public void getCharacterList(String name) {
		characters = new ArrayList<String>();
		
		String cDir = App.workDir + "\\Campaigns\\" + name + "\\";
		
		if(new File(cDir).exists()){
			if(new File(cDir + "Characters\\").exists()){
				//if exist, then begin adding every folder in directory to the list.
				
				File file = new File(cDir + "Characters\\");
				String[] fNames = file.list();

				for(String folder : fNames)
				{
					if (new File(cDir + "Characters\\" + folder).isFile())
				    {
						if(folder.replace(".txt", "") != null){
							//app.addCharToList(folder.replace(".txt", ""));
							characters.add(folder.replace(".txt", ""));
					        System.out.println("Added `" + folder.replace(".txt", "") + "` to campaign " + name + " Character list.");
						}
				    	
				    }
				}
			} else System.out.println("Couldnt find Characters folder under campaign " + name);
		} else System.out.println("Couldnt find campaign " + name);
	}
	
	public void getTownList(String name) {
		towns = new ArrayList<String>();
		
		String cDir = App.workDir + "\\Campaigns\\" + name + "\\";
		//campaign Directory
		
		if(new File(cDir).exists()){
			if(new File(cDir + "Towns\\").exists()){
				//if exist, then begin adding every folder in directory to the list.
				
				File folder = new File(cDir + "Towns\\");
				String[] fNames = folder.list();

				for(String file : fNames)
				{
					if (new File(folder + "\\" +  file).isFile())
				    {
						if(file.replace(".txt", "") != null){
							//app.addTownToList(file.replace(".txt", ""));
							towns.add(file.replace(".txt", ""));
					        System.out.println("Added `" + file.replace(".txt", "") + "` to campaign " + name + " Town list.");
						}
				    	
				    }
				}
			} else System.out.println("Couldnt find Towns folder under campaign " + name);
		} else System.out.println("Couldnt find campaign " + name);
	}
	
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getProficiency() {
		return proficiency;
	}


	public void setProficiency(String proficiency) {
		this.proficiency = proficiency;
	}


	public ArrayList<String> getCharacters() {
		return characters;
	}


	public void setCharacters(ArrayList<String> characters) {
		this.characters = characters;
	}


	public ArrayList<String> getTowns() {
		return towns;
	}


	public void setTowns(ArrayList<String> towns) {
		this.towns = towns;
	}
	
}
