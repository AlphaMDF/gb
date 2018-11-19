package com.zwsi.gb.feature

import android.os.Bundle
import android.provider.AlarmClock
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.zwsi.gblib.GBController

class RaceFragment : Fragment() {

    companion object {

        fun newInstance(message: String): RaceFragment {

            val f = RaceFragment()

            val bdl = Bundle(1)

            bdl.putString("uId", message)

            f.setArguments(bdl)

            return f

        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view: View? = inflater.inflate(R.layout.fragment_race, container, false);


        val raceID = arguments!!.getString("uId").toInt()

        val imageView = view!!.findViewById<ImageView>(R.id.RaceView)

        if (raceID == 0)
            imageView.setImageResource(R.drawable.xenost)
        else
            imageView.setImageResource(R.drawable.impit)


        val universe = GBController.universe
        val races = universe!!.racesArray
        val r = races[raceID]

        var stats = view.findViewById<TextView>(R.id.RaceStats)
        var paint = stats.paint
        paint.textSize = 40f

        stats.append("\n")
        stats.append("Name : " + (r!!.name) + "\n")
        stats.append("Type : " + (r.birthrate) + "\n")
        stats.append("Size : " + (r.explore) + "\n")
        stats.append("Owner: " + (r.absorption) + "\n")

        stats = view!!.findViewById<TextView>(R.id.RaceBackground)
        paint = stats.paint
        paint.textSize = 40f

        stats.append("\n")
        stats.append(r.description)









        return view
    }
}