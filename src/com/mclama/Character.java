package com.mclama;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Character {
	
	private String name      = "";
	private int str, dex, con, intel, wis, cha;
	
	private String town      = "";
	private String job       = "";
	private String race      = "";
	private String charClass = "";
	private int level        = 0;
	private String alive     = "true";
	
	private String reserved1   = "";
	private String reserved2   = "";
	private String reserved3   = "";
	private String reserved4   = "";
	private String reserved5   = "";
	private String reserved6   = "";
	
	private String spellsKnown = "";
	private String spellSlots  = "";
	
	private String notes       = "";
	private App app;
	
	public Character(App app){
		this.app = app;
		this.name = "null";
	}

	
	public Character(App app, String name){ //load character
		this.app = app;
		this.name = name;
		
		File ccDir = new File(App.workDir + App.fSep + "Campaigns" + App.fSep + App.campaign.getName() + App.fSep + "Characters" + App.fSep);
		
		if(ccDir.exists())
		{
			File character = new File(ccDir + App.fSep + name + ".txt");
			
			try (BufferedReader br = new BufferedReader(new FileReader(new File(character+"")));)
			{
				String line;
				String[] xspl;
				
				line = br.readLine(); //name
				str = Integer.parseInt(br.readLine().split("Strength:: ")[1]);
				dex = Integer.parseInt(br.readLine().split("Dexterity:: ")[1]);
				con = Integer.parseInt(br.readLine().split("Constitution:: ")[1]);
				intel = Integer.parseInt(br.readLine().split("Intelligence:: ")[1]);
				wis = Integer.parseInt(br.readLine().split("Wisdom:: ")[1]);
				cha = Integer.parseInt(br.readLine().split("Charisma:: ")[1]);
				
				try {
					town = br.readLine().split("Town:: ")[1];
				} catch (Exception e) {
					//e.printStackTrace();
				}
				try {
					job = br.readLine().split("Job:: ")[1];
				} catch (Exception e) {
					//e.printStackTrace();
				}
				try {
					race = br.readLine().split("Race:: ")[1];
				} catch (Exception e) {
					//e.printStackTrace();
				}
				try {
					charClass = br.readLine().split("Class:: ")[1];
				} catch (Exception e) {
					//e.printStackTrace();
				}
				try {
					level = Integer.parseInt(br.readLine().split("Level:: ")[1]);
				} catch (NumberFormatException e) {
					//e.printStackTrace();
				}
				try {
					alive = br.readLine().split("Alive:: ")[1];
				} catch (Exception e) {
					//e.printStackTrace();
				}
				
				try {
					reserved1 = br.readLine().split("Reserved1:: ")[1];
				} catch (Exception e) {
					//e.printStackTrace();
				}
				try {
					reserved2 = br.readLine().split("Reserved2:: ")[1];
				} catch (Exception e) {
					//e.printStackTrace();
				}
				try {
					reserved3 = br.readLine().split("Reserved3:: ")[1];
				} catch (Exception e) {
					//e.printStackTrace();
				}
				try {
					reserved4 = br.readLine().split("Reserved4:: ")[1];
				} catch (Exception e) {
					//e.printStackTrace();
				}
				try {
					reserved5 = br.readLine().split("Reserved5:: ")[1];
				} catch (Exception e) {
					//e.printStackTrace();
				}
				try {
					reserved6 = br.readLine().split("Reserved6:: ")[1];
				} catch (Exception e) {
					//e.printStackTrace();
				}
				
				try {
					spellsKnown = br.readLine().split("Spells Known:: ")[1];
					spellsKnown = spellsKnown.replace("\\n","\n");
				} catch (Exception e) {
					//e.printStackTrace();
				}
				try {
					spellSlots = br.readLine().split("Spell Slots:: ")[1];
				} catch (Exception e) {
					//e.printStackTrace();
				}
				
				try {
					notes = br.readLine().split("Notes:: ")[1];
					//notes = notes.replace("\\n",System.getProperty("line.separator"));
					notes = notes.replace("\\n","\n");
				} catch (Exception e) {
					//e.printStackTrace();
				}
				
				br.close();
				
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	
	
	public Character(App app, String name, int str, int dex, int con, int intel, int wis, int cha){
		this.app = app;
		this.name = name;
		this.str = str;
		this.dex = dex;
		this.con = con;
		this.intel = intel;
		this.wis = wis;
		this.cha = cha;
		
		//now lets save new character.
		saveCharacter();
	}



	public void saveCharacter() {
		if(name != null && name != "")
		{
			if(app != null)
			{
				if(app.isNameCurrentlyUsed(name) == false){
					app.addCharToList(name);
				}
			}
			File ccDir = new File(App.workDir + App.fSep + "Campaigns" + App.fSep + App.campaign.getName() + App.fSep + "Characters" + App.fSep);
			// Campaigs/Characters directory
			if(ccDir.exists())
			{
				File character = new File(ccDir + App.fSep + name + ".txt");
				try 
				{
					PrintWriter out = new PrintWriter(character);
					
					out.println("Name:: " + name);
					
					out.println("Strength:: " + str);
					out.println("Dexterity:: " + dex);
					out.println("Constitution:: " + con);
					out.println("Intelligence:: " + intel);
					out.println("Wisdom:: " + wis);
					out.println("Charisma:: " + cha);
					
					out.println("Town:: " + town);
					out.println("Job:: " + job);
					out.println("Race:: " + race);
					out.println("Class:: " + charClass);
					out.println("Level:: " + level);
					out.println("Alive:: " + alive);
					
					out.println("Reserved1:: " + reserved1);
					out.println("Reserved2:: " + reserved2);
					out.println("Reserved3:: " + reserved3);
					out.println("Reserved4:: " + reserved4);
					out.println("Reserved5:: " + reserved5);
					out.println("Reserved6:: " + reserved6);
					
					out.println("Spells Known:: " + spellsKnown.replace("\r","").replace("\n", "\\n"));
					out.println("Spell Slots:: " + spellSlots);
					out.println("Notes:: " + notes.replace("\n", "\\n").replace("\r",""));
					
					
					out.close(); //finished writing
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
				
			} 
			else ccDir.mkdirs(); //if cannot find, create folder
		}
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStr() {
		return str;
	}

	public void setStr(int str) {
		this.str = str;
	}

	public int getDex() {
		return dex;
	}

	public void setDex(int dex) {
		this.dex = dex;
	}

	public int getCon() {
		return con;
	}

	public void setCon(int con) {
		this.con = con;
	}

	public int getIntel() {
		return intel;
	}

	public void setIntel(int intel) {
		this.intel = intel;
	}

	public int getWis() {
		return wis;
	}

	public void setWis(int wis) {
		this.wis = wis;
	}

	public int getCha() {
		return cha;
	}

	public void setCha(int cha) {
		this.cha = cha;
	}


	public String getTown() {
		return town;
	}



	public void setTown(String town) {
		this.town = town;
	}



	public String getJob() {
		return job;
	}



	public void setJob(String job) {
		this.job = job;
	}



	public String getRace() {
		return race;
	}



	public void setRace(String race) {
		this.race = race;
	}



	public String getCharClass() {
		return charClass;
	}



	public void setCharClass(String charClass) {
		this.charClass = charClass;
	}



	public int getLevel() {
		return level;
	}



	public void setLevel(int level) {
		this.level = level;
	}



	public String getAlive() {
		return alive;
	}



	public void setAlive(String alive) {
		this.alive = alive;
	}



	public String getReserved1() {
		return reserved1;
	}



	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}



	public String getReserved2() {
		return reserved2;
	}



	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}



	public String getReserved3() {
		return reserved3;
	}



	public void setReserved3(String reserved3) {
		this.reserved3 = reserved3;
	}



	public String getReserved4() {
		return reserved4;
	}



	public void setReserved4(String reserved4) {
		this.reserved4 = reserved4;
	}



	public String getReserved5() {
		return reserved5;
	}



	public void setReserved5(String reserved5) {
		this.reserved5 = reserved5;
	}



	public String getReserved6() {
		return reserved6;
	}



	public void setReserved6(String reserved6) {
		this.reserved6 = reserved6;
	}



	public String getSpellsKnown() {
		return spellsKnown;
	}



	public void setSpellsKnown(String spellsKnown) {
		this.spellsKnown = spellsKnown;
	}



	public String getSpellSlots() {
		return spellSlots;
	}



	public void setSpellSlots(String spellSlots) {
		this.spellSlots = spellSlots;
	}



	public String getNotes() {
		return notes;
	}



	public void setNotes(String notes) {
		this.notes = notes;
	}
	
}
