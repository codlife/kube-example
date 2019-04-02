import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

import java.util.*;

public class PodRequest {
    public void createPod() {

        ConfigBuilder builder = new ConfigBuilder().withMasterUrl("https://39.104.74.209:6443");
        builder.withOauthToken("eyJhbGciOiJSUzI1NiIsImtpZCI6IiJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlLXN5c3RlbSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJtYW5hZ2VtZW50LWFkbWluLXRva2VuLW1kdm10Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6Im1hbmFnZW1lbnQtYWRtaW4iLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiJmMjdlMmRjMi01NDI2LTExZTktOTg5Ni0wMDE2M2UwMDBmYmUiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6a3ViZS1zeXN0ZW06bWFuYWdlbWVudC1hZG1pbiJ9.UoysOyqhe4g24HwLW8yxr2w0m8IbxteSC_nTFXOI-5ao2gAXiNpgq9DF3ZcvgP792E1A1Z6eSjBbyHtNuljfgSNJYAcixwL3RcQhMj85hxXyWgxwYoLvpkwIBduZb7gtZGgtHx5-SdeOjhNQG9bQPko-8XwYpNguyyeLeNX1fcFLX7sR0w_3rXzl-X79cxbOtraiS32yOXjWE8iRdL8CrePQw8tldDKFkwHhctOVwAhsKOfrbBhKMMcDIozoBtut4uW_C39c0xX6DqCFnSwXiHHdMxi3ioRUu4c9y3B9d3hj5q-no9_o-V1P30pBWvxGGQxkxr1ja6aT3omMwHcQJg");
        builder.withTrustCerts(true);
        Config config = builder.build();
            KubernetesClient client = new DefaultKubernetesClient(config);


            System.out.println(client.getVersion());
            System.out.println("version");
            Map<String, Quantity> requestsMap = new HashMap<String, Quantity>();
            requestsMap.put("cpu", new Quantity("250m"));
            requestsMap.put("memory", new Quantity("64Mi"));

            Map<String, Quantity> limitsMap = new HashMap<String, Quantity>();
            limitsMap.put("cpu", new Quantity("500m"));
            limitsMap.put("memory", new Quantity("128Mi"));

            Container container = new ContainerBuilder().withImage("busybox").
                    withNewResources().withRequests(requestsMap).withLimits(limitsMap).endResources().
                    withCommand("sleep", "3600").withImagePullPolicy("IfNotPresent").withName("busybox").build();

            Pod pod = new PodBuilder().
                    withApiVersion("v1").
                    withNewMetadata().
                    withName("busybox").
                    withNamespace("default").
                    endMetadata().
                    withNewSpec().
                    withContainers(container).
                    withRestartPolicy("Always").
                    endSpec().build();

            client.pods().create(pod);

    }


    public static void main(String[] args) {
        PodRequest p = new PodRequest();
        p.createPod();
    }
}
