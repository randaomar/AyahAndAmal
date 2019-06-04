package jetpack.randa.com.ayahandamal.viewModel;

import io.reactivex.Flowable;
import jetpack.randa.com.ayahandamal.model.AyahDecoration;
import jetpack.randa.com.ayahandamal.model.Surah;
import kotlin.Pair;

import java.util.List;

public interface SurahViewModel {
    Flowable<Pair<Surah, List<AyahDecoration>>> getSurah(int surahId);

}
