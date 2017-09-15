package ec.uce.lex.androidchatproject.contactlist.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import ec.uce.lex.androidchatproject.R;
import ec.uce.lex.androidchatproject.domain.AvatarHelper;
import ec.uce.lex.androidchatproject.lib.ImageLoader;
import ec.uce.lex.androidchatproject.entities.User;

/**
 * Created by Alexis on 08/09/2017.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {


    private List<User> contactList;
    private ImageLoader imageLoader;
    private OnItemClickListener clickListener;

    public ContactListAdapter(List<User> contactList, ImageLoader imageLoader, OnItemClickListener clickListener) {
        this.contactList = contactList;
        this.imageLoader = imageLoader;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_contact, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = contactList.get(position);
        holder.setClickListener(user, clickListener);

        String email = user.getEmail();
        boolean online = user.isOnline();
        String status = online ? "online" : "offline";
        int color = online ? Color.GREEN : Color.RED;

        holder.txtUser.setText(email);
        holder.txtStatus.setText(status);
        holder.txtStatus.setTextColor(color);

        imageLoader.load(holder.imgAvatar, AvatarHelper.getAvatarUrl(email));
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder {
        @BindView(R.id.imgAvatar)
        CircleImageView imgAvatar;
        @BindView(R.id.txtUser)
        TextView txtUser;
        @BindView(R.id.txtStatus)
        TextView txtStatus;

        private View view;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.view= itemView;
        }
        public void setClickListener(final User user,
                                     final OnItemClickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(user);
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onItemLongClick(user);
                    return false;
                }
            });
        }

    }
}
