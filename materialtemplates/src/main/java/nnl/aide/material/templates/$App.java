package nnl.aide.material.templates;

import android.app.*;
import android.content.*;
import android.os.*;
import java.util.*;

public class $App extends Application
{
	public static Context context;
	public static final String CHANNEL_ID="NotiChannel";
	private static Map<String,Object> objMap;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		context=getApplicationContext();
		objMap=new HashMap<>();
		createNotificationChannel();
	}

	private void createNotificationChannel()
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
		{
			CharSequence name = "NotiChannel";
			String description = "NotiChannel";
			int importance = NotificationManager.IMPORTANCE_DEFAULT;
			NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
			channel.setDescription(description);
			NotificationManager notificationManager = getSystemService(NotificationManager.class);
			notificationManager.createNotificationChannel(channel);
		}
	}
	
	public static void setGlobal(String name,Object obj){
			objMap.put(name,obj);
	}
	
	public static Object getGlobal(String name){
		return objMap.get(name);
	}

}

