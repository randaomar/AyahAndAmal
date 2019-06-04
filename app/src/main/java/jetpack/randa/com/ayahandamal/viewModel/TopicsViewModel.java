package jetpack.randa.com.ayahandamal.viewModel;

import io.reactivex.Flowable;
import jetpack.randa.com.ayahandamal.model.Ayah;
import jetpack.randa.com.ayahandamal.model.TopicHeads;
import kotlin.Pair;

import java.util.List;

public interface TopicsViewModel {

    Flowable<List<TopicHeads>> getAllTopics();

    Flowable<Pair<Ayah,String>> getAyahsOfTopics(String topicId);

}
