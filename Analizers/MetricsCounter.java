package diploma.Analizers;

import java.util.Enumeration;

import net.n3.nanoxml.XMLElement;

public class MetricsCounter extends AnalizerBase{
	private int parCount;
	private int sentCount;
	private int wordCount;
	
	public MetricsCounter(XMLElement _xml) {
		super(_xml);
		parCount = sentCount = wordCount = 0;
	}

	@Override
	protected void Analize() {
		if (xml != null) {
            Enumeration<XMLElement> enumRootChild = xml.enumerateChildren();
            while (enumRootChild.hasMoreElements()) {
                XMLElement paragraph = enumRootChild.nextElement();
                parCount++;

                Enumeration<XMLElement> enumParagraphChild = paragraph.enumerateChildren();
                while (enumParagraphChild.hasMoreElements()) {
                    XMLElement sentence = enumParagraphChild.nextElement();
                    sentCount++;

                    Enumeration<XMLElement> enumSentenceChild = sentence.enumerateChildren();
                    while (enumSentenceChild.hasMoreElements()) {
                        XMLElement word = enumSentenceChild.nextElement();
                        if (word.getName().equals("word") && !word.getContent().equals("")) {
                            wordCount++;
                        }
                    }
                }
            }
		}
	}

	public XMLElement getResult() {
		xml.setAttribute("paragraphs_count", new Integer(parCount).toString());
		xml.setAttribute("sentences_ount", new Integer(sentCount).toString());
		xml.setAttribute("words_count", new Integer(wordCount).toString());
		return this.xml;
	}
}
