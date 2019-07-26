import com.jfrog.bintray.gradle.BintrayExtension
import com.jfrog.bintray.gradle.tasks.BintrayUploadTask
import org.apache.tools.ant.filters.ReplaceTokens
import java.util.Date

plugins {
    signing
    java
    application
    `maven-publish`

    id("com.jfrog.bintray") version "1.8.4"
    id("com.github.johnrengelman.shadow") version "4.0.2"
}

group = parent!!.group
version = parent!!.version

application {
    mainClassName = "com.typicalbot.nxt.Launcher"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    // TypicalBot SDK
    implementation(project(":sdk"))

    // JDA
    implementation("net.dv8tion:JDA:4.BETA.0_29")
    implementation("com.sedmelluq:jda-nas:1.1.0")
    implementation("com.sedmelluq:lavaplayer:1.3.18")

    // Logger
    implementation("org.slf4j:slf4j-api:1.7.26")
    implementation("org.slf4j:slf4j-log4j12:1.7.26")

    // Config
    implementation("com.github.Carleslc:Simple-YAML:1.3")
    implementation("org.json:json:20180813")

    // Database
    implementation("org.mongodb:mongo-java-driver:3.10.2")
    implementation("org.mongodb.morphia:morphia:1.3.2")

    // Utility
    implementation("com.mashape.unirest:unirest-java:1.4.9")
    implementation("io.sentry:sentry:1.7.24")

    // JUnit
    testImplementation("org.junit.jupiter:junit-jupiter:5.4.2")
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

val bintrayUpload : BintrayUploadTask by tasks
val javadoc : Javadoc by tasks
val jar : Jar by tasks
val build : Task by tasks
val clean : Task by tasks

val sourcesForRelease = task<Copy>("sourcesForRelease") {
    from("src/main/java") {
        include("**/Launcher.java")
        val tokens = mapOf(
            "version" to project.version
        )
        filter<ReplaceTokens>(mapOf("tokens" to tokens))
    }
    into("build/filteredSrc")

    includeEmptyDirs = false
}

val sourcesJar = task<Jar>("sourcesJar") {
    classifier = "sources"
    from("src/main/java") {
        exclude("**/Launcher.java")
    }
    from(sourcesForRelease.destinationDir)

    dependsOn(sourcesForRelease)
}

val javadocJar = task<Jar>("javadocJar") {
    dependsOn(javadoc)
    classifier = "javadoc"
    javadoc.destinationDir
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

            artifactId = "typicalbot"
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
        name = "typicalbot"
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
