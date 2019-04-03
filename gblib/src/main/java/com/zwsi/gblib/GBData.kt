// Copyright 2018-2019 Louis Perrochon. All rights reserved

// GBData contain
//  - All the global static data
//    includes names, types and other attributes of stars, planets, sectors
//  - All the creation logic for stars, planets, sectors
//
// GB Data is a separate class because later we want to read this from an external source (JSON file, web, db, etc.)

// FEATURE read universe data from a config file or other external data source

package com.zwsi.gblib

import java.util.*

class GBData {

    init {
    } // Eventually we may be able to get rid of this, and be an object only

    companion object {

        // TODO Doesn't belong here but don't have a better place for filename of current game
        const val currentGameFileName = "currentgame.json"

        val rand = Random(1) // Our RNG. We could seed it for testing. Make it var, and assign in init block?

        internal const val UniverseMaxX = 1000 // Width of the Universe
        internal const val UniverseMaxY = 1000 // Height of the Universe
        // TODO difference between these two
        internal const val starMaxOrbit = 40f // Radius of star systems
        const val MaxSystemOrbit = 36f // Radius of the orbit of the outermost Planet
        const val PlanetOrbit = 2f // Radius of planetary Orbit

        internal const val MinNumberOfPlanets = 2
        internal const val MaxNumberOfPlanets = 8

        internal const val Mission1 = "Mission 1"
        internal const val Mission2 = "Mission 2"
        internal const val Mission3 = "Mission 3"
        internal const val Mission4 = "Mission 4"
        internal const val Mission5 = "Mission 5"
        internal const val Mission6 = "Mission 6"
        internal const val MissionRandom = "Random Mission"
        internal const val Map1 = "Map 1"
        internal const val Map2 = "Map 2"
        internal const val Map3 = "Map 3"
        internal const val MapRandom = "Random Map"

        internal fun selectStarNameIdx(): Int {
            // TODO Science no dupes in System Names - not a high priority, real world may have duplicates...
            return rand.nextInt(starNames.size)
        }

        internal fun starNameFromIdx(n: Int): String {
            return starNames[n]
        }

        internal fun selectPlanetNameIdx(): Int {
            // TODO Science no dupes in Planet Names - not a high priority, real world may have duplicates...
            return rand.nextInt(planetNames.size)
        }

        internal fun planetNameFromIdx(n: Int): String {
            return planetNames[n]
        }

        internal fun selectPlanetTypeIdx(): Int {
            // TODO Science specified distribution based on star type, instead of random
            return rand.nextInt(planetTypesNames.size)
        }

        internal fun planetTypeFromIdx(n: Int): String {
            return planetTypesNames[n]
        }

        internal fun selectPlanetSize(typeIdx: Int): Pair<Int, Int> {
            val low = planetTypesSize[typeIdx][0]
            val hi = planetTypesSize[typeIdx][1]
            val height = GBData.rand.nextInt(hi - low) + low
            return Pair(height, height * 2)
        }

        // Types from: Galactic Bloodshed
        private val planetTypesNames =
            arrayOf("M Class", "Jovian", "Water", "Desert", "Forest", "Iceball", "Airless", "Asteroid")

        internal val planetTypesSize = arrayOf(
            // min height, max height. Width will be within 2x height range
            intArrayOf(4, 6), // M Class
            intArrayOf(6, 8), // Jovian
            intArrayOf(4, 6), // Water
            intArrayOf(4, 6), // Desert
            intArrayOf(3, 5), // Forest
            intArrayOf(3, 4), // Iceball
            intArrayOf(3, 5), // Airless
            intArrayOf(2, 3)  // Asteroid
        )

        // Sectors
        // Planets are rectangles of sectors with wrap-arounds on the sides. Think Mercator.
        // Sector Types from: Galactic Bloodshed
        // Source had 7 types, added 8th type "rock" because I had a bitmap, and because asteroids and mountains are different
        //private val sectorTypesNames = arrayOf("Water", "Land", "Gas", "Desert", "Mountain", "Forest", "Ice", "Rock")

        private val sectorTypesConsole = arrayOf("~", ".", "@", "-", "^", "*", "#", "x")
        fun sectorTypeConsoleFromIdx(n: Int): String {
            return sectorTypesConsole[n]
        }

        private val sectorTypesMoney = arrayOf(1, 2, 1, 1, 2, 1, 0, 2)
        fun sectorMoneyFromIdx(n: Int): Int {
            return sectorTypesMoney[n]
        }

        private val sectorTypesMaxPopulation = arrayOf(1000, 2000, 200, 500, 800, 5000, 100, 200)
        fun sectorMaxPopulationFromIdx(n: Int): Int {
            return sectorTypesMaxPopulation[n]
        }

        internal val sectorTypesChance = arrayOf(
            intArrayOf(0, 0, 0, 0, 1, 1, 1, 1, 5, 6), // M Class
            intArrayOf(2, 2, 2, 2, 2, 2, 2, 2, 2, 2), // Jovian
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 4), // Water
            intArrayOf(3, 3, 3, 3, 3, 3, 3, 3, 7, 7), // Desert
            intArrayOf(5, 5, 5, 5, 5, 5, 5, 5, 5, 1), // Forest
            intArrayOf(7, 7, 6, 6, 6, 6, 6, 6, 6, 6), // Iceball
            intArrayOf(1, 7, 7, 3, 3, 3, 4, 4, 4, 6), // Airless
            intArrayOf(7, 7, 7, 7, 7, 7, 7, 7, 6, 6)  // Asteroid
        )

