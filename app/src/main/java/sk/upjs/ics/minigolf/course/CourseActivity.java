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

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Bundle extras = getIntent().getExtras();
        game = Game.fromBundle(extras);
        configureTabLayout();
    }

    private void configureTabLayout() {
        final ViewPager viewPager = findViewById(R.id.holePager);
        final CoursePagerAdapter adapter = new CoursePagerAdapter
                (getSupportFragmentManager(), game);
        viewPager.setOffscreenPageLimit(1);

        viewPager.setAdapter(adapter);
    }

    public void onCompleteCourseClick(View view) {
        Intent intent = new Intent(this, GameSummaryActivity.class);
        intent = intent.putExtras(game.toBundle());
        startActivity(intent);
    }
}
