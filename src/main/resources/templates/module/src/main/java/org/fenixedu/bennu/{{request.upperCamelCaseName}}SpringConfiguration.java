{% if request.ui == 'bennu-spring' %}
package org.fenixedu.bennu;

import org.fenixedu.bennu.spring.BennuSpringModule;

@BennuSpringModule(basePackages = "{{request.packageName}}", bundles = "{{request.upperCamelCaseName}}Resources")
public class {{request.upperCamelCaseName}}SpringConfiguration {
}
{% endif %}