/*
 * Controller for the transactions (home) page of the app.
 */
app.controller('TransFormCtrl', ['$scope', '$location', 'DatabaseService', 'UserService', function ($scope, $location, DatabaseService, UserService) {
	var redirect = checkUser();
	var today = getCurrDate();
	$scope.paymentType = 0;
	
	$scope.transInfo = {
		date: today,
		amount: 0.00
	};

	function checkUser(){
		if(UserService.getUsername() == null)
			$location.path('/');
	};
	
	function getCurrDate() {
	    var local = new Date();
	    local.setMinutes(0);
	    return local;
	};
	
	$scope.setAmount = function(){
		if($scope.paymentType == 1){
			var temp = (Math.random().toFixed(3)*1000);// + Math.random().toFixed(2);
			$scope.transInfo.amount = temp;
		}
		else 
			$scope.transInfo.amount = 0.00;	
	};
	
	$scope.validateForm = function(){
		alert("Form submitted.");
		DatabaseService.sendForm($scope.transInfo).success(function(result){
			alert("Sent successfully.");
		}).error(function(){
			alert("There was an error sending the form.");
		});
	};
	
	$scope.cancelTrans = function(){ $location.path('/home'); }
	
	$scope.logout = function(){
		UserService.setUsername(null);
		$location.path('/');
	};
}]);