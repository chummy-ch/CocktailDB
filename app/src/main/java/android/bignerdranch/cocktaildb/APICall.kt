package android.bignerdranch.cocktaildb

import android.app.Activity
import android.app.DownloadManager
import android.app.VoiceInteractor
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

class APICall(private val context: Context?) {
    private val drinksArray = "drinks"
    private val drinkName = "strDrink"
    private val categoryName = "strCategory"
    private val drinkPicture = "strDrinkThumb"
    private var selectedCategories: ArrayList<String> = ArrayList()


    enum class Urls(val url: String) {
        DRINKS("https://www.thecocktaildb.com/api/json/v1/1/filter.php?c="),
        CATEGORIES("https://www.thecocktaildb.com/api/json/v1/1/list.php?c=list")
    }

    private fun findSelectedCategories() {
        for (i in CategoryListFragment.categoriesList) {
            if (i.isSelected) selectedCategories.add(i.name)
        }
    }

    fun loadDrinks() {
        val queue = Volley.newRequestQueue(context)
        val stringRequest: StringRequest
        if (CategoryListFragment.categoriesList.size == 0) {
            loadCategories()
        }
        while (CategoryListFragment.categoriesList.size == 0) {
            Thread.sleep(50)
        }
        findSelectedCategories()
        for (i in selectedCategories) {
            if(!DrinksListFragment.drinksMap.containsKey(i)){
                stringRequest = StringRequest(Request.Method.GET, Urls.DRINKS.url + i,
                        { response ->
                            setDrinks(response, i)
                        },
                        { Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show() })
                queue.add(stringRequest)
                break
            }
        }
    }

    fun loadCategories() {
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(Request.Method.GET, Urls.CATEGORIES.url,
                { response ->
                    setCategories(response)
                },
                { Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show() })
        queue.add(stringRequest)
    }

    private fun setDrinks(response: String, category: String) {
        val recycler: RecyclerView = (context as Activity).findViewById(R.id.recyclerview)
        val list: ArrayList<Drink> = arrayListOf()
        val obj = JSONObject(response)
        val jsArray = obj.getJSONArray(drinksArray)
        for (i in 0 until jsArray.length()) {
            val o = jsArray.getJSONObject(i)
            val d = Drink(o.getString(drinkName), o.getString(drinkPicture))
            list.add(d)
        }
        DrinksListFragment.drinksMap[category] = list
        if (DrinksListFragment.drinksMap.containsKey(selectedCategories[0]))
            recycler.adapter!!.notifyDataSetChanged()

    }

    private fun setCategories(response: String) {
        var recycler: RecyclerView? = null
        if (CategoryListFragment.categoriesList.size != 0)
            recycler = (context as Activity).findViewById(R.id.recycler_filters)
        val list: ArrayList<Category> = arrayListOf()
        val obj = JSONObject(response)
        val jsArray = obj.getJSONArray(drinksArray)
        for (i in 0 until jsArray.length()) {
            val cat = Category(jsArray.getJSONObject(i).getString(categoryName), true)
            list.add(cat)
        }
        CategoryListFragment.categoriesList = list
        if (recycler != null)
            recycler.adapter = CategoryListAdapter()
    }
}