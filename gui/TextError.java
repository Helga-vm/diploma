package gui;

public class TextError {
	String _errmsg;
	int _paragraph;
	int _sentence;
	int _word;
	
	public TextError(String errmsg, int paragraph, int sentence, int word) {
		_errmsg = errmsg;
		_paragraph = paragraph;
		_sentence = sentence;
		_word = word;
	}
	
	public int getParagraphIndex() {
		return _paragraph;
	}
	
	public int getSentenceIndex() {
		return _sentence;
	}
	
	public int getWordIndex() {
		return _word;
	}
	
}
