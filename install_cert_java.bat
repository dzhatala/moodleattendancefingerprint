@rem when you got PKIX error, it mean your JVM doen;t trust remote (webservice) YET
@rem get remote CERT from firefox and save to your local drive.
@rem use this script ton install downloaded CERT into JVM(jre/lib/security/cacerts)


@rem NO NEED ? echo you must running java InstallCert first
@set jre_bin=e:\master\java\jdk1.8.0_40\jre\bin


@rem TARGET JVM 
set var_keys=jssecacerts
set var_keys=E:\master\java\jdk1.8.0_40\jre\lib\security\cacerts

set var_alias=cs.cepatpintar.biz.id-1

%jre_bin%\keytool -delete -noprompt -alias "%var_alias%" -keystore %var_keys% -storepass changeit

@rem %jre_bin%\keytool -exportcert -alias cs.cepatpintar.biz.id-1 -keystore jssecacerts -storepass changeit -file cs_cepat_pintar_biz_id.cer


@rem 
%jre_bin%\keytool -importcert -alias %var_alias% -keystore %var_keys% -storepass changeit -file cs_cepat_pintar_biz_id.cer

@rem %jre_bin%\keytool -importcert -alias cs.cepatpintar.biz.id -keystore jssecacerts  -storepass changeit -file cs_cepat_pintar_biz_id.cer
