package ec.uce.lex.androidchatproject.chat.ui.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ec.uce.lex.androidchatproject.R;
import ec.uce.lex.androidchatproject.entities.ChatMessage;
import ec.uce.lex.androidchatproject.entities.User;

/**
 * Created by Alexis on 18/09/2017.
 */
public class ChatAdapter  extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{

    private Context context;
    private List<ChatMessage> chatMessages;

    public ChatAdapter(Context context,List<ChatMessage> chatMessages) {
        this.context = context;
        this.chatMessages = chatMessages;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessages.get(position);

        String msg = chatMessage.getMsg();
        holder.txtMessage.setText(msg);

        int color = fetchColor(R.attr.colorPrimary);
        int gravity = Gravity.LEFT;

        if (!chatMessage.isSentByMe()) {
            gravity = Gravity.RIGHT;
            color = fetchColor(R.attr.colorAccent);
        }

        holder.txtMessage.setBackgroundColor(color);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)holder.txtMessage.getLayoutParams();
        params.gravity = gravity;
        holder.txtMessage.setLayoutParams(params);
    }


    private int fetchColor(int color) {
        TypedValue typedValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(typedValue.data,
                new int[] { color });
        int returnColor = a.getColor(0, 0);
        a.recycle();
        return returnColor;
    }

    /*
    public void add(ChatMessage msg) {
        if (!chatMessages.contains(msg)){
            chatMessages.add(msg);
            notifyDataSetChanged();
        }
    }
    */
    public void add(ChatMessage message) {
        if (!alreadyInAdapter(message)) {
            this.chatMessages.add(message);
            this.notifyDataSetChanged();
        }
    }

    private boolean alreadyInAdapter(ChatMessage newMsg){
        boolean alreadyInAdapter = false;
        for (ChatMessage msg : this.chatMessages) {
            if (msg.getMsg().equals(newMsg.getMsg()) &&
                    msg.getSender().equals(newMsg.getSender())) {
                alreadyInAdapter = true;
                break;
            }
        }

        return alreadyInAdapter;
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txtMessage)  TextView txtMessage;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
