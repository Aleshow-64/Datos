package View;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.LayoutStyle;
import javax.swing.RowFilter;

public class ListPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public JTable movieTable;
	public DefaultTableModel tableModel;
	public JTextField txtSearch;
	public JComboBox<String> cmbSearchBy;
	public TableRowSorter<DefaultTableModel> sorter;

	/**
	 * Create the panel.
	 */
	public ListPanel() {
		setBackground(new Color(45, 45, 45));
		setBorder(new TitledBorder(new LineBorder(new Color(90, 90, 90)), " Movie Catalog ", TitledBorder.LEADING, TitledBorder.TOP, new Font(Font.SANS_SERIF, Font.BOLD, 14), new Color(220, 220, 220)));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().setBackground(new Color(55, 55, 55));
		scrollPane.setBorder(new LineBorder(new Color(90, 90, 90)));
		
		JLabel lblSearch = new JLabel("🔍 Search by:");
		lblSearch.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        lblSearch.setForeground(new Color(200, 200, 200));
        
        cmbSearchBy = new JComboBox<>();
        cmbSearchBy.setModel(new DefaultComboBoxModel<>(new String[] {"Title", "Genre", "Year"}));
        cmbSearchBy.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        
		txtSearch = new JTextField();
		txtSearch.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        txtSearch.setBackground(new Color(60, 60, 60));
        txtSearch.setForeground(new Color(220, 220, 220));
        txtSearch.setCaretColor(Color.WHITE);
        txtSearch.setBorder(new LineBorder(new Color(90, 90, 90), 2));
		txtSearch.setColumns(10);
		
		String[] columnNames = {"Title", "Director", "Year", "Genre"};
		tableModel = new DefaultTableModel(columnNames, 0) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		movieTable = new JTable(tableModel) {
            private static final long serialVersionUID = 1L;

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (isRowSelected(row)) {
                    c.setBackground(new Color(0, 123, 255));
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(row % 2 == 0 ? new Color(55, 55, 55) : new Color(50, 50, 50));
                    c.setForeground(new Color(220, 220, 220));
                }
                return c;
            }
        };
        
        movieTable.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        movieTable.setRowHeight(32);
        movieTable.setShowGrid(false);
        movieTable.setIntercellSpacing(new Dimension(0, 0));
        
        JTableHeader header = movieTable.getTableHeader();
        header.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        header.setBackground(new Color(30, 30, 30));
        header.setForeground(new Color(0, 123, 255));
        header.setReorderingAllowed(false);
        header.setBorder(new LineBorder(new Color(90, 90, 90)));

		scrollPane.setViewportView(movieTable);
		
		// Sorter for table
		sorter = new TableRowSorter<>(tableModel);
		movieTable.setRowSorter(sorter);
		
		// --- Layout ---
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblSearch)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(cmbSearchBy, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(txtSearch)))
					.addGap(20))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(15)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lblSearch)
						.addComponent(cmbSearchBy, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
					.addGap(20))
		);
		setLayout(groupLayout);
	}
	
	public void filterTable(String query, int searchColumn) {
        if (query.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query, searchColumn));
        }
    }
}
