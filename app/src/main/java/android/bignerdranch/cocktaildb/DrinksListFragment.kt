package android.bignerdranch.cocktaildb

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.ArrayMap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.arrayMapOf
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap

class DrinksListFragment : Fragment(R.layout.drinks_list_fragment) {
    companion object{
        var drinksMap = TreeMap<String, ArrayList<Drink>>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.drinks_list_fragment, container, false)
        val recycler: RecyclerView = view.findViewById(R.id.recyclerview)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = DrinkListAdapter()
        (context as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recycler, newState)
                if(!recyclerView.canScrollVertically(3)){
                    loadDrinks()
                }
            }
        }
        recycler.addOnScrollListener(scrollListener)

        drinksMap = TreeMap()
        loadDrinks()


        return view
    }

    private fun loadDrinks(){
        val ser = Executors.newSingleThreadExecutor()
        ser.submit { APICall(context).loadDrinks() }
    }
}