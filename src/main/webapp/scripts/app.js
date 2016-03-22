'use strict';

angular
.module('cloudPosApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'isteven-multi-select',
    'ngTouch',
    'ngMessages',
    'mgcrea.ngStrap',
    'flow',
    'ngFlash',
    'ui.grid',
    'ui.grid.edit',
    'ui.grid.rowEdit',
    'ui.grid.cellNav',
    'ui.grid.validate'
])
.config([ '$routeProvider', function ($routeProvider) {
  $routeProvider
    .when('/', {
      templateUrl: 'views/main.html',
      controller: 'MainCtrl'
    })
    .when('/sales', {
      templateUrl: 'views/sales.html',
      controller: 'SalesCtrl'
    })
    .when('/products', {
      templateUrl: 'views/products.html',
      controller: 'ProductsCtrl'
    })
    .when('/products/new', {
      templateUrl: 'views/product-edit.html',
      controller: 'ProductEditCtrl'
    })
    .when('/products/:id', {
      templateUrl: 'views/product-edit.html',
      controller: 'ProductEditCtrl'
    })
    .when('/categories', {
      templateUrl: 'views/categories.html',
      controller: 'CategoriesCtrl'
    })
    .when('/supplies', {
      templateUrl: 'views/supplies.html',
      controller: 'SuppliesCtrl'
    })
    .otherwise({
      redirectTo: '/'
    });
}]);
