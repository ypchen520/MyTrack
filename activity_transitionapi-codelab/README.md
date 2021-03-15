# Detecting User Activity changes using Activity Recognition Transition API

* The codelab can be found [here](https://developer.android.com/codelabs/activity-recognition-transition?index=..%2F..index#0).

## Pre-requisites

* Android API Level > v14
* Android Build Tools > v21
* Android Studio 3.5 or later to run the code
* A device/emulator running on Oreo or later (this codelab targets Android 10)

## Goals

* The goal of this app is to test the Google's Transition API in [our testing scenarios](./doc/testing-scenarios/)

## Testing flow

* Select the testing scenario
* Enter the transition type (as the filename)
* Tap on CREAT LOG FILE
* Start performing the activity
* When a activity transition is detected, the user will receive a notification with the title **Transition detected** and a lightning icon
* Tap on the notification (**This has to be done before saving to the file**)
* Tap on SAVE LOG TO FILE

## Logger

* There are two buttons in the home page of the app
  * CREAT LOG FILE
    * This button can be used to specify the name of the current log file, which should be the transition type
      * The app automatically attach the current time to the specified filename in the format of **MM-dd-HH-mm-ss**
    * Upon creating the file, the current log will be cleared to start a new log for the upcoming transitions
  * SAVE LOG TO FILE
    * This button can be used to save the current log
    * The log file should be created when this button is tapped on, otherwise, the user will receive a message saying: "Filename not specified!"
    * After being saved to the file, the current log will be cleared
* The logged files can be found in the following way in Android Studio
  * View -> Tool Windows -> Device File Explorer
    * data/data/com.google.example.android.basicactivityrecognitiontransitionsample/files/ (this directory may vary depending on the device)
* Sample logs can be found [here](./doc/sample-logs/)
* Note
  * Currently the logger does not work in the case where the app is closed (changing to the Home screen or turning off the phone screen is fine)

## Activity Transition

* There are two types of data that are returned by the API
  * Transition types
    * ENTER
    * EXIT
  * Activity types
    * WALKING
    * STILL
    * IN_VEHICLE
    * ON_BICYCLE
    * RUNNING
* Important note
  * currently, only the transition between activity types will trigger the notification, for example
    * STILL (EXIT) -> WALKING(ENTER) will trigger the notification
    * WALKING(ENTER) -> WALKING(EXIT) will **not** trigger the notification
