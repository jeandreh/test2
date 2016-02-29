angular.module('cloudPosApp')
  .directive('unique', function() {
  return {
    restrict: 'A',
    require: 'ngModel',
    link: function(scope, elm, attrs, ctrl) {
      ctrl.$validators.unique = function(modelValue, viewValue) {
        if(_.findWhere(fetchFromObject(scope,attrs['unique']), { name: viewValue })) {
          return false;
        }
          return true;
        }
      }
  };
});
