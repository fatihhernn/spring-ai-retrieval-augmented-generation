package com.faeren.spring_ai_rag.service;


import com.faeren.spring_ai_rag.model.Answer;
import com.faeren.spring_ai_rag.model.Question;

public interface OpenAIService {

    Answer getAnswer(Question question);
}