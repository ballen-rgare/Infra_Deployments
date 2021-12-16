/*
Template for Oracle Try Script for Prod and Prod-1 env feeder jobs.
parameters needs to be replaced as per Project:
deploy_params section is mostly one time setup based on project environments (UAT,PROD)
		
	deploy_params: deploy_environment: database_source - Friendly name of source database server
	deploy_params: deploy_environment: database_target - Friendly name of target database server
	deploy_params: deploy_environment: generic_job - location of generic job on the same Jenkins server
	deploy_params: deploy_environment: script_repo - github repo where the client promotion files are located
	deploy_params: deploy_environment: script_repo_credential_name - The Jenkins Credential that contains the key to access the SCRIPT_REPO
	deploy_params: deploy_environment: config_file_location - location of the deployPrepConfigVertica.groovy script in the SCRIPT_REPO
	deploy_params: deploy_environment: email_list - Individuals that will be notified on failure
	deploy_params: deploy_environment: slack_channel - Slcak Channel name
	pipeline:agent - set the appropriate targetserver label to run on
	pipeline:parameters:deploy_environment- UAT,PROD(one time setup list)
	pipeline:parameters:project_name - Project name should match with file name (one time setup list)
	
*/

library identifier: 'lib@master', retriever: modernSCM(
        [$class: 'GitSCMSource', remote: 'git@github.com:rgare/rga-deployment.git',
         credentialsId: 'rga-deployment-ro'])
		 
def deploy_params = [
	
          UAT: [
			database_source: 'stletl3_tst',
            database_target: 'stletl3_uat',
            generic_job: '../../../DB_Deployment/Prod/GenericOracleTRY',
			script_repo: 'git@github.com:rgare/im-etl-metadata.git',
			script_repo_credential_name: 'rga-dms-buildbot',
			config_file_location: '../oracle/scripts/deployPrepConfig.groovy',
			script_directory: '/oracle/scripts',
			email_list: 'sparvathaneni@rgare.com',
			slack_channel: '#bos-imas-jenkins-np'
			
          ],
		  PROD: [
			database_source: 'stletl3_uat',
            database_target: 'stletl3_prd',
            generic_job: '../../../DB_Deployment/Prod/GenericOracleTRY',
			script_repo: 'git@github.com:rgare/im-etl-metadata.git',
			script_repo_credential_name: 'rga-dms-buildbot',
			config_file_location: '../oracle/scripts/deployPrepConfig.groovy',
			script_directory: '/oracle/scripts',
			email_list: 'sparvathaneni@rgare.com',
			slack_channel: '#bos-imas-jenkins-np'
			
          ]
]		 

pipeline {
    agent { label 'wf-docker-slave' }

    parameters {
		choice(name: 'deploy_environment', choices: ['UAT','PROD'], description: 'Choose the Deployment Environment')
		choice(name: 'project_name', choices: ['ora_test_proj1','ora_test_proj2'], description: 'Choose the Project')
    }
	
	
	environment {
	
		database_source="${deploy_params[params.deploy_environment].database_source}"
		database_target="${deploy_params[params.deploy_environment].database_target}"
		script_repo="${deploy_params[params.deploy_environment].script_repo}"
		script_repo_credential_name="${deploy_params[params.deploy_environment].script_repo_credential_name}"
		config_file_location="${deploy_params[params.deploy_environment].config_file_location}"
		script_directory="${deploy_params[params.deploy_environment].script_directory}"
		generic_job="${deploy_params[params.deploy_environment].generic_job}"	
        email_list="${deploy_params[params.deploy_environment].email_list}"
		slack_channel="${deploy_params[params.deploy_environment].slack_channel}"
		notify_desc="${params.deploy_environment}-${params.project_name}"

    }

    options {
        buildDiscarder(logRotator(numToKeepStr: "1000", daysToKeepStr: "370", artifactDaysToKeepStr: "1000", artifactNumToKeepStr: "370"))
    }



    stages {
        stage("Execute Oracle Try Script") {
            steps {
                script {
                    
                    buildResult = build(job: "${env.generic_job}",
                            parameters: [

									string(name: 'db_source', value: "${env.database_source}"),
									string(name: 'db_target', value: "${env.database_target}"),
									string(name: 'project_list', value: "${params.project_name}"),
									string(name: 'service_now_ticket', value: 'N/A'),
									string(name: 'script_repo', value: "${env.script_repo}"),
									string(name: 'script_repo_credential_name', value: "${env.script_repo_credential_name}"),
									string(name: 'config_file_location', value: "${env.config_file_location}")
                            ],
                            propagate: false
                    )
                    currentBuild.displayName = "#${BUILD_NUMBER} - ${buildResult.number} - [${params.project_name}]"
                    currentBuild.result = buildResult.getResult()
                }
            }
            post {
                failure {
                    script {
                        RgaNotify.notify(this, 'FAILURE', env.notify_desc, env.email_list, env.slack_channel)
                   }
                }
                success {
                    script {
                        RgaNotify.notify(this, 'SUCCESS', env.notify_desc, env.email_list, env.slack_channel)
                    }
                }
            }
        }

    }
}

