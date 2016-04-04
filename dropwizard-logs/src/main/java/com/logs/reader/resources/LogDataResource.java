package com.logs.reader.resources;

import com.logs.reader.core.LogData;
import com.logs.reader.db.LogDataDAO;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.NonEmptyStringParam;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/logs/{logId}")
@Produces(MediaType.APPLICATION_JSON)
public class LogDataResource {

    private final LogDataDAO logDataDAO;

    public LogDataResource(LogDataDAO peopleDAO) {
        this.logDataDAO = peopleDAO;
    }

    @GET
    @UnitOfWork
    public LogData getLog(@PathParam("logId") NonEmptyStringParam logId) {
        return findSafely(logId.get().get());
    }

    private LogData findSafely(String logId) {
        return logDataDAO.findById(logId).orElseThrow(() -> new NotFoundException("No such log data."));
    }
}
