package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JEditorPane;
import javax.swing.JSplitPane;

import java.awt.Canvas;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.n3.nanoxml.XMLElement;
import parser.XmlParser;

public class mainfile extends JFrame {

	private JPanel contentPane;
	XmlParser prs;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainfile frame = new mainfile();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public mainfile() {
		prs = new XmlParser();
		initUI();
		
	}
	
	private void initUI(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setToolTipText("Type you`re text here");
		
		JButton btnAnalize = new JButton("Analize");
		
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		horizontalBox_1.setToolTipText("Here are the mistakes");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(horizontalBox, GroupLayout.PREFERRED_SIZE, 396, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnAnalize)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(horizontalBox_1, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(18, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(horizontalBox, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnAnalize)
						.addComponent(horizontalBox_1, GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		final JEditorPane editorPane_1 = new JEditorPane();
		editorPane_1.setEditable(false);
		JScrollPane scrollPane_1 = new JScrollPane(editorPane_1);
		horizontalBox_1.add(scrollPane_1);
		
		final JEditorPane editorPane = new JEditorPane();
		JScrollPane scrollPane = new JScrollPane(editorPane);
		horizontalBox.add(scrollPane);
		btnAnalize.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				prs.setString(editorPane.getText());
				XMLElement xl = prs.getXmlElement();
				editorPane_1.setText(prs.xmlToString(xl));
			}
		});
		JMenuBar menubar = new JMenuBar();
		
		JMenu fileM = new JMenu("File");
		fileM.setMnemonic(KeyEvent.VK_F);
		JMenu helpM = new JMenu("Help");
		helpM.setMnemonic(KeyEvent.VK_H);
		
		JMenuItem openI = new JMenuItem("Open");
		openI.setMnemonic(KeyEvent.VK_O);
		openI.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				JFileChooser filechooser = new JFileChooser();
				FileFilter fileFilter = new FileNameExtensionFilter("text files","txt");
				filechooser.addChoosableFileFilter(fileFilter);
				
				int ret = filechooser.showDialog(contentPane, "Open file");

                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = filechooser.getSelectedFile();
                    String text = readFile(file);
                    editorPane.setText(text);
                }
			}
		});
		
		JMenuItem exitI = new JMenuItem("Exit");
		exitI.setMnemonic(KeyEvent.VK_X);
		exitI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
		
		JMenuItem aboutI = new JMenuItem("About");
		aboutI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");
			}
		});
		aboutI.setMnemonic(KeyEvent.VK_U);
		
		fileM.add(openI);
		fileM.addSeparator();
		fileM.add(exitI);
		helpM.add(aboutI);
		menubar.add(fileM);
		menubar.add(helpM);
		setJMenuBar(menubar);
		contentPane.setLayout(gl_contentPane);
	}
	
	public String readFile(File file) {

        StringBuffer fileBuffer = null;
        String fileString = null;
        String line = null;

        try {
            FileReader in = new FileReader(file);
            BufferedReader brd = new BufferedReader(in);
            fileBuffer = new StringBuffer();

            while ((line = brd.readLine()) != null) {
                fileBuffer.append(line).append(
                        System.getProperty("line.separator"));
            }

            in.close();
            fileString = fileBuffer.toString();
        } catch (IOException e) {
            return null;
        }
        return fileString;
    }
}
