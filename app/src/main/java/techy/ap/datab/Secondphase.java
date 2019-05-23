package techy.ap.datab;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;

public class Secondphase extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Contact> contactArrayList=new ArrayList<>();
    private Contact contact;
    private DatabaseHandler db;
    SwipeController swipeController = null;
    private RecyclerView.ViewHolder currentItemViewHolder = null;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.childlayout);
        contactArrayList=getData();
       // final SwipeController swipeController=new SwipeController();

        recyclerView=(RecyclerView)findViewById(R.id.recycleview);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
                swipeController.onDraw(c);
            }
        });

      //  setData();


        recyclerViewAdapter=new RecyclerViewAdapter(contactArrayList,this);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

        db=new DatabaseHandler(this);

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                recyclerViewAdapter.contacts.remove(position);
               // db.deletedata(contact);
                recyclerViewAdapter.notifyItemRemoved(position);
                recyclerViewAdapter.notifyItemRangeChanged(position, recyclerViewAdapter.getItemCount());
            }
        });

        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });



    }



    private ArrayList<Contact> getData() {

       // db.getAllRecords();
        db=new DatabaseHandler(this);


        //ArrayList<Contact> contactArrayList=new ArrayList<>();
        contactArrayList=db.getAllRecords();

         return contactArrayList;
    }
}
