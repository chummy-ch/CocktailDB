package android.bignerdranch.cocktaildb

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CategoryListFragment : Fragment(R.layout.categories_list_fragment) {

    companion object{
        var categoriesList: ArrayList<Category> = ArrayList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.categories_list_fragment,
                container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_filters)
        val button: Button = view.findViewById(R.id.apply_button)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val applyClick = View.OnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        button.setOnClickListener(applyClick)

        if(categoriesList.size == 0) APICall(context).loadCategories()
        else recyclerView.adapter = CategoryListAdapter()

        val toolbar = (context as AppCompatActivity).supportActionBar
        toolbar?.setDisplayHomeAsUpEnabled(true)

        return view
    }

}