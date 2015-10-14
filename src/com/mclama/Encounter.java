package com.mclama;

import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Encounter {
	
	private App app;
	
	public Encounter(App app){
		this.app = app;
	}
	
	public void moveCreature(JTable table_Battle, int move) {
		TableModel model = table_Battle.getModel();
		
		//Set dimensions
			Object data = new Object[model.getRowCount()][model.getColumnCount()];
			//create a clone
			TableModel cloneModel = new DefaultTableModel((Object[][]) data,
				new String[] {
					"Name", "Initiative", "Health", "Heal/Damage"
				});
			//set clone model information
			for(int i = 0; i < model.getRowCount(); i++){
				cloneModel.setValueAt(model.getValueAt(i, 0), i, 0);
				cloneModel.setValueAt(model.getValueAt(i, 1), i, 1);
				cloneModel.setValueAt(model.getValueAt(i, 2), i, 2);
			}
			
		if((table_Battle.getSelectedRow() > 0 || move == 1) && (table_Battle.getSelectedRow() < model.getRowCount() || move == -1)){ //if within boundaries.
			model.setValueAt(cloneModel.getValueAt(table_Battle.getSelectedRow()+move, 0), table_Battle.getSelectedRow(), 0);
			model.setValueAt(cloneModel.getValueAt(table_Battle.getSelectedRow()+move, 1), table_Battle.getSelectedRow(), 1);
			model.setValueAt(cloneModel.getValueAt(table_Battle.getSelectedRow()+move, 2), table_Battle.getSelectedRow(), 2);
			
			model.setValueAt(cloneModel.getValueAt(table_Battle.getSelectedRow(), 0), table_Battle.getSelectedRow()+move, 0);
			model.setValueAt(cloneModel.getValueAt(table_Battle.getSelectedRow(), 1), table_Battle.getSelectedRow()+move, 1);
			model.setValueAt(cloneModel.getValueAt(table_Battle.getSelectedRow(), 2), table_Battle.getSelectedRow()+move, 2);
			
			table_Battle.setRowSelectionInterval(0, table_Battle.getSelectedRow()+move);
		}
		
		
	}
	
	protected void selectedBattleDeath(JTable table_Battle, JList list_Battle_Dead) {
		TableModel model = table_Battle.getModel();
		
		int row = table_Battle.getSelectedRow();
		String name = (String) model.getValueAt(row, 0);
		
		
		SortedListModel listModel = new SortedListModel();
		//Due to the jList not allowing duplicate names, I went with a numerical form.
		//Showing the death count of same name.
		Object value;
		boolean onList = false;
		for(int i = 0; i < list_Battle_Dead.getModel().getSize(); i++){
			value = list_Battle_Dead.getModel().getElementAt(i);
			
			String str[] = ((String) value).split("-");
			
			if(str[0].equals(name)) { //if same name, increase occur count.
				onList = true;
				if(str.length > 1){ 
					int count = Integer.parseInt(str[1].replace("(", "").replace(")", "")) + 1;
					value = str[0] + "-(" + count + ")";
					listModel.removeElement(str[0] + "-" + str[1]);
				} else
				{
					value = str[0] + "-(2)";
					listModel.removeElement(str[0]);
				}
			}
			
			listModel.add(value);
		}
		if(onList==false)
			listModel.add(name);
		list_Battle_Dead.setModel(listModel);
		
		//now update the battle table by removing the dead row.
		((DefaultTableModel) model).removeRow(table_Battle.getSelectedRow());
	}


	protected void startInitiative(JTable table_Battle) {
		TableModel model = table_Battle.getModel();
		
		//Set dimensions
		Object data = new Object[model.getRowCount()][model.getColumnCount()];
		//create a clone
		TableModel cloneModel = new DefaultTableModel((Object[][]) data,
			new String[] {
				"Name", "Initiative", "Health", "Heal/Damage"
			});
		//set clone model information
		for(int i = 0; i < model.getRowCount(); i++){
			cloneModel.setValueAt(model.getValueAt(i, 0), i, 0);
			cloneModel.setValueAt(model.getValueAt(i, 1), i, 1);
			cloneModel.setValueAt(model.getValueAt(i, 2), i, 2);
		}
		//make sure there are no null values.
		for(int i = 0; i < model.getRowCount(); i++){
			if(cloneModel.getValueAt(i, 1) == null){
				cloneModel.setValueAt(-1, i, 1);
			}
			
		}
		
		//now add from highest to lowest to a new model
		TableModel newModel = new DefaultTableModel((Object[][]) data,
				new String[] {
					"Name", "Initiative", "Health", "Heal/Damage"
				});
		
		int best;
		int bRow;
		
		for(int i = 0; i < model.getRowCount(); i++){
			best = -100;
			bRow = -1;
			//cycle the clone model for the highest initiative.
			for(int j = 0; j < model.getRowCount(); j++){
				if((int) cloneModel.getValueAt(j, 1) > best){
					bRow = j;
					best = (int) cloneModel.getValueAt(j, 1);
				}
			}
			if(bRow != -1){
				//now we have the highest value from the list, lets do something with the row now.
				newModel.setValueAt(cloneModel.getValueAt(bRow, 0), i, 0); //name
				newModel.setValueAt(cloneModel.getValueAt(bRow, 1), i, 1); //Inititative
				newModel.setValueAt(cloneModel.getValueAt(bRow, 2), i, 2); //health
				
				cloneModel.setValueAt(-1, bRow, 1); //fake out clone model into removing the best row from the iteration.
			}
			
		}
		//okay, now we have our list in newModel, change our table to look the same.
		
		for(int i = 0; i < model.getRowCount(); i++){
			model.setValueAt(newModel.getValueAt(i, 0), i, 0); //name
			model.setValueAt(newModel.getValueAt(i, 1), i, 1); //Initiative
			model.setValueAt(newModel.getValueAt(i, 2), i, 2); //health
			model.setValueAt(null,  i, 3);                     //heal/damage... just a reset
		}
		
		table_Battle.clearSelection();
	}


	protected void addToEncounter(JTable table_Battle, JTextField textField_Battle_Name, JTextField textField_Battle_Init
			, JTextField textField_Battle_Health) {
		DefaultTableModel model = (DefaultTableModel) table_Battle.getModel();
		String cName = textField_Battle_Name.getText();
		
		Integer initiation = null;
		Integer cHealth = null;
		
			
		if(!cName.equals("")){
			try
			{ 
				if(app.chckbxRandomInit.isSelected())
				{
					String str = textField_Battle_Init.getText();
					if(str.contains("+")){ //if contains a +
						initiation = app.rollForStat(1, 20, 0, "", Integer.parseInt(str.replace("+","")));
					} else if (str == null || str.equals("")){ //if blank, roll d20
						initiation = app.rollForStat(1, 20, 0, "");
					}
					else initiation = new Integer(textField_Battle_Init.getText());
				} 
				else initiation = new Integer(textField_Battle_Init.getText()); 
				
			} catch (NumberFormatException e) {}
			if(textField_Battle_Health.getText().contains("d")){
				String rollInfo[] = app.getDiceRollInfo(textField_Battle_Health.getText());
				
				int diceToRoll = Integer.parseInt(rollInfo[0]);
				int diceSideToRoll = Integer.parseInt(rollInfo[1]);
				
				int postCount = Integer.parseInt(rollInfo[2]);
				String postFix = rollInfo[3];
				
				int additional = Integer.parseInt(rollInfo[4]);
				
				cHealth = app.rollForStat(diceToRoll, diceSideToRoll, postCount, postFix, additional);
				
			}else 
			 try { cHealth = new Integer(textField_Battle_Health.getText());  } catch (NumberFormatException e) {}
		
			
			model.addRow(new Object[]{ cName, initiation, cHealth, null });
			
			if(!app.chckbxKeepname.isSelected())
				textField_Battle_Name.setText(""); //if it is not selected, clear name
			if(!textField_Battle_Init.getText().contains("+"))
				textField_Battle_Init.setText(""); //if we are rolling with addition
			if(!textField_Battle_Health.getText().contains("d"))
				textField_Battle_Health.setText(""); //if we're not rolling health, keep
		}
		
	}


	protected void battleDamageApply(JTable table_Battle) {
		TableModel model = table_Battle.getModel();
		int row = table_Battle.getSelectedRow();
		
		int health = 0;
		try {
			health = (int) (model.getValueAt(row, 2));
		} catch (Exception e1) {
			System.out.println("Health is null");
		}
		int damage=0;
		try {
			damage = (int) (model.getValueAt(row, 3));
		} catch (Exception e1) {
			System.out.println("Damage is null");
		}
		
		
		try {
			model.setValueAt(health - damage,row, 2);
		} catch (Exception e) {
			System.out.println("Either Health or Damage was a null value");
		}
		
		model.setValueAt(null, row, 3);
		
		
	}


	protected void cycleBattleTurns(JTable table_Battle) {
		TableModel model = table_Battle.getModel();
		
		//Set dimensions
		Object data = new Object[model.getRowCount()][model.getColumnCount()];
		//create our clone model.
		TableModel newModel = new DefaultTableModel((Object[][]) data,
			new String[] {
				"Name", "Initiative", "Health", "Heal/Damage"
			});
		//set clone model information
		for(int i = model.getRowCount()-2; i >= 0; i--){
			newModel.setValueAt(model.getValueAt(i+1, 0), i, 0);
			newModel.setValueAt(model.getValueAt(i+1, 1), i, 1);
			newModel.setValueAt(model.getValueAt(i+1, 2), i, 2);
		}
		
		newModel.setValueAt(model.getValueAt(0, 0), model.getRowCount()-1, 0);
		newModel.setValueAt(model.getValueAt(0, 1), model.getRowCount()-1, 1);
		newModel.setValueAt(model.getValueAt(0, 2), model.getRowCount()-1, 2);
		
		//now copy clone model information over to our current model.
		
		
		
		for(int i = model.getRowCount()-1; i >= 0; i--){
			model.setValueAt(newModel.getValueAt(i, 0), i, 0);
			model.setValueAt(newModel.getValueAt(i, 1), i, 1);
			model.setValueAt(newModel.getValueAt(i, 2), i, 2);
		}
		
		//table_Battle.setModel(newModel);
		table_Battle.clearSelection(); //remove selection because the list moved.
		
	}

}
