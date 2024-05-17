[![JetBrains incubator project](http://jb.gg/badges/official.svg)](https://confluence.jetbrains.com/display/ALL/JetBrains+on+GitHub) 

# KotlinConf App

This is the official KotlinConf App! We hope you enjoy(ed) the conference and sessions. This repository contains the source code of the application. 

All pieces of the application are implemented in *Kotlin*. Backend, frontend and mobile apps are Kotlin applications.
Yes, Kotlin is powering all parts of the story. Did I already say that? Okay, let's get to the details:

### Server

KotlinConf App is connecting to the server running in the cloud to get information about sessions,
speakers, favorites and votes. It is developed using [Ktor](https://ktor.io), an asynchronous Kotlin web framework.

The server polls [Sessionize](https://sessionize.com) service, which is used for planning the conference. 
Once in a while, it connects to APIs to get the latest information about sessions, speakers, and timeline. 
It then augments and republishes this information for clients to consume. 
It also provides a couple of extra APIs to save your favorites and accumulate votes.

### iOS and Android Applications

Both Android and iOS applications are developed using Kotlin Multiplatform Mobile technology within a single codebase.
The UI is implemented using Compose Multiplatform library, sharing the same code between platforms.

## How to build and run

### Pre-requisites

 * Make sure you have the Android SDK installed
 * Create a file `local.properties` in the root directory of the project, pointing to your Android SDK installation. On Mac OS, the contents should be `sdk.dir=/Users/<your username>/Library/Android/sdk`. On other OSes, please adjust accordingly.

### Running the Android app

* Open project in Android Studio or JetBrains Fleet: https://www.jetbrains.com/fleet/
* Select `KotlinConf` run configuration and hit run

### Running the iOS

To run iOS application, you can open `iosApp/KotlinConf.xcworkspace`, select a device XCode and hit run.

## Running the desktop app

* Run `./gradlew :shared:run` to start the desktop application

### Running the backend

* Run `./gradlew :backend:run` to start the server
