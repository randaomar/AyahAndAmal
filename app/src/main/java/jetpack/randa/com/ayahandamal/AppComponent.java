package jetpack.randa.com.ayahandamal;

import dagger.Component;
import jetpack.randa.com.ayahandamal.activity.Add3amalActivity;
import jetpack.randa.com.ayahandamal.activity.ListActivity;
import jetpack.randa.com.ayahandamal.activity.ReadQuranActivity;
import jetpack.randa.com.ayahandamal.activity.SplashActivity;
import jetpack.randa.com.ayahandamal.broadcastReciever.AlarmReciever;
import jetpack.randa.com.ayahandamal.fragment.HomeFragment;
import jetpack.randa.com.ayahandamal.fragment.SettingsFragment;
import jetpack.randa.com.ayahandamal.fragment.SurahListFragment;
import jetpack.randa.com.ayahandamal.fragment.TopicsListFragment;

import javax.inject.Singleton;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
 void inject(HomeFragment fragment);
 void inject(Add3amalActivity add3amalActivity);
 void inject(SurahListFragment fragment);
 void inject(AlarmReciever reciever);
 void inject(SettingsFragment fragment);
 void inject(ReadQuranActivity activity);
 void inject(ListActivity activity);
 void inject(TopicsListFragment fragment);
}
