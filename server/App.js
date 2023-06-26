import express from 'express'
import bodyParser from 'body-parser'
import routerUser from './routes/user.js'
import routerChat from './routes/chat.js'
import routerLogin from './routes/login.js'
import cors from 'cors'
import mongoose from 'mongoose'
import customEnv  from 'custom-env'
import http from 'http'
import { Server } from 'socket.io'
import { init } from './services/firebase.js'

var app = express();
app.use(cors());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json({ limit: '2mb' }));
app.use(express.json());
app.use(express.static('public'));
const server = http.createServer(app);
const io = new Server(server, {
  cors: {
    origin: "*",
    methods: ["GET", "POST", "DELETE"]
  },
});

io.on('connection', (socket) => {

  socket.on('join_room', (room) => {
    socket.join(room.room);
  });

  socket.on('send_message', (data) => {
    socket.to(data.room).emit("receive_message", data);
  });

  socket.on('message_sent', () => {
    socket.broadcast.emit("rcv_msg");
    socket.broadcast.emit("receive_message");
  });

  socket.on('delete_chat', () => {
    socket.broadcast.emit("delete_request");
  });

  socket.on('chat_deleted', (data) => {
    socket.to(data.room).emit("delete_this", data);
  });

  socket.on('add_chat', (data) => {
    socket.broadcast.emit("added_chat");
  });

});
server.listen(6001);

app.use('/api/Users', routerUser);
app.use('/api/Chats', routerChat);
app.use('/api/Tokens', routerLogin);

customEnv.env(process.env.NODE_ENV, './config');
mongoose.connect(process.env.CONNECTION_STRING,
  {
    useNewUrlParser: true,
    useUnifiedTopology: true
  });

let hasBeenCalled = false;

if (!hasBeenCalled) {
  init();
  hasBeenCalled = true;
}


app.listen(process.env.PORT);


