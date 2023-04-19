package com.example.journeyjournal;

import static com.example.journeyjournal.DBmain.TABLENAME;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    Context context;
    int singledata;
    ArrayList<Model>modelArrayList;
    SQLiteDatabase sqLiteDatabase;
    //generate constructor
    DBmain dBmain;
    ImageView avatar, showphoto;
    TextView showtitle, showdescription, showdate;
    int id=0;
    RelativeLayout relative;


    public MyAdapter(Context context, int singledata, ArrayList<Model> modelArrayList, SQLiteDatabase sqLiteDatabase) {
        this.context = context;
        this.singledata = singledata;
        this.modelArrayList = modelArrayList;
        this.sqLiteDatabase = sqLiteDatabase;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.singledata,null);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Model model=modelArrayList.get(position);


        byte[]image=model.getProavatar();
        Bitmap bitmap= BitmapFactory.decodeByteArray(image,0,image.length);
        holder.imageavatar.setImageBitmap(bitmap);
        holder.txtname.setText(model.getUsername());
        holder.txtdate.setText(model.getDate());
        //--------------------------------------
        holder.txtdescription.setText(model.getDescription());




        //flow menu
        holder.flowmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(context,holder.flowmenu);
                popupMenu.inflate(R.menu.flow_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.edit_menu:
                                ///////
                                //edit operation
                                Bundle bundle=new Bundle();
                                bundle.putInt("id",model.getId());
                                bundle.putByteArray("avatar",model.getProavatar());
                                bundle.putString("name",model.getUsername());
                                bundle.putString("description",model.getDescription());
                                bundle.putString("date",model.getDate());
                                //----------------------------

                                Intent intent=new Intent(context, CreateJournal.class);
                                intent.putExtra("userdata",bundle);
                                context.startActivity(intent);
                                break;
                            case R.id.delete_menu:
                                ///delete operation
                                DBmain dBmain=new DBmain(context);
                                sqLiteDatabase=dBmain.getReadableDatabase();
                                long recdelete=sqLiteDatabase.delete(TABLENAME,"id="+model.getId(),null);
                                if (recdelete!=-1){
                                    Toast.makeText(context, "Journal deleted", Toast.LENGTH_SHORT).show();
                                    //remove position after deleted
                                    modelArrayList.remove(position);
                                    //update data
                                    notifyDataSetChanged();
                                }
                                break;
                            default:
                                return false;
                        }
                        return false;
                    }
                });
                //display menu
                popupMenu.show();
            }
        });
        holder.imageavatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle=new Bundle();
                bundle.putByteArray("avatar",model.getProavatar());
                bundle.putString("name",model.getUsername());
                bundle.putString("description",model.getDescription());
                bundle.putString("date",model.getDate());
                //----------------------------

                Intent intent=new Intent(context,ViewJournal.class);
                intent.putExtra("userdata",bundle);
                context.startActivity(intent);

            }

        });
        holder.txtname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putByteArray("avatar",model.getProavatar());
                bundle.putString("name",model.getUsername());
                bundle.putString("description",model.getDescription());
                bundle.putString("date",model.getDate());
                //----------------------------

                Intent intent=new Intent(context,ViewJournal.class);
                intent.putExtra("userdata",bundle);
                context.startActivity(intent);

            }
        });
        holder.txtdescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putByteArray("avatar",model.getProavatar());
                bundle.putString("name",model.getUsername());
                bundle.putString("description",model.getDescription());
                bundle.putString("date",model.getDate());
                //----------------------------

                Intent intent=new Intent(context,ViewJournal.class);
                intent.putExtra("userdata",bundle);
                context.startActivity(intent);

            }
        });
        holder.txtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putByteArray("avatar",model.getProavatar());
                bundle.putString("name",model.getUsername());
                bundle.putString("description",model.getDescription());
                bundle.putString("date",model.getDate());
                //----------------------------

                Intent intent=new Intent(context,ViewJournal.class);
                intent.putExtra("userdata",bundle);
                context.startActivity(intent);

            }
        });

    }
    private byte[] ImageViewToByte(ImageView avatar) {
        Bitmap bitmap=((BitmapDrawable)avatar.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
        byte[]bytes=stream.toByteArray();
        return bytes;
    }



    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageavatar;
        TextView txtname, txtdescription, txtdate;
        ImageButton flowmenu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageavatar=(ImageView)itemView.findViewById(R.id.viewavatar);
            txtname=(TextView)itemView.findViewById(R.id.txt_name);
            flowmenu=(ImageButton)itemView.findViewById(R.id.flowmenu);
            //------------------------------------------
            txtdescription=(TextView)itemView.findViewById(R.id.txt_description);
            txtdate=(TextView)itemView.findViewById(R.id.txt_date);

            showphoto=itemView.findViewById(R.id.showphoto);
            showtitle=itemView.findViewById(R.id.showtitle);
            showdescription=itemView.findViewById(R.id.showdescription);
            showdate=itemView.findViewById(R.id.showdate);
        }
    }
}