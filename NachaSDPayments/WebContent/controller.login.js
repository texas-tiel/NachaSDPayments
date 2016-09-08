app.controller('LoginCtrl', function ($scope) {
	$scope.number = 0;
	
	$scope.myFunction = function(){
		$scope.number = Math.floor((Math.random() * 10) + 1);
	};
});