package sk.upjs.ics.minigolf.mainmenu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import sk.upjs.ics.minigolf.mainmenu.gamehistory.GameHistoryFragment;
import sk.upjs.ics.minigolf.mainmenu.newgame.NewGameFragment;

public class HomescreenPagerAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;

    public HomescreenPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NewGameFragment();
            case 1:
                return new GameHistoryFragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
