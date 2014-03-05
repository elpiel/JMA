package category;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class Categories extends JPanel{
	
	private static final long serialVersionUID = 7806750679143450555L;
	JLabel labelCategory = new JLabel("Име на категорията:");
	JTextField categoryField = new JTextField(15);
	
	JTable Table = new JTable();
	JScrollPane scroller = new JScrollPane(Table);
	
	JButton buttonCreate = new JButton("Създай категория");
	JButton buttonDelete = new JButton("Изтрий");
	JButton buttonEdit = new JButton("Редактрирай");
	
	public Categories(){
		super();
		setLayout(new MigLayout("wrap 1"));
		
		JPanel newPanel = new JPanel();
		newPanel.setLayout(new MigLayout());
		newPanel.add(labelCategory);
		newPanel.add(categoryField, "wrap");
		
		newPanel.add(buttonCreate, "wrap 30");
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new MigLayout());
		buttonsPanel.add(buttonDelete);
		buttonsPanel.add(buttonEdit, "wrap");
		
		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new MigLayout());
		
		Table.setPreferredScrollableViewportSize(new Dimension(600,150));
		Table.setFillsViewportHeight(true);
		
		tablePanel.add(scroller, "span");
		
		
		this.add(newPanel);
		this.add(buttonsPanel);
		this.add(tablePanel);
		
	}
}