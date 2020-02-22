# Chapter 03_04

- Create a repository on GitHub using the [New Repository](https://github.com/new) page.  Name it something that relates to the lesson like `exercise-files`.

- In a terminal, run the following commands to initialize the directory as a git repository.

        git init
        git add .
        git commit -m 'first check in'

- Now add the new repository you created as a remote for the local repo.

        git remote add origin git@github.com:YOUR_GITHUB_USER_NAME_HERE/exercise-files.git

- After the remote is added, push the files to the remote.

        git push -u origin master

 - Browse to the repository on GitHub.com and reload the page to confirm the files have been properly pushed.

Once the files are hosted on GitHub.com, you're ready to start making changes locally and pushing them to the remote repo.
