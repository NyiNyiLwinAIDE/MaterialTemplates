package nnl.aide.material.templates;

import android.support.v7.widget.*;
import android.view.*;
import java.util.*;

public class $RVAdapter<T> extends RecyclerView.Adapter<$RVAdapter.ViewHolder>
{
	public static interface RVCallback<T>
	{
		public void _itemClick(View view, int position, T item)
		public void _itemLongClick(View view, int position, T item)
		public View _createView(ViewGroup viewGroup, int viewType)
		public void _bindView(T item, $RVAdapter.ViewHolder viewHolder,int position)
		public int _getItemViewType(T item,int position)
		public boolean _addToFilterList(T item,int pos)
	}

	private List<T> items,filteredItems;
	private RVCallback<T> listener;

	public $RVAdapter(RVCallback<T> listener)
	{
		super();
		this.listener = listener;
		this.items=new ArrayList<>();
		this.filteredItems=new ArrayList<>();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
	{
		return new ViewHolder(listener._createView(viewGroup, viewType));
	}

	@Override
	public void onBindViewHolder($RVAdapter.ViewHolder<T> holder, int position)
	{
		listener._bindView(getItem(position), holder,position);
	}

	@Override
	public int getItemCount()
	{
		return filteredItems.size();
	}

	@Override
	public int getItemViewType(int position)
	{
		return listener._getItemViewType(getItem(position),position);
	}

	public T getItem(int index)
	{
		return filteredItems.get(index);
	}

	public void setList(List<T> list)
	{
		items.clear();
		items.addAll(list);
		filteredItems.clear();
		filteredItems.addAll(items);
		notifyDataSetChanged();
	}

	public List<T> getList()
	{
		return items;
	}

	public void addItem(T item){
		items.add(item);
		filteredItems = new ArrayList<>();
		filteredItems.addAll(items);
		notifyDataSetChanged();
	}

	public void addAll(List<T> list)
	{
		items.addAll(list);
		filteredItems.clear();
		filteredItems.addAll(items);
		notifyDataSetChanged();
	}

	public void reset()
	{
		items.clear();
		filteredItems.clear();
		notifyDataSetChanged();
	}
	
	public void showAll(){
		filteredItems.clear();
		filteredItems.addAll(items);
		notifyDataSetChanged();
	}

	public void filter()
	{
		filteredItems.clear();
		if (listener!=null)
		{
			for (int pos=0;pos<items.size();pos++)
			{
				if (listener._addToFilterList(items.get(pos),pos))
				{ 
					filteredItems.add(items.get(pos));
				}
			}
		}
		notifyDataSetChanged();
	}

	//ViewHolder
	//==================
	public class ViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener,
	View.OnLongClickListener
	{

		@Override
		public boolean onLongClick(View view)
		{
			if (listener != null){
				listener._itemClick(view, getAdapterPosition(), getItem(getAdapterPosition()));
				return true;
			}
			return false;
		}


		private Map<Integer, View> views;

		@Override
		public void onClick(View view)
		{
			if (listener != null)
				listener._itemClick(view, getAdapterPosition(), getItem(getAdapterPosition()));
		}


		public ViewHolder(View view)
		{
			super(view);
			views = new HashMap<>();
			views.put(0, view);

			if (listener != null)
				view.setOnClickListener(this);
		}

		public void initViewList(int[] idList)
		{
			for (int id : idList)
				initViewById(id);
		}

		public void initViewById(int id)
		{
			View view = getView().findViewById(id);
			if (view != null)
				views.put(id, view);
		}

		public View getView()
		{
			return getView(0);
		}

		public View getView(int id)
		{
			if (views.containsKey(id))
				return views.get(id);
			else
				initViewById(id);

			return views.get(id);
		}

	}
}


