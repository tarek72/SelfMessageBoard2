package com.example.tarek72.slefmessageboard2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MessageAdapter.OnClickListener, MessageDetailsFragment.MessageDeletedListener {
    private static final String TAG = "MainActivity";
    private static final String MESSAGES = "messages";



    ImageButton sendButton;
    RecyclerView recyclerView;
    EditText usernameEditText;
    EditText messageEditText;

    private MessageAdapter mAdapter;



    @Override
    public void onSaveInstanceState(Bundle outState) {
        ArrayList<String> messages;
        messages = new ArrayList<String>(mAdapter.data.size());
        for (Message msg : mAdapter.data) {
            messages.add(msg.toString());

        }
        outState.putStringArrayList(MESSAGES, messages);

        super.onSaveInstanceState(outState);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.list);


        ArrayList<Message> input = getInput(savedInstanceState);
        mAdapter = new MessageAdapter(input,this);

        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sendButton = (ImageButton) findViewById(R.id.send);
        messageEditText = (EditText) findViewById(R.id.messageEditText);
        usernameEditText = (EditText) findViewById(R.id.usernameEditText);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(messageEditText.getText()) && TextUtils.isEmpty(usernameEditText.getText())) {
                    Snackbar.make(messageEditText, "must enter a message and a name to send", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                Message insertedMessage = new Message(usernameEditText.getText().toString(), messageEditText.getText().toString(), System.currentTimeMillis());
//                Message insertedMessage = new Message("ttttt", messageEditText.getText().toString(), System.currentTimeMillis());
                mAdapter.addMessage(insertedMessage);
                messageEditText.setText("");
                usernameEditText.setText("");

            }
        });

        messageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
//                if () {
                sendButton.setEnabled(!TextUtils.isEmpty(s)&& !TextUtils.isEmpty(usernameEditText.getText()));
//                }
            }
        });

        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
//                if () {
                sendButton.setEnabled(!TextUtils.isEmpty(s) && !TextUtils.isEmpty(messageEditText.getText()));
//                }
            }
        });
    }


    private ArrayList<Message> getInput(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return new ArrayList<>();
        }
        if (savedInstanceState.getStringArrayList(MESSAGES) == null) {
            return new ArrayList<>();
        }
        ArrayList<Message> output = new ArrayList<>();
        for (String singleMessage : Objects.requireNonNull(savedInstanceState.getStringArrayList(MESSAGES))) {
            output.add(new Message(usernameEditText.getText().toString(), messageEditText.getText().toString(), System.currentTimeMillis()));

        }
        return output;
    }


    @Override
    public void onClick(Message message) {
        MessageDetailsFragment frag = MessageDetailsFragment.newInstance(message);
        FragmentManager manager = getSupportFragmentManager();

        manager.beginTransaction().add(R.id.frame,frag).addToBackStack(null)
                .commit();
    }

    @Override
    public void onMessageDeleted(Message msg) {
        mAdapter.removeItem(msg);
        getSupportFragmentManager().popBackStack();
    }


    public void onMessageDoNothing() {

    }
}

