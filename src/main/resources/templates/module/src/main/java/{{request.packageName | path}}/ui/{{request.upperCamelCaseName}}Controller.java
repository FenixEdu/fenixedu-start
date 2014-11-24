{% if request.ui == 'bennu-spring' %}
package {{request.packageName}}.ui;

import org.fenixedu.bennu.spring.portal.SpringApplication;
import org.fenixedu.bennu.spring.portal.SpringFunctionality;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/{{request.artifactId}}")
@SpringApplication(group = "logged", path = "{{request.artifactId}}", title = "title.{{request.upperCamelCaseName}}")
@SpringFunctionality(app = {{request.upperCamelCaseName}}Controller.class, title = "title.{{request.upperCamelCaseName}}")
public class {{request.upperCamelCaseName}}Controller {

    @RequestMapping
    public String home(Model model) {
        model.addAttribute("world", "World");
        return "{{request.artifactId}}/home";
    }

}
{% endif %}