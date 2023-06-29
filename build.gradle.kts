// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.build}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navVersion}")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}