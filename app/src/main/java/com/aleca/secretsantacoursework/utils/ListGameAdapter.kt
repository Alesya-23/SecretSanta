package com.aleca.secretsantacoursework.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aleca.secretsantacoursework.R
import com.aleca.secretsantacoursework.model.Game
import com.google.android.material.textview.MaterialTextView

class ListGameAdapter(private val listGames: ArrayList<Game>) :
    RecyclerView.Adapter<ListGameAdapter.MyViewHolder>() {
    var onItemClick: ((Game) -> Unit)? = null
    var onItemClickDel: ((Game) -> Unit)? = null

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameGame: MaterialTextView? = null
        var countPeople: TextView = itemView.findViewById(R.id.count_people)

        init {
            nameGame = itemView.findViewById(R.id.name_game)
            itemView.setOnClickListener {
                onItemClick?.invoke(listGames[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_game, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nameGame?.text = listGames[position].name
        holder.countPeople.text = listGames[position].countGamers.toString()
    }

    override fun getItemCount() = listGames.size
}
