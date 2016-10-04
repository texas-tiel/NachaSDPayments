/*
 * Controller for the transactions (home) page of the app.
 */
app.controller('HomeCtrl', ['$scope', '$location', 'DatabaseService', 'UserService', function ($scope, $location, DatabaseService, UserService) {
	var redirect = checkUser();
	$scope.loading = true;
	$scope.user;
	$scope.transactions;
	
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
	
	$scope.fetchUserData = function(){
		$scope.loading = true;
		DatabaseService.getTransactions().success(function(result){
			$scope.loading = false;
			$scope.transactions = result;
		});
	};
	
	$scope.logout = function(){
		UserService.setUsername(null);
		$location.path('/');
	};
}]);