package jetpack.randa.com.ayahandamal.viewModel;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import jetpack.randa.com.ayahandamal.model.Al3amal;
import jetpack.randa.com.ayahandamal.model.Ayah;
import jetpack.randa.com.ayahandamal.model.Surah;

import java.util.List;

public interface Add3amalViewModel {
    Flowable<List<Surah>> getAllSurahs();

    Flowable<List<Ayah>> getAyahsOfSurah(String surahNum);

    Flowable<Long> insert3amal(Al3amal al3amal, Long timeStamp);

    Completable delete3amal(Al3amal al3amal);

    Flowable<Ayah> saveTafsirs();


}

