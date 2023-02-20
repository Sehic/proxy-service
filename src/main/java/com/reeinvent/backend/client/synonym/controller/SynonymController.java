package com.reeinvent.backend.client.synonym.controller;

import com.reeinvent.backend.client.synonym.models.Synonym;
import com.reeinvent.backend.client.synonym.models.Word;
import com.reeinvent.backend.client.synonym.service.SynonymService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.reeinvent.backend.util.EndpointConstants.*;

@RestController
@RequestMapping(SYNONYM)
public class SynonymController {

    @Autowired
    private SynonymService synonymService;

    @Secured({"ROLE_ADMIN"})
    @PostMapping(UPSERT)
    public ResponseEntity<Word> upsertWord(@RequestBody Word word) {
        return ResponseEntity.ok(synonymService.upsertWord(word));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping(DELETE)
    public ResponseEntity deleteWord(@RequestBody Word word) {
        synonymService.deleteWord(word);
        return ResponseEntity.ok().build();
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping(ATTACH)
    public ResponseEntity attachSynonym(@RequestBody Synonym synonym) {
        synonymService.attachSynonym(synonym);
        return ResponseEntity.ok().build();
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping(DETACH)
    public ResponseEntity detachSynonym(@RequestBody Synonym synonym) {
        synonymService.detachSynonym(synonym);
        return ResponseEntity.ok().build();
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping(WORDS)
    public ResponseEntity<List<Word>> getAllWords() {
        return ResponseEntity.ok(synonymService.getAllWords());
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping(SYNONYMS)
    public ResponseEntity<Word> getSynonymsByWord(@RequestBody Word word) {
        return ResponseEntity.ok(synonymService.getSynonymsByWord(word));
    }
}
