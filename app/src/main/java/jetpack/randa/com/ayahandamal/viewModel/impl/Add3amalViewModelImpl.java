package jetpack.randa.com.ayahandamal.viewModel.impl;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import jetpack.randa.com.ayahandamal.dataLayer.Al3amalDao;
import jetpack.randa.com.ayahandamal.dataLayer.AyahsDao;
import jetpack.randa.com.ayahandamal.dataLayer.NotificationDao;
import jetpack.randa.com.ayahandamal.dataLayer.RetrofitFactory;
import jetpack.randa.com.ayahandamal.model.A3malNotification;
import jetpack.randa.com.ayahandamal.model.Al3amal;
import jetpack.randa.com.ayahandamal.model.Ayah;
import jetpack.randa.com.ayahandamal.model.Surah;
import jetpack.randa.com.ayahandamal.viewModel.Add3amalViewModel;
import org.intellij.lang.annotations.Flow;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.List;

 class Add3amalViewModelImpl implements Add3amalViewModel {

    private AyahsDao ayahsDao;
    private Al3amalDao al3amalDao;
    private NotificationDao notificationDao;

    Add3amalViewModelImpl(AyahsDao dao, Al3amalDao al3amalDao, NotificationDao notificationDao) {
        this.ayahsDao = dao;
        this.notificationDao = notificationDao;
        this.al3amalDao = al3amalDao;
    }

    @Override
    public Flowable<List<Surah>> getAllSurahs() {
        return ayahsDao.getSurahs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Flowable<List<Ayah>> getAyahsOfSurah(String surahNum) {
        return ayahsDao.getAyahsOfSurah(surahNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

     @Override
     public Flowable<Long> insert3amal(Al3amal al3amal, Long timestamp) {
        //if(timestamp>0) {
         return Flowable.unsafeCreate((Publisher<Long>) s -> {
             try {
                 s.onNext(al3amalDao.insert3amal(al3amal));
                 s.onComplete();
             }catch(Exception e) {
                 s.onError(new Throwable());
             }
         }) .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread());


       /* } else {
            return al3amalDao.insert3amal(al3amal)
                    .flatMap(new Function<Long, Publisher<Long>>() {
                        @Override
                        public Publisher<Long> apply(Long aLong) throws Exception {
                            return notificationDao.addNotification(new A3malNotification(aLong, timestamp));
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }*/
     }

     @Override
     public Completable delete3amal(Al3amal al3amal) {
         return Completable.fromAction(() -> al3amalDao.delet3amal(al3amal))
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread());
     }


     public Flowable<Ayah> saveTafsirs() {
         RetrofitFactory.RetroService service = RetrofitFactory.createService(RetrofitFactory.RetroService.class);
         return Flowable.range(109,6).flatMap(new Function<Integer, Publisher<Ayah>>() {
             @Override
             public Publisher<Ayah> apply(Integer integer) throws Exception {
                 return service.getAyahs(integer).flatMap(new Function<RetrofitFactory.AyahDataResponse, Publisher<Ayah>>() {
                     @Override
                     public Publisher<Ayah> apply(RetrofitFactory.AyahDataResponse verseData) throws Exception {
                         return Flowable.fromIterable(verseData.getVerseData().getAyahData()).flatMap(new Function<RetrofitFactory.AyahData, Publisher<Ayah>>() {
                             @Override
                             public Publisher<Ayah> apply(RetrofitFactory.AyahData ayahData) throws Exception {
                                 return ayahsDao.getOneAyah(integer.toString(), ayahData.getVerseNum()+"").flatMap(new Function<Ayah, Publisher<Ayah>>() {
                                     @Override
                                     public Publisher<Ayah> apply(Ayah ayah) throws Exception {
                                            ayah.setAyahText(ayahData.getAyahTxt());
                                         return Flowable.unsafeCreate(new Publisher<Ayah>() {
                                             @Override
                                             public void subscribe(Subscriber<? super Ayah> s) {
                                                 ayahsDao.updateTafseer(ayah.getAyahText(), ayah.getAyahNum(), ayah.getSurahNum());
                                                 s.onNext(ayah);
                                                 s.onComplete();
                                             }
                                         });
                                     }
                                 });
                             }
                         });
                     }
                 });
                 /*
                    return ayahsDao.getAyahsOfSurah(integer.toString()).flatMap(new Function<List<Ayah>, Publisher<Ayah>>() {
                        @Override
                        public Publisher<Ayah> apply(List<Ayah> ayahs) throws Exception {
                            return Flowable.fromIterable(ayahs).flatMap(new Function<Ayah, Publisher<Ayah>>() {
                                @Override
                                public Publisher<Ayah> apply(Ayah ayah) throws Exception {
                                    return service.getTafsir(integer, Integer.parseInt(ayah.getAyahNum()), 91)
                                            .flatMap(new Function<RetrofitFactory.TafsirResponse, Publisher<Ayah>>() {
                                        @Override
                                        public Publisher<Ayah> apply(RetrofitFactory.TafsirResponse tafsirResponse) throws Exception {
                                            ayah.setTafseer(tafsirResponse.getTafsirTextResponseList().get(0).getTextTafsir());
                                           return Flowable.unsafeCreate(new Publisher<Ayah>() {
                                               @Override
                                               public void subscribe(Subscriber<? super Ayah> s) {
                                                        ayahsDao.updateTafsir(ayah);
                                                        s.onNext(ayah);
                                                        s.onComplete();
                                               }
                                           });

                                        }
                                    });
                                }
                            });
                        }
                    });
             */}
         }).subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread());
/*return ayahsDao.getAyas().flatMap(new Function<List<Ayah>, Publisher<Ayah>>() {
    @Override
    public Publisher<Ayah> apply(List<Ayah> ayahs) throws Exception {
        return Flowable.fromIterable(ayahs).flatMap(new Function<Ayah, Publisher<Ayah>>() {
            @Override
            public Publisher<Ayah> apply(Ayah ayah) throws Exception {

                return service.getTafsir(ayah.getSurahIntNum(), Integer.parseInt(ayah.getAyahNum()), 91)
                        .flatMap(new Function<RetrofitFactory.TafsirResponse, Publisher<Ayah>>() {
                            @Override
                            public Publisher<Ayah> apply(RetrofitFactory.TafsirResponse tafsirResponse) throws Exception {
                                ayah.setTafseer(tafsirResponse.getTafsirTextResponseList().get(0).getTextTafsir());
                                return Flowable.unsafeCreate(new Publisher<Ayah>() {
                                    @Override
                                    public void subscribe(Subscriber<? super Ayah> s) {
                                        ayahsDao.updateTafseer(ayah.getTafseer(), ayah.getAyahNum(), ayah.getSurahNum());
                                        s.onNext(ayah);
                                        s.onComplete();
                                    }
                                });
                            }
                        });
            }
        });
    }
});*/
     }
 }
