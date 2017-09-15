package ec.uce.lex.androidchatproject.contactlist.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ec.uce.lex.androidchatproject.R;
import ec.uce.lex.androidchatproject.contactlist.ContactListPresenter;
import ec.uce.lex.androidchatproject.contactlist.ui.adapter.ContactListAdapter;
import ec.uce.lex.androidchatproject.contactlist.ui.adapter.OnItemClickListener;
import ec.uce.lex.androidchatproject.lib.GlideImageLoader;
import ec.uce.lex.androidchatproject.entities.User;
import ec.uce.lex.androidchatproject.lib.ImageLoader;

public class ContactListActivity extends AppCompatActivity implements ContactListView, OnItemClickListener {

    private ContactListPresenter presenter;
    private ContactListAdapter adapter;

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.recyclerViewContacts)
    RecyclerView recyclerViewContacts;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        ButterKnife.bind(this);

        setupAdapter();
        setupRecyclerView();

//        presenter.onCreate();
        setupToolbar();
    }

    private void setupRecyclerView() {
        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewContacts.setAdapter(adapter);
    }

    private void setupToolbar(){
//        toolbar.setTitle(presenter.getCurrentUserEmail());
        setSupportActionBar(toolbar);

    }
    private void setupAdapter(){
        ImageLoader imageLoader = new GlideImageLoader(this.getApplicationContext());
/*
        User user= new User();
        user.setOnline(false);
        user.setEmail("galegale1024@gmail.com");
        adapter = new ContactListAdapter(Arrays.asList(new User[]{user}), imageLoader, this);
*/
        adapter = new ContactListAdapter(new ArrayList<User>(), imageLoader, this);

    }

    @OnClick(R.id.fab)
    public void addContact() {
        Toast.makeText(this, "Click en Boton", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
//        presenter.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
//        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        presenter.onResume();
    }

    @Override
    public void onContactAdded(User user) {

    }

    @Override
    public void onContactChanged(User user) {

    }

    @Override
    public void onContactRemoved(User user) {

    }

    @Override
    public void onItemClick(User user) {

    }

    @Override
    public void onItemLongClick(User user) {

    }
}
