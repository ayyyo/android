package com.ayyayo.g.common;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import com.ayyayo.g.listener.ResultCallBack;

import org.apache.commons.io.IOUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import timber.log.Timber;

public class FileHelper {

	private static final String LOG_TAG = FileHelper.class.getSimpleName();

	public void copyInputStreamToFile (final InputStream in, String fileName, final ResultCallBack<File> resultCallBack) {
		String fileAbsolutePath = getDownloadDirectoryAbsolutePath(fileName);
		Log.e(LOG_TAG, fileAbsolutePath);
		new AsyncTask<String, Void, Void>() {
			@Override
			protected Void doInBackground (String... params) {
				File file = new File(params[0]);
				try {
					BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
					byte[] buf = new byte[1024];
					int len;
					while (( len = in.read(buf) ) > 0) {
						out.write(buf, 0, len);
					}
					out.close();
					in.close();
					resultCallBack.onResultCallBack(file, null);
				} catch (Exception e) {
					Log.e(LOG_TAG, Log.getStackTraceString(e));
					resultCallBack.onResultCallBack(file, e);
				}
				return null;
			}
		}.execute(fileAbsolutePath);
	}

	private String getDownloadDirectoryAbsolutePath (String filename) {
		File file = new File(Environment.getExternalStorageDirectory().getPath(), "quezx/downloads/");
//    Log.e(LOG_TAG, file.exists() + "");
		if (!file.exists()) {
			file.mkdirs();
		}
		return ( file.getAbsolutePath() + "/" + filename ); // uriString; // uriString
	}

	public String UriToBase64(Context context, Uri uri) {
		String base64encoding = "";
		try {
			InputStream inputStream = context.getContentResolver().openInputStream(uri);
			if (inputStream == null) return base64encoding;
			byte[] byteArray = IOUtils.toByteArray(inputStream);
			base64encoding = encodeBase64(byteArray);
		} catch (Exception e) {
			Timber.e(Log.getStackTraceString(e));
		}
		return base64encoding;
	}

	public String encodeBase64(byte[] byteArray) {
		if (byteArray == null) return "";
		return Base64.encodeToString(byteArray, Base64.NO_WRAP);
	}

	public String getFileName(Uri uri) {
		String name = "";
		String[] directoryTree = uri.getPath().split("/");
		if (directoryTree.length > 0) {
			name = directoryTree[directoryTree.length - 1];
		}
		return name;
	}
}
