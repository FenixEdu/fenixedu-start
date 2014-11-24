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
