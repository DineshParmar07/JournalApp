package com.ntorq.journalApp.controller;

import com.ntorq.journalApp.entity.JournalEntry;
import com.ntorq.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    //READ
    @GetMapping
    public ResponseEntity<?> getAll(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return journalEntryService.showEntries(authentication.getName());
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<?> getEntryById(@PathVariable String id){

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return journalEntryService.showEntry(authentication.getName(), id);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    //CREATE
    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry journal){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            journalEntryService.saveEntry(journal,authentication.getName());//Exc

            return new ResponseEntity<>(journal, HttpStatus.CREATED);

        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //UPDATE
    @PutMapping("id/{id}")
    public ResponseEntity<?> updateEntry(@RequestBody JournalEntry newEntry,@PathVariable String id){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return journalEntryService.updateEntry(newEntry,id, authentication.getName());

    }

    //DELETE
    @DeleteMapping("id/{id}")
    public void deleteEntryById(@PathVariable String  id){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        journalEntryService.removeEntry(authentication.getName(), id);

    }

}
//controller -> service -> repo
// journal_entries --> JournalEntry < -- Update List <-- @PathVariable UserName <- User
