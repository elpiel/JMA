import javax.swing.JFrame;

import java.awt.FlowLayout;

import javax.swing.JTabbedPane;

import category.Categories;
import movie.Movies;
public class Main extends JFrame{
	private static final long serialVersionUID = 8074663065485731358L;
	private JTabbedPane tab;
	
	public Main(){
		super("JMA");
		components();
	}
	
	public void components(){
		
		setLayout(new FlowLayout(FlowLayout.CENTER));
		tab = new JTabbedPane();
		
		Movies moviesTab = new Movies();
		tab.addTab("Филми",moviesTab);
		
		Categories categoriesTab = new Categories();
		tab.addTab("Категории",categoriesTab);
		add(tab);
	}
	
	public static void main(String[] args){
		Main window = new Main();
		window.setSize(700, 500);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
}
