package com.example.crosswordtogo;

import java.util.ArrayList;
import java.util.List;


public class Word {

	private String text_;
	List<Square> squares_;
	private Orientation orientation_;
	
	
	public enum Orientation { ACROSS, DOWN }
	
		
	public Word() {
		squares_ = new ArrayList<Square>();
	}
	
	public void setText(String text) throws Exception {
		if (text.length() == squares_.size()) {
			throw new Exception("incorrect length");
		}
		text_ = text;
		for (int index = 0; index < squares_.size(); index++) {
			squares_.get(index).setLetter(text.charAt(index));
		}
	}
	
	public void addSquare(Square s) {
		squares_.add(s);
	}
		
}
