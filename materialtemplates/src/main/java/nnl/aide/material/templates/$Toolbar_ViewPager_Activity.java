package nnl.aide.material.templates;

import android.os.*;
import android.support.design.widget.*;
import android.support.v4.app.*;
import android.support.v4.view.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import java.util.*;

import android.support.v7.widget.Toolbar;
import android.widget.TabHost.*;
import android.support.design.widget.TabLayout.*;

public abstract class $Toolbar_ViewPager_Activity extends $Toolbar_Activity
{

	private ViewPager vp;
	private List<Fragment> fragmentList;
	private List<String>titles;
	private VPAdapter adapter;
	private TabLayout tl;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		vp=(ViewPager)findViewById(R.id.nnl_viewpager);
		adapter=new VPAdapter(getSupportFragmentManager());
		vp.setAdapter(adapter);
		tl=(TabLayout)findViewById(R.id.nnl_tablayout);
		tl.setupWithViewPager(vp);
		tl.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

				@Override
				public void onTabSelected(TabLayout.Tab p1)
				{
					_page_Selected(p1);
				}

				@Override
				public void onTabUnselected(TabLayout.Tab p1)
				{
					_page_Unselected(p1);
				}

				@Override
				public void onTabReselected(TabLayout.Tab p1)
				{
					_page_Reselected(p1);
				}
			});

	}

	protected void _page_Selected(Tab t){}
	protected void _page_Reselected(Tab t){}
	protected void _page_Unselected(Tab t){}

	protected void $selectPage(int i){
		vp.setCurrentItem(i,true);
	}

	@Override
	protected int _getTemplateLayoutId()
	{
		if(_hasCollapsingToolbar())
			return R.layout.nnl_collapsing_toolbar_viewpager_layout;
		else
			return R.layout.nnl_toolbar_viewpager_layout;
	}

	public void $addPage(Fragment f,String title){
		fragmentList.add(f);
		titles.add(title);
		adapter.notifyDataSetChanged();
	}

	public void $addAllPages(ArrayList<Fragment> fragments,ArrayList<String>ts){
		fragmentList.addAll(fragments);
		titles.addAll(ts);
		adapter.notifyDataSetChanged();
	}

	public void $removePage(int pos){
		fragmentList.remove(pos);
		titles.remove(pos);
		adapter.notifyDataSetChanged();
	}

	public void $removePage(Fragment f){
		fragmentList.remove(fragmentList.indexOf(f));
		titles.remove(fragmentList.indexOf(f));
		adapter.notifyDataSetChanged();
	}

	public void $removeAllPages(){
		fragmentList.clear();
		titles.clear();
		adapter.notifyDataSetChanged();
	}

	public class VPAdapter extends FragmentStatePagerAdapter
	{
		public VPAdapter(FragmentManager fm)
		{
			super(fm);
			fragmentList=new ArrayList<>();
			titles=new ArrayList<>();
		}

		@Override
		public Fragment getItem(int position)
		{
			return fragmentList.get(position);
		}

		@Override
		public int getCount()
		{
			return fragmentList.size();
		}

		@Override
		public CharSequence getPageTitle(int position)
		{
			return titles.get(position);
		}
	}
}

