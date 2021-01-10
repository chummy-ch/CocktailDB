package android.bignerdranch.cocktaildb

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.*
import kotlin.collections.ArrayList
import android.bignerdranch.cocktaildb.DrinksListFragment.Companion.drinksMap as listMap

class DrinkListAdapter() :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val HEADER = 0
    private val ITEM = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM) {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.drink_card,
                    parent, false)
            ItemViewHolder(itemView)
        } else {
            val header = LayoutInflater.from(parent.context).inflate(R.layout.drinks_header,
                    parent, false)
            HeaderViewHolder(header)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return HEADER
        var sizer = 1

        val list = ArrayList<String>()
        for (i in CategoryListFragment.categoriesList) {
            if (i.isSelected) list.add(i.name)
        }
        for (i in list) {
            sizer += listMap[i]!!.size
            if (sizer == position) return HEADER
            sizer += 1
            if (sizer > position) break
        }
        return ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (CategoryListFragment.categoriesList.size == 0) return
        if (holder is HeaderViewHolder) {
            val header = getHeader(position)
            holder.headerTextView.text = header
        } else if (holder is ItemViewHolder) {
            val drink = getItem(position)
            holder.textView.text = drink!!.name
            Glide.with(holder.textView.context).load(drink.imageUrl).into(holder.imageView)
        }
    }

    override fun getItemCount(): Int {
        var size = 0
        for (i in listMap) {
            size++
            size += i.value.size
        }
        return size
    }

    private fun getHeader(pos: Int): String? {
        val list = ArrayList<String>()
        for (i in CategoryListFragment.categoriesList) {
            if (i.isSelected) list.add(i.name)
        }
        if (pos == 0) return list[0]
        var sizer = 0
        for (i in list) {
            sizer += listMap[i]!!.size + 1
            if (pos == sizer) return list[list.indexOf(i) + 1]
        }
        return null
    }

    private fun getItem(pos: Int): Drink? {
        var sizer = 0
        val list = ArrayList<String>()
        for (i in CategoryListFragment.categoriesList) {
            if (i.isSelected) list.add(i.name)
        }
        for (i in list) {
            sizer += listMap[i]!!.size + 1
            if (pos < sizer) {
                val index = sizer - listMap[i]!!.size
                val l: ArrayList<Drink> = listMap.getOrDefault(i, ArrayList())
                return l[pos - index]
            }
        }
        return null
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.drink_pic)
        val textView: TextView = view.findViewById(R.id.drink_name)
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val headerTextView: TextView = view.findViewById(R.id.header)
    }
}