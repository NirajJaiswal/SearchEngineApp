plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
    alias(libs.plugins.dagger)
}

android {
    namespace = "com.example.niraj.searchengineapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.niraj.searchengineapp"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Retrofit for API calls
    implementation(libs.retrofit)
    implementation(libs.converter.gson) // Gson converter
    implementation(libs.logging.interceptor) //Logging
    implementation(libs.adapter.rxjava3) // RxJava3 Adapter

    // RxJava3
    implementation(libs.rxandroid)
    implementation(libs.rxjava)

    // Lifecycle Components
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Hilt for dependency injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.compose.navigation)

    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline) // For mocking final classes/methods
    testImplementation(libs.androidx.core.testing) //For Testing LiveData

    implementation(libs.accompanist.webview)

    implementation(libs.androidx.compose.material3)

    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline) // For mocking final classes/methods
    testImplementation(libs.androidx.core.testing) //For Testing LiveData
    testImplementation(libs.kotlinx.coroutines.test)

}