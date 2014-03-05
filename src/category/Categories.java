package category;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import db.MyModel;
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
	
	public Categories() {
		super();
		setLayout(new MigLayout("wrap 1"));
		
		JPanel newPanel = new JPanel();
		newPanel.setLayout(new MigLayout());
		newPanel.add(labelCategory);
		newPanel.add(categoryField, "wrap");
		
		newPanel.add(buttonCreate, "wrap 30");
		buttonCreate.addActionListener(new NewCategory());
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new MigLayout());
		buttonDelete.setEnabled(false);
		
		buttonsPanel.add(buttonDelete);
		buttonsPanel.add(buttonEdit, "wrap");
		
		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new MigLayout());
		
		Table.setPreferredScrollableViewportSize(new Dimension(600,150));
		Table.setFillsViewportHeight(true);
		
		tablePanel.add(scroller, "span");
		
		try {
			Table.setModel(CategoryModel.getAllCategories());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	ListSelectionModel lsm = (ListSelectionModel) event.getSource();
	        	
	            if(lsm.isSelectionEmpty()) {
	                System.out.println("<none>");
	            } else {
	            	System.out.println(Table.getValueAt(0, 0));
	            	//int[] rows = Table.getSelectedRows();
	            	//System.out.println(Arrays.toString(rows));
	            }
	        	
	        	/*if ( Table.getSelectedRowCount() > 0) {
	        		buttonDelete.setEnabled(true);
	        		System.out.println("1");
	    		}else{
	    			buttonDelete.setEnabled(false);
	    		}*/
	        	
	            
	        }
	    });
		
		
		
		this.add(newPanel);
		this.add(buttonsPanel);
		this.add(tablePanel);
		
	}
	
	class NewCategory implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			CategoryModel.insertCategory(categoryField.getText());
			refreshContent();
		}
		
	}
	
	public void refreshContent(){
		try {
			MyModel model = CategoryModel.getAllCategories();
			model.fireTableDataChanged();
			Table.setModel(model);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}