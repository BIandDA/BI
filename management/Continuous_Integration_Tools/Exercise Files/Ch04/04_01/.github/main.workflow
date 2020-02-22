workflow "Pipeline" {
  on = "push"
  resolves = [
    "Check",
    "Build",
    "Deploy Staging",
    "Deploy Production",
  ]
}

action "Check" {
  uses = "./.github/action-check"
}

action "Build" {
  needs = "Check"
  uses = "./.github/action-build"
  secrets = [
    "AWS_ACCESS_KEY_ID",
    "AWS_SECRET_ACCESS_KEY",
    "AWS_DEFAULT_REGION",
  ]
}

action "Deploy Staging" {
  needs = "Build"
  uses = "./.github/action-deploy"
  args = "staging"
  secrets = [
    "AWS_ACCESS_KEY_ID",
    "AWS_SECRET_ACCESS_KEY",
    "AWS_DEFAULT_REGION",
  ]
}

action "Deploy Production" {
  needs = "Deploy Staging"
  uses = "./.github/action-deploy"
  args = "production"
  secrets = [
    "AWS_ACCESS_KEY_ID",
    "AWS_SECRET_ACCESS_KEY",
    "AWS_DEFAULT_REGION",
  ]
}

action "Test Production" {
  needs = "Deploy Production"
  uses = "./.github/action-test"
  args = "production"
}
