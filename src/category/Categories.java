package category;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Categories extends JPanel {
	
	private static final long serialVersionUID = -7021387462856765137L;
	
	JLabel catNameLabel = new JLabel("Име на категорията:");
	JTextField catNameField = new JTextField("", 10);
	
	JButton createCat = new JButton("Създай Категория");
	
	JButton deleteCats = new JButton("Изтрий");
	JButton editCat = new JButton("Редактирай");
	
	JButton table = new JButton("Таблица ТУК");
	
	public Categories() {
		super();
		BorderLayout border1 = new BorderLayout();
		this.setLayout(border1); // Adding the Border layout
		
		JPanel panel_north = new JPanel();
		panel_north.setLayout(new GridLayout(2,2));
		
		panel_north.add(catNameLabel);
		panel_north.add(catNameField);
		
		panel_north.add(new JPanel());
		panel_north.add(createCat);
		
		this.add(panel_north, BorderLayout.NORTH);
		
		JPanel panel_south = new JPanel();
		panel_south.setLayout(new BorderLayout());
		
		JPanel panelButtons = new JPanel();
		panelButtons.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelButtons.add(editCat);
		panelButtons.add(deleteCats);
		
		
		panel_south.add(panelButtons, BorderLayout.NORTH);
		
		panel_south.add(table, BorderLayout.SOUTH);
		
		this.add(panel_south, BorderLayout.SOUTH);
	}

}
