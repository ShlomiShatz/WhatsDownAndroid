// const Message = require('../models/message');
import Message from '../models/message.js'

const createMessage = async (msg, sender) => {
    let message = new Message({
        sender: sender,
        content: msg,
        created: Date.now()
    });
    let saveMessage = await message.save()
    message.id = saveMessage._id;
    await message.save();
    return message._id;
};

const getAllMessage = async (chat) => {
    const allMsg = [];
    for(let msg of chat.messages){
        let singleMsg = await Message.findById(msg).populate('sender').exec();
        const { username, ...newObjectSender } = singleMsg.sender;
        const newObject = {
            ...singleMsg,
            sender: username
        };
        allMsg.push(newObject);
    }
    return allMsg;
};

const getfullMsgById  = async (id) => {
    let singleMsg = await Message.findById(id).populate('sender').exec();
    return singleMsg;
};

const deleteMsgById  = async (id) => {
    const msg = await Message.findById(id);
    if (!msg)
        return null;
    await msg.deleteOne();
    return msg;
};

export {
    createMessage,
    getAllMessage,
    getfullMsgById,
    deleteMsgById
};