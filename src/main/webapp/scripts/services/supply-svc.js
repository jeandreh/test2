'use strict';

/**
 * @ngdoc service
 * @name cloudPosApp.supplySvc
 * @description
 * # productSvc
 * Fetches a list with the supplies from the backend server and caches it in the Local Storage.
 */
angular.module('cloudPosApp')
  .factory('supplySvc', [ '$http', '$q', '$window', function ($http, $q, $window) {

	return {

		server_end_point: $window.location.origin + $window.location.pathname + 'rest/supplies',

		fetchAll: function() {
			var url = this.server_end_point;
			return $http.get(url);
 		},
 		fetch: function(id) {
			var url = this.server_end_point + '/' + id;
			return $http.get(url);
 		},
 		create: function(supply) {
			var url = this.server_end_point;
			return $http.post(url, supply);
 		},
	   	update: function(supply) {
	  		var url = this.server_end_point + '/' + supply.id;
	  		return $http.put(url, supply);
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