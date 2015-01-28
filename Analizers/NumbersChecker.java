package Analizers;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Enumeration;

import net.n3.nanoxml.XMLElement;

public class NumbersChecker extends AnalizerBase {
	public NumbersChecker(XMLElement _xml){
		super(_xml);
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
                        int k = 0;
                        
                        if (sentence.hasChildren()){
                        Enumeration<XMLElement> enumeration = sentence.enumerateChildren();

                        	while(enumeration.hasMoreElements()) {
                        		XMLElement word = enumeration.nextElement();

                        		//if (word.getName().equals("word") && word.getContent().matches("[0-9]*,?[0-9]*")) {
                        		if (word.getName().equals("word") && word.getContent().matches("^[0-9]+$")){
                        		//if  (word.getName().equals("word") && isNumeric(word.getContent()) ) {
                        			//if (isInteger(word.getContent())) {
                        				int digit = new Integer(word.getContent());
                        				if ( digit < 1000 ){
                        					word.setAttribute("numbers", "Numbers as words");
                        					xml.getChildAtIndex(i).getChildAtIndex(j).getChildAtIndex(k).setAttribute("numbers", "Числа невеликої величини в технічних текстах слід писати словами");
                        				//}
                        			}
                        			//}
                        			else{
                        				}
                        		}
                        		k++;
                        	}
                        }
                        j++;
                    }
                }
                i++;
            }
        }
	}
	private boolean isNumeric(String str)
	{
		  NumberFormat formatter = NumberFormat.getInstance();
		  ParsePosition pos = new ParsePosition(0);
		  formatter.parse(str, pos);
		  return str.length() == pos.getIndex();
	}
	private boolean isInteger(String str) {
		try{
			Integer.parseInt(str);
			return true;
		}catch(NumberFormatException ex) {
			return false;
		}
	}
	private boolean isDouble(String str) {
		try{
			Double.parseDouble(str);
			return true;
		}catch(NumberFormatException ex) {
			return false;
		}
	}
	public XMLElement getResult(){
		return this.xml;
	}
	
}
