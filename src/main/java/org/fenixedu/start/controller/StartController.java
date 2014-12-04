/**
 * Copyright 2014 Instituto Superior Técnico
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
package org.fenixedu.start.controller;

import com.mitchellbosecke.pebble.error.PebbleException;
import org.fenixedu.start.ProjectRequest;
import org.fenixedu.start.Version;
import org.fenixedu.start.service.APISyncService;
import org.fenixedu.start.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class StartController {

    private final APISyncService syncService;
    private final ProjectService projectService;

    @Autowired
    public StartController(APISyncService syncService, ProjectService projectService) {
        this.syncService = syncService;
        this.projectService = projectService;
    }

    @ModelAttribute("versions")
    public Map<String, List<Version>> versions() {
        return syncService.getVersions();
    }

    @RequestMapping("/")
    public String welcome() {
        return "welcome";
    }

    @RequestMapping("/webapp")
    public String webapp() {
        return "webapp";
    }

    @RequestMapping("/module")
    public String module() {
        return "module";
    }

    @RequestMapping(value = "/webapp.txt", method = RequestMethod.POST)
    @ResponseBody String webappText(@ModelAttribute ProjectRequest request) throws IOException, PebbleException {
        return projectService.buildWebapp(request).entrySet().stream().map(
                entry -> "Path: " + entry.getKey() + "\nValue:\n" + new String(entry.getValue())).collect(
                Collectors.joining("\n\n---------\n\n"));
    }

    @RequestMapping(value = "/webapp.zip", method = RequestMethod.POST)
    @ResponseBody ResponseEntity<byte[]> webappZip(@ModelAttribute ProjectRequest request) throws IOException, PebbleException {
        return build(projectService.buildWebapp(request), request);
    }

    @RequestMapping(value = "/module.txt", method = RequestMethod.POST)
    @ResponseBody String moduleText(@ModelAttribute ProjectRequest request) throws IOException, PebbleException {
        return projectService.buildModule(request).entrySet().stream().map(
                entry -> "Path: " + entry.getKey() + "\nValue:\n" + new String(entry.getValue())).collect(
                Collectors.joining("\n\n---------\n\n"));
    }

    @RequestMapping(value = "/module.zip", method = RequestMethod.POST)
    @ResponseBody ResponseEntity<byte[]> moduleZip(@ModelAttribute ProjectRequest request) throws IOException, PebbleException {
        return build(projectService.buildModule(request), request);
    }

    private ResponseEntity<byte[]> build(Map<String, byte[]> project, ProjectRequest request) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(stream);

        for (Map.Entry<String, byte[]> mapEntry : project.entrySet()) {
            ZipEntry entry = new ZipEntry(mapEntry.getKey());
            zip.putNextEntry(entry);
            zip.write(mapEntry.getValue());
            zip.closeEntry();
        }

        zip.close();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/zip");
        headers.add("Content-Disposition", "attachment; filename=\"" + request.getArtifactId() + "\"");
        return new ResponseEntity<>(stream.toByteArray(), headers, HttpStatus.OK);
    }

}
