import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import category.Categories;
import movie.Movies;

class Main extends JFrame{
	
	private static final long serialVersionUID = 8793784007542786219L;
	JTabbedPane tabs = new JTabbedPane();
	
	public Main(){
		super("Java Movies Application");
		Components();
	}
	
	public void Components() {
		setLayout(new FlowLayout(FlowLayout.CENTER));

		Movies movie = new Movies();
		tabs.addTab("Филми",movie);
		add(tabs);
		
		Categories categories = new Categories();
		tabs.addTab("Категории",categories);
		add(tabs);
	}
	
	public static void main(String[] args) {
		Main window = new Main();
		window.setSize(1000,700);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
}
