pipeline {
  agent none
  options { timestamps() }
      triggers {
          issueCommentTrigger('.*test this please.*')
      }
  stages {
    stage('tests') {
          agent none
          steps {
                echo "build number: $env.BUILD_NUMBER"
                echo "change id: $env.CHANGE_ID"
                echo "branch name $env.BRANCH_NAME"
          }

    } // END stage('tests')

  }
}
