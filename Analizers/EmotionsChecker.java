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
                                    if (words.getChildAtIndex(i).getName().equals("punctuation")) {
                                        checkEmotionsInPunctuation(words.getChildAtIndex(i).getContent());
                                        //TODO rewrite next
                                        if (emotions != null) {
                                            words.getChildAtIndex(i).setAttribute("emotions", emotions);
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

	private void checkEmotionsInPunctuation(String content) {
		if (content != null) {
            if (content.matches("[\\!|\\?]")) {
                emotions = "В технічних текстах не повинно бути емоційного окрасу.";
            }
        }
	}
	
	public XMLElement getResult(){
		
		return this.xml;
	}
}
