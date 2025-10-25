package View;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Color;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public FormPanel formPanel;
	public ListPanel listPanel;

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setTitle("🎬 Movie Catalog");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 950, 680);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(30, 30, 30));
		contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 2, 20, 20));

		// Initialize and add panels
		formPanel = new FormPanel();
		contentPane.add(formPanel);
		
		listPanel = new ListPanel();
		contentPane.add(listPanel);
	}
}
