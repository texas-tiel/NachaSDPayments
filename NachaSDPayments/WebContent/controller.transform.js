/*
 * Controller for the transactions (home) page of the app.
 */
app.controller('TransFormCtrl', ['$scope', '$location', 'DatabaseService', 'UserService', function ($scope, $location, DatabaseService, UserService) {
	var redirect = checkUser();
	var today = getCurrDate();
	$scope.paymentType = 0;
	var masterList = [];
	$scope.accounts = [];
	
	$scope.transInfo = {
		date: today,
		amount: 0.00
	};
	
	//Reroutes user if they're not logged in
	function checkUser(){
		if(UserService.getUsername() == null)
			$location.path('/');
		else
			DatabaseService.getAccounts(UserService.getUserId()).success(function(result){
				masterList = JSON.parse(JSON.stringify(result));
				$scope.accounts = result;
				for(var i = 0; i < result.length; i++){
					var temp = $scope.accounts[i].account;
					for(var j = 0; j < temp.length-4; j++)
						temp = temp.substring(0,j) + "x" + temp.substring(j+1, temp.length);
					$scope.accounts[i].account = temp;
				}
			});
	};
	
	//Gets today's date
	function getCurrDate() {
	    var local = new Date();
	    local.setMinutes(0);
	    return local;
	};
	
	//Sets the amount in the final box when payment type is selected in the dropdown
	$scope.setAmount = function(){
		if($scope.paymentType == 1){
			var temp = (Math.random().toFixed(3)*1000);// + Math.random().toFixed(2);
			$scope.transInfo.amount = temp;
		}
		else 
			$scope.transInfo.amount = 0.00;	
	};
	
	//Called when the "submit" button is pressed
	$scope.validateForm = function(){
		$scope.transInfo.id = UserService.getUserId();
		$scope.transInfo.account = masterList[$scope.transInfo.account].account; 
		DatabaseService.sendForm($scope.transInfo).success(function(result){
			var confirmationNum = Math.floor(Math.random()*200000)+100000;// + Math.random().toFixed(2);
			
			toastr.success('Transaction successful. Confirmation #: ' + confirmationNum);
			if(result == true)
				toastr.success('Your transaction will be processed using NACHA same day payment.');
			
			$location.path('/home');
		}).error(function(){
			toastr.error('Transaction failed');
		});
	};
	
	//Called when the "cancel" button is pressed
	$scope.cancelTrans = function(){ $location.path('/home'); }
	
	//Logs user out
	$scope.logout = function(){
		UserService.setUsername(null);
		$location.path('/');
	};
}]);