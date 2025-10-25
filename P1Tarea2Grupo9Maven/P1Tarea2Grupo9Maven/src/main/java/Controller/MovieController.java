package Controller;

import Model.LinkedList;
import Model.Movie;
import View.MainWindow;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class MovieController implements ActionListener, ListSelectionListener, DocumentListener {

    private MainWindow view;
    private LinkedList model;

    public MovieController(MainWindow view, LinkedList model) {
        this.view = view;
        this.model = model;
        
        // Register listeners
        this.view.formPanel.btnSave.addActionListener(this);
        this.view.formPanel.btnNew.addActionListener(this);
        this.view.formPanel.btnDelete.addActionListener(this);
        this.view.formPanel.btnMoveToBeginning.addActionListener(this);
        this.view.formPanel.btnMoveToEnd.addActionListener(this);
        this.view.listPanel.movieTable.getSelectionModel().addListSelectionListener(this);
        this.view.listPanel.txtSearch.getDocument().addDocumentListener(this);
        this.view.listPanel.cmbSearchBy.addActionListener(this);

        // Update table (will be empty at start)
        updateTable();
    }

    // --- Listeners ---

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == view.formPanel.btnSave) {
            saveMovie();
        } else if (source == view.formPanel.btnNew) {
            clearForm();
        } else if (source == view.formPanel.btnDelete) {
            deleteMovie();
        } else if (source == view.formPanel.btnMoveToBeginning) {
            moveToBeginning();
        } else if (source == view.formPanel.btnMoveToEnd) {
            moveToEnd();
        } else if (source == view.listPanel.cmbSearchBy) {
            filter();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = view.listPanel.movieTable.getSelectedRow();
            if (selectedRow != -1) {
                // Convert view index to model index (in case it's filtered)
                int modelRow = view.listPanel.movieTable.convertRowIndexToModel(selectedRow);
                String title = (String) view.listPanel.tableModel.getValueAt(modelRow, 0);
                
                Movie movie = model.findByTitle(title);
                if (movie != null) {
                    fillForm(movie);
                }
            }
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        filter();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        filter();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        filter();
    }

    // --- Business Logic Methods ---

    private void saveMovie() {
        String title = view.formPanel.txtTitle.getText().trim();
        String director = view.formPanel.txtDirector.getText().trim();
        String yearStr = view.formPanel.txtYear.getText().trim();
        String genre = view.formPanel.txtGenre.getText().trim();
        String synopsis = view.formPanel.txtSynopsis.getText();

        if (title.isEmpty() || director.isEmpty() || yearStr.isEmpty() || genre.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please complete all fields (Title, Director, Year, Genre).", "Incomplete Fields", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int year;
        try {
            if (!Pattern.matches("\\d{4}", yearStr)) {
                JOptionPane.showMessageDialog(view, "Year must be a 4-digit number.", "Invalid Year Format", JOptionPane.ERROR_MESSAGE);
                return;
            }
            year = Integer.parseInt(yearStr);
            if (year < 1888 || year > java.time.Year.now().getValue() + 1) {
                JOptionPane.showMessageDialog(view, "Please enter a valid year (between 1888 and present).", "Invalid Year", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Year must be a valid number.", "Invalid Year Format", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Movie existingMovie = model.findByTitle(title);
        if (existingMovie != null && view.formPanel.txtTitle.isEditable()) {
            JOptionPane.showMessageDialog(view, "A movie with that title already exists.", "Duplicate Title", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (existingMovie != null && !view.formPanel.txtTitle.isEditable()) {
            // Update existing movie
            existingMovie.setDirector(director);
            existingMovie.setYear(year);
            existingMovie.setGenre(genre);
            existingMovie.setSynopsis(synopsis);
            JOptionPane.showMessageDialog(view, "Movie updated successfully.", "Update Successful", JOptionPane.INFORMATION_MESSAGE);
            updateTable();
        } else {
            // Create new movie and insert at selected position
            Movie newMovie = new Movie(title, director, year, genre, synopsis);
            
            // Check the selected insert position from ComboBox
            String insertPosition = (String) view.formPanel.cmbInsertPosition.getSelectedItem();
            if ("Insert at Beginning".equals(insertPosition)) {
                model.insertAtBeginning(newMovie);
            } else {
                model.insertAtEnd(newMovie);
            }
            
            JOptionPane.showMessageDialog(view, "Movie saved successfully at " + insertPosition.toLowerCase() + ".", "Save Successful", JOptionPane.INFORMATION_MESSAGE);
            updateTable();
        }

        clearForm();
    }

    private void deleteMovie() {
        int selectedRow = view.listPanel.movieTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select a movie from the list to delete.", "No Movie Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = view.listPanel.movieTable.convertRowIndexToModel(selectedRow);
        String title = (String) view.listPanel.tableModel.getValueAt(modelRow, 0);
        int response = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete the movie '" + title + "'?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            boolean deleted = model.deleteByTitle(title);
            if (deleted) {
                JOptionPane.showMessageDialog(view, "Movie deleted successfully.", "Deletion Successful", JOptionPane.INFORMATION_MESSAGE);
                updateTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(view, "Could not delete the movie.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateTable() {
        // Save current filter
        javax.swing.RowFilter<? super DefaultTableModel, ? super Integer> currentFilter = view.listPanel.sorter.getRowFilter();
        
        // Temporarily disable sorter to avoid warnings
        view.listPanel.movieTable.setRowSorter(null);
        
        view.listPanel.tableModel.setRowCount(0);
        Movie[] movies = model.getAllMovies();
        for (Movie movie : movies) {
            Object[] rowData = {
                movie.getTitle(),
                movie.getDirector(),
                movie.getYear(),
                movie.getGenre()
            };
            view.listPanel.tableModel.addRow(rowData);
        }
        
        // Reactivate sorter and restore filter
        view.listPanel.movieTable.setRowSorter(view.listPanel.sorter);
        view.listPanel.sorter.setRowFilter(currentFilter);
    }

    private void clearForm() {
        view.formPanel.txtTitle.setText("");
        view.formPanel.txtDirector.setText("");
        view.formPanel.txtYear.setText("");
        view.formPanel.txtGenre.setText("");
        view.formPanel.txtSynopsis.setText("");
        view.listPanel.movieTable.clearSelection();
        view.formPanel.txtTitle.setEditable(true);
        // Clear search field, which also resets table filter
        view.listPanel.txtSearch.setText("");
    }

    private void fillForm(Movie movie) {
        view.formPanel.txtTitle.setText(movie.getTitle());
        view.formPanel.txtDirector.setText(movie.getDirector());
        view.formPanel.txtYear.setText(String.valueOf(movie.getYear()));
        view.formPanel.txtGenre.setText(movie.getGenre());
        view.formPanel.txtSynopsis.setText(movie.getSynopsis());
        view.formPanel.txtTitle.setEditable(false);
    }
    
    private void filter() {
        String query = view.listPanel.txtSearch.getText();
        String searchBy = (String) view.listPanel.cmbSearchBy.getSelectedItem();
        int searchColumn;

        switch (searchBy) {
            case "Title":
                searchColumn = 0;
                break;
            case "Genre":
                searchColumn = 3;
                break;
            case "Year":
                searchColumn = 2;
                break;
            default:
                searchColumn = 0;
        }
        
        view.listPanel.filterTable(query, searchColumn);
    }
    
    private void moveToBeginning() {
        int selectedRow = view.listPanel.movieTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select a movie from the list to move.", "No Movie Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Get the movie title from the selected row
        int modelRow = view.listPanel.movieTable.convertRowIndexToModel(selectedRow);
        String title = (String) view.listPanel.tableModel.getValueAt(modelRow, 0);
        
        // Find the movie in the list
        Movie movie = model.findByTitle(title);
        if (movie == null) {
            JOptionPane.showMessageDialog(view, "Movie not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Remove from current position and insert at beginning
        model.deleteByTitle(title);
        model.insertAtBeginning(movie);
        
        JOptionPane.showMessageDialog(view, "Movie moved to beginning successfully.", "Move Successful", JOptionPane.INFORMATION_MESSAGE);
        updateTable();
        clearForm();
    }
    
    private void moveToEnd() {
        int selectedRow = view.listPanel.movieTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select a movie from the list to move.", "No Movie Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Get the movie title from the selected row
        int modelRow = view.listPanel.movieTable.convertRowIndexToModel(selectedRow);
        String title = (String) view.listPanel.tableModel.getValueAt(modelRow, 0);
        
        // Find the movie in the list
        Movie movie = model.findByTitle(title);
        if (movie == null) {
            JOptionPane.showMessageDialog(view, "Movie not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Remove from current position and insert at end
        model.deleteByTitle(title);
        model.insertAtEnd(movie);
        
        JOptionPane.showMessageDialog(view, "Movie moved to end successfully.", "Move Successful", JOptionPane.INFORMATION_MESSAGE);
        updateTable();
        clearForm();
    }
}
