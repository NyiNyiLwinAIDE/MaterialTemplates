package nnl.aide.material.templates;
import android.app.*;
import android.content.*;
import android.widget.*;
import java.util.*;

public class $Dialog
{
	public static void showMsg(Activity a,String title, String msg)
	{
		final AlertDialog ad=new AlertDialog.Builder(a).create();

		ad.setTitle(title);
		ad.setMessage(msg);

		ad.setButton(
			AlertDialog.BUTTON_POSITIVE,
			"Ok", 
			new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					ad.dismiss();
				}
			});
		ad.show();
	}

	public static void showMultiChoiceDialog(Activity a,String title, String[] choices,final boolean[] checkedItems,String ok, String cancel, final MultiChoiceResponse dr)
	{
		new AlertDialog.Builder(a)
			.setTitle(title)
			.setPositiveButton(ok, new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					dr.onResponse(checkedItems);
				}
			})
			.setMultiChoiceItems(choices, checkedItems, new DialogInterface.OnMultiChoiceClickListener(){

				@Override
				public void onClick(DialogInterface p1, int index, boolean isChecked)
				{
					checkedItems[index]=isChecked;
				}


			})
			.setNeutralButton(cancel, null)
			.show();
	}

	public static void showSingleChoiceDialog(Activity a,String title,String[] choices,final int selected,String ok, String cancel, final SingleChoiceResponse dr)
	{
		final int[] selectedIdx=new int[]{selected};
		new AlertDialog.Builder(a)
			.setTitle(title)
			.setPositiveButton(ok, new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					dr.onResponse(selectedIdx[0]);
				}
			})
			.setSingleChoiceItems(choices,selected, new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int index)
				{
					selectedIdx[0]=index;
				}


			})
			.setNeutralButton(cancel, null)
			.show();
	}

	public static void showDialog(Activity a,String title, String msg, String yes, String no, String cancel, final DialogResponse dr)
	{
		AlertDialog.Builder builder=new AlertDialog.Builder(a)
			.setTitle(title)
			.setMessage(msg)
			.setPositiveButton(yes, new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					dr.onResponse(p2);
				}
			});

		if (no != "")
		{
			builder.setNegativeButton(no, new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface p1, int p2)
					{
						dr.onResponse(p2);
					}
				});
		}

		if (cancel != "")
		{
			builder.setNeutralButton(cancel, null);
		}

		builder.show();
	}

	public static void showTimePicker(Activity a,final TimePickerResponse resp){
		Calendar mCalendar = Calendar.getInstance();
		TimePickerDialog timePickerDialog = new TimePickerDialog(a, new TimePickerDialog.OnTimeSetListener() {
				@Override
				public void onTimeSet(TimePicker p1, int h, int m)
				{
					resp.onTimeSet(h,m);
				}
			},mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true);
		timePickerDialog.show();
	}

	public static void showDatePicker(Activity a,final DatePickerResponse resp){
		Calendar mCalendar = Calendar.getInstance();
		DatePickerDialog datePickerDialog = new DatePickerDialog(a, new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker p1, int y, int m, int d)
				{
					resp.onDateSet(y,m+1,d);
				}
			},mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
		datePickerDialog.show();
	}

	public static interface DialogResponse
	{
		public void onResponse(int btn);
	}

	public static interface SingleChoiceResponse
	{
		public void onResponse(int selectedIndex)
	}

	public static interface MultiChoiceResponse
	{
		public void onResponse(boolean[] checkedItems)
	}

	public static interface TimePickerResponse
	{
		public void onTimeSet(int h,int m)
	}

	public static interface DatePickerResponse
	{
		public void onDateSet(int y,int m,int d)
	}

}

