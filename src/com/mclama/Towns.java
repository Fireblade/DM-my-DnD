package com.mclama;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Towns {

	private App app;
	
	private String name = "";
	private String tNote = "";
	private String townSize = "";
	
	public Towns(App app){
		this.app = app;
	}
	
	public void loadTown(String name) {
		this.name = name;
		File saveDir = new File(app.workDir + App.fSep + "Campaigns" + App.fSep + app.campaign.getName() + App.fSep + "Towns" + App.fSep + name + ".txt");
		
		if(saveDir.exists())
		{
			try (BufferedReader br = new BufferedReader(new FileReader(saveDir));)
			{
				String line;
				String[] xspl;
				
				br.readLine(); //ignore this line, we already know the name.
				
				try { townSize = br.readLine().split("Size:: ")[1]; } catch (Exception e) {}
				
				try { tNote = br.readLine().split("Notes:: ")[1];
				} catch (Exception e) {}
				

				if(tNote.contains("]]~")) tNote = tNote.replace("]]~","\n");
				else tNote = tNote.replace("\\n","\n"); //pre-release version
				
				
				
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void saveTown(){
		if(name != null && name != "")
		{
			if(app.isTownNameCurrentlyUsed(name) == false){
				app.lastKnownTownName=name;
				app.addTownToList(name);
			}
			File ctDir = new File(App.workDir + App.fSep + "Campaigns" + App.fSep + App.campaign.getName() + App.fSep + "Towns" + App.fSep);
			// Campaigns/Towns directory
			if(!ctDir.exists())
			{
				ctDir.mkdirs();
			}
			
			File town = new File(ctDir + App.fSep + name + ".txt");
			try 
			{
				PrintWriter out = new PrintWriter(town);
				
				out.println("Name:: " + name);
				out.println("Size:: " + townSize);
				out.println("Notes:: " + tNote.replace("\n", "]]~").replace("\r",""));
				
				
				
				out.close(); //finished writing
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String gettNote() {
		return tNote;
	}

	public void settNote(String tNote) {
		this.tNote = tNote;
	}

	public String getTownSize() {
		return townSize;
	}

	public void setTownSize(String townSize) {
		this.townSize = townSize;
	}
	
	
}
