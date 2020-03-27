package com.backpakcersourav.intentexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String[] courses = {"Java","Python",".Net","PHP","JavaScript","Django","Android","Flutter","React Native","IOS","Kali Linux"};

    Button btnGo,btnDial,btnBs,btnShow;
    EditText etSite,etPhoneNo;
    TextView tvHttp;
    AutoCompleteTextView tvAuto;

    Button btnShare;
    EditText etShareText;

    Button btnSms;
    EditText etSmsNo;
    EditText etSmsBody;

    EditText etEmailTo,etEmailSubject,etEmailMsg;
    Button btnMail;

    // this method is for doing findViewById setting for each components
    private void setView(){
        // For ACTION_VIEW INTENT
        tvHttp = findViewById(R.id.tvHttp);
        etSite = findViewById(R.id.etSiteName);
        btnGo = findViewById(R.id.btnGo);

        //For ACTION_DIAL INTENT
        btnDial = findViewById(R.id.btnDial);
        etPhoneNo = findViewById(R.id.etPhoneNo);

        // For WEBVIEW EXAMPLE
        btnBs = findViewById(R.id.btnBS);

        // For AUTOCOMPLETE TEXT VIEW EXAMPLE
        tvAuto = findViewById(R.id.actvAuto);
        btnShow = findViewById(R.id.btnShow);

        btnShare = findViewById(R.id.btnShare);
        etShareText = findViewById(R.id.etShareText);

        btnSms = findViewById(R.id.btnSms);
        etSmsNo = findViewById(R.id.etSms);
        etSmsBody = findViewById(R.id.etSmsBody);

        etEmailTo = findViewById(R.id.etTo);
        etEmailSubject = findViewById(R.id.etSubject);
        etEmailMsg = findViewById(R.id.etMailBody);
        btnMail = findViewById(R.id.btnSendEmail);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setView();

        //  AUTOCOMPLETE TEXT VIEW EXAMPLE
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,courses); // creating an ArrayAdapter for middleware
        tvAuto.setThreshold(1); // This line is for how many words type for autocomplete
        tvAuto.setAdapter(arrayAdapter); // setting adapter to autocomplete text view
        btnShow.setOnClickListener(new View.OnClickListener() { // after hit Autocomplete bitton, shwing the text
            @Override
            public void onClick(View view) {
                String selected = tvAuto.getText().toString();
                if(!selected.trim().isEmpty())
                    Toast.makeText(MainActivity.this, "You selected : "+selected, Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Please enter a course", Toast.LENGTH_SHORT).show();
            }
        });


        // for ACTION_VIEW IMPLICIT INTENT
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String URL;
                URL = tvHttp.getText().toString();
                String siteName = etSite.getText().toString();
                if(siteName.isEmpty()){ // setting the error msg if empty
                    etSite.setError("Enter site name");
                    etSite.requestFocus();
                    return;
                }
                // if site name is not empty then go user sspecified site
                URL += siteName;
                Log.d("myapp",URL);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(URL));
                startActivity(intent);
                Toast.makeText(MainActivity.this, "URL : "+URL, Toast.LENGTH_LONG).show();
            }
        });

        // for dialing phone number
        btnDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNo = etPhoneNo.getText().toString();
                Log.d("myapp","PHONE NO : "+phoneNo);
                if(phoneNo.trim().isEmpty()){
                    etPhoneNo.setError("Type phone no");
                    etPhoneNo.requestFocus();
                    return;
                }

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phoneNo));
                startActivity(intent);
            }
        });

        // this is for WebView, BS means 'Backpacker Sourav' - my youtube channel
        btnBs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,WebViewExample.class);
                startActivity(intent);
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = etShareText.getText().toString();
                if(text.trim().isEmpty()){
                    etShareText.setError("type your message");
                    etShareText.requestFocus();
                }
                else{
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT,text);
                    startActivity(Intent.createChooser(intent,"from Sourav app : "));
                }
            }
        });

        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String no = etSmsNo.getText().toString();
                String smsBody = etSmsBody.getText().toString();
                if(no.trim().isEmpty()){
                    etSmsNo.setError("Enter no to msg");
                    etSmsNo.requestFocus();
                }else{
                    Uri phNo = Uri.parse("smsto:"+no);
                    Intent intent = new Intent(Intent.ACTION_SENDTO,phNo);
                    if(intent.resolveActivity(getPackageManager()) != null){
                        intent.putExtra("sms_body",smsBody);
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this, "No relevant apps", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String to = etEmailTo.getText().toString();
                String subject = etEmailSubject.getText().toString();
                String msg = etEmailMsg.getText().toString();

                if(to.trim().isEmpty()){
                    etEmailTo.setError("mail to");
                    etEmailTo.requestFocus();
                }else{
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                    emailIntent.putExtra(Intent.EXTRA_TEXT, msg);

                    //need this to prompts email client only
                    emailIntent.setType("message/rfc822");

                    startActivity(Intent.createChooser(emailIntent, "From Sourav Ganguly app :"));
//                startActivity(emailIntent);
                }


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.about){
            Intent intent = new Intent(getApplicationContext(),About.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.clearData){
            Toast.makeText(this, "Clearing all fields", Toast.LENGTH_SHORT).show();
            etSite.getText().clear();
            etSmsNo.getText().clear();
            etSmsBody.getText().clear();
            etPhoneNo.getText().clear();
            etShareText.getText().clear();
            tvAuto.getText().clear();

        }


        return super.onOptionsItemSelected(item);
    }
}
