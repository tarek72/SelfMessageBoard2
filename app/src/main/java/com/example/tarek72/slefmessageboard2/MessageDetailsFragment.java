package com.example.tarek72.slefmessageboard2;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageDetailsFragment extends Fragment {

    private static final String TAG = "MessageDetailsFragment";
    public static final String KEY_MESSAGE_NAME = "name";
    public static final String KEY_MESSAGE_CONTENT = "content";
    public static final String KEY_MESSAGE_TIME_STAMP = "timeStamp";

    @BindView(R.id.textViewAuthor)
    TextView title;
    @BindView(R.id.textViewTimeStamp)
    TextView timestamp;
    @BindView(R.id.textViewMessageContent)
    TextView content;




    //widgets
    private Button mDeleteButton;
    MessageDeletedListener listener;
    Message message;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.message_details_fragment, container, false);
        updateMessage();
        ButterKnife.bind(this, v);
        title.setText(message.getName());
        timestamp.setText(DateUtils.getRelativeTimeSpanString(message.getTimestamp(), System.currentTimeMillis(), DateUtils.FORMAT_ABBREV_ALL));
        content.setText(message.getContent());
        mDeleteButton = v.findViewById(R.id.buttonDelete);


        mDeleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.onMessageDeleted(message);
            }
        });


        return v;
    }
    private void updateMessage() {
        if (getArguments() != null) {
            message = new Message(getArguments().get(KEY_MESSAGE_NAME).toString(),getArguments().get(KEY_MESSAGE_CONTENT).toString(),(long)getArguments().get(KEY_MESSAGE_TIME_STAMP));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MessageDeletedListener) {
            this.listener = (MessageDeletedListener) context;
        }
    }


    public static MessageDetailsFragment newInstance(Message message) {

        Bundle args = new Bundle();
        args.putString("name", message.getName());
        args.putString("content", message.getContent());
        args.putLong("timeStamp", message.getTimestamp());

        MessageDetailsFragment fragment = new MessageDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public interface MessageDeletedListener {
        void onMessageDeleted(Message msg);
    }
}
