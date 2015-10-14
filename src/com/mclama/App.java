package com.mclama;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.CodeSource;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;
import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;

import java.awt.event.MouseAdapter;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ButtonGroup;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JFormattedTextField;

import java.awt.Font;

import javax.swing.JSeparator;

import java.awt.Color;

import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.event.MenuKeyListener;
import javax.swing.event.MenuKeyEvent;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class App extends JFrame {
	
	public static int buildn = 1; //build number, I will usually increase this when something needs to know when it can work.
	public static String version = "v1.0.0";
	
	private static long PRESSED_ENTER=0;
	private static String lastKnownCharacterName = "";
	private static String lastKnownCampaignName = "";
	private static String lastKnownNotes= "";
	private static String lastKnownSpells= "";
	
	public static String lSep = System.getProperty("line.separator");
	public static String fSep = System.getProperty("file.separator");

	private JPanel contentPane;
	private JTextField textField;
	public static String workDir;
	public static Campaign campaign = null;
	public static Character character;
	public static Character townChar = null;
	public static Encounter encounter;
	public ArrayList<String> charList;
	public ArrayList<String> townList;
	private Random numGen = new Random();
	private JTextField txtStatstr;
	private JTextField txtStatDex;
	private JTextField txtStatCon;
	private JTextField txtStatInt;
	private JTextField txtStatWis;
	private JTextField txtStatCha;
	private JTextField txtChardiceroll;
	private JTabbedPane tabbedPane;
	private JPanel panel_Misc;
	private JPanel panel_Character;
	private JScrollPane scrollPane;
	private JTextField textField_Char_Name;
	private JTextField textField_Char_Str;
	private JTextField textField_Char_Dex;
	private JTextField textField_Char_Con;
	private JTextField textField_Char_Int;
	private JTextField textField_Char_Wis;
	private JTextField textField_Char_Cha;
	private JTextField textField_Char_DiceRoll;
	private JTextField textField_Char_Filter;
	private JTextField textField_Char_Town;
	private JTextField textField_Char_Job;
	private JTextField textField_Char_Race;
	private JTextField textField_Char_Class;
	private JFormattedTextField textField_Char_Level;
	private JTextField textField_Char_StrMod;
	private JTextField textField_Char_DexMod;
	private JTextField textField_Char_ConMod;
	private JTextField textField_Char_IntMod;
	private JTextField textField_Char_WisMod;
	private JTextField textField_Char_ChaMod;
	private JTextField textField_Char_Proficiency;
	private JTextField textField_Settings_Proficiency;
	private JComboBox comboBox_Character_Campaign;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JComboBox comboBox_Towns_Campaign;
	private JComboBox comboBox_Notes_Campaign;
	private JComboBox comboBox_Dungeon_Campaign;
	private JComboBox comboBox_Settings_Campaign;
	private JTextArea textArea_Char_Notes;
	private JCheckBox chckbxAlive;
	private JTextField textField_Settings_CharRoll;
	private JList list_Characters;
	private JTextArea textArea_Char_Spells;
	private JCheckBox chckbxDead;
	private JButton btnRollStats;
	private JLabel lblRollFor;
	private JTextField textField_Settings_Race;
	private JTextField textField_Settings_Class;
	public JList list_Settings_Class;
	public JList list_Settings_Race;
	private JButton btnRollClear;
	public static Settings settings;
	private JTextField textField_Towns_TownFilter;
	private JTextField textField_Towns_CharFilter;
	private JTextField textField_Towns_Name;
	protected Object lastKnownTownName;
	private JList list_Towns_Towns;
	private static Towns town;
	private JTextField textField_Towns_Size;
	private JTextArea textArea_Towns_Notes;
	private JList list_Towns_Char;
	private JTextArea textArea_Towns_CharNotes;
	private JButton btnTownClear;
	private JTextArea textArea_Notes_Notes;
	private JMenuItem mntmTownsCreateCharacter;
	private JPopupMenu popupMenu_Towns;
	private JTable table_Battle;
	private JTextField textField_Battle_Name;
	private JTextField textField_Battle_Init;
	private JTextField textField_Battle_Health;
	protected JCheckBox chckbxRandomInit;
	private JList list_Battle_Dead;
	protected JCheckBox chckbxKeepname;
	private Component mntmTownsCreateTown;
	private boolean canSaveApp = false;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CodeSource codeSource = App.class.getProtectionDomain().getCodeSource();
			File jarFile = new File(codeSource.getLocation().toURI().getPath());
			String jarDir = jarFile.getParentFile().getPath();
			
			workDir = jarDir;
			//workDir=new File(App.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath();
			System.out.println("Running from " + workDir);
			
			if(!new File(workDir + fSep +"Name List.txt").exists()){
				System.out.println("Export: " + ExportResource("/Name List.txt"));
			} else System.out.println("Found Name List.txt...");
			
		} catch (URISyntaxException e1) {
			System.out.println("Failed to get workDir");
		} catch (Exception e) {
			System.out.println("Failed to copy name list over.");
			//e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App frame = new App();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "serial", "serial", "serial" })
	public App() {
		character = new Character(this);
		settings = new Settings(this);
		town = new Towns(this);
		encounter = new Encounter(this);
		setResizable(false);
		setTitle("DM my DnD");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 458);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.LIGHT_GRAY);
		tabbedPane.setBounds(0, 0, 973, 689);
		contentPane.add(tabbedPane);
		
		panel_Character = new JPanel();
		panel_Character.setBackground(Color.LIGHT_GRAY);
		panel_Character.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				globalMousePressed(e);
			}
		});
		tabbedPane.addTab("Characters", null, panel_Character, null);
		panel_Character.setLayout(null);
		
		JLabel lblCampagin = new JLabel("For Campaign:");
		lblCampagin.setBounds(0, 0, 81, 16);
		panel_Character.add(lblCampagin);
		
		comboBox_Character_Campaign = new JComboBox();
		comboBox_Character_Campaign.setToolTipText("Click enter after entering a new name to create a new campaign.");
		comboBox_Character_Campaign.setEditable(true);
		comboBox_Character_Campaign.setBounds(0, 16, 196, 25);
		panel_Character.add(comboBox_Character_Campaign);
		
		comboBox_Character_Campaign.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.ACTION_PERFORMED==1001){ //pressed enter
					if(!comboBox_Character_Campaign.getSelectedItem().toString().equals(lastKnownCampaignName)){
						String campaignName = comboBox_Character_Campaign.getSelectedItem().toString();
						campaignChange(campaignName);
					}
				}
			}
		});
		
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 199, 521, 196);
		panel_Character.add(scrollPane);
		
		textArea_Char_Notes = new JTextArea();
		textArea_Char_Notes.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!textField_Char_Name.getText().equals("")){
					character.setNotes(textArea_Char_Notes.getText());
					
					if(!character.getName().equals(textField_Char_Name.getText())){
						character.setName(textField_Char_Name.getText());
					}
					
					character.saveCharacter();
					
					if(townChar != null)
					{
						if(list_Characters.getSelectedValue().toString().equals(townChar.getName()))
						{
							textArea_Towns_CharNotes.setText(textArea_Char_Notes.getText());
						}
					}
				}
			}
		});
		scrollPane.setViewportView(textArea_Char_Notes);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(545, 199, 144, 196);
		panel_Character.add(scrollPane_1);
		
		textArea_Char_Spells = new JTextArea();
		textArea_Char_Spells.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!textField_Char_Name.getText().equals("")){
					character.setSpellsKnown(textArea_Char_Spells.getText());

					if(!character.getName().equals(textField_Char_Name.getText())){
						character.setName(textField_Char_Name.getText());
					}
					
					character.saveCharacter();
				}
			}
		});
		textArea_Char_Spells.setText("Cantrips\nLevel 1 - (0)\nLevel 2 - (0)\nLevel 3 - (0)\nLevel 4 - (0)\nLevel 5 - (0)\nLevel 6 - (0)\nLevel 7 - (0)\nLevel 8 - (0)\nLevel 9 - (0)");
		scrollPane_1.setViewportView(textArea_Char_Spells);
		
		JLabel lblNotes = new JLabel("Notes:");
		lblNotes.setBounds(12, 184, 55, 16);
		panel_Character.add(lblNotes);
		
		JLabel lblSpells = new JLabel("Spells and Spell Slots");
		lblSpells.setBounds(545, 184, 144, 16);
		panel_Character.add(lblSpells);
		
		textField_Char_Name = new JTextField();
		textField_Char_Name.setBounds(12, 62, 114, 20);
		panel_Character.add(textField_Char_Name);
		textField_Char_Name.setColumns(10);
		textField_Char_Name.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.ACTION_PERFORMED == 1001){ //pressed enter
					if(!textField_Char_Name.getText().equals(lastKnownCharacterName)){
						System.out.println("NAME CHANGE: " + lastKnownCharacterName + " to " + textField_Char_Name.getText());
						lastKnownCharacterName = textField_Char_Name.getText();
					}
				}
			}
		});
		
		textField_Char_Name.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if(!textField_Char_Name.getText().equals(lastKnownCharacterName)){
					System.out.println("NAME CHANGE: " + lastKnownCharacterName + " to " + textField_Char_Name.getText());
					lastKnownCharacterName = textField_Char_Name.getText();
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});
		
		JLabel lblCharacterName = new JLabel("Name");
		lblCharacterName.setBounds(12, 47, 33, 16);
		panel_Character.add(lblCharacterName);
		
		JLabel label = new JLabel("Strength");
		label.setBounds(12, 83, 55, 16);
		panel_Character.add(label);
		
		textField_Char_Str = new JTextField();
		textField_Char_Str.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!textField_Char_Name.getText().equals("")){
					if(textField_Char_Str.equals("") && textField_Char_Str != null){
						character.setStr(Integer.parseInt(textField_Char_Str.getText()));
					}
					else character.setStr(0);

					if(!character.getName().equals(textField_Char_Name.getText())){
						character.setName(textField_Char_Name.getText());
					}
					
					character.saveCharacter();
				}
			}
		});
		textField_Char_Str.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Char_Str.setColumns(10);
		textField_Char_Str.setBounds(12, 98, 55, 20);
		panel_Character.add(textField_Char_Str);
		
		JLabel label_1 = new JLabel("Dexterity");
		label_1.setBounds(12, 117, 55, 16);
		panel_Character.add(label_1);
		
		textField_Char_Dex = new JTextField();
		textField_Char_Dex.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!textField_Char_Name.getText().equals("")){
					if(textField_Char_Dex.equals("")){
						character.setDex(Integer.parseInt(textField_Char_Dex.getText()));
					}
					else character.setDex(0);

					if(!character.getName().equals(textField_Char_Name.getText())){
						character.setName(textField_Char_Name.getText());
					}
					
					character.saveCharacter();
				}
			}
		});
		textField_Char_Dex.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Char_Dex.setColumns(10);
		textField_Char_Dex.setBounds(12, 132, 55, 20);
		panel_Character.add(textField_Char_Dex);
		
		JLabel label_2 = new JLabel("Constitution");
		label_2.setBounds(12, 152, 68, 16);
		panel_Character.add(label_2);
		
		textField_Char_Con = new JTextField();
		textField_Char_Con.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!textField_Char_Name.getText().equals("")){
					if(textField_Char_Con.equals("")){
						character.setCon(Integer.parseInt(textField_Char_Con.getText()));
					}
					else character.setCon(0);

					if(!character.getName().equals(textField_Char_Name.getText())){
						character.setName(textField_Char_Name.getText());
					}
					
					character.saveCharacter();
				}
			}
		});
		textField_Char_Con.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Char_Con.setColumns(10);
		textField_Char_Con.setBounds(12, 167, 55, 20);
		panel_Character.add(textField_Char_Con);
		
		JLabel label_3 = new JLabel("Intelligence");
		label_3.setBounds(138, 85, 68, 16);
		panel_Character.add(label_3);
		
		textField_Char_Int = new JTextField();
		textField_Char_Int.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!textField_Char_Name.getText().equals("")){
					if(textField_Char_Int.equals("")){
						character.setIntel(Integer.parseInt(textField_Char_Int.getText()));
					}
					else character.setIntel(0);

					if(!character.getName().equals(textField_Char_Name.getText())){
						character.setName(textField_Char_Name.getText());
					}
					
					character.saveCharacter();
				}
			}
		});
		textField_Char_Int.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Char_Int.setColumns(10);
		textField_Char_Int.setBounds(138, 99, 55, 20);
		panel_Character.add(textField_Char_Int);
		
		JLabel label_4 = new JLabel("Wisdom");
		label_4.setBounds(138, 119, 55, 16);
		panel_Character.add(label_4);
		
		textField_Char_Wis = new JTextField();
		textField_Char_Wis.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!textField_Char_Name.getText().equals("")){
					if(textField_Char_Wis.equals("")){
						character.setWis(Integer.parseInt(textField_Char_Wis.getText()));
					}
					else character.setWis(0);

					if(!character.getName().equals(textField_Char_Name.getText())){
						character.setName(textField_Char_Name.getText());
					}
					
					character.saveCharacter();
				}
			}
		});
		textField_Char_Wis.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Char_Wis.setColumns(10);
		textField_Char_Wis.setBounds(138, 133, 55, 20);
		panel_Character.add(textField_Char_Wis);
		
		JLabel label_5 = new JLabel("Charisma");
		label_5.setBounds(138, 153, 55, 16);
		panel_Character.add(label_5);
		
		textField_Char_Cha = new JTextField();
		textField_Char_Cha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!textField_Char_Name.getText().equals("")){
					if(textField_Char_Cha.equals("")){
						character.setCha(Integer.parseInt(textField_Char_Cha.getText()));
					}
					else character.setCha(0);

					if(!character.getName().equals(textField_Char_Name.getText())){
						character.setName(textField_Char_Name.getText());
					}
					
					character.saveCharacter();
				}
			}
		});
		textField_Char_Cha.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Char_Cha.setColumns(10);
		textField_Char_Cha.setBounds(138, 167, 55, 20);
		panel_Character.add(textField_Char_Cha);
		
		textField_Char_DiceRoll = new JTextField();
		textField_Char_DiceRoll.setText("3d6");
		textField_Char_DiceRoll.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Char_DiceRoll.setColumns(10);
		textField_Char_DiceRoll.setBounds(198, 21, 93, 20);
		panel_Character.add(textField_Char_DiceRoll);
		
		JLabel label_6 = new JLabel("Dice Roll");
		label_6.setBounds(204, 7, 55, 16);
		panel_Character.add(label_6);
		
		JButton btnRollCharacter = new JButton("Character");
		btnRollCharacter.setToolTipText("Rolls a new character with a random name, and random stats.");
		btnRollCharacter.setFont(new Font("Dialog", Font.BOLD, 8));
		btnRollCharacter.setBounds(298, 23, 70, 20);
		panel_Character.add(btnRollCharacter);
		btnRollCharacter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createNewCharacter();
			}
		});
		
		textField_Char_Filter = new JTextField();
		textField_Char_Filter.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				updateCharacterList();
			}
		});
		textField_Char_Filter.setBounds(545, 14, 144, 20);
		panel_Character.add(textField_Char_Filter);
		textField_Char_Filter.setColumns(10);
		
		JLabel lblSearchCharacter = new JLabel("Filter Character");
		lblSearchCharacter.setBounds(545, 0, 88, 16);
		panel_Character.add(lblSearchCharacter);
		
		textField_Char_Town = new JTextField();
		textField_Char_Town.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!textField_Char_Name.getText().equals("")){
					character.setTown(textField_Char_Town.getText());

					if(!character.getName().equals(textField_Char_Name.getText())){
						character.setName(textField_Char_Name.getText());
					}
					
					character.saveCharacter();
				}
			}
		});
		textField_Char_Town.setBounds(138, 62, 114, 20);
		panel_Character.add(textField_Char_Town);
		textField_Char_Town.setColumns(10);
		
		JLabel lblFromTown = new JLabel("From Town");
		lblFromTown.setBounds(144, 47, 68, 16);
		panel_Character.add(lblFromTown);
		
		textField_Char_Job = new JTextField();
		textField_Char_Job.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!textField_Char_Name.getText().equals("")){
					character.setJob(textField_Char_Job.getText());

					if(!character.getName().equals(textField_Char_Name.getText())){
						character.setName(textField_Char_Name.getText());
					}
					
					character.saveCharacter();
				}
			}
		});
		textField_Char_Job.setBounds(264, 62, 114, 20);
		panel_Character.add(textField_Char_Job);
		textField_Char_Job.setColumns(10);
		
		JLabel lblJob = new JLabel("Job");
		lblJob.setBounds(270, 47, 21, 16);
		panel_Character.add(lblJob);
		
		textField_Char_Race = new JTextField();
		textField_Char_Race.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!textField_Char_Name.getText().equals("")){
					character.setRace(textField_Char_Race.getText());

					if(!character.getName().equals(textField_Char_Name.getText())){
						character.setName(textField_Char_Name.getText());
					}
					
					character.saveCharacter();
				}
			}
		});
		textField_Char_Race.setBounds(264, 98, 114, 20);
		panel_Character.add(textField_Char_Race);
		textField_Char_Race.setColumns(10);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(545, 35, 144, 149);
		panel_Character.add(scrollPane_2);
		
		list_Characters = new JList();
		list_Characters.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				//if(e.KEY_RELEASED == e.VK_UP || e.KEY_RELEASED == e.VK_DOWN){
					loadCharacter(list_Characters.getSelectedValue().toString());
				//}
			}
		});
		list_Characters.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				loadCharacter(list_Characters.getSelectedValue().toString());
			}
		});
		list_Characters.setModel(new AbstractListModel() {
			String[] values = new String[] {"Create ", "a ", "Campaign ", "first"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list_Characters.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_2.setViewportView(list_Characters);
		
		
		
		
		JLabel lblRace = new JLabel("Race");
		lblRace.setBounds(270, 83, 55, 16);
		panel_Character.add(lblRace);
		
		textField_Char_Class = new JTextField();
		textField_Char_Class.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!textField_Char_Name.getText().equals("")){
					character.setCharClass(textField_Char_Class.getText());

					if(!character.getName().equals(textField_Char_Name.getText())){
						character.setName(textField_Char_Name.getText());
					}
					
					character.saveCharacter();
				}
			}
		});
		textField_Char_Class.setBounds(264, 132, 114, 20);
		panel_Character.add(textField_Char_Class);
		textField_Char_Class.setColumns(10);
		
		JLabel lblClass = new JLabel("Class");
		lblClass.setBounds(270, 117, 55, 16);
		panel_Character.add(lblClass);
		
		chckbxAlive = new JCheckBox("Alive");
		chckbxAlive.setBackground(Color.LIGHT_GRAY);
		chckbxAlive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!textField_Char_Name.getText().equals("")){
					character.setAlive("true");

					if(!character.getName().equals(textField_Char_Name.getText())){
						character.setName(textField_Char_Name.getText());
					}
					
					character.saveCharacter();
				}
			}
		});
		buttonGroup.add(chckbxAlive);
		chckbxAlive.setSelected(true);
		chckbxAlive.setBounds(385, 58, 52, 24);
		panel_Character.add(chckbxAlive);
		
