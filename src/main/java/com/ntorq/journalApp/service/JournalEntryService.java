package com.ntorq.journalApp.service;

import com.ntorq.journalApp.entity.JournalEntry;
import com.ntorq.journalApp.entity.User;
import com.ntorq.journalApp.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public ResponseEntity<?> showEntries(String userName){

        User user = userService.getUser(userName);
        List<JournalEntry> userJournalEntries = user.getJournalEntries(); //[]

        if (!userJournalEntries.isEmpty()) {
            return new ResponseEntity<>(userJournalEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> showEntry(String userName, String journalId){
        User user = userService.getUser(userName);//ram
        Optional<JournalEntry> entry=Optional.ofNullable(journalEntryRepository.getEntryById(journalId));
        return entry.isPresent()?new ResponseEntity<>(entry,HttpStatus.OK):new ResponseEntity<>(HttpStatus.NOT_FOUND);
        

    }

    @Transactional
    //transaction -> container -> partial commit -> rollback -> pura fail
    public void saveEntry(JournalEntry journal,String userName){
            User user = userService.getUser(userName);
            JournalEntry saved = journalEntryRepository.save(journal);
            user.getJournalEntries().add(saved);

            userService.saveNewUser(user);

    }


    public void removeEntry(String userName, String id){

        User user = userService.getUser(userName);

        user.getJournalEntries().removeIf(x->x.getId().equals(id));

        userService.saveNewUser(user);
        journalEntryRepository.deleteById(id);

    }

    public ResponseEntity<?> updateEntry(JournalEntry entry, String id,String userName){

        User user = userService.getUser(userName);
        JournalEntry entryDB = journalEntryRepository.getEntryById(id); //null

        if (entryDB!=null){
            entryDB.setTitle(entry!=null && !entry.getTitle().isEmpty()? entry.getTitle() : entryDB.getTitle());
            entryDB.setContent(entry!=null && !entry.getContent().isEmpty()? entry.getContent() : entryDB.getContent());
            journalEntryRepository.save(entryDB);

            return new ResponseEntity<>(entryDB,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
