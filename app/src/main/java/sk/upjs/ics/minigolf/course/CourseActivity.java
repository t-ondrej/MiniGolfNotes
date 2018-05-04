package sk.upjs.ics.minigolf.course;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import sk.upjs.ics.minigolf.R;
import sk.upjs.ics.minigolf.course.gamesummary.GameSummaryActivity;
import sk.upjs.ics.minigolf.models.Game;

public class CourseActivity extends AppCompatActivity {

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_activity);

        if (savedInstanceState != null) {
            game = game.fromBundle(savedInstanceState);
        } else {
            Bundle extras = getIntent().getExtras();
            game = Game.fromBundle(extras);
        }

        configureTabLayout();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        game.toBundle(outState);

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }

    private void configureTabLayout() {
        final ViewPager viewPager = findViewById(R.id.holePager);
        final CoursePagerAdapter adapter = new CoursePagerAdapter
                (getSupportFragmentManager(), game);
        viewPager.setOffscreenPageLimit(1);

        viewPager.setAdapter(adapter);
    }

    public void onCompleteCourseClick(View view) {
        new AlertDialog.Builder(this, R.style.AlertDialogStyle)
                .setTitle(R.string.end_game_question)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    Log.d("CourseActivity", "Completing course.");
                    Intent intent = new Intent(CourseActivity.this, GameSummaryActivity.class);
                    intent = intent.putExtras(game.toBundle());
                    startActivity(intent);
                })
                .setNegativeButton(R.string.no, (dialog, which) -> Log.d("CourseActivity", "User cancelled completion."))
                .show();
    }
}
