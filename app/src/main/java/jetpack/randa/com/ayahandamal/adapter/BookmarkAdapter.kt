package jetpack.randa.com.ayahandamal.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jetpack.randa.com.ayahandamal.IntentKeys
import jetpack.randa.com.ayahandamal.R
import jetpack.randa.com.ayahandamal.activity.ReadQuranActivity
import jetpack.randa.com.ayahandamal.model.Bookmark
import kotlinx.android.synthetic.main.alamal_list_item_layout.view.*

class BookmarkAdapter(val list: List<Bookmark>, val deleteBookmark: (bookmark: Bookmark)->Unit) : RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): BookmarkViewHolder =
        BookmarkViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.alamal_list_item_layout, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.itemView.ayah_tv.text = list[position].bookmarkAyah
        holder.itemView.al3amal_tv.text = list[position].bookmarkDescription
        holder.itemView.delete_iv.setOnClickListener { deleteBookmark.invoke(list[position]) }
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ReadQuranActivity::class.java)
            intent.putExtra(IntentKeys.SELECTED_SURAH_ID, list[position].surahNum)
            intent.putExtra(IntentKeys.SELECTED_AYAH_ID, list[position].ayahNum)
            holder.itemView.context.startActivity(intent)

        }
    }


    class BookmarkViewHolder (val view : View)  : RecyclerView.ViewHolder(view)
}