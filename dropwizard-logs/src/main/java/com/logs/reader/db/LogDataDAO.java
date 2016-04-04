package com.logs.reader.db;

import com.logs.reader.core.LogData;

import io.dropwizard.hibernate.AbstractDAO;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class LogDataDAO extends AbstractDAO<LogData> {
    public LogDataDAO(SessionFactory factory) {
        super(factory);
    }

    public Optional<LogData> findById(String uuid) {
        return Optional.ofNullable(get(uuid));
    }

    public LogData create(LogData person) {
        return persist(person);
    }

    public List<LogData> findAll() {
        return list(namedQuery("com.logs.reader.core.LogData.findAll"));
    }
    
    public List<LogData> findAllWithAssets() {
        return list(namedQuery("com.logs.reader.core.LogData.findAllWithAssets"));
    }
 
    public List<LogData> findAllWithAssets(int nth) {
        return list(namedQuery("com.logs.reader.core.LogData.findAllWithAssets"));
    }

    public int deleteAll() {
        Query query = namedQuery("com.logs.reader.core.LogData.deleteAll");
        return query.executeUpdate();
    }
}
