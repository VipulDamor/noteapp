plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlin.ksp)


}

android {
    namespace = "com.developers1993.cleanarchitecturenotesapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.developers1993.cleanarchitecturenotesapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 2
        versionName = "1.1"

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
        kotlinCompilerExtensionVersion = "2.0.0"
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

    //Compose
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.navigation)
    implementation(libs.androidx.icons)
    implementation(libs.androidx.hilt.navigation)

    //Coroutines
    implementation(libs.kotlinx.coroutine.core)
    implementation(libs.kotlinx.coroutine.android)

    //Dagger-hilt
    implementation(libs.com.google.dagger)
    ksp(libs.com.google.dagger.compiler)
    ksp(libs.androidx.hilt.hilt.compiler)

    //Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.room)
    ksp(libs.androidx.room.compiler)

    //Debug and testing libs
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}