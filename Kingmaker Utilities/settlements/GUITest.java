package settlements;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

//////////////////////////////////////////////////////// CountWords
public class GUITest extends JFrame {

    //====================================================== fields
    JTextField   _fileNameTF  = new JTextField(15);
    JTextField   _wordCountTF = new JTextField(4);
    JFileChooser _fileChooser = new JFileChooser();

    //================================================= constructor
    GUITest() {
        //... Create / set component characteristics.
        _fileNameTF.setEditable(false);
        _wordCountTF.setEditable(false);

        //... Add listeners

        //... Create content pane, layout components
        JPanel content = new JPanel();
        content.setLayout(new FlowLayout());
        content.add(new JLabel("File:"));
        content.add(_fileNameTF);
        content.add(new JLabel("Word Count:"));
        content.add(_wordCountTF);

        //... Create menu elements (menubar, menu, menu item)
        JMenuBar menubar  = new JMenuBar();
        JMenu    fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open...");
        openItem.addActionListener(new OpenAction());

        //... Assemble the menu
        menubar.add(fileMenu);
        fileMenu.add(openItem);

        //... Set window characteristics
        this.setJMenuBar(menubar);
        this.setContentPane(content);
        this.setTitle("Count Words");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();                      // Layout components.
        this.setLocationRelativeTo(null); // Center window.
    }

    //============================================= countWordsInFile
    private int countWordsInFile(File f) {

        int numberOfWords = 0;  // Count of words.

        try {
            Scanner in = new Scanner(f);

            while (in.hasNext()) {
                String word = in.next();  // Read a "token".
                numberOfWords++;
            }
            in.close();        // Close Scanner's file.

        } catch (FileNotFoundException fnfex) {
            // ... We just got the file from the JFileChooser,
            //     so it's hard to believe there's problem, but...
            JOptionPane.showMessageDialog(GUITest.this,
                        fnfex.getMessage());
        }
        return numberOfWords;
    }


    ///////////////////////////////////////////////////// OpenAction
    class OpenAction implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            //... Open a file dialog.
            int retval = _fileChooser.showOpenDialog(GUITest.this);
            if (retval == JFileChooser.APPROVE_OPTION) {
                //... The user selected a file, get it, use it.
                File file = _fileChooser.getSelectedFile();

                //... Update user interface.
                _fileNameTF.setText(file.getName());
                _wordCountTF.setText("" + countWordsInFile(file));
            }
        }
    }

    //========================================================= main
    public static void main(String[] args) {
        JFrame window = new GUITest();
        window.setVisible(true);
    }
}