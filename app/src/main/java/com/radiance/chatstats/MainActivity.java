package com.radiance.chatstats;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private Cursor rCursor, sCursor;
    TextView text;
    String address;
    ConversationThread conversationThread;
    ArrayList<Conversation> messages;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //need to add fragments now
        //set references
        text = (TextView)findViewById((R.id.textView));
       // text.setText("hi");
        //get Contact address, any one
// arbitrary position, just checking if messages work
        // address =  cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

        address = "+16479685968";
        rCursor = getContentResolver().query(Uri.parse("content://sms/inbox"),new String [] {"address", "body", "date"},null,null,null);
        sCursor = getContentResolver().query(Uri.parse("content://sms/sent"),new String [] {"address", "body", "date"},null,null,null);
        conversationThread = new ConversationThread(rCursor,sCursor,address);
        messages = conversationThread.getConversations();
       Analytics analytics = new Analytics (conversationThread);
       text.setText(""+ analytics.getResponseTime());
        // mCursor.moveToFirst();

    }

    public void displayMessage (View view){
        //get address via itemClickListener


        //get messages from address


        //get inbox

        //get sent

        //merge and sort by unix time

        //call stats

        //messages = conversationThread.getMessages();
        text.setText(messages.get(i).toString()+ "\n Date End:" + messages.get(i).getDateEnd() + "\n Date Start:"+messages.get(i).getDateStart() );
        i++;
        i%=messages.size();
        //mCursor.moveToNext();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
