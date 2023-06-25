import express from 'express';
import * as chatController from '../controllers/chats.js'
import * as loginController from '../controllers/login.js'

var routerChat = express.Router();

routerChat.route('/')
    .get(loginController.isLoggedIn, chatController.getAllChats)
    .post(loginController.isLoggedIn, chatController.createChat);
routerChat.route('/:id')
    .delete(loginController.isLoggedIn, chatController.deleteChat)
    .get(loginController.isLoggedIn, chatController.getChat);
routerChat.route('/:id/Messages')
    .post(loginController.isLoggedIn, chatController.addMessage)
    .get(loginController.isLoggedIn, chatController.getAllMessages);

export default routerChat;