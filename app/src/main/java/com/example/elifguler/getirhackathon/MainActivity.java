package com.example.elifguler.getirhackathon;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView startDateTextView;
    Button startDateButton;
    TextView endDateTextView;
    Button endDateButton;
    EditText minCountEditText;
    EditText maxCountEditText;
    Button sendButton;
    LinearLayout recordsLayout;
    TextView codeTextView;
    TextView messageTextView;
    TextView recordLabelTextView;
    Button prevButton;
    Button nextButton;

    RecyclerView recordsRecyclerView;
    RecordListAdapter adapter;

    boolean isStartDatePicker = true;

    List<Record> records = new ArrayList<>();
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startDateTextView = findViewById(R.id.start_date_tv);
        startDateButton = findViewById(R.id.start_date_button);
        endDateTextView = findViewById(R.id.end_date_tv);
        endDateButton = findViewById(R.id.end_date_button);
        minCountEditText = findViewById(R.id.min_count_edit);
        maxCountEditText = findViewById(R.id.max_count_edit);
        sendButton = findViewById(R.id.send_button);
        recordsLayout = findViewById(R.id.records_layout);
        codeTextView = findViewById(R.id.code_tv);
        messageTextView = findViewById(R.id.message_tv);
        recordLabelTextView = findViewById(R.id.records_label_tv);
        recordsRecyclerView = findViewById(R.id.records_rv);
        prevButton = findViewById(R.id.prev_btn);
        nextButton = findViewById(R.id.next_btn);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recordsRecyclerView.setLayoutManager(layoutManager);

        adapter = new RecordListAdapter();
        recordsRecyclerView.setAdapter(adapter);

        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStartDatePicker = true;
                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this);
                dialog.setOnDateSetListener(MainActivity.this);
                dialog.show();
            }
        });

        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStartDatePicker = false;
                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this);
                dialog.setOnDateSetListener(MainActivity.this);
                dialog.show();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeRequest();
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index >= 10) {
                    index -= 10;
                    adapter.setRecordData(records.subList(index, index + 10));
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index + 20 <= records.size()) {
                    index += 10;
                    adapter.setRecordData(records.subList(index, index + 10));
                } else if (index + 10 <= records.size()) {
                    index += 10;
                    adapter.setRecordData(records.subList(index, records.size()));
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        month++;
        String sDay = (day < 10 ? "0" + day : "" + day);
        String sMonth = (month < 10 ? "0" + month : "" + month);

        if (isStartDatePicker) {
            startDateTextView.setText(year + "-" + sMonth + "-" + sDay);
        } else {
            endDateTextView.setText(year + "-" + sMonth + "-" + sDay);
        }
    }

    private void makeRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://getir-bitaksi-hackathon.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RestInterfaceController controller = retrofit.create(RestInterfaceController.class);

        RequestBody body = new RequestBody();
        body.startDate = startDateTextView.getText().toString();
        body.endDate = endDateTextView.getText().toString();
        try {
            body.minCount = Integer.parseInt(minCountEditText.getText().toString().trim());
        } catch (NumberFormatException e) {
            body.minCount = null;
        }
        try {
            body.maxCount = Integer.parseInt(maxCountEditText.getText().toString().trim());
        } catch (NumberFormatException e) {
            body.maxCount = null;
        }

        Call<Response> call = controller.request(body);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                codeTextView.setText("Code: " + response.body().code);
                messageTextView.setText("Message: " + response.body().message);

                if (response.body().records != null) {
                    records.addAll(response.body().records);
                    index = (records.size() > 10 ? 10 : records.size());
                    adapter.setRecordData(records.subList(0, index));
                    index = 0;
                } else {
                    records = new ArrayList<>();
                    adapter.setRecordData(records);
                }

                recordsLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                recordsLayout.setVisibility(View.GONE);
            }
        });
    }
}
