package Analizers;

import java.util.Enumeration;

import net.n3.nanoxml.XMLElement;

public class SizeChecker extends AnalizerBase{
	private int paragraphMaxSize, sentenceMaxSize, sentencesTotalCount, totalLargeSentences;
	public SizeChecker(XMLElement _xml){
		super(_xml);
		paragraphMaxSize = 1;
		sentenceMaxSize = 18;
	}

	@Override
	public void Analize() {
		// TODO Auto-generated method stub
		if (xml != null) {
            Enumeration<XMLElement> enumerateChildren = xml.enumerateChildren();
            int i = 0;
            while (enumerateChildren.hasMoreElements()) {
                XMLElement paragraph = enumerateChildren.nextElement();
                if (checkParagraphSize(paragraph)) {
                	xml.getChildAtIndex(i).setAttribute("SentCount", "Critical");
                }
                Enumeration<XMLElement> paragraphChildrens = paragraph.enumerateChildren();
                int j = 0;
                while (paragraphChildrens.hasMoreElements()) {
                    XMLElement sentence = paragraphChildrens.nextElement();
                    if (checkSentenceSize(sentence)) {
                    	xml.getChildAtIndex(i).getChildAtIndex(j).setAttribute("Length", "Critical");
                    }
                    j++;
                }
                i++;
            }
            //countSentencesStatistic(xml);
		}
	}
	
	private void countSentencesStatistic(XMLElement xml) {// not important
		// TODO Auto-generated method stub
		if (xml != null) {
            int largeSentencesPercent = (int) (((float) totalLargeSentences / (float) sentencesTotalCount) * 100);
            if (largeSentencesPercent > 0) {
                String statisticsResult = "В тексті " + largeSentencesPercent + "% занадто великих речень.";
                xml.setAttribute("metrics-sizeChecker", statisticsResult);
            }
        }
	}

	private boolean checkSentenceSize(XMLElement sentence) {
		// TODO Auto-generated method stub
		if (sentence != null) {
            //sentencesTotalCount++;
            Enumeration<XMLElement> sentenceChildrens = sentence.enumerateChildren();
            int wordsCount = 0;
            while (sentenceChildrens.hasMoreElements()) {
                XMLElement word = sentenceChildrens.nextElement();

                    if (word.getName() == "word") {
                        wordsCount++;
                    }
            }
            if (wordsCount > sentenceMaxSize) {
                //totalLargeSentences++;
            	return true;
                //sentence.setAttribute("error-sizeChecker", "Речення занадто велике. Максимальна кількість слів - " + sentenceMaxSize);
            }
        }
		return false;
	}

	private boolean checkParagraphSize(XMLElement paragraph) {
		// TODO Auto-generated method stub
		if (paragraph != null) {
            if (paragraph.getChildrenCount() > paragraphMaxSize) {
                return true;
            	//paragraph.setAttribute("error-sizeChecker", "В абзаці забагато речень. Максимальная кількість речень - " + paragraphMaxSize);
            }
        }
		return false;
	}

	public XMLElement getResult(){
		return this.xml;
	}
}
