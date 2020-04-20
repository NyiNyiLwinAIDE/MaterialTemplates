package nnl.aide.material.templates;

import android.os.*;
import android.support.design.widget.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.support.v4.content.*;
import android.widget.Toast;

public abstract class $Toolbar_Activity extends $BaseActivity
{
	private Toolbar toolbar;
	private CollapsingToolbarLayout ctl;
	private ImageView ivCTL;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setupToolbar();
	}

	protected void setupToolbar(){
		toolbar=(Toolbar)findViewById(R.id.nnl_toolbar);
		setSupportActionBar(toolbar);
		if(_hasCollapsingToolbar()){
			ctl=(CollapsingToolbarLayout)findViewById(R.id.nnl_ctl);
			ivCTL=(ImageView)findViewById(R.id.ivCTL);
		}
	}

	protected void $setTitleEnabled(boolean b){
		if(ctl!=null) ctl.setTitleEnabled(b);
	}
	protected abstract int _getMenuXmlId();
	protected abstract boolean _hasCollapsingToolbar()
	protected abstract int _getSearchViewId();
	protected void _searchTextChange(String searchText){}
	protected void _searchTextSubmitted(String searchText){}
	
	@Override
	protected int _getTemplateLayoutId()
	{
		if(_hasCollapsingToolbar())
			return R.layout.nnl_collapsing_toolbar_layout;
		else
			return R.layout.nnl_toolbar_layout;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		if(_getMenuXmlId()!=0){
			getMenuInflater().inflate(_getMenuXmlId(),menu);
			if(_getSearchViewId()!=0){
				MenuItem myActionMenuItem = menu.findItem(_getSearchViewId());
				SearchView sv = (SearchView) myActionMenuItem.getActionView();
				sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
						@Override
						public boolean onQueryTextSubmit(String p1)
						{
							return false;
						}

						@Override
						public boolean onQueryTextChange(String p1)
						{
							_searchTextChange(p1);
							return true;
						}
					});
			}
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if(item.getItemId()==android.R.id.home){
			_home_Click();
		}else{
			_optionsItem_Click(item.getItemId());
		}
		return super.onOptionsItemSelected(item);
	}

	//Events
	public void _optionsItem_Click(int itemId){}
	public void _home_Click(){}

	protected void $addBackArrow(){
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	protected void $setCollapsingToolbarImage(int resid){
		if(ivCTL!=null) ivCTL.setImageResource(resid);
		//.setImageDrawable(ContextCompat.getDrawable(this, resid));
	}
}

