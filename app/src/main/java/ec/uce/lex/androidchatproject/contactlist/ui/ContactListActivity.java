package ec.uce.lex.androidchatproject.contactlist.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ec.uce.lex.androidchatproject.R;
import ec.uce.lex.androidchatproject.addcontact.ui.AddContactFragment;
import ec.uce.lex.androidchatproject.contactlist.ContactListPresenter;
import ec.uce.lex.androidchatproject.contactlist.ContactListPresenterImpl;
import ec.uce.lex.androidchatproject.contactlist.ui.adapter.ContactListAdapter;
import ec.uce.lex.androidchatproject.contactlist.ui.adapter.OnItemClickListener;
import ec.uce.lex.androidchatproject.lib.GlideImageLoader;
import ec.uce.lex.androidchatproject.entities.User;
import ec.uce.lex.androidchatproject.lib.ImageLoader;
import ec.uce.lex.androidchatproject.login.LoginActivity;

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
        presenter= new ContactListPresenterImpl(this);//le enviamos de parametro la actividad porque esta es la vista
        presenter.onCreate();
        setupToolbar();
    }

    private void setupRecyclerView() {
        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewContacts.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout){
            presenter.signOff();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contactlist,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setupToolbar(){
        toolbar.setTitle(presenter.getCurrentUserEmail());
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
        new AddContactFragment().show(getSupportFragmentManager(),getString(R.string.addcontact_message_title));
    }

    @Override
    protected void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onContactAdded(User user) {
        adapter.add(user);
    }

    @Override
    public void onContactChanged(User user) {
        adapter.update(user);

    }

    @Override
    public void onContactRemoved(User user) {
        adapter.remove(user);

    }

    @Override
    public void onItemClick(User user) {

    }

    @Override
    public void onItemLongClick(User user) {
        presenter.removeContact(user.getEmail());
    }
}
