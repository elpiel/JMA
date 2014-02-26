package movie;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
//import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//import visual.Table;

//import visual.Main.ButtonClick;

public class Movies extends JFrame {
	
	JLabel movieNameLabel = new JLabel("Име на филма:");
	JTextField movieNameField = new JTextField("", 10);
	
	JLabel movieTrailerLabel = new JLabel("Трейлър:");
	JTextField movieTrailerField = new JTextField("", 30);
	
	JLabel movieDescLabel = new JLabel("Описание:");
	JTextArea movieDescArea = new JTextArea(5, 10);
	
	
	
	JLabel movieCategoriesLabel = new JLabel("Категория");
	String[] categoriesArr = { "new", "old", "black" };
	JComboBox<String> movieCategoriesBox = new JComboBox<String>(categoriesArr);
	
	JButton createButton = new JButton("Създай");
	
	
	public Movies() {
		super();
		BorderLayout border1 = new BorderLayout();
		this.setLayout(border1); // Adding the Border layout
		this.setSize(600, 600); // size of JFrame
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(4,2));
		//panel1.setSize(200, 200);
		panel1.add(movieNameLabel);
		panel1.add(movieNameField);
		
		panel1.add(movieTrailerLabel);
		panel1.add(movieTrailerField);
		
		panel1.add(movieDescLabel);
		panel1.add(movieDescArea);
		
		panel1.add(movieCategoriesLabel);
		panel1.add(movieCategoriesBox);
		
		this.add(panel1, BorderLayout.NORTH);
		
		
		/*this.add(label); // adding the label of the text field
		this.add(textField); // adding the text field
		this.add(box); // adding the Combobox
		this.add(button); // Adding button
		
		//ActionListener l;
		button.addActionListener(new ButtonClick());*/
		
	}
	
	public static void main(String[] args) {
		Movies jFrame = new Movies(); // Starting JFrame
		
		// set it visible for the user
		jFrame.setVisible(true);
		
		// the location to be in the middle of the screen
		jFrame.setLocationRelativeTo(null);
		
		// what the X to do
		jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE); // Exit when X clicked

	}
}
