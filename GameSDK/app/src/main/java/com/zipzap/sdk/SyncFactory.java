package com.zipzap.sdk;

import android.app.Activity;

public class SyncFactory {
	public static ISyncManager getSyncManger(Activity activity,
			String backupDir, String backupFile, Boolean sharedPref) {

		if (sharedPref)
			return (ISyncManager) SharedPrefSyncManager.getInstance(activity, backupDir, backupFile);
		else
			return (ISyncManager) FileSyncManager.getInstance(activity, backupDir, backupFile);
	}
	
}
