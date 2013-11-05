package com.jn.kickoff.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TestFragmentAdapter extends FragmentStatePagerAdapter {

	private List<Fragment> viewResources;
	private int mCount;

	public TestFragmentAdapter(FragmentManager fm, List<Fragment> viewResources) {
		super(fm);

		this.viewResources = viewResources;
		mCount = this.viewResources.size();

	}

	@Override
	public Fragment getItem(int position) {
		return viewResources.get(position);
	}

	@Override
	public int getCount() {
		return mCount;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return "";
	}
	
	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}
	
	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return POSITION_NONE;
	}
	

}