//********************************************************************//
//            Login Page Controller
//*******************************************************************//
app.controller("LoginController",function($scope,$http,sharedProperties){
	sharedProperties.setProperty(null);
	document.getElementById("login").style.display = "block"
	document.getElementById("logout").style.display = "none"
	$scope.username = null
	$scope.password =null
	$scope.message = null
// Share Username
	$scope.changeU = function(){
		
		sharedProperties.setProperty($scope.username);
	}	
// Validate Credentials	
	$scope.validate = function(ev){
		var jsondata  = {};
		jsondata.name = $scope.username;
		jsondata.password = $scope.password
        var data = angular.toJson(jsondata);
		$http.post(URI + "Service/validateUser",data ).then(
				function(response) {
					if(response.data.message != null){
						$scope.username = null;
						$scope.password =null;
						$scope.message = response.data.message;
					}else{
						window.location.href = '#/newRequest';
						$scope.message = null;
					}
				}, function(response) {
					$scope.message = response.data.message
					$scope.username = null;
					$scope.password = null;
				});
	}
	
// Changing message fields 
	$scope.changeInput  = function(){
		$scope.message = null
	}
})
//********************************************************************//
//            New Request Page Controller
//*******************************************************************//

app.controller("CreateReqController",function($scope,$http,sharedProperties , nameLocation){
	$scope.data.username = sharedProperties.getProperty() 
	if($scope.data.username == null || $scope.data.username == ""){
		window.location.href = '#/loginpage'
	}else{
		document.getElementById("login").style.display = "none"
		document.getElementById("logout").style.display = "block"
	}
	
	$scope.onChange = function(){
		$scope.message = null
	}
// Generate Request Id
	$scope.getReqId = function(){
		$http.get(URI + "Service/getReqId").then(
			function(response) {
				$scope.requestId =  response.data.CustomerId;
			}, function(response) {
				$scope.message = response.data.message
			});
	}
// Setting Name and Location
    $scope.setValue = function(){
	nameLocation.setProperty($scope.name , $scope.location, $scope.requestId);
    }
})

//********************************************************************//
//            Home Page Controller
//*******************************************************************//
app.controller("HomeController",function($scope,$http){
	 $scope.data ={}
	 $scope.data.username = null
	 document.getElementById("login").style.display = "block"
	 document.getElementById("logout").style.display = "none"
})
//********************************************************************//
//            Add Product Page Controller
//*******************************************************************//
app.controller("ProductController",['$scope','$http','$modal','nameLocation','sharedProperties',
                'productList','passControl',function($scope,$http,$modal ,nameLocation,sharedProperties,productList,passControl ){
	$scope.customer =  nameLocation.getProperty() // for getting name and location
	if($scope.customer.name == "" || sharedProperties.getProperty() == null){
		window.location.href = '#/loginpage'	
	}else{
		$scope.product =[]
		$scope.productName = {}  // product names to be populated
		$scope.selected=[]
		
	//Product attributes Popup
		$scope.open = function(event){
			$scope.message = null;
			passControl.setProperty(Number(event.target.id))
			$scope.x = productList.getProperty();
			var modalInstance = $modal.open({
			templateUrl: 'partials/product_attribute.html',
			controller: 'PopupCont',
			});
		};
	// To fetch Product Offerings	
		$scope.fetchPO = function() {
			$http.get(URI + "Service/fetchproducttypes").then(
					function(response) {
						$scope.productName = response.data;
						//$scope.productName.message = null;
					}, function(response) {
						$scope.message = response.data.message
						$scope.productName.message = response.data.message;
						$scope.productName = null;
					});
		};	
			
	}

	$scope.checkProduct = function(event){
		$scope.message = null
		$scope.selected =[]
		x = productList.getProperty();
		mT= 0;
		for (i = 0 ;i<$scope.product.length ;i++){
			ct = null;
			crs = null
			for( j = 0 ; j < x.length ;j++){
				if(x[j].key == $scope.product[i]){
					ct  = x[j].contractTerm;
					crs = x[j].customerRequiredSpeed ;
				}
			}
			if($scope.product[i] != undefined || $scope.product[i] != null){
				var dummy ={
						"key"   :$scope.product[i] ,
						"value" : $scope.productName[$scope.product[i]],
				 "contractTerm" : ct,
				 "customerRequiredSpeed":crs
				}
				$scope.selected.push(dummy) // only checked product will be selected
				
				document.getElementById($scope.product[i]+'').style.marginTop = mT+"rem"
				mT= 0
			}else{
				
				mT+= 4;
			}
		}
		productList.setProperty($scope.selected);
	}
 // Navigating to Next Page
	$scope.next = function(){
		if($scope.selected.length == 0){
			$scope.message = "Select atleast one Product";
		}
		else{
			pl = productList.getProperty();
			flag = false;
			for(i = 0;i<pl.length;i++){
				if(pl[i].contractTerm == null || pl[i].customerRequiredSpeed == null){
				 flag =true;
				}
			}
			if(flag){
				$scope.message = "Please choose Required attributes";
			}else{
				window.location.href="#/serviceDetail"
			}
			
		}
	}
}])

