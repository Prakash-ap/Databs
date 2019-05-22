package techy.ap.datab;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    TextView name,email,phone;
    Button save,view,update;
    DatabaseHandler db;
    private Context context;
    ImageView image;
    String namv,emav,phnv;
    private ArrayList<Contact>contacts;
    private Contact contact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=(TextView)findViewById(R.id.ename);
        email=(TextView)findViewById(R.id.eemail);
        phone=(TextView)findViewById(R.id.ephone);
        image=(ImageView)findViewById(R.id.mainimage);

        save=(Button)findViewById(R.id.bsave);
        view=(Button)findViewById(R.id.bview);
        update=(Button)findViewById(R.id.bupdate);
        db=new DatabaseHandler(this);
        contact=new Contact();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                namv=name.getText().toString();
                emav=email.getText().toString();
                phnv=phone.getText().toString();

               contacts=new ArrayList<>();
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

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String aa,bb,cc;
                aa=name.getText().toString();
                bb=email.getText().toString();
                cc=phone.getText().toString();

                ArrayList<Contact>contacts=new ArrayList<>();
                Contact contact;
                contact=new Contact();
               // int position=contacts.get()
                contact.setName(aa);
                contact.setEmail(bb);
                contact.setPhone(cc);

                contacts.add(contact);
                db.updatedata(contact);

                Intent intent=new Intent(MainActivity.this,Secondphase.class);
                startActivity(intent);




            }
        });









    }
}
