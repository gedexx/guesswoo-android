package com.guesswoo.android.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import com.guesswoo.android.GuessWooApplication;
import com.guesswoo.android.R;
import com.guesswoo.android.adapter.MessageAdapter;
import com.guesswoo.android.domain.Message;
import com.guesswoo.android.fragment.MainFragment_;
import com.guesswoo.android.helper.database.GuessWooDatabaseHelper;
import com.guesswoo.android.service.rest.response.MessageResponseTemp;
import com.j256.ormlite.dao.Dao;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.springframework.core.NestedRuntimeException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.sql.SQLException;
import java.text.DateFormat;

@EActivity(R.layout.activity_game)
@OptionsMenu(R.menu.menu_game)
public class GameActivity extends AppCompatActivity {

    @App
    GuessWooApplication application;

    @ViewById(R.id.lvMessages)
    protected ListView lvMessages;

    @ViewById(R.id.etMessage)
    protected EditText etMessage;

    private MessageAdapter messageAdapter;

    private String gameId;
    private String username;

    private MessageResponseTemp messageResponse;

    @OrmLiteDao(helper = GuessWooDatabaseHelper.class)
    Dao<Message, String> messageDao;

    @AfterViews
    protected void init() {

        Bundle bundle = getIntent().getExtras();
        gameId = bundle.getString(MainFragment_.GAME);
        username = bundle.getString(MainFragment_.USERNAME);

        getSupportActionBar().setTitle(username);

        CharSequence hintSendMessage = etMessage.getHint() + " " + username;
        etMessage.setHint(hintSendMessage);

        try {
            messageAdapter = new MessageAdapter(this, R.layout.listview_message_row, messageDao.queryForEq
                    ("gameId", gameId));
            lvMessages.setAdapter(messageAdapter);
        } catch (SQLException e) {
            Log.e(GameActivity.class.getName(), "Can't retrieve messages data", e);
            throw new RuntimeException(e);
        }
    }

    @Click(R.id.btSend)
    public void onBtSendClick() {

        String messageText = etMessage.getText().toString();
        if (TextUtils.isEmpty(messageText)) {
            return;
        }

        doSendMessageInBackground(messageText);
    }

    @UiThread
    protected void saveMessageAndDisplay() {
        try {

            Message message = new Message();
            message.setId(messageResponse.getId());
            message.setGameId(messageResponse.getTo());
            message.setBody(messageResponse.getContent().toString());
            message.setDateTime(DateFormat.getDateTimeInstance().format(messageResponse.getDate()));
            message.setIsMe(true);

            messageDao.createIfNotExists(message);

            etMessage.setText("");

            displayMessage(message);

        } catch (SQLException e) {
            Log.e(GameActivity.class.getName(), "Can't insert message", e);
            throw new RuntimeException(e);
        }
    }

    @Background
    protected void doSendMessageInBackground(String message) {
        try {
            // Construction des paramètres à passer au login
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.set("message", message);

            messageResponse = application.getGameService().sendMessage(username, formData);

            saveMessageAndDisplay();
        } catch (NestedRuntimeException e) {
            messageResponse = null;
        }
    }

    public void displayMessage(Message message) {
        messageAdapter.add(message);
        messageAdapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        lvMessages.setSelection(lvMessages.getCount() - 1);
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
