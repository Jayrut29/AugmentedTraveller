package com.example.adapters;

import java.util.List;
import com.example.agt.R;
import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ViewPagerAdapter extends PagerAdapter
{

	private List<Integer> list;
	LayoutInflater inflater;

	public ViewPagerAdapter(Activity act, List<Integer> user)
	{

		inflater = act.getLayoutInflater();
		this.list = user;

	}

	@Override
	public int getCount()
	{
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object)
	{
		return view == ((LinearLayout) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position)
	{
		View v = inflater.inflate(R.layout.pager_item, null);
		ImageView img = (ImageView) v.findViewById(R.id.my_image);
		img.setImageResource(list.get(position));
		((ViewPager) container).addView(v);
		return v;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object)
	{
		((ViewPager) container).removeView((LinearLayout) object);
	}

}
