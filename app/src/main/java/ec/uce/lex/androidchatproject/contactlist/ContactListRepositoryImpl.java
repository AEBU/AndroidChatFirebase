package ec.uce.lex.androidchatproject.contactlist;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import ec.uce.lex.androidchatproject.contactlist.events.ContactListEvent;
import ec.uce.lex.androidchatproject.domain.FirebaseHelper;
import ec.uce.lex.androidchatproject.entities.User;
import ec.uce.lex.androidchatproject.lib.EventBus;
import ec.uce.lex.androidchatproject.lib.GreenRobotEventBus;

/**
 * Created by Alexis on 15/09/2017.
 */
public class ContactListRepositoryImpl implements ContactListRepository {
    private FirebaseHelper helper;
    private ChildEventListener contactEventListener;

    public ContactListRepositoryImpl() {
        this.helper=FirebaseHelper.getInstance();
    }

    @Override
    public void signOff() {
        helper.signOff();

    }

    @Override
    public String getCurrentUserEmail() {
        return helper.getAuthUserEmail();
    }

    @Override
    public void removeContact(String email) {
        String currentUserEmail= helper.getAuthUserEmail();
        helper.getOneContactReference(currentUserEmail,email).removeValue();
        helper.getOneContactReference(email,currentUserEmail).removeValue();
    }

    @Override
    public void destroyListener() {
        contactEventListener=null;

    }

    @Override
    public void subscribeToContactListEvents() {
        if (contactEventListener == null) {
            contactEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildKey) {
                    handleContact(dataSnapshot,ContactListEvent.onContactAdded);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String previousChildKey) {
                    handleContact(dataSnapshot,ContactListEvent.onContactChanged);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    handleContact(dataSnapshot,ContactListEvent.onContactRemoved);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError firebaseError) {}
            };
        }
        helper.getMyContactsReference().addChildEventListener(contactEventListener);
    }


    @Override
    public void unsubscribeToContactListEvents() {
        if (contactEventListener != null){
            helper.getMyContactsReference().removeEventListener(contactEventListener);
        }
    }

    @Override
    public void changeConnectionStatus(boolean online) {
        helper.changeUserConnectionStatus(online);
    }
    private void handleContact(DataSnapshot dataSnapshot, int type) {
        String email= dataSnapshot.getKey();
        email = email.replace("_",".");
        boolean online= ((Boolean) dataSnapshot.getValue()).booleanValue();
        User user = new User();
        user.setEmail(email);
        user.setOnline(online);
        post(type,user);
    }
    private void post(int type, User user) {
        ContactListEvent event= new ContactListEvent();
        event.setEventType(type);
        event.setUser(user);
        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(event);

    }
}
