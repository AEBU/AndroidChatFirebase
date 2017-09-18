package ec.uce.lex.androidchatproject.addcontact;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import ec.uce.lex.androidchatproject.addcontact.events.AddContactEvent;
import ec.uce.lex.androidchatproject.domain.FirebaseHelper;
import ec.uce.lex.androidchatproject.entities.User;
import ec.uce.lex.androidchatproject.lib.EventBus;
import ec.uce.lex.androidchatproject.lib.GreenRobotEventBus;

import static android.R.attr.id;

/**
 * Created by Alexis on 18/09/2017.
 */
public class AddContactRepositoryImpl implements AddContactRepository {

    private EventBus eventBus;
    private FirebaseHelper helper;

    public AddContactRepositoryImpl() {
        this.helper=FirebaseHelper.getInstance();
        this.eventBus= GreenRobotEventBus.getInstance();
    }

    @Override
    public void addContact(final String email) {
        final String key = email.replace(".","_");
        DatabaseReference userReference = helper.getUserReference(email);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user!=null){
                    DatabaseReference myContactsReference = helper.getMyContactsReference();
                    myContactsReference.child(key).setValue(user.isOnline());

                    String currentUserKey=helper.getAuthUserEmail();
                    currentUserKey=currentUserKey.replace(".","_");

                    DatabaseReference reverserContactReference=helper.getContactsReference(email);
                    reverserContactReference.child(currentUserKey).setValue(User.ONLINE);

                    postSuccess();

                }else postError();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                postError();
            }
        });
}

    private void postSuccess(){
        post(false);
    }

    private void postError(){
        post(true);
    }

    private  void post(boolean error){
        AddContactEvent event=new AddContactEvent();
        event.setError(error);
        eventBus.post(event);
    }

}
