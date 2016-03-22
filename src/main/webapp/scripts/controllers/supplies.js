angular.module('cloudPosApp')
.controller('SuppliesCtrl', [
            '$scope', 
            '$modal', 
            '$routeParams', 
            '$location',
            '$timeout',
            'supplySvc', 
            'Flash',
            '$q',
            'uiGridValidateService',
function ($scope, 
    		$modal, 
    		$routeParams, 
    		$location, 
    		$timeout,
    		supplySvc, 
    		flash,
    		$q,
    		uiGridValidateService
){
     
     $scope.errors = [];
            	
     $scope.gridOptions = {
         enableSorting: false,
         columnDefs: [
           {	
        	   name:'Name', 
        	   field:'name' , 
        	   validators:{required:true, minLength:3, maxLength:25}, 
        	   cellTemplate: 'ui-grid/cellTitleValidator', 
        	   enableCellEditOnFocus:true,
        	   cellClass: function(grid, row, col) {
        	      if (grid.getCellValue(row,col) === null) {
        	         return 'ui-grid-empty-cell';
        	      }
        	   },
        	   width: '30%'
           },
           {
        	   name:'Unity', 
        	   field: 'unity',  
        	   validators:{required:true, minLength:1, maxLength:2},  
        	   cellTemplate: 'ui-grid/cellTitleValidator', 
        	   enableCellEditOnFocus:true,
        	   width: '20%'
           },
           {
        	   name:'Price', 
        	   field: 'price',
        	   type: 'number',
///        	   validators:{minLength:1, maxLength:2}, // TODO number validator
        	   cellTemplate: 'ui-grid/cellTitleValidator', 
        	   enableCellEditOnFocus:true,
        	   width: '20%'
           },
           {
        	   name:'Stock', 
        	   field: 'stock',
        	   type: 'number',
//        	   validators:{minLength:1, maxLength:2},  // TODO number validator
        	   cellTemplate: 'ui-grid/cellTitleValidator', 
        	   enableCellEditOnFocus:true,
        	   width: '20%'
           },
           {
        	   name: 'Remove',
        	   width: '10%',
        	   enableCellEdit: false,
        	   cellTemplate: 
        		   '<div class="ui-grid-cell-contents" title="TOOLTIP">'+
        		   	'<span class="glyphicon glyphicon-remove-circle" style="cursor:pointer" ng-click="grid.appScope.removeSupply(row.entity)"></span>'+
        		   '</div>'
           }
         ],
         data: [], // to be filled with categories
 		 onRegisterApi: function (gridApi) {
 			 $scope.gridApi = gridApi;
 			 gridApi.validate.on.validationFailed($scope,function(rowEntity, colDef, newValue, oldValue){
 				 var field = colDef.field;
 				 $scope.errors.push({ 
 					 'field' : field, 
 					 'message' : uiGridValidateService.getErrorMessages(rowEntity,colDef)[0] 
 				 });
 	         });
 			 gridApi.rowEdit.on.saveRow($scope, function(rowEntity) {
 				 var promise = null;
 				 if (rowEntity['$$invalid' + 'Name'] || rowEntity['$$invalid' + 'Unity']) {
 					 // if validation failed, cancel row persistence
 					 var cancelEditPromise = $q.defer();
 					 $timeout(function() {
 						cancelEditPromise.reject();
 						console.log("Saving cancelled due to validation errors");
 					 });
 					 promise = cancelEditPromise.promise;
 				 }
 				 else {
 					$scope.errors = [];
 					promise = $scope.saveSupply(rowEntity);
 				 }
 				 gridApi.rowEdit.setSavePromise(rowEntity, promise);
 			});
 		 }
     };
     
	 var promise = supplySvc.fetchAll();
	 promise.then(
        function(response) {
            $scope.gridOptions.data = response.data;
        },
        function(response) {
        	 flash.create('danger', 
        		'<b>Error loading categories list from server</b><br/>' + 
        		'Details: ' + response.data, 10000, null, false);
        	 console.error(response.data);
        }
	 );
	    
	 $scope.addSupply = function () {
		 var last = $scope.gridOptions.data.length - 1;
		 if ($scope.gridOptions.data[last].name != null && !$scope.errors.length) {
			 $scope.gridOptions.data.push({'name': null, 'unity': null, 'price': null, 'stock': null});
			 $timeout(function () { 
				$scope.gridApi.cellNav.scrollToFocus(
					$scope.gridOptions.data[++last],
					$scope.gridOptions.columnDefs[0]);
			 });
		 }
		 else {
			 $scope.gridApi.rowEdit.setRowsDirty([$scope.gridOptions.data[last]]);
		 }
	 } 
	 
	 $scope.saveCategory = function (supply) {
		 var index = _.indexOf($scope.gridOptions.data, supply);
		 var promise = categorySvc.create(supply);
		 promise.then(
	        function(response) {
	            $scope.gridOptions.data[index].id = response.data;
	            console.log('Supply {' + supply.name + '} saved with id=' + response.data);
	        },
	        function(response) {
	        	$scope.errors = response.data;
	        }
		 );
		 return promise;
	 } 
    
	 $scope.removeSupply = function (supply) {
		 // TODO persist removal to server
		 var index = _.indexOf($scope.gridOptions.data, supply);
		 if (index != -1)
			 $scope.gridOptions.data.splice(index, 1);
	 }
	 
}]);