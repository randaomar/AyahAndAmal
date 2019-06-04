package jetpack.randa.com.ayahandamal.viewModel.impl;

import dagger.Module;
import dagger.Provides;
import jetpack.randa.com.ayahandamal.dataLayer.*;
import jetpack.randa.com.ayahandamal.viewModel.*;

import javax.inject.Singleton;

@Module
public class ViewModelModule {

    @Provides
    @Singleton
    public HomeViewModel providesHomeViewModel(Al3amalDao al3amalDao, AyahsDao ayahsDao){
        return new HomeViewModelImpl(al3amalDao, ayahsDao);

    }

    @Provides
    @Singleton
    public Add3amalViewModel providesAdd3amalViewModel(AyahsDao dao, Al3amalDao al3amalDao, NotificationDao notificationDao){
        return new Add3amalViewModelImpl(dao, al3amalDao, notificationDao);
    }

    @Provides
    @Singleton
    public BookmarkViewModel providesBookmarkViewModel(BookmarksDao dao){
        return new BookmarkViewModelImpl(dao);
    }

    @Provides
    @Singleton
    public SurahViewModel providesSurahViewModel(SurahDao dao, AyahDecorationDao ayahDecorationDao){
        return new SurahViewModelImpl(dao, ayahDecorationDao);

    }

    @Provides
    @Singleton
    public TopicsViewModel providesTopicsViewModel(TopicsDao dao, AyahsDao ayahsDao, SurahDao surahDao){
        return new TopicsViewModelImpl(dao, ayahsDao, surahDao);
    }

}
