package techy.ap.datab;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.Image;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.media.ExifInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    EditText name, email, phone;
    Button save, view, update;
    DatabaseHandler db;
    private Context context;
    ImageView image;
    String namv, emav, phnv;
    private ArrayList<Contact> contacts;
    private Contact contact;
    Bitmap bitmap;
    byte[] photo;
    private int GALLERY = 2, CAMERA = 1;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    File gallerypic;
    private String selectedImagePath;
    Uri contentUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.ename);
        email = (EditText) findViewById(R.id.eemail);
        phone = (EditText) findViewById(R.id.ephone);
        image = (ImageView) findViewById(R.id.mainimage);

        save = (Button) findViewById(R.id.bsave);
        view = (Button) findViewById(R.id.bview);
        update = (Button) findViewById(R.id.bupdate);
        db = new DatabaseHandler(this);
        contact = new Contact();
        //setData();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                namv = name.getText().toString();
                emav = email.getText().toString();
                phnv = phone.getText().toString();

                if (namv.equals("") || emav.equals("") || phnv.equals("")) {
                    Toast.makeText(MainActivity.this, "Please Fill All the fields", Toast.LENGTH_SHORT).show();
                } else {

                    contacts = new ArrayList<>();
                    Contact contact;
                    contact = new Contact();
                    contact.setName(namv);
                    contact.setEmail(emav);
                    contact.setPhone(phnv);
                    contacts.add(contact);
                    db.insertdata(contact);
                    Toast.makeText(MainActivity.this, "Data Inserted Succesfully", Toast.LENGTH_SHORT).show();

                    name.setText("");
                    email.setText("");
                    phone.setText("");


                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Secondphase.class);
                startActivity(intent);
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPictureDialog();
               /* Intent intentimage=new Intent(Intent.ACTION_PICK);
                intentimage.setType("image/*");
                startActivityForResult(intentimage,5);*/
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String aa, bb, cc;
                aa = name.getText().toString();
                bb = email.getText().toString();
                cc = phone.getText().toString();


                ArrayList<Contact> contacts = new ArrayList<>();
                Contact contact;
                contact = new Contact();
                https://android.jlelse.eu/androids-new-image-capture-from-a-camera-using-file-provider-dd178519a954
                // int position=contacts.get()
                contact.setName(aa);
                contact.setEmail(bb);
                contact.setPhone(cc);

                contacts.add(contact);
                db.updatedata(contact);


                Intent intent = new Intent(MainActivity.this, Secondphase.class);
                startActivity(intent);


            }
        });


    }

    private void showPictureDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Image");
        String[] listItems = {"Take Picture", "Select from Gallery"};
        builder.setItems(listItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        TakePhotoFromCamera();
                        break;
                    case 1:
                        PickFromGallery();
                        break;
                }
            }
        });
        builder.show();
    }

    private void PickFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void TakePhotoFromCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(cameraIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(cameraIntent, CAMERA);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {

                contentUri = data.getData();



                   // Picasso.get().load(contentUri).centerCrop().resize(400,400).into(image);
                Bitmap gallerybitmap = null;

                try {

                    gallerybitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), contentUri);


                   // rotateImage(gallerybitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
               // String path = saveImage(gallerybitmap);
                    Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();

                   image.setImageBitmap(gallerybitmap);



        } else if (requestCode == CAMERA) {
            if (data != null && data.getExtras() != null) {


                Bitmap thumnail = (Bitmap) data.getExtras().get("data");
                //Picasso.get().load(contentUri).centerCrop().resize(400,400).into(image);

                image.setImageBitmap(thumnail);
                saveImage(thumnail);
                Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();


            }
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private String saveImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);



        gallerypic = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + IMAGE_DIRECTORY);

        if (!gallerypic.exists()) {
            gallerypic.mkdirs();
        }

        String timeStamps=new SimpleDateFormat("yyyy_MM_dd_HH:mm:ss", Locale.getDefault()).format(new Date());
        Log.d("MainActivity.class", "timestamps: "+timeStamps);

        String  fname="Images-"+timeStamps+".jpg";
        Log.d("Main", "saveImage: "+fname);
        File file=new File(gallerypic,fname);
        if(file.exists())
            file.delete();
            try{
                FileOutputStream out=new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG,90,out);
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                        Uri.parse("file://" +Environment.getExternalStorageDirectory())));
                out.flush();
                out.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }



       /* try {
            File f = new File(gallerypic, Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(byteArrayOutputStream.toByteArray());
            MediaScannerConnection.scanFile(this, new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());
            return f.getAbsolutePath();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return "";
    }

    private void rotateImage(Bitmap bitmap){
        ExifInterface exifInterface=null;
        try{
            exifInterface=new ExifInterface(selectedImagePath);

        } catch (IOException e) {
            e.printStackTrace();
        }

        int orientation=exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_UNDEFINED);
        Matrix matrix=new Matrix();
        switch (orientation){
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
                default:
        }
        Bitmap bitmap1=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        image.setImageBitmap(bitmap1);
    }

  /*  private void requestMutiplePermission(){
        Dexter
    }*/


   /*   if(resultCode ==this.RESULT_CANCELED){
        return;
    }

        if(requestCode ==GALLERY){

        if(resultCode==RESULT_OK){
            Uri imageuri=data.getData();
            if(imageuri!=null){
                bitmap=decodeUri(imageuri,200);
                image.setImageBitmap(bitmap);

            }
        }
    }*/

 /*   protected Bitmap decodeUri(Uri slectImage,int REQUIRED_SIZE){
        try{
            BitmapFactory.Options bimapoptions=new BitmapFactory.Options();
            bimapoptions.inJustDecodeBounds=true;
            BitmapFactory.decodeStream(getContentResolver().openInputStream(slectImage),null,bimapoptions);

            int width_temp=bimapoptions.outWidth,height_temp=bimapoptions.outHeight;
            int scale=1;
            while (true){
                if(width_temp/2 < REQUIRED_SIZE || height_temp/2 <REQUIRED_SIZE){
                    break;
                }
                width_temp /=2;
                height_temp /=2;
                scale *= 2;

            }

            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inSampleSize=scale;
            return BitmapFactory.decodeStream(getContentResolver().openInputStream(slectImage),null,options);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }


    private byte[] profileimage(Bitmap b){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG,0,byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }*/
}
