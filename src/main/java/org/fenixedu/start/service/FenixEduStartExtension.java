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
package org.fenixedu.start.service;

import com.google.common.collect.ImmutableMap;
import com.mitchellbosecke.pebble.extension.AbstractExtension;
import com.mitchellbosecke.pebble.extension.Filter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class FenixEduStartExtension extends AbstractExtension {

    @Override
    public Map<String, Filter> getFilters() {
        return ImmutableMap.of("path", new Filter() {
            @Override
            public Object apply(Object input, Map<String, Object> args) {
                if (input instanceof String) {
                    return ((String) input).replace('.', '/');
                }
                return input;
            }

            @Override
            public List<String> getArgumentNames() {
                return null;
            }
        }, "latest", new Filter() {
            @Override
            public Object apply(Object input, Map<String, Object> args) {
                if(input instanceof List) {
                    List<? extends Comparable> list = (List<? extends Comparable>) input;
                    Collections.sort(list, Comparator.<Comparable> reverseOrder());
                    return list.isEmpty() ? null : list.get(0);
                }
                return input;
            }

            @Override
            public List<String> getArgumentNames() {
                return null;
            }
        });
    }
}
