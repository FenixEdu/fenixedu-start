{% if request.ui == 'angular' %}
var {{request.upperCamelCaseName}} = angular.module('{{request.upperCamelCaseName}}', ['bennuToolkit']);

{{request.upperCamelCaseName}}.controller('{{request.upperCamelCaseName}}Controller', ['$scope', function($scope) {
    $scope.text = "Hello world!";
}]);
{% endif %}