package com.example.crosswordtogo.screens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

import com.example.crosswordtogo.R;
import com.example.crosswordtogo.logic.ContentDescriptor;
import com.example.crosswordtogo.model.Crossword;

public class EditCrosswordActivity extends Activity {
	
	static boolean firstRun_ = true;
	Crossword crossword_;
	private static CrosswordAdapter crosswordAdapter_;
	
	// UI controls
	private EditText userWordInput_;
	private Spinner numberSpinner_;
	private Spinner acrossSpinner_;
	private Button writeButton_;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_edit_crossword);

		ContentResolver contentResolver = this.getContentResolver();
		String crosswordName = getIntent().getStringExtra("crosswordName");

		// Grab numRows and numCols from DB
		int numRows = 0;
		int numCols = 0;
		Cursor crosswordCursor = contentResolver.query(ContentDescriptor.Crossword.CONTENT_URI, null, 
				(ContentDescriptor.Crossword.Cols.NAME + " = " + "'" + crosswordName + "'"), null, null);
		while (crosswordCursor.moveToNext()) {
			numRows = crosswordCursor.getInt(crosswordCursor.getColumnIndex(ContentDescriptor.Crossword.Cols.NUMROWS));
			numCols = crosswordCursor.getInt(crosswordCursor.getColumnIndex(ContentDescriptor.Crossword.Cols.NUMCOLS));			
		}
		
		// Grab block indices from DB
		List<Integer> blockList = new ArrayList<Integer>();	
		Cursor blocksCursor = contentResolver.query(ContentDescriptor.Blocks.CONTENT_URI, null, 
				(ContentDescriptor.Blocks.Cols.CROSSWORD_NAME + " = " + "'" + crosswordName + "'"), null, null);
		while (blocksCursor.moveToNext()) {
			blockList.add(blocksCursor.getInt(blocksCursor.getColumnIndex(ContentDescriptor.Blocks.Cols.INDEX)));		
		}
		
		// Grab words from DB
		Map<Integer, String> acrossWords = new HashMap<Integer, String>();
		Map<Integer, String> downWords = new HashMap<Integer, String>();
		Cursor wordsCursor = contentResolver.query(ContentDescriptor.Words.CONTENT_URI, null, 
				(ContentDescriptor.Words.Cols.CROSSWORD_NAME + " = " + "'" + crosswordName + "'"), null, null);
		while (wordsCursor.moveToNext()) {
			int wordNum = blocksCursor.getInt(blocksCursor.getColumnIndex(ContentDescriptor.Words.Cols.NUMBER));
			int isAcross = blocksCursor.getInt(blocksCursor.getColumnIndex(ContentDescriptor.Words.Cols.ISACROSS));
			String wordText = blocksCursor.getString(blocksCursor.getColumnIndex(ContentDescriptor.Words.Cols.WORDTEXT));
			
			if (isAcross == 1) {
				acrossWords.put(wordNum, wordText);
			} else {
				downWords.put(wordNum, wordText);
			}		
		}

		crossword_ = Crossword.createCrossword(crosswordName, numRows, numCols, blockList, acrossWords, downWords);
		
		if(firstRun_) {
			crosswordAdapter_ = new CrosswordAdapter(this, crossword_);
			Log.d("ONCREATE", "flag is true");
			firstRun_ = false;
		}
		
		userWordInput_ = (EditText) findViewById(R.id.edit_answer);
		
		// Fill our number spinner with all possible values
		numberSpinner_ = (Spinner) findViewById(R.id.number_spinner);	
		List<CharSequence> wordNumbers = new ArrayList<CharSequence>();
		for (int i = 1; i <= crossword_.getMaxWordNumber(); i++) {
			wordNumbers.add(String.valueOf(i));
		}
		ArrayAdapter<CharSequence> nAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
		nAdapter.addAll(wordNumbers);
		nAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		numberSpinner_.setAdapter(nAdapter);

		

		
		
		acrossSpinner_ = (Spinner) findViewById(R.id.across_down_spinner);
		ArrayAdapter<CharSequence> sAdapter = ArrayAdapter.createFromResource(this, R.array.across_down_array, android.R.layout.simple_spinner_item);
		sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		acrossSpinner_.setAdapter(sAdapter);
		
		writeButton_ = (Button) findViewById(R.id.answer_button);

		writeButton_.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click
				String inputedText = userWordInput_.getText().toString();
				String numberValue = numberSpinner_.getSelectedItem().toString();
				String acrossValue = acrossSpinner_.getSelectedItem().toString();
				int currentNumber = Integer.valueOf(numberValue);

				if (acrossValue.equals("Across")) {
					try {
						crossword_.applyStringToAcrossWord(currentNumber, inputedText.toUpperCase());
						crosswordAdapter_.notifyDataSetChanged();
						userWordInput_.setText("");
					} catch (Exception e) {

					}
				}

				if (acrossValue.equals("Down")) {
					try {
						crossword_.applyStringToDownWord(currentNumber, inputedText.toUpperCase());
						crosswordAdapter_.notifyDataSetChanged();
						userWordInput_.setText("");
					} catch (Exception e) {

					}
				}
			}
		});
		
		GridView cwView_ = (GridView) findViewById(R.id.block_chooser_crossword);
		cwView_.setNumColumns(numCols);
		cwView_.setAdapter(crosswordAdapter_);
	}
}

