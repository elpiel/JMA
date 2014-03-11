package movie;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
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

import category.CategoryModel;
import db.MyModel;
import net.miginfocom.swing.MigLayout;

public class Movies extends JPanel{

	private static final long serialVersionUID = 5868937736280780583L;
	JLabel labelMovieName = new JLabel("Име на филма:");
	JLabel labelMovieTrailer = new JLabel("Трайлър:");
	JLabel labelMovieDate = new JLabel("Година:");
	JLabel labelMovieDesc = new JLabel("Описание:");
	JLabel labelMovieCats = new JLabel("Категория:");
	
	JTextField movieName = new JTextField(10);
	JTextField movieTrailer = new JTextField(10);
	JTextField movieDate = new JTextField(10);
	JTextField searchName = new JTextField("Заглавие на филма",15);
	JTextArea movieDesc = new JTextArea(4, 30);
	
	//Object[] categories = {"Екшън","Комедия","Ужас","Драма","Криминален","Спортен"};
	Object[] categories = CategoryModel.getAllCatsNames();
	static int[] categoriesIds = CategoryModel.getAllCatsIds();
	JList<Object> listCategories = new JList<Object>(categories);
	JScrollPane scrollCategories = new JScrollPane(listCategories);
	
	JComboBox<Object> dropDownChoice = new JComboBox<Object>(categories);
	
	JButton buttonAdd = new JButton("Запиши");
	JButton buttonSearch = new JButton("Търси");
	
	JButton buttonSQL1 = new JButton("Филми започващи със 'Страшен' и Дата след: 01-01-2000");
	JButton buttonSQL2 = new JButton("Филми с категория 'Екшън' и id > 3");
	
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
		
		movieInfoPanel.add(labelMovieTrailer);
		movieInfoPanel.add(movieTrailer, "wrap");
		
		movieInfoPanel.add(labelMovieDate);
		movieInfoPanel.add(movieDate, "wrap");
		
		movieInfoPanel.add(labelMovieDesc);
		movieInfoPanel.add(movieDesc, "wrap");
		
		movieInfoPanel.add(labelMovieCats);
		listCategories.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listCategories.setLayoutOrientation(JList.VERTICAL);
		listCategories.setVisibleRowCount(4);
		movieInfoPanel.add(scrollCategories, "wrap");
		
		buttonAdd.addActionListener(new NewMovieListener());
		movieInfoPanel.add(buttonAdd, "wrap 30");
		
		JPanel SQLPanel = new JPanel();
		SQLPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonSQL1.addActionListener(new SQLListener1(1));
		SQLPanel.add(buttonSQL1);
		buttonSQL2.addActionListener(new SQLListener1(2));
		SQLPanel.add(buttonSQL2);
		
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		searchName.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                searchName.setText("");
            }
        });

		//saerchName.add
		//searchName.addMouseListener();
		//searchName.addActionListener(new ClearOnClick());
		searchPanel.add(searchName);
		searchPanel.add(dropDownChoice);
		buttonSearch.addActionListener(new SearchListener());
		searchPanel.add(buttonSearch);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new MigLayout());
		buttonDelete.setEnabled(false);
		buttonsPanel.add(buttonDelete);
		
		buttonEdit.setEnabled(false);
		buttonEdit.addActionListener(new EditMovieListener());
		buttonsPanel.add(buttonEdit, "wrap");
		
		JPanel tablePanel = new JPanel();
		dataTable.setPreferredScrollableViewportSize(new Dimension(600,150));
		dataTable.setFillsViewportHeight(true);
		tablePanel.add(scrollerTable);
		
		try {
			dataTable.setModel(MovieModel.getAllMovies(""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		dataTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
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
		this.add(SQLPanel, "wrap");
		this.add(searchPanel, "wrap");
		this.add(buttonsPanel, "wrap");
		this.add(tablePanel, "wrap");
	}
	
	class RefreshOnCloseWindowListener extends WindowAdapter {
	    public void windowClosed(WindowEvent e) {
	    	refreshContent("");
	    }
	}
	
	class NewMovieListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			ArrayList<Integer> selectedCategories = getCatsIdsFromList(listCategories.getSelectedValuesList());
			
			MovieModel.insertMovie(movieName.getText(), movieTrailer.getText(), movieDesc.getText(), movieDate.getText(), selectedCategories);
			
			movieName.setText("");
			movieDesc.setText("");
			movieTrailer.setText("");
			movieDate.setText("");
			refreshContent("");
		}
	}
	
	class EditMovieListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			int m_id = (int) dataTable.getValueAt(dataTable.getSelectedRow(), 0);
			
			MovieFrame mFrame = new MovieFrame(m_id );
			mFrame.setSize(500,300);
			mFrame.setVisible(true);
			mFrame.setLocationRelativeTo(null);
			
			mFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			mFrame.addWindowListener(new RefreshOnCloseWindowListener());
			
		}
		
	}
	
	class SQLListener1 implements ActionListener{

		public int sql_number;
		
		public SQLListener1 ( int sql_number ) {
			this.sql_number = sql_number;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			refreshContent("sql"+this.sql_number);
		}	
	}
	
	class SearchListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String movieName = searchName.getText();
			int catName = dropDownChoice.getSelectedIndex();
			
			try {
				MyModel model = MovieModel.getMoviesSearch(movieName, catName);
				model.fireTableDataChanged();
				dataTable.setModel(model);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			
		}
		
	}
	
	public static ArrayList<Integer> getCatsIdsFromList(List<Object> categoriesNames) {
		Object[] catsNamesArray = categoriesNames.toArray();
		
		ArrayList<Integer> catIds = new ArrayList<Integer>();
		for (Object catName: catsNamesArray) {
			int catIndex = categoriesNames.indexOf(catName);
			catIds.add(categoriesIds[catIndex]);
		}
		
		return catIds;
	}
	
	public void refreshContent(String with_content){
		try {
			MyModel model = MovieModel.getAllMovies(with_content);
			model.fireTableDataChanged();
			dataTable.setModel(model);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}