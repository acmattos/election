/**
 * 'election' module controller. Controls general aspects of the application.
 */
export default class IndexController{
   constructor(scope, state, log, window, auth2Service, 
      httpService, userService, LOGIN_STATE){
      let vm = this;
      vm._scope = scope;
      vm._state = state;
      vm._log = log;
      vm._localStorage = window.localStorage;
      vm._auth2Service = auth2Service;
      vm._httpService = httpService;
      vm._userService = userService;
      vm._loginState = LOGIN_STATE;
      vm.employee = {};
 
      scope.$on('onSuccessfulLogin', function(event, value) { 
         vm._log.debug('IndexControler.constructor - onSuccessfulLogin', value);
         vm._storeEmployeeData();
      });
   }

   /**
    * Stores Employee data
    */
   _storeEmployeeData(){
      let self = this;
      let request = this._httpService.createHttpRequest(
        'GET',
        '/v1/employees');
     let user = self._userService.getUser();
     if (user) {
         self._httpService.http(request).then(
            (response) => {
             let employees = response.data;
             let employee = employees.filter(function(employee){
                if(user.username === employee.credential.username){
                    return employee;
                }
             });
             self.employee = employee[0];
             self._localStorage.setItem('employee', 
                JSON.stringify(self.employee));
         });
      }
   }
}

IndexController.$inject = [
   '$rootScope', 
   '$state',
   '$log',
   '$window',
   'auth2Service',
   'httpService',
   'userService',
   'LOGIN_STATE'
];