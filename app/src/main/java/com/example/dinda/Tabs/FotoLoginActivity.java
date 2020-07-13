package com.example.dinda.Tabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.example.dinda.R;


public class FotoLoginActivity extends AppCompatActivity {
    private ImageView imageHolder;
    private final int requestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto_login);

        imageHolder = (ImageView)findViewById(R.id.captured_photo);
        Button capturedImageButton = (Button)findViewById(R.id.take_picture);
        Log.e("D:", capturedImageButton.getText().toString());

        capturedImageButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( capturedImageButton.getText().toString().equals( "FOTO" )) {
                    Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(photoCaptureIntent, requestCode);
                    capturedImageButton.setText( "LANJUT" );
                    Log.e("B:", String.valueOf(requestCode));
                }
                else {
                    Log.e("C:", String.valueOf(requestCode));
                    Intent intent = new Intent(FotoLoginActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    FotoLoginActivity.this.finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(this.requestCode == requestCode && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            imageHolder.setImageBitmap(bitmap);
            Log.e( "A:", "aa" );
        }
    }

    @Override
    public void onBackPressed() {

    }
}