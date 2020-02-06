package com.pubquiz.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pubquiz.models.Question;
import com.pubquiz.R;

import java.util.ArrayList;
import java.util.Collections;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionHolder> {

    public interface AnswerSubmittedListener {
        void onAnswerSubmitted(int position, String answer);
    }

    private ArrayList<Question> questions;
    private AnswerSubmittedListener listener;

    public QuestionsAdapter(ArrayList<Question> questions, AnswerSubmittedListener listener) {
        this.questions = questions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public QuestionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new QuestionHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_question, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final QuestionHolder holder, final int position) {
        final Question question = questions.get(position);
        TextView questionNumber = holder.itemView.findViewById(R.id.question_number);
        TextView questionText = holder.itemView.findViewById(R.id.question_text);
        final RadioGroup radioGroup = holder.itemView.findViewById(R.id.radio_group);
        RadioButton answerOne = holder.itemView.findViewById(R.id.answer_one);
        RadioButton answerTwo = holder.itemView.findViewById(R.id.answer_two);
        RadioButton answerThree = holder.itemView.findViewById(R.id.answer_three);
        RadioButton answerFour = holder.itemView.findViewById(R.id.answer_four);
        TextView submitAnswer = holder.itemView.findViewById(R.id.submit_answer);
        questionNumber.setText(String.valueOf(position + 1));
        questionText.setText(question.getQuestion());
        ArrayList<String> answers = (ArrayList<String>) question.getIncorrectAnswers();
        answers.add(question.getCorrectAnswer());
        Collections.shuffle(answers);
        answerOne.setText(answers.get(0));
        answerTwo.setText(answers.get(1));
        answerThree.setText(answers.get(2));
        answerFour.setText(answers.get(3));

        submitAnswer.setOnClickListener(view -> {
            if (radioGroup.getCheckedRadioButtonId() != -1) {
                RadioButton selectedAnswer = holder.itemView.findViewById(radioGroup.getCheckedRadioButtonId());
                listener.onAnswerSubmitted(position, selectedAnswer.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class QuestionHolder extends RecyclerView.ViewHolder {
        QuestionHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
