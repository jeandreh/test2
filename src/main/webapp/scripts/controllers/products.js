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
    '$scope', '$modal', '$routeParams', '$location', 'productSvc', 'categorySvc',
    function ($scope, $modal, $routeParams, $location, productSvc, categorySvc) {
     	
    $scope.products = [];
    $scope.categories = [];
    //$scope.searchText = "";
    //$scope.categoryFilter = "";
    
    var promise = productSvc.fetchAll();
    promise.then(
        function(response) {
            $scope.products = response.data;
        },
        function(response) {
        	//TODO handle error messsages
            console.log(response.data);
        }
    );
    
    var promise = categorySvc.fetchAll();
    promise.then(
        function(response) {
            $scope.categories = response.data;
        },
        function(response) {
        	//TODO handle error messsages
            console.log(response.data);
        }
    );
    
    $scope.setFilter = function (category) {
    	$scope.categoryFilter = category.name;
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