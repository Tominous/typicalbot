# Contributing to TypicalBot

Thank you for considering making a contribution to TypicalBot! This guide explains how to setup your environment for TypicalBot development and where to get help if you encounter trouble.

## Follow the Code of Conduct

In order to foster a more inclusive community, TypicalBot has adopted the [Contributor Covenant](CODE_OF_CONDUCT.md).

## Making Chanes

### Development Setup

In order to make changes to TypicalBot, you'll need:

* A text editor or IDE. We use and recommend [IntelliJ](https://www.jetbrains.com/?from=typicalbot).
* A [Java Development Kit](https://www.oracle.com/technetwork/java/javase/downloads/index.html) version 1.8 or higher. 
* [MongoDB](https://www.mongodb.com/download-center) version 4.0 or higher.
* [git](https://git-scm.com) and a [GitHub account](https://github.com/join).

TypicalBot uses a pull request model for contributions. Fork [typicalbot/typicalbot](https://github.com/typicalbot/typicalbot) and clone your fork.

Configure your Git username and email with
```
git config user.name 'First Last'
git config user.email user@example.com
```

Before importing the project into IntelliJ make sure to run `./gradlew check` at least once so all required files are generated.