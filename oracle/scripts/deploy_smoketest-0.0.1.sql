-- Instructions to modify script
-- :setvar parameters:
--     l_project : the name of the "pipeline" to deploy.  this name must match the filename
--     l_oldver  : the prior version number, that this one follows.  use 0.0.0 for a new project
--     l_newver  : the version number for this script. Typically an increment from the oldver
--     l_tfsid   : an informational tie back to TFS for linking stories/tasks
--     l_feature : an informational description of the change
--     l_schema  : Schema where script needs to be executed. 
--********************************  Variables Block Begin ***********************

define l_oldver    = 0.0.0
define l_newver    = 0.0.1
define l_tfsid     = 1
define l_feature   = "Validate the Deployment Framework is functional"

define l_project   = DEPLOY_SMOKETEST
define l_schema    = SCH_DEPLOYMENT
define l_instance  = "&&1"
define l_logon     = "&&2"
define l_passwd    = "&&3"
define l_servnowid = "&&4"

--********************************  Variables Block End ***********************

--******************************** Pre-Script block begin. ****************
--******************************** Do not modify ********************************

WHENEVER SQLERROR EXIT SQL.SQLCODE ROLLBACK;
connect &l_logon[&l_schema] / &l_passwd @ &l_instance ;
@pre1_sql.sql &l_instance &l_logon &l_passwd &l_project &l_oldver &l_newver

Prompt Connected to instance &l_instance as &l_schema
--******************************** Pre-Script block end ********************************************

--******************************** Deployment-Script Block Begin******************
-- Place custom or generated SQL commands here.
print "select from sch_deployment.ddl_version1";
select * from sch_deployment.ddl_version1;

--******************************** Deployment-Script Block End *******************


--******************************** Post-Script Block Begin ***********************
--******************************** Do not modify *********************************
execute SCH_DEPLOYMENT.VERSION_UTILS1.UPDATE_DDL_VERSION ('&l_project','&l_oldver.','&l_newver.','&l_feature','&l_tfsid','&l_servnowid');
commit;
spool off;
--******************************** Post-Script block end *******************************************