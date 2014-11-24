{% if request.ui == 'angular' %}
<div ng-app="{{request.upperCamelCaseName}}">
    <div ng-view>
        <div ng-controller="{{request.upperCamelCaseName}}Controller">
            <h1 ng-bind="text"></h1>
            <input type="text" ng-model="text"/>
        </div>
    </div>
    ${portal.angularToolkit()}
    <script type="text/javascript" src="${pageContext.request.contextPath}/{{request.artifactId}}/js/{{request.artifactId}}.js"></script>
</div>
{% endif %}