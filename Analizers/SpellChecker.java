package Analizers;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import net.n3.nanoxml.IXMLElement;
import net.n3.nanoxml.IXMLParser;
import net.n3.nanoxml.IXMLReader;
import net.n3.nanoxml.StdXMLReader;
import net.n3.nanoxml.XMLElement;
import net.n3.nanoxml.XMLParserFactory;

public class SpellChecker extends AnalizerBase{
	private final String query = "http://speller.yandex.net/services/spellservice/checkText?lang=uk&text=";
	public SpellChecker(XMLElement _xml){
		super(_xml);
	}

	@Override
	public void Analize() {
		int k = 0;
		if (xml != null) {
            Enumeration<XMLElement> enumerateChildren = xml.enumerateChildren();
            while (enumerateChildren.hasMoreElements()) {
                XMLElement paragraph = enumerateChildren.nextElement();

                if (paragraph.hasChildren()) {
                    Enumeration<XMLElement> paragraphChildrens = paragraph.enumerateChildren();
                    int j = 0;
                    while (paragraphChildrens.hasMoreElements()) {
                        XMLElement sentence = paragraphChildrens.nextElement();

                        if (sentence.hasChildren()) {
                            Enumeration<XMLElement> sentenceChildrens = sentence.enumerateChildren();
                            int i = 0;
                            while (sentenceChildrens.hasMoreElements()) {
                                XMLElement words = sentenceChildrens.nextElement();

                                if (words.getName().equals("word")) {
                                    String spellError = checkSpell(words.getContent());
                                    if (spellError != null) {
                                    	 //TODO 
                                       words.setAttribute("spell", spellError);
                                    }
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

	private String checkSpell(String word) {
		if (word != null) {
            try {
                IXMLParser parser = XMLParserFactory.createDefaultXMLParser();
                if (xmlFromUrlToString(this.query + word) != null) {
                    IXMLReader reader = StdXMLReader.stringReader(xmlFromUrlToString(word));
                    parser.setReader(reader);
                    IXMLElement xml1 = (IXMLElement) parser.parse();

                    Enumeration<XMLElement> elementEnumeration = xml1.getChildrenNamed("error").elements();
                    ArrayList resultArray = new ArrayList();

                    while (elementEnumeration.hasMoreElements()) {
                        XMLElement prompt = elementEnumeration.nextElement();

                        for (int i = 0; i < prompt.getChildrenCount(); i++) {
                            if (prompt.getChildAtIndex(i).getName().equals("s")) {
                                resultArray.add(prompt.getChildAtIndex(i).getContent());
                            }
                        }
                    }

                    if (resultArray.size() > 0) {
                        String errorMessage = "Варiанти написання слова: ";
                        for  (int i = 0; i < resultArray.size(); i++) {
                            errorMessage += resultArray.get(i) + ", ";
                        }
                        errorMessage = errorMessage.substring(0, errorMessage.length() - 2);
                        errorMessage += ".";
                        return errorMessage;
                    } else {
                        return null;
                    }
                }
            } catch (Exception e) {
                
            }
        }
        return null;
	}

	private String xmlFromUrlToString(String word) {
		try {
            URL sourceUrl = new URL(this.query + URLEncoder.encode(word, "UTF-8"));
            InputStream inputStream = sourceUrl.openStream();
            byte[] buffer = new byte[1024];
            int read;
            StringBuilder stringBuilder = new StringBuilder();
            while ((read = inputStream.read(buffer, 0, buffer.length)) != -1) {
                stringBuilder.append(new String(buffer, 0, read, Charset.forName("UTF-8")));
            }
            String resultString = stringBuilder.toString();
            return resultString;
        } catch (Exception e) {
            
        }

		return null;
	}

}
