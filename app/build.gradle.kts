import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.example.marvelapp30"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.marvelapp30"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        buildConfigField("String", "PRIVATE_KEY", "\"${properties["PRIVATE_KEY"]}\"")
        buildConfigField("String", "PUBLIC_KEY", "\"${properties["PUBLIC_KEY"]}\"")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", ApiUrl.BASE_URL)
        }

        getByName("debug") {
            buildConfigField("String", "BASE_URL", ApiUrl.BASE_URL)
        }
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = Versions.jvmTarget
    }
}

dependencies {

    implementation("androidx.core:core-ktx:${Versions.core}")
    implementation("androidx.appcompat:appcompat:${Versions.appCompat}")
    implementation("com.google.android.material:material:${Versions.material}")
    implementation("androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}")
    testImplementation("junit:junit:${Versions.jUnit}")
    androidTestImplementation("androidx.test.ext:junit:${Versions.ext}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.espresso}")

    // Splashscreen API
    implementation("androidx.core:core-splashscreen:${Versions.splashScreen}")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:${Versions.navigation}")
    implementation("androidx.navigation:navigation-ui-ktx:${Versions.navigation}")

    // Glide
    implementation("com.github.bumptech.glide:glide:${Versions.glide}")
    annotationProcessor("com.github.bumptech.glide:compiler:${Versions.glide}")

    // Koin
    implementation("io.insert-koin:koin-android:${Versions.koin}")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:${Versions.retrofit}")
    implementation("com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}")

    // Gson
    implementation("com.squareup.retrofit2:converter-gson:${Versions.gson}")

    // Paging
    implementation("androidx.paging:paging-runtime:${Versions.paging}")

    //Room Database
    implementation("androidx.room:room-ktx:${Versions.room}")
    annotationProcessor("androidx.room:room-compiler:${Versions.room}")
}