package com.zwsi.gb.feature

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.zwsi.gblib.GBController

class StarFragment : Fragment() {

    companion object {

        fun newInstance(message: String): StarFragment {

            val f = StarFragment()

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

        var view: View? = inflater.inflate(R.layout.fragment_star, container, false);

        val starID = arguments!!.getString("uId").toInt()

        val imageView = view!!.findViewById<ImageView>(R.id.StarView)

        imageView.setImageResource(R.drawable.yellow)

        val universe = GBController.universe
        val stars = universe!!.allStars
        val st = stars[starID]

        var stats = view.findViewById<TextView>(R.id.StarStats)
        var paint = stats.paint
        paint.textSize = 40f

        stats.append("\n")
        stats.append("Name     : " + (st!!.name) + "\n")
        stats.append("Position : (" + (st.x) + ", " + st.y + ")\n")

        if (st.starShips.isNotEmpty()) {
            stats.append("Ships present:\n")
            for (sh in st.starShips) {
                stats.append("           " + sh.name + "\n")
            }
        }

        stats.append("\n")
        stats.append("Lorem ipsum dolor sit amet")

        return view
    }
}
