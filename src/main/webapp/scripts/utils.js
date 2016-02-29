function fetchFromObject(obj, prop) {
  if(typeof obj === 'undefined') {
    return false;
  }
  var offset = prop.indexOf('.')
  if(offset > -1) {
    return fetchFromObject(obj[prop.substring(0, offset)], prop.substr(offset + 1));
  }
  return obj[prop];
}

function execOnMatch(list1, prop1, list2, prop2, func) {
	if ( list1 && list2 && 
		list1.constructor === Array &&
		list2.constructor === Array) {
		for (var i = 0; i < list1.length; i++) {
		    for (var j = 0; j < list2.length; j++) {
		      if (list1[i][prop1] === list2[j][prop2]) {
		         func(list1[i],list2[j]);
		      }
		   }
		}
	}
	return list1;
}