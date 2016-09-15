/*
 * Angular injections allow you to use other AngularJS modules/controllers/services/etc in your
 * code. In this controller, I'm injecting $scope and the DatabaseService service. I don't know
 * everything that $scope does, but what's really important about it is that any variable named
 * $scope.<variable_name> can be accessed from the controller's connected HTML file. This includes functions
 * as well. If you declare normal JavaScript variables, they will be limited to use in the controller
 * only. On the whole, injections are INCREDIBLY picky about syntax. You MUST declare your injections
 * in an array like this, with the last array element being the function, or you will run into errors.
 */
app.controller('LoginCtrl', ['$scope', '$location', 'UserService', function ($scope, $location, UserService) {
	$scope.user = {name: '', password: ''};
	$scope.incorrectLogin = false;
	
	//Compares values in the textboxes to the accepted username/password combo, then redirects
	//the user to the homepage if they are valid.
	$scope.validateUser = function(){
		$scope.incorrectLogin = false;
		if($scope.user.name == "user" && $scope.user.password == "123456"){
			UserService.setUsername($scope.user.name);
			$location.path('/home'); //route to the home page
		}	
		else
			$scope.incorrectLogin = true;
	};
	
}]);