package nnl.aide.material.templates;

import android.support.v7.app.*;
import android.os.*;
import android.widget.*;
import android.view.*;
import android.support.design.widget.*;
import android.support.v4.app.*;
import android.widget.AdapterView.*;
import java.util.*;
import android.content.*;
import android.support.v4.content.*;
import android.content.pm.*;

public abstract class $BaseActivity extends AppCompatActivity
{
	protected FrameLayout nnl_main;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		super.setContentView(_getTemplateLayoutId());
		nnl_main = (FrameLayout)findViewById(R.id.nnl_main);
	}

	protected abstract int _getTemplateLayoutId();

	@Override
    public void setContentView(int layoutResID)
	{
        if (nnl_main != null)
		{
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
            View stubView = inflater.inflate(layoutResID, nnl_main, false);
            nnl_main.addView(stubView, lp);
        }
    }

    @Override
    public void setContentView(View view)
	{
        if (nnl_main != null)
		{
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
            nnl_main.addView(view, lp);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params)
	{
        if (nnl_main != null)
		{
            nnl_main.addView(view, params);
        }
    }

	//Permission request
	//==================
	public boolean $checkAndRequestPermissions(int code)
	{
		if (Build.VERSION.SDK_INT >= 23)
		{
			List<String> listPermissionsNeeded = $getPermissions();
			List<String> listPermissionsAssign=new ArrayList<>();
			for (String per:listPermissionsNeeded)
			{
				if (ContextCompat.checkSelfPermission(getApplicationContext(), per) != PackageManager.PERMISSION_GRANTED)
				{
					listPermissionsAssign.add(per);
				}
			}

			if (!listPermissionsAssign.isEmpty())
			{
				ActivityCompat.requestPermissions(this, listPermissionsAssign.toArray(new String[listPermissionsAssign.size()]), code);
				return false;
			} 
		}
		return true;
	}

	public boolean $checkAndRequestPermissions(String[] permissions, int code)
	{
		if (Build.VERSION.SDK_INT >= 23)
		{
			List<String> listPermissionsAssign=new ArrayList<>();

			for(String permission:permissions){
				if (ContextCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED)
				{
					listPermissionsAssign.add(permission);
				}
			}

			if (!listPermissionsAssign.isEmpty())
			{
				ActivityCompat.requestPermissions(this, listPermissionsAssign.toArray(new String[listPermissionsAssign.size()]), code);
				return false;
			} 
		}
		return true;
	}

	public List<String> $getPermissions()
	{
		List<String> per=new ArrayList<>();
		try
		{
			PackageManager pm = getApplicationContext().getPackageManager();
			PackageInfo pi=pm.getPackageInfo(getApplicationContext().getPackageName(), PackageManager.GET_PERMISSIONS);
			String permissionInfo[]=pi.requestedPermissions;
			for (String p : permissionInfo)
			{
				per.add(p);
			}
		}
		catch (Exception e)
		{ }
		return per;
	}

	private void checkResult(int requestCode, String permissions[], int[] grantResults)
	{
		List<String> listPermissionsNeeded = $getPermissions();
		Map<String, Integer> perms = new HashMap<>();
		for (String permission:listPermissionsNeeded)
		{
			perms.put(permission, PackageManager.PERMISSION_GRANTED);
		}
		if (grantResults.length > 0)
		{
			for (int i = 0; i < permissions.length; i++)
			{
				perms.put(permissions[i], grantResults[i]);
			}
			boolean isAllGranted=true;
			for (String permission:listPermissionsNeeded)
			{
				if (perms.get(permission) == PackageManager.PERMISSION_DENIED)
				{
					isAllGranted = false;
					break;
				}
			}
			if (!isAllGranted)
			{
				boolean shouldRequest=false;
				for (String permission:listPermissionsNeeded)
				{
					if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission))
					{
						shouldRequest = true;
						break;
					}
				}

				if (shouldRequest)
				{
					_permissionCancelledAndCanRequest(requestCode);
				}
				else
				{
					_permissionCancelledAndCannotRequest();
				}
			}else{
				_permissionGranted(requestCode);
			}
		}
	}

	public void _permissionGranted(int code){}

	public void _permissionCancelledAndCanRequest(int code){}

	public void _permissionCancelledAndCannotRequest(){}

	public boolean isPermissionGranted(String perm){
		return (ContextCompat.checkSelfPermission(getApplicationContext(), perm) == PackageManager.PERMISSION_GRANTED);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
		checkResult(requestCode,permissions, grantResults);
	}

	//FAB
	//========

	public void _fab_Click(View v)
	{}

	protected void $hideFAB()
	{
		((FloatingActionButton)findViewById(R.id.nnl_fab)).setVisibility(View.GONE);
	}

	protected void $setFabImage(int resid)
	{
		((FloatingActionButton)findViewById(R.id.nnl_fab)).setImageResource(resid);
	}

	//Fragment methods

	protected void $addFragment(int containerId, Fragment f, String tag)
	{
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(containerId, f, tag);
		ft.commit();
	}

	protected void $replaceFragment(int containerId, Fragment f, String tag)
	{
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(containerId, f, tag);
		ft.commit();
	}

	protected void $addFragment(Fragment f, String tag)
	{
		if (nnl_main != null)
		{
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.add(R.id.nnl_main, f, tag);
			ft.commit();
		}
	}

	protected void $replaceFragment(Fragment f, String tag)
	{
		if (nnl_main != null)
		{
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.nnl_main, f, tag);
			ft.commit();
		}
	}

	protected void $showFragment(Fragment f)
	{
		if (nnl_main != null)
		{
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.show(f);
			ft.commit();
		}
	}

	protected void $hideFragment(Fragment f)
	{
		if (nnl_main != null)
		{
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.hide(f);
			ft.commit();
		}
	}

	//Toast

	protected void $toast(String msg)
	{
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	protected void $toast(String msg, int dur)
	{
		Toast.makeText(this, msg, dur).show();
	}

	//SnackBar

	public void $snackBar(String msg){
		Snackbar.make(nnl_main,msg,Snackbar.LENGTH_SHORT).show();
	}

	public void $snackBar(String msg,String ok){
		final Snackbar sb=Snackbar.make(nnl_main,msg,Snackbar.LENGTH_INDEFINITE);
		sb.setAction(ok, new View.OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					sb.dismiss();
				}
			})
			.show();
	}

	//Spinner helper

	protected void $addSpinner(final int spid, String[] data)
	{
		$addSpinner(spid,(List<String>)Arrays.asList(data));
	}

	protected void $addSpinner(final int spid, List<String> data)
	{
		Spinner sp=(Spinner)findViewById(spid);
		//sp.setTag(spid);
		ArrayAdapter adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, data);
		sp.setAdapter(adapter);
		sp.setOnItemSelectedListener(new OnItemSelectedListener(){

				@Override
				public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4)
				{
					_spinner_ItemSelected(spid, p3);
				}

				@Override
				public void onNothingSelected(AdapterView<?> p1)
				{
				}
			});
	}

	protected void _spinner_ItemSelected(int id,int pos)
	{}

	//ListView helper

	protected void $addListView(final int lvid,String[] data)
	{
		$addListView(lvid,(ArrayList<String>)Arrays.asList(data));
	}

	protected void $addListView(final int lvid, ArrayList<String> data)
	{
		ListView lv=(ListView)findViewById(lvid);
		lv.setTag(lvid);
		ArrayAdapter adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
				{
					_listView_ItemClick(lvid, p3);
				}
			});
		lv.setOnItemLongClickListener(new OnItemLongClickListener(){

				@Override
				public boolean onItemLongClick(AdapterView<?> p1, View p2, int p3, long p4)
				{
					_listView_ItemLongClick(lvid, p3);
					return true;
				}
			});
	}

	protected void _listView_ItemClick(int id, int pos)
	{}

	protected void _listView_ItemLongClick(int id, int pos)
	{
		_listView_ItemClick(id,pos);
	}

	//Button helper

	protected void $addButton(final int resid)
	{
		Button bt=(Button)findViewById(resid);
		bt.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					_button_Click(p1);
				}
			});

		bt.setOnLongClickListener(new OnLongClickListener(){

				@Override
				public boolean onLongClick(View p1)
				{
					_button_LongClick(p1);
					return true;
				}
			});
	}

	protected void _button_Click(View v)
	{}
	protected void _button_LongClick(View v)
	{
		_button_Click(v);
	}

	//SharedPreferences

	protected String $readFromSharedPrefs(String key, String defaultVal)
	{
		SharedPreferences sharedpreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
		String ret= sharedpreferences.getString(key, defaultVal);
		return ret;
	}

	protected void $writeToSharedPrefs(String key, String val)
	{
		SharedPreferences sharedpreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
		SharedPreferences.Editor  editor = sharedpreferences.edit();
		editor.putString(key, val);
		editor.apply();
	}

	//Dialogs

	protected void $showMsg(String msg)
	{
		$showMsg("",msg);
	}

	protected void $showMsg(String title, String msg)
	{
		$Dialog.showMsg(this,title,msg);
	}

	protected void $showDialog(String title, String msg, String yes, String no, String cancel, final $Dialog.DialogResponse dr)
	{
		$Dialog.showDialog(this,title,msg,yes,no,cancel,dr);
	}

	protected void $showDatePicker(final $Dialog.DatePickerResponse resp){
		$Dialog.showDatePicker(this,resp);
	}

	protected void $showTimePicker(final $Dialog.TimePickerResponse resp){
		$Dialog.showTimePicker(this,resp);
	}

	protected void $showMultiChoiceDialog(String title,
										 String[] choices,boolean[]checked,String ok,String cancel,final $Dialog.MultiChoiceResponse resp){
		$Dialog.showMultiChoiceDialog(this,title,choices,checked,ok,cancel,resp);
	}

	protected void $showSingleChoiceDialog(String title,
										  String[] choices,int checked,String ok,String cancel,final $Dialog.SingleChoiceResponse resp){
		$Dialog.showSingleChoiceDialog(this,title,choices,checked,ok,cancel,resp);
	}

}

