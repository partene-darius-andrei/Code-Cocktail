package com.compilation.demos.adapterToggle;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import java.lang.ref.WeakReference;

class TabLayoutOnChangeListener implements ViewPager.OnPageChangeListener {

    private final WeakReference<TabLayout> mTabLayoutRef;
    private int mPreviousScrollState;
    private int mScrollState;

    TabLayoutOnChangeListener(TabLayout tabLayout) {
        mTabLayoutRef = new WeakReference<>(tabLayout);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mPreviousScrollState = mScrollState;
        mScrollState = state;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        final TabLayout tabLayout = mTabLayoutRef.get();
        if (tabLayout != null) {
            final boolean updateText = (mScrollState == ViewPager.SCROLL_STATE_DRAGGING)
                    || (mScrollState == ViewPager.SCROLL_STATE_SETTLING
                    && mPreviousScrollState == ViewPager.SCROLL_STATE_DRAGGING);
            tabLayout.setScrollPosition(position, positionOffset, updateText);
        }
    }

    @Override
    public void onPageSelected(int position) {
        final TabLayout tabLayout = mTabLayoutRef.get();
        if (tabLayout != null) {
            tabLayout.getTabAt(position).select();
        }
    }
}