package com.cmaye.tic_toc_toe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.cmaye.tic_toc_toe.model.TTT


class GridViewAdapter(val itemlist: MutableList<TTT>) :
    RecyclerView.Adapter<GridViewAdapter.ViewHolder>() {

    private var player_status: Boolean = true
    var isClickable : Boolean = true

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: TTT) {
            val cardView = itemView.findViewById<CardView>(R.id.cardView)
            val imageView = itemView.findViewById<ImageView>(R.id.imgIcon)
            cardView.setOnClickListener {
                if (isClickable)
                {
                    if (item.check) mClickListener?.showToast("Already Check!")
                    else {
                        item.check = true
                        item.status = if (player_status) 1 else 2
                        mClickListener?.isCheck(if (player_status) 1 else 2, itemlist)
                        player_status = if (player_status) {
                            imageView.setImageResource(R.drawable.circle_svgrepo_com)
                            false
                        } else {
                            imageView.setImageResource(R.drawable.wrong_svgrepo_com)
                            true
                        }
                    }
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemlist[position]
        holder.bindView(item)
    }

    override fun getItemCount(): Int = itemlist.size

    interface OnClickListener {
        fun isCheck(status: Int, list: MutableList<TTT>)
        fun showToast(msg: String)
    }

    var mClickListener: OnClickListener? = null
    fun setOnClickListener(clickListener: OnClickListener) {
        mClickListener = clickListener
    }

}
