package Analizers;

import java.util.ArrayList;
import java.util.Enumeration;

import net.n3.nanoxml.XMLElement;

public class ComplexSentenceChecker extends AnalizerBase {
	private int complexity;
	public ComplexSentenceChecker(XMLElement _xml){
		super(_xml);
		complexity = 0;
	}

	@Override
	public void Analize() {
		int i = 0;
		if (xml != null) {
            Enumeration<XMLElement> enumerateChildren = xml.enumerateChildren();
            while (enumerateChildren.hasMoreElements()) {
            	
                XMLElement paragraph = enumerateChildren.nextElement();
                int j = 0;
                if (paragraph.hasChildren()) {
                    Enumeration<XMLElement> paragraphChildrens = paragraph.enumerateChildren();

                    while (paragraphChildrens.hasMoreElements()) {
                    	
                        XMLElement sentence = paragraphChildrens.nextElement();
                        checkComplexSentence(sentence);
                        if (complexity>1){
                        	xml.getChildAtIndex(i).getChildAtIndex(j).setAttribute("complexity", "Too complex sentence");
                        }
                        j++;
                    }
                    complexity = 0;
                }
                i++;
            }
		}
	}

	private void checkComplexSentence(XMLElement sentence) {
		ArrayList<Integer> punctuationIndex = new ArrayList();
        for (int i = 0; i < sentence.getChildrenCount(); i++) {
            XMLElement sentenceElement = (XMLElement) sentence.getChildAtIndex(i);
            if (sentenceElement.getName().equals("punctuation") && (sentenceElement.getContent().equals(",")||sentenceElement.getContent().equals("."))) {
                punctuationIndex.add(i);
            }
        }
        punctuationIndex.add(sentence.getChildrenCount() - 1);

        for (int i = 0; i < punctuationIndex.size(); i++) {
            try {
                 if ((punctuationIndex.get(i + 1) - punctuationIndex.get(i) > 4)) {
                        complexity++;
                 }
            } catch (IndexOutOfBoundsException e) {
            }
        }
	}
	
	public XMLElement getResult() {
// TODO: need to add checking complexity
// or not needing
// anyway
		return this.xml;
	}
	
}
