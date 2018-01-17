package de.dbaelz.onofftracker;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import de.dbaelz.onofftracker.activities.chart.ChartActivity;
import de.dbaelz.onofftracker.activities.main.MainActivity;
import de.dbaelz.onofftracker.services.OnOffCountService;

@Module
public abstract class ComponentBuilder {

    @ContributesAndroidInjector
    abstract OnOffCountService bindOnOffCountService();

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract ChartActivity bindChartActivity();
}
