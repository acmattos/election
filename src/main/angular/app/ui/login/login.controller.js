/**
 * 'election.ui.login' module controller. Controls employee's authentication and
 * authorization process, getting employee's credential and passing to BE, in 
 * order to validate the action.
 */
export default class LoginController{
   constructor(scope, state, log, auth2Service, tokenStorageService, isLogout) {
      let vm = this;
      vm._scope = scope; 
      vm._state = state;
      vm._log = log;
      vm._auth2Service = auth2Service;
      vm._tokenStorageService = tokenStorageService;
      vm.error = {};
      vm.employee = {};
      vm.employee.username = '';
      vm.employee.password = '';
      vm._verifyPreviousAuthentication(); 
      if(isLogout){
         vm.logout();
      }     
   }
   
   /**
    * Logout employee. TODO TEST IT
    */
    logout() {
      this._auth2Service.logout();
      this._scope.$broadcast('onLogoutSuccessful', true);
      this._state.go('/');
   }

   /**
    * Login process.
    */
   login(){
      let self = this;
      let request = self._getLoginRequest();
      self._auth2Service.login(request).then(
      function(data) {
         self._onSuccessfulLogin();
      })
      .catch(function (error) {
         self.error = self._prepareErrorResponse(error);
      });
   }

   /**
    * Verifies a previous login attempt, using refresh_token for automatic 
    * athentication.
    */
   _verifyPreviousAuthentication(){
      let self = this;
      self._tokenStorageService.getRefreshToken().then(
      function(refreshToken){
         if(refreshToken) {
            return self._auth2Service.refreshToken();
         }
      })
      .then(
      function(response){
         if(response && 200 == response.status){
            self._onSuccessfulLogin();
         }
      })
      .catch(
      function(error){
         self._log.error(
           'LoginController._verifyPreviousAuthentication:' + 
           ' It was not possible to automatically login the employee!');
      });
   }

   /**
    * After a successful login, prepares the application for employee to use.
    */
   _onSuccessfulLogin(){
      let self = this;
      self._state.go('vote');
      self._scope.$broadcast('onSuccessfulLogin', true);
      self._log.debug('LoginController._onSuccessfulLogin: OK!');
   }
  
   /**
    * Creates a login configuration request.
    * @return Login request.
    */
   _getLoginRequest(){
      let request = {
         method: 'POST',
         url: '/oauth/token',
         client: 'election-frontend',
         client_secret: 'election-secret',
         grant_type: 'password',
         username: this.employee.username,
         password: this.employee.password
      };
      return request;
   }

   /**
    * Prepares error message.
    * @param {*} response Failure login response.
    */
   _prepareErrorResponse(response){
      let error = {};
      if(response.stack){
         error.status = -500;
         error.message = response.message;
      } else {
         error.status = response.status;
      }
      if(504 === response.status) {
         error.message = 'Auth Server not online!';
      } else if(401 === response.status) {
         error.message = 
            'Access forbidden! Please, verify username and password.';
      } else {
         error.message = response.message;
      }
      return error;
   }   
}
LoginController.$inject = [
   '$rootScope',
   '$state',
   '$log',
   'auth2Service',
   'tokenStorageService',
   'isLogout'
];