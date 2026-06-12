plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    id("com.google.protobuf") version "0.9.5"
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.spiderapp1"

    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.spiderapp1"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }


    buildFeatures {
        compose = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    implementation(
        "androidx.navigation:navigation-compose:2.7.7"
    )

    // Preferences DataStore
    implementation(
        "androidx.datastore:datastore-preferences:1.2.1"
    )

    // Proto DataStore
    implementation(
        "androidx.datastore:datastore:1.2.1"
    )

    implementation(
        "com.google.protobuf:protobuf-kotlin-lite:4.32.1"
    )

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    androidTestImplementation(
        platform(libs.androidx.compose.bom)
    )

    androidTestImplementation(
        libs.androidx.compose.ui.test.junit4
    )

    debugImplementation(
        libs.androidx.compose.ui.tooling
    )

    debugImplementation(
        libs.androidx.compose.ui.test.manifest
    )
}

protobuf {

    protoc {
        artifact =
            "com.google.protobuf:protoc:4.32.1"
    }

    generateProtoTasks {
        all().forEach { task ->

            task.builtins {

                create("java") {
                    option("lite")
                }
            }
        }
    }
}