
@set jre_bin=e:\master\java\jdk1.8.0_40\jre\bin
 
%jre_bin%\keytool -exportcert -alias cs.cepatpintar.biz.id-1 -keystore jssecacerts -storepass changeit -file cs_cepat_pintar_biz_id.cer
%jre_bin%\keytool -importcert -alias cs.cepatpintar.biz.id -keystore E:\master\java\jdk1.8.0_40\jre\lib\security\cacerts -storepass changeit -file cs_cepat_pintar_biz_id.cer