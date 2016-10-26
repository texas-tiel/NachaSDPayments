/*
 * Controller for the transactions (home) page of the app.
 */
app.controller('TransFormCtrl', ['$scope', '$location', 'DatabaseService', 'UserService', function ($scope, $location, DatabaseService, UserService) {
	var redirect = checkUser();
	var today = getCurrDate();
	$scope.paymentType = 0;
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
				$scope.accounts = result;
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
		alert("Form submitted.");
		DatabaseService.sendForm($scope.transInfo).success(function(result){
			alert("Sent successfully.");
			$location.path('/home');
		}).error(function(){
			alert("There was an error sending the form.");
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