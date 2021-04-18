package com.example.fgo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Fuel_update extends AppCompatActivity {
ImageView petrol,diesel;
    ImageView adder,remove;
    int flag=0;
    EditText count;
    int content=0;
final Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_update);

        petrol=findViewById(R.id.petrol);
        diesel=findViewById(R.id.diesel);


        petrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater=LayoutInflater.from(context);
                final View promptsview=layoutInflater.inflate(R.layout.order,null);
                final AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(context);
                TextView fuel_txt=promptsview.findViewById(R.id.fuel_type);
                fuel_txt.setText(" Fuel type is : petrol ");
                TextView price_txt=promptsview.findViewById(R.id.rate);
                price_txt.setText(" price of petrol is : 80Rs");
                adder=promptsview.findViewById(R.id.adder_but);
                remove=promptsview.findViewById(R.id.remove);
                count=promptsview.findViewById(R.id.count);
                adder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       content++;
                       if(content<8)
                       {
                           count.setText(String.valueOf(content));
                       }
                       else
                           count.setError("Max limit is reached ");
                    }
                });
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(content>0)
                        {
                            content--;
                            count.setText(String.valueOf(content));
                        }
                        else
                        {
                            count.setError("Negative values are not possible");
                        }
                    }
                });
                alertDialogBuilder.setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        content=0;
                        startActivity(new Intent(getApplicationContext(),Cart.class));



                    }
                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                              content=0;
                                dialog.cancel();
                            }
                        });
                alertDialogBuilder.setView(promptsview);
                AlertDialog alertDialog=alertDialogBuilder.create();
                alertDialog.show();
            }

        });
        diesel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater=LayoutInflater.from(context);
                final View promptsview=layoutInflater.inflate(R.layout.order,null);
                final AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(context);
                adder=promptsview.findViewById(R.id.adder_but);
                remove=promptsview.findViewById(R.id.remove);
                count=promptsview.findViewById(R.id.count);
                TextView fuel_txt=promptsview.findViewById(R.id.fuel_type);
                fuel_txt.setText(" Fuel type is : Diesel ");
                TextView price_txt=promptsview.findViewById(R.id.rate);
                price_txt.setText(" price of diesel  is : 70    Rs");
                adder.setClickable(false);
                              adder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(content<8)
                        {
                            content++;
                            count.setText(String.valueOf(content));
                        }
                        else
                        {
                            count.setError("Max limit is 8 litres only");
                        }
                    }
                });
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(content>0)
                        {
                            content--;
                            count.setText(String.valueOf(content));
                        }
                        else
                        {
                            count.setError("Negative values are not possible");
                        }
                    }
                });
                alertDialogBuilder.setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        content=0;
                startActivity(new Intent(getApplicationContext(),Cart.class));
                    }
                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                content=0;
                                count.setText(String.valueOf(content));
                                dialog.cancel();
                            }
                        });
                alertDialogBuilder.setView(promptsview);
                AlertDialog alertDialog=alertDialogBuilder.create();
                alertDialog.show();
            }

        });

    }
}
