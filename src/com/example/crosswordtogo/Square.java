package com.example.crosswordtogo;

public class Square {
	
	private char letter_;
	private int number_;
	private boolean isBlock_;
	private Word acrossWord_;
	private Word downWord_;
	
	public Square() {
		letter_ = ' ';
		isBlock_ = false;
	}

	public boolean isBlock() {
		return isBlock_;
	}

	public void setToBlock() {
		isBlock_ = true;
	}
	
	public void setToBlank() {
		isBlock_ = false;
	}
	
	public void toggleBlockness() {
		if (isBlock_) {
			this.setToBlank();
		} else {
			this.setToBlock();
		}
	}
	
	public void setLetter(char letter) {
		letter_ = letter;
	}
	
	public char getLetter() {
		return letter_;
	}
	
	public void setNumber(int number) {
		number_ = number;
	}
	
	public int getNumber() {
		return number_;
	}
	
	public void setAcrossWord(Word word) {
		acrossWord_ = word;
	}
	
	public Word getAcrossWord() {
		return acrossWord_;
	}
	
	public void setDownWord(Word word) {
		downWord_ = word;
	}
	
	public Word getDownWord() {
		return downWord_;
	}
}
