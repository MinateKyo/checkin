import org.jlleitschuh.gradle.ktlint.ReporterType

plugins {
  id("com.android.application")
  id("kotlin-android")
  id("kotlin-kapt")
  id("org.jlleitschuh.gradle.ktlint") version Versions.KTLINT_GRADLE
  id("com.google.gms.google-services") apply false
}

val env = "env"
android {
  compileSdkVersion(Versions.COMPILE_SDK)
  dataBinding.isEnabled = true
  defaultConfig {
    applicationId = Versions.APPLICATION_ID
    minSdkVersion(Versions.MIN_SDK)
    targetSdkVersion(Versions.TARGET_SDK)
    setProperty("archivesBaseName", "${project.name}-${versionName}")
    versionCode = 1
    versionName = "1.0"
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables.useSupportLibrary = true
  }

  dexOptions {
    preDexLibraries = "true" != System.getenv("CI")
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
      isShrinkResources = false
      proguardFile(getDefaultProguardFile("proguard-android.txt"))
      proguardFile(file("proguard-rules.pro"))
    }
  }

  lintOptions {
    lintConfig = file("lint.xml")
    disable("AppCompatResource")
    textReport = true
    textOutput("stdout")
  }

  flavorDimensions(env)

  productFlavors {
    create("development") {
      dimension = env
      applicationId = "${Versions.APPLICATION_ID}.dev"
      testApplicationId = "${Versions.APPLICATION_ID}dev.test"
      // Specify devel base URL. Always end it with the trailing slash because the HTTP client
      // expects it.
      val baseUrl = "http://scan-mock.com/"
      buildConfigField("String", "BASE_URL", "\"${baseUrl}\"")
    }

    create("production") {
      dimension = env
    }
  }
}

dependencies {
  api(project(":data"))
  // Support library
  implementation(Dependencies.Support.design)
  implementation(Dependencies.Support.recyclerView)
  implementation(Dependencies.Support.customtabs)
  implementation(Dependencies.Support.cardview)
  implementation(Dependencies.Support.constraintLayout)
  implementation(Dependencies.ktx)
  // Firebase
  implementation(Dependencies.Firebase.uiAuth)
  implementation(Dependencies.Firebase.crash)
  // Google maps
  implementation(Dependencies.Map.google)
  implementation(Dependencies.Map.utils)
  implementation(Dependencies.Glide.core)
  implementation(Dependencies.Glide.okhttp3)
  // Annotation processors
  kapt(Dependencies.Databinding.compiler)
  kapt(Dependencies.Dagger.compiler)
  kapt(Dependencies.Dagger.processor)
  kapt(Dependencies.Lifecycle.compiler)
  kapt(Dependencies.Glide.compiler)
  debugImplementation(Dependencies.Leakcanary.op)
  releaseImplementation(Dependencies.Leakcanary.noOp)
  // Tests
  testImplementation(Dependencies.junit)
}

kapt {
  useBuildCache = true
}

ktlint {
  version = Versions.KTLINT
  android = true
  reporter = ReporterType.CHECKSTYLE
  ignoreFailures = false
}

// MUST BE AT THE BOTTOM
apply(mapOf("plugin" to "com.google.gms.google-services"))