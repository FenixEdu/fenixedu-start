/**
 * Copyright 2018 Instituto Superior Técnico
 * This project is part of the FenixEdu Project: https://fenixedu.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fenixedu.start.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.fenixedu.start.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class APISyncService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Map<String, List<Version>> versions;

    public Map<String, List<Version>> syncVersions() {
        logger.info("Synchronizing with the Github API");
        Map<String, List<Version>> versions = new HashMap<>();
        for (String project : Arrays.asList("bennu", "bennu-spring", "fenixedu-maven")) {
            try {
                ResponseEntity<String> resp = new RestTemplate()
                        .getForEntity("https://api.github.com/repos/FenixEdu/" + project + "/tags", String.class);
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

    @Scheduled(fixedRateString = "${reload.versions.timer.in.milliseconds:3600000}")
    public void reload() {
        this.versions = syncVersions();
        logger.info("Initialized module versions.");
        versions.forEach((project, versions) -> {
            logger.info("Module {} has versions {}", project, versions);
        });
    }

}
