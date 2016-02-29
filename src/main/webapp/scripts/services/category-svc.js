'use strict';

/**
 * @ngdoc service
 * @name cloudPosApp.categorySvc
 * @description
 * # categorySvc
 * Fetches a list with the categories from the backend server and caches it in the Local Storage.
 */
angular.module('cloudPosApp')
  .factory('categorySvc', [ '$http', '$q', '$window', function ($http, $q, $window) {

	return {

		server_end_point: $window.location.origin + $window.location.pathname + 'rest/categories',

		fetchAll: function() {
			var url = this.server_end_point;
			return $http.get(url);
 		},
 		fetch: function(id) {
			var url = this.server_end_point + '/' + id;
			return $http.get(url);
 		},
 		create: function(category) {
			var url = this.server_end_point;
			return $http.post(url, category);
 		},
	   	update: function(category) {
	  		var url = this.server_end_point + '/' + category.id;
	  		return $http.put(url, category);
	   	}

	};

  	// function saveInLocalStorage(prodList) {
   //  	localStorage['cloudPosApp.productList'] = JSON.stringify(prodList);
   //  }

    // function loadFromServer(/*rev*/) {

    	// TODO this list will first come from the backend server, beeing cached in Local Storage. Based on the
    	/*
    	var prodList = {
	    	revision: 0,
	    	revisionDate: 0,
	    	products: {
			   '00001':
			   {
				   code : '00001',
				   name : 'Black Coffe',
				   price : 2.75,
				   image : 'images/black_coffee.jpg'
			   },
			   '00002':
			   {
				   code : '000002',
				   name : 'White Coffee',
				   price : 3.00,
				   image : 'images/white_coffee.jpg'
			   },
			   '00003':
			   {
				   code : '000003',
				   name : 'Capuccino',
				   price : 4.00,
				   image : 'images/capuccino.jpg'
			   },
			   '00004':
			   {
				   code : '000004',
				   name : 'Mocca',
				   price : 5.00,
				   image : 'images/moccacino.jpg'
			   },
			   '00005':
			   {
				   code : '000005',
				   name : 'Hot Chocolate',
				   price : 5.50,
				   image : 'images/hot_chocolate.jpg'
			   }
			}*/
		// }

		// saveInLocalStorage(prodList);
		// 	return prodList;
  //   }

  //   function loadFromLocalStorage() {
  //   	return JSON.parse(localStorage['cloudPosApp.productList']);
  //   }

  //  function load() {
  // 	if (localStorage['cloudPosApp.productList']) {
  // 		prodList = loadFromLocalStorage();
  // 		// TODO load new products from server asyncronously
  // 	}
  // 	else {
  //   	prodList = fetch;
		// }
  // 	return prodList;
  // }

  }]);