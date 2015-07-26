On-Off Tracker
================================
The On-Off Tracker is an application to measure how often the screen has been switched on and off. In addition, it also tracks how often the device is unlocked.

Features:
- Today and overall numbers for on-off switching of the screen and unlocking.
- Background service that runs automatically after the device is booted.

Please note: After installation the application must be started once to activate the background service without a device restart.

Build
-------------
The projects can be build with the integrated Gradle wrapper.

Signing
-------------
To sign your Android app with gradle use the property [OnOff-Tracker.signing](https://github.com/dbaelz/OnOff-Tracker/blob/master/app/build.gradle#L32) and external config files for the keystore information. [Tim Roes](https://github.com/timroes) wrote a very informative blog post about [handling signing configs](https://www.timroes.de/2013/09/22/handling-signing-configs-with-gradle/).

Note that property _OnOff-Tracker.signing_ has to point to the __folder__ of your keystore and not to the filename of the keystore file!

```groovy
android {
  signingConfigs {
    release {
      storeFile file(project.property("OnOff-Tracker.signing") + "/release.keystore")
      storePassword "KEYSTORE_PASSWORD"
      keyAlias "KEY_ALIAS_RELEASE"
      keyPassword "KEY_PASSWORD"
    }

    debug {
      storeFile file(project.property("OnOff-Tracker.signing") + "/debug.keystore")
      storePassword "android"
      keyAlias "androiddebugkey"
      keyPassword "android"
    }
  }
 
  buildTypes {
    release {
      signingConfig signingConfigs.release
    }

	debug {
      signingConfig signingConfigs.debug
    }
  }
}
```

License
-------------
OnOff-Tracker is licensed under the [Apache 2 License](https://github.com/dbaelz/OnOff-Tracker/blob/master/LICENSE).
