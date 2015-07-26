package de.dbaelz.onofftracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.TextView;

import de.dbaelz.onofftracker.OnOffTrackerApplication;
import de.dbaelz.onofftracker.R;
import de.dbaelz.onofftracker.helpers.ActionHelper;
import de.dbaelz.onofftracker.models.Action;
import de.dbaelz.onofftracker.services.UnlockService;


public class MainActivity extends AppCompatActivity {
    private CardView cardViewToday;
    private CardView cardViewOverall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent serviceIntent = new Intent(this, UnlockService.class);
        startService(serviceIntent);

        cardViewToday = (CardView) findViewById(R.id.cardviewToday);
        cardViewOverall = (CardView) findViewById(R.id.cardviewOverall);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshCardviews();
    }

    private void refreshCardviews() {
        ((TextView) cardViewToday.findViewById(R.id.cardview_title)).setText(getString(R.string.cardview_action_title_today));
        ((TextView) cardViewOverall.findViewById(R.id.cardview_title)).setText(getString(R.string.cardview_action_title_overall));

        OnOffTrackerApplication app = (OnOffTrackerApplication) getApplication();
        ActionHelper actionHelper = app.getActionHelper();

        long screenOnToday = actionHelper.countTodaysActions(Action.ActionType.SCREENON);
        long screenOffToday = actionHelper.countTodaysActions(Action.ActionType.SCREENOFF);
        long unlockedToday = actionHelper.countTodaysActions(Action.ActionType.UNLOCKED);

        ((TextView) cardViewToday.findViewById(R.id.cardview_screenon)).setText(String.format(getString(R.string.cardview_screenon), screenOnToday));
        ((TextView) cardViewToday.findViewById(R.id.cardview_screenoff)).setText(String.format(getString(R.string.cardview_screenoff), screenOffToday));
        ((TextView) cardViewToday.findViewById(R.id.cardview_unlocked)).setText(String.format(getString(R.string.cardview_unlocked), unlockedToday));

        long screenOnOverall = actionHelper.countAllActions(Action.ActionType.SCREENON);
        long screenOffOverall = actionHelper.countAllActions(Action.ActionType.SCREENOFF);
        long unlockedOverall = actionHelper.countAllActions(Action.ActionType.UNLOCKED);

        ((TextView) cardViewOverall.findViewById(R.id.cardview_screenon)).setText(String.format(getString(R.string.cardview_screenon), screenOnOverall));
        ((TextView) cardViewOverall.findViewById(R.id.cardview_screenoff)).setText(String.format(getString(R.string.cardview_screenoff), screenOffOverall));
        ((TextView) cardViewOverall.findViewById(R.id.cardview_unlocked)).setText(String.format(getString(R.string.cardview_unlocked), unlockedOverall));
    }
}
