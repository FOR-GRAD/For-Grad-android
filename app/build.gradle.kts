plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("kotlin-kapt")
}

android {
	namespace = "umc.com.mobile.project"
	compileSdk = 34

	defaultConfig {
		applicationId = "umc.com.mobile.project"
		minSdk = 26
		targetSdk = 33
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

//		Properties properties = new Properties()
//		properties.load(project.rootProject.file("local.properties").newDataInputStream())
//		buildConfigField "String", "BASE_URL", "\"${properties.getProperty("BASE_URL")}\""
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
		viewBinding = true
		dataBinding = true
	}
}

dependencies {

	implementation("androidx.core:core-ktx:1.9.0")
	implementation("androidx.appcompat:appcompat:1.6.1")
	implementation("com.google.android.material:material:1.11.0")
	implementation("androidx.constraintlayout:constraintlayout:2.1.4")
	implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
	implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
	implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
	implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
	implementation("androidx.legacy:legacy-support-v4:1.0.0")
	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
	implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

	//border radius
	implementation("com.google.android.material:material:1.0.0")
	//bottom Sheet Dialog
	implementation("com.google.android.material:material:1.6.1")

	//gridLayout
	implementation("androidx.gridlayout:gridlayout:1.0.0")

	// Retrofit 라이브러리
	implementation ("com.squareup.retrofit2:retrofit:2.9.0")

	// Gson 변환기 라이브러리
	implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

	// Scalars 변환기 라이브러리
	implementation ("com.squareup.retrofit2:converter-scalars:2.6.4")

	// cookie
	implementation ("com.squareup.okhttp3:okhttp-urlconnection:4.9.1")

	//Paging 라이브러리
	implementation ("androidx.paging:paging-runtime-ktx:3.1.0")
}