package View;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;

public class FormPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public JTextField txtTitle;
	public JTextField txtDirector;
	public JTextField txtYear;
	public JTextField txtGenre;
	public JTextArea txtSynopsis;
	public JComboBox<String> cmbInsertPosition;
	public JButton btnSave;
	public JButton btnNew;
	public JButton btnDelete;
	public JButton btnMoveToBeginning;
	public JButton btnMoveToEnd;

	/**
	 * Create the panel.
	 */
	public FormPanel() {
		setBackground(new Color(45, 45, 45));
		setBorder(new TitledBorder(new LineBorder(new Color(90, 90, 90)), " Movie Form ", TitledBorder.LEADING, TitledBorder.TOP, new Font(Font.SANS_SERIF, Font.BOLD, 14), new Color(220, 220, 220)));
		
		JLabel lblTitle = new JLabel("Title:");
		styleLabel(lblTitle);
		txtTitle = new JTextField();
		styleTextField(txtTitle);
		
		JLabel lblDirector = new JLabel("Director:");
		styleLabel(lblDirector);
		txtDirector = new JTextField();
		styleTextField(txtDirector);
		
		JLabel lblYear = new JLabel("Year:");
		styleLabel(lblYear);
		txtYear = new JTextField();
		styleTextField(txtYear);
		
		JLabel lblGenre = new JLabel("Genre:");
		styleLabel(lblGenre);
		txtGenre = new JTextField();
		styleTextField(txtGenre);
		
		JLabel lblSynopsis = new JLabel("Synopsis:");
		styleLabel(lblSynopsis);
		txtSynopsis = new JTextArea();
		txtSynopsis.setLineWrap(true);
		txtSynopsis.setWrapStyleWord(true);
		styleTextArea(txtSynopsis);
		JScrollPane scrollPane = new JScrollPane(txtSynopsis);
		scrollPane.setBorder(null);
		
		JLabel lblInsertPosition = new JLabel("Insert Position:");
		styleLabel(lblInsertPosition);
		cmbInsertPosition = new JComboBox<>(new String[]{"Insert at End", "Insert at Beginning"});
		styleComboBox(cmbInsertPosition);
		
		btnSave = new JButton("💾 Save");
		styleButton(btnSave, new Color(0, 123, 255));
		
		btnNew = new JButton("✨ New");
		styleButton(btnNew, new Color(40, 167, 69));

		btnDelete = new JButton("🗑️ Delete");
		styleButton(btnDelete, new Color(220, 53, 69));
		
		btnMoveToBeginning = new JButton("⬆️ Move to Beginning");
		styleButton(btnMoveToBeginning, new Color(255, 193, 7));
		
		btnMoveToEnd = new JButton("⬇️ Move to End");
		styleButton(btnMoveToEnd, new Color(108, 117, 125));
		
		// --- Layout ---
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnSave, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(btnNew, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(btnDelete, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnMoveToBeginning, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(btnMoveToEnd, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(lblDirector)
								.addComponent(lblTitle)
								.addComponent(lblYear)
								.addComponent(lblGenre)
								.addComponent(lblSynopsis)
								.addComponent(lblInsertPosition))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(txtDirector)
								.addComponent(txtTitle)
								.addComponent(txtYear)
								.addComponent(txtGenre)
								.addComponent(scrollPane)
								.addComponent(cmbInsertPosition))))
					.addGap(20))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lblTitle)
						.addComponent(txtTitle, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lblDirector)
						.addComponent(txtDirector, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lblYear)
						.addComponent(txtYear, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lblGenre)
						.addComponent(txtGenre, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(lblSynopsis)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lblInsertPosition)
						.addComponent(cmbInsertPosition, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNew, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(btnMoveToBeginning, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnMoveToEnd, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
					.addGap(20))
		);
		setLayout(groupLayout);
	}

	private void styleLabel(JLabel label) {
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        label.setForeground(new Color(200, 200, 200));
    }

    private void styleTextField(JTextField textField) {
        textField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        textField.setBackground(new Color(60, 60, 60));
        textField.setForeground(new Color(220, 220, 220));
        textField.setCaretColor(Color.WHITE);
        textField.setBorder(new LineBorder(new Color(90, 90, 90), 2));
    }
    
    private void styleTextArea(JTextArea textArea) {
        textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        textArea.setBackground(new Color(60, 60, 60));
        textArea.setForeground(new Color(220, 220, 220));
        textArea.setCaretColor(Color.WHITE);
        textArea.setBorder(new LineBorder(new Color(90, 90, 90), 2));
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(bgColor.darker(), 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        comboBox.setBackground(new Color(60, 60, 60));
        comboBox.setForeground(new Color(220, 220, 220));
        comboBox.setBorder(new LineBorder(new Color(90, 90, 90), 2));
    }
}
