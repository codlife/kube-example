/**
 * Copyright (C) 2015 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.NamespaceBuilder;
import io.fabric8.kubernetes.api.model.ServiceAccount;
import io.fabric8.kubernetes.api.model.ServiceAccountBuilder;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
public class DeploymentExamples {
    private static final Logger logger = LoggerFactory.getLogger(DeploymentExamples.class);
    public static void main(String[] args) throws InterruptedException {

        ConfigBuilder builder = new ConfigBuilder().withMasterUrl("https://39.104.74.209:6443");
        builder.withOauthToken("eyJhbGciOiJSUzI1NiIsImtpZCI6IiJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlLXN5c3RlbSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJtYW5hZ2VtZW50LWFkbWluLXRva2VuLXZyNnRnIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6Im1hbmFnZW1lbnQtYWRtaW4iLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiIyODA3NzVjYS01NDhlLTExZTktODMzYS0wMDE2M2UwMDBmYmUiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6a3ViZS1zeXN0ZW06bWFuYWdlbWVudC1hZG1pbiJ9.NcD1beaVqNWihrji-B_gBz480hP70PV0DJwqwq79VCg79l3yTlYXfeVY04iN8Xm4uh3z-8HvdIu2Hip9iTQsNuAtNz9U6GrAdcfb0auV1P6tsydpxDK62rg5qTmX7rh-pkgGjgW-S83W_b6AgbY39Hv-nq62GM_bMGtEc6Frj2ozIovJj-Cg1OjUmK3qN1gaJ7JIWN_Bb4t5mvbO1SHEfNz6Ab9d9rTDWlqkBEpCvMwB9RWQZ49ABaGMeJphWUpKgSAZlF_UQvX1JmjH6h4ILfeC88VopKJSZEbToDByJuejuHlw7E3FxcYPfJynM_4MWyYLgX2IhM3Qdg59_1xURA");
        builder.withTrustCerts(true);
        Config config =  builder.build();
        KubernetesClient client = new DefaultKubernetesClient(config);
        try {
            // Create a namespace for all our stuff
            Namespace ns = new NamespaceBuilder().withNewMetadata().withName("thisisatest2").addToLabels("this", "rocks").endMetadata().build();
            log("Created namespace", client.namespaces().createOrReplace(ns));

            ServiceAccount fabric8 = new ServiceAccountBuilder().withNewMetadata().withName("fabric8").endMetadata().build();

            client.serviceAccounts().inNamespace("thisisatest2").createOrReplace(fabric8);
            for (int i = 0; i < 2; i++) {
                System.err.println("Iteration:" + (i+1));
                Deployment deployment = new DeploymentBuilder()
                        .withNewMetadata()
                        .withName("nginx2")
                        .endMetadata()
                        .withNewSpec()
                        .withReplicas(1)
                        .withNewTemplate()
                        .withNewMetadata()
                        .addToLabels("app", "nginx")
                        .endMetadata()
                        .withNewSpec()
                        .addNewContainer()
                        .withName("nginx2")
                        .withImage("nginx")
                        .addNewPort()
                        .withContainerPort(80)
                        .endPort()
                        .endContainer()
                        .endSpec()
                        .endTemplate()
                        .withNewSelector()
                        .addToMatchLabels("app", "nginx")
                        .endSelector()
                        .endSpec()
                        .build();
                deployment = client.apps().deployments().inNamespace("thisisatest").create(deployment);
                log("Created deployment", deployment);

                System.err.println("Scaling up:" + deployment.getMetadata().getName());
                client.apps().deployments().inNamespace("thisisatest").withName("nginx").scale(2, true);
                log("Created replica sets:", client.apps().replicaSets().inNamespace("thisisatest").list().getItems());
                System.err.println("Deleting:" + deployment.getMetadata().getName());
//                client.resource(deployment).delete();
            }
            log("Done.");

        }finally {
            client.namespaces().withName("thisisatest").delete();
            client.close();
        }
    }

    private static void log(String action, Object obj) {
        logger.info("{}: {}", action, obj);
    }

    private static void log(String action) {
        logger.info(action);
    }
}