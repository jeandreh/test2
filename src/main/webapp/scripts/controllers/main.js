'use strict';

/**
 * @ngdoc function
 * @name cloudPosApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the cloudPosApp
 */
angular.module('cloudPosApp')
  .controller('MainCtrl', [ '$scope', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  }]);
