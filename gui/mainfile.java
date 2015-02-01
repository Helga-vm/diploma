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
import javax.swing.JTextPane;
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
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.border.LineBorder;

import java.awt.Color;
import javax.swing.JProgressBar;

public class mainfile extends JFrame {

	private JPanel contentPane;
	XmlParser prs;
	private JTable table1;
	ArrayList<TextError> errList;
	XMLElement xl;
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
		setBounds(100, 100, 780, 409);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		Box horizontalBox = Box.createHorizontalBox();		
		Box horizontalBox_2 = Box.createHorizontalBox();
		
		final StyleContext sc = new StyleContext();
	    final DefaultStyledDocument doc = new DefaultStyledDocument(sc);;
	    final Style cwStyle = sc.addStyle("ConstantWidth", null);
	    StyleConstants.setForeground(cwStyle, Color.red);
	    
	    final Style defaultStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
	    /*final Style defaultStyle = sc.addStyle("Def", null);
	    StyleConstants.setForeground(defaultStyle, Color.black);
	    StyleConstants.setBold(defaultStyle, false);*/
	    
	    final JTextPane textPane = new JTextPane(doc);
	    textPane.setToolTipText("\u0412\u0456\u0434\u043A\u0440\u0438\u0439\u0442\u0435 \u0442\u0435\u043A\u0441\u0442\u043E\u0432\u0438\u0439 \u0444\u0430\u0439\u043B \u0430\u0431\u043E \u0432\u0441\u0442\u0430\u0432\u0442\u0435 \u0442\u0435\u043A\u0441\u0442 \u0434\u043B\u044F \u043F\u0435\u0440\u0435\u0432\u0456\u0440\u043A\u0438 \u0432 \u0446\u0435 \u043F\u043E\u043B\u0435");
	    textPane.setContentType("text/plain");
		JScrollPane scrollPane = new JScrollPane(textPane);
		horizontalBox.add(scrollPane);
		
