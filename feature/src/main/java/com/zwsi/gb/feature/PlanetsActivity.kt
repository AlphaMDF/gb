package com.zwsi.gb.feature

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import com.zwsi.gblib.GBTest
import android.support.constraint.ConstraintLayout
import android.widget.TextView
import android.support.constraint.ConstraintSet
import android.support.v4.view.ViewCompat


class PlanetsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planets)

        var planet = findViewById<ImageView>(R.id.homeplanet)

        val d = BitmapFactory.decodeResource(getResources(), R.drawable.desert)
        val f = BitmapFactory.decodeResource(getResources(), R.drawable.forest)
        val g = BitmapFactory.decodeResource(getResources(), R.drawable.gas)
        val i = BitmapFactory.decodeResource(getResources(), R.drawable.ice)
        val l = BitmapFactory.decodeResource(getResources(), R.drawable.land)
        val m = BitmapFactory.decodeResource(getResources(), R.drawable.mountain)
        val r = BitmapFactory.decodeResource(getResources(), R.drawable.rock)
        val w = BitmapFactory.decodeResource(getResources(), R.drawable.water)
        val bitmaps = arrayOf(w,l,g,d,m,f,i,r)

        var merged = Bitmap.createBitmap(200, 100, d.config)
        var canvas = Canvas(merged)

        canvas.drawBitmap(bitmaps[0], 0f, 0f, null)
        canvas.drawBitmap(bitmaps[1], 50f, 0f, null)
        canvas.drawBitmap(bitmaps[2], 100f, 0f, null)
        canvas.drawBitmap(bitmaps[3], 150f, 0f, null)
        canvas.drawBitmap(bitmaps[4], 0f, 50f, null)
        canvas.drawBitmap(bitmaps[5], 50f, 50f, null)
        canvas.drawBitmap(bitmaps[6], 100f, 50f, null)
        canvas.drawBitmap(bitmaps[7], 150f, 50f, null)

        planet.setImageBitmap(merged)


        // Now add the other planets below
        val linearLayout1 = findViewById(R.id.planetsLinearLayout) as LinearLayout

        val universe = GBTest.getUniverse()
        val stars = universe.getStars()
        for (s in stars) {
            val planets = universe.getPlanets(s)
            for (p in planets) {

                val constraintLayout = ConstraintLayout(this)
                linearLayout1.addView(constraintLayout)

                planet = ImageView(this)
                planet.imageAlpha = 255
                merged = Bitmap.createBitmap(p.getWidth()*50, p.getHeight()*50, d.config)
                canvas = Canvas(merged)


                val sectors = universe.getSectors(p)
                for (h in 0 until sectors.size) {
                    for (w in 0 until sectors[h].size) {
                        canvas.drawBitmap(bitmaps[sectors[h][w].type],w*50f,h*50f,null)
                    }
                }

                planet.setImageBitmap(merged)
                planet.setId(ViewCompat.generateViewId()) // Needs to have an ID. View.setId() is API level 17+
                constraintLayout.addView(planet)

                val planetStats = TextView(this)
                planetStats.setText("Planet: " + p.name +"\n")
                planetStats.append("Type: "+ p.type_string +"\n")
                planetStats.setTextSize(14f)
                planetStats.setId(ViewCompat.generateViewId()) // Needs to have an ID. View.setId() is API level 17+
                constraintLayout.addView(planetStats)

                val cs = ConstraintSet()
                cs.clone(constraintLayout)

                cs.constrainHeight(planet.id,200)
                cs.constrainWidth(planet.id,400)
                //cs.connect(planet.id,ConstraintSet.TOP,constraintLayout.id,ConstraintSet.TOP,16)
                //cs.connect(planet.id,ConstraintSet.BOTTOM,constraintLayout.id,ConstraintSet.BOTTOM,16)
                //cs.connect(planet.id,ConstraintSet.LEFT,constraintLayout.id,ConstraintSet.LEFT,16)
                //cs.connect(planet.id,ConstraintSet.RIGHT,constraintLayout.id,ConstraintSet.RIGHT,16)

                //cs.centerVertically(planet.id,planetStats.id)

                // why does this work left/right but not right/left?
                cs.connect(planet.getId(), ConstraintSet.LEFT, planetStats.id, ConstraintSet.RIGHT, 16)

                cs.applyTo(constraintLayout)

            }
        }


    }
}