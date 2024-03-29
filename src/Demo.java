import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Demo extends JFrame {
    private JPanel panel;
    private JButton button1;
    private JTextField textFieldNazevDeskovky;
    private JTextArea textAreaSource;
    private JScrollPane scrollpane;

    private JFileChooser chooser = new JFileChooser(".");
    private File aktualniSoubor;

    public Demo() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        JMenuItem openItem = new JMenuItem("Open");
        openItem.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFileAndLoadContents();
            }
        });
        menu.add(openItem);

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFileContents();
            }
        });
        menu.add(saveItem);

        JMenuItem saveAsItem = new JMenuItem("Save As");
        saveAsItem.setAccelerator(KeyStroke.getKeyStroke("control shift S"));
        saveAsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAsFile();
            }
        });
        menu.add(saveAsItem);


    }



    private void selectFileAndLoadContents() {
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            loadFileContents(selectedFile);
        }
    }

    private void loadFileContents(File selectedFile) {
        textAreaSource.setText("");
        aktualniSoubor = selectedFile;
        boolean firstLine = true;
        try (
                Scanner scanner =
                        new Scanner(new BufferedReader(
                                new FileReader(selectedFile)))
        ) {
            while (scanner.hasNextLine()) {

                if (firstLine == true){
                    textAreaSource.append(scanner.nextLine());
                    firstLine = false;
                }
                textAreaSource.append("\n" + scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveFileContents() {
        try (PrintWriter writer = new PrintWriter(
                new BufferedWriter(new FileWriter(aktualniSoubor)))
        ) {
            writer.print(textAreaSource.getText());
        } catch (IOException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
    private void saveAsFile(){
        int result = chooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            saveFileAsContents(selectedFile);
        }
    }
    private void saveFileAsContents(File soubor) {
        try (PrintWriter writer = new PrintWriter(
                new BufferedWriter(new FileWriter(soubor)))
        ) {
            writer.print(textAreaSource.getText());
        } catch (IOException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public static void main(String[] args) {
        Demo d = new Demo();
        d.setContentPane(d.panel);
        d.setTitle("DemoTab");
        d.pack();
        d.setBounds(500, 500, 1280, 720);
        d.setResizable(true);
        d.setVisible(true);
        d.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
}
