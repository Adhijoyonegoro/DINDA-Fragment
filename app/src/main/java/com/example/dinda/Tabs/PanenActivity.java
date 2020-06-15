package com.example.dinda.Tabs;

import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dinda.Libraries.DatabaseHelper;
import com.example.dinda.Libraries.Utils;
import com.example.dinda.Model.ModelDashboard;
import com.example.dinda.Popup.LayoutPopupWarning;
import com.example.dinda.R;

import java.util.Arrays;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class PanenActivity extends AppCompatActivity {
    DatabaseHelper db;
    @BindView(R.id.txtHeader)
    TextView tvHeader;
    @BindView(R.id.txtQuestion)
    TextView tvQuestion;
    @BindView(R.id.editAnswerTime)
    TextView etQuestionTime;
    @BindView(R.id.editAnswerNumber)
    EditText etQuestionNumber;
    @BindView(R.id.txtDescription)
    TextView tvDescription;
    @BindView(R.id.rbKegiatan)
    Switch rbKegiatan;
    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.btnPrior)
    Button btnPrior;
    @BindView(R.id.ll_content)
    LinearLayout ll_content;

    private String header;
    private int seqQuestion=0;
    private int questionCount=0;
    private TimePicker timePicker1;
    private boolean validAnswer = true;
    private int invalid = 0;
