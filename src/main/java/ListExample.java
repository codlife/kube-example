import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by wjf on 19-3-29.
 */
public class ListExample {
    public static void main(String[] args){

        ConfigBuilder builder = new ConfigBuilder().withMasterUrl("https://39.104.74.209:6443");
        builder.withOauthToken("eyJhbGciOiJSUzI1NiIsImtpZCI6IiJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlLXN5c3RlbSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJtYW5hZ2VtZW50LWFkbWluLXRva2VuLXZyNnRnIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6Im1hbmFnZW1lbnQtYWRtaW4iLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiIyODA3NzVjYS01NDhlLTExZTktODMzYS0wMDE2M2UwMDBmYmUiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6a3ViZS1zeXN0ZW06bWFuYWdlbWVudC1hZG1pbiJ9.NcD1beaVqNWihrji-B_gBz480hP70PV0DJwqwq79VCg79l3yTlYXfeVY04iN8Xm4uh3z-8HvdIu2Hip9iTQsNuAtNz9U6GrAdcfb0auV1P6tsydpxDK62rg5qTmX7rh-pkgGjgW-S83W_b6AgbY39Hv-nq62GM_bMGtEc6Frj2ozIovJj-Cg1OjUmK3qN1gaJ7JIWN_Bb4t5mvbO1SHEfNz6Ab9d9rTDWlqkBEpCvMwB9RWQZ49ABaGMeJphWUpKgSAZlF_UQvX1JmjH6h4ILfeC88VopKJSZEbToDByJuejuHlw7E3FxcYPfJynM_4MWyYLgX2IhM3Qdg59_1xURA");
        builder.withTrustCerts(true);
        Config config =  builder.build();
        try  {
            KubernetesClient client = new DefaultKubernetesClient(config);
            System.out.println(client.namespaces().list());
            System.out.println(client.namespaces().withLabel("this", "works").list());
            System.out.println(client.pods().withLabel("this", "works").list());
            System.out.println(client.pods().inNamespace("test").withLabel("this", "works").list());
            System.out.println(client.pods().inNamespace("test").withName("testing").get());
            System.out.println(client.getConfiguration().getMasterUrl());
            System.out.println(client.getNamespace());
            String service = client.services().toString();
            System.out.println(service);

      /*
       * 	The continue option should be set when retrieving more results from the server.
       * 	Since this value is server defined, clients may only use the continue value from
       * 	a previous query result with identical query parameters (except for the value of
       * 	continue) and the server may reject a continue value it does not recognize.
       */

        } catch (KubernetesClientException e) {
            e.printStackTrace();
        }
    }
}
