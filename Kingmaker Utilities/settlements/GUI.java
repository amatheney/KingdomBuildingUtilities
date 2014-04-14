package settlements;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.GridLayout;

import javax.swing.JTabbedPane;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenu;
import javax.swing.JList;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

public class GUI {

	private JFrame frmKingdomBuildingUtility;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmKingdomBuildingUtility.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmKingdomBuildingUtility = new JFrame();
		frmKingdomBuildingUtility.setTitle("Kingdom Building Utility - By James Hunter (bitwize01)");
		frmKingdomBuildingUtility.setSize(649, 483);
		
		JMenuBar menuBar = new JMenuBar();
		frmKingdomBuildingUtility.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewKingdom = new JMenuItem("New Kingdom");
		mnNewMenu.add(mntmNewKingdom);
		
		JMenuItem mntmOpen = new JMenuItem("Open...");
		mnNewMenu.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnNewMenu.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As...");
		mnNewMenu.add(mntmSaveAs);
		
		JMenuItem mntmQuit = new JMenuItem("Quit");
		mnNewMenu.add(mntmQuit);
		frmKingdomBuildingUtility.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		ImageIcon icon = createImageIcon("visualResources/bullet_black.png");
		
		JComponent panel1 = makeTextPanel("Panel #1");
		panel1.setPreferredSize(new Dimension(410, 50));
		tabbedPane.addTab("Tab 1", icon, panel1,
		                  "Does nothing");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		JComponent panel2 = makeTextPanel("Panel #2");
		panel2.setPreferredSize(new Dimension(410, 50));
		tabbedPane.addTab("Tab 2", icon, panel2,
		                  "Does twice as much nothing");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		JComponent panel3 = makeTextPanel("Panel #3");
		panel3.setPreferredSize(new Dimension(410, 50));
		tabbedPane.addTab("Tab 3", icon, panel3,
		                  "Still does nothing");
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

		JComponent panel4 = makeTextPanel(
		        "Panel #4 (has a preferred size of 410 x 50).");
		panel4.setPreferredSize(new Dimension(410, 50));
		tabbedPane.addTab("Tab 4", icon, panel4,
		                      "Does nothing at all");
		tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
		
		frmKingdomBuildingUtility.getContentPane().add(tabbedPane, "2, 2, fill, fill");
	}
	
    protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        panel.setLayout(null);
        JLabel filler = new JLabel("");
        filler.setBounds(0, 0, 622, 388);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.add(filler);
        
        JList SampleRoomList = new JList();
        SampleRoomList.setBounds(0, 36, 148, 341);
        panel.add(SampleRoomList);
        
        JLabel lblRooms = new JLabel("Sample Rooms");
        lblRooms.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
        lblRooms.setBounds(10, 11, 119, 14);
        panel.add(lblRooms);
        
        JTextArea txtrDescription = new JTextArea();
        txtrDescription.setEditable(false);
        txtrDescription.setText("Description");
        txtrDescription.setLineWrap(true);
        txtrDescription.setBounds(185, 36, 262, 341);
        panel.add(txtrDescription);
        return panel;
    }

	/** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = GUI.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

	private static void addPopup(Component component,JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
