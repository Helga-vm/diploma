package Analizers;

import java.util.Enumeration;

import net.n3.nanoxml.XMLElement;

public class SizeChecker extends AnalizerBase{
	private int paragraphMaxSize, sentenceMaxSize, sentencesTotalCount, totalLargeSentences;
	public SizeChecker(XMLElement _xml){
		super(_xml);
		paragraphMaxSize = sentencesTotalCount = totalLargeSentences = 0;
		sentenceMaxSize = 18;
	}

	@Override
	protected void Analize() {
		// TODO Auto-generated method stub
		if (xml != null) {
            Enumeration<XMLElement> enumerateChildren = xml.enumerateChildren();
            while (enumerateChildren.hasMoreElements()) {
                XMLElement paragraph = enumerateChildren.nextElement();
                checkParagraphSize(paragraph);
                Enumeration<XMLElement> paragraphChildrens = paragraph.enumerateChildren();
                while (paragraphChildrens.hasMoreElements()) {
                    XMLElement sentence = paragraphChildrens.nextElement();
                    checkSentenceSize(sentence);

                }
            }

            countSentencesStatistic(xml);
		}
	}
	
	private void countSentencesStatistic(XMLElement xml) {
		// TODO Auto-generated method stub
		if (xml != null) {
            int largeSentencesPercent = (int) (((float) totalLargeSentences / (float) sentencesTotalCount) * 100);
            if (largeSentencesPercent > 0) {
                String statisticsResult = "В тексті " + largeSentencesPercent + "% занадто великих речень.";
                xml.setAttribute("metrics-sizeChecker", statisticsResult);
            }
        }
	}

	private void checkSentenceSize(XMLElement sentence) {
		// TODO Auto-generated method stub
		if (sentence != null) {
            sentencesTotalCount++;
            Enumeration<XMLElement> sentenceChildrens = sentence.enumerateChildren();
            int wordsCount = 0;
            while (sentenceChildrens.hasMoreElements()) {
                XMLElement word = sentenceChildrens.nextElement();

                    if (word.getName() == "word") {
                        wordsCount++;
                    }
            }
            if (wordsCount > sentenceMaxSize) {
                totalLargeSentences++;
                sentence.setAttribute("error-sizeChecker", "Речення занадто велике. Максимальна кількість слів - " + sentenceMaxSize);
            }
        }
	}

	private void checkParagraphSize(XMLElement paragraph) {
		// TODO Auto-generated method stub
		if (paragraph != null) {
            if (paragraph.getChildrenCount() > paragraphMaxSize) {
                paragraph.setAttribute("error-sizeChecker", "В абзаці забагато речень. Максимальная кількість речень - " + paragraphMaxSize);
            }
        }
	}

	public XMLElement getResult(){
		return this.xml;
	}
}
