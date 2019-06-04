package jetpack.randa.com.ayahandamal.viewModel;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import jetpack.randa.com.ayahandamal.model.Al3amal;
import jetpack.randa.com.ayahandamal.model.Al3amalCombined;
import jetpack.randa.com.ayahandamal.model.Ayah;

import java.util.List;

public interface HomeViewModel {
    Completable insert3amal(Al3amal al3amal);
    Completable delete3amal(Al3amal al3amal);
    Flowable<Al3amalCombined> getAll3amal();
    Flowable<Al3amalCombined> get3amal(long id);
}
