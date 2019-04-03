// Copyright 2018-2019 Louis Perrochon. All rights reserved

// Race AIs are here

package com.zwsi.gblib

import com.zwsi.gblib.GBController.Companion.u
import com.zwsi.gblib.GBData.Companion.BATTLESTAR
import com.zwsi.gblib.GBData.Companion.CRUISER
import com.zwsi.gblib.GBData.Companion.FACTORY
import com.zwsi.gblib.GBData.Companion.POD
import com.zwsi.gblib.GBData.Companion.SHUTTLE
import com.zwsi.gblib.GBData.Companion.STATION
import kotlin.math.PI

class GBAutoPlayer() {

    companion object {

        // Whether autoplayers kill each other or not (i.e. fly to each otehrs home planet and attack headquarters)
        // TODO either make a universe setting or remove
        private fun autoKillauto(): Boolean {
            return true
        }

        private fun findOrOrderFactory(r: GBRace): GBShip? {
            val factory = r.raceShips.filter { it.idxtype == FACTORY }.sortedBy { -it.uid }.firstOrNull()
            if (factory == null) {
                val order = GBOrder()
                order.makeStructure(r.getHome().uid, r.uid, FACTORY)
                u.orders.add(order)
            }
            return factory
        }

        private fun orderFactories(r: GBRace) {
            for (planet in u.planets.values) {
                if (r.uid in planet.planetUidRaces){
                    if (planet.landedShips.filter { it.uidRace == r.uid && it.idxtype == FACTORY }.isEmpty()) {
                        val order = GBOrder()
                        order.makeStructure(planet.uid, r.uid, FACTORY)
                        u.orders.add(order)
                    }
                }
            }
        }

        fun playXenos() {

            val r = u.race(0)
            GBLog.d("(Not) Playing Xenos in turn $u.turn")

        }

        fun playImpi() {

            val r = u.race(1)
            GBLog.d("Playing Impi in turn $u.turn")

        }

        fun playBeetle() {

            // TODO: Beetles just make a new queen/headquarter if they lose it...
            val r = u.race(2)
            GBLog.d("Playing Beetles in turn ${u.turn} (${u.ship(r.uidHeadquarters).health})")

            // Find factory and order a pod. If we don't have a factory order one at home.
            val factory = findOrOrderFactory(r) ?: return

            orderFactories(r)

            val order = GBOrder()
            order.makeShip(factory.uid, POD)
            u.orders.add(order)
            GBLog.d("Ordered Pod")

            // Send any pod that doesn't have a destination to some random planet
            for (pod in r.raceShips.filter { (it.idxtype == GBData.POD) && (it.dest == null) }) {

                // TODO Fun: Other than random. E.g. order all planets by direction to create a nice spiral :-)
                val planet = u.planets[GBData.rand.nextInt(u.planets.size)]!!
                val loc = GBLocation(planet, 0, 0) // TODO Have caller give us a better location?
                GBLog.d("Setting Destination of " + pod.name + " to " + planet.name)
                pod.dest = loc
            }
            GBLog.d("Directed Pods")
        }


        fun playTortoise() {

            val r = u.race(3)
            GBLog.d("Playing Tortoise in  turn  $u.turn")

            // Find factory and order a cruiser, up to a certain number of shipsData. If we don't have a factory order one
            val factory = findOrOrderFactory(r) ?: return

            if (r.raceShips.filter { it.idxtype == CRUISER }.size < 31) {
                val order = GBOrder()
                order.makeShip(factory.uid, CRUISER)
                u.orders.add(order)
            } else if (r.raceShips.filter { it.idxtype == STATION }.size < 5) {
                val order = GBOrder()
                order.makeShip(factory.uid, STATION)
                u.orders.add(order)
            } else if (r.raceShips.filter { it.idxtype == SHUTTLE }.size < 5) {
                val order = GBOrder()
                order.makeShip(factory.uid, SHUTTLE)
                u.orders.add(order)
            } else if (r.raceShips.filter { it.idxtype == BATTLESTAR }.size < 5) {
                val order = GBOrder()
                order.makeShip(factory.uid, BATTLESTAR)
                u.orders.add(order)
            }
            // Send any cruiser that's landed (likely freshly made) to some random planet, but not the beetle home
            val homeBeetle = u.race(2).getHome()
            for (cruiser in r.raceShips.filter
            {
                (it.idxtype == GBData.CRUISER) && (it.loc.level == GBLocation.ORBIT) && it.loc.uidRef == r.uidHomePlanet
            }.drop(5)) {
                val planet = u.planets[GBData.rand.nextInt(u.planets.size)]!!
                if (planet != homeBeetle || autoKillauto()) {
                    val loc = GBLocation(planet, GBData.PlanetOrbit, GBData.rand.nextFloat() * 2 * PI.toFloat())
                    GBLog.d("Setting Destination of " + cruiser.name + " to " + planet.name)
                    cruiser.dest = loc

                } // else just try again next time this code runs...
            }

        }

        fun play5() {

            val r = u.race(4)
            GBLog.d("Playing Impi in turn $u.turn")

            val factory = findOrOrderFactory(r) ?: return

            if (r.raceShips.filter { it.idxtype == CRUISER }.size < 31) {
                val order = GBOrder()
                order.makeShip(factory.uid, CRUISER)
                u.orders.add(order)
            } else if (r.raceShips.filter { it.idxtype == STATION }.size < 5) {
                val order = GBOrder()
                order.makeShip(factory.uid, STATION)
                u.orders.add(order)
            } else if (r.raceShips.filter { it.idxtype == SHUTTLE }.size < 5) {
                val order = GBOrder()
                order.makeShip(factory.uid, SHUTTLE)
                u.orders.add(order)
            } else if (r.raceShips.filter { it.idxtype == BATTLESTAR }.size < 5) {
                val order = GBOrder()
                order.makeShip(factory.uid, BATTLESTAR)
                u.orders.add(order)
            }
            // Send any cruiser that's landed (likely freshly made) to some random planet, but not the beetle home
            val homeBeetle = u.race(2).getHome()
            for (cruiser in r.raceShips.filter
            {
                (it.idxtype == GBData.CRUISER) && (it.loc.level == GBLocation.ORBIT) && it.loc.uidRef == r.uidHomePlanet
            }.drop(5)) {
                val planet = u.planets[GBData.rand.nextInt(u.planets.size)]!!
                if (planet != homeBeetle || autoKillauto()) {
                    val loc = GBLocation(planet, GBData.PlanetOrbit, GBData.rand.nextFloat() * 2 * PI.toFloat())
                    GBLog.d("Setting Destination of " + cruiser.name + " to " + planet.name)
                    cruiser.dest = loc

                } // else just try again next time this code runs...
            }
        }


        fun play6() {

            val r = u.race(5)
            GBLog.d("Playing Impi in turn $u.turn")

            val factory = findOrOrderFactory(r) ?: return

            if (r.raceShips.filter { it.idxtype == CRUISER }.size < 31) {
                    val order = GBOrder()
                    order.makeShip(factory.uid, CRUISER)
                    u.orders.add(order)
                } else if (r.raceShips.filter { it.idxtype == STATION }.size < 5) {
                    val order = GBOrder()
                    order.makeShip(factory.uid, STATION)
                    u.orders.add(order)
                } else if (r.raceShips.filter { it.idxtype == SHUTTLE }.size < 5) {
                    val order = GBOrder()
                    order.makeShip(factory.uid, SHUTTLE)
                    u.orders.add(order)
                } else if (r.raceShips.filter { it.idxtype == BATTLESTAR }.size < 5) {
                    val order = GBOrder()
                    order.makeShip(factory.uid, BATTLESTAR)
                    u.orders.add(order)
                }
            // Send any cruiser that's landed (likely freshly made) to some random planet, but not the beetle home
            val homeBeetle = u.race(2).getHome()
            for (cruiser in r.raceShips.filter
            {
                (it.idxtype == GBData.CRUISER) && (it.loc.level == GBLocation.ORBIT) && it.loc.uidRef == r.uidHomePlanet
            }.drop(5)) {
                val planet = u.planets[GBData.rand.nextInt(u.planets.size)]!!
                if (planet != homeBeetle || autoKillauto()) {
                    val loc = GBLocation(planet, GBData.PlanetOrbit, GBData.rand.nextFloat() * 2 * PI.toFloat())
                    GBLog.d("Setting Destination of " + cruiser.name + " to " + planet.name)
                    cruiser.dest = loc

                } // else just try again next time this code runs...
            }
        }

    }
}