        // Races
        internal data class RaceData(
            val name: String,
            val birthrate: Int, // 00...100 (originally 0..1?)
            val explore: Int, // adventurism 00..100 (originally percent)
            val absorption: Int,
            val production: Int,
            val description: String
        )

        private val races = hashMapOf(
            0 to RaceData(
                "Xenos", 50, 20, 20, 50,
                "Xenos can’t burn and they can’t suffocate. " +
                        "They can glide at 65mph and they can run at 20 mph and they walk at 5mph. " +
                        "They can find underground materials fast."
            ),
            1 to RaceData(
                "Impis", 20, 50, 10, 50,
                "Impis can breath underwater, they swim at 80mph and they can change color like a chameleon. " +
                        "They run at 30 mph, they walk at 5.3mph and can use all four hands for piloting, fighting and " +
                        "other activities."
            ),
            2 to RaceData(
                "Rainbow Beetles", 10, 5, 0, 10,
                "Beetles have small pincers and they fly like a beetle at 25 mph. They crawl at 40mph, " +
                        "can see in all directions and they can hang upside down and climb walls."
            ),
            3 to RaceData(
                "Warhide Tortoises", 5, 20, 0, 50,
                "Tortoises have hard shells, can see with three heads and have super sharp claws. \n" +
                        "They move at 25 mph,  can swim at 65mph. Its sharp shell can turn into a spinning shell.\n"
            ),
            4 to RaceData(
                "Tools", 20, 50, 10, 50,
                "Tools are transformers and they are good pilots."
            ),
            5 to RaceData(
                "Elemental Ghost Babies", 20, 50, 10, 50,
                "Can Summon Fire and can summon water. They can hover in any gas and move at 25 mph."
            )
        )

        internal fun getRaceName(idx: Int): String {
            return races.get(idx)!!.name
        }

        internal fun getRaceBirthrate(idx: Int): Int {
            return races.get(idx)!!.birthrate
        }

        internal fun getRaceExplore(idx: Int): Int {
            return races.get(idx)!!.explore
        }

        internal fun getRaceAbsorption(idx: Int): Int {
            return races.get(idx)!!.absorption
        }

        internal fun getRaceProduction(idx: Int): Int {
            return races.get(idx)!!.production
        }


        internal fun getRaceDescription(idx: Int): String {
            return races.get(idx)!!.description
        }

