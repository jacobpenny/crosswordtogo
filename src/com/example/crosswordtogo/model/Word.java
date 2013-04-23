package com.example.crosswordtogo.model;

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
	
	public void setText(String text) {
		text_ = text;
		for (int index = 0; index < squares_.size(); index++) {
			squares_.get(index).setLetter(text.charAt(index));
		}
	}
	
	public void addSquare(Square s) {
		squares_.add(s);
	}
		
}
