package com.lyricat.crosswordtogo.screens.menu;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.lyricat.crosswordtogo.R;
import com.lyricat.crosswordtogo.screens.TemplateChooserActivity;

public class NewCrosswordDialogFragment extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setItems(R.array.new_crossword_dialog_array, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0 : { 
					Intent i = new Intent(getActivity(), TemplateChooserActivity.class);
					getActivity().startActivity(i);
					break; 
					}

				case 1 : { 
					// Create from scratch selected
					
					// Create new fragment and transaction
					Fragment newDimensionsFragment = new DimensionChoiceDialogFragment();
					FragmentTransaction transaction = getFragmentManager().beginTransaction();

					// Replace whatever is in the fragment_container view with this fragment,
					// and add the transaction to the back stack
					transaction.add(newDimensionsFragment, null);
					//transaction.replace(R.id.fragment_container, newFragment);
					//transaction.addToBackStack(null);

					// Commit the transaction
					transaction.commit();

					}
				}

			}});


		// Create the AlertDialog object and return it
		return builder.create();
	}
}
