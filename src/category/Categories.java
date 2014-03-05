package category;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class Categories extends JPanel{
	
	private static final long serialVersionUID = 7806750679143450555L;
	JLabel labelCategory = new JLabel("Име на категорията:");
	JTextField categoryField = new JTextField();
	
	JTable Table = new JTable();
	JScrollPane scroller = new JScrollPane(Table);
	
	JButton buttonCreate = new JButton("Създай категория");
	JButton buttonDelete = new JButton("Изтрий");
	JButton buttonEdit = new JButton("Редактрирай");
	
	public Categories(){
		super();
		setLayout(new GridLayout(4,1));
		
		JPanel newPanel = new JPanel();
		newPanel.setLayout(new GridLayout(3,2,1,4));
		newPanel.add(labelCategory);
		newPanel.add(categoryField);
		newPanel.add(new JPanel());
		newPanel.add(buttonCreate);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(null);
		buttonsPanel.add(buttonDelete).setBounds(10,100,90,25);
		buttonsPanel.add(buttonEdit).setBounds(120,100,110,25);
		
		JPanel tablePanel = new JPanel();
		Table.setPreferredScrollableViewportSize(new Dimension(600,150));
		Table.setFillsViewportHeight(true);
		tablePanel.add(scroller);
		
		
		add(newPanel);
		add(buttonsPanel);
		add(tablePanel);
		
	}
}