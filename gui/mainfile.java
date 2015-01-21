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
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JCheckBox;

import net.n3.nanoxml.XMLElement;
import parser.XmlParser;
import Analizers.*;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class mainfile extends JFrame {

	private JPanel contentPane;
	XmlParser prs;
	private JTable table1;
	ArrayList<TextError> errList;
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
		errList = new ArrayList<TextError>();
		initUI();
		
	}
	
	private void initUI(){

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 494);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setToolTipText("Type you`re text here");
		
		JButton btnAnalize = new JButton("Analize");
		
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		horizontalBox_1.setToolTipText("Here are the mistakes");
		
		final JCheckBox chckbxComplexity = new JCheckBox("Complexity");
		
		final JCheckBox chckbxMetrics = new JCheckBox("Metrics");
		
		final JCheckBox chckbxNumbers = new JCheckBox("Numbers");
		
		final JCheckBox chckbxEmotions = new JCheckBox("Emotions");
		
		final JCheckBox chckbxSizes = new JCheckBox("Sizes");
		
		Box horizontalBox_2 = Box.createHorizontalBox();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(chckbxComplexity)
								.addComponent(chckbxMetrics)
								.addComponent(chckbxSizes))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(horizontalBox_1, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE))
						.addComponent(horizontalBox, GroupLayout.PREFERRED_SIZE, 396, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(chckbxNumbers)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
									.addComponent(btnAnalize)
									.addComponent(chckbxEmotions)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(horizontalBox_2, GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(14, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(horizontalBox, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
					.addGap(15)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(chckbxComplexity)
						.addComponent(horizontalBox_1, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(chckbxMetrics)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxSizes)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxNumbers)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxEmotions)
							.addPreferredGap(ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
							.addComponent(btnAnalize))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(horizontalBox_2, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
							.addGap(40))))
		);
		
		table1 = new JTable();
		table1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Number", "Problem"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				true, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table1.getColumnModel().getColumn(1).setPreferredWidth(367);
		table1.getColumnModel().getColumn(1).setMinWidth(20);
		table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		horizontalBox_2.add(table1);
		
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
				ArrayList<AnalizerBase> analizerList = new ArrayList();
				prs.setString(editorPane.getText());
				XMLElement xl = prs.getXmlElement();
				if (chckbxComplexity.isSelected()){
					analizerList.add(new ComplexSentenceChecker(xl));
				} 
				if( chckbxMetrics.isSelected() ) {
					analizerList.add(new MetricsCounter(xl));
				}
				if ( chckbxNumbers.isSelected()){
					analizerList.add(new NumbersChecker(xl));
				}
				if (chckbxEmotions.isSelected()){
					analizerList.add(new EmotionsChecker(xl));
				}
				if (chckbxSizes.isSelected()) {
					analizerList.add(new SizeChecker(xl));
				}
				if (!analizerList.isEmpty()) {
					for ( int i = 0; i < analizerList.size(); i++ ) {
						analizerList.get(i).Analize();
					}
				}
				SetErrorList(xl);
				FillTab();
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
	
	private void SetErrorList(XMLElement xml) {
		// TODO Auto-generated method stub
		int k = 0;
		if (xml != null) {
            Enumeration<XMLElement> enumerateChildren = xml.enumerateChildren();
            while (enumerateChildren.hasMoreElements()) {
                XMLElement paragraph = enumerateChildren.nextElement();
                int j=0;
                if ( paragraph.hasAttribute("SentCount") ){
                	errList.add(new TextError(paragraph.getAttribute("SentCount"), k, -1, -1));
                }
                if (paragraph.hasChildren()) {
                    Enumeration<XMLElement> paragraphChildrens = paragraph.enumerateChildren();

                    while (paragraphChildrens.hasMoreElements()) {
                        XMLElement sentence = paragraphChildrens.nextElement();
                        if ( sentence.hasAttribute("complexity")) {
                        	errList.add(new TextError(sentence.getAttribute("complexity"), k, j, -1));
                        }
                        if (sentence.hasAttribute("emotions")) {
                        	errList.add(new TextError(sentence.getAttribute("emotions"), k, j, -1));
                        }
                        if (sentence.hasAttribute("Length")) {
                        	errList.add(new TextError(sentence.getAttribute("Length"), k, j, -1));
                        }
                        if (sentence.hasChildren()) {
                            Enumeration<XMLElement> sentenceChildrens = paragraph.enumerateChildren();

                            while (sentenceChildrens.hasMoreElements()) {
                                XMLElement words = sentenceChildrens.nextElement();

                                for (int i = 0; i < words.getChildrenCount(); i++) {
                                    if (words.hasAttribute("numbers")) {
                                        errList.add(new TextError(words.getAttribute("numbers"), k, j, i));
                                    }
                                    if (words.hasAttribute("slang")) {
                                    	errList.add(new TextError(words.getAttribute("numbers"), k, j, i));
                                    }
                                    //TODO attributes for spellchecker
                                }
                            }
                        }
                    
                        j++;
                    }
                }
                k++;
            }
		}
	}
	private void FillTab() {
		// TODO Auto-generated method stub
		
	}
}

