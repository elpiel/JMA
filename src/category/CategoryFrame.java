package category;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class CategoryFrame extends JFrame {

	private static final long serialVersionUID = -1463729941742096638L;
	
	JLabel labelCategory = new JLabel("Име на категорията:");
	JTextField categoryField = new JTextField(15);
	
	JButton buttonEdit = new JButton("Редактирай");
	
	public CategoryFrame(int cat_id) {
		super();
		
		ResultSet category = CategoryModel.getCategoryById(cat_id);
		
		String cat_name = "";
		try {
			cat_name = category.getString("cat_name");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		categoryField.setText(cat_name);
		
		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JPanel newPanel = new JPanel();
		newPanel.setLayout(new MigLayout());
		
		newPanel.add(new JLabel("Редакция на Категория #" + cat_id ), "span");
		
		newPanel.add(labelCategory);
		newPanel.add(categoryField, "wrap");
		
		newPanel.add(buttonEdit, "wrap 30");
		buttonEdit.addActionListener(new EditCatAction(cat_id));
		
		this.add(newPanel);
		
		categoryField.requestFocusInWindow();
	}
	
	class EditCatAction implements ActionListener{
		
		int cat_id;
		
		public EditCatAction( int cat_id ) {
			this.cat_id = cat_id;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String newCat_name = categoryField.getText();
			CategoryModel.updateCategory(this.cat_id, newCat_name);
			dispose(); // from the Category Frame so that the windows Closes and Refresh table
		}
		
	}
	
}
