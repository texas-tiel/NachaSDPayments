app.service('UserService', [function () {
	var username;
	
	this.getUsername = function(){ return username; }
	this.setUsername = function(u){ username = u; }
}]);