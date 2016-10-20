/*
 * Controller for the transactions (home) page of the app.
 */
app.controller('HomeCtrl', ['$scope', '$location', 'DatabaseService', 'UserService', function ($scope, $location, DatabaseService, UserService) {
	var redirect = checkUser();
	$scope.loading = true;
	$scope.databaseFailure = false;
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
			}).error(function(){
				$scope.databaseFailure = true;
			});
		}
	};
	
	$scope.fetchUserData = function(){
		$scope.completedTrans = [];
		$scope.pendingTrans = [];
		$scope.loading = true;
		$scope.databaseFailure = false;
		DatabaseService.getTransactions().success(function(result){
			$scope.loading = false;
			sortTransaction(result);
		}).error(function(){
			$scope.databaseFailure = true;
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
	
	$scope.hasElements = function(list){ return list.length > 0; };
	
	$scope.startNewTrans = function(){ $location.path('/home/new_transaction'); }
	
	$scope.logout = function(){
		UserService.setUsername(null);
		$location.path('/');
	};
}]);