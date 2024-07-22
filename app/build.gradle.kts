plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("org.jetbrains.kotlin.kapt")
    id("com.google.dagger.hilt.android")
    alias(libs.plugins.ksp) apply false
}

android {
    namespace = "com.news.newsapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.news.newsapplication"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
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
    }

    buildFeatures {
        dataBinding = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //dagger
//    implementation (libs.dagger)
//    annotationProcessor (libs.dagger.compiler)

    implementation (libs.androidx.room.runtime)
    annotationProcessor (libs.androidx.room.compiler)
    // Guava support for Room, including Optional and ListenableFuture
    implementation (libs.androidx.room.guava)
    kapt(libs.androidx.room.compiler)
    // optional - Test helpers
    testImplementation (libs.androidx.room.testing)

    // optional - Paging 3 Integration
    implementation (libs.androidx.room.paging)

    // retrofit
    implementation (libs.retrofit2.retrofit)

    // GSON
    implementation (libs.converter.gson)

    // coroutine
    implementation (libs.kotlinx.coroutines.android)
    implementation (libs.kotlinx.coroutines.core)

    implementation (libs.androidx.lifecycle.viewmodel.ktx)
//    implementation (libs.androidx.lifecycle.runtime.ktx)

    implementation (libs.hilt.android)
    kapt (libs.androidx.hilt.compiler)
    implementation (libs.glide)
    annotationProcessor (libs.compiler)
    implementation(libs.androidx.room.ktx)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
//    implementation (libs.androidx.lifecycle.viewmodel.ktx)
}