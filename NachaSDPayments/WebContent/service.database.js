/*
 *$http.post() will go to the specified address and return the data found there.
 *The "/api" segment tells Jersey that it's going to handle this call.
 *The "/data" segment specifies the class and "/test" specifies the method.
 *These routing paths are *explicitly* declared in the Java class files using @Path
 */
app.service('DatabaseService', ['$http', '$location', function ($http, $location) {
	var baseUrl = getBase($location.absUrl());
	
	//Checks username/password to see if they are a valid login combo in the database
	this.verifyLogin = function(info){
		return $http.post(baseUrl + "api/data/user", info);
	};
	
	//Fetches all transactions currently stored in the database for a specific user
	this.getTransactions = function(id){
	    return $http.post(baseUrl + "api/data/history", id);
	};
	
	this.getAccounts = function(id){
		return $http.post(baseUrl + "api/data/accountinfo", id);
	};
	
	//Processes new transaction form information into a NACHA file
	this.sendForm = function(form){
		return $http.post(baseUrl + "api/data/newtran", form);
	};
	
	//Returns a substring portion of the app's url
	function getBase(url){
		var index = url.indexOf("#");
		return url.substring(0, index);
	}
}]);