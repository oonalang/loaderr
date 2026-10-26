plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "astral.loader"
    compileSdk = 35

    defaultConfig {
        applicationId = "astral.loader"
        minSdk = 28
        targetSdk = 35
        versionCode = 5
        versionName = "1.2"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    // Signing secrets belong in user-local Gradle properties or CI secrets.
    // An unsigned release build is available without release credentials.
    val releaseKeyPath = providers.gradleProperty("astral.signing.file").orNull
    val releaseKeyPassword = providers.gradleProperty("astral.signing.storePassword").orNull
    val releaseAlias = providers.gradleProperty("astral.signing.keyAlias").orNull
    val releasePassword = providers.gradleProperty("astral.signing.keyPassword").orNull
    val hasReleaseCredentials = listOf(releaseKeyPath, releaseKeyPassword, releaseAlias, releasePassword)
        .all { !it.isNullOrBlank() }
    if (hasReleaseCredentials) {
        signingConfigs {
            create("release") {
                storeFile = file(releaseKeyPath!!)
                storePassword = releaseKeyPassword!!
                keyAlias = releaseAlias!!
                keyPassword = releasePassword!!
            }
        }
    }

    buildTypes {
        getByName("release") {
            if (hasReleaseCredentials) signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            versionNameSuffix = "-release"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            versionNameSuffix = "-debug"
        }
    }

    buildFeatures {
        viewBinding = false
        buildConfig = true
    }

    // Robolectric renders the real layouts on the JVM so the notebook page can be
    // checked without a device (this project is built on-device, with no emulator).
    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("androidx.activity:activity-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("com.github.bumptech.glide:glide:4.12.0")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.robolectric:robolectric:4.14.1")
}
