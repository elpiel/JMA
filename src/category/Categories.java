package category;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
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
		buttonDelete.addActionListener(new DeleteCatsListener());
		buttonsPanel.add(buttonDelete);
		
		buttonEdit.setEnabled(false);
		buttonEdit.addActionListener(new EditCatListener());
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
	        	/*ListSelectionModel lsm = (ListSelectionModel) event.getSource();
	            if(lsm.isSelectionEmpty()) {
	                System.out.println("<none>");
	            } else {
	            	System.out.println(Table.getValueAt(0, 0));
	            	//int[] rows = Table.getSelectedRows();
	            	//System.out.println(Arrays.toString(rows));
	            }*/
	        	int rowCount = Table.getSelectedRowCount();
	        	
	        	if ( rowCount > 0) {
	        		buttonDelete.setEnabled(true);
	    		}else{
	    			buttonDelete.setEnabled(false);
	    		}
	        	
	        	if( rowCount == 1 ) {
        			buttonEdit.setEnabled(true);
        		}else{
        			buttonEdit.setEnabled(false);
        		}
	        }
	    });
		
		this.add(newPanel);
		this.add(buttonsPanel);
		this.add(tablePanel);
	}
	
	class DeleteCatsListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			int[] rows = Table.getSelectedRows();
			
			// creating array of X number of integers in memory
			int[] cat_ids = new int[Table.getSelectedRowCount()];
			
			for(int i = 0; i < Table.getSelectedRowCount(); i++) {
				// every ID is set to the array
				cat_ids[i] = (int) Table.getValueAt(rows[i], 0); // this is First column (cat_id) from Table
			}
			
			// delete these IDs
			CategoryModel.deleteCategories(cat_ids);
			
			refreshContent();
		}
		
	}
	
	class EditCatListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			int cat_id = (int) Table.getValueAt(Table.getSelectedRow(), 0);
			
			CategoryFrame catFrame = new CategoryFrame(cat_id);
			catFrame.setSize(400,300);
			catFrame.setVisible(true);
			catFrame.setLocationRelativeTo(null);
			
			catFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			catFrame.addWindowListener(new RefreshOnCloseWindowListener());
			
		}
		
	}
	
	class RefreshOnCloseWindowListener extends WindowAdapter {
	    public void windowClosed(WindowEvent e) {
	    	refreshContent();
	    }
	}
	
	class NewCategory implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println( CategoryModel.insertCategory(categoryField.getText()) );
			categoryField.setText("");
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