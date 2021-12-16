/*
Template for Oracle Deploy One Script Non Prod feeder jobs.
parameters that needs to be replaced as per Project:
deploy_params section is mostly one time setup based on project environments (DEV,TEST)

		
	deploy_params: deploy_environment: database_target - Friendly name of target database server
	deploy_params: deploy_environment: generic_job - location of generic job on the same Jenkins server
	deploy_params: deploy_environment: script_repo - github repo where the client promotion files are located
	deploy_params: deploy_environment: script_repo_credential_name - The Jenkins Credential that contains the key to access the SCRIPT_REPO
	deploy_params: deploy_environment: config_file_location - location of the deployPrepConfigVertica.groovy script in the SCRIPT_REPO
	deploy_params: deploy_environment: email_list - Individuals that will be notified on failure
	deploy_params: deploy_environment: slack_channel - Slcak Channel name
	pipeline:agent - set the appropriate targetserver label to run on
	pipeline:parameters:deploy_environment- DEV,TEST,...(one time setup list)
	pipeline:parameters:project_name - Project name should match with file name (one time setup list)
	pipeline:parameters:filename_version - File name (user input - current file name version, 0.0.1,0.0.2..)
	pipeline:parameters:servicenow_ticket_nbr - TFS Story number for DEV or change task number for higher environments 
	
*/

library identifier: 'lib@master', retriever: modernSCM(
        [$class: 'GitSCMSource', remote: 'git@github.com:rgare/rga-deployment.git',
         credentialsId: 'rga-deployment-ro'])
		 
		 
def deploy_params = [
	
          DEV: [
            database_target: 'stletl3_dev',
            generic_job: '../NonProd/GenericOracleDEPLOYOneScript',
			script_repo: 'git@github.com:rgare/im-etl-metadata.git',
			script_repo_credential_name: 'rga-dms-buildbot',
			config_file_location: '../oracle/scripts/deployPrepConfig.groovy',
			script_directory: '/oracle/scripts',
			email_list: 'sparvathaneni@rgare.com',
			slack_channel: '#bos-imas-jenkins-np'
			
          ],
          TEST: [
			database_target: 'stletl3_tst',
            generic_job: '../NonProd/GenericOracleDEPLOYOneScript',
			script_repo: 'git@github.com:rgare/im-etl-metadata.git',
			script_repo_credential_name: 'rga-dms-buildbot',
			config_file_location: '../oracle/scripts/deployPrepConfig.groovy',
			script_directory: '/oracle/scripts',
			email_list: 'sparvathaneni@rgare.com',
			slack_channel: '#bos-imas-jenkins-np'
          ]
         

]		 

pipeline {
    agent { label 'im-slave' }

    parameters {
		choice(name: 'deploy_environment', choices: ['DEV','TEST'], description: 'Choose the Deployment Environment')
		choice(name: 'project_name', choices: ['ora_test_proj1','ora_test_proj2'], description: 'Choose the Project')
		string(name: 'filename_version', description: 'Enter the version number of the sql file to be deployed.Example. If the script file name is "vrt_bops_acf_ity-0.0.1.sql", then just enter "0.0.1".')
		string(name: 'servicenow_ticket_nbr', description: 'Approved Ticket # for the promotion')
		
    }
	
	
	environment {
	
		database_target="${deploy_params[params.deploy_environment].database_target}"
		filename="${params.project_name}-${params.filename_version}.sql"
		script_repo="${deploy_params[params.deploy_environment].script_repo}"
		script_repo_credential_name="${deploy_params[params.deploy_environment].script_repo_credential_name}"
		config_file_location="${deploy_params[params.deploy_environment].config_file_location}"
		script_directory="${deploy_params[params.deploy_environment].script_directory}"
		generic_job="${deploy_params[params.deploy_environment].generic_job}"	
        email_list="${deploy_params[params.deploy_environment].email_list}"
		slack_channel="${deploy_params[params.deploy_environment].slack_channel}"
		notify_desc="${params.deploy_environment}-${params.project_name}-${params.filename_version}"

    }

    options {
        buildDiscarder(logRotator(numToKeepStr: "1000", daysToKeepStr: "370", artifactDaysToKeepStr: "1000", artifactNumToKeepStr: "370"))
    }



    stages {
        stage("Execute Oracle Single Script") {
            steps {
                script {
                    
                    buildResult = build(job: "${env.generic_job}",
                            parameters: [
                                    string(name: 'db_target', value: "${env.database_target}"),
									string(name: 'service_now_ticket', value: "${params.servicenow_ticket_nbr}"),
									string(name: 'script_filename', value: "${env.filename}"),			
									string(name: 'script_repo', value: "${env.script_repo}"),
									string(name: 'script_repo_credential_name', value: "${env.script_repo_credential_name}"),
									string(name: 'script_location_in_repo', value: "${env.script_directory}"),
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

