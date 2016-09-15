/*
 * Controller for the transactions (home) page of the app.
 */
app.controller('HomeCtrl', ['$scope', '$location', 'DatabaseService', 'UserService', function ($scope, $location, DatabaseService, UserService) {
	var redirect = checkUser();
	$scope.loading = true;
	$scope.user;
	$scope.transactions;
	
	/* Because this function is set equal to the value of redirect (above), it
	 * is forced to run when the page is loaded. This probably isn't the best way to
	 * do this, but it's the only way I've found that reliably works. If the user hasn't
	 * logged in, they're redirected back to the login page via $location.path(). If they
	 * have logged in, their username is set and their transactions are loaded from the
	 * "database" (an ArrayList in the backend).
	 */
	function checkUser(){
		if(UserService.getUsername() == null)
			$location.path('/');
		else{
			$scope.user = UserService.getUsername();
			DatabaseService.getTransactions().success(function(result){
				$scope.loading = false;
				$scope.transactions = result;
			});
		}
	};
	
	$scope.logout = function(){
		UserService.setUsername(null);
		$location.path('/');
	};
}]);