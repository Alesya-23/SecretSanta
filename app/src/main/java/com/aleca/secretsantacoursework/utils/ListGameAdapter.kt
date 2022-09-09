package com.aleca.secretsantacoursework.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.aleca.secretsantacoursework.MainActivity
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
        val statusGame: TextView = itemView.findViewById(R.id.status_game_is_active)
        var buttonDelete: Button = itemView.findViewById(R.id.delete_game)

        init {
            nameGame = itemView.findViewById(R.id.name_game)
            itemView.setOnClickListener {
                onItemClick?.invoke(listGames[position])
            }
            buttonDelete = itemView.findViewById(R.id.delete_game)
            buttonDelete.setOnClickListener {
                onItemClickDel?.invoke(listGames[adapterPosition])
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
        holder.statusGame.text = getStatus(listGames[position].statusGameIsActive)
        holder.statusGame.setTextColor(getColorStatus(listGames[position].statusGameIsActive))
    }

    private fun getStatus(status: Int): String {
        return if (status == 0) {
            "Прошедшая"
        } else "Активная"
    }

    private fun getColorStatus(status: Int): Int {
        return if (status == 0) {
            Color.RED
        } else Color.GREEN
    }

    override fun getItemCount() = listGames.size
}
