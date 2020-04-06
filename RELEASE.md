:tada: GB: A Galactic Bloodshed (and others) inspired 4x game for mobile :tada:

Steps for release, because I always forget some. // TODO Automate this

### Release
- Build and test
- Update release notes below
- Commit to github and get commit # (from Github)
- In build.gradle update version_name to one more than commit # from Github
  Major.Minor.Build - Build is commit # MOD 1000 from Github of this change (current+1)
- Below update version name, too.
- Sync Gradle
- In app/build.gradle increase version_code to Major*10,000 + Minor*1000 + Build
  version 0.2.804 becomes build number 002804 or 2804
- Commit again
- Create signed AAB (goes to app->release, AS no longer finds the folder :-() ) (TODO default to C:\AndroidStudioProjects\gb\app\release)
- Upload that to Android Play
- Check release name in Android Play (it takes versionName by default, but check)
- Paste release notes into Play. 500 character limit.
- review and publish to internal beta. 
- Optional: Release previous or current to Beta
- Write release notes in e-mail. 
- Send e-mail

### Release Notes

0.2.804
- Post module/bundle restructure.

0.1.799
- First "Shelter in Place" release. Might as well code if I have to shelter.
- Increased hyper speed 2-3x to speed up the action.
- Minor visual refreshes and help text updates.

0.1.781
- Stronger AIs. Beetles have awesome defense now, but still die.
- Owning sectors (on planets) currently gives you 1 credit per turn. Use pods to colonize.
- Now all missions test for and celebrate mission success.
- Demo mode, and switch to turn on demo mode for Xenos for all missions.
- Massive upgrade in version number scheme.

.754/.756/.765
- New ship types: shuttles, battle stars, stations, headquarters, research centers.
- Races earn money each turn which limits their building abilities. Battle stars are pricey.
- Missions are "destroy all other headquarters" (except first mission). 
- Player Stats Screen. Beat your personal record for each mission.
- Two new races.
- Three new missions (still suffering from true diversity).
- New Destination Selection Activity replaces ugly Spinner.
- Stations are animated.
- Better AI in .765

.677/.678
- Rules change: 1 action per turn. Choose wisely.
- Two Players Pass and Play. With Fog of War!
- Settings. Turn on Hyper Sensors to see through Fog of War.

.652
- Minor: Tutorial to HTML and in-app. Fixed click target detection at high zoom. 

.614
- Minor: animated shots, larger and scaling ship bitmaps

.609
- Automatically load current game (if there is one) upon (re)start
- Minor: Distribute ships in orbit evenly over time, ships get limited shots per turn (for now 1), 
help button (linking to Github), shots in race color, basic animation of ships (rotating dot)

.578 
- Mostly internal stability and bugs. Testing changes to release process, too.

.566 First Beta
- Persistence! If the app gets killed by Android one can continue the game after restarting the app
- 3 predefined missions are now selectable from main menu (a benefit of having persistence)
- .566 became the first beta, thanks to persistence!

.551
- Awesome new buttons and detail windows. Colored rounded frames and all.
- System size doubled. Larger system makes better use of screen real estate. It's not like the game is "to scale" anyway. This creates some interesting overlapping binary stars.
- Double click on a planet pins the planet in a "geo-centric" view. Everything will now move around the planet.  Double click on a star, or zoom out to go back to regular heliocentric mode.
- News now updates again each turn on the main screen (and is more concise).
- No longer need to click "Fly To" after selecting destination. Selection in the drop down will set it.
Known Issues:
- Click target circles displayed for this release. Not sure if this is a good feature.
- Direction setting dropdown not working if window stays open during update. Work around: close and reopen detail window.

.477
-Online Tutorial https://github.com/perrochon/gb/blob/master/TUTORIAL.md
-The installed app will say .407, even if it's .477
-All ships visible even when fully zoomed out
-AI races are playing and can't be turned off
-Certain older devices are no longer supported (I am pretty sure that the app wouldn't have worked anyway). It now enforces minimum Nougat/Android 7.0 (2016). Let me know if this causes actual trouble.
 
.366
-Cruisers shoot at cruisers. This means all other races are now your enemy, and will shoot  at you, and your factories. Missions are broken, but Mission 2 is now informally "Destroy the Beetle Factory"

.361
-New Name: Andromeda Rising (which puts it very high up in Android's alphabetical list of apps :-)
-Play the game from the map. No more clumsy lists.
-Cruisers, that (automatically) shoot on Pods of other races. Just fly a cruiser to the Rainbow Beetle home system and wait and see. Or wait until they find you. Better have a cruiser in orbit around your planets.
-A lot of stability improvements, e.g. device rotation works better now. 
-Gameplay video: https://photos.app.goo.gl/TgM8vpACFLwv82FH6

.228
-Zoomable, pannable map of the stars (not yet restricted to the areas you explored)
-Pods can now fly to other systems, as well
-A new God mode butten called "Make". Press it once, then look at the Starmap. It's God mode, and if you don't understand what it does exactly, that would not be unexpected.

.193
-Missions! To be more accurate: Mission. The game now includes our most exciting mission ever: Tata! Mission 1. 
- Ships. They can go places.
-Lots of additional exploration: View planets of a star, view ships in a system, etc.
-Lots of bugs fixed. More bugs and bad code introduced, too.