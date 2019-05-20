package techy.ap.datab;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    TextView name,email,phone;
    Button save,view;
    DatabaseHandler db;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] kp={"aaa","2","dsafsf","aedwfe","dfrftge","ijoijoij","vcnvn,dsfn","bmbgnjgjg","hdgfhegjkgh","jfhghghjtgj"};

        name=(TextView)findViewById(R.id.ename);
        email=(TextView)findViewById(R.id.eemail);
        phone=(TextView)findViewById(R.id.ephone);

        save=(Button)findViewById(R.id.bsave);
        view=(Button)findViewById(R.id.bview);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namv,emav,phnv;
                namv=name.getText().toString();
                emav=email.getText().toString();
                phnv=phone.getText().toString();

                ArrayList<Contact>contacts=new ArrayList<>();
                Contact contact;
                contact=new Contact();
                contact.setName(namv);
                contact.setEmail(emav);
                contact.setPhone(phnv);
                contacts.add(contact);
                db.insertdata(contact);

            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Secondphase.class);
                startActivity(intent);
            }
        });









    }
}
