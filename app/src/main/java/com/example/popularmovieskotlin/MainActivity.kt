package com.example.popularmovieskotlin

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    /* Configuration options for NavigationUI methods that interact with implementations of
    the app bar pattern such as Toolbar, CollapsingToolbarLayout, and ActionBar. */
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //force Night Mode
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        //setup toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //NavHostFragment provides an area within your layout for self-contained navigation to occur.
        val host: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_fragment) as NavHostFragment?
                ?: return

        /* Each NavHostFragment has a NavController that defines valid navigation within the navigation host.
        This includes the navigation graph as well as navigation state such as current location and back stack
        that will be saved and restored along with the NavHostFragment itself. */
        val navController = host.navController

        //Initializing appBarConfiguration
        appBarConfiguration = AppBarConfiguration(navController.graph)

        /* Sets up the ActionBar returned by AppCompatActivity.getSupportActionBar() for use with a NavController.
        By calling this method, the title in the action bar will automatically be updated
        when the destination changes (assuming there is a valid label). */
        setupActionBarWithNavController(navController, appBarConfiguration)


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
// Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.main_fragment).navigateUp(appBarConfiguration)
    }
}
