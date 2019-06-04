package jetpack.randa.com.ayahandamal.viewModel.impl;

import android.support.annotation.MainThread;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import jetpack.randa.com.ayahandamal.dataLayer.BookmarksDao;
import jetpack.randa.com.ayahandamal.model.Bookmark;
import jetpack.randa.com.ayahandamal.viewModel.BookmarkViewModel;

import java.util.List;

class BookmarkViewModelImpl implements BookmarkViewModel {
    private BookmarksDao dao;

    BookmarkViewModelImpl(BookmarksDao dao){
        this.dao = dao;
    }

    @Override
    public Flowable<List<Bookmark>> getBookmarks() {
        return dao.getBookmarks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable insertBookmark(Bookmark bookmark) {
        return Completable.fromAction(() -> dao.insertBookmark(bookmark))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable deleteBookmark(Bookmark bookmark) {
        return Completable.fromAction(() -> dao.deleteBookmark(bookmark))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
