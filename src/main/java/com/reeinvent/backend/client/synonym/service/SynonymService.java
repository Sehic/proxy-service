package com.reeinvent.backend.client.synonym.service;

import com.reeinvent.backend.GRPCWord;
import com.reeinvent.backend.GRPCWords;
import com.reeinvent.backend.SynonymServiceGrpc;
import com.reeinvent.backend.client.synonym.SynonymChannel;
import com.reeinvent.backend.client.synonym.mapper.SynonymMapper;
import com.reeinvent.backend.client.synonym.models.Synonym;
import com.reeinvent.backend.client.synonym.models.Word;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SynonymService implements InitializingBean {

    @Autowired
    private SynonymChannel synonymChannel;

    private SynonymServiceGrpc.SynonymServiceBlockingStub blockingStub;

    public Word upsertWord(Word word) {
        GRPCWord grpcWord = blockingStub.upsertWord(SynonymMapper.toGRPCWord(word));
        return SynonymMapper.toWord(grpcWord);
    }

    public void deleteWord(Word word) {
        blockingStub.deleteWord(SynonymMapper.toGRPCWord(word));
    }

    public void attachSynonym(Synonym synonym) {
        blockingStub.attachSynonym(SynonymMapper.toGRPCSynonym(synonym));
    }

    public void detachSynonym(Synonym synonym) {
        blockingStub.detachSynonym(SynonymMapper.toGRPCSynonym(synonym));
    }

    public List<Word> getAllWords() {
        GRPCWords grpcWords = blockingStub.getAllWords(com.reeinvent.backend.Void.newBuilder().build());
        return SynonymMapper.toWords(grpcWords);
    }

    public Word getSynonymsByWord(Word word) {
        GRPCWord grpcWord = blockingStub.getSynonymsByWord(SynonymMapper.toGRPCWord(word));
        return SynonymMapper.toWord(grpcWord);
    }

    @Override
    public void afterPropertiesSet() {
        blockingStub = SynonymServiceGrpc.newBlockingStub(synonymChannel.getChannel());
    }
}
