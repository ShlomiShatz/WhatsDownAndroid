//import User from './user.js';
import mongoose from 'mongoose';
const Schema = mongoose.Schema;

const Message = new Schema({
    id: {
        type: String
    },
    created: {
        type: Date,
        default: Date.now()
    },
    sender: {
        type: Schema.Types.ObjectId,
        ref: 'UserOmitPassword'
    },
    content: {
        type: String,
        nullable: true
    }
});

export default mongoose.model('Message', Message);