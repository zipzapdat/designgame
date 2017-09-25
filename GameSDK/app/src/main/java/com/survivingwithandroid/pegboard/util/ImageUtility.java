/**
 * This is a tutorial source code 
 * provided "as is" and without warranties.
 *
 * For any question please visit the web site
 * http://www.survivingwithandroid.com
 *
 * or write an email to
 * survivingwithandroid@gmail.com
 *
 */
package com.survivingwithandroid.pegboard.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.zipzap.sdk.ISyncManager;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

/*
 * Copyright (C) 2014 Francesco Azzola - Surviving with Android (http://www.survivingwithandroid.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class ImageUtility {

	public static void saveImage(Bitmap bitmap, String fileName, Context context)
			throws SaveFileException {

		ContextWrapper cw = new ContextWrapper(context);
		// path to imageDir - @ app specific locatiom
		File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
		// Create default image to be saved and loaded
		File mypath = new File(directory, "design.png");

		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(mypath);
			// Use the compress method on the BitMap object to write image to
			// the OutputStream
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.close();

		} catch (Throwable t) {
			throw new SaveFileException();
		}

		Log.e("Arun", directory.getAbsolutePath());
		Toast error = Toast.makeText(context, directory.getAbsolutePath()
				.toString(), Toast.LENGTH_LONG);
		error.show();
		
//		//////////////////		
//		ByteArrayOutputStream bos = new ByteArrayOutputStream();
//		bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
//		byte[] bitMapData = bos.toByteArray();
//
//		ContentValues values = new ContentValues();
//		values.put("name", bitMapData);
//		context.getContentResolver().update(Uri.parse("content://com.zipzapsync.MyProvider/cte"), values, null, null);
//		//////////////////
		
		
	}
	
	public static boolean loadImageFromStorage(ImageView img, Context context) {

		ContextWrapper cw = new ContextWrapper(context);
		// path to /data/data/yourapp/app_data/imageDir
		File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
		// Create imageDir
		File mypath = new File(directory, "design.png");

		try {

			Bitmap b = BitmapFactory.decodeStream(new FileInputStream(mypath));

			img.setImageBitmap(b);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;

	}

}
