app.controller('HomeCtrl', ['$scope', '$location', 'DatabaseService', 'UserService', function ($scope, $location, DatabaseService, UserService) {
	var redirect = checkUser();
	$scope.user;
	
	function checkUser(){
		if(UserService.getUsername() == null)
			$location.path('/');
		else
			$scope.user = UserService.getUsername();
	}
}]);