package com.lyricat.crosswordtogo.screens;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyricat.crosswordtogo.R;
import com.lyricat.crosswordtogo.model.Crossword;
import com.lyricat.crosswordtogo.model.Square;

public class CrosswordAdapter extends BaseAdapter {

	private Context context_;
	private Crossword crossword_;

	public CrosswordAdapter(Context c, Crossword crossword) {
		super();
		// TODO Auto-generated constructor stub
		context_ = c;
		crossword_ = crossword;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return crossword_.getCount();
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		return crossword_.getSquareAt(index);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		Square currentSquare = crossword_.getSquareAt(position);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
		
		if (currentSquare.isBlock()) {
			LinearLayout blockLayout = (LinearLayout) LayoutInflater.from(context_).inflate(R.layout.cw_block, null); // set to parent maybe
			
			TextView number = (TextView) blockLayout.findViewById(R.id.number);
			TextView letter = (TextView) blockLayout.findViewById(R.id.letter);

			int numCols = crossword_.getNumCols();
			int numberTextSize = (int) getScaledNumSize(numCols);
			
			number.setTextSize(TypedValue.COMPLEX_UNIT_DIP, numberTextSize);
			
			//LinearLayout.LayoutParams params = (LayoutParams) letter.getLayoutParams();
			//params.setMargins(0, -1 * numberTextSize - 10, 0, 0); // left, top, right, bottom
			//letter.setLayoutParams(params);
			
			letter.setTextSize(TypedValue.COMPLEX_UNIT_DIP, getScaledTextSize(numCols));
			
			return blockLayout;
		} else if (currentSquare.getNumber() == 0) {
			LinearLayout blankNoNumLayout = (LinearLayout) LayoutInflater.from(context_).inflate(R.layout.cw_blank_no_num, null);
			TextView number = (TextView) blankNoNumLayout.findViewById(R.id.number);
			TextView letter = (TextView) blankNoNumLayout.findViewById(R.id.letter);

			int numCols = crossword_.getNumCols();
			int numberTextSize = (int) getScaledNumSize(numCols);
			
			number.setTextSize(TypedValue.COMPLEX_UNIT_DIP, numberTextSize);
			
			//LinearLayout.LayoutParams params = (LayoutParams) letter.getLayoutParams();
			//params.setMargins(0, -1 * numberTextSize - 10, 0, 0); // left, top, right, bottom
			//letter.setLayoutParams(params);
			
			letter.setTextSize(TypedValue.COMPLEX_UNIT_DIP, getScaledTextSize(numCols));
			letter.setText(String.valueOf(currentSquare.getLetter()));

			return blankNoNumLayout;
		} else {
			LinearLayout blankWithNumLayout = (LinearLayout) LayoutInflater.from(context_).inflate(R.layout.cw_blank_with_num, null);
			TextView number = (TextView) blankWithNumLayout.findViewById(R.id.number);
			TextView letter = (TextView) blankWithNumLayout.findViewById(R.id.letter);
			
			int numCols = crossword_.getNumCols();
			int numberTextSize = (int) getScaledNumSize(numCols);
			
			number.setTextSize(TypedValue.COMPLEX_UNIT_DIP, numberTextSize);
			
			//LinearLayout.LayoutParams params = (LayoutParams) letter.getLayoutParams();
			//params.setMargins(0, -1 * numberTextSize - 10, 0, 0); // left, top, right, bottom
			//letter.setLayoutParams(params);
			
			number.setText(String.valueOf(currentSquare.getNumber()));
			letter.setTextSize(TypedValue.COMPLEX_UNIT_DIP, getScaledTextSize(numCols));
			letter.setText(String.valueOf(currentSquare.getLetter()));
			
			return blankWithNumLayout;
		}

		
	}
	
	private float getScaledNumSize(int numCols) {
		float result;
		switch(crossword_.getNumCols()) {
		case 13:
			result = 8;
			break;
		case 15:
			result = 8;
			break;
		case 17:
			result = 8;
			break;
		case 19:
			result = 6;
			break;
		case 21:
			result = 5;
			break;
		case 23:
			result = 4;
			break;
		case 25:
			result = 4;
			break;
		default:
			result = 8;
			break;
		}
		return result;
	}
	
	private float getScaledTextSize(int numCols) {
		float result;
		switch(crossword_.getNumCols()) {
		case 13:
			result = 16;
			break;
		case 15:
			result = 14;
			break;
		case 17:
			result = 13;
			break;
		case 19:
			result = 12;
			break;
		case 21:
			result = 12;
			break;
		case 23:
			result = 10;
			break;
		case 25:
			result = 9;
			break;
		default:
			result = 10;
			break;
		}
		
		return result;
	}

}
