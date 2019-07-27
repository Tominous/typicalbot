plugins {
    `java-library`

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
    api("net.dv8tion:JDA:4.BETA.0_29")
    api("com.sedmelluq:lavaplayer:1.3.18")

    // Utility
    implementation("org.json:json:20180813")
    implementation("com.google.guava:guava:28.0-jre")
    implementation("org.javassist:javassist:3.25.0-GA")

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
