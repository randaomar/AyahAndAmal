package jetpack.randa.com.ayahandamal.viewModel.impl;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import jetpack.randa.com.ayahandamal.dataLayer.AyahDecorationDao;
import jetpack.randa.com.ayahandamal.dataLayer.SurahDao;
import jetpack.randa.com.ayahandamal.model.AyahDecoration;
import jetpack.randa.com.ayahandamal.model.Surah;
import jetpack.randa.com.ayahandamal.viewModel.SurahViewModel;
import kotlin.Pair;

import java.util.List;

public class SurahViewModelImpl implements SurahViewModel {
    private SurahDao dao;
    private AyahDecorationDao ayahDecorationDao;

    SurahViewModelImpl(SurahDao dao, AyahDecorationDao ayahDecorationDao) {
        this.dao = dao;
        this.ayahDecorationDao = ayahDecorationDao;
    }

    @Override
    public Flowable<Pair<Surah, List<AyahDecoration>>> getSurah(int surahId) {
        return Flowable.combineLatest(dao.getSurah(surahId), ayahDecorationDao.getSurahDecorations(surahId),
                Pair::new)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}

