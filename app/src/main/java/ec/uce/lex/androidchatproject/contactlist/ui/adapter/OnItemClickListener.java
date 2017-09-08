package ec.uce.lex.androidchatproject.contactlist.ui.adapter;

import ec.uce.lex.androidchatproject.entities.User;

/**
 * Created by Alexis on 08/09/2017.
 */

public interface OnItemClickListener {
    void onItemClick(User user);
    void onItemLongClick(User user);
}
