/**
 * 'election.ui.vote' module controller. Collects employee's non voted 
 * requirements from BE and presents them. It also computes employee's votes.
 */
export default class VoteController{
   constructor(filter, log, q, timeout, localStorageService, httpService, 
      userService) {
      let vm = this;
      vm._filter = filter;
      vm._log = log;
      vm._q = q;
      vm._localStorageService = localStorageService;
      vm._httpService = httpService;
      vm._userService = userService;
      vm.requirements = {};
      vm.messages = '';
      timeout(function(){ 
         vm._loadData();
      }, 500);
   }
   
   /**
    * Retrieves non voted requirements from BE.
    */
   _loadData() {
      let self = this;
      self._getEmployee().then(
      function(employee){
         let request = self._httpService.createHttpRequest(
            'GET',
            '/v1/election/requirements/'.concat(employee.id)
         );
         self._httpService.http(request)
         .then((response) => {
            self.requirements = response.data;
         }).catch((error) => {
            self.requirements = {};
            self.message = `You already voted for all available requirements 
            or there are no registered requirements to vote for!`; 
         });
      });
   }   

   /**
    * Computes an employee's vote. 
    * @param {*} requirementId Requirement's Idendenty.
    * @param {*} comment Comment about the vote.
    */   
   vote(requirementId, comment) {
      let self = this;
      let datetime = self._filter('date')(new Date(), "yyyy-MM-dd HH:mm:ss Z");
      self._getEmployee().then(
      function(employee){
        let vote = self._createVote(
          comment, datetime, employee.id, requirementId);

        let request = self._httpService.createHttpRequest(
          'POST',
          '/v1/election/votes', vote); 
        self._httpService.http(request)
        .then((response) => {
          vote = response.data;
          request = self._httpService.createHttpRequest(
              'GET',
              '/v1/election/requirements/'.concat(employee.id)); 
          self._httpService.http(request)
          .then((response) => {
              self.requirements = response.data;
          }).catch((erro) => {
              self.requirements = {};
              self.message = `There are no requirements available for you
                or there are no requirements available for voting!`;  
          });
        });
      });
   }

   /**
    * Gets employee from local storage or from Rest API.
    */
   _getEmployee() {
     let self = this;
     let defered = self._q.defer(); 
     let employee = self._localStorageService.get('employee');
     if(!employee){
        self._userService.getUser().then(
        (user) => {
            let request = this._httpService.createHttpRequest(
                'GET', 
                '/v1/employees'); 
            self._httpService.http(request).then(
            (response) => {
               let employees = response.data;
               employee = employees.filter(function(employee){
                  if(user.username === employee.credential.username){
                    return employee;
                  }
               });
               self.employee = employee[0];
               self._localStorageService.set('employee', self.employee);
               defered.resolve(self.employee);
            });
        });
     } else {
        defered.resolve(employee);
     }
     return defered.promise;
   }

   /**
    * Creates a vote to be computed.
    * @param {*} comment Comment about this volte
    * @param {*} datetime Date/Time of this vote.
    * @param {*} employeeId Employee's Identity.
    * @param {*} requirementId Requirement's Identity.
    */
   _createVote(comment, datetime, employeeId, requirementId){
      let vote = {
         "comment": comment,
         "datetime": datetime,
         "employee":{
            "id": employeeId
         },
         "requirement":{
            "id": requirementId
         }       
      };
      return vote;
   }
}

VoteController.$inject = [
   '$filter',
   '$log',
   '$q',
   '$timeout',
   'localStorageService',
   'httpService',
   'userService'
];
