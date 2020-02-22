# Exercise Files for Learning GitHub Actions

The exercise files are located in folders named to match the chapter and lesson they accompany.

- *PREREQUSITES: You'll need to have the `git` command line tool installed for your system and access to a terminal program like [GitBash](https://gitforwindows.org/) on Windows or [iTerm2](https://www.iterm2.com/) on macOS.  The default terminal program for your system will work as well as long as the git program is in your PATH.*

## Using Exercise Files with Repositories

Follow these steps if you want to use the exercise files the same way they are used within the course.

We'll use the exercise files from Chapter 1, Lesson 1 for this example.

- Create a repository on GitHub using the [New Repository](https://github.com/new) page.  Name it something that relates to the lesson like `ch01-01-01`.

- Download the exercise files and unzip the archive.  If you are reading this, you have most likely completed this step! :D

- Open a terminal window and change directories to the location where you unzipped archive.  "CD" into the directory with the lesson you want to work with; for this example, we'll use Ch01/01_01.

        cd Ch01/01_01

- Run the following commands to initialize the directory as a git repository.

        git init
        git add .
        git commit -m 'first check in'

- Now add the new repository you created as a remote for the local repo.

        git remote add origin git@github.com:YOUR_GITHUB_USER_NAME_HERE/ch01-01-01.git

- After the remote is added, push the files to the remote.

        git push -u origin master

 - Browse to the repository on GitHub.com and reload the page to confirm the files have been properly pushed.

Once the files are hosted on GitHub.com, you're ready to start making changes locally and pushing them to the remote repo.
