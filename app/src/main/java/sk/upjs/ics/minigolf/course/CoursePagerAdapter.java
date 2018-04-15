package sk.upjs.ics.minigolf.course;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import sk.upjs.ics.minigolf.R;
import sk.upjs.ics.minigolf.models.Game;

public class CoursePagerAdapter extends FragmentStatePagerAdapter {

    private Game game;
    private int mNumOfTabs;
    private FragmentManager fm;

    public CoursePagerAdapter(FragmentManager fm, Game game) {
        super(fm);
        this.fm = fm;
        this.game = game;
        this.mNumOfTabs = game.getHoleCount();
    }

    @Override
    public Fragment getItem(int position) {
        RankingFragment fragment = new RankingFragment();
        Bundle bundle = game.toBundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }


}