//********************************************************************//
//            Product Popup   Controller
//*******************************************************************//
app.controller('PopupCont', ['$scope','$http','$modalInstance',
              'productList','passControl',function ($scope,$http, $modalInstance,productList,passControl) { 
	$scope.contractTerm = null
	$scope.customerRequiredSpeed = null
	index = passControl.getProperty() 
	product = productList.getProperty();
	$scope.attributes ={}
  // To fetch Product attributes
	$scope.fetchPA = function(ev){
	$http.get(URI + "Service/fetchproductattributes/"+index).then(
           
			function(response) {
				 var i = 0 	;
                for(key in response.data ){
                    $scope.attributes[i] = JSON.parse(response.data[key].replace(/'/g, '"'));
                    i++;
                }
			}, function(response) {
				$scope.message = response.data.message
			});
	}
 // Custom Sorting for String	
    $scope.cSort = function(value){
    	return parseInt(value)
    }
 // TO close Pop Up   
	$scope.close = function () {
		
		for (i = 0 ; i < product.length ;i++ ){
			if(product[i].key == index){
			  product[i].contractTerm = $scope.contractTerm
			  product[i].customerRequiredSpeed = $scope.customerRequiredSpeed
			}
		}
		productList.setProperty(product)
	    $modalInstance.dismiss('cancel');
	};
}]);

//********************************************************************//
//             Service Attribute Page Controller
//*******************************************************************//
app.controller("ServiceController",['$scope','$http','$modal','nameLocation','sharedProperties',
                'productList','feasibleReport',function($scope,$http,$modal,nameLocation,
                		sharedProperties,productList,feasibleReport){

	$scope.customer =  nameLocation.getProperty()
	if($scope.customer.name == "" || sharedProperties.getProperty() == null){
		window.location.href = '#/loginpage'
	}else{
		$scope.message = null
		$scope.create = true
		$scope.messageError = null
		
		document.getElementById("idFeasiblity").style.display = "none"
		document.getElementById("login").style.display = "none"
	    document.getElementById("logout").style.display = "block"
    	document.getElementById("id_port_unique").style.display = "none";
        document.getElementById("id_Access_type").style.display = "none";
	    $scope.product=productList.getProperty();
	    $scope.contractTerm = null;
	    $scope.customerRequiredSpeed = null;
	    document.getElementById("idSave").style.display = "none";
		$scope.port = null;
		$scope.accessType = null;
		$scope.services = {}
		$scope.properties = {}
		index = null;
		$scope.successStyle = null
		$scope.portIdentifier = null
	}

 // Fetch Service Attributes	
    $scope.fetchSA = function(event){
            index = event.target.id;
            $scope.message = null
        	$scope.properties = {};
            $scope.services = {};
            $scope.port = null;
            $scope.portIdentifier = null
            document.getElementById("list-"+index).classList.add("active");
            if(document.getElementById(index).style.color == "green"){
            		$scope.message = "Feasibility Report was Successfull !!!";
            		document.getElementById("id_service").style.display = "none";
                    for(i=0 ; i < $scope.product.length ; i++){
                    	if($scope.product[i].key == index){
                		 $scope.contractTerm = $scope.product[i].contractTerm
                         $scope.customerRequiredSpeed =  $scope.product[i].customerRequiredSpeed
                         document.getElementById("list-"+index).classList.add("active");
                    	}else{
                    		className = document.getElementById("list-"+$scope.product[i].key).className.split(" ");
                    		var ix = className.indexOf("active")
                    		if(ix > 0){
                    			document.getElementById("list-"+$scope.product[i].key).classList.remove("active");
                    		}
                    	}
                    }
            }else{
            	document.getElementById("id_service").style.display = "inline";
                document.getElementById("idFeasiblity").style.display = "inline";
                for(i=0 ; i < $scope.product.length ; i++){
                	if($scope.product[i].key == index){
            		 $scope.contractTerm = $scope.product[i].contractTerm
                     $scope.customerRequiredSpeed =  $scope.product[i].customerRequiredSpeed
                     document.getElementById("list-"+index).classList.add("active");
                	}else{
                		className = document.getElementById("list-"+$scope.product[i].key).className.split(" ");
                		var ix = className.indexOf("active")
                		if(ix > 0){
                			document.getElementById("list-"+$scope.product[i].key).classList.remove("active");
                		}
                	}
                }
               
                $http.get(URI + "Service/fetchserviceattributes/"+index).then(
                 function(response) {
                       i = 0 ;
                       for(key in response.data ){
                              if (i !=0){
                                     $scope.services[key+''] = JSON.parse(response.data[key].replace(/'/g, '"'));
                              }else{
                                     $scope.servicename = (JSON.parse(response.data[key].replace(/'/g, '"')))[0];
                                     $scope.port = $scope.servicename.split(" ")[0];
                                     if($scope.port == "Ethernet"){
                                    	 $scope.accessType = "BDSL"
                                     }else{
                                    	 l = $scope.servicename.split(" ").length;
                                    	 j = 0;
                                    	 string =""
                                    	 while(j< l-1){
                                    		 string = string + $scope.servicename.split(" ")[j] +" "
                                    		 j++
                                    	 }
                                    	 $scope.accessType = string
                                     }
                              }
                              i++;
                       }
               	    $scope.properties["Restricted Routing Policy"] =  null
               		$scope.properties["Restricted Routing Policy v6"] = null
                   	document.getElementById("id_port_unique").style.display = "inline";
                    document.getElementById("id_Access_type").style.display = "inline";
                }, function(response) {
                	$scope.messageError = response.data.message
                });
        }
      // On Change of Fields	
        $scope.onChange = function(event){
        	id = event.key;
        	$scope.message = null
        	$scope.messageError = null
        	if(id == "Port Speed"){
        		if (Number($scope.properties["Port Speed"].split(" ")[0]) < Number($scope.customerRequiredSpeed) ){
        			$scope.properties["Port Speed"] = null
        			$scope.messageError = " Invalid Port Speed "
        		}
        	}else if(id == "Port Speed(CIR)"){
        		if (Number($scope.properties["Port Speed(CIR)"].split(" ")[0]) < Number($scope.customerRequiredSpeed) ){
        			$scope.properties["Port Speed(CIR)"] = null
        			$scope.messageError = " Invalid Port Speed "
        		}
        	}else if (id == "Access Speed"){
        		
        		if(($scope.properties["Port Speed"] == null && id == "Port Speed") ||
        				$scope.properties["Port Speed(CIR)"] == null && id == "Port Speed(CIR)"){
        			$scope.messageError = "Select Port Speed First";
        			$scope.properties["Access Speed"] = null
        		}else{
    				if($scope.properties["Port Speed"] != null){
						ps = $scope.properties["Port Speed"].split(" ")[0]
					}else{
						ps = $scope.properties["Port Speed(CIR)"].split(" ")[0]
					}
        			
        			
            		if (Number($scope.properties["Access Speed"].split(" ")[0]) < Number($scope.customerRequiredSpeed)
            				||  Number($scope.properties["Access Speed"].split(" ")[0]) < 
            				Number(ps) ){
            			$scope.properties["Access Speed"] = null
            			$scope.messageError = " Invalid Access Speed " 
            		}else{ 
            			if($scope.accessType.search('ADSL') >= 0){
            				if ($scope.properties["Access Speed"] == '256 kbps'){
            					$scope.properties["Max Allowed Cable Distance"] = '3000m';
            				}else if ($scope.properties["Access Speed"] == '512 kbps') {
            					$scope.properties["Max Allowed Cable Distance"] = '2500m';
            				}else if ($scope.properties["Access Speed"] == '1024 kbps') {
            					$scope.properties["Max Allowed Cable Distance"] = '2000m';
            				}else if ($scope.properties["Access Speed"] == '2048 kbps') {
            					$scope.properties["Max Allowed Cable Distance"] = '1500m';
            				}else if ($scope.properties["Access Speed"] == '4096 kbps') {
            					$scope.properties["Max Allowed Cable Distance"] = '1000m';
            				}else if ($scope.properties["Access Speed"] == '8192 kbps') {
            					$scope.properties["Max Allowed Cable Distance"] = '750m';
            				}else{
            					$scope.properties["Max Allowed Cable Distance"] = '500m';
            				}
            			}else if ($scope.accessType == 'BDSL'){
            				if ($scope.properties["Access Speed"] == '256 kbps'){
            					$scope.properties["Max Allowed Cable Distance"] = '8000m';
            				}else if ($scope.properties["Access Speed"] == '512 kbps') {
            					$scope.properties["Max Allowed Cable Distance"] = '7500m';
            				}else if ($scope.properties["Access Speed"] == '1024 kbps') {
            					$scope.properties["Max Allowed Cable Distance"] = '7000m';
            				}else if ($scope.properties["Access Speed"] == '2048 kbps') {
            					$scope.properties["Max Allowed Cable Distance"] = '6500m';
            				}else if ($scope.properties["Access Speed"] == '4096 kbps') {
            					$scope.properties["Max Allowed Cable Distance"] = '6000m';
            				}else if ($scope.properties["Access Speed"] == '8192 kbps') {
            					$scope.properties["Max Allowed Cable Distance"] = '5500m';
            				}else{
            					$scope.properties["Max Allowed Cable Distance"] = '5000m';
            				}
            			}
                        a = document.getElementById("Max Allowed Cable Distance")
                        a.disabled = true;
            		 }
        		}

        	}else if (id = "IP Variant"){
        		if($scope.properties["IP Variant"] == "IPv6"){
        			 document.getElementById("div-Restricted Routing Policy").style.display = "none";
        			 document.getElementById("div-Restricted Routing Policy v6").style.display = "inline";
        			 document.getElementById("Restricted Routing Policy").style.display = "none";
        			 document.getElementById("Restricted Routing Policy v6").style.display = "inline";
        			 $scope.properties["Restricted Routing Policy"] = "x"
        		}else if ($scope.properties["IP Variant"] == "IPv4"){
        			 document.getElementById("div-Restricted Routing Policy").style.display = "inline";
        			 document.getElementById("div-Restricted Routing Policy v6").style.display = "none";
        			 $scope.properties["Restricted Routing Policy v6"] = "x"
        			 document.getElementById("Restricted Routing Policy").style.display = "inline";
        			 document.getElementById("Restricted Routing Policy v6").style.display = "none";
        		}else{
        			 document.getElementById("div-Restricted Routing Policy").style.display = "inline";
        			 document.getElementById("div-Restricted Routing Policy v6").style.display = "inline";
        			 document.getElementById("Restricted Routing Policy").style.display = "inline";
        			 document.getElementById("Restricted Routing Policy v6").style.display = "inline";
        		}
        	}
        	
            }
            
    }
  // Custom sort for String  
    $scope.cSort = function(value){
    	return parseInt(value)
    }
    
    $scope.openFeasibility = function(){
    	document.getElementById("idSave").style.display = "none"
    	if(index == null){
    		$scope.messageError = "select product to check feasibility";
    	}else{
    		var jsondata  = {};
    		jsondata.prodId = Number(index);
    		jsondata.locationName = nameLocation.getProperty().location
    		jsondata.distance = Number($scope.properties["Max Allowed Cable Distance"].split("m")[0])
    		jsondata.accessSpeed = Number($scope.properties["Access Speed"].split(" ")[0])
            var data = angular.toJson(jsondata);
    		$http.post(URI + "Service/feasibility",data ).then(
    				function(response) {
    					feasibleReport.setProperty(response.data);
    					if(response.data.result == "Success"){
    						for( i = 0;i<$scope.product.length ; i++){
    							if($scope.product[i].key == index){
    								$scope.product[i].result="success"
    							}
    						}
    						document.getElementById(index).style.color = 'green';	
    					}else{
    						for( i = 0;i<$scope.product.length ; i++){
    							if($scope.product[i].key == index){
    								$scope.product[i].result="Failed"
    							}
    						}
    						document.getElementById(index).style.color  = 'red'
    					}
    					var modalInstance = $modal.open({
    		    			templateUrl: 'partials/feasibility.html',
    		    			controller: 'feasibility',
    		    		});
    				}, function(response) {
    					$scope.messageError = response.data.message
    				});
    	}
    }
    
  // Set Product attributes
    $scope.open = function(event){
    	 index = event.target.id
    	 $scope.contractTerm = $scope.product[index].contractTerm
    	 $scope.customerRequiredSpeed =  $scope.product[index].customerRequiredSpeed
    	 $scope.properties = {}
    }
    
  // To Create New Customer   
    $scope.createCustomer = function(){
    	$scope.message = null;
    	$scope.messageError = null
		var jsondata  = {};
		jsondata.customerId = Number($scope.customer.requestId);
		jsondata.name = $scope.customer.name;
		jsondata.location = $scope.customer.location;
        var data = angular.toJson(jsondata);
		$http.post(URI + "Service/create",data ).then(
				function(response) {
						$scope.message = response.data.message;
						document.getElementById("idSave").style.display = "none"
				}, function(response) {
					$scope.messageError = response.data.message
				});
    }
}]);
//********************************************************************//
//  Feasibility Popup Controller
//*******************************************************************//
app.controller('feasibility', ['$scope','$modalInstance','feasibleReport',
                               'productList',function ($scope, $modalInstance ,feasibleReport,productList) {
	
 // Fetch Feasibility Report	
	$scope.fetchDetails = function(){
		$scope.feasible = feasibleReport.getProperty();
	}
 // On close
	$scope.close = function () {
	products = 	productList.getProperty();
	count = 0
	for(i = 0 ; i<products.length ; i++){	
		if(products[i].result == 'success'){
			count++;
		}
	}
	if(count == products.length){
		$scope.create = true
		document.getElementById("idSave").style.display = "inline"
	}
	$modalInstance.dismiss('cancel');
	};
}]);
