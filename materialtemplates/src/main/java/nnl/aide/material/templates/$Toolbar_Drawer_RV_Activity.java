package nnl.aide.material.templates;

import android.os.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public abstract class $Toolbar_Drawer_RV_Activity<T> extends $Toolbar_Drawer_Activity
implements $RVAdapter.RVCallback<T>
{
	@Override
	public void _itemClick(View view, int position, T item)
	{
		_rvItem_Click(view, position, item);
	}

	private RecyclerView rv;
	private $RVAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nnl_rv_layout);
		rv=(RecyclerView)findViewById(R.id.recyclerView);

		if(_useGridLayout()){
			rv.setLayoutManager(new GridLayoutManager(this,_getColumns()));

		}else{
			rv.setLayoutManager(new LinearLayoutManager(this));
		}

		adapter=new $RVAdapter<T>(this);
		//_fillRVData(adapter);
		rv.setAdapter(adapter);
	}

	protected void _rvItem_Click(View view, int position, T item){}
	protected abstract boolean _useGridLayout()
	protected int _getColumns(){
		return 2;
	}
}

