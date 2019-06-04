package jetpack.randa.com.ayahandamal.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jetpack.randa.com.ayahandamal.IntentKeys
import jetpack.randa.com.ayahandamal.R
import jetpack.randa.com.ayahandamal.activity.ListActivity
import jetpack.randa.com.ayahandamal.activity.ReadQuranActivity
import jetpack.randa.com.ayahandamal.model.Bookmark
import jetpack.randa.com.ayahandamal.model.Topic
import jetpack.randa.com.ayahandamal.model.TopicHeads
import kotlinx.android.synthetic.main.alamal_list_item_layout.view.*
import kotlinx.android.synthetic.main.topic_laout_item.view.*

class TopicsAdapter(val list: List<TopicHeads>) : RecyclerView.Adapter<TopicsAdapter.BookmarkViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): BookmarkViewHolder =
        BookmarkViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.topic_laout_item, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.itemView.topic.text = list[position].topic_text
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ListActivity::class.java)
            intent.putExtra(IntentKeys.TOPIC_HEAD,list[position])
            holder.itemView.context.startActivity(intent)

        }
    }


    class BookmarkViewHolder (val view : View)  : RecyclerView.ViewHolder(view)
}