package com.example.hw9_maktab28.mainController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.hw9_maktab28.model.State;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return TabFragment.newInstance(State.Todo);
            case 1: return TabFragment.newInstance(State.Doing);
            case 2: return TabFragment.newInstance(State.Done);
        }
        return null;
    }



    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "ToDo";
            case 1:
                return "Doing";
            case 2:
                return "Done";
            default:
                return null;
        }
    }




}
