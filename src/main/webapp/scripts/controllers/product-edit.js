'use strict';

/**
 * @ngdoc function
 * @name cloudPosApp.controller:ProductsCtrl
 * @description
 * # AboutCtrl
 * Controller of the cloudPosApp
 */
angular.module('cloudPosApp')
  .controller('ProductEditCtrl', [
    '$scope', '$modal', '$routeParams', '$window', '$location', 'productSvc', 'categorySvc', 'supplySvc',
    function ($scope, $modal, $routeParams, $window, $location, productSvc, categorySvc, supplySvc) {

    $scope.categories = [];

    $scope.ingredients = [];

    $scope.selectedIngredients = [];

    $scope.selectedCategories = [];

    $scope.categoriesLocalLang = {
        selectAll       : 'Select All',
        selectNone      : 'Clear All',
        reset           : 'Reset',
        search          : 'Search...',
        nothingSelected : 'Categories...'
    };

    $scope.ingredientsLocalLang = {
        selectAll       : 'Select All',
        selectNone      : 'Clear All',
        reset           : 'Reset',
        search          : 'Search...',
        nothingSelected : 'Ingredients...'
    };
    
    $scope.option = {
    	"name" : null,
    	"shortName" : null,
    	"description" : null,
    	"price" : null,
    	"compositions" : []
    };

    var ingredientModal = $modal({
        scope: $scope,
        templateUrl: "views/templates/ingredient-modal.html",
        show: false
    });

    var optionModal = $modal({
        scope: $scope,
        templateUrl: "views/templates/option-modal.html",
        show: false
    });

    var DEFAULT_IMG_URL = "http://www.placehold.it/150x150/EFEFEF/AAAAAA&amp;text=add+image";
    
    // load categories and supplies from backend-server. TODO implement local cache
    var promise = categorySvc.fetchAll();
    promise.then(
    		function(response) {
                $scope.categories = response.data;
            },
            function(response) {
            	//TODO handle error
                console.log(response.data);
            }
    );
    
    var promise = supplySvc.fetchAll();
    promise.then(
    		function(response) {
                $scope.ingredients = response.data;
            },
            function(response) {
            	//TODO handle error
                console.log(response.data);
            }
    );
    
    if ($location.path().indexOf('/new') != -1) {
        handleProductCreation();
    }
    else if ($routeParams['id'] != null) {
        handleProductUpdate($routeParams['id']);
    }
    else {
        handleError();
    }

    function handleProductCreation() {
        $scope.product = {
            "name": null,
            "imageUrl": DEFAULT_IMG_URL,
            "retailOptions": [],
            "categories": [],
            "ingredients": []
        };
    }

    function handleProductUpdate(id) {
        var promise = productSvc.fetch(id);
        promise.then(
            function(response) {
                $scope.product = response.data;
                tickCategories();
                tickIngredients();
            },
            function(response) {
                console.log(response.data);
            }
        );
    }

    function handleError() {
        console.log("Invalid Request");
        console.log($location);
        console.log($routeParams);
    }

    function tickCategories() {
        execOnMatch(
            $scope.categories,'name',
            $scope.product.categories, 'name',
            function(match) {
                match.ticked = true;
            }
        );
    }

    function tickIngredients() {
        execOnMatch(
            $scope.ingredients, 'name',
            $scope.product.ingredients, 'name',
            function(match) {
                match.ticked = true;
            }
        );
    }

    function saveSelectedIngredients() {
        execOnMatch(
            $scope.ingredients, 'name',
            $scope.selectedIngredients, 'name',
            function(match) {
                match.ticked = true;
            }
        );
    }

    $scope.showIngredientModal = function () {
    	saveSelectedIngredients();
    	ingredientModal.$promise.then(ingredientModal.show);
    };

    $scope.showOptionModal = function () {
    	saveSelectedIngredients();
        optionModal.$promise.then(optionModal.show);
    };

    $scope.deselectIngredient = function (index, ingred) {
        $scope.selectedIngredients.splice(index,1);
        _.map($scope.ingredients, function(item) {
            if (item.name === ingred.name) {
                item.ticked = false;
            }
        });
        saveSelectedIngredients();
    };

    $scope.addIngredient = function (ingred) {
        if (!_.findWhere($scope.ingredients, { "name": ingred.name })) {
            // add ingredient to ingredients list
            ingred.ticked = true;
            $scope.ingredients.push(ingred);
            // hide modal
            ingredientModal.hide();
        }
        else {
            // TODO handle error message
        }
    };

    $scope.addOption = function (option) {
        if (!_.findWhere($scope.product.retailOptions, { "name": option.name })) {
            $scope.product.retailOptions.push(option);
            // hide modal
            optionModal.hide();
            // reset option object
            $scope.option = {
	        	"name" : null,
	        	"shortName" : null,
	        	"description" : null,
	        	"price" : null,
	        	"compositions" : []
            };
        }
        else {
            // TODO handle error message
        }
    };

    $scope.saveProduct = function() {
    	
    	if ($scope.product.imageUrl === DEFAULT_IMG_URL) {
    		$scope.product.imageUrl = null;
    	}
    	
    	for (var i = 0; i < $scope.selectedIngredients.length; i++) {
    		var ingred = $scope.selectedIngredients[i];
    		$scope.product.ingredients.push(
				{
					"id": ingred.id,
					"name": ingred.name,
					"unity": (ingred.unity ? ingred.unity : null)
				}
    		);
    	}
    	
    	for (var i = 0; i < $scope.selectedCategories.length; i++) {
    		var category = $scope.selectedCategories[i];
    		$scope.product.categories.push(
				{
					"id": category.id,
					"name": category.name,
				}
    		);
    	}
    	
        var promise = productSvc.create($scope.product);
        promise.then(
            function(response) {
                $scope.product.id = response.data;
            },
            function(response) {
            	// TODO handle error.
                console.log(response.data);
            }
        );
        console.log($scope.product);
        $window.location.href = "#/products";
    };
    
    $scope.handleUploadError = function (file, msg) {
    	// TODO show error message
    	console.log(file);
    	console.log(msg);
    };
    
    $scope.handleUploadSuccess = function (file, msg) {
    	// TODO show error message
    	$scope.product.imageUrl = msg;
    	console.log($scope.product);
    };
    
    $scope.clearImage = function () {
    	// TODO show error message
    	$scope.product.imageUrl = DEFAULT_IMG_URL;
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