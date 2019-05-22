package techy.ap.datab;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    public ArrayList<Contact>contacts;
    private Context context;

    public RecyclerViewAdapter(ArrayList<Contact> contacts, Context context) {
        this.contacts = contacts;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview= LayoutInflater.from(context).inflate(R.layout.recyclechild,null,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder myViewHolder, int i) {
       Contact contact=contacts.get(i);
       myViewHolder.name.setText(contact.getName());
       myViewHolder.email.setText(contact.getEmail());
       myViewHolder.phone.setText(contact.getPhone());
      // myViewHolder.image.setImageDrawable(R.drawable.image);



    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name,email,phone;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.image);
            name=(TextView)itemView.findViewById(R.id.name1);
            email=(TextView)itemView.findViewById(R.id.email1);
            phone=(TextView)itemView.findViewById(R.id.phone1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(context,MainActivity.class);
                    context.startActivity(intent);

                }
            });

        }
    }
}
