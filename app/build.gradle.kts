plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.hotelbrowserandroid"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.hotelbrowserandroid"
        minSdk = 27
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"

        buildFeatures {
            viewBinding = true
            dataBinding = true
        }
    }


    dependencies {
        implementation(libs.androidx.core.ktx.v1101)
        implementation(libs.androidx.appcompat.v161)
        implementation(libs.material.v1100)
        implementation(libs.androidx.constraintlayout.v214)
        implementation(libs.androidx.navigation.fragment.ktx)
        implementation(libs.androidx.navigation.ui.ktx)
        implementation(libs.androidx.fragment.ktx)
        implementation(libs.retrofit)
        implementation(libs.converter.gson)
        implementation(libs.androidx.room.runtime)
        kapt(libs.androidx.room.compiler)
        implementation(libs.androidx.room.ktx)
        implementation(libs.hilt.android)
        kapt(libs.hilt.compiler)
        implementation(libs.material.v160)
        implementation(libs.androidx.recyclerview)
        implementation(libs.androidx.lifecycle.viewmodel.compose)
        implementation (libs.androidx.constraintlayout)
        implementation(kotlin("script-runtime"))
    }

    kapt {
        correctErrorTypes = true
    }}