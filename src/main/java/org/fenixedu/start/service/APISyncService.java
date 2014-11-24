package org.fenixedu.start.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.fenixedu.start.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class APISyncService implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Map<String, List<Version>> versions;

    public Map<String, List<Version>> syncVersions() {
        logger.info("Synchronizing with the Github API");
        Map<String, List<Version>> versions = new HashMap<>();
        for (String project : Arrays.asList("bennu", "bennu-spring", "fenixedu-maven")) {
            try {
                ResponseEntity<String> resp = new RestTemplate().getForEntity("https://api.github.com/repos/FenixEdu/" + project + "/tags", String.class);
                JsonArray array = new JsonParser().parse(resp.getBody()).getAsJsonArray();
                List<Version> ownVersions = new ArrayList<>();
                versions.put(project, ownVersions);
                for (JsonElement element : array) {
                    ownVersions.add(Version.parse(element.getAsJsonObject().get("name").getAsString().substring(1)));
                }
                ownVersions.sort(Comparator.reverseOrder());
            } catch (ResourceAccessException e) {
                logger.warn("Could not determine versions for {} due to an exception: {}", project, e);
            }
        }
        return versions;
    }

    public Map<String, List<Version>> getVersions() {
        return versions;
    }

    public void reload() {
        this.versions = syncVersions();
        logger.info("Initialized module versions.");
        versions.forEach((project, versions) -> {
            logger.info("Module {} has versions {}", project, versions);
        });
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        reload();
    }
}
