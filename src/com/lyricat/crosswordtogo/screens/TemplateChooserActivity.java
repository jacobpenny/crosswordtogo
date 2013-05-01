package com.lyricat.crosswordtogo.screens;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lyricat.crosswordtogo.R;


public class TemplateChooserActivity extends FragmentActivity {
	static final int NUM_ITEMS = 1;
	static int[] thumbnails_ = new int[NUM_ITEMS];
	static String[] templateArray_;
	TemplatePagerAdapter mAdapter;

	ViewPager mPager;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_pager);

		thumbnails_[0] = R.drawable.american_15_15_a;
		
		
		mAdapter = new TemplatePagerAdapter(getSupportFragmentManager());

		mPager = (ViewPager)findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);

		// Watch for button clicks.
		Button button = (Button)findViewById(R.id.goto_first);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mPager.setCurrentItem(0);
			}
		});
		button = (Button)findViewById(R.id.goto_last);
		button.setOnClickListener(new OnClickListener() {
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
			return v;

		}
	}
}