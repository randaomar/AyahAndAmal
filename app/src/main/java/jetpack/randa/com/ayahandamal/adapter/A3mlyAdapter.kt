package jetpack.randa.com.ayahandamal.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import jetpack.randa.com.ayahandamal.IntentKeys
import jetpack.randa.com.ayahandamal.R
import jetpack.randa.com.ayahandamal.activity.Add3amalActivity
import jetpack.randa.com.ayahandamal.model.Al3amal
import jetpack.randa.com.ayahandamal.model.Al3amalCombined
import kotlinx.android.synthetic.main.alamal_list_item_layout.view.*

class A3mlyAdapter (val deleteFun: (al3amal: Al3amal )->Unit) : RecyclerView.Adapter<A3mlyAdapter.Al3amalViewHolder>() {
    val list: MutableList<Al3amalCombined> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): Al3amalViewHolder =
        Al3amalViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.alamal_list_item_layout, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Al3amalViewHolder, position: Int) {
        holder.itemView.ayah_tv.text = list[position].ayahText
        holder.itemView.al3amal_tv.text = list[position].al3amal.al3amal
        holder.itemView.delete_iv.setOnClickListener {
            deleteFun.invoke(list[position].al3amal)
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, Add3amalActivity::class.java)
            intent.putExtra(IntentKeys.COMBINED_3AMAL_OBJECT, list[position])
            holder.itemView.context.startActivity(intent)
        }
    }

    fun addItem(o: Al3amalCombined){
        list.add(o)
        notifyDataSetChanged()
    }

    class Al3amalViewHolder (val view : View)  : RecyclerView.ViewHolder(view)
}