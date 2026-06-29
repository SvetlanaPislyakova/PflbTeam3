pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "maven 3.9.6"
    }

    parameters{
        choice(choices: ['chrome', 'firefox'], name: 'BROWSER')
        string(name: 'USER', defaultValue: '', description: 'Username for authentication')
        string(name: 'PASSWORD', defaultValue: '', description: 'Password for authentication')
        string(name: 'DB_USER', defaultValue: '', description: 'Database username')
        string(name: 'DB_PASSWORD', defaultValue: '', description: 'Database password')
    }

    stages {
        stage('Run test') {
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/SvetlanaPislyakova/PflbTeam3.git'

                // Run Maven on a Unix agent.
    sh """  mvn clean test \
            -Dbrowser=${params.BROWSER} \
            -Demail=${params.USER} \
            -Dpassword=${params.PASSWORD} \
            -DdbUser=${params.DB_USER} \
            -DdbPassword=${params.DB_PASSWORD} """
                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    allure includeProperties: false, jdk: '', resultPolicy: 'LEAVE_AS_IS', results: [[path: 'target/allure-results']]
                }
            }
        }
    }
}