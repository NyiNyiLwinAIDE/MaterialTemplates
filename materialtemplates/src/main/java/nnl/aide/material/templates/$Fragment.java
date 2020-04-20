package nnl.aide.material.templates;

import android.support.v4.app.*;
import android.view.*;
import android.os.*;
import android.app.Activity;

public abstract class $Fragment extends Fragment
{
	protected Activity mActivity;
	protected FragmentCallback mFragmentCallback;
	protected int layout;
	
	public $Fragment(Activity a,int layout, FragmentCallback cb){
		this.mActivity=a;
		this.layout=layout;
		this.mFragmentCallback=cb;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view=inflater.inflate(layout,container,false);
		_initializeViews(view);
		return view;
	}

	public boolean handleBackPress(){
		return false;
	}

	public abstract void _initializeViews(View parent)
	public void onBackPressed(){}

	

	public static interface FragmentCallback
	{
		public void _fragmentCallback(String who,Object[] data);
	}
}

