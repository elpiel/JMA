package movie;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import category.CategoryModel;
import net.miginfocom.swing.MigLayout;

public class MovieFrame extends JFrame {

	private static final long serialVersionUID = -1463729941742096638L;
	
	JLabel labelMovieName = new JLabel("Име на филма:");
	JLabel labelMovieTrailer = new JLabel("Трайлър:");
	JLabel labelMovieDate = new JLabel("Година:");
	JLabel labelMovieDesc = new JLabel("Описание:");
	JLabel labelMovieCats = new JLabel("Категория:");
	
	JTextField movieName = new JTextField(10);
	JTextField movieTrailer = new JTextField(10);
	JTextField movieDate = new JTextField(10);
	JTextArea movieDesc = new JTextArea(4, 30);
	
	Object[] categories = CategoryModel.getAllCatsNames();
	static int[] categoriesIds = CategoryModel.getAllCatsIds();
	JList<Object> listCategories = new JList<Object>(categories);
	JScrollPane scrollCategories = new JScrollPane(listCategories);
	
	JButton buttonEdit = new JButton("Редактирай");
	
	public MovieFrame(int m_id) {
		super();
		
		ResultSet movie = MovieModel.getMovieById(m_id);
		
		String m_name = "", m_trailer = "", m_desc = "", m_date = "";
		try {
			m_name = movie.getString("m_name");
			m_trailer = movie.getString("m_trailer");
			m_desc = movie.getString("m_desc");
			m_date = movie.getString("m_date");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		int[] m_categories = getSelectedCategoriesIndexes(MovieModel.getMovieCatsById(m_id));
		
		for ( int i=0; i < m_categories.length; i++ ) {
			System.out.println(m_categories[i]);
		}
		
		
		movieName.setText(m_name);
		movieTrailer.setText(m_trailer);
		movieDesc.setText(m_desc);
		try {
			movieDate.setText(MovieModel.DateFormatterForField(m_date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JPanel movieInfoPanel = new JPanel();
		movieInfoPanel.setLayout(new MigLayout());
		
		movieInfoPanel.add(new JLabel("Редакция на Филм #" + m_id ), "span");
		
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
		
		//listCategories.setSelectedIndices();
		listCategories.setSelectedIndices(m_categories);
		
		movieInfoPanel.add(scrollCategories, "wrap");
		
		movieInfoPanel.add(buttonEdit, "wrap 30");
		buttonEdit.addActionListener(new EditCatAction(m_id));
		
		this.add(movieInfoPanel);
		
		movieName.requestFocusInWindow();
	}
	
	public int[] getSelectedCategoriesIndexes(ResultSet movieCategories) {
		
		List<Integer> selectedIndexes = new ArrayList<Integer>();
		
		List<Object> catsList = Arrays.asList(this.categories);
		try {
			while ( movieCategories.next() ) {
				String catName = movieCategories.getString("cat_name");
				System.out.println(catName);
				if ( catsList.contains(catName) == true) {
					selectedIndexes.add(catsList.indexOf(catName));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		Object[] objectArray = selectedIndexes.toArray();
		
		int[] intArray = new int [objectArray.length];
		for( int i = 0; i < objectArray.length; i++ ) {
			intArray[i] = (int)objectArray[i];
		}
		
		return intArray;
	}
	
	class EditCatAction implements ActionListener{
		
		int m_id;
		
		public EditCatAction( int m_id ) {
			this.m_id = m_id;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			ArrayList<Integer> selectedCategories = Movies.getCatsIdsFromList(listCategories.getSelectedValuesList());
			
			MovieModel.updateMovie(m_id, movieName.getText(), movieTrailer.getText(), movieDesc.getText(), movieDate.getText(), selectedCategories);
			
			dispose(); // from the Movie Frame so that the windows Closes and Refresh table
		}
		
	}
	
}
