package nnl.aide.material.templates;
import android.os.*;
import android.support.design.widget.*;
import android.view.*;

public abstract class $Toolbar_Bottom_Navigation_Activity extends $Toolbar_Activity
implements BottomNavigationView.OnNavigationItemSelectedListener
{

	@Override
	protected int _getTemplateLayoutId()
	{
		return R.layout.nnl_toolbar_bottom_navigation_layout;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		BottomNavigationView navigation = (BottomNavigationView)findViewById(R.id.nnl_bottom_nv);
		navigation.setOnNavigationItemSelectedListener(this);
	}

	@Override
	public boolean onNavigationItemSelected(MenuItem p1)
	{
		_navigationItem_Click(p1);
		return true;
	}

	protected abstract void _navigationItem_Click(MenuItem item)


}

