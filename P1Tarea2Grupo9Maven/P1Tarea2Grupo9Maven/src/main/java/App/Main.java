package App;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import Controller.MovieController;
import Model.LinkedList;
import View.MainWindow;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
                    // Try to set Nimbus Look and Feel
                    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                } catch (Exception e) {
                    try {
                        // If Nimbus fails, try system L&F
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
				
				// 1. Create Model
				LinkedList model = new LinkedList();
				
				// 2. Create View
				MainWindow view = new MainWindow();
				
				// 3. Create Controller to connect Model and View
				new MovieController(view, model);
				
				// 4. Make View visible
				view.setVisible(true);
			}
		});
	}
}
