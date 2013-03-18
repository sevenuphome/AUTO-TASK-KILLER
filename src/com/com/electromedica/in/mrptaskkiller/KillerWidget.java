package com.electromedica.in.mrptaskkiller;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class KillerWidget extends AppWidgetProvider {

	public static String ACTION_WIDGET_RECEIVER = "ActionReceiverUltraSimpleTaskKillerWidget";

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.main);

		Intent clicSurBtnKillIntent = new Intent(context, KillerWidget.class);
		clicSurBtnKillIntent.setAction(ACTION_WIDGET_RECEIVER);
		PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context,
				0, clicSurBtnKillIntent, 0);
		remoteViews.setOnClickPendingIntent(R.id.btnKill, actionPendingIntent);

		appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// v1.5 fix that doesn't call onDelete Action
		final String action = intent.getAction();
		if (AppWidgetManager.ACTION_APPWIDGET_DELETED.equals(action)) {
			final int appWidgetId = intent.getExtras().getInt(
					AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
			if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
				this.onDeleted(context, new int[] { appWidgetId });
			}
		} else {
			// On a cliqué sur le bouton
			if (intent.getAction().equals(ACTION_WIDGET_RECEIVER)) {

				// Liste des packages à ne pas tuer.
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
					// Log.d("TKTKTK",
					// "========="+processus.pid+" : "+processus.processName);
					String packageName = processus.processName.split(":")[0];
					if (!context.getPackageName().equals(packageName)
							&& !reservedPackages.contains(packageName)) {
						am.restartPackage(packageName);
						compteProcessusTues++;
					}
				}
				// Auto-kill (désactivé dans le cas d'un widget)
				// am.restartPackage(context.getPackageName());

				/**
				 * Gestion de la notification
				 */
				PendingIntent contentIntent = PendingIntent.getActivity(
						context, 0, intent, 0);
				NotificationManager notificationManager = (NotificationManager) context
						.getSystemService(Context.NOTIFICATION_SERVICE);
				Notification noty = new Notification(R.drawable.icon, context
						.getResources().getQuantityString(
								R.plurals.nb_process_tues,
								((compteProcessusTues == 0) ? 1
										: compteProcessusTues),
								compteProcessusTues),
						System.currentTimeMillis());
				noty.setLatestEventInfo(
						context,
						context.getString(R.string.app_name),
						context.getResources().getQuantityString(
								R.plurals.nb_process_tues,
								((compteProcessusTues == 0) ? 1
										: compteProcessusTues),
								compteProcessusTues), contentIntent);
				notificationManager.notify(1, noty);

			}
			super.onReceive(context, intent);
		}
	}
}
