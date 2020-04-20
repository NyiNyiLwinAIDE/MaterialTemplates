package nnl.aide.material.templates;
import android.content.res.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v4.widget.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;

public abstract class $Toolbar_Drawer_Activity extends $Toolbar_Activity
{
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private NavigationView navigationView;
	private View header;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		navigationView = (NavigationView) findViewById(R.id.nnl_navigation_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.nnl_drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 0, 0);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setupNV();

		navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
				@Override
				public boolean onNavigationItemSelected(MenuItem menuItem)
				{
					menuItem.setChecked(true);
					mDrawerLayout.closeDrawers();
					_menuItem_Click(menuItem.getItemId());
					return true;
				}
			});
	}

	protected void _menuItem_Click(int id)
	{}

	private void setupNV()
	{
		header = navigationView.inflateHeaderView(_getDrawerHeaderLayoutResource());
		navigationView.getMenu().clear();
		navigationView.inflateMenu(_getDrawerMenuResource());
	}

	protected int _getDrawerHeaderLayoutResource()
	{
		return R.layout.nnl_drawer_header_layout;
	}

	protected abstract int _getDrawerMenuResource();

	protected void $setDrawerHeaderImage(int resid)
	{
		try
		{
			ImageView ivHeader=header.findViewById(R.id.nnl_ivDrawerHeader);
			ivHeader.setImageResource(resid);
		}
		catch (Exception e)
		{}
	}

	protected void $setDrawerHeaderText(String txt)
	{
		try
		{
			TextView headerTextView=header.findViewById(R.id.nnl_tvDrawerHeader);
			headerTextView.setText(txt);
		}
		catch (Exception e)
		{}
	}

	@Override

    protected void onPostCreate(Bundle savedInstanceState)
	{
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
	{
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

	@Override
    public boolean onOptionsItemSelected(MenuItem item)
	{
        if (mDrawerToggle.onOptionsItemSelected(item))
		{
            return true;
        }
        _optionsItem_Click(item.getItemId());

        return super.onOptionsItemSelected(item);
    }

	@Override
	protected int _getTemplateLayoutId()
	{
		if (_hasCollapsingToolbar())
		{
			return R.layout.nnl_collapsing_toolbar_drawer_layout;
		}
		else
		{
			return R.layout.nnl_toolbar_drawer_layout;
		}
	}
}

