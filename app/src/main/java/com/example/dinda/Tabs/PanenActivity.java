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

import java.text.SimpleDateFormat;
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
    private String typeOperation, maxValue, tempValue, description, condition, diIdRef, companyOffice, posCode, diId;
    Calendar c = Calendar.getInstance();
    String tdate = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());

    ModelDashboard modelDashboard;
    private String value="";
    private String afd="";
    private String kategori1="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panen);
        ButterKnife.bind(this);
        db = new DatabaseHelper(PanenActivity.this);

        header = getIntent().getStringExtra("HEADER").trim();
        tvHeader.setText(header);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            tvDescription.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
//        }
        String _header = tdate+" "+header;
        String[] _cekAdaAnswer = _header.split(" ");
        int _kegiatan = db.getKPIAnswerStatus( _cekAdaAnswer );

//        int _kegiatan=2;
// 0 start, 1 ada kegiatan, 2 tidak ada kegiatan
        if( _kegiatan == 0 ) {
            tvQuestion.setText("Ada kegiatan");
            etQuestionNumber.setVisibility(View.GONE);
            btnNext.setText( "LANUT" );
            etQuestionTime.setVisibility(View.GONE);
            tvDescription.setText("Klik Lanjut untuk mengisi kegiatan.");
            rbKegiatan.setVisibility(View.VISIBLE);
            rbKegiatan.setChecked(true);
            btnPrior.setVisibility(View.GONE);
            rbKegiatan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        tvQuestion.setText("Ada kegiatan");
                        tvDescription.setText("Klik Lanjut untuk mengisi kegiatan.");
                        btnNext.setText( "LANUT" );
                    }
                    else {
                        tvQuestion.setText("Tidak ada kegiatan");
                        tvDescription.setText("Pilihan ini menyebabkan Anda tidak dapat mengisi kegiatan hari ini. Anda yakin?");
                        btnNext.setText( "YA, SIMPAN TANPA KEGIATAN" );
                    }
                }
            });
        }
        else if ( _kegiatan == 1 ) {
            rbKegiatan.setVisibility(View.GONE);
            rbKegiatan.setChecked(true);
            validAnswer=true;
            btnNext.setText( "LANUT" );
            String[] _paramQuestion = header.split(" ");
            Log.e("QUESTION2c1", Arrays.toString(_paramQuestion));

            Cursor _cursor = db.getTemplateQuestionCount(_paramQuestion);
            if (_cursor.moveToFirst()) {
                questionCount = _cursor.getInt(_cursor.getColumnIndex("ROWS"));
            }
            Log.e("COUNT", String.valueOf(questionCount));

            //                btnPrior.setVisibility(View.GONE);
//                Log.e("QUESTION1", String.valueOf(seqQuestion));
            _displayQuestion(1);
        }
        else if ( _kegiatan == 2 ) {
            tvQuestion.setText("Tidak Ada kegiatan");
            etQuestionNumber.setVisibility(View.GONE);
            etQuestionTime.setVisibility(View.GONE);
            tvDescription.setText("Anda sudah memilih tidak ada kegiatan hari ini.");
            rbKegiatan.setVisibility(View.GONE);
            rbKegiatan.setChecked(true);
            btnPrior.setVisibility(View.GONE);
            btnNext.setText("TUTUP");
        }
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
        PanenActivity.this.finish();
    }

    @OnClick(R.id.btnNext)
    public void onViewClickedNext() {
        if( btnNext.getText().toString() == "TUTUP" ) {
            PanenActivity.this.finish();
        }
        else {
            if (rbKegiatan.getVisibility() == View.VISIBLE) {
                if (!rbKegiatan.isChecked()) {
                    String _header = header;
                    String _createdate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(c.getTime());
                    String[] _items = _header.split(" ");
                    String[] _paramAnswer = new String[5];
                    if (_items.length == 3) {
                        _paramAnswer[3] = _items[2]; // AFD
                    } else {
                        _paramAnswer[3] = ""; // AFD
                    }
                    _paramAnswer[0] = tdate;
                    _paramAnswer[1] = _items[0]; // KATEGORI1
                    _paramAnswer[2] = _items[1]; // COMPANY_OFFICE
                    //                _paramAnswer[3] = _items[2]; // AFD
                    _paramAnswer[4] = _createdate;

                    db.insertKPITanpaKegiatan(_paramAnswer);

                    Utils._alertClose(PanenActivity.this, "Anda sudah memilih tanpa kegiatan untuk hari ini.");
                    //                LayoutPopupWarning lpe = new LayoutPopupWarning(PanenActivity.this);
                    //                lpe.setCancelable(true);
                    //                lpe.showDialog();
                    //                lpe.tvTitle.setText("Konfirmasi");
                    //                lpe.tvKeterangan.setText(Html.fromHtml("Pilihan ini menyebabkan Anda tidak dapat mengisi kegiatan hari ini. Anda yakin?"));
                    //                lpe.btnPositive.setVisibility(View.VISIBLE);
                    //                lpe.btnNegativie.setVisibility(View.VISIBLE);
                    //                lpe.btnPositive.setText("YA");
                    //                lpe.btnNegativie.setText("TIDAK");
                    //                lpe.btnNegativie.setOnClickListener(new View.OnClickListener() {
                    //                    @Override
                    //                    public void onClick(View v) {
                    //                        lpe.dismissDialog();
                    //                    }
                    //                });
                    //                lpe.btnPositive.setOnClickListener(new View.OnClickListener() {
                    //                    @Override
                    //                    public void onClick(View v) {
                    //                        tvQuestion.setText("Tidak ada kegiatan");
                    //                        btnNext.setVisibility(View.GONE);
                    //                        btnPrior.setText("TUTUP");
                    //                        rbKegiatan.setEnabled(false);
                    //                        tvDescription.setText("Anda telah memilih tidak ada kegiatan hari ini.");
                    //                        lpe.dismissDialog();
                    //                    }
                    //                });
                    rbKegiatan.setVisibility(View.GONE);
                } else {
                    rbKegiatan.setVisibility(View.GONE);
                    validAnswer = true;
                    String[] _paramQuestion = header.split(" ");
                    Log.e("QUESTION2c1", Arrays.toString(_paramQuestion));

                    Cursor _cursor = db.getTemplateQuestionCount(_paramQuestion);
                    if (_cursor.moveToFirst()) {
                        questionCount = _cursor.getInt(_cursor.getColumnIndex("ROWS"));
                    }
                    Log.e("COUNT", String.valueOf(questionCount));

                    //                btnPrior.setVisibility(View.GONE);
                    //                Log.e("QUESTION1", String.valueOf(seqQuestion));
                    _displayQuestion(1);
                }
            } else {
                String[] _paramQuestion = header.split(" ");
                Log.e("QUESTION2c1", Arrays.toString(_paramQuestion));

                Cursor _cursor = db.getTemplateQuestionCount(_paramQuestion);
                if (_cursor.moveToFirst()) {
                    questionCount = _cursor.getInt(_cursor.getColumnIndex("ROWS"));
                }
                Log.e("COUNT", String.valueOf(questionCount));

                //                btnPrior.setVisibility(View.GONE);
                _displayQuestion(1);
            }
        }
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
            if( diIdRef.length() > 0 ) {
                String[] _checkIdRef = new String [3];
                _checkIdRef[0] = tdate;
                _checkIdRef[1] = posCode;
                _checkIdRef[2] = diIdRef;
                String _strCheck = db.getTemplateIdRef( _checkIdRef );
                Log.e( "strchk:", _strCheck + " & " + tempValue );
                if( _strCheck == "0" || _strCheck == "" || _strCheck == null ) {
                    if( tempValue != "0" && tempValue != null && tempValue != "" ){
                        validAnswer = false;
                        Utils._alert(PanenActivity.this, "INVALID INPUT: ");
                    }
                }
                else {
                    if( _strCheck != "0" || _strCheck != "" || _strCheck != null ) {
                        if( tempValue == "0" && tempValue == null && tempValue == "" ){
                            validAnswer = false;
                            Utils._alert(PanenActivity.this, "INVALID INPUT: ");
                        }
                    }
                }
            }
            else
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
        String _createdate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(c.getTime());

//        validAnswer = false;
        Log.i("VALID1:", String.valueOf(validAnswer));
        if(validAnswer) {
            seqQuestion+=_status;
            if (seqQuestion <= 1) {
//                Calendar c = Calendar.getInstance();
//                String tglNow = new SimpleDateFormat("EEEE, dd MMMM yyyy").format(c.getTime());

                Log.e("PERTAMAX", "1");
                seqQuestion = 1;
                if (!rbKegiatan.isChecked())
                    PanenActivity.this.finish();
            }
            if (seqQuestion == 1) {
                btnPrior.setVisibility(View.GONE);
            }
            else {
                btnPrior.setVisibility(View.VISIBLE);
                if (typeOperation.equals("TIME")) {
                    tempValue = etQuestionTime.getText().toString().trim();
                }
                else {
                    tempValue = etQuestionNumber.getText().toString().trim();
                }
                Log.e( "tempValue:", tempValue );
                String[] _paramAnswer = new String[8];

                _paramAnswer[0] = tdate;
                _paramAnswer[1] = companyOffice;
                _paramAnswer[2] = posCode;
                _paramAnswer[3] = diId;
                _paramAnswer[4] = tempValue;
                _paramAnswer[5] = kategori1;
                _paramAnswer[6] = afd;
                _paramAnswer[7] = _createdate;
                db.insertKPI(_paramAnswer);
            }
//            Log.e("QUESTION2b", String.valueOf(seqQuestion));
            if( questionCount >= seqQuestion ) {
//                Log.e("QUESTION2", String.valueOf(seqQuestion));
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
            String _header = header + " " + seqQuestion+" "+tdate;
            String[] _paramQuestion = _header.split(" ");

            Log.e("QUESTION2c", Arrays.toString(_paramQuestion));

            Cursor _cursor = db.getTemplateQuestion(_paramQuestion);
            if (_cursor.moveToFirst()) {
                tvQuestion.setText(_cursor.getString(_cursor.getColumnIndex(db.KEY_TEMPLATE_KATEGORI2)));
                description = _cursor.getString(_cursor.getColumnIndex(db.KEY_TEMPLATE_DESC_OPERATION));
                maxValue = _cursor.getString(_cursor.getColumnIndex(db.KEY_TEMPLATE_MAX_VALUE));
                typeOperation = _cursor.getString(_cursor.getColumnIndex(db.KEY_TEMPLATE_TYPE_OPERATION));
                afd = _cursor.getString(_cursor.getColumnIndex(db.KEY_TEMPLATE_AFD));
                condition = _cursor.getString(_cursor.getColumnIndex(db.KEY_TEMPLATE_CONDITION));
                diIdRef = _cursor.getString(_cursor.getColumnIndex(db.KEY_TEMPLATE_DI_ID_REF));
                kategori1 = _cursor.getString(_cursor.getColumnIndex(db.KEY_TEMPLATE_KATEGORI1));
                companyOffice = _cursor.getString(_cursor.getColumnIndex(db.KEY_TEMPLATE_COMPANY_OFFICE));
                posCode = _cursor.getString(_cursor.getColumnIndex(db.KEY_TEMPLATE_POS_CODE));
                diId = _cursor.getString(_cursor.getColumnIndex(db.KEY_TEMPLATE_DI_ID));
                value = _cursor.getString(_cursor.getColumnIndex(db.KEY_TEMPLATE_VALUE));
//Log.e( "value:", value);
                if( condition.equals( "0" )) {
                    _displayQuestion(1);
                }
                if (typeOperation.equals("INT")) {
// INTEGER
                    etQuestionNumber.setVisibility(View.VISIBLE);
                    etQuestionTime.setVisibility(View.GONE);
                    etQuestionNumber.setInputType(InputType.TYPE_CLASS_NUMBER);

                    etQuestionNumber.setText(value);
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

                    etQuestionNumber.setText(value);
                }
                else if (typeOperation.equals("TIME")) {
// TIME
                    etQuestionTime.setVisibility(View.VISIBLE);
                    etQuestionNumber.setVisibility(View.GONE);
                    etQuestionTime.setText(value);
                }
                else {
// OTHER
                    etQuestionTime.setVisibility(View.GONE);
                    etQuestionNumber.setVisibility(View.VISIBLE);
                    etQuestionNumber.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                    etQuestionNumber.setText(value);
                }
                description = description.equals("null") ? "" : description;
                tvDescription.setText(description);
                btnPrior.setText("KEMBALI");
            }
        }
    }
}
