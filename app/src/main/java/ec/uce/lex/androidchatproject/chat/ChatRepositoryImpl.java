package ec.uce.lex.androidchatproject.chat;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import ec.uce.lex.androidchatproject.chat.events.ChatEvent;
import ec.uce.lex.androidchatproject.domain.FirebaseHelper;
import ec.uce.lex.androidchatproject.entities.ChatMessage;
import ec.uce.lex.androidchatproject.lib.EventBus;
import ec.uce.lex.androidchatproject.lib.GreenRobotEventBus;

/**
 * Created by Alexis on 19/09/2017.
 */
public class ChatRepositoryImpl implements ChatRepository {

    private static final String TAG=ChatRepositoryImpl.class.getSimpleName();
    private String receiver;
    private FirebaseHelper helper;
    private ChildEventListener chatEventListener;
    private EventBus eventBus;

    public ChatRepositoryImpl(){
        this.helper = FirebaseHelper.getInstance();
        this.eventBus= GreenRobotEventBus.getInstance();

    }

    @Override
    public void sendMessage(String msg) {
        String keySender = helper.getAuthUserEmail().replace(".","_");
        ChatMessage chatMessage = new ChatMessage(keySender, msg);
        DatabaseReference chatsReference = helper.getChatsReference(receiver);
        chatsReference.push().setValue(chatMessage);
    }

    @Override
    public void setRecipient(String recipient) {
        this.receiver = recipient;
    }

    @Override
    public void destroyChatListener() {
        chatEventListener = null;

    }

    @Override
    public void subscribeForChatUpates() {
        if (chatEventListener == null) {
            chatEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildKey) {
                    ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                    String msgSender = chatMessage.getSender();
                    msgSender = msgSender.replace("_",".");

                    String currentUserEmail = helper.getAuthUserEmail();
                    chatMessage.setSentByMe(msgSender.equals(currentUserEmail));

                    ChatEvent chatEvent = new ChatEvent(chatMessage);
                    eventBus.post(chatEvent);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String previousChildKey) {}

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {}

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onCancelled(DatabaseError firebaseError) {}
            };
            helper.getChatsReference(receiver).addChildEventListener(chatEventListener);
        }
    }

    @Override
    public void unSubscribeForChatUpates() {
        if (chatEventListener != null) {
            helper.getChatsReference(receiver).removeEventListener(chatEventListener);
        }
    }

    @Override
    public void changeConnectionStatus(boolean online) {
        helper.changeUserConnectionStatus(online);
    }
}
