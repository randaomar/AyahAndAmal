package jetpack.randa.com.ayahandamal.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jetpack.randa.com.ayahandamal.IntentKeys
import jetpack.randa.com.ayahandamal.R
import jetpack.randa.com.ayahandamal.activity.ReadQuranActivity
import jetpack.randa.com.ayahandamal.model.Surah
import kotlinx.android.synthetic.main.surahz_list_item.view.*

class SurahListAdapter  (val surahList: List<Surah>) : RecyclerView.Adapter<SurahListAdapter.SurahListHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SurahListAdapter.SurahListHolder =
        SurahListHolder(LayoutInflater.from(p0.context).inflate(R.layout.surahz_list_item, p0, false))

    override fun getItemCount(): Int = surahList.size

    override fun onBindViewHolder(holder: SurahListAdapter.SurahListHolder, position: Int) {
        holder.itemView.surah_num_tv.text = surahList[position].id.toString()
        holder.itemView.surah_name_tv.text = surahList[position].surahName
        holder.itemView.surah_desc_tv.text = surahList[position].surahDesc
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ReadQuranActivity::class.java)
            intent.putExtra(IntentKeys.SELECTED_SURAH_ID, surahList[position].id)
            holder.itemView.context.startActivity(intent)
        }
    }
    class SurahListHolder (val view : View)  : RecyclerView.ViewHolder(view)
}