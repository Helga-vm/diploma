package diploma.parser;

import net.n3.nanoxml.IXMLElement;
import net.n3.nanoxml.XMLElement;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlParser {

    private String source;
    private XMLElement xmlElement;
    
    public XmlParser() {};
    public XmlParser(String source) {
        this.source = source;
    }

    public XmlParser(XMLElement xmlElement) {
        this.xmlElement = xmlElement;
    }

    public XMLElement getXmlElement() {
        return textToXml(this.source);
    }

    /*public String getHtmlElement(XMLElement xmlElement) {
        //return xmlToHtml(xmlElement);
    }*/
    public void setString(String str) {
    	this.source = str;
    }

    private XMLElement textToXml(String source) {
        IXMLElement xmlElement = new XMLElement("root");
        ArrayList paragraphs = parseParagraphs(source);

        if (paragraphs != null && paragraphs.size() > 0) {
            for (int i = 0; i < paragraphs.size(); i++) {
                IXMLElement paragraph = xmlElement.createElement("paragraph");
                xmlElement.addChild(paragraph);

                ArrayList sentences = parseSentences(String.valueOf(paragraphs.get(i)));
                if (sentences != null && sentences.size() > 0) {
                    for (int j = 0; j < sentences.size(); j++) {
                        IXMLElement sentence = paragraph.createElement("sentence");
                        paragraph.addChild(sentence);

                        ArrayList wordsAndPunctuations = parseWordsAndPunctuations(String.valueOf(sentences.get(j)));
                        if (wordsAndPunctuations != null && wordsAndPunctuations.size() > 0) {
                            for (int k = 0; k < wordsAndPunctuations.size(); k++) {
                                if (wordsAndPunctuations.get(k).equals(".") || wordsAndPunctuations.get(k).equals(",") || wordsAndPunctuations.get(k).equals("!") || wordsAndPunctuations.get(k).equals("?") || wordsAndPunctuations.get(k).equals(":") || wordsAndPunctuations.get(k).equals(";") || wordsAndPunctuations.get(k).equals("\"") || wordsAndPunctuations.get(k).equals("'") || wordsAndPunctuations.get(k).equals("%") || wordsAndPunctuations.get(k).equals("в„–") || wordsAndPunctuations.get(k).equals("*") || wordsAndPunctuations.get(k).equals(" ") || wordsAndPunctuations.get(k).equals("-")) {
                                    IXMLElement punctuation = sentence.createElement("punctuation");
                                    sentence.addChild(punctuation);
                                    punctuation.setContent(String.valueOf(wordsAndPunctuations.get(k)));
                                } else {
                                    IXMLElement word = sentence.createElement("word");
                                    sentence.addChild(word);
                                    word.setContent(String.valueOf(wordsAndPunctuations.get(k)));
                                }
                            }

                            deleteEmptyWords((XMLElement) sentence);

                        }
                    }
                }
            }
        }
        return (XMLElement) xmlElement;
    }
    //Test
    public String xmlToString(XMLElement xmlElement) {
        if (xmlElement != null) {
            String resultString = "";


            Enumeration<XMLElement> enumerateChildren = xmlElement.enumerateChildren();
            while (enumerateChildren.hasMoreElements()) {
                XMLElement paragraph = enumerateChildren.nextElement();

                if (paragraph.hasChildren()) {
                    Enumeration<XMLElement> paragraphChildrens = paragraph.enumerateChildren();

                    if (paragraphChildrens.hasMoreElements()) {
                        XMLElement sentence = paragraphChildrens.nextElement();

                        if (sentence.hasChildren()) {
                            Enumeration<XMLElement> sentenceChildrens = paragraph.enumerateChildren();

                            while (sentenceChildrens.hasMoreElements()) {
                                XMLElement words = sentenceChildrens.nextElement();
                                Enumeration<XMLElement> wordsChildrens = words.enumerateChildren();

                                while(wordsChildrens.hasMoreElements()) {
                                    XMLElement word = wordsChildrens.nextElement();
                                    resultString += word.getContent();
                                }
                            }
                        }
                    }
                }
            }

            return resultString;
        }

        return null;
    }

    private void deleteEmptyWords(XMLElement sentence) {
        for (int i = 0; i < sentence.getChildrenCount(); i++) {
            XMLElement sentenceElement = (XMLElement) sentence.getChildAtIndex(i);
            if (sentenceElement.getName().equals("word") && sentenceElement.getContent().equals("")) {
                sentence.removeChildAtIndex(i);
            }
        }
    }

    private String getMetricsAttributes(IXMLElement xmlBlock) {
        String result = "";
        Enumeration enumeration = xmlBlock.enumerateAttributeNames();

        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            String value = xmlBlock.getAttribute(key, "");
            result += "<p id=\"" + key +  "\">";
            result += value;
            result += "</p>";
        }

        return result;
    }

    private String getErrorAttributes(IXMLElement xmlBlock) {
        String result = "";
        Enumeration enumeration = xmlBlock.enumerateAttributeNames();

        if (enumeration.hasMoreElements()) {
            result += "class=\"" + xmlBlock.getName() + "WithErrors\"";
        }

        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            String value = xmlBlock.getAttribute(key, "");
            result += " " + key + " = \"" + value + "\"";
        }

        return result;
    }

    private ArrayList parseParagraphs(String source) {
        ArrayList<String> paragraphs = new ArrayList<String>();

        if (source != null) {
            String [] stringArray = source.split("[\n\t]");
            for (int i = 0; i < stringArray.length; i++) {
                paragraphs.add(stringArray[i]);
            }
        }

        return paragraphs;
    }

    private ArrayList parseSentences(String paragraph) {
        if (paragraph != null && paragraph.length() > 0) {
            ArrayList sentences = new ArrayList();
            StringBuffer stringBuffer = new StringBuffer(paragraph);
            String regex = "[.!?]\\s[А-ЯA-Z|І|Є|Ї]";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(stringBuffer);

            int beginIndex = 0;
            int endIndex = 0;
            while (matcher.find()) {
                endIndex = matcher.start() + 1;
                sentences.add(stringBuffer.substring(beginIndex, endIndex));
                beginIndex = endIndex + 1;
            }

            stringBuffer.delete(0, beginIndex);
            sentences.add(String.valueOf(stringBuffer));

            return sentences;
        }
        return null;
    }

    private ArrayList parseWordsAndPunctuations(String sentence) {
        if (sentence != null && sentence.length() > 0) {
            ArrayList wordsAndPunctuations = new ArrayList();
            StringBuffer stringBuffer = new StringBuffer(sentence);
            String regex = "[,|.|:|;|\"|!|?|*|(|)|№|-|+|=|\\s]";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(stringBuffer);

            int beginIndex = 0;
            int endIndex = 0;

            while (matcher.find()) {
                endIndex = matcher.start();
                wordsAndPunctuations.add(stringBuffer.substring(beginIndex, endIndex).trim());
                wordsAndPunctuations.add(stringBuffer.substring(endIndex, endIndex + 1));
                beginIndex = endIndex + 1;
            }
            if ( endIndex != ( stringBuffer.length() - 1) ) {
            	wordsAndPunctuations.add(stringBuffer.substring(endIndex + 1, stringBuffer.length()).trim());
            }
            return wordsAndPunctuations;
        }
        return null;
    }

}
