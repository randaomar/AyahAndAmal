package jetpack.randa.com.ayahandamal.viewModel;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import jetpack.randa.com.ayahandamal.model.Bookmark;

import java.util.List;

public interface BookmarkViewModel {
    Flowable<List<Bookmark>> getBookmarks();

    Completable insertBookmark(Bookmark bookmark);

    Completable deleteBookmark(Bookmark bookmark);
}
