var directives = {};

directives.focus = ['$timeout', '$parse', function($timeout, $parse) {
  focus = {
    restrict: 'A',
    link: function(scope, element, attrs) {
      scope.$watch(attrs.focus, function(newValue, oldValue) {
          if (newValue) { element[0].focus(); }
      });
      element.bind("blur", function(e) {
          $timeout(function() {
              scope.$apply(attrs.focus + "=false"); 
          }, 0);
      });
      element.bind("focus", function(e) {
          $timeout(function() {
              scope.$apply(attrs.focus + "=true");
          }, 0);
      })
    }
  }
  return focus;
}];
