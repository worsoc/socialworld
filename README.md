# Social World

SocialWorld is a software project for simulation people's social behaviour. Therefore the actions, the speech and the physical and mental constitution are part of a simulation process that calculates, how attributes of creatures and characters change and how they act and react. Furthermore the simulation focuses on perception, interests, relations and knowledge.

![Project Status](https://github.com/worsoc/socialworld/actions/workflows/build-gradle-project.yml/badge.svg?event=push)

## Prerequisites

* **Java SDK 21** or higher.
* **X11 / Graphical Environment** (The simulation requires a display to run the GUI).

## How to start

1. Clone the project with `git clone https://github.com/worsoc/socialworld.git`
1. Build the project with `./gradlew build`
1. Run the project with `./gradlew run`

### With Eclipse Gradle Project

This is a **Gradle Project**. The recommended way to develop is using the Eclipse Gradle integration:

1. In Eclipse, go to **File -> Import... -> Gradle -> Existing Gradle Project**.
2. Select the project root folder.
3. This automatically manages all dependencies and the Java 21 build path.

### With Eclipse

As this is a _Gradle Project_ Gradle provides tasks for creating _Eclipse_ specific files.

Run:
```bash
> ./gradlew cleanEclipse # For cleaning Eclipse specific stuff (sometimes necessary)



> ./gradlew eclipse # For creating files
> ./gradlew cleEcl ecl # Shortcut for doing both
```

Then open _Eclipse_ and **import the project** into your workspace. If you already had imported the project and you have problems, delete the project and **re-import** it.

It's a good advice to have your workspace not in the project folder but outside.

### With VSCode

Install the recommended plugins from `.vscode/extensions.json`.

## License

This project is licensed under the **GNU General Public License v3.0 or later**. See the [LICENSE](LICENSE) file for details.

## How to contribute

1. Clone the project to your local machine (as described above)
1. Create a new branch with `git checkout -B my-new-feature-branch`
1. Check if you are on your branch with `git status`
1. Make changes to your code
1. Commit your changes with `git commit -am "Commit Message"`
1. Push your changes to the remote with `git push`
1. Create a new pull request on Github here: <https://github.com/worsoc/socialworld/pulls>
   1. Select `master` as _base_ and your branch `my-new-feature-branch` as _compare_
   1. Add some meaningful description
1. Assign someone to the PR for review.
