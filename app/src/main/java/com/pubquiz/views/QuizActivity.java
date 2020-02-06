package com.pubquiz.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pubquiz.recycler.NoScrollLinearLayoutManager;
import com.pubquiz.models.Question;
import com.pubquiz.models.QuestionsResponse;
import com.pubquiz.R;
import com.pubquiz.network.ApiServiceFactory;
import com.pubquiz.recycler.QuestionsAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity implements QuestionsAdapter.AnswerSubmittedListener {

    private ArrayList<Question> questions = new ArrayList<>();
    private RecyclerView recyclerView;
    private NoScrollLinearLayoutManager layoutManager;
    private QuestionsAdapter adapter;
    private Call<QuestionsResponse> getQuestionsCall;

    private int score = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity);
        initRecyclerView();
        getQuestions(getIntent().getStringExtra("category"));
    }

    // postavljanje adaptera i layout managera
    private void initRecyclerView() {
        recyclerView = findViewById(R.id.quiz_recycler_view);
        adapter = new QuestionsAdapter(questions, this);
        layoutManager = new NoScrollLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    // api poziv
    private void getQuestions(String categoryId) {
        getQuestionsCall = ApiServiceFactory.getApiService().getQuestions(categoryId);
        getQuestionsCall.enqueue(new Callback<QuestionsResponse>() {
            @Override
            public void onResponse(Call<QuestionsResponse> call, Response<QuestionsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    questions.addAll(response.body().getResults());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<QuestionsResponse> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // callback na stisnut Submit answer iz recyclerview itema
    @Override
    public void onAnswerSubmitted(int position, String answer) {
        if (questions.get(position).getCorrectAnswer().equals(answer)) {
            score += 1;
        }
        // ako to pitanje nije zadnje, otkljucaj scrollanje i scrollaj na sljedece pitanje, inace prikazi rezultat
        if (position < 2) {
            layoutManager.setCanScroll(true);
            recyclerView.scrollToPosition(position + 1);
            layoutManager.setCanScroll(false);
        } else {
            Intent intent = new Intent(this, ScoreActivity.class);
            intent.putExtra("score", String.valueOf(score));
            startActivity(intent);
            finish();
        }
    }

    // ako se ne stavi ova provjera, app ce puknut ako se activity zavrsi a api poziv je u tijeku
    @Override
    protected void onStop() {
        super.onStop();
        if (getQuestionsCall != null) {
            getQuestionsCall.cancel();
        }
    }
}
