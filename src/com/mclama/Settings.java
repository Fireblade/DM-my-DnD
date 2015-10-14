package com.mclama;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.ListModel;

public class Settings {
	
	private App app;
	
	private String proficiency = "5,9,13,17";
	private String defaultCharRoll = "3d6";
	
	private ListModel classList, raceList;
	
	
	public Settings(App app){
		this.app = app;
	}
	
	public void saveSettings(){
		File saveDir = new File(app.workDir + App.fSep + "Campaigns" + App.fSep + app.campaign.getName() + App.fSep + "Settings.txt");
		if(true){
			
			
			try 
			{
				PrintWriter out = new PrintWriter(saveDir);
				
				String classStr = "";
				for(int i = 0; i < app.list_Settings_Class.getModel().getSize(); i++){
					classStr += app.list_Settings_Class.getModel().getElementAt(i) + ":;";
				}
				
				String raceStr = "";
				for(int i = 0; i < app.list_Settings_Race.getModel().getSize(); i++){
					raceStr += app.list_Settings_Race.getModel().getElementAt(i) + ":;";
				}
				
				out.println("Class List:: " + classStr);
				out.println("Race List:: " + raceStr);
				
				
				out.close(); //finished writing
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void loadSettings(String campaign){
		File saveDir = new File(app.workDir + App.fSep + "Campaigns" + App.fSep + campaign + App.fSep + "Settings.txt");
		
		if(saveDir.exists())
		{
			try (BufferedReader br = new BufferedReader(new FileReader(saveDir));)
			{
				String line;
				String[] xspl;
				
				line = br.readLine().split("Class List:: ")[1]; //Char Class
				SortedListModel model = new SortedListModel();
				xspl = line.split(":;");
				for(int i = 0; i<xspl.length; i++){
					model.add(xspl[i]);
				}
				app.list_Settings_Class.setModel(model);
				
				line = br.readLine().split("Race List:: ")[1]; //Char Race
				model = new SortedListModel();
				xspl = line.split(":;");
				for(int i = 0; i<xspl.length; i++){
					model.add(xspl[i]);
				}
				app.list_Settings_Race.setModel(model);
				
				
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
