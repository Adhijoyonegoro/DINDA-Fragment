package com.example.dinda.Tabs;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dinda.Libraries.DatabaseHelper;
import com.example.dinda.Libraries.Utils;
import com.example.dinda.Model.ModelDashboard;
import com.example.dinda.R;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PanenActivity extends AppCompatActivity {
    DatabaseHelper db;
    @BindView(R.id.txtHeader)
    TextView tvHeader;
    @BindView(R.id.txtQuestion)
    TextView tvQuestion;
    @BindView(R.id.editAnswerTime)
    EditText etQuestionTime;
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

    private String header;
    private int seqQuestion=0;
    private int questionCount=0;

    ModelDashboard modelDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panen);
        ButterKnife.bind(this);
        db = new DatabaseHelper(PanenActivity.this);

        header = getIntent().getStringExtra("HEADER").trim();
        tvHeader.setText(header);

        String[] _paramQuestion = header.split(" ");
        Cursor _cursor = db.getTemplateQuestionCount(_paramQuestion);
        if (_cursor.moveToFirst()) {
            questionCount = _cursor.getInt(_cursor.getColumnIndex("ROWS"));
        }
        Log.e("COUNT", String.valueOf(questionCount));
        tvQuestion.setText("Kegiatan");
        etQuestionNumber.setVisibility(View.GONE);
        etQuestionTime.setVisibility(View.GONE);
        tvDescription.setText("Aktifkan jika ada kegiatan, nonaktifkan jika tidak ada.");
        rbKegiatan.setVisibility(View.VISIBLE);
        btnPrior.setText("BATAL");
    }

    @OnClick(R.id.btnPrior)
    public void onViewClickedPrior() {
        if( btnPrior.getText() == "BATAL" ) {
            PanenActivity.this.finish();
        }
        else {
            seqQuestion--;
            if (seqQuestion <= 1) {
                seqQuestion = 1;
                if (!rbKegiatan.isChecked())
                    PanenActivity.this.finish();
            }
            _displayQuestion();
            if (seqQuestion == 1) {
                btnPrior.setVisibility(View.GONE);
            }
            else {
                btnPrior.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        onViewClickedPrior();
    }

    @OnClick(R.id.btnNext)
    public void onViewClickedNext() {
        if( !rbKegiatan.isChecked() )
            Utils._alertClose(PanenActivity.this, "Tidak ada kegiatan!");
        else if( questionCount > seqQuestion ) {
            seqQuestion++;
            if( seqQuestion == 1 ) {
                btnPrior.setVisibility(View.GONE);
            }
            else {
                btnPrior.setVisibility(View.VISIBLE);
            }
            _displayQuestion();
        }
        else {
            Utils._alertClose(PanenActivity.this, "Input selesai!");
        }
    }

    public void _displayQuestion() {
        String _header = header + " " + seqQuestion;
//        db = new DatabaseHelper(PanenActivity.this);
        String[] _paramQuestion = _header.split(" ");
        Cursor _cursor = db.getTemplateQuestion(_paramQuestion);
        if (_cursor.moveToFirst()) {
            tvQuestion.setText(_cursor.getString(_cursor.getColumnIndex(db.KEY_TEMPLATE_KATEGORI2)));
            String _description = _cursor.getString(_cursor.getColumnIndex(db.KEY_TEMPLATE_DESC_OPERATION));
            _description = _description.equals("null") ? "" : _description;
            tvDescription.setText(_description);
            etQuestionNumber.setVisibility(View.VISIBLE);
            rbKegiatan.setVisibility(View.GONE);
            btnPrior.setText("KEMBALI");
        }
    }
}
