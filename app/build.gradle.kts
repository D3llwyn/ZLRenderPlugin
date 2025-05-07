plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    val nameId = "com.movtery.zalithplugin.renderer"

    namespace = nameId
    compileSdk = 34

    signingConfigs {
        create("customDebug") {
            keyAlias = "key0"
            keyPassword = "ZALITH_RENDERER_PLUGIN"
            storeFile = file("plugin-key.jks")
            storePassword = "ZALITH_RENDERER_PLUGIN"
        }
    }

    defaultConfig {
        applicationId = nameId
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        debug {
            isDebuggable = true
            signingConfig = signingConfigs.getByName("customDebug")
              }
        }
        configureEach {
            resValue("string","app_name","Test Renderer")
            applicationIdSuffix = ".testrender"

            manifestPlaceholders["des"] = "test render"
            //The specific definition format of a renderer is ${name}:${renderer library name}:${EGL library name}, for example: Some Renderer:libSome_renderer.so:libSome_renderer.so
            manifestPlaceholders["renderer"] = "${name}"

            //Special Env
            //DLOPEN=libxxx.so used to load external library
            //If there are multiple libraries, you can use "," to separate them, for example  DLOPEN=libxxx.so,libyyy.so
            manifestPlaceholders["pojavEnv"] = mutableMapOf<String,String>().apply {
                //put("POJAV_RENDERER", "renderer_id")
                //put("ENV_KEY", "env_value")
            }.run {
                var env = ""
                forEach { (key, value) ->
                    env += "$key=$value:"
                }
                env.dropLast(1)
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

implementation(project(":ltw"))
}
