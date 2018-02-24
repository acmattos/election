/**
 * 'election.ui.results' module controller. Collects election's partial results 
 * from BE and presents to the user.
 */
export default class ResultsController{
   constructor(log, timeout, httpService) {
      let vm = this;
      vm._log = log;
      vm._httpService = httpService;
      vm.results = {};
      vm.message = '';
      timeout(function(){
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
        '/v1/election/results');
      self._httpService.http(request)
      .then((response) => {
        self.results = response.data;
      }).catch((error)=>{
         self.mensagem = 'No requirement available for now!';
      });
   }
}

ResultsController.$inject = [
   '$log',
   '$timeout',
   'httpService'
];
