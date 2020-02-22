import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2018_2.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2018.2"

project {

    vcsRoot(HttpsGithubComAutomate6500teamcityDemoRefsHeadsMaster)

    buildType(Pipeline)
}

object Pipeline : BuildType({
    name = "Pipeline"

    params {
        param("env.AWS_DEFAULT_REGION", "us-west-2")
        password("env.AWS_SECRET_ACCESS_KEY", "zxx32994b069953113f6e42ab0da1bbd68db1801461cf26c0d1eeb5425410992b220098f87209201c98775d03cbe80d301b", label = "AWS_SECRET_ACCESS_KEY", description = "AWS_SECRET_ACCESS_KEY", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("env.AWS_ACCESS_KEY_ID", "zxx362a35ac520668a294fb705feca3fd1218978e369077db49", label = "AWS_ACCESS_KEY_ID", description = "AWS_ACCESS_KEY_ID", display = ParameterDisplay.HIDDEN, readOnly = true)
    }

    vcs {
        root(HttpsGithubComAutomate6500teamcityDemoRefsHeadsMaster)
    }

    steps {
        script {
            name = "Requirements"
            scriptContent = """
                #!/bin/bash
                python3 -m virtualenv venv
                source venv/bin/activate
                pip install --upgrade --requirement requirements.txt
            """.trimIndent()
        }
        script {
            name = "Check"
            scriptContent = """
                #!/bin/bash
                source venv/bin/activate
                flake8 --ignore=E501,E231 *.py tests/*.py
                pylint --errors-only --disable=C0301 --disable=C0326 *.py tests/*.py
                python -m unittest --verbose --failfast
            """.trimIndent()
        }
        script {
            name = "Build"
            scriptContent = """
                #!/bin/bash
                source venv/bin/activate
                ./upload-new-version.sh
            """.trimIndent()
        }
        script {
            name = "Deploy Staging"
            scriptContent = """
                #!/bin/bash
                source venv/bin/activate
                ./deploy-new-version.sh staging
            """.trimIndent()
        }
        script {
            name = "Test Staging"
            scriptContent = """
                #!/bin/bash
                ./test-environment.sh staging
            """.trimIndent()
        }
        script {
            name = "Deploy Production"
            scriptContent = """
                #!/bin/bash
                source venv/bin/activate
                ./deploy-new-version.sh production
            """.trimIndent()
        }
        script {
            name = "Test Production"
            scriptContent = """
                #!/bin/bash
                ./test-environment.sh production
            """.trimIndent()
        }
    }

    triggers {
        vcs {
        }
    }
})

object HttpsGithubComAutomate6500teamcityDemoRefsHeadsMaster : GitVcsRoot({
    name = "https://github.com/automate6500/teamcity-demo#refs/heads/master"
    url = "https://github.com/automate6500/teamcity-demo"
    authMethod = password {
        userName = "automate6500"
        password = "zxx6b8831c09ee6589309a43fe9cc3fa91b33875ea28eb9c622a1248ee79fc337aa"
    }
})
