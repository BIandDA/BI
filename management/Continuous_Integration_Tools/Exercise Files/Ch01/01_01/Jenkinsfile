pipeline {
    agent any
    environment {
        AWS_ACCESS_KEY_ID     = credentials('AWS_ACCESS_KEY_ID')
        AWS_SECRET_ACCESS_KEY = credentials('AWS_SECRET_ACCESS_KEY')
        AWS_DEFAULT_REGION    = 'us-west-2'
    }
    stages {
        stage('Requirements') {
            steps {
                sh('''#!/bin/bash
                        python3 -m virtualenv local
                        source ./local/bin/activate
                        pip install --upgrade --requirement requirements.txt
                        ''')
            }
        }
        stage('Check') {
            parallel {
                stage('Check:Lint') {
                    steps {
                        sh('''#!/bin/bash
                                source ./local/bin/activate
                                flake8 --ignore=E501,E231 *.py tests/*.py
                                pylint --errors-only --disable=C0301 --disable=C0326 *.py tests/*.py
                                ''')
                    }
                }
                stage('Check:UnitTest') {
                    steps {
                        sh('''#!/bin/bash
                                source ./local/bin/activate
                                python -m unittest --verbose --failfast
                                ''')
                    }
                }
            }
        }
        stage('Build') {
            steps {
                sh('''#!/bin/bash
                        source ./local/bin/activate
                        ./upload-new-version.sh
                        ''')
            }
        }
        stage('Deploy Staging') {
            steps {
                sh('''#!/bin/bash
                        source ./local/bin/activate
                        ./deploy-new-version.sh staging
                        ''')
            }
        }
        stage('Test Staging') {
            steps {
                sh('''#!/bin/bash
                        source ./local/bin/activate
                        ./test-environment.sh staging
                        ''')
            }
        }
        stage('Deploy Production') {
            steps {
                sh('''#!/bin/bash
                        source ./local/bin/activate
                        ./deploy-new-version.sh production
                        ''')
            }
        }
        stage('Test Production') {
            steps {
                sh('''#!/bin/bash
                        source ./local/bin/activate
                        ./test-environment.sh production
                        ''')
            }
        }
    }
}