        const val FACTORY = 0
        const val POD = 1
        const val CRUISER = 2
        const val HEADQUARTERS = 3
        const val RESEARCH = 4
        const val SHUTTLE = 5
        const val BATTLESTAR = 6
        const val STATION = 7

        data class ShipData(
            val type: String,
            val speed: Int,
            val shots: Int,
            val damage: Int,
            val range: Int,
            val health: Int,
            val cost: Int,
            val surface: Boolean
        )

        val shipsData = hashMapOf(
            FACTORY to ShipData("Factory", 0, 0, 0, 0, 500, 0, true),
            POD to ShipData("Spore Pod", 1, 0, 0, 0, 10, 10, true),
            CRUISER to ShipData("Cruiser", 3, 1, 40, 5, 100, 50, false),
            HEADQUARTERS to ShipData("Headquarters", 0, 0, 0, 0, 1000, 5000, true),
            RESEARCH to ShipData("Research", 0, 0, 0, 100, 100, 2000, true),
            SHUTTLE to ShipData("Shuttle", 3, 1, 0, 2, 50, 20, false),
            BATTLESTAR to ShipData("Battle Star", 1, 4, 60, 8, 1000, 1000, false),
            STATION to ShipData("Orbital Station", 0, 6, 50, 10, 100, 500, false)
        )