//		NumberFormat format = NumberFormat.getInstance();
//	    NumberFormatter formatter = new NumberFormatter(format);
//	    formatter.setValueClass(Integer.class);
//	    formatter.setAllowsInvalid(false);
//	    formatter.setMaximum(Integer.MAX_VALUE);
//	    formatter.setCommitsOnValidEdit(true);
//	    
//	    textField_Char_Level = new JFormattedTextField(formatter);
		textField_Char_Level = new JFormattedTextField();
		textField_Char_Level.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!textField_Char_Name.getText().equals("")){
					try {
						character.setLevel(Integer.parseInt(textField_Char_Level.getText()));

						if(!character.getName().equals(textField_Char_Name.getText())){
							character.setName(textField_Char_Name.getText());
						}
						
						character.saveCharacter();
					} catch (NumberFormatException e1) {
						System.out.println("Tried to save character level with non-digits.");
					}
					
				}
			}
		});

		
		textField_Char_Level.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Char_Level.setBounds(264, 167, 55, 20);
		panel_Character.add(textField_Char_Level);
		textField_Char_Level.setColumns(10);
		
		JLabel lblLevel = new JLabel("Level");
		lblLevel.setBounds(270, 152, 55, 16);
		panel_Character.add(lblLevel);
		
		textField_Char_StrMod = new JTextField();
		textField_Char_StrMod.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Char_StrMod.setBounds(79, 98, 46, 20);
		panel_Character.add(textField_Char_StrMod);
		textField_Char_StrMod.setColumns(10);
		
		textField_Char_DexMod = new JTextField();
		textField_Char_DexMod.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Char_DexMod.setColumns(10);
		textField_Char_DexMod.setBounds(79, 132, 46, 20);
		panel_Character.add(textField_Char_DexMod);
		
		textField_Char_ConMod = new JTextField();
		textField_Char_ConMod.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Char_ConMod.setColumns(10);
		textField_Char_ConMod.setBounds(80, 167, 46, 20);
		panel_Character.add(textField_Char_ConMod);
		
		textField_Char_IntMod = new JTextField();
		textField_Char_IntMod.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Char_IntMod.setColumns(10);
		textField_Char_IntMod.setBounds(206, 98, 46, 20);
		panel_Character.add(textField_Char_IntMod);
		
		textField_Char_WisMod = new JTextField();
		textField_Char_WisMod.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Char_WisMod.setColumns(10);
		textField_Char_WisMod.setBounds(205, 132, 46, 20);
		panel_Character.add(textField_Char_WisMod);
		
		textField_Char_ChaMod = new JTextField();
		textField_Char_ChaMod.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Char_ChaMod.setColumns(10);
		textField_Char_ChaMod.setBounds(205, 167, 46, 20);
		panel_Character.add(textField_Char_ChaMod);
		
		textField_Char_Proficiency = new JTextField();
		textField_Char_Proficiency.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Char_Proficiency.setColumns(10);
		textField_Char_Proficiency.setBounds(332, 167, 46, 20);
		panel_Character.add(textField_Char_Proficiency);
		
		chckbxDead = new JCheckBox("Dead");
		chckbxDead.setBackground(Color.LIGHT_GRAY);
		chckbxDead.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!textField_Char_Name.getText().equals("")){
					character.setAlive("false");

					if(!character.getName().equals(textField_Char_Name.getText())){
						character.setName(textField_Char_Name.getText());
					}
					
					character.saveCharacter();
				}
			}
		});
		buttonGroup.add(chckbxDead);
		chckbxDead.setBounds(457, 58, 54, 24);
		panel_Character.add(chckbxDead);
		
		JButton btnRollRace = new JButton("Race");
		btnRollRace.setToolTipText("Rolls a random race based off the settings race list.");
		btnRollRace.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				Random gen = new Random();
				int choose = gen.nextInt(list_Settings_Race.getModel().getSize());
				String race =(String) list_Settings_Race.getModel().getElementAt(choose);
				textField_Char_Race.setText(race);
				character.setRace(race);
				character.saveCharacter();
			}
		});
		btnRollRace.setFont(new Font("Dialog", Font.BOLD, 8));
		btnRollRace.setBounds(372, 37, 52, 20);
		panel_Character.add(btnRollRace);
		
		JButton btnRollClass = new JButton("Class");
		btnRollClass.setToolTipText("Rolls a random class based off the settings class list.");
		btnRollClass.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				Random gen = new Random();
				int choose = gen.nextInt(list_Settings_Class.getModel().getSize());
				String classi =(String) list_Settings_Class.getModel().getElementAt(choose);
				textField_Char_Class.setText(classi);
				character.setCharClass(classi);
				character.saveCharacter();
			}
		});
		btnRollClass.setFont(new Font("Dialog", Font.BOLD, 8));
		btnRollClass.setBounds(425, 37, 54, 20);
		panel_Character.add(btnRollClass);
		
		btnRollStats = new JButton("Stats");
		btnRollStats.setToolTipText("Rolls random stats using the Dice roll field for your current character.");
		btnRollStats.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				rollNewCharacterStats();
				
				try {
					character.setStr(Integer.parseInt(textField_Char_Str.getText()));
					character.setDex(Integer.parseInt(textField_Char_Dex.getText()));
					character.setCon(Integer.parseInt(textField_Char_Con.getText()));
					character.setIntel(Integer.parseInt(textField_Char_Int.getText()));
					character.setWis(Integer.parseInt(textField_Char_Wis.getText()));
					character.setCha(Integer.parseInt(textField_Char_Cha.getText()));
				} catch (NumberFormatException e1) {
					System.out.println("ERROR: Failed to save new character stats");
				}
				
				character.saveCharacter();
			}
		});
		btnRollStats.setFont(new Font("Dialog", Font.BOLD, 8));
		btnRollStats.setBounds(480, 37, 53, 20);
		panel_Character.add(btnRollStats);
		
		lblRollFor = new JLabel("Roll for:");
		lblRollFor.setBounds(297, 3, 55, 16);
		panel_Character.add(lblRollFor);
		
		JButton btnRollRename = new JButton("Rename");
		btnRollRename.setToolTipText("Enter in a new name, and click to rename.");
		btnRollRename.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				String prevName = character.getName();
				String newName = textField_Char_Name.getText();
				
				if(!prevName.equals(newName))
				{
					if(!new File(workDir + fSep +"Campaigns"+ fSep + campaign.getName() + fSep +"Characters" + fSep + newName + ".txt").exists())
					{//if new character name doesn't already exist
						File file = new File(workDir + fSep +"Campaigns"+ fSep + campaign.getName() + fSep +"Characters" + fSep + prevName + ".txt");
						
						character.setName(newName);
						//textField_Char_Name.setText(newName);
						removeCharFromList(prevName);
						addCharToList(newName);
						
						character.saveCharacter();
						file.delete();
						
						updateCharacterList();
					}
				}
			}
		});
		btnRollRename.setHorizontalAlignment(SwingConstants.RIGHT);
		btnRollRename.setFont(new Font("Dialog", Font.BOLD, 8));
		btnRollRename.setBounds(61, 42, 64, 19);
		panel_Character.add(btnRollRename);
		
		JButton btnRollName = new JButton("Name");
		btnRollName.setToolTipText("Rolls a new name for the current character.");
		btnRollName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				String prevName = textField_Char_Name.getText();
				String newName = getNewCharacterName();
				
				//File here = new File("");
				File file = new File(workDir + fSep + "Campaigns" + fSep + campaign.getName() + fSep + "Characters" + fSep + prevName + ".txt");
				
				character.setName(newName);
				textField_Char_Name.setText(newName);
				removeCharFromList(prevName);
				addCharToList(newName);
				
				character.saveCharacter();
				file.delete();
				
			}
		});
		btnRollName.setFont(new Font("Dialog", Font.BOLD, 8));
		btnRollName.setBounds(372, 16, 55, 20);
		panel_Character.add(btnRollName);
		
		btnRollClear = new JButton("Clear");
		btnRollClear.setToolTipText("Clears all of the current info.");
		btnRollClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				textField_Char_Name.setText("");
				
				textField_Char_Str.setText("");
				textField_Char_Dex.setText("");
				textField_Char_Con.setText("");
				textField_Char_Int.setText("");
				textField_Char_Wis.setText("");
				textField_Char_Cha.setText("");
				
				textField_Char_StrMod.setText("");
				textField_Char_DexMod.setText("");
				textField_Char_ConMod.setText("");
				textField_Char_IntMod.setText("");
				textField_Char_WisMod.setText("");
				textField_Char_ChaMod.setText("");
				
				textField_Char_Town.setText("");
				textField_Char_Job.setText("");
				textField_Char_Race.setText("");
				textField_Char_Class.setText("");
				textField_Char_Level.setText("");
				
				textArea_Char_Notes.setText("");
				textArea_Char_Spells.setText("Cantrips\nLevel 1 - (0)\nLevel 2 - (0)\nLevel 3 - (0)\nLevel 4 - (0)\nLevel 5 - (0)\nLevel 6 - (0)\nLevel 7 - (0)\nLevel 8 - (0)\nLevel 9 - (0)");
				
				
				chckbxAlive.setSelected(true);
				chckbxDead.setSelected(false);
			}
		});
		btnRollClear.setFont(new Font("Dialog", Font.BOLD, 8));
		btnRollClear.setBounds(480, 15, 53, 20);
		panel_Character.add(btnRollClear);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(131, 98, 9, 88);
		panel_Character.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(257, 98, 9, 88);
		panel_Character.add(separator_1);
		
		JPanel panel_Towns = new JPanel();
		panel_Towns.setBackground(Color.LIGHT_GRAY);
		panel_Towns.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				globalMousePressed(e);
			}
		});
		tabbedPane.addTab("Towns", null, panel_Towns, null);
		panel_Towns.setLayout(null);
		
		JLabel label_7 = new JLabel("For Campaign:");
		label_7.setBounds(0, 0, 99, 16);
		panel_Towns.add(label_7);
		
		comboBox_Towns_Campaign = new JComboBox();
		comboBox_Towns_Campaign.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.ACTION_PERFORMED==1001){ //pressed enter
					if(!comboBox_Towns_Campaign.getSelectedItem().toString().equals(lastKnownCampaignName)){
						String campaignName = comboBox_Towns_Campaign.getSelectedItem().toString();
						campaignChange(campaignName);
					}
				}
			}
		});
		comboBox_Towns_Campaign.setEditable(true);
		comboBox_Towns_Campaign.setBounds(0, 16, 196, 25);
		panel_Towns.add(comboBox_Towns_Campaign);
		
		JLabel lblCharacterNotes = new JLabel("Character Notes:");
		lblCharacterNotes.setBounds(11, 256, 96, 16);
		panel_Towns.add(lblCharacterNotes);
		
		JScrollPane scrollPane_6 = new JScrollPane();
		scrollPane_6.setBounds(12, 272, 518, 121);
		panel_Towns.add(scrollPane_6);
		
		textArea_Towns_CharNotes = new JTextArea();
		textArea_Towns_CharNotes.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(townChar != null){
					townChar.setNotes(textArea_Towns_CharNotes.getText());
					townChar.saveCharacter();
					
					if(list_Characters.getSelectedValue().toString().equals(townChar.getName()))
					{
						textArea_Char_Notes.setText(textArea_Towns_CharNotes.getText());
					}
				}
			}
		});
		scrollPane_6.setViewportView(textArea_Towns_CharNotes);
		
		JLabel lblTownFilter = new JLabel("Town Filter");
		lblTownFilter.setBounds(545, 0, 88, 16);
		panel_Towns.add(lblTownFilter);
		
		textField_Towns_TownFilter = new JTextField();
		textField_Towns_TownFilter.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				updateTownsList();
			}
		});
		textField_Towns_TownFilter.setColumns(10);
		textField_Towns_TownFilter.setBounds(545, 14, 144, 20);
		panel_Towns.add(textField_Towns_TownFilter);
		
		JScrollPane scrollPane_7 = new JScrollPane();
		scrollPane_7.setBounds(545, 35, 144, 149);
		panel_Towns.add(scrollPane_7);
		
		list_Towns_Towns = new JList();
		list_Towns_Towns.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				String name = list_Towns_Towns.getSelectedValue().toString();
				lastKnownTownName = name;
				loadSelectedTown(name);
			}
		});
		scrollPane_7.setViewportView(list_Towns_Towns);
		list_Towns_Towns.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JLabel lblCharacterFilter = new JLabel("Character Filter:");
		lblCharacterFilter.setBounds(543, 210, 144, 16);
		panel_Towns.add(lblCharacterFilter);
		
		JButton btnRollTownName = new JButton("Roll Name");
		btnRollTownName.setFont(new Font("Dialog", Font.BOLD, 8));
		btnRollTownName.setBounds(406, 38, 72, 20);
		panel_Towns.add(btnRollTownName);
		
		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setBounds(11, 59, 518, 193);
		panel_Towns.add(scrollPane_5);
		
		textArea_Towns_Notes = new JTextArea();
		textArea_Towns_Notes.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!textField_Towns_Name.getText().equals("")){
					town.settNote(textArea_Towns_Notes.getText());
					
					if(!town.getName().equals(textField_Towns_Name.getText())){
						town.setName(textField_Towns_Name.getText());
					}
					
					town.saveTown();
				}
			}
		});
		scrollPane_5.setViewportView(textArea_Towns_Notes);
		
		popupMenu_Towns = new JPopupMenu();
		popupMenu_Towns.setFont(new Font("Dialog", Font.BOLD, 9));
		addPopup(textArea_Towns_Notes, popupMenu_Towns);
		
		mntmTownsCreateCharacter = new JMenuItem("Create Character");
		mntmTownsCreateCharacter.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("Create Character Pop");
				createNewCharacter(textArea_Towns_Notes.getSelectedText());
				tabbedPane.setSelectedComponent(panel_Character);
			}
		});
		
		mntmTownsCreateCharacter.setFont(new Font("Dialog", Font.BOLD, 9));
		popupMenu_Towns.add(mntmTownsCreateCharacter);
		
		JMenuItem mntmCreateTown = new JMenuItem("Create Town");
		mntmCreateTown.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("Create Town Pop");
				
				lastKnownTownName = textArea_Towns_Notes.getSelectedText();
				
				town = new Towns(App.this);
				
				textField_Towns_Name.setText(textArea_Towns_Notes.getSelectedText());
				textField_Towns_TownFilter.setText("");
				textField_Towns_CharFilter.setText("");
				textField_Towns_Size.setText("");
				textArea_Towns_Notes.setText("");
				textArea_Towns_CharNotes.setText("");
				updateTownsCharList("");
				
				town.setName(textArea_Towns_Notes.getSelectedText());
				town.saveTown();
			}
		});
		mntmCreateTown.setFont(new Font("Dialog", Font.BOLD, 10));
		popupMenu_Towns.add(mntmCreateTown);
		
		JLabel lblTownNotes = new JLabel("Town Notes:");
		lblTownNotes.setBounds(10, 43, 70, 16);
		panel_Towns.add(lblTownNotes);
		
		JScrollPane scrollPane_8 = new JScrollPane();
		scrollPane_8.setBounds(543, 249, 144, 149);
		panel_Towns.add(scrollPane_8);
		
		list_Towns_Char = new JList();
		list_Towns_Char.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				townChar = new Character(null, list_Towns_Char.getSelectedValue().toString());
				
				textArea_Towns_CharNotes.setText(townChar.getNotes());
			}
		});
		scrollPane_8.setViewportView(list_Towns_Char);
		list_Towns_Char.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		textField_Towns_CharFilter = new JTextField();
		textField_Towns_CharFilter.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!textField_Towns_Name.getText().equals(""))
				{
					updateTownsCharList(textField_Towns_Name.getText());
				}
			}
		});
		textField_Towns_CharFilter.setColumns(10);
		textField_Towns_CharFilter.setBounds(543, 227, 144, 20);
		panel_Towns.add(textField_Towns_CharFilter);
		
		textField_Towns_Name = new JTextField();
		textField_Towns_Name.setColumns(10);
		textField_Towns_Name.setBounds(280, 4, 114, 20);
		textField_Towns_Name.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.ACTION_PERFORMED == 1001){ //pressed enter
					if(!textField_Towns_Name.getText().equals(lastKnownTownName)){
						System.out.println("NAME CHANGE: " + lastKnownTownName + " to " + textField_Towns_Name.getText());
						lastKnownTownName = textField_Towns_Name.getText();
						
						town = new Towns(App.this);
						
						textField_Towns_TownFilter.setText("");
						textField_Towns_CharFilter.setText("");
						textField_Towns_Size.setText("");
						textArea_Towns_Notes.setText("");
						textArea_Towns_CharNotes.setText("");
						updateTownsCharList("");
						
						town.setName(textField_Towns_Name.getText());
						town.saveTown();
						
					}
				}
			}
		});
		
		panel_Towns.add(textField_Towns_Name);
		
		
		JLabel lblTownName = new JLabel("Town Name");
		lblTownName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTownName.setBounds(201, 6, 68, 16);
		panel_Towns.add(lblTownName);
		
		JButton btnTownRename = new JButton("Rename");
		btnTownRename.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				String prevName = town.getName();
				String newName = textField_Towns_Name.getText();
				
				if(!prevName.equals(newName)) //if not equal, then we have a rename.
				{
					if(!new File(workDir + fSep + "Campaigns" + fSep+ campaign.getName() + fSep + "Towns" + fSep + newName + ".txt").exists())
					{ //if town name isn't already in use....
						File file = new File(workDir + fSep +"Campaigns" + fSep + campaign.getName() + fSep + "Towns" + fSep + prevName + ".txt");
						
						town.setName(newName);
						//textField_Towns_Name.setText(newName);
						removeTownFromList(prevName); //remove old name
						addTownToList(newName);       //add new name
						
						town.saveTown();
						file.delete();
					}
				}
			}
		});
		btnTownRename.setFont(new Font("Dialog", Font.BOLD, 8));
		btnTownRename.setBounds(406, 19, 64, 20);
		panel_Towns.add(btnTownRename);
		
		JLabel lblTownSize = new JLabel("Town Size");
		lblTownSize.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTownSize.setBounds(201, 38, 68, 16);
		panel_Towns.add(lblTownSize);
		
		textField_Towns_Size = new JTextField();
		textField_Towns_Size.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!textField_Towns_Name.getText().equals("")){
					town.setTownSize(textField_Towns_Size.getText());
					town.saveTown();
				}
			}
		});
		textField_Towns_Size.setColumns(10);
		textField_Towns_Size.setBounds(280, 36, 114, 20);
		panel_Towns.add(textField_Towns_Size);
		
		btnTownClear = new JButton("Clear");
		btnTownClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				clearTowns();
			}
		});
		btnTownClear.setFont(new Font("Dialog", Font.BOLD, 8));
		btnTownClear.setBounds(406, 0, 64, 20);
		panel_Towns.add(btnTownClear);
		
		JPanel panel_Notes = new JPanel();
		panel_Notes.setBackground(Color.LIGHT_GRAY);
		panel_Notes.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				globalMousePressed(e);
			}
		});
		tabbedPane.addTab("Notes", null, panel_Notes, null);
		panel_Notes.setLayout(null);
		
		JLabel label_9 = new JLabel("For Campaign:");
		label_9.setBounds(0, 0, 99, 16);
		panel_Notes.add(label_9);
		
		comboBox_Notes_Campaign = new JComboBox();
		comboBox_Notes_Campaign.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.ACTION_PERFORMED==1001){ //pressed enter
					if(!comboBox_Notes_Campaign.getSelectedItem().toString().equals(lastKnownCampaignName)){
						String campaignName = comboBox_Notes_Campaign.getSelectedItem().toString();
						campaignChange(campaignName);
					}
				}
			}
		});
		comboBox_Notes_Campaign.setEditable(true);
		comboBox_Notes_Campaign.setBounds(0, 16, 196, 25);
		panel_Notes.add(comboBox_Notes_Campaign);
		
		JScrollPane scrollPane_9 = new JScrollPane();
		scrollPane_9.setBounds(10, 48, 673, 345);
		panel_Notes.add(scrollPane_9);
		
		textArea_Notes_Notes = new JTextArea();
		textArea_Notes_Notes.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				File file = new File(App.workDir + fSep + "Campaigns" + fSep + campaign.getName() + fSep + "Notes.txt");
				
				try {
					PrintWriter out = new PrintWriter(file);
					out.println(textArea_Notes_Notes.getText().replace("\n", "]]~").replace("\r",""));
					out.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		scrollPane_9.setViewportView(textArea_Notes_Notes);
		
		JPanel panel_Battle = new JPanel();
		panel_Battle.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("Battle", null, panel_Battle, null);
		panel_Battle.setLayout(null);
		
		JScrollPane scrollPane_10 = new JScrollPane();
		scrollPane_10.setBounds(12, 12, 359, 381);
		panel_Battle.add(scrollPane_10);
		
		table_Battle = new JTable();
		table_Battle.setBackground(Color.LIGHT_GRAY);
		table_Battle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		table_Battle.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == e.VK_ENTER){
					encounter.battleDamageApply(table_Battle);
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == e.VK_ENTER){
					//table_Battle.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
					table_Battle.getCellEditor(table_Battle.getSelectedRow(), table_Battle.getSelectedColumn()).stopCellEditing();
				}
			}
		});
		
		scrollPane_10.setViewportView(table_Battle);
		table_Battle.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "Initiative", "Health", "Heal/Damage"
			}
		) {
			Class[] columnTypes = new Class[] {
				Object.class, Integer.class, Integer.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table_Battle.getColumnModel().getColumn(0).setPreferredWidth(166);
		table_Battle.getColumnModel().getColumn(1).setPreferredWidth(59);
		table_Battle.getColumnModel().getColumn(1).setMinWidth(46);
		table_Battle.getColumnModel().getColumn(2).setPreferredWidth(52);
		table_Battle.getColumnModel().getColumn(2).setMinWidth(38);
		table_Battle.getColumnModel().getColumn(3).setPreferredWidth(81);
		table_Battle.getColumnModel().getColumn(3).setMinWidth(79);
		createKeybindings(table_Battle);
		table_Battle.getTableHeader().setFont(new Font("SansSerif", Font.PLAIN, 10));
		
		JLabel lblUseANegative = new JLabel("Use a negative number to heal.");
		lblUseANegative.setBounds(389, 12, 174, 16);
		panel_Battle.add(lblUseANegative);
		
		JLabel lblPressEnterTo = new JLabel("Press enter to apply the change.");
		lblPressEnterTo.setBounds(393, 26, 183, 16);
		panel_Battle.add(lblPressEnterTo);
		
		JButton btn_Battle_Turn = new JButton("+Turn");
		btn_Battle_Turn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				encounter.cycleBattleTurns(table_Battle);
			}
		});
		btn_Battle_Turn.setBounds(383, 367, 98, 26);
		panel_Battle.add(btn_Battle_Turn);
		
		JLabel label_10 = new JLabel("*");
		label_10.setBounds(4, 32, 5, 16);
		panel_Battle.add(label_10);
		
		textField_Battle_Name = new JTextField();
		textField_Battle_Name.setBounds(383, 118, 114, 20);
		panel_Battle.add(textField_Battle_Name);
		textField_Battle_Name.setColumns(10);
		
		textField_Battle_Init = new JTextField();
		textField_Battle_Init.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Battle_Init.setBounds(384, 154, 54, 20);
		panel_Battle.add(textField_Battle_Init);
		textField_Battle_Init.setColumns(10);
		
		textField_Battle_Health = new JTextField();
		textField_Battle_Health.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Battle_Health.setColumns(10);
		textField_Battle_Health.setBounds(384, 188, 54, 20);
		panel_Battle.add(textField_Battle_Health);
		
		JButton btn_Battle_Add = new JButton("Add");
		btn_Battle_Add.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				encounter.addToEncounter(table_Battle, textField_Battle_Name ,textField_Battle_Init, textField_Battle_Health);
			}
		});
		btn_Battle_Add.setBounds(506, 114, 56, 26);
		panel_Battle.add(btn_Battle_Add);
		
		JLabel lblCreatureName = new JLabel("Creature name");
		lblCreatureName.setBounds(389, 103, 85, 16);
		panel_Battle.add(lblCreatureName);
		
		JLabel lblInitiativeAndHealth = new JLabel("Initiative");
		lblInitiativeAndHealth.setBounds(383, 139, 47, 16);
		panel_Battle.add(lblInitiativeAndHealth);
		
		JLabel lblHealth = new JLabel("Health");
		lblHealth.setBounds(389, 173, 36, 16);
		panel_Battle.add(lblHealth);
		
		JButton btnStartInitiative = new JButton("Start Initiative");
		btnStartInitiative.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				encounter.startInitiative(table_Battle);
			}
		});
		btnStartInitiative.setBounds(377, 213, 112, 26);
		panel_Battle.add(btnStartInitiative);
		
		JLabel lblSortListFrom = new JLabel("Sort list from highest initiative to lowest.");
		lblSortListFrom.setFont(new Font("Dialog", Font.BOLD, 10));
		lblSortListFrom.setBounds(377, 237, 199, 14);
		panel_Battle.add(lblSortListFrom);
		
		chckbxRandomInit = new JCheckBox("allow rolling d20");
		chckbxRandomInit.setSelected(true);
		chckbxRandomInit.setBackground(Color.LIGHT_GRAY);
		chckbxRandomInit.setFont(new Font("Dialog", Font.BOLD, 10));
		chckbxRandomInit.setBounds(446, 154, 107, 22);
		panel_Battle.add(chckbxRandomInit);
		
		JLabel lblMustBeNumbers = new JLabel("Must be numbers.");
		lblMustBeNumbers.setFont(new Font("Dialog", Font.PLAIN, 8));
		lblMustBeNumbers.setBounds(231, 2, 61, 10);
		panel_Battle.add(lblMustBeNumbers);
		
		JButton btnUp = new JButton("up");
		btnUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				encounter.moveCreature(table_Battle, -1);//-1 makes the row go up.
			}
		});
		btnUp.setFont(new Font("Dialog", Font.BOLD, 10));
		btnUp.setBounds(384, 48, 61, 24);
		panel_Battle.add(btnUp);
		
		JButton btnDown = new JButton("down");
		btnDown.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				encounter.moveCreature(table_Battle, 1);//1 makes the row go down.
			}
		});
		btnDown.setFont(new Font("Dialog", Font.BOLD, 10));
		btnDown.setBounds(384, 79, 61, 24);
		panel_Battle.add(btnDown);
		
		JButton btnDie = new JButton("die");
		btnDie.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				encounter.selectedBattleDeath(table_Battle, list_Battle_Dead);
			}
		});
		btnDie.setBounds(509, 78, 51, 26);
		panel_Battle.add(btnDie);
		
		JScrollPane scrollPane_11 = new JScrollPane();
		scrollPane_11.setBounds(528, 279, 158, 114);
		panel_Battle.add(scrollPane_11);
		
		list_Battle_Dead = new JList();
		scrollPane_11.setViewportView(list_Battle_Dead);
		
		JLabel lblDeadList = new JLabel("Dead list");
		lblDeadList.setBounds(528, 263, 55, 16);
		panel_Battle.add(lblDeadList);
		
		JButton btnClear = new JButton("clear");
		btnClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				list_Battle_Dead.setModel(new SortedListModel()); //clear list
			}
		});
		btnClear.setFont(new Font("Dialog", Font.BOLD, 10));
		btnClear.setBounds(626, 255, 59, 24);
		panel_Battle.add(btnClear);
		
		JLabel lblSelectedCreatureDead = new JLabel("selected creature dead?");
		lblSelectedCreatureDead.setBounds(509, 65, 139, 16);
		panel_Battle.add(lblSelectedCreatureDead);
		
		chckbxKeepname = new JCheckBox("KeepName");
		chckbxKeepname.setBackground(Color.LIGHT_GRAY);
		chckbxKeepname.setBounds(570, 116, 112, 24);
		panel_Battle.add(chckbxKeepname);
		
		JLabel lblYouCanRoll = new JLabel("You can roll health too!");
		lblYouCanRoll.setBounds(446, 190, 128, 16);
		panel_Battle.add(lblYouCanRoll);
		
		JLabel lblRollsDOn = new JLabel("Rolls d20 on blank");
		lblRollsDOn.setFont(new Font("Dialog", Font.BOLD, 10));
		lblRollsDOn.setBounds(571, 163, 103, 16);
		panel_Battle.add(lblRollsDOn);
		
		JLabel lblOr = new JLabel("Or +#   example: +2 is d20+2");
		lblOr.setFont(new Font("Dialog", Font.BOLD, 10));
		lblOr.setBounds(548, 173, 160, 16);
		panel_Battle.add(lblOr);
		
		JPanel panel_Settings = new JPanel();
		panel_Settings.setBackground(Color.LIGHT_GRAY);
		panel_Settings.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				globalMousePressed(e);
			}
		});
		tabbedPane.addTab("Settings", null, panel_Settings, null);
		panel_Settings.setLayout(null);
		
		comboBox_Settings_Campaign = new JComboBox();
		comboBox_Settings_Campaign.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.ACTION_PERFORMED==1001){ //pressed enter
					if(!comboBox_Settings_Campaign.getSelectedItem().toString().equals(lastKnownCampaignName)){
						String campaignName = comboBox_Settings_Campaign.getSelectedItem().toString();
						campaignChange(campaignName);
					}
				}
			}
		});
		comboBox_Settings_Campaign.setEditable(true);
		comboBox_Settings_Campaign.setBounds(12, 32, 196, 25);
		panel_Settings.add(comboBox_Settings_Campaign);
		
		JLabel lblForCampaign = new JLabel("For Campaign:");
		lblForCampaign.setBounds(12, 16, 99, 16);
		panel_Settings.add(lblForCampaign);
		
		textField_Settings_Proficiency = new JTextField();
		textField_Settings_Proficiency.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				settings.setProficiency(textField_Settings_Proficiency.getText());
				settings.saveSettings();
			}
		});
		textField_Settings_Proficiency.setText("5,9,13,17");
		textField_Settings_Proficiency.setBounds(12, 69, 114, 20);
		panel_Settings.add(textField_Settings_Proficiency);
		textField_Settings_Proficiency.setColumns(10);
		
		JLabel lblProficiency = new JLabel("Proficiency");
		lblProficiency.setBounds(12, 55, 75, 16);
		panel_Settings.add(lblProficiency);
		
		JLabel lblDefaultCharacterRoll = new JLabel("Default Character Roll");
		lblDefaultCharacterRoll.setHorizontalAlignment(SwingConstants.CENTER);
		lblDefaultCharacterRoll.setBounds(12, 91, 124, 16);
		panel_Settings.add(lblDefaultCharacterRoll);
		
		textField_Settings_CharRoll = new JTextField();
		textField_Settings_CharRoll.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				settings.setDefaultCharRoll(textField_Settings_CharRoll.getText());
				settings.saveSettings();
			}
		});
		textField_Settings_CharRoll.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Settings_CharRoll.setText("3d6");
		textField_Settings_CharRoll.setColumns(10);
		textField_Settings_CharRoll.setBounds(12, 105, 114, 20);
		panel_Settings.add(textField_Settings_CharRoll);
		
		JLabel lblRaceList = new JLabel("Race list");
		lblRaceList.setBounds(549, 0, 88, 16);
		panel_Settings.add(lblRaceList);
		
		textField_Settings_Race = new JTextField();
		textField_Settings_Race.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == e.VK_ENTER){
					SortedListModel model = new SortedListModel();
					
					ListModel cModel = list_Settings_Race.getModel();
					
					for(int i = 0; i < cModel.getSize(); i++){
						model.add(cModel.getElementAt(i));
					}
					model.add(textField_Settings_Race.getText());
					list_Settings_Race.setModel(model);
					
					textField_Settings_Race.setText("");
					
					settings.saveSettings();
				}
				
			}
		});
		textField_Settings_Race.setColumns(10);
		textField_Settings_Race.setBounds(549, 14, 144, 20);
		panel_Settings.add(textField_Settings_Race);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(550, 36, 141, 146);
		panel_Settings.add(scrollPane_4);
		
		list_Settings_Race = new JList();
		scrollPane_4.setViewportView(list_Settings_Race);
		list_Settings_Race.setModel(new AbstractListModel() {
			String[] values = new String[] {"Dwarf", "Elf", "Half-Elf", "Gnome", "Dragonborn", "Human", "Halfling", "Half-Orc", "Tiefling"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list_Settings_Race.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JLabel lblClassList = new JLabel("Class list");
		lblClassList.setBounds(393, 0, 88, 16);
		panel_Settings.add(lblClassList);
		
		textField_Settings_Class = new JTextField();
		textField_Settings_Class.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == e.VK_ENTER){
					SortedListModel model = new SortedListModel();
					
					ListModel cModel = list_Settings_Class.getModel();
					
					for(int i = 0; i < cModel.getSize(); i++){
						model.add(cModel.getElementAt(i));
					}
					model.add(textField_Settings_Class.getText());
					list_Settings_Class.setModel(model);
					
					textField_Settings_Class.setText("");
					
					settings.saveSettings();
				}
			}
		});
		textField_Settings_Class.setColumns(10);
		textField_Settings_Class.setBounds(393, 14, 144, 20);
		panel_Settings.add(textField_Settings_Class);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(394, 36, 141, 146);
		panel_Settings.add(scrollPane_3);
		
		list_Settings_Class = new JList();
		scrollPane_3.setViewportView(list_Settings_Class);
		list_Settings_Class.setModel(new AbstractListModel() {
			String[] values = new String[] {"Barbarian", "Bard", "Cleric", "Druid", "Fighter", "Monk", "Paladin", "Ranger", "Rogue", "Sorcerer", "Warlock", "Wizard"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list_Settings_Class.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JLabel lblNotUsedYet = new JLabel("Not used yet");
		lblNotUsedYet.setFont(new Font("Dialog", Font.BOLD, 10));
		lblNotUsedYet.setBounds(125, 70, 60, 14);
		panel_Settings.add(lblNotUsedYet);
		
		JPanel panel_Info = new JPanel();
		panel_Info.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("Info", null, panel_Info, null);
		panel_Info.setLayout(null);
		
		JTextArea txtrYouCanMake = new JTextArea();
		txtrYouCanMake.setFont(new Font("Dialog", Font.BOLD, 12));
		txtrYouCanMake.setBackground(Color.LIGHT_GRAY);
		txtrYouCanMake.setText("You can make rolls!\r\nd6 - rolls a normal 6 sided die.\r\n2d6 - rolls 2 normal 6 sided die.\r\n2d6d1 - rolls 2 dice, but drops the lowest 1 results.\r\n2d6dl1 - rolls 2 dice, but drops the lowest 1 results.\r\n3d6+6 - rolls 3 dice, but adds 6 at the end. (3,3,1 = 7, +6 = 13)\r\n3d6-3 - rolls 3 dice, but subtracts 3 at the end. (1,1,1 = 3, -3 = 0)\r\n\r\n\r\n\r\nd4, d6, d8, d10, d12, d20, d100. They all work!\r\n\r\n\r\nCurrently not available>\r\n2d6kh1 - rolls 2 dice, but keeps the highest 1 results.\r\n3d6kl1 - rolls 3 dice, but keeps the lowest 1 results.\r\n");
		txtrYouCanMake.setBounds(12, 12, 668, 381);
		panel_Info.add(txtrYouCanMake);
		
		JPanel panel_Dungeon = new JPanel();
		panel_Dungeon.setBackground(Color.LIGHT_GRAY);
		panel_Dungeon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				globalMousePressed(e);
			}
		});
		tabbedPane.addTab("Dungeon", null, panel_Dungeon, null);
		tabbedPane.setEnabledAt(6, false);
		panel_Dungeon.setLayout(null);
		
		JLabel label_8 = new JLabel("For Campaign:");
		label_8.setBounds(0, 0, 99, 16);
		panel_Dungeon.add(label_8);
		
		comboBox_Dungeon_Campaign = new JComboBox();
		comboBox_Dungeon_Campaign.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.ACTION_PERFORMED==1001){ //pressed enter
					if(!comboBox_Dungeon_Campaign.getSelectedItem().toString().equals(lastKnownCampaignName)){
						String campaignName = comboBox_Dungeon_Campaign.getSelectedItem().toString();
						campaignChange(campaignName);
					}
				}
			}
		});
		comboBox_Dungeon_Campaign.setEditable(true);
		comboBox_Dungeon_Campaign.setBounds(0, 16, 196, 25);
		panel_Dungeon.add(comboBox_Dungeon_Campaign);
		
		panel_Misc = new JPanel();
		panel_Misc.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("Misc", null, panel_Misc, null);
		tabbedPane.setEnabledAt(7, false);
		panel_Misc.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(12, 36, 196, 20);
		panel_Misc.add(textField);
		textField.setColumns(10);
		
		JLabel lblChracterName = new JLabel("Chracter Name");
		lblChracterName.setBounds(12, 23, 97, 14);
		panel_Misc.add(lblChracterName);
		
		JButton btnRollNameMisc = new JButton("Roll Name");
		btnRollNameMisc.setBounds(99, 12, 108, 23);
		panel_Misc.add(btnRollNameMisc);
		
		txtStatstr = new JTextField();
		txtStatstr.setBounds(219, 82, 114, 20);
		panel_Misc.add(txtStatstr);
		txtStatstr.setHorizontalAlignment(SwingConstants.CENTER);
		txtStatstr.setColumns(10);
		
		JLabel lblStrength = new JLabel("Strength");
		lblStrength.setBounds(219, 68, 55, 16);
		panel_Misc.add(lblStrength);
		
		JLabel lblDexterity = new JLabel("Dexterity");
		lblDexterity.setBounds(219, 114, 55, 16);
		panel_Misc.add(lblDexterity);
		
		txtStatDex = new JTextField();
		txtStatDex.setBounds(219, 128, 114, 20);
		panel_Misc.add(txtStatDex);
		txtStatDex.setHorizontalAlignment(SwingConstants.CENTER);
		txtStatDex.setColumns(10);
		
		JLabel lblCon = new JLabel("Constitution");
		lblCon.setBounds(219, 160, 68, 16);
		panel_Misc.add(lblCon);
		
		txtStatCon = new JTextField();
		txtStatCon.setBounds(219, 174, 114, 20);
		panel_Misc.add(txtStatCon);
		txtStatCon.setHorizontalAlignment(SwingConstants.CENTER);
		txtStatCon.setColumns(10);
		
		JLabel lblIntelligence = new JLabel("Intelligence");
		lblIntelligence.setBounds(219, 206, 68, 16);
		panel_Misc.add(lblIntelligence);
		
		txtStatInt = new JTextField();
		txtStatInt.setBounds(219, 220, 114, 20);
		panel_Misc.add(txtStatInt);
		txtStatInt.setHorizontalAlignment(SwingConstants.CENTER);
		txtStatInt.setColumns(10);
		
		JLabel lblWisdom = new JLabel("Wisdom");
		lblWisdom.setBounds(219, 252, 55, 16);
		panel_Misc.add(lblWisdom);
		
		txtStatWis = new JTextField();
		txtStatWis.setBounds(219, 266, 114, 20);
		panel_Misc.add(txtStatWis);
		txtStatWis.setHorizontalAlignment(SwingConstants.CENTER);
		txtStatWis.setColumns(10);
		
		JLabel lblCharisma = new JLabel("Charisma");
		lblCharisma.setBounds(219, 298, 55, 16);
		panel_Misc.add(lblCharisma);
		
		txtStatCha = new JTextField();
		txtStatCha.setBounds(219, 312, 114, 20);
		panel_Misc.add(txtStatCha);
		txtStatCha.setHorizontalAlignment(SwingConstants.CENTER);
		txtStatCha.setColumns(10);
		
		txtChardiceroll = new JTextField();
		txtChardiceroll.setBounds(220, 36, 114, 20);
		panel_Misc.add(txtChardiceroll);
		txtChardiceroll.setHorizontalAlignment(SwingConstants.CENTER);
		txtChardiceroll.setText("4d6dl1");
		txtChardiceroll.setColumns(10);
		
		JLabel lblDiceRoll = new JLabel("Dice Roll");
		lblDiceRoll.setBounds(226, 22, 55, 16);
		panel_Misc.add(lblDiceRoll);
		btnRollNameMisc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String text=getNewCharacterName();
				
				textField.setText(text);
			}
		});
		
		
		
		
		
		
		
		
		
		Thread updater = new Thread(){
			public void run() {
		
				if(new File(workDir + fSep + "Campaigns" + fSep).exists()){
					//set up Campaign Combo Box
					File file = new File(workDir + fSep + "Campaigns" + fSep);
					String[] subDir = file.list();
					
					if(subDir.length==0){
						comboBox_Character_Campaign.addItem("");
					} else
					{
						System.out.println("Getting campaign list...");
						for(String dir : subDir)
						{
						    if (new File(workDir + fSep + "Campaigns" + fSep + dir).isDirectory())
						    {
						        createNewCampaign(dir);
						        System.out.println("Added `" + dir + "` to Campaign List");
						        lastKnownCampaignName = dir;
						    }
						}
					}
					System.out.println("Getting App Settings file...");
					//get settings file for last used campaign
					if(new File(workDir + fSep + "Settings.txt").exists()){
						try (
							    BufferedReader br = new BufferedReader(new FileReader( new File(workDir + fSep + "Settings.txt")));
						){
							String line = "";
							String[] str;
							
							try {line = br.readLine();} catch (Exception e) {} //version of save file;
							try {line = br.readLine();} catch (Exception e) {} //last known campaign name.
							if(line.length() > 0){
								str = line.split(":: ");
								System.out.println("loading settings: " + str[1]);
								setCampaignTo(str[1], false);
								lastKnownCampaignName = str[1];
							}
							
							br.close();
						} catch (FileNotFoundException e1) {
							System.out.println("Couln't find settings file " + new File(workDir + fSep + "Settings.txt") + " Now saving.");
							//saveAppSettings();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						catch (Exception e) {e.printStackTrace();}
					}
					else setCampaignTo(lastKnownCampaignName, false); //if we don't find settings file
					
				}
				canSaveApp = true;
				System.out.println("Done initial loading.");
//				charList = (ArrayList<String>) campaign.getCharacters().clone();
//				list_Characters.removeAll();
//				updateCharacterList();
				//end of loading
				
			}
		};
		updater.start();
		
		//end of frame
	}


	protected void clearTowns() {
		townChar = null;
		
		textField_Towns_Name.setText("");
		textField_Towns_Size.setText("");
		textField_Towns_CharFilter.setText("");
		textField_Towns_TownFilter.setText("");
		
		textArea_Towns_Notes.setText("");
		textArea_Towns_CharNotes.setText("");
		
		list_Towns_Towns.setSelectedValue(null, false);
		list_Towns_Char.setSelectedValue(null, false);
		
		updateTownsList();
		updateTownsCharList("");
	}


	protected void loadSelectedTown(String name) {
		town = new Towns(this);
		
		town.loadTown(name);
		
		textField_Towns_Size.setText(town.getTownSize());
		textArea_Towns_Notes.setText(town.gettNote());
		
		textField_Towns_Name.setText(name);
		
		updateTownsCharList(name);
	}




	protected void removeCharFromList(String prevName) {
		for(int i = 0; i < campaign.getCharacters().size(); i++){
			if(campaign.getCharacters().get(i).equals(prevName)){
				campaign.getCharacters().remove(i);
				System.out.println("pass c");
				break;
			}
		}
		
		for(int i = 0; i < charList.size(); i++){
			if(charList.get(i).equals(prevName)){
				charList.remove(i);
				System.out.println("pass cl");
				break;
			}
		}
		updateCharacterList();
	}
	
	protected void removeTownFromList(String prevName) {
		for(int i = 0; i < campaign.getTowns().size(); i++){
			if(campaign.getTowns().get(i).equals(prevName)){
				campaign.getTowns().remove(i);
				break;
			}
		}
		
		for(int i = 0; i < townList.size(); i++){
			if(townList.get(i).equals(prevName)){
				townList.remove(i);
				break;
			}
		}
		updateTownsList();
	}
	
	public static boolean isTownNameCurrentlyUsed(String newName) {
		//campaign.getCharacterList(campaign.getName());
		for(int i = 0; i < campaign.getTowns().size(); i++){
			if(campaign.getTowns().get(i).equals(newName)){
				return true;
			}
		}
		
		return false;
	}

	public static boolean isNameCurrentlyUsed(String newName) {
		//campaign.getCharacterList(campaign.getName());
		for(int i = 0; i < campaign.getCharacters().size(); i++){
			if(campaign.getCharacters().get(i).equals(newName)){
				return true;
			}
		}
		
		return false;
	}

	protected void rollNewCharacterStats() {
		String rollInfo[] = getDiceRollInfo(textField_Char_DiceRoll.getText());
		
		int diceToRoll = Integer.parseInt(rollInfo[0]);
		int diceSideToRoll = Integer.parseInt(rollInfo[1]);
		
		int postCount = Integer.parseInt(rollInfo[2]);
		String postFix = rollInfo[3];
		
		
		int strength = rollForStat(diceToRoll, diceSideToRoll, postCount, postFix);
		int dex =      rollForStat(diceToRoll, diceSideToRoll, postCount, postFix);
		int con =      rollForStat(diceToRoll, diceSideToRoll, postCount, postFix);
		int intel =    rollForStat(diceToRoll, diceSideToRoll, postCount, postFix);
		int wis =      rollForStat(diceToRoll, diceSideToRoll, postCount, postFix);
		int cha =      rollForStat(diceToRoll, diceSideToRoll, postCount, postFix);
		
		textField_Char_Str.setText(strength+"");
		textField_Char_Dex.setText(dex+"");
		textField_Char_Con.setText(con+"");
		textField_Char_Int.setText(intel+"");
		textField_Char_Wis.setText(wis+"");
		textField_Char_Cha.setText(con+"");
		
		updateStatMods();
	}

	protected void loadCharacter(String name) {
		character = new Character(this, name);
		
		textField_Char_Name.setText(name);
		
		textField_Char_Str.setText(character.getStr()+"");
		textField_Char_Dex.setText(character.getDex()+"");
		textField_Char_Con.setText(character.getCon()+"");
		textField_Char_Int.setText(character.getIntel()+"");
		textField_Char_Wis.setText(character.getWis()+"");
		textField_Char_Cha.setText(character.getCha()+"");
		
		textField_Char_Town.setText(character.getTown()+"");
		textField_Char_Job.setText(character.getJob()+"");
		textField_Char_Race.setText(character.getRace()+"");
		textField_Char_Class.setText(character.getCharClass()+"");
		textField_Char_Level.setText(character.getLevel()+"");
		if(character.getAlive().equals("true")) {
			chckbxAlive.setSelected(true);
			chckbxDead.setSelected(false);
		}
		else if(character.getAlive().equals("false")) {
			chckbxDead.setSelected(true);
			chckbxAlive.setSelected(false);
		}
		
		textArea_Char_Notes.setText(character.getNotes()+"");
		textArea_Char_Spells.setText(character.getSpellsKnown()+"");
		
		updateStatMods();
		
	}

	private void updateStatMods() {
		textField_Char_StrMod.setText((int) Math.floor((getStr()-10)/2)+"");
		textField_Char_DexMod.setText((int) Math.floor((getDex()-10)/2)+"");
		textField_Char_ConMod.setText((int) Math.floor((getCon()-10)/2)+"");
		textField_Char_IntMod.setText((int) Math.floor((getInt()-10)/2)+"");
		textField_Char_WisMod.setText((int) Math.floor((getWis()-10)/2)+"");
		textField_Char_ChaMod.setText((int) Math.floor((getCon()-10)/2)+"");
	}
	
	

	void updateCharacterList() {
		SortedListModel model = new SortedListModel();
		list_Characters.removeAll();
		
		for(int i = 0; i < charList.size(); i++){
			model.add(charList.get(i));
		}
		filterModel(model, textField_Char_Filter.getText(), charList);
		list_Characters.setModel(model);
	}
	
	void updateTownsList() {
		SortedListModel model = new SortedListModel();
		list_Towns_Towns.removeAll();
		
		for(int i = 0; i < townList.size(); i++){
			model.add(townList.get(i));
		}
		filterModel(model, textField_Towns_TownFilter.getText(), townList);
		list_Towns_Towns.setModel(model);
	}

	private void updateTownsCharList(final String name) {
		Thread updater = new Thread(){
			public void run() {
				SortedListModel model = new SortedListModel();
				list_Towns_Char.removeAll();
				
				ArrayList<String> list = new ArrayList<String>();
		
		
				for(int i = 0; i < charList.size(); i++){
					String c = charList.get(i);
					Character character = new Character(null, c); //load character
					String town = character.getTown();
					//if(town=="") System.out.println("NULL");
					if(town.equals(name) && !town.equals("") && town != null){
						model.add(c);
						list.add(c);
					}
				}
				filterModel(model, textField_Towns_CharFilter.getText(), list);
				list_Towns_Char.setModel(model);
				//interrupt();
			}
		};
		updater.start();
	}
	
	public void filterModel(SortedListModel model, String filter, ArrayList<String> list) {
	    for (String s : list) {
	        if (!s.toLowerCase().contains(filter.toLowerCase())) {
	            if (model.contains(s)) {
	                model.removeElement(s);
	            }
	        } else {
	            if (!model.contains(s)) {
	                model.add(s);
	            }
	        }
	    }
	}
	
	protected void createNewCharacter() {createNewCharacter(null);}


	protected void createNewCharacter(String name) {
		String rollInfo[] = getDiceRollInfo(textField_Char_DiceRoll.getText());
		
		int diceToRoll = Integer.parseInt(rollInfo[0]);
		int diceSideToRoll = Integer.parseInt(rollInfo[1]);
		
		int postCount = Integer.parseInt(rollInfo[2]);
		String postFix = rollInfo[3];
		
		int additional = Integer.parseInt(rollInfo[4]);
		
		
		int strength = rollForStat(diceToRoll, diceSideToRoll, postCount, postFix, additional);
		int dex =      rollForStat(diceToRoll, diceSideToRoll, postCount, postFix, additional);
		int con =      rollForStat(diceToRoll, diceSideToRoll, postCount, postFix, additional);
		int intel =    rollForStat(diceToRoll, diceSideToRoll, postCount, postFix, additional);
		int wis =      rollForStat(diceToRoll, diceSideToRoll, postCount, postFix, additional);
		int cha =      rollForStat(diceToRoll, diceSideToRoll, postCount, postFix, additional);
		
		textField_Char_Str.setText(strength+"");
		textField_Char_Dex.setText(dex+"");
		textField_Char_Con.setText(con+"");
		textField_Char_Int.setText(intel+"");
		textField_Char_Wis.setText(wis+"");
		textField_Char_Cha.setText(con+"");
		
		updateStatMods();
		
		String charName;
		if(name != null)
		{
			charName = name;
			character = new Character(this, charName, strength, dex, con, intel, wis, cha);
			if(textField_Towns_Name.getText().length() != 0)
			{
				textField_Char_Town.setText(textField_Towns_Name.getText());
				character.setTown(textField_Towns_Name.getText());
			}
		} else 
		{
			charName = getNewCharacterName();
			character = new Character(this, charName, strength, dex, con, intel, wis, cha);
			textField_Char_Town.setText("");
		}
		textField_Char_Name.setText(charName);
		lastKnownCharacterName = charName;
		
		
		textArea_Char_Notes.setText("");
		textField_Char_Job.setText("");
		textField_Char_Race.setText("");
		textField_Char_Class.setText("");
		textField_Char_Level.setText("");
		
		textArea_Char_Spells.setText("Cantrips\nLevel 1 - (0)\nLevel 2 - (0)\nLevel 3 - (0)\nLevel 4 - (0)\nLevel 5 - (0)\nLevel 6 - (0)\nLevel 7 - (0)\nLevel 8 - (0)\nLevel 9 - (0)");
		
		
		chckbxAlive.setSelected(true);
		
		
		addCharToList(charName);
		character.saveCharacter();
	}

	public void addCharToList(String charName) {
		campaign.getCharacters().add(charName);
		charList.add(charName);
		updateCharacterList();
	}
	
	public void addTownToList(String charName) {
		campaign.getTowns().add(charName);
		townList.add(charName);
		updateTownsList();
	}

	protected String[] getDiceRollInfo(String getFrom) {
		String diceRoll = getFrom;
		if(diceRoll.contains("d")){
			if(diceRoll.startsWith("d")){
				diceRoll = ("1" + diceRoll); //roll 1 dice then
			}
		}
		
		String str[] = null;
		String postFix = "";
		int postCount = 0;
		int additional = 0;
		if(diceRoll.contains("+")){ //add by
			String[] spl = diceRoll.split("\\+");
			additional = Integer.parseInt(spl[1]);
			diceRoll = spl[0]; //remove it as if it was never there
		}
		if(diceRoll.contains("-")){ //add by
			String[] spl = diceRoll.split("\\-");
			additional -= Integer.parseInt(spl[1]);
			diceRoll = spl[0]; //remove it as if it was never there.
		}
		if(diceRoll.contains("dl")){ //drop lowest
			str = diceRoll.split("dl");
			postFix = "dl";
			postCount = Integer.parseInt(str[1]);
			str = str[0].split("d");
		}
		else if(diceRoll.contains("dh")){ //drop highest
			str = diceRoll.split("dh");
			postFix = "dh";
			postCount = Integer.parseInt(str[1]);
			str = str[0].split("d");
		}
		else if(diceRoll.contains("kh")){ //keep highest
			str = diceRoll.split("kh");
			postFix = "kh";
			postCount = Integer.parseInt(str[1]);
			str = str[0].split("d");
		}
		else if(diceRoll.contains("kl")){ //keep lowest
			str = diceRoll.split("kl");
			postFix = "kl";
			postCount = Integer.parseInt(str[1]);
			str = str[0].split("d");
		}
		else if(occurence(diceRoll)>1){ //drop lowest count
			str = diceRoll.split("d");
			postFix = "d";
			postCount = Integer.parseInt(str[2]);
		}
		else {
			str = diceRoll.split("d");
		}
		
		String rollInfo[] = new String[5];
		
		rollInfo[0] = str[0];
		rollInfo[1] = str[1];
		rollInfo[2] = postCount+"";
		rollInfo[3] = postFix+"";
		rollInfo[4] = additional+"";
		
		return rollInfo;
	}

	protected void globalMousePressed(MouseEvent e) {
		//Character Name
		if(!textField_Char_Name.getText().equals(lastKnownCharacterName)){
			System.out.println("NAME CHANGE: " + lastKnownCharacterName + " to " + textField_Char_Name.getText());
			lastKnownCharacterName = textField_Char_Name.getText();
		}
		//Campaign changes
		if(!comboBox_Character_Campaign.getSelectedItem().toString().equals(lastKnownCampaignName)){
			String campaignName = comboBox_Character_Campaign.getSelectedItem().toString();
			campaignChange(campaignName);
		}
		else if(!comboBox_Towns_Campaign.getSelectedItem().toString().equals(lastKnownCampaignName)){
			String campaignName = comboBox_Character_Campaign.getSelectedItem().toString();
			campaignChange(campaignName);
		}
		else if(!comboBox_Notes_Campaign.getSelectedItem().toString().equals(lastKnownCampaignName)){
			String campaignName = comboBox_Character_Campaign.getSelectedItem().toString();
			campaignChange(campaignName);
		}
		else if(!comboBox_Dungeon_Campaign.getSelectedItem().toString().equals(lastKnownCampaignName)){
			String campaignName = comboBox_Character_Campaign.getSelectedItem().toString();
			campaignChange(campaignName);
		}
		else if(!comboBox_Settings_Campaign.getSelectedItem().toString().equals(lastKnownCampaignName)){
			String campaignName = comboBox_Character_Campaign.getSelectedItem().toString();
			campaignChange(campaignName);
		}
	}

	protected void campaignChange(String campaignName) {
		File newFolder = new File(workDir + fSep + "Campaigns" + fSep + campaignName);
		if(newFolder.exists()){
			setCampaignTo(campaignName, false); //if exists, go to it
			System.out.println("Found campaign: " + campaignName);
		} else if(newFolder.mkdirs()){   //else we need to create the campaign
			createNewCampaign(campaignName);
			setCampaignTo(campaignName, false);
			System.out.println("Created campaign: " + campaignName);
		} else {System.out.println("Failed to create directory at " + newFolder);}
	}

	private void createNewCampaign(String campaignName) {
		comboBox_Character_Campaign.addItem(campaignName);
		comboBox_Towns_Campaign.addItem(campaignName);
		comboBox_Notes_Campaign.addItem(campaignName);
		comboBox_Dungeon_Campaign.addItem(campaignName);
		comboBox_Settings_Campaign.addItem(campaignName);
	}

	private void setCampaignTo(String line, boolean save) {
		
		int n = comboBox_Character_Campaign.getItemCount();
		for(int i = 0; i < n; i++){
			if(comboBox_Character_Campaign.getItemAt(i).toString().equals(line)){
				comboBox_Character_Campaign.setSelectedIndex(i);
				break;
			}
		}
		
		n = comboBox_Towns_Campaign.getItemCount();
		for(int i = 0; i < n; i++){
			if(comboBox_Towns_Campaign.getItemAt(i).toString().equals(line)){
				comboBox_Towns_Campaign.setSelectedIndex(i);
				break;
			}
		}
		
		n = comboBox_Notes_Campaign.getItemCount();
		for(int i = 0; i < n; i++){
			if(comboBox_Notes_Campaign.getItemAt(i).toString().equals(line)){
				comboBox_Notes_Campaign.setSelectedIndex(i);
				break;
			}
		}
		
		n = comboBox_Dungeon_Campaign.getItemCount();
		for(int i = 0; i < n; i++){
			if(comboBox_Dungeon_Campaign.getItemAt(i).toString().equals(line)){
				comboBox_Dungeon_Campaign.setSelectedIndex(i);
				break;
			}
		}
		
		n = comboBox_Settings_Campaign.getItemCount();
		for(int i = 0; i < n; i++){
			if(comboBox_Settings_Campaign.getItemAt(i).toString().equals(line)){
				comboBox_Settings_Campaign.setSelectedIndex(i);
				break;
			}
		}
		
		lastKnownCampaignName = line;
		campaign = new Campaign(this, line);
		
		//System.out.println("CAMPAIGN CHANGE: " + lastKnownCampaignName + " to " + line);
		//lastKnownCampaignName = line;
		
		
		settings.loadSettings(line);
		loadCampaignSettings();
		
		charList = (ArrayList<String>) campaign.getCharacters().clone();
		list_Characters.removeAll();
		updateCharacterList();
		
		townList = (ArrayList<String>) campaign.getTowns().clone();
		list_Towns_Towns.removeAll();
		updateTownsList();
		
		
		//load campaign notes
		File file = new File(App.workDir + fSep + "Campaigns" + fSep + campaign.getName() + fSep + "Notes.txt");
		
		if(file.exists()){
			try (BufferedReader br = new BufferedReader(new FileReader(file));)
			{
				
				String notes = "";
				try { notes = br.readLine(); /*first line is notes.*/}catch (Exception e) {}

				if(notes.contains("]]~")) notes = notes.replace("]]~",lSep); //Compatability change
				else notes = notes.replace("\\n","\n");  //pre-release version
				
				textArea_Notes_Notes.setText(notes);
				
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		
		if(save) {
			saveAppSettings();
			//settings.saveSettings();
		}
		
	}

	private void saveAppSettings() {
		if(canSaveApp){
			System.out.println("Saving App Settings...");
			File appSettings = new File(workDir + fSep + "Settings.txt");
			
			//if(appSettings.exists())
			{
				try
				{
					PrintWriter out = new PrintWriter(appSettings);
					
					out.println("txt:: 1"); //misc random number for future
					out.println("Campaign:: " + lastKnownCampaignName);
					
					
					out.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
			}
		}
	}


	private void loadCampaignSettings() {
		textField_Settings_Proficiency.setText(settings.getProficiency());
		textField_Settings_CharRoll.setText(settings.getDefaultCharRoll());
		textField_Char_DiceRoll.setText(settings.getDefaultCharRoll()); //set this too, as its the default roll.
	}


	protected String getNewCharacterName() {
		String text = "error";
		int maxLines=1;
		try {
			maxLines = count(workDir + fSep + "Name List.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("total lines: " + maxLines);
		do
		{
			try {
				int lines = numGen.nextInt(maxLines);
				text = getLine(lines);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} while(isNameCurrentlyUsed(text) == true);
		
		System.out.println("Rolled name: " + text);
		return text;
	}
	
	protected int rollForStat(int diceToRoll, int diceSideToRoll, int postCount, String postFix) {
		return rollForStat(diceToRoll,diceSideToRoll,postCount,postFix,0);
	}

	protected int rollForStat(int diceToRoll, int diceSideToRoll, int postCount, String postFix, int additional) {
		int[] roll = new int[diceToRoll];
		Random gen = new Random();
		
		postCount = Math.min(postCount, diceToRoll); //postCount cannot be higher than the amount of dice being rolled.
		
		for(int i=0; i<diceToRoll; i++){
			roll[i]=gen.nextInt(diceSideToRoll)+1;
		}
		
		int total = 0;
		int count = 0;
		String rolls = "";
		List<String> dRolls = new ArrayList<String>(); 
		
		for(int i=0; i<roll.length; i++){
			total += roll[i];
			rolls += roll[i] + "," ;
			dRolls.add(roll[i] + "");
		}
		
		if(postFix.equals("")) {
			if(additional != 0){
				total += additional;
				System.out.println("Total: " + total + "(+" + additional + ") rolls: " + rolls);
			} else System.out.println("Total: " + total + " rolls: " + rolls);
			return total;
		}
		
		if(postFix.equals("dl") || postFix.equals("d")) {
			System.out.println(dRolls);
			for(int repeatFor = 0; repeatFor<postCount; repeatFor++){
				int lowestDie = 0;
				int lowestRoll = diceSideToRoll;
				for(int i = 0; i<dRolls.size(); i++){
					//for(int j = 0; j<dRolls.size(); j++){
						if(Integer.parseInt(dRolls.get(i))<lowestRoll){ //if the dice is lower
							lowestDie = i;
							lowestRoll = Integer.parseInt(dRolls.get(i));
						}
					//}
				}
				
				dRolls.remove(lowestDie);
			}
			System.out.println(dRolls);
			
			
//			for(int repeatFor = 0; repeatFor<postCount; repeatFor++){
//				int lowestDie = 0;
//				int lowestRoll = diceSideToRoll;
//				for(int i = 0; i<roll.length; i++){
//					if(roll[i]<lowestRoll){ //if the dice is lower
//						lowestDie = i;
//						lowestRoll = roll[i];
//					}
//				}
//				roll[lowestDie] = 0;
//			}
			
			for(int i=0; i<dRolls.size(); i++){
				count += Integer.parseInt(dRolls.get(i));
			}
			
//			for(int i=0; i<roll.length; i++){
//				count += roll[i];
//			}
			
			
			
			if(additional != 0){
				count += additional;
				System.out.println("Total: " + count + "(+" + additional + ") rolls: " + rolls);
			} else System.out.println("Total: " + count + " rolls: " + rolls);
			return count;
			
		}
		
		
		return 0;
	}
	

	public int count(String filename) throws IOException {
		//System.out.println("rolling... " + filename);
	    InputStream is = new BufferedInputStream(new FileInputStream(filename));
	    try {
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean endsWithoutNewLine = false;
	        while ((readChars = is.read(c)) != -1) {
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n')
	                    ++count;
	            }
	            endsWithoutNewLine = (c[readChars - 1] != '\n');
	        }
	        if(endsWithoutNewLine) {
	            ++count;
	        } 
	        return count;
	    } finally {
	        is.close();
	    }
	}
	
	public static int countLines(String filename) throws IOException {
	    InputStream is = new BufferedInputStream(new FileInputStream(filename));
	    try {
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = is.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	        }
	        return (count == 0 && !empty) ? 1 : count;
	    } finally {
	        is.close();
	    }
	}
	
	public String getLine(int i) throws FileNotFoundException, IOException{
		System.out.println("line number: " + i);
		String line = "error2";
		int count=0;
		if(i==0){i++;}
		try (
//		    InputStream fis = new FileInputStream(workDir + fSep + "Name List.txt");
//		    InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
//		    BufferedReader br = new BufferedReader(isr);
				BufferedReader br = new BufferedReader(new FileReader( new File(workDir + fSep + "Name List.txt")));
		) {
			while(count!=i){
				count++;
				line = br.readLine();
				if(count==i){
					return line;
				}
			}
			
			
//		    while ((line = br.readLine()) != null) {
//		    	while(count!=i){
//		    		count++;
//		    		if(count==i){
//		    			return line;
//		    		}
//		    	}
//		    }
		}
		return line;
	}
	
	/**
     * Export a resource embedded into a Jar file to the local file path.
     *
     * @param resourceName ie.: "/SmartLibrary.dll"
     * @return The path to the exported resource
     * @throws Exception
     */
    static public String ExportResource(String resourceName) throws Exception {
        InputStream stream = null;
        OutputStream resStreamOut = null;
        String jarFolder;
        try {
            stream = App.class.getResourceAsStream(resourceName);//note that each / is a directory down in the "jar tree" been the jar the root of the tree
            
            if(stream == null) {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }

            int readBytes;
            byte[] buffer = new byte[4096];
            jarFolder = new File(App.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
            resStreamOut = new FileOutputStream(jarFolder + resourceName);
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            stream.close();
            resStreamOut.close();
        }

        return jarFolder + resourceName;
    }
    
    /**
     * returns a count of occurence string appears in
     *
     * @param s
     * @return The path to the exported resource
     */
    
    static public int occurence(String s){
    	int counter = 0;
    	for( int i=0; i<s.length(); i++ ) {
    	    if( s.charAt(i) == '$' ) {
    	        counter++;
    	    } 
    	}
    	return counter;
    }
    
    public int getStr(){
		return Integer.parseInt(textField_Char_Str.getText());
	}
	
	public int getDex(){
		return Integer.parseInt(textField_Char_Dex.getText());
	}
	
	public int getCon(){
		return Integer.parseInt(textField_Char_Con.getText());
	}
	
	public int getInt(){
		return Integer.parseInt(textField_Char_Int.getText());
	}
	
	public int getWis(){
		return Integer.parseInt(textField_Char_Wis.getText());
	}
	
	public int getCha(){
		return Integer.parseInt(textField_Char_Cha.getText());
	}
	
	private void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(e.getButton()==3) //right click
					if(textArea_Towns_Notes.getSelectedText() == null || isNameCurrentlyUsed(textArea_Towns_Notes.getSelectedText())){
						//popupMenu_Towns.remove(mntmTownsCreateCharacter);
						mntmTownsCreateCharacter.setEnabled(false);
					} else {
						//popupMenu_Towns.add(mntmTownsCreateCharacter);
						mntmTownsCreateCharacter.setEnabled(true);
					}
				if (e.isPopupTrigger()) {
					
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	private void createKeybindings(JTable table) {
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
		    table.getActionMap().put("Enter", new AbstractAction() {
		        @Override
		        public void actionPerformed(ActionEvent ae) {
		            //do something on JTable enter pressed
		        }
		    });
		}
}
