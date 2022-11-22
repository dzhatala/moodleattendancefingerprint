set cp_cp=bin2;libs\beandt.jar;libs\dbswing.jar;libs\dx.jar;libs\gson-2.8.7-javadoc.jar;libs\gson-2.8.7.jar;libs\json-simple-1.1.1.jar


set cp_cp=%cp_cp%;libs/mysql-connector-java-5.1.46-bin.jar
set cp_cp=%cp_cp%;libs/mysql-connector-java-5.1.46.jar

@set cp_cp=%cp_cp%;..\lib\https\httpcomponents-client-4.3.2\lib\commons-codec-1.6.jar
@set cp_cp=%cp_cp%;..\lib\https\httpcomponents-client-4.3.2\lib\commons-logging-1.1.3.jar
@set cp_cp=%cp_cp%;..\lib\https\httpcomponents-client-4.3.2\lib\fluent-hc-4.3.2.jar
@set cp_cp=%cp_cp%;..\lib\https\httpcomponents-client-4.3.2\lib\httpclient-4.3.2.jar
@set cp_cp=%cp_cp%;..\lib\https\httpcomponents-client-4.3.2\lib\httpclient-cache-4.3.2.jar
@set cp_cp=%cp_cp%;..\lib\https\httpcomponents-client-4.3.2\lib\httpcore-4.3.1.jar
@set cp_cp=%cp_cp%;..\lib\https\httpcomponents-client-4.3.2\lib\httpmime-4.3.2.jar


call e:\master\java\jdk1.8.0_40\jre\bin\java -cp %cp_cp% cpintar.Launcher 
