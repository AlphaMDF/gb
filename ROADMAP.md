# Roadmap

## Guiding Principles
Right now, the focus is on enabling game play, i.e. add features that allow more game play. Features that don't enhance
game play, or prevent game play are second priority. Here are a few examples
* Fleshed out missions: Missions keep changing, so they are bare bones, and only described in the tutorial.
* Placeholder graphics: Things are very much in flux, so there are no final creative assets anywhere.

## Regression
* Ship buttons in Factory no longer disabled when no money (due to array list due to no FindViewByID)

## Short Term Small Items
* DO button crashes app when no game loaded. Grey it out.
* Disable Demo mode option for PvP maps.
* Have autoplayers use pods, or 
* Allow shuttles to colonize
* Improve action counts
* Better Track mission wins
* Race Screen: Stats, population, money, ships, etc.
* Deploy remaining ships (mostly surface)
* Look into skipped frames (on emulator) - make sure we minimize work on UI thread
* Moving Dot animation on squares follow the square, instead of a circle. Drive by a parameter of the ship (extension)
* same sytem patrol flies to inside of circle, instead of outside...
* Settings: Clean code smell
* Fog of War: per race, 3 states (unknown, seen, visible). Once you saw a system, you remember planets...
* Re-creating missions on each build and copy them into res directory. Or create missions in the app, not tests...

## Short Term Larger Items
* Population on planets (per race, and do correctly). Fix "Make Factory" etc.
* Settings! AI support (off, low (build factories), full (like Impi))
* Different speeds on auto, or until contact/battle
* Manual Shooting?
* Set Destination on Map, not from a list?
  # Look and Feel: 
* Better bitmaps, buttons, etc.
* Stars should get bigger at high zoom level, similar to planets

# Coding Standards
* Refactor to guidelines: https://github.com/ribot/android-guidelines/blob/master/project_and_code_guidelines.md
XML resources (e.g. text_version) are in snake case, like most Android code. This looks weird in Kotlin when
accessed as variables (e.g. text_version.text = "1.1.1"), but it also makes it clear this is not a regular variable,
but an XML layout element.

## Code Quality
* Analyze -> Inspect Code
* Analyze -> Code Cleanup
* Turn colors to proper resource strings. Then parse them once.
* Tests for patrol points
* Review all TODO and warnings
* Unit Test Missions (or at least the lib part...)
* Review all unit tests, and add whats missing (GBLocation? GBxy? )
* Refactor to use resources for strings for Missions
* Strings to resources. What to do about library strings?
* Clean up logging. Get rid of GBDebug?
* Download image as on-demand media
* Can we get rid of FindViewById in global stuff. Why do we have to go find the map?

### Not Gating
* Different layouts for landscape/tablet
* Big: Problem with GBLib relying on u.() when it should use vm.x() for view model. Implement extension pattern every where
* Refactor firing solution and move logic for firing and taking damage into each ship's class (depends on ship refactor)
* Refactor and apply naming conventions for UI elements (btn_do etc.) and other things
* Access and visibility in gblib. What returns variable GBObjects needs to be internal. App needs their own.
* Use Application for global state in Android: https://developer.android.com/reference/android/app/Application
* Debug buffer and Console Buffer. Message buffer per race. These may all be related.
* On Animations: "Nowadays, Android documentation clearly recommends not to use resources directly from android.R.*, since every 
release of the platform has changes on them. Even some resources dissapear from one version to another, 
so you shouldn't rely on them. On the other hand, lots of resources are private and not available from a developer's code."
* Revise all strings and fix text related issues. Not clear we care about localization, as it's just too expensive

## Test Efficiency
* Fast and Slow Tests
* On Device tests

## Design
* Make "DO" a FloatingActionButton? It's the "primary activity" in the game...

## Features

### Future missions
* Build infrastructure: planetary effects
* Play different races: Mission n could be getting another race up and running.
* Research

#### Frontend
* Mark "active" object (the one with the detail screen open). Maybe a colored click circle?
* Run Tests on phone (text based?)

### General
* Get a picture of the Andromeda Galaxy https://www.spacetelescope.org/images/heic1502a/ Problem is the 40k picture is not high enough, and the fullsize is 69536 x 22230 px (4.3GB, psb file). That could give me a 22,000 square picture, or 484k pixel (right now I have 18k (325k pixel). This is a 50% increase in pixels - and image size from 40MB to say 60M (compressed). Original resolution of the Hubble image (a third of the total) would be ~2GB. Not sure how to compress that.

### Star Map

#### Planet
* Population: multiple races per planet properly implemented (so they fight!)
* Environment and impact on migration and population growth

#### Systems
* Stats for systems

#### Universe
* Statistics on population, etc.

####Races
* Give races home planets in GBData
* Show ships of that race from race screen (don't have race screen right now, maybe access from planet or ship?)
* Make more distinct races
* Visibility and actions per race (and god mode cleanup)

#### Ships
* load/unload population into ships
* More planetary units
* Locations of planetary units
* more space units: Battleship, defense stations
* ships on system patrol and/or orbit

#### Interactivity
* land battle (can do this for morphs with pods only)
* ship battle. Animated, serialized?

## Android 
* Download background image separately (so it doesn't have to be downloaded each time)

