package jetpack.randa.com.ayahandamal.dataLayer;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class DataLayerModule {

    @Provides
    @Singleton
    AyahsDao providesAyahsDao(AppDatabase appDatabase){
        return appDatabase.ayahsDao();
    }

    @Provides
    @Singleton
    Al3amalDao providesAl3amalDao(AppDatabase appDatabase){
        return appDatabase.al3amalDao();
    }

    @Provides
    @Singleton
    NotificationDao providesNotificationDao(AppDatabase appDatabase){
        return appDatabase.notificationDao();
    }

    @Provides
    @Singleton
    BookmarksDao providesBookmarkDao(AppDatabase appDatabase){
        return appDatabase.bookmarkDao();
    }

    @Provides
    @Singleton
    SurahDao providesSurahDao(AppDatabase appDatabase){
        return appDatabase.surahDao();
    }

    @Provides
    @Singleton
    TopicsDao providesTopicsDao(AppDatabase appDatabase){
        return appDatabase.topicsDao();
    }

    @Provides
    @Singleton
    AyahDecorationDao providesAyahDecorationDao(AppDatabase appDatabase){
        return appDatabase.ayahDecorationDao();
    }
}
