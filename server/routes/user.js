import express from 'express';
// const userController = require('../controllers/user.js');
// const loginController = require('../controllers/login.js');
import * as userController from '../controllers/user.js'
import * as loginController from '../controllers/login.js'

var routerUser = express.Router();

routerUser.route('/').post(userController.createUser);
routerUser.route('/:username').get(loginController.isLoggedIn, userController.getUser);

export default routerUser;