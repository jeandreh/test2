angular.module('cloudPosApp')
  .controller('CategoriesCtrl', [
    '$scope', '$modal', '$routeParams', '$location', 'categorySvc', 'Flash',
    function ($scope, $modal, $routeParams, $location, categorySvc, flash) {

     $scope.categories = [];
    	
	 var promise = categorySvc.fetchAll();
	    promise.then(
	        function(response) {
	            $scope.categories = response.data;
	        },
	        function(response) {
	        	 flash.create('danger', 
	        		'<b>Error loading categories list from server</b><br/>' + 
	        		'Details: ' + response.data, 0, null, false);
	        	 console.error(response.data);
	        }
	 );
	    
	 $scope.addCategory = function () {
		 $scope.categories.push({
			"name": null,
			"description": null
		 });
	 } 
	 
	 $scope.saveCategory = function (category) {
		 var index = _.indexOf($scope.categories, category);
		 var promise = categorySvc.create(category);
		    promise.then(
		        function(response) {
		            $scope.categories[index].id = response.data;
		        },
		        function(response) {
		        	 flash.create('danger', 
		        		'<b>Error saving category to the server</b><br/>' + 
		        		'Details: ' + response.data, 0, null, false);
		        	 console.error(response.data);
		        }
		 );
	 } 
    
	 $scope.removeCategory = function (category) {
		 var index = _.indexOf($scope.categories, category);
		 if (index != -1)
			 $scope.categories.splice(index, 1);
	 }
	 
}]);