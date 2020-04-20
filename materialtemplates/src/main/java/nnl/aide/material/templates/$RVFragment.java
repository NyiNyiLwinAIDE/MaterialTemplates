package nnl.aide.material.templates;

import android.support.v4.app.*;
import java.util.*;
import android.view.*;
import android.os.*;
import android.support.v7.widget.*;
import nnl.aide.material.templates.$RVAdapter.*;

public abstract class $RVFragment<T> extends Fragment
implements $RVAdapter.RVCallback<T>
{
	private RecyclerView rv;
	private $RVAdapter<T> adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view=inflater.inflate(R.layout.nnl_rv_layout,container,false);
		rv=view.findViewById(R.id.recyclerView);
		if(_useGridLayout()){
			rv.setLayoutManager(new GridLayoutManager(getActivity(),_getColumns()));

		}else{
			rv.setLayoutManager(new LinearLayoutManager(getActivity()));

		}
		adapter=new $RVAdapter<T>(this);
		rv.setAdapter(adapter);
		return view;
	}

	public abstract boolean _useGridLayout();

	public int _getColumns(){
		return 2;
	}
}

