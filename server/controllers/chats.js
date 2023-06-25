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
    addContactToFirebase(username, req.body.username); 
};


const addContactToFirebase = async (sender, user) => {
    socket.emit("add_chat");
    let fireToken = await userService.getFirebaseTokenByUsername(user);
    if (fireToken && fireToken !== "") {
        const senderName = await userService.getDisplaynameByUsername(sender);
        const message = {
            notification: {
                title: 'You have been added in What\'s Down!',
                body: 'a' + senderName + " has added you in What\'s Down! Write him a message!"
            },
            token: fireToken
        };
        sendMessageToFirebase(message);
    }
}

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
    const username = await loginService.getUsernameFromToken(req.headers.authorization.split(" ")[1]);
    const getChatId = await chatService.getChatById(req.params.id);
    const users = getChatId.users;
    const user1 = await users[0].username;
    const user2 = await users[1].username;
    const chat = await chatService.deleteChatById(req.params.id);
    await deleteContactFirebase(user1, user2, username);
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
    sendAMessageToFirebase(req.params.id, username, req.body.msg);
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

const sendAMessageToFirebase = async (id, sender, content) => {
    socket.emit("message_sent");
    const users = await chatService.getUsersById(id);
    if (users[0].username !== sender) {
        let fireToken = await userService.getFirebaseTokenByUsername(users[0].username);
        if (fireToken && fireToken !== "") {
            const senderName = await userService.getDisplaynameByUsername(sender);
            const message = {
                notification: {
                    title: 'New What\'s Down message!',
                    body: 'm' + senderName + ': ' + content
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
                    body: 'm' + senderName + ': ' + content
                },
                token: fireToken
            };
            sendMessageToFirebase(message);
        }
    }
}

const deleteContactFirebase = async (user1, user2, sender) => {
    socket.emit("delete_chat");
    let fireToken1 = await userService.getFirebaseTokenByUsername(user1);
    let fireToken2 = await userService.getFirebaseTokenByUsername(user2);
    if (user1 !== sender) {
        if (fireToken1 && fireToken1 !== "") {
            const message = {
                notification: {
                    title: 'New What\'s Down message!',
                    body: 'deleted stuff'
                },
                token: fireToken1
            };
            sendMessageToFirebase(message);
        }
    } else {
        if (fireToken2 && fireToken2 !== "") {
            const message = {
                notification: {
                    title: 'New What\'s Down message!',
                    body: 'deleted stuff'
                },
                token: fireToken2
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