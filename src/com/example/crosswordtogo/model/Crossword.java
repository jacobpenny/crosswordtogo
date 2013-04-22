package com.example.crosswordtogo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Crossword {

	String name_;
	private Square[] squares_;
	private int numRows_;
	private int numCols_;
	Map<Integer, Word> acrossWords_;
	Map<Integer, Word> downWords_;
	boolean blocksLocked_;
	private int maxWordNumber_;


	private Crossword(int numRows, int numCols) {
		numRows_ = numRows;
		numCols_ = numCols;
		squares_ = new Square[numRows_ * numCols_];
		for (int i = 0; i < squares_.length; ++i) {
			squares_[i] = new Square();
		}
		acrossWords_ = new HashMap<Integer, Word>();
		downWords_ = new HashMap<Integer, Word>();
	}

	public static Crossword createBlank(int numRows, int numCols) {
		Crossword crossword = new Crossword(numRows, numCols);
		crossword.blocksLocked_ = false;
		return crossword;
	}

	public static Crossword createCrossword(String name, int numRows, int numCols, List<Integer> blockIndices, Map<Integer, String> acrossWords, Map<Integer, String> downWords) {
		Crossword crossword = new Crossword(numRows, numCols);
		crossword.blocksLocked_ = true;
		crossword.setListOfSquaresToBlocks(blockIndices);
		crossword.generateWords();

		for (Map.Entry<Integer, String> entry : acrossWords.entrySet()) {
			crossword.acrossWords_.get(entry.getKey()).setText(entry.getValue());
		}
		for (Map.Entry<Integer, String> entry : downWords.entrySet()) {
			crossword.acrossWords_.get(entry.getKey()).setText(entry.getValue());
		}

		return crossword;
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

	public void applyStringToAcrossWord(int number, String s) throws Exception {
		Word selectedWord = acrossWords_.get(number);
		selectedWord.setText(s);
	}

	public void applyStringToDownWord(int number, String s) throws Exception {
		Word selectedWord = downWords_.get(number);
		selectedWord.setText(s);
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

	public void generateWords() {
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
					currentSquare.setNumber(counter);
					assignedNewNumber = true;	
					Word currentWord = new Word();
					currentWord.addSquare(currentSquare);
					currentSquare.setAcrossWord(currentWord);
					acrossWords_.put(counter, currentWord);			
				} else {
					// There exists a blank square to the left of the current
					// square. They both belong to the same across word.
					Word commonAcrossWord = prevAcrossSquare.getAcrossWord();
					commonAcrossWord.addSquare(currentSquare);
					currentSquare.setAcrossWord(commonAcrossWord);		
				}

				// Find the down word that this square belongs to (or create a
				// new one).
				Square prevDownSquare = getSquareAt(rowIndex - 1, colIndex);
				if (prevDownSquare == null || prevDownSquare.isBlock()) {
					// There is no blank square above the current square
					// therefore it is the beginning of a new down word.
					currentSquare.setNumber(counter);
					assignedNewNumber = true;
					Word currentWord = new Word();
					currentWord.addSquare(currentSquare);
					currentSquare.setDownWord(currentWord);
					downWords_.put(counter, currentWord);
				} else {
					// There exists a blank square above of the current square.
					// They both belong to the same down word.
					Word commonDownWord = prevDownSquare.getDownWord();
					commonDownWord.addSquare(currentSquare);
					currentSquare.setDownWord(commonDownWord);	
				}

				if (assignedNewNumber) {
					counter++;
				}
			}
		}

		maxWordNumber_ = counter - 1;

	}

}
