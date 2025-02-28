plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // OkHttp
    implementation ("com.squareup.okhttp3:okhttp:4.12.0")

    // Gson
    implementation ("com.google.code.gson:gson:2.10.1")
    implementation ("mysql:mysql-connector-java:8.0.29")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}