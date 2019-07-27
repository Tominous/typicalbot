import com.jfrog.bintray.gradle.BintrayExtension
import com.jfrog.bintray.gradle.tasks.BintrayUploadTask

import java.util.Date

plugins {
    signing
    `java-library`
    `maven-publish`

    id("com.jfrog.bintray") version "1.8.4"
    id("com.github.johnrengelman.shadow") version "4.0.2"
}

group = parent!!.group
version = parent!!.version

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    // Code safety
    api("com.google.code.findbugs:jsr305:3.0.2")
    api("org.jetbrains:annotations:17.0.0")

    // Logger
    api("org.slf4j:slf4j-api:1.7.26")

    // JDA
    api("net.dv8tion:JDA:4.BETA.0_30")
    api("com.sedmelluq:lavaplayer:1.3.18")

    // Utility
    implementation("org.json:json:20180813")
    implementation("com.google.guava:guava:28.0-jre")
    implementation("org.javassist:javassist:3.25.0-GA")

    // JUnit
    testImplementation("org.junit.jupiter:junit-jupiter:5.4.2")
}

val bintrayUpload : BintrayUploadTask by tasks
val compileJava : JavaCompile by tasks
val javadoc : Javadoc by tasks
val jar : Jar by tasks
val build : Task by tasks
val clean : Task by tasks

val sourcesJar = task<Jar>("sourcesJar") {
    classifier = "sources"
    from("src/main/java")
}

val javadocJar = task<Jar>("javadocJar") {
    dependsOn(javadoc)
    classifier = "javadoc"
    javadoc.destinationDir
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.isIncremental = true
    options.compilerArgs = mutableListOf("-Xlint:deprecation", "-Xlint:unchecked")
}

tasks.withType<Javadoc> {
    isFailOnError = false

    options.memberLevel = JavadocMemberLevel.PUBLIC
    options.encoding = "UTF-8"

    if (options is StandardJavadocDocletOptions) {
        val opt = options as StandardJavadocDocletOptions

        opt.author()
    }
}

build.apply {
    dependsOn(jar)
    dependsOn(javadocJar)
    dependsOn(sourcesJar)

    jar.mustRunAfter(clean)
    javadocJar.mustRunAfter(jar)
    sourcesJar.mustRunAfter(javadocJar)
}

bintrayUpload.apply {
    dependsOn(clean)
    dependsOn(build)

    build.mustRunAfter(clean)

    onlyIf { getProjectProperty("bintrayUser").isNotEmpty() }
    onlyIf { getProjectProperty("bintrayKey").isNotEmpty() }
}

publishing {
    publications {
        register("BintrayRelease", MavenPublication::class) {
            from(components["java"])

            artifactId = "typicalbotsdk"
            groupId = project.group as String
            version = project.version as String

            artifact(javadocJar)
            artifact(sourcesJar)
        }
    }
}

bintray {
    user = getProjectProperty("bintrayUser")
    key = getProjectProperty("bintrayKey")
    setPublications("BintrayRelease")
    pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
        repo = "maven"
        name = "typicalbotsdk"
        userOrg = "typicalbot"
        setLicenses("Apache-2.0")
        vcsUrl = "https://github.com/typicalbot/typicalbot.git"
        publish = true
        version(delegateClosureOf<BintrayExtension.VersionConfig> {
            name = project.version as String
            released = Date().toString()
        })
    })
}

fun getProjectProperty(name: String): String {
    var property = ""
    if (hasProperty(name)) {
        property = this.properties[name] as? String ?: ""
    }
    return property
}
