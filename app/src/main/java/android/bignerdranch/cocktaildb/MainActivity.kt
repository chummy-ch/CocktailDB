package android.bignerdranch.cocktaildb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.commit
import androidx.fragment.app.replace

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.elevation = 100f

        if(savedInstanceState == null){
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<DrinksListFragment>(R.id.fragment_container)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.category, menu)
        return true
    }

    private fun createCategoryFragment(){
        val manager = supportFragmentManager
        manager.commit{
            replace<CategoryListFragment>(R.id.fragment_container)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.filter ->{
                createCategoryFragment()
                return true
            }
            android.R.id.home -> {
                supportFragmentManager.popBackStack()
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}