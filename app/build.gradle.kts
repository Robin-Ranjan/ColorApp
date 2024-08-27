plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.task.janitritask"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.task.janitritask"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    implementation(libs.androidx.lifecycle.runtime.compose.android)
    implementation(libs.firebase.firestore)
//    implementation(libs.androidx.room.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    // Jetpack Compose
    implementation ("androidx.compose.runtime:runtime-livedata:1.4.3")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    // For ViewModel integration with Compose
//    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")

    // Lifecycle components for LiveData and ViewModel
//    implementation( "androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")
//    implementation( "androidx.lifecycle:lifecycle-livedata-ktx:2.8.4")

//    // For state management with LiveData
//    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.8.4")

//    // Other dependencies
//    implementation ("androidx.core:core-ktx:1.13.1")
//    implementation ("androidx.compose.runtime:runtime-livedata:1.6.8")


    // Room components
    implementation("androidx.room:room-runtime:2.6.0")
    kapt ("androidx.room:room-compiler:2.6.1")
    implementation ("androidx.room:room-ktx:2.6.1")

//    // Kotlin coroutines
//    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
}