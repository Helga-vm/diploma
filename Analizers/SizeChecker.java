package Analizers;

import java.util.Enumeration;

import net.n3.nanoxml.XMLElement;

public class SizeChecker extends AnalizerBase{
	private int paragraphMaxSize, sentenceMaxSize, sentencesTotalCount, totalLargeSentences;
	public SizeChecker(XMLElement _xml){
		super(_xml);
		paragraphMaxSize = 14;
		sentenceMaxSize = 18;
	}

	@Override
	public void Analize() {
		if (xml != null) {
            Enumeration<XMLElement> enumerateChildren = xml.enumerateChildren();
            int i = 0;
            while (enumerateChildren.hasMoreElements()) {
                XMLElement paragraph = enumerateChildren.nextElement();
                if (checkParagraphSize(paragraph)) {
                	xml.getChildAtIndex(i).setAttribute("SentCount", "Забагато речень в одному абзаці, розбийте його на кілька менших");
                }
                Enumeration<XMLElement> paragraphChildrens = paragraph.enumerateChildren();
                int j = 0;
                while (paragraphChildrens.hasMoreElements()) {
                    XMLElement sentence = paragraphChildrens.nextElement();
                    if (checkSentenceSize(sentence)) {
                    	xml.getChildAtIndex(i).getChildAtIndex(j).setAttribute("Length", "Забагато слів в одному реченні, скоротіть його або розбийте на кілька менших");
                    }
                    j++;
                }
                i++;
            }
		}
	}

	private boolean checkSentenceSize(XMLElement sentence) {
		if (sentence != null) {
            Enumeration<XMLElement> sentenceChildrens = sentence.enumerateChildren();
            int wordsCount = 0;
            while (sentenceChildrens.hasMoreElements()) {
                XMLElement word = sentenceChildrens.nextElement();

                    if (word.getName() == "word") {
                        wordsCount++;
                    }
            }
            if (wordsCount > sentenceMaxSize) {
            	return true;
            }
        }
		return false;
	}

	private boolean checkParagraphSize(XMLElement paragraph) {
		if (paragraph != null) {
            if (paragraph.getChildrenCount() > paragraphMaxSize) {
                return true;
            }
        }
		return false;
	}

	public XMLElement getResult(){
		return this.xml;
	}
}
