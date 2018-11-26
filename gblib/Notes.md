# Other Notes

//
// Content Sources used
// Notes.txt: http://www.jongware.com/galaxy1.html
// FAQ: http://web.archive.org/web/20060501033212/http://monkeybutts.net:80/games/gb/

// Must include following file: https://github.com/kaladron/galactic-bloodshed/blob/master/LICENSE
// List of Planet Names: https://github.com/kaladron/galactic-bloodshed/blob/master/data/planet.list
// List of Stars: https://github.com/kaladron/galactic-bloodshed/blob/master/data/star.list

// Terrain Assets
https://assetstore.unity.com/packages/2d/environments/painted-2d-terrain-tiles-basic-set-45675


Log
***

2018-11-16
Released .76 Bunch of bug fixes and small improvements. Added basic Race View Activity with a picture of an Impi

2018-11-15
Fully Migrating Java to Kotlin. Cleaning up lots of stuff while at it, like moving static data into GBData. Migration
broke badly, and it was not clean with race allocation to sector, so commented out right now.

2018-11-14
Single planet view and colonize (through teleportation! saves implementing ships). Now have minimal "colonize the
universe functionality". Code is in bad shape, so starting to clean up. Don't want to add more functionality this way.
Need to refactor, and planning to switch everything to Kotlin. GDrive installs on all my phones broke, but work on other
phones. Not clear why.

2018-11-13
Android Studio couldn't write settings anymore. Couldn't restart. Windows reboot failed with Bitlock error. Recovery
took only the morning, mostly because it came back and I don't really know why.
Also fixed z coordinates in main, and text colors. More readable and vivid now, but not ideal
Third activity StarsView which required updating the gblib with star coordinates and an algo to distribute stars

2018-11-12
Creating second activity to deal with planets so we can have a ScrollView of planets

2018-11-11
Working on rendering one hard coded planet

2018-11-10
More refactoring. Two races. Buttons!

2018-11-09
Scrollable TextView in App displays output of text. Refactored classes out of a single file into multiple.

2018-11-08
Started this Log. Finally Enabled Hyper-V. It's under "Security" in the BIOS! Emulator is now running faster.




TileMap Notes

Now manually inflating the view, to better understand what happens, still getting

     Caused by: android.view.InflateException: Binary XML file line #2: Binary XML file line #2: Error inflating class com.qozix.tileview.TileView
     Caused by: android.view.InflateException: Binary XML file line #2: Error inflating class com.qozix.tileview.TileView
     Caused by: java.lang.reflect.InvocationTargetException
        at java.lang.reflect.Constructor.newInstance0(Native Method)
        at java.lang.reflect.Constructor.newInstance(Constructor.java:343)
        at android.view.LayoutInflater.createView(LayoutInflater.java:647)
        at android.view.LayoutInflater.createViewFromTag(LayoutInflater.java:790)
        at android.view.LayoutInflater.createViewFromTag(LayoutInflater.java:730)
        at android.view.LayoutInflater.inflate(LayoutInflater.java:492)
        at android.view.LayoutInflater.inflate(LayoutInflater.java:423)
        at com.zwsi.gb.feature.MapActivity.onCreate(MapActivity.kt:66)

   override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_constraint)

        val layoutInflater:LayoutInflater = LayoutInflater.from(applicationContext)
        val layout = findViewById<ConstraintLayout>(R.id.root_layout)

        // Inflate the layout using LayoutInflater
/*66*/      val view: View = layoutInflater.inflate(
            R.layout.activity_map, // Custom view/ layout
            layout, // Root layout to attach the view
            false // Attach with root layout or not
        )

Commenting out implementation 'com.qozix:tileview:3.0.1' leads to compiler errors (Unresolved reference)
Commenting out implementation 'com.jakewharton:disklrucache:2.0.2' in feature compiles, then we get same error. I am
pretty sure that tileview has it as a dependency and gradle takes care of it.
Changing implementation 'com.qozix:tileview:3.0.1' to implementation 'com.qozix:tileview:3.0.2' creates an error, so
there is something right happening wiht that include

Potential Leads:
Mismatching contexts? https://stackoverflow.com/a/2594271/10682598

