# Oracle deploy Project Template

This is the project template for using the TAS Oracle Database promotion process.

This template provides:
1. Required Directory structure
2. Required groovy Configuration file
3. Initial sql file to smoke-test the promotion framework
4. Jenkins File updates as per project

## Table of Contents

Make sure this is updated based on the sections included:

- [Getting Started](#getting-started)
- [Contributing](#contributing)
- [Versioning](#versioning)
- [Support + Feedback](#support--feedback)
- [License](#license)

## Getting Started

Create your own repo for Oracle deployments by using this repo as the Template.

#### deployPrepConfig.groovy
This script is required and provides the flexibility to change the location of the actual .sql scripts.  The individual
line items are list below:
```js
// Do not change. Directory to write promotion output. Is created relative to root.
appSettings.DeployHome = 'DbDeployPrepTest'

//Location of scripts to run.  This repo is copied to the "root" of the jenkins workspace.  This path is relative to the ./src folder where the promotion executes.  ../ returns to the root of the workspace.
appSettings.TfsScriptBase = '../oracle/scripts'

// not used, but required. set to any database friendly name.
appSettings.BaselineDBHome = 'tom_ci1' 

//not used,Do not change.  schema where the depoloyment metadata is stored.  
appSettings.metaDataSchema = 'sch_deployment'

```

#### Jenkins Files Information 

###### Location : im-oracle-deploy-template/Jenkinsfile/

 JenkinsfileOracleDEPLOYFeeder.groovy  - Feeder script for  deploy job(prod and prod-1) 

 JenkinsfileOracleDEPLOYFeederNP.groovy - Feeder script for  deploy job(lower environments)

 JenkinsfileOracleDEPLOYOneScriptFeeder.groovy - Feeder script for one script / Adhoc job(prod and prod-1), Only for DBA group

 JenkinsfileOracleDEPLOYOneScriptFeederNP.groovy - Feeder script for one script / Adhoc job(lower environments), DEV is for Developers and the rest is for DBA group

 JenkinsfileOracleTRYFeeder.groovy - Feeder script for  try job(prod and prod-1) - Developers can run this job in all environments 

 JenkinsfileOracleTRYFeederNP.groovy - Feeder script for  try job(prod and prod-1) - Developers can run this job in all environments 



#### Jenkins Files updates as per Project needs 

1. Update deploy_params section for the first time, as per project.
2. Update parameters section with the project appropriate choices.
3. Save, Commit and Push the changes to github repo.
4. Below are the parameters that need to be changed in the Jenkins files accordingly:
	
deploy_params: deploy_environment: database_source - Friendly name of source database server

deploy_params: deploy_environment: database_target - Friendly name of target database server

deploy_params: deploy_environment: generic_job - location of generic job on the same Jenkins server

deploy_params: deploy_environment: script_repo - github repo where the client promotion files are located

deploy_params: deploy_environment: script_repo_credential_name - The Jenkins Credential that contains the key to access the SCRIPT_REPO

deploy_params: deploy_environment: config_file_location - location of the deployPrepConfigVertica.groovy script in the SCRIPT_REPO

deploy_params: deploy_environment: email_list - Team's distribution list

deploy_params: deploy_environment: slack_channel - Slack Channel name

pipeline:agent - set the appropriate targetserver label to run on

pipeline:parameters:deploy_environment- TEST,...(one time setup list)

pipeline:parameters:project_name - Project name should match with file name (one time setup list)

pipeline:parameters:servicenow_ticket_nbr - TFS Story number for DEV or change task number for higher environments 


#### deploy_smoketest-0.0.1.sql

Sample deployment file, with instructions.  Contains some standards that should be adhered to for successfuly well-commented output files.
The ```deploy_smoketest-0.0.1.sql``` file can be used to verify a promotion.



A deployment .sql file is composed of 4 distinct components. 

1. Variables            : Modify per script. See instructions in the file itself
2. Pre-Script           : Do not Modify. Boilerplate
3. Deployment-Script    : Modify per script.
4. Post-Script          : Do not Modify. Boilerplate 


## Contributing

We appreciate feedback and contribution to this repo! Before you get started, please see the following:

- [RGA's general contribution guidelines](https://github.com/rgare/innersource-project-template/blob/master/GENERAL-CONTRIBUTING.md)
- [RGA's code of conduct guidelines](https://www.rgare.com/docs/default-source/regulatory-documents/rga-code-of-conduct.pdf)
- [This repo's contribution guide](CONTRIBUTING.md)

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags).  

## Support + Feedback

Include information on how to get support. Consider adding:

- Use [Issues](https://github.com/rgare/innersource-project-template/issues) for code-level support
- Use [Slack](https://rgare.slack.com/archives/C8YL8FGRJ/p1518107525000584) for usage, questions, specific cases
- Link to other support forums and FAQs

## License

Link to [LICENSE](LICENSE) doc. Typically RGA copyright notice.
