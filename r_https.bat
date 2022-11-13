@set cp_cp=
@set cp_cp=%cp_cp%;..\lib\https\httpcomponents-client-4.3.2\lib\commons-codec-1.6.jar
@set cp_cp=%cp_cp%;..\lib\https\httpcomponents-client-4.3.2\lib\commons-logging-1.1.3.jar
@set cp_cp=%cp_cp%;..\lib\https\httpcomponents-client-4.3.2\lib\fluent-hc-4.3.2.jar
@set cp_cp=%cp_cp%;..\lib\https\httpcomponents-client-4.3.2\lib\httpclient-4.3.2.jar
@set cp_cp=%cp_cp%;..\lib\https\httpcomponents-client-4.3.2\lib\httpclient-cache-4.3.2.jar
@set cp_cp=%cp_cp%;..\lib\https\httpcomponents-client-4.3.2\lib\httpcore-4.3.1.jar
@set cp_cp=%cp_cp%;..\lib\https\httpcomponents-client-4.3.2\lib\httpmime-4.3.2.jar



@set cp_cp=bin;%cp_cp%
@set jre_bin=e:\master\java\jdk1.8.0_40\jre\bin
rem \java
rem %javavm% -cp %cp_cp% util.InstallCert cs.cepatpintar.biz.id:443


@set cp_cp=..\testHTTPS_01\bin;%cp_cp%
REM %jre_bin%\java -cp %cp_cp% -Djavax.net.debug=all -Dcom.sun.security.enableAIAcaIssuers=true testHTTPS_01.Main_02
@set cp_cp=..\testHTTPS_01\bin;%cp_cp%
%jre_bin%\java -cp %cp_cp% -Djavax.net.debug=all -Dcom.sun.security.enableAIAcaIssuers=true util.InstallCert cs.cepatpintar.biz.id
 
REM %jre_bin%\keytool --list -keystore E:\master\java\jdk1.8.0_40\jre\lib\security\cacerts
