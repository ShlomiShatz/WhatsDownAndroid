import * as chatService from'../services/chat.js';
import * as messageService from '../services/message.js';
import * as userOmitPasswordService from '../services/userOmitPassword.js';
import * as loginService from '../services/login.js';
import * as userService from '../services/user.js'
import { sendMessageToFirebase } from '../services/firebase.js';
import { socket } from "../Socket.js";

const createChat = async (req, res) => {
    if(!req.body.username){
        return res.status(500);
    }
    const username = loginService.getUsernameFromToken(req.headers.authorization.split(" ")[1]);
    if(username === req.body.username){
        return res.status(400).json({error: ['Thou shalt not talk with thy self']});
    }
    const user1Obj = await userOmitPasswordService.getUserIdByName(username);
    const user2Obj = await userOmitPasswordService.getUserIdByName(req.body.username);
    if(!user2Obj){
        return res.status(409).json({error: ['user not found']});
    }
    res.json(await chatService.createChat(user1Obj, user2Obj));
};

const getChat = async (req, res) => {
    const username = loginService.getUsernameFromToken(req.headers.authorization.split(" ")[1]);
    const chat = await chatService.getChatById(req.params.id);
    if(!chat){
        return res.status(401).json({error: ['chat not found']});
    }
    if(!chat.users.filter((element) => element.username === username)){
        return res.status(401).json({error: ['no permissions']});
    }
    res.json(chat);
};

const getAllChats = async (req, res) => {
    const username = loginService.getUsernameFromToken(req.headers.authorization.split(" ")[1]);
    const chat = await chatService.getAllChats(username);
    res.json(chat);
};

const deleteChat = async (req, res) => {
    if (!req.params.id) {
        return res.status(401).json({error: ['chat not found']});
    } 
    const username = loginService.getUsernameFromToken(req.headers.authorization.split(" ")[1]);
    const chat = await chatService.deleteChatById(req.params.id);
    if(!chat){
        return res.status(404).json({error: ['chat not found']});
    }
    if(!chat.users.filter((element) => element.username === username)){
        return res.status(401).json({error: ['no permissions']});
    }
    for(let msg of chat.messages){
        messageService.deleteMsgById(msg._id);
    }
    res.json(chat);
};

const addMessage = async (req, res) => {
    if(!req.body.msg){
        return res.status(500);
    }
    if (!req.params.id) {
        return res.status(401).json({error: ['chat not found']});
    } 
    const username = loginService.getUsernameFromToken(req.headers.authorization.split(" ")[1]);
    const current_chat = await chatService.getChatById(req.params.id);
    if(!current_chat.users?.filter((element) => element.username === username)){
        return res.status(401).json({error: ['no permissions']});
    }
    const userId = await userOmitPasswordService.getUserIdByName(username);
    const msgId = await messageService.createMessage(req.body.msg, userId);
    const chat = await chatService.updateChatById(req.params.id, msgId);
    if(!chat){
        return res.status(401).json({error: ['chat not found']});
    }
    const msg = await messageService.getfullMsgById(msgId)
    res.json(msg);
    sendToFirebase(req.params.id, username, req.body.msg);
};

const getAllMessages = async (req, res) => {
    if (!req.params.id) {
        return res.status(401).json({error: ['chat not found']});
    } 
    const username = loginService.getUsernameFromToken(req.headers.authorization.split(" ")[1]);

    const current_chat = await chatService.getChatById(req.params.id);
    if(!current_chat){
        return res.status(401).json({error: ['chat not found']});
    }
    else if(!current_chat.users.filter((element) => element.username === username)){
        return res.status(401).json({error: ['no permissions']});
    }
    const allChats = await chatService.allMessagesChatById(req.params.id);
    if(!allChats){
        return res.status(401).json({error: ['chat not found']});
    }
    
    res.json(allChats);
};

const sendToFirebase = async (id, sender, content) => {
    socket.emit("message_sent");
    const users = await chatService.getUsersById(id);
    if (users[0].username !== sender) {
        let fireToken = await userService.getFirebaseTokenByUsername(users[0].username);
        if (fireToken && fireToken !== "") {
            const senderName = await userService.getDisplaynameByUsername(sender);
            const message = {
                notification: {
                    title: 'New What\'s Down message!',
                    body: senderName + ': ' + content
                },
                token: fireToken
            };
            sendMessageToFirebase(message);
        }
    } else {
        let fireToken = await userService.getFirebaseTokenByUsername(users[1].username);
        if (fireToken && fireToken !== "") {
            const senderName = await userService.getDisplaynameByUsername(sender);
            const message = {
                notification: {
                    title: 'New What\'s Down message!',
                    body: senderName + ': ' + content
                },
                token: fireToken
            };
            sendMessageToFirebase(message);
        }
    }
}

export {
    createChat,
    getChat,
    getAllChats,
    deleteChat,
    addMessage,
    getAllMessages
};