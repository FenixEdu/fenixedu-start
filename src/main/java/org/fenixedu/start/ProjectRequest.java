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
package org.fenixedu.start;

import com.google.common.base.CaseFormat;

import java.util.List;

public class ProjectRequest {

    private String groupId;
    private String artifactId;
    private String name;
    private String packageName;
    private String mavenVersion;
    private String bennuVersion;
    private List<String> bennu;
    private String ui;
    private boolean generateDml;
    private String dbHost;
    private String dbName;
    private int dbPort;
    private String dbUser;
    private String dbPassword;

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getMavenVersion() {
        return mavenVersion;
    }

    public void setMavenVersion(String mavenVersion) {
        this.mavenVersion = mavenVersion;
    }

    public String getBennuVersion() {
        return bennuVersion;
    }

    public void setBennuVersion(String bennuVersion) {
        this.bennuVersion = bennuVersion;
    }

    public List<String> getBennu() {
        return bennu;
    }

    public void setBennu(List<String> bennu) {
        this.bennu = bennu;
    }

    public String getUi() {
        return ui;
    }

    public void setUi(String ui) {
        this.ui = ui;
    }

    public void setGenerateDml(boolean generateDml) {
        this.generateDml = generateDml;
    }

    public boolean isGenerateDml() {
        return generateDml;
    }

    public String getDbHost() {
        return dbHost;
    }

    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public int getDbPort() {
        return dbPort;
    }

    public void setDbPort(int dbPort) {
        this.dbPort = dbPort;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getUpperCamelCaseName() {
        return CaseFormat.LOWER_HYPHEN.converterTo(CaseFormat.UPPER_CAMEL).convert(artifactId);
    }
}
