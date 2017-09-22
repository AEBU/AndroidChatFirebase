package ec.uce.lex.androidchatproject.chat.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import ec.uce.lex.androidchatproject.R;
import ec.uce.lex.androidchatproject.chat.ChatPresenter;
import ec.uce.lex.androidchatproject.chat.ChatPresenterImpl;
import ec.uce.lex.androidchatproject.chat.ui.ChatView;
import ec.uce.lex.androidchatproject.chat.ui.adapter.ChatAdapter;
import ec.uce.lex.androidchatproject.domain.AvatarHelper;
import ec.uce.lex.androidchatproject.entities.ChatMessage;
import ec.uce.lex.androidchatproject.lib.GlideImageLoader;
import ec.uce.lex.androidchatproject.lib.ImageLoader;

public class ChatActivity extends AppCompatActivity implements ChatView{

    @BindView(R.id.imgAvatar)
    CircleImageView imgAvatar;
    @BindView(R.id.txtUser)
    TextView txtUser;
    @BindView(R.id.txtStatus)
    TextView txtStatus;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.messageRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.editTxtMessage)
    EditText editTxtMessage;
    @BindView(R.id.btnSendMessage)
    ImageButton btnSendMessage;

    private ChatAdapter adapter;
    private ChatPresenter presenter;


    public final static String EMAIL_KEY = "email";
    public final static String ONLINE_KEY = "online";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        setupAdapter();
        setupRecyclerView();

        presenter = new ChatPresenterImpl(this);
        presenter.onCreate();
        setToolbarData(getIntent());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onMessageReceived(ChatMessage msg) {
        adapter.add(msg);
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }

    private void setupAdapter() {
        /*
        ChatMessage msg1=new ChatMessage();
        ChatMessage msg2=new ChatMessage();
        ChatMessage msg3=new ChatMessage();

        msg1.setMsg("Hola como estas");
        msg2.setMsg("Aquí bien");
        msg3.setMsg("Y como has pasado");

        msg1.setSentByMe(false);
        msg2.setSentByMe(false);
        msg3.setSentByMe(true);
        adapter= new ChatAdapter(this, Arrays.asList(new ChatMessage[]{msg1,msg2,msg3}));
*/
        adapter = new ChatAdapter(this,new ArrayList<ChatMessage>());
    }

    private void setToolbarData(Intent i) {
        String recipient = i.getStringExtra(EMAIL_KEY);
        presenter.setChatRecipient(recipient);

        boolean online = i.getBooleanExtra(ONLINE_KEY, false);
        String status = online ? "online" : "offline";
        int color = online ? Color.GREEN : Color.RED;

        txtUser.setText(recipient);
        txtStatus.setText(status);
        txtStatus.setTextColor(color);

        // Application app =  getApplication();
        ImageLoader imageLoader = new GlideImageLoader(getApplicationContext());
        imageLoader.load(imgAvatar, AvatarHelper.getAvatarUrl(recipient));
        setSupportActionBar(toolbar);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        presenter.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @OnClick(R.id.btnSendMessage)
    public void sendMessage(){
        presenter.sendMessage(editTxtMessage.getText().toString());
        editTxtMessage.setText("");
    }
}
