package com.guesswoo.android.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import com.guesswoo.android.R;
import com.guesswoo.android.adapter.MessageAdapter;
import com.guesswoo.android.domain.Message;
import com.guesswoo.android.fragment.MainFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EActivity(R.layout.activity_game)
@OptionsMenu(R.menu.menu_game)
public class GameActivity extends AppCompatActivity {

    @ViewById(R.id.lvMessages)
    protected ListView lvMessages;

    @ViewById(R.id.etMessage)
    protected EditText etMessage;

    private MessageAdapter messageAdapter;

    @AfterViews
    protected void init() {

        Bundle bundle = getIntent().getExtras();
        String username = bundle.getString(MainFragment_.USERNAME);

        getSupportActionBar().setTitle(username);

        CharSequence hintSendMessage = etMessage.getHint() + " " + username;
        etMessage.setHint(hintSendMessage);

        messageAdapter = new MessageAdapter(this, R.layout.listview_message_row, dummyMessages());
        lvMessages.setAdapter(messageAdapter);
    }

    @Click(R.id.btSend)
    public void onBtSendClick() {

        String messageText = etMessage.getText().toString();
        if (TextUtils.isEmpty(messageText)) {
            return;
        }

        Message message = new Message();
        message.setUserId("122");//dummy
        message.setBody(messageText);
        message.setDateTime(DateFormat.getDateTimeInstance().format(new Date()));
        message.setIsMe(true);

        etMessage.setText("");

        displayMessage(message);
    }

    public void displayMessage(Message message) {
        messageAdapter.add(message);
        messageAdapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        lvMessages.setSelection(lvMessages.getCount() - 1);
    }

    private List<Message> dummyMessages() {

        List<Message> messages = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            Message message = new Message("User 1", "Message " + i, DateFormat.getDateTimeInstance().format(new Date
                    ()), false);
            messages.add(message);
        }

        return messages;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
