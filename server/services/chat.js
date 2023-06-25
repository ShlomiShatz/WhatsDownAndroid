import Chat from '../models/chat.js'
import message from '../models/message.js';


const createChat = async (creator, user) => {
    const chat = new Chat({
        users: [creator, user],
        messages: []
    });
    let newChat = await chat.save();
    chat.id = newChat._id;
    newChat = await chat.save();
    const newChatExtented = Chat.findById(newChat._id).populate('users').populate('messages');
    const retChet = {
        id: newChat._id,
        user: (await newChatExtented).users[1]
    };
    return retChet;
};

const getChatById = async (id) => {
    const result = await Chat.findById(id).populate('users').populate('messages').populate({
        path: 'messages',
        populate: {
          path: 'sender'
        },
      }).exec();
    return result;
};

const getUsersById = async (id) => {
    const result = await getChatById(id);
    return result.users;
}

const getAllChats = async (userConnected) => {
    const chats = await Chat.find({}).populate('users').populate('messages').populate({
        path: 'messages',
        populate: {
          path: 'sender'
        },
      }).exec();
    const detailsAllChats = []
    for (let chat of chats) {
        if (chat.users[0].username === userConnected) {
            const amountMessages = chat.messages.length;
            let lastmsg = null;
            if (amountMessages !== 0)
                lastmsg = chat.messages[chat.messages.length - 1];
            const newObj = {
                id: chat.id,
                user: chat.users[1],
                lastMessage: lastmsg
            }
            detailsAllChats.push(newObj);
        }
        else if(chat.users[1].username === userConnected){
            const amountMessages = chat.messages.length;
            let lastmsg = null;
            if (amountMessages !== 0)
                lastmsg = chat.messages[chat.messages.length - 1];
            const newObj = {
                id: chat.id,
                user: chat.users[0],
                lastMessage: lastmsg
            }
            detailsAllChats.push(newObj);
        }
    }
    return detailsAllChats;
};

const deleteChatById = async (id) => {
    const chat = await getChatById(id);
    if (!chat)
        return null;
    await chat.deleteOne();
    return chat;
};

const updateChatById = async (id, msg) => {
    const chat = await getChatById(id);
    if (!chat)
        return null;
    chat.messages = chat.messages.push(msg);
    const update = {
        $set: {
            messages: chat.messages
        }
    };
    const filter = { _id: chat._id };
    const result = await Chat.updateOne(filter, update).populate('messages').populate('users').populate({
        path: 'messages',
        populate: {
          path: 'sender',
        },
      }).exec();
    return chat._id;
};

const allMessagesChatById = async (id) => {
    const chat = await getChatById(id);
    if (!chat)
        return null;
    return chat.messages;
};

export {
    createChat,
    getChatById,
    getAllChats,
    deleteChatById,
    updateChatById,
    allMessagesChatById,
    getUsersById
};