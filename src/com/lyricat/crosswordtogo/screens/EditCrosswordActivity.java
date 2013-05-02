package com.lyricat.crosswordtogo.screens;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.lyricat.crosswordtogo.R;
import com.lyricat.crosswordtogo.model.Crossword;
import com.lyricat.crosswordtogo.utils.AppConstants;
import com.lyricat.crosswordtogo.utils.LogUtil;

public class EditCrosswordActivity extends Activity {
	
	String crosswordName_; 
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

		crosswordName_ = getIntent().getStringExtra(AppConstants.CROSSWORD_NAME_EXTRA);

		// Grab numRows and numCols from DB
		int numRows = 0;
		int numCols = 0;
		
	
		Gson gson = new Gson();
		SharedPreferences settings = getSharedPreferences(AppConstants.CROSSWORDS_STORAGE, 0);
	    String cword = settings.getString(crosswordName_, " ");

		
		crossword_ = gson.fromJson(cword, Crossword.class);
		crossword_.generateSquareLists();
		
		crosswordAdapter_ = new CrosswordAdapter(this, crossword_);

		
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
			@Override
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
		
		Button jsonButton = (Button) findViewById(R.id.json_button);

		jsonButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Gson gson = new Gson();
				LogUtil.splitAndLog("JSON", gson.toJson(crossword_));
				
			}
		});
		
		GridView cwView_ = (GridView) findViewById(R.id.block_chooser_crossword);
		cwView_.setNumColumns(crossword_.getNumCols());
		cwView_.setAdapter(crosswordAdapter_);
	}
	
	

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		saveCrossword();
	}



	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		saveCrossword();
	}
	
	private void saveCrossword() { 
		SharedPreferences crosswordStorage = getSharedPreferences(AppConstants.CROSSWORDS_STORAGE, 0);
		SharedPreferences.Editor editor = crosswordStorage.edit();
		Gson gson = new Gson();
		editor.putString(crosswordName_, gson.toJson(crossword_));
		editor.commit();
	}
}

