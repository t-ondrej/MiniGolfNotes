package sk.upjs.ics.minigolf.ui.course.gamesummary;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import sk.upjs.ics.minigolf.models.Game;

public class GamesummaryPagerAdapter extends FragmentStatePagerAdapter {

    private Game game;
    private int mNumOfTabs;
    private FragmentManager fm;

    public GamesummaryPagerAdapter(FragmentManager fm, Game game) {
        super(fm);
        this.fm = fm;
        this.game = game;
        this.mNumOfTabs = 2;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return PlayerTableFragment.newInstance(game);
        } else if (position == 1) {
            return StatisticsFragment.newInstance(game);
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