        // Stars
        // Stars from: https://github.com/kaladron/galactic-bloodshed/blob/master/data/star.list
        private val starNames = arrayOf(
            "Abel",
            "Achernar",
            "Acrab",
            "Acticum",
            "Adhara",
            "Adara",
            "Adonis",
            "Akbar",
            "Albireo",
            "Alcor",
            "Aldebran",
            "Aleramin",
            "Alferatz",
            "Algol",
            "Alhema",
            "Alioth",
            "Alkalurops",
            "Almach",
            "Alnath",
            "Alpha",
            "Altair",
            "Aludra",
            "Alya",
            "Ananke",
            "Andromeda",
            "Antares",
            "Aphrodite",
            "Apocalypse",
            "Aquarius",
            "Ara",
            "Archernar",
            "Ares",
            "Arioch",
            "Artemis",
            "Ascella",
            "Auriga",
            "Avior",
            "Ayachi",
            "Azagthoth",
            "Baetica",
            "Bear",
            "Beholder",
            "Bellatrix",
            "Benetnasch",
            "Betelgeuse",
            "Bohr",
            "Bones",
            "Bootes",
            "Boreas",
            "Brahe",
            "Brin",
            "Brust",
            "Bujold",
            "Byron",
            "Calypso",
            "Camille",
            "Caneb",
            "Capella",
            "Capricorn",
            "Carina",
            "Cassiopeia",
            "Cat",
            "Centauri",
            "Cepheus",
            "Cetus",
            "Chi",
            "Citurnae",
            "Columba",
            "Copernicus",
            "Corona",
            "Crater",
            "Crux",
            "Cthulhu",
            "Cursa",
            "Cygnus",
            "Cylon",
            "Dalos",
            "Daphne",
            "Dacia",
            "Dark",
            "Data",
            "Death",
            "Delphinus",
            "Deneb",
            "Dionysus",
            "Diphda",
            "Donaldson",
            "Dorado",
            "Draco",
            "Dubhe",
            "Ea",
            "Eburacum",
            "Edessa",
            "Elrond",
            "Elymais",
            "Ekpe",
            "Emporium",
            "Eniph",
            "Epirus",
            "Epsilon",
            "Eridanus",
            "Erusa",
            "Eshu",
            "Eta",
            "Fantor",
            "Fermi",
            "Fisher",
            "Fomalhaut",
            "Fornax",
            "Fredux",
            "Galatia",
            "Gamma",
            "Gauss",
            "Gamorae",
            "Gemini",
            "Gerd",
            "Geinah",
            "Gnur",
            "Gomeisa",
            "Gorsu",
            "Grus",
            "Guiness",
            "Gungnir",
            "Hadar",
            "Halley",
            "Hamal",
            "Hawking",
            "Hector",
            "Heisenberg",
            "Hera",
            "Hercules",
            "Hephiastes",
            "Hispalis",
            "Hodge",
            "Hofstader",
            "Holly",
            "Hopper",
            "Horae",
            "Hospodar",
            "Hubble",
            "Hyades",
            "Hydrus",
            "Hyperion",
            "Iberia",
            "Iki",
            "Innana",
            "Interigon",
            "Iota",
            "Indus",
            "Inus",
            "Ishtar",
            "Iswar",
            "Izahami",
            "Jade",
            "Janus",
            "Jhurna",
            "Jocasta",
            "Jones",
            "Jove",
            "Jupiter",
            "Kappa",
            "Kaus",
            "Keats",
            "Kelsi",
            "Kenobi",
            "Kirk",
            "Kiltis",
            "Klingon",
            "Kocab",
            "Koivikko",
            "Kornophoros",
            "Kryten",
            "Lafka",
            "Lamarna",
            "Leia",
            "Leo",
            "Lepus",
            "Lesath",
            "Libra",
            "Lister",
            "Lucas",
            "Lugdunensis",
            "Lupus",
            "Lynx",
            "Lyra",
            "Magellan",
            "Malaca",
            "Mars",
            "Mayhew",
            "McCaffery",
            "Melitene",
            "Mercury",
            "Mesuta",
            "Megnazon",
            "Memphis",
            "Merak",
            "Meukalinan",
            "Miaplacidus",
            "Mimosa",
            "Mirach",
            "Mizar",
            "Moesia",
            "Monoceros",
            "Mordor",
            "Muphrid",
            "Musca",
            "Narbonensis",
            "Nemesis",
            "Neptune",
            "Nessus",
            "Newton",
            "Noricum",
            "Nuada",
            "Numidia",
            "Nunka",
            "Nysvaekoto",
            "Nyx",
            "Og",
            "Omega",
            "Omicron",
            "Oox",
            "Ophiucus",
            "Orion",
            "Otski",
            "Palmyra",
            "Pannonia",
            "Pavo",
            "Peace",
            "Pegasus",
            "Pelops",
            "Perseus",
            "Petra",
            "Phakt",
            "Pheonix",
            "Phi",
            "Picard",
            "Pictor",
            "Pisces",
            "Planck",
            "Pleides",
            "Pluto",
            "Polaris",
            "Procyon",
            "Psi",
            "Ptolemy",
            "Puppis",
            "Pyxis",
            "Qrdus",
            "Quadran",
            "Quipus",
            "Rantsu",
            "Rastaban",
            "Regulus",
            "Remus",
            "Reticulum",
            "Rhaetia",
            "Rho",
            "Rigel",
            "Riker",
            "Rimmer",
            "Rompf",
            "Rosenberg",
            "Rotarev",
            "Saberhagen",
            "Sadalrud",
            "Sagittarius",
            "Salamis",
            "Sandage",
            "Sargas",
            "Saturn",
            "Scorpio",
            "Scrolli",
            "Segusio",
            "Seleusia",
            "Servi",
            "Shapley",
            "Sheat",
            "Sigma",
            "Simmons",
            "Sinope",
            "Sirius",
            "Skywalker",
            "Sol",
            "Spica",
            "Spock",
            "Sulu",
            "Suomi",
            "Susa",
            "Taeffae",
            "Tammuz",
            "Tarazed",
            "Taurus",
            "Teller",
            "Tertsix",
            "Thospia",
            "Theta",
            "Thuban",
            "Trinus",
            "Tolkien",
            "Triangulum",
            "Tucana",
            "Umbriel",
            "Unukalkay",
            "Upsilon",
            "Uranus",
            "Ursa",
            "Urth",
            "Vader",
            "Valentia",
            "Vedas",
            "Vega",
            "Vela",
            "Venatici",
            "Virgo",
            "Volans",
            "War",
            "Wesen",
            "Williams",
            "Wirth",
            "Woosley",
            "Worf",
            "Xi",
            "Yima",
            "Yoda",
            "Yuggoth",
            "Yrga",
            "Zarg",
            "Zawijah",
            "Zeus",
            "Zot",
            "Zog",
            "Zozca",
            "Zeta",
            "Zuben"
        )

