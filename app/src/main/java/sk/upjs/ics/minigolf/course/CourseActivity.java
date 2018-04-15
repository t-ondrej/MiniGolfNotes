package sk.upjs.ics.minigolf.course;

import android.content.Intent;
import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.OnClick;
import sk.upjs.ics.minigolf.R;
import sk.upjs.ics.minigolf.course.gamesummary.GameSummaryActivity;
import sk.upjs.ics.minigolf.models.Game;

public class CourseActivity extends AppCompatActivity {

    @BindView(R.id.courseToolbar) Toolbar toolbar;
    @BindView(R.id.courseCompleteImageView) ImageView courseCompleteImageView;
    // @BindView(R.id.playersRecyclerView)
    // RecyclerView recyclerView;

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Bundle extras = getIntent().getExtras();
        game = Game.fromBundle(extras);
        configureTabLayout();
      /*  RankingFragment fragment = new RankingFragment();
        fragment.setArguments(extras);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rankingFragmentPlace, fragment)
                .commit();*/
    }

    private void configureTabLayout() {
        TabLayout tabLayout = findViewById(R.id.holeTabLayout);
        final ViewPager viewPager = findViewById(R.id.holePager);
        final CoursePagerAdapter adapter = new CoursePagerAdapter
                (getSupportFragmentManager(), game);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager.setOffscreenPageLimit(1);

        for (int i = 1; i <= game.getHoleCount(); i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(i + ". Jamka");
            tabLayout.addTab(tab);
        }


        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    public void onCompleteCourseClick(View view) {
        Intent intent = new Intent(this, GameSummaryActivity.class);
        intent = intent.putExtras(game.toBundle());
        startActivity(intent);
    }
}
