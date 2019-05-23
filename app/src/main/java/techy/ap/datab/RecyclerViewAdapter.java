package techy.ap.datab;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    public ArrayList<Contact>contacts;
    private Context context;
    private DatabaseHandler db;
    private MainActivity mainActivity;
    private EditText etname,etemail,etphone;
    private String name,email,phone;
    private static final String TAG="RecyclerAdapter";
    ItemClickListener itemClickListener;


    public RecyclerViewAdapter(ArrayList<Contact> contacts, Context context) {
        this.contacts = contacts;
        this.context = context;
        db=new DatabaseHandler(context);
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview= LayoutInflater.from(context).inflate(R.layout.recyclechild,null,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder myViewHolder, final int i) {
       final Contact contact=contacts.get(i);
       myViewHolder.name.setText(contact.getName());
       myViewHolder.email.setText(contact.getEmail());
       myViewHolder.phone.setText(contact.getPhone());
      // myViewHolder.image.setImageDrawable(R.drawable.image);

        myViewHolder.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemclick(View v, int pos) {

                AlertDialog.Builder alertDialog=new AlertDialog.Builder(context);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    alertDialog.setView(R.layout.dialog_layout);
                    alertDialog.setTitle("Update Now");

                }


                //  View view=LayoutInflater.from(context).inflate(R.layout.dialog_layout,v)
                Intent i=new Intent(context,MainActivity.class);
                i.putExtra("name",contacts.get(pos).getName());
                i.putExtra("email",contacts.get(pos).getEmail());
                i.putExtra("phone",contacts.get(pos).getPhone());
                context.startActivity(i);

            }
        });

      /*  myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //setItemClickListener(i,contact);
                Intent intent=new Intent(context,MainActivity.class);
                String nams=contact.getName().toString();
                String emas=contact.getEmail().toString();
              //  String phos=contact.getPhone().toString();
                intent.putExtra("name",nams.indexOf(i));
                intent.putExtra("email",emas.indexOf(i));
             //   intent.putExtra("phone",phos.indexOf(i));
                context.startActivity(intent);
                Log.d(TAG, "response : "+intent);

            }
        });*/



    }

   /* private void setItemClickListener(int i, Contact contact) {


       *//* mainActivity=new MainActivity();
       etname=mainActivity.findViewById(R.id.ename);
       etemail=mainActivity.findViewById(R.id.eemail);
       etphone=mainActivity.findViewById(R.id.ephone);

       etname.setText(contact.getName().indexOf(i));
       etemail.setText(contact.getEmail().indexOf(i));
       etphone.setText(contact.getPhone().indexOf(i));
*//*

    }*/

    private void updateData() {
        name=etname.getText().toString().trim();
        email=etemail.getText().toString().trim();

    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView image;
        TextView name,email,phone;
        ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.image);
            name=(TextView)itemView.findViewById(R.id.name1);
            email=(TextView)itemView.findViewById(R.id.email1);
            phone=(TextView)itemView.findViewById(R.id.phone1);
            itemView.setOnClickListener(this);



        }

        @Override
        public void onClick(View v) {

            this.itemClickListener.onItemclick(v,getLayoutPosition());

        }

        public void setOnItemClickListener(ItemClickListener ic){
            this.itemClickListener=ic;

        }

    }
}
