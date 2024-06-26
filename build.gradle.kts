buildscript {
    dependencies {
        classpath ("com.google.gms:google-services:4.4.1")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.51.1")
    }
    repositories {
        google()
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
    id("com.google.firebase.crashlytics") version "3.0.1" apply false
    id("com.google.firebase.firebase-perf") version "1.4.2" apply false


}