import org.jetbrains.gradle.ext.CopyrightConfiguration
import org.jetbrains.gradle.ext.ProjectSettings

import java.time.LocalDate

plugins {
    java
    application

    id("com.github.johnrengelman.shadow") version "4.0.2"
    id("org.jetbrains.gradle.plugin.idea-ext") version "0.5"
}

val versionObject = Version(major = "3", minor = "0", patch = "0")

project.group = "com.typicalbot"
project.version = "$versionObject"

application {
    mainClassName = "com.typicalbot.TypicalBot"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    jcenter()
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    // JDA
    implementation("net.dv8tion:JDA:3.8.3_461")
    implementation("com.sedmelluq:jda-nas:1.0.6")
    implementation("com.sedmelluq:lavaplayer:1.3.11")
    implementation("com.github.natanbc:lavadsp:0.5")

    // Logging
    implementation("org.slf4j:slf4j-api:1.7.25")
    implementation("org.slf4j:slf4j-log4j12:1.7.25")

    // Config/Database
    implementation("com.github.Carleslc:Simple-YAML:1.3")
    implementation("org.json:json:20180813")
    implementation("org.mongodb:mongo-java-driver:3.10.1")
    implementation("org.mongodb.morphia:morphia:1.3.2")

    // Sentry
    implementation("io.sentry:sentry:1.7.17")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.isIncremental = true
}

idea {
    project {
        this as ExtensionAware
        configure<ProjectSettings> {
            this as ExtensionAware
            configure<CopyrightConfiguration> {
                profiles {
                    create("TypicalBot License") {
                        keyword = "Copyright"
                        notice = """
                            Copyright ${LocalDate.now().year} Bryan Pikaard, Nicholas Sylke and the TypicalBot contributors

                            Licensed under the Apache License, Version 2.0 (the "License");
                            you may not use this file except in compliance with the License.
                            You may obtain a copy of the License at

                                 http://www.apache.org/licenses/LICENSE-2.0

                            Unless required by applicable law or agreed to in writing, software
                            distributed under the License is distributed on an "AS IS" BASIS,
                            WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
                            See the License for the specific language governing permissions and
                            limitations under the License.
                        """.trimIndent()
                    }
                }
            }
        }
    }
}

class Version(
    val major: String,
    val minor: String,
    val patch: String) {
    override fun toString() = "$major.$minor.$patch"
}
