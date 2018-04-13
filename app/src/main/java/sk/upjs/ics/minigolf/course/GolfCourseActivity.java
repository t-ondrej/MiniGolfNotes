package sk.upjs.ics.minigolf.course;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import butterknife.BindView;
import sk.upjs.ics.minigolf.R;

public class GolfCourseActivity extends AppCompatActivity {

    @BindView(R.id.golf_course_toolbar) Toolbar toolbar;

    // @BindView(R.id.playersRecyclerView)
    // RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_golf_course);

       // int test  = getIntent().getExtras().getInt("hitCountMax");

        RankingFragment fragment = new RankingFragment();
        fragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rankingFragmentPlace, fragment)
                .commit();
    }
}
