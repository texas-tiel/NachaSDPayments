/*
 * Controller for the transactions (home) page of the app.
 */
app.controller('HomeCtrl', ['$scope', '$location', '$interval', 'DatabaseService', 'UserService', function ($scope, $location, $interval, DatabaseService, UserService) {
	var redirect = checkUser();
	$scope.loading = true;
	$scope.databaseFailure = false;
	$scope.user;
	$scope.completedTrans = [];
	$scope.pendingTrans = [];
	
	//Called whenever the page is loaded
	//If there's no user loaded into the UserService module, then redirect to the login screen
	//If there is a user, pull their transaction information via AJAX call
	function checkUser(){
		if(UserService.getUsername() == null)
			$location.path('/');
		else{
			$scope.user = UserService.getUsername();
			fetchUserData();
		}
	};
	
	//Makes AJAX call to the backend to fetch user's transaction data
	function fetchUserData(){
		$scope.completedTrans = [];
		$scope.pendingTrans = [];
		$scope.loading = true;
		$scope.databaseFailure = false;
		
		//AJAX call
		DatabaseService.updatePending(UserService.getUserId()).success(function(result){
			if(result.message != ""){
				if(result.message.indexOf("success") != -1)
					toastr.success(result.message);
				else
					toastr.warning(result.message);
			}
			
			sortTransactions(result.trans);
			$scope.loading = false;
		}).error(function(){
			$scope.databaseFailure = true;
		});
	};
	
	$interval(fetchUserData, 60000); //reloads every minute
	
	//Sorts the passed list of transactions into 'pending' and 'success/fail' lists
	function sortTransactions(list){
		for(var i = 0; i < list.length; i++){
			var temp = list[i];
			if(temp.status != 'Pending'){
				$scope.completedTrans.push(temp);
			}else{
				$scope.pendingTrans.push(temp);
			}
		}
	};
	
	//Returns true if a list's length is longer than 0
	$scope.hasElements = function(list){ return list.length > 0; };
	
	//Redirects to the new transactions form
	$scope.startNewTrans = function(){ $location.path('/home/new_transaction'); }
	
	//Logs user out
	$scope.logout = function(){
		UserService.setUsername(null);
		$location.path('/');
	};
}]);