        // Planets
        // Planets from: https://github.com/kaladron/galactic-bloodshed/blob/master/data/planets.list
        private val planetNames = arrayOf(
            "Aasarus",
            "Aberius",
            "Abyssinia",
            "Aceldama",
            "Achates",
            "Acheron",
            "Achilles",
            "Acropolis",
            "Acrisis",
            "Adam",
            "Adrastea",
            "Adreaticus",
            "Aeetes",
            "Aegea",
            "Aegir",
            "Aeneas",
            "Aeolus",
            "Aesir",
            "Aesop",
            "Aether",
            "Agamemnon",
            "Agnar",
            "Agni",
            "Aiakos",
            "Ajax",
            "Akroteri",
            "Albion",
            "Alcmene",
            "Alexander",
            "Alfheim",
            "Amalthea",
            "Amaterasu",
            "Americos",
            "Amphion",
            "Anacreon",
            "Anchises",
            "Angakok",
            "Angurboda",
            "Ansu",
            "Antaeus",
            "Antigone",
            "Anubis",
            "Apache",
            "Apaosha",
            "Aphrodite",
            "Apsyrtus",
            "Aragorn",
            "Arapaho",
            "Argos",
            "Argus",
            "Ariadne",
            "Arion",
            "Armageddon",
            "Asgard",
            "Ashur",
            "Astarte",
            "Athens",
            "Atlantis",
            "Atlas",
            "Atropos",
            "Attica",
            "Audhumla",
            "Avalon",
            "Azazel",
            "AziDahak",
            "Azores",
            "Baal",
            "Babylon",
            "Bacchus",
            "Baezelbub",
            "Bagdemagus",
            "Balder",
            "Bakongo",
            "Bali",
            "Ban",
            "Baugi",
            "Bedivere",
            "Belial",
            "Bellerophon",
            "Bellona",
            "Benkei",
            "Bergelmir",
            "Bernoulli",
            "Berserks",
            "Bianca",
            "Bifrost",
            "Bohr",
            "Boeotia",
            "Bohort",
            "Bora",
            "Bors",
            "Boz",
            "Bragi",
            "Brave",
            "Brisingamen",
            "Britannia",
            "Buri",
            "Bushido",
            "Cadiz",
            "Caeser",
            "Cain",
            "Camelot",
            "Camlan",
            "Canan",
            "Carbonek",
            "Carme",
            "Carthage",
            "Celaeno",
            "Celephais",
            "Celeus",
            "Celt",
            "Cerberus",
            "Cetelus",
            "Ceto",
            "Cheng-huang",
            "Chansky",
            "Chaos",
            "Charon",
            "Charybdis",
            "Cheiron",
            "Cherubim",
            "Chi'ih-yu",
            "Chimera",
            "Chiron",
            "Chow",
            "Chuai",
            "Cirrus",
            "Claire",
            "Cleito",
            "Clotho",
            "Clytemnestra",
            "Cocytus",
            "Colchis",
            "Coniraya",
            "Cordelia",
            "Corinth",
            "Coriolis",
            "Cranus",
            "Crassus",
            "Cressida",
            "Cronus",
            "Cyclops",
            "Cyzicus",
            "Daedalus",
            "Dagon",
            "Damon",
            "Danae",
            "Danaus",
            "Dannaura",
            "Danube",
            "Dardanus",
            "Darkalheim",
            "Darwin",
            "Dawn",
            "Deganus",
            "Deimus",
            "Delphi",
            "Demeter",
            "Demise",
            "Demogorgon",
            "Depth",
            "Descartes",
            "Desdemona",
            "Desire",
            "Desolation",
            "Despoina",
            "Deucalion",
            "Devadatta",
            "Dharma",
            "Diana",
            "Dinka",
            "Diogenes",
            "Dis",
            "Djanggawul",
            "Dorian",
            "Dorus",
            "Doula",
            "Dove",
            "Dragon",
            "Draupnir",
            "Drak",
            "Dream",
            "Druj",
            "Dwarka",
            "Dyaus",
            "Earth",
            "Echo",
            "Ectoprimax",
            "Ector",
            "Eddas",
            "Edjo",
            "Eegos",
            "Ehecatl",
            "Einstein",
            "Elara",
            "Eldorado",
            "Electra",
            "Eleusis",
            "Ellindil",
            "Elysia",
            "Enceladus",
            "Endoprimax",
            "Enkindu",
            "Enlil",
            "Ephialtes",
            "Ephraites",
            "Epigoni",
            "Epimetheus",
            "Erebus",
            "Erech",
            "Ergos",
            "Erishkegal",
            "Eros",
            "Estanatleh",
            "Eteocles",
            "Eurasia",
            "Euripedes",
            "Europa",
            "Eurydice",
            "Eurytion",
            "Excalibur",
            "Exodus",
            "Falcha",
            "Februs",
            "FeiLien",
            "Fenrir",
            "FonLegba",
            "FourthWorld",
            "Freedom",
            "Freuchen",
            "Freyr",
            "Frigg",
            "Frisia",
            "Forseti",
            "Fu'hsi",
            "Fujin",
            "Future",
            "Gabora",
            "Gades",
            "Gadir",
            "Gaea",
            "Galactica",
            "Galahad",
            "Galanopoulus",
            "Galileo",
            "Gandalf",
            "Ganymede",
            "Garm",
            "Gaokerena",
            "Gathas",
            "Gawain",
            "Geirrod",
            "Gerda",
            "Germania",
            "Geryon",
            "Ghal",
            "Gil-galad",
            "Gilgamesh",
            "Gimli",
            "Ginnungagap",
            "Gjallarhorn",
            "Glispa",
            "Gondor",
            "Gorgias",
            "Gorgon",
            "Gorlois",
            "Gosadaya",
            "Graeae",
            "Granis",
            "Grunbunde",
            "Guinevere",
            "Hadad",
            "Halloway",
            "Halvec",
            "Haoma",
            "Harpie",
            "Haroeris",
            "Hathor",
            "Heaven",
            "Hebe",
            "Hecate",
            "Hegel",
            "Hei-tiki",
            "Heidrun",
            "Heidegger",
            "Heimdall",
            "Heisenburg",
            "Hel",
            "Helene",
            "Hell",
            "Helle",
            "Heracles",
            "Hermod",
            "Hesoid",
            "Hestia",
            "Himalia",
            "Hodur",
            "Hoeinir",
            "Homer",
            "Hope",
            "Hotei",
            "Hringhorni",
            "Hugi",
            "Huron",
            "Hyksos",
            "Hyperion",
            "Iacchos",
            "Iapetus",
            "Ibanis",
            "Icarus",
            "Idun",
            "Illiam",
            "Illyria",
            "Imperium",
            "Infinity",
            "Iolcus",
            "Ioue",
            "Irkalla",
            "Ismene",
            "Iulus",
            "Ixbalanque",
            "Ixmirsis",
            "Jackal",
            "Jamsid",
            "Jataka",
            "Jazdarnil",
            "Jena",
            "Jotars",
            "Jotunheim",
            "Juliet",
            "Jung",
            "Jurojin",
            "Ku'unLun",
            "Kachinas",
            "Kadath",
            "Kalki",
            "Kamakura",
            "Kant",
            "Karaja",
            "Karnak",
            "Kay",
            "Kepa",
            "Keshvar",
            "Kettoi",
            "Khepri",
            "Khwanirath",
            "Kierkegaard",
            "Kirin",
            "Kishijoten",
            "Koffler",
            "Kol",
            "Kunmanggur",
            "Kuya-Shonin",
            "Kythamil",
            "Labyrinth",
            "Lachesis",
            "Laindjung",
            "Laius",
            "Lancelot",
            "Lao-tze",
            "Lapiths",
            "Lares",
            "Larissa",
            "Larnok",
            "Leda",
            "Leibniz",
            "Leinster",
            "Lemnos",
            "Leng",
            "Leodegrance",
            "Lethe",
            "Leto",
            "Leviathan",
            "Lhard",
            "Lodbrok",
            "Lodur",
            "Logan",
            "Logi",
            "Loki",
            "Lot",
            "Lotan",
            "Love",
            "Lugundum",
            "Lungshan",
            "Luntag",
            "Luyia",
            "Lycurgus",
            "Lysithea",
            "Macha",
            "Mador",
            "Maenads",
            "Magna",
            "Mandara",
            "Manitou",
            "Marduk",
            "Marmora",
            "Mars",
            "Mashyane",
            "Medea",
            "Medusa",
            "Meepzorp",
            "Megara",
            "Meneleus",
            "Menrua",
            "Mercury",
            "Merlin",
            "Metis",
            "Midas",
            "Midgard",
            "Mimas",
            "Mimir",
            "Minoa",
            "Minos",
            "Miranda",
            "Miriam",
            "Mirkwood",
            "Mithrandir",
            "Mjolnir",
            "Modred",
            "Moirai",
            "Montaigne",
            "Morrigan",
            "Mulungu",
            "Munin",
            "Muspellheim",
            "Nada",
            "Nagilfar",
            "Naiad",
            "Nanna",
            "Narcissus",
            "Nardis",
            "Nebo",
            "Nelligan",
            "Neptune",
            "Nereid",
            "Nietszche",
            "Night",
            "Ninhursag",
            "Ninigi",
            "Nirvana",
            "Njord",
            "Norn",
            "Nu-ku",
            "Nuba",
            "Nuptadi",
            "Nyambe",
            "Nyarlathotep",
            "Nymph",
            "Oberon",
            "Obolus",
            "Oceana",
            "Od",
            "Odin",
            "Odysseus",
            "Oedipus",
            "Oeneus",
            "Ohrmazd",
            "Ojin",
            "Okesa",
            "Okokanoffee",
            "Okuninushi",
            "Olifat",
            "Oliphant",
            "Olympus",
            "Ophelia",
            "Orchomenus",
            "Ord",
            "Orestes",
            "Orpheus",
            "Oro",
            "Orthoprimax",
            "Orunila",
            "Osirus",
            "Otus",
            "Ovid",
            "Oz",
            "P'an-Ku",
            "Pachacutil",
            "Pan",
            "Pandora",
            "Parshya",
            "Parvati",
            "Pasiphae",
            "Patala",
            "Patrise",
            "Pelias",
            "Pellas",
            "Pellinore",
            "Peloponnesus",
            "Pek",
            "Percivale",
            "Persphone",
            "Phegethon",
            "Phineas",
            "Phobos",
            "Phoebe",
            "Phoenix",
            "Phorcys",
            "Phrixus",
            "Phyrro",
            "Piithar",
            "Pindar",
            "Pinel",
            "Pifall",
            "Plato",
            "Pluto",
            "Polybus",
            "Polydectes",
            "Polyneices",
            "Poseiden",
            "Priam",
            "Primax",
            "Prithivi",
            "Prometheus",
            "Proetus",
            "Pthura",
            "Ptolemy",
            "Puck",
            "Pymeria",
            "Pyrgos-Dirou",
            "Pyrrha",
            "Pythia",
            "Qa",
            "Qwaai",
            "QuagKeep",
            "Quetzalcoatl",
            "Quimbaya",
            "Quirnok",
            "R'lyeh",
            "Radha",
            "Ragnarok",
            "Rashnu",
            "Rata",
            "Rauta42",
            "Rauta5-0",
            "Rauta22",
            "Rauta69",
            "Rauta26",
            "Rauta99",
            "Reggae",
            "Regia",
            "Regulus",
            "Resida",
            "Rhadamanthys",
            "Rhea",
            "Rhpisort",
            "Rimahad",
            "Rol",
            "Rosalind",
            "Rousseau",
            "Rudra",
            "Ruhe",
            "Rukmini",
            "Runes",
            "Rustam",
            "Ryobu",
            "Sagurak",
            "Sakra",
            "Salacia",
            "Sappedon",
            "Sardonis",
            "Sarnak",
            "Satan",
            "Sati",
            "Sauron",
            "Scearce",
            "Schopenhaur",
            "Scylla",
            "Secundus",
            "Sedaria",
            "Sedna",
            "Sega",
            "Sekasis",
            "Semele",
            "Seneca",
            "Seriphos",
            "Sha-Lana",
            "Shaman",
            "Shantak",
            "Shapash",
            "Shax",
            "Shen",
            "Siddhartha",
            "Sif",
            "Sig",
            "Sigyn",
            "Sinope",
            "Sirens",
            "Skidbladnir",
            "Skirnir",
            "Skrymir",
            "Skuld",
            "Sleipner",
            "Socrates",
            "Solaria",
            "Sphinx",
            "Spinner",
            "Spinoza",
            "Srishok",
            "Styx",
            "Suboceana",
            "Suga",
            "Sugriva",
            "Surt",
            "Surya",
            "Swarga",
            "Symplegades",
            "Syrinx",
            "T'aishan",
            "Tacitus",
            "Tadanobu",
            "Tantrus",
            "Tare",
            "Tarnesus",
            "Tartarus",
            "Taweskare",
            "Tekeli-li",
            "Telesto",
            "Terminus",
            "Terra",
            "Tethys",
            "Thalassa",
            "Thebes",
            "Themis",
            "Thera",
            "Theseus",
            "Thinis",
            "Thjalfi",
            "Thjazi",
            "Thor",
            "Thought",
            "Thrace",
            "Thrasymachus",
            "Ti'i",
            "Tigra-nog'th",
            "Tiki",
            "Tillich",
            "Timaeus",
            "Time",
            "Tirawa-Atius",
            "Titan",
            "Tiu",
            "Tjinimin",
            "Tou-Mu",
            "Trantor",
            "Trashtri",
            "Trisala",
            "Triton",
            "Troll",
            "Troy",
            "Tsathoggua",
            "Tsentsa",
            "Typhon",
            "Tyr",
            "Tyrns",
            "Tyrrea",
            "Uhura",
            "Ukaipu",
            "Ulka",
            "Unicorn",
            "Unok",
            "Upuaut",
            "Uranus",
            "Urd",
            "Urdanis",
            "Urech",
            "Ursa",
            "Ursharabi",
            "Urth",
            "Utgard",
            "Uther",
            "Utnapishtim",
            "Uxmal",
            "Val",
            "Valhalla",
            "Valkyrie",
            "VanCleef",
            "Vanaheim",
            "Vanir",
            "Varnak",
            "Vasuki",
            "Ve",
            "Vedies",
            "Venus",
            "Verdandi",
            "Vesta",
            "Vigari",
            "Vili",
            "Virag",
            "Vivian",
            "Vishtaspa",
            "VohuManuh",
            "Voltaire",
            "Volva",
            "Vucu'Caquix",
            "Vulcan",
            "Vurat",
            "Walla",
            "Wampum",
            "Waterloo",
            "Waura",
            "Wawalag",
            "Woden",
            "Wu",
            "Xantor",
            "Xerxes",
            "Xrka",
            "Yaddith",
            "Yami",
            "Yamotodake",
            "Yang",
            "Yangtse",
            "Yarnek",
            "Ygerne",
            "Yggdrasil",
            "Yin",
            "Ymir",
            "Yog-sothoth",
            "Yu-Nu",
            "Yu-ti",
            "Yucatan",
            "YumCaax",
            "Zaaxon",
            "Zahak",
            "Zapana",
            "Zarathrusta",
            "Zetes",
            "Zethus",
            "Zocho",
            "Zoroaster",
            "Zrd",
            "Zurvan"
        )
    }
}