// validate
    private String typeOperation, maxValue, tempValue, description;

    ModelDashboard modelDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panen);
        ButterKnife.bind(this);
        db = new DatabaseHelper(PanenActivity.this);

        header = getIntent().getStringExtra("HEADER").trim();
        tvHeader.setText(header);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tvDescription.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
        String[] _paramQuestion = header.split(" ");
        Cursor _cursor = db.getTemplateQuestionCount(_paramQuestion);
        if (_cursor.moveToFirst()) {
            questionCount = _cursor.getInt(_cursor.getColumnIndex("ROWS"));
        }
        Log.e("COUNT", String.valueOf(questionCount));
        tvQuestion.setText("Ada kegiatan");
        etQuestionNumber.setVisibility(View.GONE);
        etQuestionTime.setVisibility(View.GONE);
        tvDescription.setText("Aktifkan jika ada, nonaktifkan jika tidak ada.");
        rbKegiatan.setVisibility(View.VISIBLE);
        rbKegiatan.setChecked(true);
        btnPrior.setText("BATAL");

        rbKegiatan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvQuestion.setText("Ada kegiatan");
                }
                else {
                    tvQuestion.setText("Tidak ada kegiatan");
                }
            }
        });
    }

    @OnClick(R.id.btnPrior)
    public void onViewClickedPrior() {
        if( btnPrior.getText() == "BATAL" || btnPrior.getText() == "TUTUP" ) {
            PanenActivity.this.finish();
        }
        else {
//            if( validAnswer )
//                seqQuestion--;
            _displayQuestion(-1);
//            if (seqQuestion <= 1) {
//                seqQuestion = 1;
//                if (!rbKegiatan.isChecked())
//                    PanenActivity.this.finish();
//            }
//            if (seqQuestion == 1) {
//                btnPrior.setVisibility(View.GONE);
//            }
//            else {
//                btnPrior.setVisibility(View.VISIBLE);
//            }
//            Log.e("QUESTION2b", String.valueOf(seqQuestion));
        }
    }

    @Override
    public void onBackPressed() {
        onViewClickedPrior();
    }

    @OnClick(R.id.btnNext)
    public void onViewClickedNext() {
        if( rbKegiatan.getVisibility() == View.VISIBLE) {
            if (!rbKegiatan.isChecked()) {
                LayoutPopupWarning lpe = new LayoutPopupWarning(PanenActivity.this);
                lpe.setCancelable(true);
                lpe.showDialog();
                lpe.tvTitle.setText("Konfirmasi");
                lpe.tvKeterangan.setText(Html.fromHtml("Pilihan ini menyebabkan Anda tidak dapat mengisi kegiatan hari ini. Anda yakin?"));
                lpe.btnPositive.setVisibility(View.VISIBLE);
                lpe.btnNegativie.setVisibility(View.VISIBLE);
                lpe.btnPositive.setText("YA");
                lpe.btnNegativie.setText("TIDAK");
                lpe.btnNegativie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lpe.dismissDialog();
                    }
                });
                lpe.btnPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tvQuestion.setText("Tidak ada kegiatan");
                        btnNext.setVisibility(View.GONE);
                        btnPrior.setText("TUTUP");
                        rbKegiatan.setEnabled(false);
                        tvDescription.setText("Anda telah memilih tidak ada kegiatan hari ini.");
                        lpe.dismissDialog();
                    }
                });
            }
            else {
                rbKegiatan.setVisibility(View.GONE);
                validAnswer=true;
                //                btnPrior.setVisibility(View.GONE);
                Log.e("QUESTION1", String.valueOf(seqQuestion));
            }
        }
        _displayQuestion(1);
    }

    @OnClick(R.id.editAnswerTime)
    public void onViewClickedll_content() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(PanenActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                etQuestionTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public void _validate() {
        if( seqQuestion > 0 ) {
// INTEGER
            if (typeOperation.equals("INT")) {
                if (maxValue.length() > 0) {
                    tempValue = tempValue.length() == 0 ? "0" : tempValue;
                    Log.e("TYPE2:", typeOperation + "#" + maxValue + "#" + seqQuestion + "#" + tempValue + "" + maxValue.length());
                    if (Integer.valueOf(tempValue) > Integer.valueOf(maxValue)) {
                        Log.e("TYPE3:", typeOperation + "#" + maxValue + "#" + seqQuestion + "#" + tempValue + "" + maxValue.length());
                        validAnswer = false;
                        Utils._alert(PanenActivity.this, "MAX VALUE: " + maxValue);
                    } else {
                        validAnswer = true;
                    }
                }
            } else if (typeOperation.equals("DIGIT")) {
// FLOAT
//                if(_maxValue.length()>0) {
//                    if( Float.valueOf( etQuestionNumber.getText().toString()) > Float.valueOf( _maxValue )) {
//                        Utils._alert(PanenActivity.this, "MAX VALUE: "+_maxValue);
//                        seqQuestion--;
//                    }
//                }

                validAnswer = true;
            } else if (typeOperation.equals("TIME")) {
// TIME
                validAnswer = true;
            } else {
// OTHER
                validAnswer = true;
            }
        }
    }

    public void _displayQuestion(int _status) {
        String _tempValue = "0";
        _validate();
//        validAnswer = false;
        Log.i("VALID1:", String.valueOf(validAnswer));
        if(validAnswer) {
            seqQuestion+=_status;

            if (seqQuestion <= 1) {
                seqQuestion = 1;
                if (!rbKegiatan.isChecked())
                    PanenActivity.this.finish();
            }
            if (seqQuestion == 1) {
                btnPrior.setVisibility(View.GONE);
            }
            else {
                btnPrior.setVisibility(View.VISIBLE);
            }
            Log.e("QUESTION2b", String.valueOf(seqQuestion));
            if( questionCount >= seqQuestion ) {
                Log.e("QUESTION2", String.valueOf(seqQuestion));
//            if( validAnswer )
//                seqQuestion++;
                if (seqQuestion == 1) {
                    btnPrior.setVisibility(View.GONE);
                } else {
                    btnPrior.setVisibility(View.VISIBLE);
                }
            }
            else {
                Utils._alertClose(PanenActivity.this, "Input selesai.<br> Anda dapat melakukan sinkronisasi di menu Status Transaksi.");
            }
            String _header = header + " " + seqQuestion;
            String[] _paramQuestion = _header.split(" ");
            Log.e("QUESTION2c", Arrays.toString(_paramQuestion));

            Cursor _cursor = db.getTemplateQuestion(_paramQuestion);
            if (_cursor.moveToFirst()) {
                tvQuestion.setText(_cursor.getString(_cursor.getColumnIndex(db.KEY_TEMPLATE_KATEGORI2)));
                description = _cursor.getString(_cursor.getColumnIndex(db.KEY_TEMPLATE_DESC_OPERATION));
                maxValue = _cursor.getString(_cursor.getColumnIndex(db.KEY_TEMPLATE_MAX_VALUE));
                typeOperation = _cursor.getString(_cursor.getColumnIndex(db.KEY_TEMPLATE_TYPE_OPERATION));
                tempValue = etQuestionNumber.getText().toString().trim();

                if (typeOperation.equals("INT")) {
// INTEGER
                    etQuestionNumber.setVisibility(View.VISIBLE);
                    etQuestionTime.setVisibility(View.GONE);
                    etQuestionNumber.setInputType(InputType.TYPE_CLASS_NUMBER);

                }
                else if (typeOperation.equals("DIGIT")) {
// FLOAT
//                if(_maxValue.length()>0) {
//                    if( Float.valueOf( etQuestionNumber.getText().toString()) > Float.valueOf( _maxValue )) {
//                        Utils._alert(PanenActivity.this, "MAX VALUE: "+_maxValue);
//                        seqQuestion--;
//                    }
//                }

                    etQuestionTime.setVisibility(View.GONE);
                    etQuestionNumber.setVisibility(View.VISIBLE);
                    etQuestionNumber.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                }
                else if (typeOperation.equals("TIME")) {
// TIME
                    etQuestionTime.setVisibility(View.VISIBLE);
                    etQuestionNumber.setVisibility(View.GONE);
                }
                else {
// OTHER
                    etQuestionTime.setVisibility(View.GONE);
                    etQuestionNumber.setVisibility(View.VISIBLE);
                    etQuestionNumber.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                }
                description = description.equals("null") ? "" : description;
                tvDescription.setText(description);
                btnPrior.setText("KEMBALI");

            }
        }
    }
}
