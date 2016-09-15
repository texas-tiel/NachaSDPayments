//The DatabaseService service is solely responsible for making HTTP requests to the backend of the app
app.service('DatabaseService', ['$http', '$location', function ($http, $location) {
	var baseUrl = getBase($location.absUrl());
	
	this.getTransactions = function(){
		/*
		 *$http.post() will go to the specified address and return the data found there.
		 *I'm using a library called Jersey to handle routing for these calls.
		 *The "/api" segment tells Jersey that it's going to handle this call.
		 *The "/data" segment specifies the class and "/test" specifies the method.
		 *These routing paths are *explicitly* declared in the Java class files using @Path
		 *Unlike GET, POST can easily carry variables to the backend. Also, it's more secure, which
		 *is the main reason I'm using it.
		*/
	    return $http.post(baseUrl + "api/data/history");
	};
	
	function getBase(url){
		var index = url.indexOf("#");
		return url.substring(0, index);
	}
}]);