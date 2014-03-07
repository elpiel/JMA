package movie;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;

public class Movies extends JPanel{

	private static final long serialVersionUID = 5868937736280780583L;
	JLabel labelMovieName = new JLabel("Име на филма:");
	JLabel labelTrailer = new JLabel("Трайлър:");
	JLabel labelYear = new JLabel("Година:");
	JLabel labelDescription = new JLabel("Описание:");
	JLabel labelCategories = new JLabel("Категория:");
	
	JTextField movieName = new JTextField(10);
	JTextField movieTrailer = new JTextField(10);
	JTextField movieYear = new JTextField(5);
	JTextField searchName = new JTextField("Заглавие на филма",15);
	JTextArea movieDescription = new JTextArea(4, 30);
	
	Object[] categories = {"Екшън","Комедия","Ужас","Драма","Криминален","Спортен"};
	JList<Object> listCategories = new JList<Object>(categories);
	JScrollPane scrollCategories = new JScrollPane(listCategories);
	
	JComboBox<Object> dropDownChoice = new JComboBox<Object>(categories);
	
	JButton buttonAdd = new JButton("Запиши");
	JButton buttonSearch = new JButton("Търси");
	
	JButton buttonDelete = new JButton("Изтрий");
	JButton buttonEdit = new JButton("Редактрирай");
	
	JTable dataTable = new JTable();
	JScrollPane scrollerTable = new JScrollPane(dataTable);
	
	public Movies(){
		super();
		setLayout(new MigLayout());

		JPanel movieInfoPanel = new JPanel();
		movieInfoPanel.setLayout(new MigLayout());
		
		movieInfoPanel.add(labelMovieName);
		movieInfoPanel.add(movieName, "wrap");
		
		movieInfoPanel.add(labelTrailer);
		movieInfoPanel.add(movieTrailer, "wrap");
		
		movieInfoPanel.add(labelYear);
		movieInfoPanel.add(movieYear, "wrap");
		
		movieInfoPanel.add(labelDescription);
		movieInfoPanel.add(movieDescription, "wrap");
		
		movieInfoPanel.add(labelCategories);
		listCategories.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listCategories.setLayoutOrientation(JList.VERTICAL);
		listCategories.setVisibleRowCount(4);;
		movieInfoPanel.add(scrollCategories, "wrap");
		
		
		movieInfoPanel.add(buttonAdd, "wrap 30");
		
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		searchPanel.add(searchName);
		searchPanel.add(dropDownChoice);
		searchPanel.add(buttonSearch);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new MigLayout());
		buttonsPanel.add(buttonDelete);
		buttonsPanel.add(buttonEdit, "wrap");
		
		JPanel tablePanel = new JPanel();
		dataTable.setPreferredScrollableViewportSize(new Dimension(600,150));
		dataTable.setFillsViewportHeight(true);
		tablePanel.add(scrollerTable);
		
		try {
			dataTable.setModel(MovieModel.getAllMovies());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		dataTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	/*ListSelectionModel lsm = (ListSelectionModel) event.getSource();
	            if(lsm.isSelectionEmpty()) {
	                System.out.println("<none>");
	            } else {
	            	System.out.println(Table.getValueAt(0, 0));
	            	//int[] rows = Table.getSelectedRows();
	            	//System.out.println(Arrays.toString(rows));
	            }*/
	        	int rowCount = dataTable.getSelectedRowCount();
	        	
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
		
		this.add(movieInfoPanel, "wrap");
		this.add(searchPanel, "wrap");
		this.add(buttonsPanel, "wrap");
		this.add(tablePanel, "wrap");
	}
}