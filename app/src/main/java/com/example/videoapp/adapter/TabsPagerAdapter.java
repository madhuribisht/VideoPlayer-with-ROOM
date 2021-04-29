package com.example.videoapp.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.videoapp.view.ui.HistoryListFragment;
import com.example.videoapp.view.ui.VideoListFragment;


public class TabsPagerAdapter extends FragmentPagerAdapter {
	int tabCount;

	public TabsPagerAdapter(FragmentManager fm, int tabCount) {
		super(fm);
		this.tabCount= tabCount;
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			return new VideoListFragment();
		case 1:
			return new HistoryListFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		return tabCount;
	}

}
