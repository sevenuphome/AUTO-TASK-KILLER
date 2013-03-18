package com.electromedica.in.mrptaskkiller;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class fireonscreenlockandrelease extends BroadcastReceiver {
	/**
	 * @see android.content.BroadcastReceiver#onReceive(Context,Intent)
	 */
	public static String ACTION_WIDGET_RECEIVER = "ActionReceiverUltraSimpleTaskKillerWidget";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Put your code here
		if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {

			List<String> reservedPackages = new ArrayList<String>();
			reservedPackages.add("system");
			reservedPackages.add("com.android.launcher2");
			reservedPackages.add("com.android.inputmethod.latin");
			reservedPackages.add("com.android.phone");
			reservedPackages.add("com.android.wallpaper");
			reservedPackages.add("com.google.process.gapps");
			reservedPackages.add("android.process.acore");
			reservedPackages.add("android.process.media");

			// On tue tous les processus, sauf ceux de la liste
			int compteProcessusTues = 0;
			ActivityManager am = (ActivityManager) context
					.getSystemService(Activity.ACTIVITY_SERVICE);
			List<RunningAppProcessInfo> listeProcessus = am
					.getRunningAppProcesses();
			for (RunningAppProcessInfo processus : listeProcessus) {
				Log.d("TKTKTK", "=========" + processus.pid + " : "
						+ processus.processName);
				String packageName = processus.processName.split(":")[0];
				if (!context.getPackageName().equals(packageName)
						&& !reservedPackages.contains(packageName)) {
					am.restartPackage(packageName);
					compteProcessusTues++;
				}
			}

		} else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {

			List<String> reservedPackages = new ArrayList<String>();
			reservedPackages.add("system");
			reservedPackages.add("com.android.launcher2");
			reservedPackages.add("com.android.inputmethod.latin");
			reservedPackages.add("com.android.phone");
			reservedPackages.add("com.android.wallpaper");
			reservedPackages.add("com.google.process.gapps");
			reservedPackages.add("android.process.acore");
			reservedPackages.add("android.process.media");

			// On tue tous les processus, sauf ceux de la liste
			int compteProcessusTues = 0;
			ActivityManager am = (ActivityManager) context
					.getSystemService(Activity.ACTIVITY_SERVICE);
			List<RunningAppProcessInfo> listeProcessus = am
					.getRunningAppProcesses();
			for (RunningAppProcessInfo processus : listeProcessus) {
				Log.d("TKTKTK", "=========" + processus.pid + " : "
						+ processus.processName);
				String packageName = processus.processName.split(":")[0];
				if (!context.getPackageName().equals(packageName)
						&& !reservedPackages.contains(packageName)) {
					am.restartPackage(packageName);
					compteProcessusTues++;
				}
			}

		}
	}
}
