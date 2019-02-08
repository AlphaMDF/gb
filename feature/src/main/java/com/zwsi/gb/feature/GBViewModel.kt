package com.zwsi.gb.feature

import android.arch.lifecycle.MutableLiveData
import com.zwsi.gblib.GBController
import com.zwsi.gblib.GBController.Companion.u
import com.zwsi.gblib.GBShip
import com.zwsi.gblib.GBxy
import kotlin.system.measureNanoTime


class GBViewModel {

    // Recommended is tying this to the life cycle of one (1) activity, and have different ones for each activity
    // However MapView requires a huge fraction of all the data, and most other views are subsets so it seems
    // wasteful to not share the ViewModel. That's why this is an app scoped singleton.
    //

    companion object {

        // maybe live data will come from GBController or so, and pass it all over, and this will go away?
        val currentTurn by lazy {MutableLiveData<Int>()}

        var viewStars = u.getAllStarsMap()
        var viewPlanets = u.getAllPlanetsMap()
        var viewRaces = u.getAllRacesMap()

        var viewShips = u.getAllShipsList()
        var viewDeepSpaceShips = u.deepSpaceShips
        var viewDeadShips = u.getDeadShipsList()

        var viewStarShips: HashMap<Int, List<GBShip>> = HashMap()
        var viewOrbitShips: HashMap<Int, List<GBShip>> = HashMap()
        var viewLandedShips: HashMap<Int, List<GBShip>> = HashMap()
        var viewRaceShips: HashMap<Int, List<GBShip>> = HashMap()
        var viewShipTrails: HashMap<Int, List<GBxy>> = HashMap()

        var viewShots = u.getAllShotsList()

        var timeModelUpdate = 0L
        var timeLastTurn = 0L

        var times = mutableMapOf<String, Long>()

        init {
            fillViewStarShips()
            fillViewShipTrails()
            fillViewOrbitShips()
            fillViewLandedShips()
            update()
        }

        fun update() {
            // This is (currently) called from the worker thread. So need to call postValue on the LiveData
            timeModelUpdate = measureNanoTime {

                // Not updating stars and planets as those lists don't change
                // If we made a deep copy of stars and planets we would need to copy changed data, e.g. location

                // Ships
                times["mAS"] = measureNanoTime { viewShips = u.getAllShipsList() }

                times["mDS"] = measureNanoTime { viewDeepSpaceShips = u.deepSpaceShips }

                times["m+S"] = measureNanoTime { viewDeadShips = u.getDeadShipsList() }

                times["mSS"] = measureNanoTime { fillViewStarShips() }

                times["mOS"] = measureNanoTime { fillViewOrbitShips() }

                times["mLS"] = measureNanoTime { fillViewLandedShips() }

                times["mRS"] = measureNanoTime { fillViewRaceShips() }

                times["mST"] = measureNanoTime { fillViewShipTrails() }

                times["mSh"] = measureNanoTime { viewShots = u.getAllShotsList() }

                timeLastTurn = GBController.elapsedTimeLastUpdate
            }

            currentTurn.postValue(u.turn)

            /*
            Note: You must call the setValue(T) method to update the LiveData object from the main thread. If the code is executed in a worker thread, you can use the postValue(T) method instead to update the LiveData object.
             */
        }

        fun fillViewStarShips() {
            viewStarShips.clear()
            for ((_, s) in viewStars) {
                viewStarShips.put(s.uid, s.starShipList)
            }
        }

        fun fillViewOrbitShips() {  // PERF combine with the next and iterate only once
            viewOrbitShips.clear()
            for ((_, p) in viewPlanets) {
                viewOrbitShips.put(p.uid, p.orbitShips)
            }
        }

        fun fillViewLandedShips() {
            viewLandedShips.clear()
            for ((_, p) in viewPlanets) {
                viewLandedShips.put(p.uid, p.landedShips)
            }
        }

        fun fillViewRaceShips() {
            viewRaceShips.clear()
            for ((_, race) in viewRaces) {
                viewRaceShips.put(race.uid, race.raceShipsList)
            }
        }

        fun fillViewShipTrails() {
            viewShipTrails.clear()
            for (sh in viewShips.filter { it.health > 0 }) {
                viewShipTrails.put(sh.uid, sh.getTrailList())
            }
        }

    }


}