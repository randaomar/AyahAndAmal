package jetpack.randa.com.ayahandamal.viewModel.impl;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import jetpack.randa.com.ayahandamal.dataLayer.AyahsDao;
import jetpack.randa.com.ayahandamal.dataLayer.SurahDao;
import jetpack.randa.com.ayahandamal.dataLayer.TopicsDao;
import jetpack.randa.com.ayahandamal.model.Ayah;
import jetpack.randa.com.ayahandamal.model.Surah;
import jetpack.randa.com.ayahandamal.model.Topic;
import jetpack.randa.com.ayahandamal.model.TopicHeads;
import jetpack.randa.com.ayahandamal.viewModel.TopicsViewModel;
import kotlin.Pair;
import org.reactivestreams.Publisher;

import java.util.List;

public class TopicsViewModelImpl implements TopicsViewModel {
    private TopicsDao dao;
    private AyahsDao ayahDao;
    private SurahDao surahDao;

    TopicsViewModelImpl(TopicsDao dao, AyahsDao ayahsDao, SurahDao surahDao) {
        this.dao = dao;
        this.surahDao =surahDao;
        this.ayahDao = ayahsDao;
    }

    @Override
    public Flowable<List<TopicHeads>> getAllTopics() {
        return dao.getTopics()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Flowable<Pair<Ayah, String>> getAyahsOfTopics(String topicId) {
        return dao.getOneTopics(topicId).flatMap(topics ->
                Flowable.fromIterable(topics)
                .flatMap( topic ->
                        Flowable.combineLatest(ayahDao.getOneAyah(topic.getSurahNum(), topic.getAyahNum()), surahDao.getSurah(topic.getSurahIntNum()),
                                (ayah, surah) -> new Pair<>(ayah, surah.getSurahName()))))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
