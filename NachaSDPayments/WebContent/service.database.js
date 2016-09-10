//The DatabaseService service is solely responsible for making http requests to the backend of the app
app.service('DatabaseService', ['$http', function ($http) {
	this.callBackend = function(){
		/*
		 *$http.get() will go to the specified address and return the data found there.
		 *I'm using a library called Jersey to handle routing for these calls.
		 *The "/api" segment tells Jersey that it's going to handle this call.
		 *The "/data" segment specifies the class and "/test" specifies the method.
		 *These routing paths are *explicitly* declared in the Java class files using @Path
		*/
	    return $http.get("http://localhost:8080/NachaSDPayments/api/data/test");
	};
}]);