package com.lyricat.crosswordtogo.screens.menu;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.lyricat.crosswordtogo.R;
import com.lyricat.crosswordtogo.screens.BlockChooserActivity;

public class DimensionChoiceDialogFragment extends DialogFragment {
	Spinner rowSpinner_;
	Spinner colSpinner_;
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Get the layout inflater
		LayoutInflater inflater = getActivity().getLayoutInflater();

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		View layout = inflater.inflate(R.layout.dialog_dimension_choice, null);
		
		
		final Spinner rowSpinner = (Spinner) layout.findViewById(R.id.dialog_dimensions_rows);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> rowAdapter = ArrayAdapter.createFromResource(getActivity(),
				R.array.dialog_dimensions_choices, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		rowAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		rowSpinner.setAdapter(rowAdapter);
		
		final Spinner colSpinner = (Spinner) layout.findViewById(R.id.dialog_dimensions_cols);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> colAdapter = ArrayAdapter.createFromResource(getActivity(),
				R.array.dialog_dimensions_choices, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		colAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		colSpinner.setAdapter(colAdapter);
		
		
		
		
		builder.setView(layout)
		// Add action buttons
		.setPositiveButton(R.string.dialog_continue, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				
				Intent i = new Intent(getActivity(), BlockChooserActivity.class);
				
				//Spinner rowSpinner = (Spinner) getActivity().findViewById(R.id.dialog_dimensions_rows);
				i.putExtra("numRows", Integer.valueOf(rowSpinner.getSelectedItem().toString()));
				
				//Spinner colSpinner = (Spinner) getActivity().findViewById(R.id.dialog_dimensions_cols);		
				i.putExtra("numCols", Integer.valueOf(colSpinner.getSelectedItem().toString()));
				
				getActivity().startActivity(i);
			}
		})
		.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				DimensionChoiceDialogFragment.this.getDialog().cancel();
			}
		});

		return builder.create();

	}
}