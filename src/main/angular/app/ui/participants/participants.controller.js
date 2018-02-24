/**
 * 'election.ui.participants' module controller. Collects election's partial 
 * results from BE and presents to the user.
 */
export default class ParticipantsController{
   constructor(log, timeout, httpService) {
      let vm = this;
      vm._log = log;
      vm._httpService = httpService;
      vm.participants = {};
      vm.message = '';
      timeout(function(){ // TODO, FIX ERROR!!!!
        vm._loadData();
     }, 500);
   }
   
   /**
    * Retrieves election results from BE.
    */
   _loadData() { 
      let self = this;
      let request = this._httpService.createHttpRequest(
         'GET',
         '/v1/election/participants'
      );
      self._httpService.http(request)
      .then((response) => {
        self.participants = response.data;
      }).catch((error)=>{
        self.message = 'No participant has already voted!';
      });
   }
}

ParticipantsController.$inject = [
   '$log',
   '$timeout',
   'httpService'
];
