/**
 * 'election' module controller. Controls general aspects of the application.
 */
export default class IndexController{
   constructor(scope, state, log, localStorageService, auth2Service, 
      httpService, userService, LOGIN_STATE){
      let vm = this;
      vm._scope = scope;
      vm._state = state;
      vm._log = log;
      vm._localStorageService = localStorageService;
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
      self._userService.getUser().then(
      (user) => {
         self._httpService.http(request).then(
            (response) => {
             let employees = response.data;
             let employee = employees.filter(function(employee){
                if(user.email === employee.email){
                    return employee;
                }
             });
             self.employee = employee[0];
             self._localStorageService.set('employee', self.employee);
         });
      });
   }
}

IndexController.$inject = [
   '$rootScope', 
   '$state',
   '$log',
   'localStorageService',
   'auth2Service',
   'httpService',
   'userService',
   'LOGIN_STATE'
];