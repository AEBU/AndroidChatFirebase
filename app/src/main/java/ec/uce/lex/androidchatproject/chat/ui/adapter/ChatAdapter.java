package ec.uce.lex.androidchatproject.chat.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import ec.uce.lex.androidchatproject.entities.ChatMessage;

/**
 * Created by Alexis on 18/09/2017.
 */
public class ChatAdapter  extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void add(ChatMessage msg) {
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
