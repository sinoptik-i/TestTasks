import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.exclude
import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.include

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.kotlin.kapt)
    id("androidx.room")
}

android {

    namespace = "com.sinoptik_.room"

    compileSdk = libs.versions.compileSdk.get().toInt()
//    compileSdk = 36
    /*compileSdk {
        version = release(36)
    }*/

    defaultConfig {
        minSdk = 30
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = freeCompilerArgs + "-Xjvm-default=all-compatibility"
    }

}

room {
    schemaDirectory("$projectDir/schemas")
}


dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)


    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    androidTestImplementation(libs.androidx.room.testing) // Проверяйте актуальную версию
    androidTestImplementation(libs.kotlinx.coroutines.test)
}