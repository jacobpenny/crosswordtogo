package com.lyricat.crosswordtogo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Crossword {

	String name_;
	private Square[] squares_;
	private int numRows_;
	private int numCols_;
	boolean blocksLocked_;
	private int maxWordNumber_;
	
	// test imp
	Map<Integer, String> wordNumToUserTextAcross_;
	Map<Integer, String> wordNumToUserTextDown_;
	Map<Integer, List<Integer>> wordNumToSquareListAcross_; 
	Map<Integer, List<Integer>> wordNumToSquareListDown_; 
	
	private Crossword(int numRows, int numCols) {
		numRows_ = numRows;
		numCols_ = numCols;
		squares_ = new Square[numRows_ * numCols_];
		for (int i = 0; i < squares_.length; ++i) {
			squares_[i] = new Square();
		}
		wordNumToUserTextAcross_ = new HashMap<Integer, String>();
		wordNumToUserTextDown_ = new HashMap<Integer, String>();
		wordNumToSquareListAcross_ = new HashMap<Integer, List<Integer>>();
		wordNumToSquareListDown_ = new HashMap<Integer, List<Integer>>();
	}

	public static Crossword createBlank(int numRows, int numCols) {
		Crossword crossword = new Crossword(numRows, numCols);
		crossword.blocksLocked_ = false;
		return crossword;
	}

	public void applyStringToAcrossWord(int number, String s) throws Exception {
		// Confirm that string is correct length
		if (wordNumToSquareListAcross_.get(number).size() != s.length()) { 
			throw new Exception("wrong length"); 
		}
		
		wordNumToUserTextAcross_.put(number, s);
		
		// Update the squares with new letters
		int counter = 0;
		for (Integer index : wordNumToSquareListAcross_.get(number)) {
			getSquareAt(index).setLetter(s.charAt(counter));
			counter++;
		}
	}
	
	public void applyStringToDownWord(int number, String s) throws Exception {
		// Confirm that string is correct length
		if (wordNumToSquareListDown_.get(number).size() != s.length()) { 
			throw new Exception("wrong length"); 
		}
		
		wordNumToUserTextDown_.put(number, s);
		
		// Update the squares with new letters
		int counter = 0;
		for (Integer index : wordNumToSquareListDown_.get(number)) {
			getSquareAt(index).setLetter(s.charAt(counter));
			counter++;
		}
	}

	public int getCount() {
		return squares_.length;
	}

	public int getNumCols() {
		return numCols_;
	}

	
	public int getMaxWordNumber() {
		return maxWordNumber_;
	}
	
	public List<Integer> getBlockList() {
		// TODO cache this
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < (numRows_ * numCols_); i++) {
			if (getSquareAt(i).isBlock()) {
				result.add(i);
			}
		}
		return result;
	}

	// dont use?
	public void toggleSquareBlockness(int index) {
		squares_[index].toggleBlockness();
	}

	// dont use?
	public void setSquareToBlock(int index) {
		squares_[index].setToBlock();
	}

	// dont use?
	public void setListOfSquaresToBlocks(List<Integer> blockIndices) {
		for(Integer index : blockIndices) {
			setSquareToBlock(index);
		}
	}

	public Square getSquareAt(int index) {
		return squares_[index];
	}

	public Square getSquareAt(int row, int col) {
		if (row < 0 || col < 0 || row >= numRows_ || col >= numCols_) {
			return null;
		}
		int index = (row * numCols_) + col;
		return squares_[index];
	}

	public void generateSquareLists() {
		assert blocksLocked_;
		int counter = 1;
		boolean assignedNewNumber;

		for (int rowIndex = 0; rowIndex < numRows_; rowIndex++) {
			for (int colIndex = 0; colIndex < numCols_; colIndex++) {

				assignedNewNumber = false;
				Square currentSquare = getSquareAt(rowIndex, colIndex);

				if (currentSquare.isBlock()) {
					continue;
				}

				// Find the across word that this square belongs to (or create a
				// new one).
				Square prevAcrossSquare = getSquareAt(rowIndex, colIndex - 1);
				if (prevAcrossSquare == null || prevAcrossSquare.isBlock()) {
					// There is no blank square to the left of this therefore it
					// is the beginning of a new across word
					List<Integer> squareList = new ArrayList<Integer>();
					int currentSquareIndex = rowIndex * numCols_ + colIndex;
					squareList.add(currentSquareIndex);
					wordNumToSquareListAcross_.put(counter, squareList);
					currentSquare.setNumber(counter);
					assignedNewNumber = true;				
				} else {
					// There exists a blank square to the left of the current
					// square. They both belong to the same across word.
					int currentSquareIndex = rowIndex * numCols_ + colIndex;
					int prevSquareIndex = currentSquareIndex - 1;
					
					// Iterate over wordNumToSquareListAcross_ to find the word number of prev square
					for (Map.Entry<Integer, List<Integer>> entry : wordNumToSquareListAcross_.entrySet()) {
					    for (Integer index: entry.getValue()) {
					    	if (index == prevSquareIndex) {
					    		entry.getValue().add(currentSquareIndex);
					    	}
					    }
					}	
				}

				// Find the down word that this square belongs to (or create a
				// new one).
				Square prevDownSquare = getSquareAt(rowIndex - 1, colIndex);
				if (prevDownSquare == null || prevDownSquare.isBlock()) {
					// There is no blank square above the current square
					// therefore it is the beginning of a new down word.
					List<Integer> squareList = new ArrayList<Integer>();
					int currentSquareIndex = rowIndex * numCols_ + colIndex;
					squareList.add(currentSquareIndex);
					wordNumToSquareListDown_.put(counter, squareList);
					currentSquare.setNumber(counter);
					assignedNewNumber = true;		
				} else {
					// There exists a blank square above of the current square.
					// They both belong to the same down word.
					int currentSquareIndex = rowIndex * numCols_ + colIndex;
					int prevSquareIndex = currentSquareIndex - 1;
					
					// Iterate over wordNumToSquareListAcross_ to find the word number of prev square
					for (Map.Entry<Integer, List<Integer>> entry : wordNumToSquareListDown_.entrySet()) {
					    for (Integer index: entry.getValue()) {
					    	if (index == prevSquareIndex) {
					    		entry.getValue().add(currentSquareIndex);
					    	}
					    }
					}
				}

				if (assignedNewNumber) {
					counter++;
				}
			}
		}

		maxWordNumber_ = counter - 1;

	}

}
