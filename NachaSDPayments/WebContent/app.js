var app = angular.module('NachaSDPayments', ['ngRoute']);
app.config(['$routeProvider', '$locationProvider', function ($routeProvider, $locationProvider) {
	$routeProvider
		.when("/", {templateUrl: "login.html", controller: "LoginCtrl"})
		//.when({})
		.otherwise("/");
}]);