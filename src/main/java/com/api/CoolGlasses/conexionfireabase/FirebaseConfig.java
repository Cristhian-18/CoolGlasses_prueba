package com.api.CoolGlasses.conexionfireabase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        String firebaseConfig = "{"
                + "\"type\": \"service_account\","
                + "\"project_id\": \"lentesdb-415c5\","
                + "\"private_key_id\": \"303e14cdf174c508df9b2780038c3d021c443cf4\","
                + "\"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCmnORI8i8WF+i2\\nhyhiji2gA52jgLAKLcq9BL3pmooPFYIPtUI/i5DGyPoOhEdFE1cwQYeDVHQ3RBEK\\n1t61kDRl3p/dxlZQe55E2sufa3OMUZ0FnthmfB1nXvnA3KcTHgxDM0XG9z4D6LeV\\nc3y5xdWKyuYMD+hy8VA4coP+L6dRRE5R8zThcToeh2zuLMmmufw27ClSGzExOHed\\n1Nn4FF3IhLnTqUWaEdNkWuKQBshFu701lR4LRRJCr6GI4xfwGm/QrAvHa33i4qs9\\n5tPqbJ1v1G4sX+6SJiRJh3TcK2UxzejdklP7JXpKiN5oX94UhNOTTivSVTwJTUfS\\n+6i6QRA9AgMBAAECggEAAdHLANLupoHQ73r1YXImpGc1tJnmRXSV9xxGPxCKSAgW\\nzGyEOMs7Sjh0QDru+x2iQTgnlMytm9OyxFjWKmRudg68bzPhygcEX1sf7lgPKHqh\\nsA4gIRDJuQQsAd1HA9BU5CaUsTIOntG1SfAIOva8OMPmrdekobcdIA8VsVQJTAiV\\nSP3UaWbAX3kbayE21BCZ/0oGAvoLC+iGTmqqisQ8cdrpAQD8OtkwghQmlCLLxsmg\\ncpfXhEeBYzX4jEqfgPqiBgfJuBTVrP/imxWKg+yEASXxzYDPQZKPyNMoB5375WV0\\ner/2VqTa0xrTPznvPX8Awvt0YXt+V6ad2vGh9DMEjwKBgQDpV4O9TDredRl9lZpT\\nggu2Nc3i2SdVX2l4VEE8zCVARPND8cY8eXoyAY2EVHcV+JaQL1xZXUnGrmi9k/pw\\nxLmkCPJTO/bpGbybjdkgZ4lj8m2nWdmHxsle5vUUHkrIvBIb5bwuNbfwt/yP0xz6\\npJY26pfwKqeFayUdCvRHNgz99wKBgQC2yptRqApDi20dgsSf9wdRBkwhjwCn3UPZ\\nkmmZlT8OLA6hqKj4YBNfHfYWsvfHxMq9IZbEIrBDawOflFelfM+3HmPbbj+fU02e\\nvVfHhM7cnDeLCPYWZETtqn0u2mqRczYj0F5A4cuV9op21znsHkFuxcAngPMh6NJj\\n9GMO4NHmawKBgQDc06ujTC8yNFPpW9lvSlVXHVg5ZRUqYF9qNyJ+LRzb0+T82iSn\\nOrd/96Q8JcuFiXrD/W5CTFzzoD8TmqWabzLYoHbkeo1TUL4D4JwpZJLLebR4ejm5\\nm1EbdSte3MQdcI8aw7HxcXkgYJ6uZja4pWZblmwwJ83eH+ZBtAo2Elr9RQKBgBP2\\n8RvsFKLn+ZT9kWg/XOc2nt4TdmjfO+fpm+sQaACuQzLY7wuhwrvi09wK1BFXpyUd\\nJBBsEYBrkcu+Mgx7I1zGzvVKh1xphtLNr2a1VLq3vUFMS0SK0xR6SI3zgSw9vy4O\\nNh00DD5m3Ry5yTPG4jKtGp218dhES6ZrLN6VI5fdAoGAY4unQM3qwNXEfdCKjl2X\\njj4ngX4RxUJqujl+ZDlUlf9nQzfjv5bkVjNYQDki0Q5V1bV0oFJBmyMDJwu01SbG\\nanl3plYu5bNSmafap8c4NOcEcl9vr7pVp775Q1OiIiFzgepOAKdqPjWYxbChtXCb\\nuwqdjXvE0cb984PL9XFrXW8=\\n-----END PRIVATE KEY-----\\n\","
                + "\"client_email\": \"firebase-adminsdk-5qo08@lentesdb-415c5.iam.gserviceaccount.com\","
                + "\"client_id\": \"103804531683386650752\","
                + "\"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\","
                + "\"token_uri\": \"https://oauth2.googleapis.com/token\","
                + "\"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\","
                + "\"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-5qo08%40lentesdb-415c5.iam.gserviceaccount.com\","
                + "\"universe_domain\": \"googleapis.com\""
                + "}";

        ByteArrayInputStream serviceAccount = new ByteArrayInputStream(firebaseConfig.getBytes(StandardCharsets.UTF_8));

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://lentesdb-415c5-default-rtdb.firebaseio.com")
                .build();

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    public FirebaseDatabase firebaseDatabase(FirebaseApp firebaseApp) {
        return FirebaseDatabase.getInstance(firebaseApp);
    }
}
