package com.reeinvent.backend.client.synonym.mapper;

import com.reeinvent.backend.GRPCSynonym;
import com.reeinvent.backend.GRPCWord;
import com.reeinvent.backend.GRPCWords;
import com.reeinvent.backend.client.synonym.models.Synonym;
import com.reeinvent.backend.client.synonym.models.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SynonymMapper {

    public static GRPCWord toGRPCWord(Word word) {
        GRPCWord.Builder builder = GRPCWord.newBuilder();

        if (word.getId() != null && !word.getId().isEmpty()) {
            builder.setId(word.getId());
        }
        if (word.getText() != null && !word.getText().isEmpty()) {
            builder.setText(word.getText());
        }
        if (word.getSynonyms() != null && !word.getSynonyms().isEmpty()) {
            builder.addAllSynonyms(word.getSynonyms().stream()
                    .map(SynonymMapper::toGRPCWord)
                    .collect(Collectors.toList())
            );
        }

        return builder.build();
    }

    public static Word toWord(GRPCWord grpcWord) {
        Word word = new Word();

        if (grpcWord.getId() != null && !grpcWord.getId().isEmpty()) {
            word.setId(grpcWord.getId());
        }
        if (grpcWord.getText() != null && !grpcWord.getText().isEmpty()) {
            word.setText(grpcWord.getText());
        }
        if (grpcWord.getSynonymsList() != null && !grpcWord.getSynonymsList().isEmpty()) {
            word.setSynonyms(grpcWord.getSynonymsList()
                    .stream()
                    .map(SynonymMapper::toWord)
                    .collect(Collectors.toList())
            );
        }

        return word;
    }

    public static GRPCSynonym toGRPCSynonym(Synonym synonym) {
        GRPCSynonym.Builder builder = GRPCSynonym.newBuilder();

        if (synonym.getWordId() != null && !synonym.getWordId().isEmpty()) {
            builder.setWordId(synonym.getWordId());
        }
        if (synonym.getSynonymId() != null && !synonym.getSynonymId().isEmpty()) {
            builder.setId(synonym.getSynonymId());
        }

        return builder.build();
    }

    public static List<Word> toWords(GRPCWords grpcWords) {

        return new ArrayList<>(grpcWords.getWordList().stream()
                .map(SynonymMapper::toWord)
                .collect(Collectors.toList()));
    }
}
