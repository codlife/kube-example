package API;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.*;
import io.fabric8.kubernetes.api.model.Node;
import io.fabric8.kubernetes.api.model.Namespace;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wjf on 19-4-26.
 */
public class KubernetesClusterInformation {
    private static KubernetesClient client = null;
    static{
        try {
            ConfigBuilder builder = new ConfigBuilder().withMasterUrl("https://39.104.74.209:6443");
            builder.withOauthToken("eyJhbGciOiJSUzI1NiIsImtpZCI6IiJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlLXN5c3RlbSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJtYW5hZ2VtZW50LWFkbWluLXRva2VuLXZyNnRnIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6Im1hbmFnZW1lbnQtYWRtaW4iLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiIyODA3NzVjYS01NDhlLTExZTktODMzYS0wMDE2M2UwMDBmYmUiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6a3ViZS1zeXN0ZW06bWFuYWdlbWVudC1hZG1pbiJ9.NcD1beaVqNWihrji-B_gBz480hP70PV0DJwqwq79VCg79l3yTlYXfeVY04iN8Xm4uh3z-8HvdIu2Hip9iTQsNuAtNz9U6GrAdcfb0auV1P6tsydpxDK62rg5qTmX7rh-pkgGjgW-S83W_b6AgbY39Hv-nq62GM_bMGtEc6Frj2ozIovJj-Cg1OjUmK3qN1gaJ7JIWN_Bb4t5mvbO1SHEfNz6Ab9d9rTDWlqkBEpCvMwB9RWQZ49ABaGMeJphWUpKgSAZlF_UQvX1JmjH6h4ILfeC88VopKJSZEbToDByJuejuHlw7E3FxcYPfJynM_4MWyYLgX2IhM3Qdg59_1xURA");
            builder.withTrustCerts(true);
            Config config = builder.build();
            client = new DefaultKubernetesClient(config);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Map<String,String> getVersion(){
        Map<String, String> map = new HashMap<String, String>();
        try{
            VersionInfo versionInfo = client.getVersion();
            map.put("Major",versionInfo.getMajor());
            map.put("Minor",versionInfo.getMinor());
            map.put("BuildDate", versionInfo.getBuildDate().toString());
            map.put("GitTreeState",versionInfo.getGitTreeState());
            map.put("Platform",versionInfo.getPlatform());
            map.put("GitVersion",versionInfo.getGitVersion());
            map.put("GoVersion",versionInfo.getGoVersion());
            map.put("GitCommit",versionInfo.getGitCommit());
            map.put("Major",versionInfo.getMajor());
            map.put("Major",versionInfo.getMajor());
            map.put("Major",versionInfo.getMajor());
        } catch(Exception e){
            e.printStackTrace();
        }
        return map;
    }

    public static List<String> getNamespaces() {
        List<String> namespaces = new ArrayList<String>();
        try{
            for(Namespace na : client.namespaces().list().getItems()){
                System.out.println(na.toString());
                namespaces.add(na.toString());
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return namespaces;
    }

    public static List<String> getPods() {
        List<String> pods = new ArrayList<String>();
        try{
            for(Pod na : client.pods().list().getItems()){
                System.out.println(na.toString());
                pods.add(na.toString());
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return pods;
    }

    public static List<String> getNodes(){
        //包含大量信息，需要再自己解析
        List<String> nodes = new ArrayList<String>();
        try {

            for (Node c : client.nodes().list().getItems()) {
                System.out.println(c.toString());
                nodes.add(c.toString());
            }
        } catch( Exception e){
            e.printStackTrace();
        }

        return nodes;
    }
    public static void main(String[] args){
//        getNodes();
//        getNamespaces();
        getPods();
    }


}
