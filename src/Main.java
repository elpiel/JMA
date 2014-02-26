import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JTabbedPane;
import movie.Movies;
public class Main extends JFrame{
	private JTabbedPane tab;
	
	public Main(){
		super("JMA");
		components();
	}
	
	public void components(){
		Movies movieTab = new Movies();
		setLayout(new FlowLayout(FlowLayout.LEFT));
		tab = new JTabbedPane();
		tab.addTab("Филми",movieTab);
		add(tab);
	}
	
	public static void main(String[] args){
		Main window = new Main();
		window.setSize(600, 500);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
}