		JButton btnOpen = new JButton("Відкрити");
		btnOpen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JFileChooser filechooser = new JFileChooser();
				FileFilter fileFilter = new FileNameExtensionFilter("Текстові файли","txt");
				filechooser.addChoosableFileFilter(fileFilter);
				int ret = filechooser.showDialog(contentPane, "Відкритти файл");

                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = filechooser.getSelectedFile();
                    String text = readFile(file);
                    textPane.setText(text);
                }
			}
		});
		
		JButton btnAnalize = new JButton("Аналізувати");
		btnAnalize.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				errList.clear();
				ArrayList<AnalizerBase> analizerList = new ArrayList();
				prs.setString(textPane.getText());
				xl = prs.getXmlElement();
				String l = prs.xmlToString(xl);
				doc.setCharacterAttributes(0, prs.xmlToString(xl).length(), defaultStyle, true);
				analizerList.add(new ComplexSentenceChecker(xl));
				analizerList.add(new MetricsCounter(xl));
				analizerList.add(new NumbersChecker(xl));
				analizerList.add(new EmotionsChecker(xl));
				analizerList.add(new SizeChecker(xl));
				analizerList.add(new SpellChecker(xl));
				analizerList.add(new SlangChecker(xl));
				
				if (!analizerList.isEmpty()) {
					for ( int i = 0; i < analizerList.size(); i++ ) {
						analizerList.get(i).Analize();
					}
				}
				SetErrorList(xl);
				FillTab();
			}
		});
		JButton btnClear = new JButton("Очистити");
		
		btnClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				textPane.setText("");
				errList.clear();
				DefaultTableModel model1 = (DefaultTableModel) table1.getModel();
				model1.setRowCount(0);
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(horizontalBox, GroupLayout.DEFAULT_SIZE, 739, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnClear, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
						.addComponent(btnOpen, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
						.addComponent(btnAnalize, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(horizontalBox_2, GroupLayout.PREFERRED_SIZE, 628, GroupLayout.PREFERRED_SIZE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(horizontalBox, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
					.addGap(17)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnOpen)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnClear)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAnalize)
							.addGap(94))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(horizontalBox_2, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		
		
		
	    
		table1 = new JTable();
		table1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int [] markers = new int[2];
				int row = table1.getSelectedRow();
				if (row != -1){
					TextError error1 = errList.get(row);
					if (error1._word != -1){
						markers = getWordMarkers(error1);
					}
					else if (error1._sentence != -1){
						markers = getSentanceMarkers(error1);
					}
					else {
						markers = getParagraphMarkers (error1);
					}
				}					
				doc.setCharacterAttributes(0, prs.xmlToString(xl).length(), defaultStyle, true);
				//textPane.setText(prs.xmlToString(xl));
				doc.setCharacterAttributes(markers[0], markers[1], cwStyle, false);
				textPane.setCaretPosition(markers[0]);					
			}
		});
		table1.setBorder(new LineBorder(new Color(0, 0, 0)));
		table1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"№", "Опис"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table1.getColumnModel().getColumn(0).setPreferredWidth(60);
		table1.getColumnModel().getColumn(0).setMaxWidth(60);
		table1.getColumnModel().getColumn(1).setMaxWidth(800);
		table1.getColumnModel().getColumn(1).setPreferredWidth(600);
		table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane_2 = new JScrollPane(table1);
		horizontalBox_2.add(scrollPane_2);
		
		JMenuBar menubar = new JMenuBar();
		
		JMenu fileM = new JMenu("Файл");
		fileM.setMnemonic(KeyEvent.VK_F);
		JMenu helpM = new JMenu("Допомога");
		helpM.setMnemonic(KeyEvent.VK_H);
		
		JMenuItem openI = new JMenuItem("Відкрити");
		openI.setMnemonic(KeyEvent.VK_O);
		openI.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				JFileChooser filechooser = new JFileChooser();
				FileFilter fileFilter = new FileNameExtensionFilter("Текстові файли","txt");
				filechooser.addChoosableFileFilter(fileFilter);
				int ret = filechooser.showDialog(contentPane, "Відкрити файл");

                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = filechooser.getSelectedFile();
                    String text = readFile(file);
                    textPane.setText(text);
                }
			}
		});
		
		JMenuItem exitI = new JMenuItem("Вихід");
		exitI.setMnemonic(KeyEvent.VK_X);
		exitI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
		
		JMenuItem aboutI = new JMenuItem("Про програму");
		aboutI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "ДП, Короташ О.");
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
	
	protected int[] getParagraphMarkers(TextError error1) {
		int [] marker = new int [2];
		if (xl != null) {
            String resultString = "";
            int i = 0;
            Enumeration<XMLElement> enumerateChildren = xl.enumerateChildren();
            while (enumerateChildren.hasMoreElements()) {
            	if(i == error1._paragraph){
                	marker[0] = resultString.length();
                	XMLElement paragraph = enumerateChildren.nextElement();

                    if (paragraph.hasChildren()) {
                    	Enumeration<XMLElement> paragraphChildrens = paragraph.enumerateChildren();
                        int j = 0;
                        while (paragraphChildrens.hasMoreElements()) {
                            XMLElement sentence = paragraphChildrens.nextElement();                        	
                            if (sentence.hasChildren()) {
                                Enumeration<XMLElement> sentenceChildrens = sentence.enumerateChildren();
                                int k = 0;
                                while (sentenceChildrens.hasMoreElements()) {
                                    XMLElement words = sentenceChildrens.nextElement();                         	
                                    resultString += words.getContent();
                                    k++;                         
                                }
                            }
                            if (paragraphChildrens.hasMoreElements()){
                            		resultString += " ";
                            }
                            j++;
                        }
                    }
                    if (enumerateChildren.hasMoreElements()){
                    	resultString += "\n";
                    }
                    marker[1] = resultString.length() - marker[0]+1;
                	return marker;
                }                
                XMLElement paragraph = enumerateChildren.nextElement();

                if (paragraph.hasChildren()) {
                	Enumeration<XMLElement> paragraphChildrens = paragraph.enumerateChildren();
                        int j = 0;
                        while (paragraphChildrens.hasMoreElements()) {
                            XMLElement sentence = paragraphChildrens.nextElement();                        	
                            if (sentence.hasChildren()) {
                                Enumeration<XMLElement> sentenceChildrens = sentence.enumerateChildren();
                                int k = 0;
                                while (sentenceChildrens.hasMoreElements()) {
                                    XMLElement words = sentenceChildrens.nextElement();                         	
                                    resultString += words.getContent();
                                    k++;                         
                                }
                            }
                            if (paragraphChildrens.hasMoreElements()){
                            		resultString += " ";
                            }
                            j++;
                        }
                    }
                    if (enumerateChildren.hasMoreElements()){
                    	resultString += "\n";
                    }
                    i++;
                }
            }  
		return null;
	}

	protected int[] getSentanceMarkers(TextError error1) {
		int [] marker = new int [2];
		if (xl != null) {
            String resultString = "";
            int i = 0;
            Enumeration<XMLElement> enumerateChildren = xl.enumerateChildren();
            while (enumerateChildren.hasMoreElements()) {
                XMLElement paragraph = enumerateChildren.nextElement();

                if (paragraph.hasChildren()) {
                    Enumeration<XMLElement> paragraphChildrens = paragraph.enumerateChildren();
                    int j = 0;
                    while (paragraphChildrens.hasMoreElements()) {
                        XMLElement sentence = paragraphChildrens.nextElement();
                        
                        if(i == error1._paragraph && j == error1._sentence){
                        	marker[0] = resultString.length();
                        	if (sentence.hasChildren()) {
                                Enumeration<XMLElement> sentenceChildrens = sentence.enumerateChildren();
                                int k = 0;
                                while (sentenceChildrens.hasMoreElements()) {
                                    XMLElement words = sentenceChildrens.nextElement();
                                    resultString += words.getContent();
                                    k++;   
                                }
                            }
                        	marker[1] = resultString.length() - marker[0] + 1;
                        	return marker;
                        }
                    	
                        if (sentence.hasChildren()) {
                            Enumeration<XMLElement> sentenceChildrens = sentence.enumerateChildren();
                            int k = 0;
                            while (sentenceChildrens.hasMoreElements()) {
                                XMLElement words = sentenceChildrens.nextElement();                         	
                                resultString += words.getContent();
                                k++;                         
                            }
                        }
                        if (paragraphChildrens.hasMoreElements()){
                        		resultString += " ";
                        }
                        j++;
                    }
                }
                if (enumerateChildren.hasMoreElements()){
                	resultString += "\n";
                }
                i++;
            }
        }
		return null;
	}

	protected int[] getWordMarkers(TextError error1) {
		int [] marker = new int [2];
		if (xl != null) {
            String resultString = "";
            int i = 0;
            Enumeration<XMLElement> enumerateChildren = xl.enumerateChildren();
            while (enumerateChildren.hasMoreElements()) {
                XMLElement paragraph = enumerateChildren.nextElement();

                if (paragraph.hasChildren()) {
                    Enumeration<XMLElement> paragraphChildrens = paragraph.enumerateChildren();
                    int j = 0;
                    while (paragraphChildrens.hasMoreElements()) {
                        XMLElement sentence = paragraphChildrens.nextElement();
                        
                        if (sentence.hasChildren()) {
                            Enumeration<XMLElement> sentenceChildrens = sentence.enumerateChildren();
                            int k = 0;
                            while (sentenceChildrens.hasMoreElements()) {
                                XMLElement words = sentenceChildrens.nextElement();
       
                              	if(i == error1._paragraph && j == error1._sentence && k == error1._word){
                               		marker[0] = resultString.length();
                               		marker[1] = words.getContent().length();
                               		return marker;
                               	}
                               	else{
                                    resultString += words.getContent();
                                    k++;
                                }    
                            }
                        }
                        if (paragraphChildrens.hasMoreElements()){
                        		resultString += " ";
                        }
                        j++;
                    }
                }
                if (enumerateChildren.hasMoreElements()){
                	resultString += "\n";
                }
                i++;
            }

        }
		return null;
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
                            Enumeration<XMLElement> sentenceChildrens = sentence.enumerateChildren();
                            int i = 0;
                            while (sentenceChildrens.hasMoreElements()) {
                                XMLElement words = sentenceChildrens.nextElement();
                                
                                if (words.hasAttribute("numbers")) {
                                    errList.add(new TextError(words.getAttribute("numbers"), k, j, i));
                                }
                                if (words.hasAttribute("slang")) {
                                 	errList.add(new TextError(words.getAttribute("slang"), k, j, i));
                                }
                                if (words.hasAttribute("spell")) {
                                  	errList.add(new TextError(words.getAttribute("spell"), k, j, i));
                                }
                                i++;    
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
		Object [][] rowData = new Object[errList.size()][2];
		for (int i=0; i<errList.size();i++){
			rowData[i][0]= i+1;
			rowData[i][1]= errList.get(i)._errmsg;
		}
		table1.setModel(new DefaultTableModel(
				rowData,
				new String[] {
					"№", "Опис"
				}
			) {
				boolean[] columnEditables = new boolean[] {
					true, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
		table1.getColumnModel().getColumn(0).setPreferredWidth(60);
		table1.getColumnModel().getColumn(0).setMaxWidth(60);
		table1.getColumnModel().getColumn(1).setMaxWidth(800);
		table1.getColumnModel().getColumn(1).setPreferredWidth(600);		
	}
}

