package jetpack.randa.com.ayahandamal;

import android.app.Application;
import dagger.Module;
import dagger.Provides;
import jetpack.randa.com.ayahandamal.dataLayer.AppDatabase;
import jetpack.randa.com.ayahandamal.dataLayer.DataLayerModule;
import jetpack.randa.com.ayahandamal.viewModel.impl.ViewModelModule;

import javax.inject.Singleton;

@Module(includes = {ViewModelModule.class, DataLayerModule.class})
public class AppModule {
    private Application app;

    AppModule(Application app) {
        this.app = app;
    }

    @Singleton
    @Provides
    AppDatabase providesAppDatabase() {
        return AppDatabase.Companion.getDatabase(app);
    }
}
