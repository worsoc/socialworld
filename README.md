# Social World

SocialWorld is a simulation of humans interaction.

![Project Status](https://github.com/MatWorsoc/socialworld/actions/workflows/build-gradle-project.yml/badge.svg?event=push)

## How to start

1. Clone the project with `git clone https://github.com/MatWorsoc/socialworld.git`
1. Build the project with `./gradlew build`
1. Run the project with `./gradlew run`

### With Eclipse

As this is a _Gradle Project_ Gradle provides tasks for creating _Eclipse_ specific files.

Run:
```bash
> ./gradlew cleanEclipse # For cleaning Eclipse specific stuff (sometime neseccary)
> ./gradlew eclipse # For creating files
> ./gradlew cleEcl ecl # Shortcut for doing both
```

The open _Eclipse_ and **import the project** into your workspace. If you already had imported the project and you have problems, delete the projects and **re-import** them.

It's a good advice to have your workspace not in the project folder but outside.

### With VSCode

Install the recommended plugins from `.vscode/extensions.json`.

## How to contribute

1. Clone the project to your local maschine (as described above)
1. Create a new branch with `git checkout -B my-new-feature-branch`
1. Check if you are on your branch with `git status`
1. Make changes to your code
1. Commit your changes with `git commit -am "Commit Message"`
1. Push your changes to the remote with `git push`
1. Create a new pull request on Github here: <https://github.com/MatWorsoc/socialworld/pulls>
   1. Select `master` as _base_ and your branch `my-new-feature-branch` as _compare_
   1. Add some meaningful description
1. Assign someone to the PR for review.
