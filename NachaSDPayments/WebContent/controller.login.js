/*
 * Angular injections allow you to use other AngularJS modules/controllers/services/etc in your
 * code. In this controller, I'm injecting $scope and the DatabaseService service. I don't know
 * everything that $scope does, but what's really important about it is that any variable named
 * $scope.<variable_name> can be accessed from the controller's connected HTML file. This includes functions
 * as well. If you declare normal JavaScript variables, they will be limited to use in the controller
 * only. On the whole, injections are INCREDIBLY picky about syntax. You MUST declare your injections
 * in an array like this, with the last array element being the function, or you will run into errors.
 */
app.controller('LoginCtrl', ['$scope', 'DatabaseService', function ($scope, DatabaseService) {

	//myFunction() is called when the button is pressed via the ng-click class
	$scope.myFunction = function(){
		/*
		 *The DatabaseService is an AngularJS service that makes http calls to the backend of the app.
		 *It can be found within the "service.database.js" file in the project.
		 *callBackend() is the specific function in the service I'm calling.
		 *This is what's considered an AJAX call. The .success() after the function name means that
		 *the service *promises* to return a value at *some* point in the future. Any Javascript
		 *following the call will be run afterwards until it is interrupted by that returned value, 
		 *which I'm calling result. At that point, the program jumps to the function contained within
		 *.success() and executes it.
		 */
		DatabaseService.callBackend().success(function(result){
			$scope.greeting = result.textA;
			$scope.source = result.textB;
		});
	};
	
}]);