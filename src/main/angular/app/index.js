import 'bootstrap/dist/css/bootstrap.min.css';

import angular         from 'angular';
import ngUiBootstrap   from 'angular-ui-bootstrap';
import ngUiRouter      from 'angular-ui-router';
import config          from '../config/app.config';
import indexController from './index.controller';
import login           from './ui/login';
import menu            from './ui/menu';
import participants    from './ui/participants';
import vote            from './ui/vote';
import results         from './ui/results';
import oauth2          from './oauth2/ls';
import run             from './oauth2/ls/run/run.config';

/**
 * 'election' module definition.
 * Loads all modules used, does all configurations needed and bootstrap the 
 * application.
 */
export default angular.module('election', [
   ngUiBootstrap,
   ngUiRouter,
   login,
   menu,
   participants,
   vote,
   results,
   oauth2
])
.constant('APP_NAME', 'ACMattos __ELECTION__')
.constant('LOGIN_STATE','/')
.config(config)
.run(run)
.controller('indexController', indexController)
.name;