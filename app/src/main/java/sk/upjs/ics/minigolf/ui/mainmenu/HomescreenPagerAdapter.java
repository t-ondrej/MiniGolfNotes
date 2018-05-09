package sk.upjs.ics.minigolf.ui.mainmenu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import sk.upjs.ics.minigolf.ui.mainmenu.gamehistory.GameHistoryFragment;
import sk.upjs.ics.minigolf.ui.mainmenu.newgame.NewGameFragment;

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
                return NewGameFragment.createNewInstance();
            case 1:
                return GameHistoryFragment.createNewInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
