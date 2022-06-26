package com.khiempt.trinhdocpdf;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.khiempt.trinhdocpdf.ui.AllFileFragment;

public class TabLayoutAdapter extends FragmentPagerAdapter {

    Context mContext;
    int mTotalTabs;
    int mBotton;

    public TabLayoutAdapter(Context context, FragmentManager fragmentManager, int totalTabs, int c) {
        super(fragmentManager);
        mContext = context;
        mTotalTabs = totalTabs;
        mBotton = c;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Log.d("asasas", position + "");
        switch (position) {
            case 0:
                return new AllFileFragment(1, position, mBotton);
            case 1:
                return new AllFileFragment(2, position, mBotton);
            case 2:
                return new AllFileFragment(3, position, mBotton);
            case 3:
                return new AllFileFragment(4, position, mBotton);
            case 4:
                return new AllFileFragment(5, position, mBotton);

            default:
                return new AllFileFragment(2, position, mBotton);

        }
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = mContext.getString(R.string.all);
                break;
            case 1:
                title = "PDF";
                break;
            case 2:
                title ="WORD";
                break;
            case 3:
                title = "EXCEL";
                break;
            case 4:
                title = "PPT";
                break;

        }
        return title;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
