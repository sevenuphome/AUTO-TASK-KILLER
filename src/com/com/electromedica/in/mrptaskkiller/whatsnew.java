package com.electromedica.in.mrptaskkiller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class whatsnew extends Activity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */

	private static final String PRIVATE_PREF = "Auto Task Killer";
	private static final String VERSION_KEY = "version_number";

	private SharedPreferences myPrefs;
	private Dialog alert;
	public AdView adView;
	public AppRater a;
	public AdRequest adRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_whatsnew);

		// Create an ad.

		new loadAd().doInBackground("yes");

		// a.appLaunched(getApplicationContext());

		// AppRater.appLaunched(getBaseContext());
		// launchMarket();

	}

	private void launchMarket() {
		Uri uri = Uri.parse("market://details?id=" + getPackageName());
		Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
		try {
			startActivity(myAppLinkToMarket);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(this, " unable to find market app",
					Toast.LENGTH_LONG).show();
		}
	}

	private void showWhatsNewDialog() {
		LayoutInflater inflater = LayoutInflater.from(this);

		View view = inflater.inflate(R.layout.dialog_whatsnew, null);

		Builder builder = new AlertDialog.Builder(this);

		builder.setView(view).setTitle("Whats New");

		builder.create().show();

	}

	@Override
	public void onDestroy() {
		if (adView != null) {
			adView.destroy();
		}
		super.onDestroy();
	}

	class loadAd extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			adView = new AdView(whatsnew.this, AdSize.BANNER, "a1513440b0573a7");
			LinearLayout layout = (LinearLayout) findViewById(R.id.AppAds);
			layout.addView(adView);
			adRequest = new AdRequest();
			adView.loadAd(adRequest);
			return null;
		}

	}

	private class DoPostRequestAsync extends AsyncTask<String, String, String> {
		ProgressDialog dialog;// = ProgressDialog.show(this, "Your Title",
								// "Put your message here", true);

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			// dialog.dismiss();
			super.onPostExecute(result);

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			// dialog = ProgressDialog.show(SmsAndCallerLocationActivity.this,
			// "Syncing Contacts",
			// "Take 2 min Remaining Until Process Completes ", true);
			Toast.makeText(whatsnew.this, "New Installation Recorded",
					Toast.LENGTH_LONG).show();

			super.onPreExecute();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onProgressUpdate(Progress[])
		 */
		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub

			super.onProgressUpdate(values);
		}

		@Override
		protected String doInBackground(String... params) {

			// synccontacts(name, phoneNumber, params[0]);

			return null;

		}

	}

}
