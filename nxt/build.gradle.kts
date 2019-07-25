plugins {
    java
    application

    id("com.github.johnrengelman.shadow") version "4.0.2"
}

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
    implementation("net.dv8tion:JDA:4.BETA.0_26")
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
