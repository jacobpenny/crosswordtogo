package com.lyricat.crosswordtogo.model;


public class Square {
	
	private char letter_;
	private int number_;
	private boolean isBlock_;
	
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
	
}
