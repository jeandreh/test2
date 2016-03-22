'use strict';

/**
 * @ngdoc function
 * @name cloudPosApp.controller:ProductsCtrl
 * @description
 * # AboutCtrl
 * Controller of the cloudPosApp
 */
angular.module('cloudPosApp')
  .controller('ProductsCtrl', [
    '$scope', '$modal', '$routeParams', '$location', 'productSvc', 'categorySvc', 'Flash',
    function ($scope, $modal, $routeParams, $location, productSvc, categorySvc, flash) {
     	
    $scope.products = [];
    $scope.categories = [];
//    $scope.searchText = null;
    $scope.categoryFilter = null;
    
    var promise = productSvc.fetchAll();
    promise.then(
        function(response) {
            $scope.products = response.data;
        },
        function(response) {
            flash.create('danger', 
    			'<b>Error loading product list from server</b><br/>' + 
    			'Details: ' + response.data, 10000, null, false);
            console.error(response.data);
        }
    );
    
    var promise = categorySvc.fetchAll();
    promise.then(
        function(response) {
            $scope.categories = response.data;
        },
        function(response) {
        	 flash.create('danger', 
     			'<b>Error loading categories list from server</b><br/>' + 
     			'Details: ' + response.data, 10000, null, false);
        	 console.error(response.data);
        }
    );
    
    $scope.setCategoryFilter = function (name) {
    	$scope.categoryFilter = name;
    };
    
    $scope.filterByCategory = function (product) {
    	var match = false;
    	if ($scope.categoryFilter == null) {
    		match = true;
    	}
    	else {
    		if (product.categories.length && 
    			_.findWhere(product.categories, {'name': $scope.categoryFilter})) {
    			match = true;
    		}
    	}
    	return match;
    };
    
}]);

/**
* $scope.product =
* {
*     "id": 1,
*     "name": "Black Coffee",
*     "imageUrl": "/imgs/black_coffee.jpg",
*     "retailOptions": [
*         {
*             "id": 1,
*             "name":"Large",
*             "short_name":"L",
*             "description":"Large black coffee (250 ml)",
*             "price":4.0
*         }
*     ],
*     "categories": [
*         {
*             "id":1,
*             "name": "Hot Drinks",
*             "description":"Try our delicious Coffees and Teas"
*         },
*         {
*             "id":3,
*             "name":"Favorites",
*             "description":"Bestseller products"
*         }
*     ],
*     "ingredients":[
*         {
*             "id":1,
*             "name":"Coffee Beans",
*             "unity":"kg"
*         }
*     ]
* };
**/