package jetpack.randa.com.ayahandamal.viewModel.impl;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import jetpack.randa.com.ayahandamal.dataLayer.Al3amalDao;
import jetpack.randa.com.ayahandamal.dataLayer.AyahsDao;
import jetpack.randa.com.ayahandamal.dataLayer.RetrofitFactory;
import jetpack.randa.com.ayahandamal.model.Al3amal;
import jetpack.randa.com.ayahandamal.model.Al3amalCombined;
import jetpack.randa.com.ayahandamal.model.Ayah;
import jetpack.randa.com.ayahandamal.viewModel.HomeViewModel;
import org.intellij.lang.annotations.Flow;
import org.reactivestreams.Publisher;

import java.util.List;

class HomeViewModelImpl implements HomeViewModel {
    private Al3amalDao al3amalDao;
    private AyahsDao ayahsDao;

    HomeViewModelImpl(Al3amalDao al3amalDao, AyahsDao ayahsDao){
        this.al3amalDao = al3amalDao;
        this.ayahsDao = ayahsDao;
    }

    @Override
    public Completable insert3amal(Al3amal al3amal) {
        return  Completable.fromAction(() -> al3amalDao.insert3amal(al3amal))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable delete3amal(Al3amal al3amal) {
        return Completable.fromAction(() -> al3amalDao.delet3amal(al3amal))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Flowable<Al3amalCombined> getAll3amal() {
        return al3amalDao.getAl3amal()
                .flatMap(al3amals -> {
                    if(!al3amals.isEmpty())
                    return Flowable.fromIterable(al3amals)
                            .flatMap(al3amal ->
                                    ayahsDao.getOneAyah(al3amal.getSurahNum(), al3amal.getAyahNum())
                                            .flatMap(ayah -> Flowable.just(new Al3amalCombined(al3amal, ayah.getAyahText())))
                                            .flatMap(new Function<Al3amalCombined, Publisher<Al3amalCombined>>() {
                                                @Override
                                                public Publisher<Al3amalCombined> apply(Al3amalCombined al3amalCombined) throws Exception {
                                                    return Flowable.just(al3amalCombined);
                                                }
                                            }));
                    else{
                        return Flowable.empty();
                    }
                })

                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Flowable<Al3amalCombined> get3amal(long id) {
        return al3amalDao.get3amal(id)
                .flatMap(al3amal -> {
                   return  ayahsDao.getOneAyah(al3amal.getSurahNum(), al3amal.getAyahNum())
                           .flatMap(ayah -> Flowable.just(new Al3amalCombined(al3amal, ayah.getAyahText())))
                           .flatMap(new Function<Al3amalCombined, Publisher<Al3amalCombined>>() {
                               @Override
                               public Publisher<Al3amalCombined> apply(Al3amalCombined al3amalCombined) throws Exception {
                                   return Flowable.just(al3amalCombined);
                               }
                           });
                })

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
}
