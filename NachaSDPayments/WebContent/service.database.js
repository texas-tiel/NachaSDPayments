app.controller('DatabaseService', function ($scope, $http) {
	$scope.callBackend = function(){
		return $http.get();
	};
});