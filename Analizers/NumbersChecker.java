package Analizers;

import java.util.Enumeration;

import net.n3.nanoxml.XMLElement;

public class NumbersChecker extends AnalizerBase {
	public NumbersChecker(XMLElement _xml){
		super(_xml);
	}

	@Override
	protected void Analize() {
		if (xml != null) {
            Enumeration<XMLElement> enumerateChildren = xml.enumerateChildren();
            while (enumerateChildren.hasMoreElements()) {
                XMLElement paragraph = enumerateChildren.nextElement();

                if (paragraph.hasChildren()) {
                    Enumeration<XMLElement> paragraphChildrens = paragraph.enumerateChildren();

                    while (paragraphChildrens.hasMoreElements()) {
                        XMLElement sentence = paragraphChildrens.nextElement();

                        checkNumbers(sentence);

                    }
                }
            }
        }
	}

	private void checkNumbers(XMLElement sentence) {
		// TODO Auto-generated method stub
		Enumeration<XMLElement> enumeration = sentence.enumerateChildren();

        while(enumeration.hasMoreElements()) {
            XMLElement word = enumeration.nextElement();

            if (word.getName().equals("word") && word.getContent().matches("^[0-9]+$")) {
                word.setAttribute("error-numbersCheck", "У технічній документації цифри та числа повинні писатися прописом.");
            }
        }
	}
	
	public XMLElement getResult(){
		return this.xml;
	}
	
}
