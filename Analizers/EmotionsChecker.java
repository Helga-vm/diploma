package Analizers;

import java.util.Enumeration;

import net.n3.nanoxml.XMLElement;

public class EmotionsChecker extends AnalizerBase{
	private String emotions;
	public EmotionsChecker(XMLElement _xml){
		super(_xml);
	}

	@Override
	public void Analize() {
		int k = 0;
		if (xml != null) {
            Enumeration<XMLElement> enumerateChildren = xml.enumerateChildren();
            while (enumerateChildren.hasMoreElements()) {
                XMLElement paragraph = enumerateChildren.nextElement();
                int j=0;
                if (paragraph.hasChildren()) {
                    Enumeration<XMLElement> paragraphChildrens = paragraph.enumerateChildren();

                    while (paragraphChildrens.hasMoreElements()) {
                        XMLElement sentence = paragraphChildrens.nextElement();

                        if (sentence.hasChildren()) {
                            Enumeration<XMLElement> sentenceChildrens = sentence.enumerateChildren();

                            while (sentenceChildrens.hasMoreElements()) {
                                XMLElement words = sentenceChildrens.nextElement();

                                //for (int i = 0; i < words.getChildrenCount(); i++) {
                                    if (words.getName().equals("punctuation")) {
                                        checkEmotionsInPunctuation(words.getContent());
                                        //TODO rewrite next
                                        if (emotions != null) {
                                        	xml.getChildAtIndex(k).getChildAtIndex(j).setAttribute("emotions", emotions);//adding attribute to the emotional sentence
                                            //words.getChildAtIndex(i).setAttribute("emotions", emotions);
                                        }
                                    }
                                //}
                            }
                        }
                        emotions = null;
                        j++;
                    }
                }
                k++;
            }
		}
	}

	private void checkEmotionsInPunctuation(String content) {
		if (content != null) {
            if (content.matches("[\\!|\\?]")) {
                emotions = "В технічних текстах не повинно бути емоційно забарвлених реченнь";
            }
        }
	}
	
	public XMLElement getResult(){
		
		return this.xml;
	}
}
