package com.vb.veritabanifull;

import android.content.DialogInterface;
import android.database.SQLException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText et_Ad, et_Soyad;
    private TextView tv_sonuc;

    //2.kısım
    DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        load();

    }

    private void load() {
        //layoutta tanıttığımız parçları alıyoruz.
        et_Ad = (EditText) findViewById(R.id.et_Ad);
        et_Soyad = (EditText) findViewById(R.id.et_Soyad);
        tv_sonuc = (TextView) findViewById(R.id.txtListe);

        //2.kısım

        // super(context, "vb.db", factory, version); database sınıfmızdaki superin icine gomuyoruz
        //this bu contexi isaret eder  ikinci parametre boş çünkü o veri tabanı adını vermek için biz direk verdik
        //3.parametre cok onemli değil sunalık
        //versioynu belirtmek için
        db = new DataBase(this, "", null, 1);

    }

    public void clicked(View view) {
       final String ad = et_Ad.getText().toString();
       final String soyad = et_Soyad.getText().toString();




            switch (view.getId()) {
                case R.id.btn_ADD:
                    //eger ad soyad boşsaa direk hata.
                    if (TextUtils.isEmpty(ad) || TextUtils.isEmpty(soyad)) {
                        Toast.makeText(this, "Ad soyad alanı boş geçilmez..", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        try {
                            db.add_Ogrenci(ad, soyad);
                        } catch (SQLException e) {
                            Toast.makeText(this, "Aynı hesap açılmış...", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(this, "kayit eklendi.", Toast.LENGTH_SHORT).show();
                    }

                    break;
                case R.id.btn_DELETE:
                    db.delete_Ogrenci(ad);
                    break;
                case R.id.btn_UPDATE:
                    AlertDialog.Builder dialog=new AlertDialog.Builder(this);
                    dialog.setTitle("Yeni adı girin:");

                    final EditText new_ad = new EditText(this);
                    dialog.setView(new_ad);

                    dialog.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            db.update_Ogrenci(ad,new_ad.getText().toString());
                        }
                    });
                    dialog.show();

                    break;
                case R.id.btn_List:
                    db.list_all_Ogrenci(tv_sonuc);
                    break;
            }




    }

    private void temizle() {
        et_Ad.getText().clear();
        et_Soyad.getText().clear();
    }

    public void temizle(View view) {
        temizle();
    }
}
