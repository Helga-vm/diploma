package Analizers;

import java.io.File;
import java.util.Enumeration;

import net.n3.nanoxml.IXMLElement;
import net.n3.nanoxml.IXMLParser;
import net.n3.nanoxml.IXMLReader;
import net.n3.nanoxml.StdXMLReader;
import net.n3.nanoxml.XMLElement;
import net.n3.nanoxml.XMLParserFactory;

public class SlangChecker extends AnalizerBase{
	private final String fileName = System.getProperty("user.dir") + File.separator + "lib" + File.separator + "slang.xml";
	public SlangChecker(XMLElement _xml){
		super(_xml);
	}

	@Override
	protected void Analize() {
		// TODO Auto-generated method stub
		if (xml != null) {
            Enumeration<XMLElement> enumerateChildren = xml.enumerateChildren();
            while (enumerateChildren.hasMoreElements()) {
                XMLElement paragraph = enumerateChildren.nextElement();

                if (paragraph.hasChildren()) {
                    Enumeration<XMLElement> paragraphChildrens = paragraph.enumerateChildren();

                    while (paragraphChildrens.hasMoreElements()) {
                        XMLElement sentence = paragraphChildrens.nextElement();

                        if (sentence.hasChildren()) {
                            Enumeration<XMLElement> sentenceChildrens = paragraph.enumerateChildren();

                            while (sentenceChildrens.hasMoreElements()) {
                                XMLElement words = sentenceChildrens.nextElement();

                                for (int i = 0; i < words.getChildrenCount(); i++) {
                                    if (words.getChildAtIndex(i).getName().equals("word")) {
                                        String slangError = checkSlang(words.getChildAtIndex(i).getContent());
                                        if (slangError != null) {
                                            words.getChildAtIndex(i).setAttribute("error-SlangChecker", slangError);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
		}
	}

	private String checkSlang(String word) {
		// TODO Auto-generated method stub
		if (word != null) {
            try {
                IXMLParser parser = XMLParserFactory.createDefaultXMLParser();
                IXMLReader reader = StdXMLReader.fileReader(fileName);
                parser.setReader(reader);
                IXMLElement xml = (IXMLElement) parser.parse();

                Enumeration<XMLElement> elementEnumeration = xml.getChildrenNamed("slang").elements();
                Enumeration<XMLElement> dictionaryWordsEnum = elementEnumeration.nextElement().enumerateChildren();

                while (dictionaryWordsEnum.hasMoreElements()) {
                    XMLElement dictionaryWords = dictionaryWordsEnum.nextElement();

                    if (dictionaryWords.getName().equals("word") && dictionaryWords.getContent().equals(word.toLowerCase())) {
                        return "—хоже на сленг, мабуть ви мали на уваз≥: " + dictionaryWords.getAttribute("right", "");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		return null;
	}

}
