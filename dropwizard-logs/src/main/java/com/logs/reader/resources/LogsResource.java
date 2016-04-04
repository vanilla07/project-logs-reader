package com.logs.reader.resources;

import com.logs.reader.core.LogData;
import com.logs.reader.db.LogDataDAO;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.List;

@Path("/logs")
@Produces(MediaType.APPLICATION_JSON)
public class LogsResource {

    private final LogDataDAO logDataDAO;

    public LogsResource(LogDataDAO logDataDAO) {
        this.logDataDAO = logDataDAO;
    }
    
    @POST
    @UnitOfWork
    public LogData createLog(LogData logData) {
        return logDataDAO.create(logData);
    }

    @GET
    @UnitOfWork
    public List<LogData> listLogs() {
        return logDataDAO.findAll();
    }
    
    @GET
    @UnitOfWork
    @Path("/logs-assets/true")
    public List<LogData> listLogsWithAssets() {
        return logDataDAO.findAllWithAssets();
    }

}
