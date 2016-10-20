/*
 * This service acts as a kind of bridge between the LoginCtrl and HomeCtrl controllers.
 * Since they can't see each other's variables directly, we store whatever values we
 * want them to have in common here.
 */
app.service('UserService', [function () {
	var username;
	var userID;
	
	this.getUsername = function(){ return username; }
	this.setUsername = function(u){ username = u; }
	
	this.getUserId = function(){ return userID; }
	this.setUserId = function(i){ userID = i; }
}]);