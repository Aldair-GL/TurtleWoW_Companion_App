// Top-level build file where you can add configuration options common to all sub-projects/modules.
val skipAndroid = gradle.startParameter.projectProperties["skipAndroid"] == "true"
val agpVersion = "8.1.4"

if (!skipAndroid) {
    buildscript {
        repositories {
            google()
            mavenCentral()
        }
        dependencies {
            classpath("com.android.tools.build:gradle:$agpVersion")
            classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.21")
        }
    }
}
