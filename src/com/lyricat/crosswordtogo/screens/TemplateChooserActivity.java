package com.lyricat.crosswordtogo.screens;

import java.io.InputStream;

import com.google.gson.Gson;
import com.lyricat.crosswordtogo.R;
import com.lyricat.crosswordtogo.utils.AppConstants;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class TemplateChooserActivity extends FragmentActivity {
	static final int NUM_ITEMS = 1;
	static int[] thumbnails_ = new int[NUM_ITEMS];
	static String[] templateArray_;
	static String[] templateFileNameArray_;
	TemplatePagerAdapter mAdapter;

	ViewPager mPager;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_pager);

		thumbnails_[0] = R.drawable.american_15_15_a;
		
		templateFileNameArray_ = getResources().getStringArray(R.array.template_file_names);
		
		mAdapter = new TemplatePagerAdapter(getSupportFragmentManager());

		mPager = (ViewPager)findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);

		// Watch for button clicks.
		Button button = (Button)findViewById(R.id.goto_first);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPager.setCurrentItem(0);
			}
		});
		button = (Button)findViewById(R.id.goto_last);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPager.setCurrentItem(NUM_ITEMS-1);
			}
		});
	}

	public static class TemplatePagerAdapter extends FragmentStatePagerAdapter {

		public TemplatePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return TemplateViewFragment.newInstance(position);

		}

		@Override
		public int getCount() {
			return NUM_ITEMS;
		}


	}


	public static class TemplateViewFragment extends Fragment
	{
		//UI Elements
		View v;
		int mNum;

		static TemplateViewFragment newInstance(int num) {
			TemplateViewFragment f = new TemplateViewFragment();

            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);

            return f;
        }

		/**
         * When creating, retrieve this instance's number from its arguments.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
            templateArray_ = getResources().getStringArray(R.array.templates);
        }

		
		//Creates UI and setups up Tab Elements
		@Override
		public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) 
		{
			if (container == null) 
				return null;

			if (v != null) 
				return v;

			v = inflater.inflate(R.layout.fragment_layout, container, false);
			TextView tv = (TextView)v.findViewById(R.id.text);
			tv.setText(templateArray_[mNum]);
			
			ImageView iv = (ImageView)v.findViewById(R.id.thumbnail);
			iv.setImageResource(thumbnails_[mNum]);
			iv.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View v) {
			    	AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
					alert.setTitle("Name your new crossword");
					final EditText input = new EditText(getActivity());
					alert.setView(input);

					alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int whichButton) {
							String name = input.getEditableText().toString();
							//commitCrossword(name);
							Intent i = new Intent(getActivity(), EditCrosswordActivity.class);
							i.putExtra(AppConstants.CROSSWORD_NAME_EXTRA, name);
							
							//String content = new Scanner(new File("filename")).useDelimiter("\\Z").next();							
							
							
							 
							
							//i.putExtra(AppConstants.CROSSWORD_JSON, crosswordJson);
							
							getActivity().startActivity(i);
							getActivity().finish();
						}
					});
					alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int whichButton) {
							dialog.cancel();
						}
					}); 
					AlertDialog alertDialog = alert.create();
					alertDialog.show(); 
			    	
			    }
			});
			
			return v;

		}
	}
}