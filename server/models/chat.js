import mongoose from 'mongoose';
//need to ask if i can use it?
const Schema = mongoose.Schema;

const Chat = new Schema({
    id: {
        type: String
    },
    users: [{
        type: Schema.Types.ObjectId,
        ref: 'UserOmitPassword',
        nullable: true,
      }],
      messages: [{
        type: Schema.Types.ObjectId,
        ref: 'Message',
        nullable: true,
      }],
});

export default mongoose.model('Chat', Chat);