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

              sh """
                echo "Inside test step"
              """

                        script {
                            // CHANGE_ID is set only for pull requests, so it is safe to access the pullRequest global variable
                            if (env.CHANGE_ID) {
                                pullRequest.addLabel('Tadaaa !!')
                            } }
                      shell """
                        echo "here..."
                      """
          }

    } // END stage('tests')

  }
}
