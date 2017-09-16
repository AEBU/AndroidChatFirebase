package ec.uce.lex.androidchatproject.addcontact;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ec.uce.lex.androidchatproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddContactFragment extends Fragment {


    public AddContactFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_contact, container, false);
    }

}
