package nnl.aide.material.templates;

import android.os.*;
import android.support.design.widget.*;
import android.support.v4.app.*;
import android.support.v4.view.*;
import android.support.v7.app.*;
import android.view.*;
import java.util.*;
import android.app.Activity;

public abstract class $VPFragment extends Fragment
{
	View main;
	ViewPager vp;
	List<Fragment> fragmentList=new ArrayList<>();
	List<String>titles=new ArrayList<>();
	VPAdapter adapter;
	TabLayout tl;

	public $VPFragment(AppCompatActivity a){
		adapter = new VPAdapter(a.getSupportFragmentManager());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View main = getActivity().getLayoutInflater().inflate(R.layout.nnl_vp_layout, null);
		vp = main.findViewById(R.id.viewPager);
		vp.setAdapter(adapter);
		tl = main.findViewById(R.id.tabLayout);
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
		return main;
	}

	protected abstract void _page_Selected(TabLayout.Tab t)
	protected abstract void _page_Reselected(TabLayout.Tab t)
	protected abstract void _page_Unselected(TabLayout.Tab t)

	protected void selectPage(int i)
	{
		vp.setCurrentItem(i, true);
	}


	public void addPage(Fragment f, String title)
	{
		fragmentList.add(f);
		titles.add(title);
		adapter.notifyDataSetChanged();
	}

	public void addAllPages(ArrayList<Fragment> fragments, ArrayList<String>ts)
	{
		fragmentList.addAll(fragments);
		titles.addAll(ts);
		adapter.notifyDataSetChanged();
	}

	public void removePage(int pos)
	{
		fragmentList.remove(pos);
		titles.remove(pos);
		adapter.notifyDataSetChanged();
	}

	public void removePage(Fragment f)
	{
		fragmentList.remove(fragmentList.indexOf(f));
		titles.remove(fragmentList.indexOf(f));
		adapter.notifyDataSetChanged();
	}

	public void removeAllPages()
	{
		fragmentList.clear();
		titles.clear();
		adapter.notifyDataSetChanged();
	}

	public class VPAdapter extends FragmentStatePagerAdapter
	{
		public VPAdapter(FragmentManager fm)
		{
			super(fm);
			fragmentList = new ArrayList<>();
			titles = new ArrayList<>();
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

