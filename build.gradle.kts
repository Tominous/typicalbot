allprojects {
    repositories {
        jcenter()
        mavenLocal()
        maven("https://jitpack.io")
    }
}

group = "com.typicalbot"
version = Version(major = "3", minor = "0", patch = "0").toString()

class Version(val major: String, val minor: String, val patch: String) {
    override fun toString() = "$major.$minor.$patch"
}
