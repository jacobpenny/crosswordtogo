package com.example.crosswordtogo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

		Square currentSquare = (Square) crossword_.getSquareAt(position);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
		
		if (currentSquare.isBlock()) {
			LinearLayout blockLayout = (LinearLayout) LayoutInflater.from(context_).inflate(R.layout.cw_block, null); // set to parent maybe
			TextView letter = (TextView) blockLayout.findViewById(R.id.letter);
			letter.setTextSize(getScaledTextSize(crossword_.getNumCols()));
			return blockLayout;
		} else if (currentSquare.getNumber() == 0) {
			LinearLayout blankNoNumLayout = (LinearLayout) LayoutInflater.from(context_).inflate(R.layout.cw_blank_no_num, null);
			TextView letter = (TextView) blankNoNumLayout.findViewById(R.id.letter);
			letter.setTextSize(getScaledTextSize(crossword_.getNumCols()));
			//letter.setText(String.valueOf(currentSquare.getLetter()));
			//letter.setText(String.valueOf(position));

			return blankNoNumLayout;
		} else {
			LinearLayout blankWithNumLayout = (LinearLayout) LayoutInflater.from(context_).inflate(R.layout.cw_blank_with_num, null);
			TextView number = (TextView) blankWithNumLayout.findViewById(R.id.number);
			number.setText(String.valueOf(currentSquare.getNumber()));
			TextView letter = (TextView) blankWithNumLayout.findViewById(R.id.letter);
			letter.setText(String.valueOf(currentSquare.getLetter()));
			return blankWithNumLayout;
		}

		/* might need this later
		TextView number = (TextView) relativeLayout.findViewById(R.id.number);
		TextView letter = (TextView) relativeLayout.findViewById(R.id.letter);
		float textSize = 32 - board_.getNumberOfColumns();
		letter.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
		*/
		// RelativeLayout relativelayout = (RelativeLayout) relativeLayout.findViewById(R.id.square);
		
		
	}
	
	private float getScaledTextSize(int numCols) {
		float result = 32;
		result -= numCols;
		return result;
	}

}
