package sk.upjs.ics.minigolf.ui.course;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import sk.upjs.ics.minigolf.GameHolder;
import sk.upjs.ics.minigolf.R;
import sk.upjs.ics.minigolf.models.Game;
import sk.upjs.ics.minigolf.ui.course.gamesummary.GameSummaryActivity;

public class CourseActivity extends AppCompatActivity {

    private Game game = GameHolder.INSTANCE.getGame();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_activity);

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
