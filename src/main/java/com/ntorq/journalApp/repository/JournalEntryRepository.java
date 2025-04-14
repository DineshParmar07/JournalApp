package com.ntorq.journalApp.repository;

import com.ntorq.journalApp.entity.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry,String> {
    JournalEntry getEntryById(String id);
}
