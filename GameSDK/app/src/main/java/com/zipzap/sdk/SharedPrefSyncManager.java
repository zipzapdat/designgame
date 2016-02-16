package com.zipzap.sdk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentValues;
import android.content.ContextWrapper;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@TargetApi(Build.VERSION_CODES.HONEYCOMB) 
public class SharedPrefSyncManager 
		implements LoaderCallbacks<Cursor>, com.zipzap.sdk.ISyncManager {

	private static com.zipzap.sdk.ISyncManager _instance;

	private Activity activity;
	private CursorLoader cursorLoader;
	private String backupDir;
	private String backupFile;
	private String key;

	private SharedPrefSyncManager(Activity activity, String backupDir, String backupFile) {
		this.activity = activity;
		this.backupDir = backupDir;
		this.backupFile = backupFile;
		this.key = "name"; //activity.getApplication().getPackageName();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void getZipZapData() {
		activity.getLoaderManager().initLoader(1,
				null, (LoaderCallbacks<Cursor>) this);
	}

	public void putZipZapData() {


		SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(activity);
//		Map<String, Object> prefs = (Map<String, Object>) sharedPreference.getAll();
		Editor editor = sharedPreference.edit();
		editor.commit();
		//put in a temp file
//		ContextWrapper cw = new ContextWrapper(activity);
		//File directory = cw.getDir(this.backupDir, Context.MODE_PRIVATE);
		File mypath = new File("/data/data/name.bagi.levente.pedometer/shared_prefs/name.bagi.levente.pedometer_preferences.xml");
		byte[] fileData = null;

		try {
			FileInputStream in = new FileInputStream(mypath);
			int size = (int) mypath.length();
			fileData = new byte[size];

			in.read(fileData, 0, size);
			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		ContentValues values = new ContentValues();
		values.put(key, fileData);
		activity.getContentResolver().update(Uri.parse("content://com.zipzap.ZipZapProvider/cte"), values, null, null);

	}

	public static ISyncManager getInstance(Activity activity, String backupDir, String backupFile) {

		if (_instance == null) {
			_instance = new SharedPrefSyncManager(activity, backupDir, backupFile);
		}
		return _instance;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

		cursor.moveToFirst();
		byte[] blob = null;
		while (!cursor.isAfterLast()) {
			blob = cursor.getBlob(cursor.getColumnIndex(this.key));
			cursor.moveToNext();
		}

		if (blob != null) {
			//Bitmap bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);
	        ContextWrapper cw = new ContextWrapper(activity);
			//File directory = cw.getDir(this.backupDir, Context.MODE_PRIVATE);
			File mypath = new File(this.backupDir+"/"+this.backupFile);
			
			FileOutputStream out = null;

	        try {

	        	out = new FileOutputStream(mypath);
	        	//bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
	        	out.write(blob);
	        	out.close();

	        } catch (FileNotFoundException e) {
	        	e.printStackTrace();
	        } catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			//nothing there on content provider
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		cursorLoader = new CursorLoader(activity,  Uri.parse("content://com.zipzap.ZipZapProvider/cte"), null, null, null, null);
		return cursorLoader;
	}
	
}
