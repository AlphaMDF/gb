package com.zwsi.gb.feature

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.zwsi.gblib.GBController
import com.zwsi.gblib.GBController.Companion.universe

class ShipFragment : Fragment() {

    companion object {

        fun newInstance(message: String): ShipFragment {

            val f = ShipFragment()

            val bdl = Bundle(1)

            bdl.putString("UID", message)

            f.setArguments(bdl)

            return f

        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View? = inflater.inflate(R.layout.fragment_ship, container, false);

        // What is this fragment about, and make sure the fragment remembers
        val shipID = arguments!!.getString("UID")!!.toInt()
        val sh = GBController.universe.getAllShipsList()[shipID]

        // TODO Nee to handle ship not found once ships get detroyed

        view!!.tag = sh

        val imageView = view.findViewById<ImageView>(R.id.ShipView)

        if (sh.idxtype == 0) {
            imageView.setImageResource(R.drawable.factory)

            view.findViewById<Button>(R.id.makePod).setVisibility(View.VISIBLE)
            view.findViewById<Button>(R.id.makeCruiser).setVisibility(View.VISIBLE)

        } else if (sh.idxtype == 1) {
            if (sh.race.uid == 2) {
                imageView.setImageResource(R.drawable.beetlepod)

            } else {
                imageView.setImageResource(R.drawable.podt)
            }
        } else if (sh.idxtype == 2) {
            imageView.setImageResource(R.drawable.cruisert)
        } else
            imageView.setImageResource(R.drawable.yellow)


        var stats = view.findViewById<TextView>(R.id.ShipStats)
        var paint = stats.paint
        paint.textSize = 40f

        stats.append("\n")
        stats.append("Name: " + sh.name + "\n")
        stats.append("Type: " + sh.type + "\n")
        stats.append("Speed: " + sh.speed + "\n")
        stats.append("Race: " + sh.race.name + "\n")
        stats.append("Location: " + sh.loc.getLocDesc() + "\n")


        stats = view.findViewById<TextView>(R.id.ShipBackground)
        paint = stats.paint
        paint.textSize = 40f

        stats.append("\n")
        stats.append(
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor " +
                    "invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.\n"
        )

        stats.append("\n")
        stats.append("id:" + sh.id + " | ")
        stats.append("refUID:" + sh.uid + " | ")
        stats.append("idxt:" + sh.idxtype + " | ")
        stats.append("loca:" + sh.loc.level + "." + sh.loc.refUID+ "\n")


        if (sh.idxtype == 0) { // TODO Better to test for speed > 0
            view.findViewById<Spinner>(R.id.spinner).setVisibility(View.GONE)
            view.findViewById<Button>(R.id.flyTo).setVisibility(View.GONE)
        }

        // TODO: better selection of possible targets once we have visibility. Right now it's all insystem
        // and first planet of each system outside.
        var planetsLocal = arrayListOf<String>()
        if (sh.getStar() != null) {
            for (p in sh.getStar()!!.starPlanets) {
                planetsLocal.add(p.name)
            }

            for (s in universe.allStars) {
                planetsLocal.add(s.starPlanets[0].name)
            }
        }
        // Create an ArrayAdapter
        val adapter = ArrayAdapter<String>(this.activity!!, android.R.layout.simple_spinner_item, planetsLocal)

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        var spinner = view.findViewById<Spinner>(R.id.spinner)
        spinner.adapter = adapter

        val flyButton = view.findViewById<Button>(R.id.flyTo)
        flyButton.tag = spinner

        return view
    }
}
