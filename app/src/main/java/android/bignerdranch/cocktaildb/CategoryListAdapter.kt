package android.bignerdranch.cocktaildb

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import android.bignerdranch.cocktaildb.CategoryListFragment.Companion.categoriesList as values

class CategoryListAdapter() :
        RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        CategoryListFragment.categoriesList = values
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.category_card,
                parent, false)
        val textView = itemView.findViewById<TextView>(R.id.category_name)

        itemView.setOnClickListener {
            val image = itemView.findViewById<ImageView>(R.id.category_checker)
            val id = getIdByName(textView.text.toString())
            if (image.drawable == null) {
                image.setImageResource(R.drawable.ic_tick)
                values[id].isSelected = true
            }
            else if(image.drawable != null) {
                image.setImageDrawable(null)
                values[id].isSelected = false
            }
            CategoryListFragment.categoriesList = values
        }

        return ViewHolder(itemView)
    }

    private fun getIdByName(name: String?): Int{
        if(name == null) return -1
        for(i in 0 until values.size){
            if(values[i].name == name) return i
        }
        return -1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView!!.text = values[position].name
        if(!values[position].isSelected) holder.imageView.setImageDrawable(null)
    }

    override fun getItemCount() = values.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.category_checker)
        val textView: TextView? = view.findViewById(R.id.category_name)
    }
}

