/** 
 * Navigation and routing for module ljprojectbuilderApp.
 */
angular.module('ljprojectbuilderApp').config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/viewcomponents/project-all/'});
}]);
