package com.zwsi.gb.feature

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.Toast
import com.zwsi.gblib.GBController

class PlanetsSlideActivity : AppCompatActivity() {

    private lateinit var viewpager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planets_slide)

        initViews()
        setupViewPager()

        val intent = getIntent()
        val planetUID = intent.getIntExtra("planetUID", -1)
        if (planetUID > 0) {
            viewpager.setCurrentItem(planetUID)
        }

    }

    private fun initViews() {
        viewpager = findViewById(R.id.viewpager)
    }

    private fun setupViewPager() {

        val adapter = MyFragmentPagerAdapter(getSupportFragmentManager())

        val universe = GBController.universe

        for (pl in universe!!.allPlanets) {

            var pf: PlanetFragment = PlanetFragment.newInstance(pl.uid.toString())
            adapter.addFragment(pf, pl.uid.toString())
        }

        viewpager.adapter = adapter

    }


    /** Called when the user taps the Colonize button */
    fun colonize0(view: View) {


        val universe = GBController.universe
        universe!!.landPopulation(universe.allPlanets[view.id.toInt()], 0, 100)

        val message = "Landing Xenos on " + universe.allPlanets[view.id.toInt()].name
        Toast.makeText(view.context, message, Toast.LENGTH_LONG).show()

    }

    /** Called when the user taps the Colonize button */
    fun colonize1(view: View) {
        val universe = GBController.universe
        universe!!.landPopulation(universe.allPlanets[view.id.toInt()], 1, 100)

        val message = "Landing Impi on " + universe.allPlanets[view.id.toInt()].name
        Toast.makeText(view.context, message, Toast.LENGTH_LONG).show()

    }
}
