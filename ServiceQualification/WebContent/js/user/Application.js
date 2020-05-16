var app=angular.module("Application",['ngRoute',"ui.bootstrap"]);

//********************************************************************//
// Route Provider
//*******************************************************************//
app.config(function($routeProvider){
	$routeProvider.when("/loginpage",{
		templateUrl:"partials/loginpage.html",
		controller : 'LoginController'
	}).when("/newRequest",{
		templateUrl:"partials/newRequest.html",
		controller : 'CreateReqController'
	}).when("/Products_details_page",{
		templateUrl:"partials/Products_details_page.html",
		controller : 'ProductController'
	}).when("/home",{
		templateUrl:"partials/home.html",
		controller : 'LoginController'
	}).when("/serviceDetail",{
    templateUrl:"partials/Result.html",
    controller : 'ServiceController'
   }).otherwise({
		redirectTo:"/",
		controller : 'LoginController'
	});
	
});

//********************************************************************//
// Automatic Session Log out
//*******************************************************************//
app.run(function($rootScope, $timeout, $document ,sharedProperties) {    
    //console.log('starting run');
    // Timeout timer value
    var TimeOutTimerValue = 600000;

    // Start a timeout
    var TimeOut_Thread = $timeout(function(){ LogoutByTimer() } , TimeOutTimerValue);
    var bodyElement = angular.element($document);

    angular.forEach(['keydown', 'keyup', 'click', 'mousemove', 'DOMMouseScroll', 'mousewheel', 'mousedown', 'touchstart', 'touchmove', 'scroll', 'focus'], 
    function(EventName) {
         bodyElement.bind(EventName, function (e) { TimeOut_Resetter(e) });  
    });

    function LogoutByTimer(window){
    	if (sharedProperties.getProperty() != ""){
          //  console.log('Logout');
            alert("Session Expired")
            document.getElementById("login").style.display = "block"
    	    document.getElementById("logout").style.display = "none"
            this.window.location.href = '#/index.html'
            	sharedProperties.setProperty('')
    	}
    }

    function TimeOut_Resetter(e){
        //console.log(' ' + e);

        /// Stop the pending timeout
        $timeout.cancel(TimeOut_Thread);

        /// Reset the timeout
        TimeOut_Thread = $timeout(function(){ LogoutByTimer() } , TimeOutTimerValue);
    }

})

//********************************************************************//
//            Service to Get Id
//*******************************************************************//
app.service('passControl', function() {
	var index;
	return {
		getProperty : function() {
			return index;
		},
		setProperty : function(value) {
			index = value
		}
	};
});

//********************************************************************//
// Service to get  Sales User Name 
//*******************************************************************//
app.service('sharedProperties', function () {
    var property = '';

    return {
        getProperty: function () {
            return property;
        },
        setProperty: function(value) {
            property = value;
        }
    };
});
//********************************************************************//
//Service to get  Customer Name & Location
//*******************************************************************//
app.service('nameLocation', function () {
	var customer = {}
	customer.name= "";
	customer.location = "";
	customer.requestId = ""
    return {
        getProperty: function () {
            return customer;
        },
        setProperty: function(value1,value2,value3) {
        	customer.name = value1;
        	customer.location = value2;
        	customer.requestId = value3
        }
    };
});
//********************************************************************//
//Service to get  Selected Product
//*******************************************************************//
app.service('productList', function () {
    var product = []
    return {
     getProperty: function () {
         return product;
     },
     setProperty: function(value) {
    	  product=[]
          for(i=0;i<value.length;i++){
        	  product.push(value[i]) 
          }
     }
 };
});
//********************************************************************//
//Feasibility of  Selected Product
//*******************************************************************//
app.service('productKey', function () {
    var product = null
    return {
     getProperty: function () {
         return product;
     },
     setProperty: function(value) {
    	 product = value
     }
 };
});


app.service('feasibleReport', function () {
    var property = null;

    return {
        getProperty: function () {
            return property;
        },
        setProperty: function(value) {
            property = value;
        }
    };
});

//********************************************************************//
//Global variable URI
//*******************************************************************//

var URI = getURI();
