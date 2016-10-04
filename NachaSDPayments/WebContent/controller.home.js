/*
 * Controller for the completedTrans (home) page of the app.
 */
app.controller('HomeCtrl', ['$scope', '$location', 'DatabaseService', 'UserService', function ($scope, $location, DatabaseService, UserService) {
	var redirect = checkUser();
	$scope.loading = true;
	$scope.user;
	$scope.completedTrans = [];
	$scope.pendingTrans = [];
	
	function checkUser(){
		if(UserService.getUsername() == null)
			$location.path('/');
		else{
			$scope.user = UserService.getUsername();
			DatabaseService.getTransactions().success(function(result){
				$scope.loading = false;
				sortTransaction(result);

			});
		}
	};
	
	$scope.fetchUserData = function(){
		$scope.completedTrans = [];
		$scope.pendingTrans = [];
		$scope.loading = true;
		DatabaseService.getTransactions().success(function(result){
			$scope.loading = false;
			sortTransaction(result);
			
		});
	};
	
	function sortTransaction(list){
		for(var i = 0; i < list.length; i++){
			var temp = list[i];
			if(temp.status != 'Pending'){
				$scope.completedTrans.push(temp);
			}else{
				$scope.pendingTrans.push(temp);
			}
		}
	};
	
	$scope.logout = function(){
		UserService.setUsername(null);
		$location.path('/');
	};
}]);