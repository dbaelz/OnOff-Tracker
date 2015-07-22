package de.dbaelz.onofftracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import de.dbaelz.onofftracker.OnOffTrackerApplication;
import de.dbaelz.onofftracker.R;
import de.dbaelz.onofftracker.helpers.ActionHelper;
import de.dbaelz.onofftracker.models.Action;
import de.dbaelz.onofftracker.services.UnlockService;


public class MainActivity extends AppCompatActivity {
    private TextView screenOnTextView;
    private TextView screenOffTextView;
    private TextView unlockedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent serviceIntent = new Intent(this, UnlockService.class);
        startService(serviceIntent);

        screenOnTextView = (TextView) findViewById(R.id.main_screen_on);
        screenOffTextView = (TextView) findViewById(R.id.main_screen_off);
        unlockedTextView = (TextView) findViewById(R.id.main_unlocked);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshText();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshText() {
        OnOffTrackerApplication app = (OnOffTrackerApplication) getApplication();
        ActionHelper actionHelper = app.getActionHelper();
        long screenOnCount = actionHelper.countActions(Action.ActionType.SCREEN_ON);
        long screenOffCount = actionHelper.countActions(Action.ActionType.SCREEN_OFF);
        long unlockedCount = actionHelper.countActions(Action.ActionType.UNLOCKED);

        screenOnTextView.setText(String.format(getString(R.string.main_screen_on), screenOnCount));
        screenOffTextView.setText(String.format(getString(R.string.main_screen_off), screenOffCount));
        unlockedTextView.setText(String.format(getString(R.string.main_unlocked), unlockedCount));
    }
}
