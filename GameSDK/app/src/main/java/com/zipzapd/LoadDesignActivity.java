package com.zipzapd;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.survivingwithandroid.pegboard.util.ImageUtility;

public class LoadDesignActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load);
		ImageView img   = (ImageView)findViewById(R.id.imgPicker);
		boolean ret = ImageUtility.loadImageFromStorage(img, getApplicationContext());
		if (!ret) {
			Toast error = Toast.makeText(getApplicationContext(), "No Previous Designs!", Toast.LENGTH_LONG);
			error.show();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

}
