package com.logs.reader.resources;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.jadira.usertype.spi.utils.lang.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.logs.reader.LogUtils;
import com.logs.reader.core.LogData;
import com.logs.reader.db.LogDataDAO;

@Produces(MediaType.APPLICATION_JSON)
@Path("/generate")
public class GenerateResource {

	private final LogDataDAO logDataDAO;
	
	private Logger logger = Logger.getLogger(GenerateResource.class);

	public GenerateResource(LogDataDAO logDataDAO) {
		this.logDataDAO = logDataDAO;
	}

	@POST
	@UnitOfWork
	public int parseLogFile() {
		List<LogData> all = logDataDAO.findAll();

		if (all.isEmpty()) {
			BufferedReader br = null;
			try {
				String currentLine;
				LogData logTmp = null;

				br = new BufferedReader(new FileReader("sample.log"));

				while ((currentLine = br.readLine()) != null) {
					if (LogUtils.isLogLine(currentLine)) {
						// new entry
						if (LogUtils.isLogEntry(currentLine)) {
							logTmp = new LogData();
							LogUtils.setLogEntry(currentLine, logTmp);
						}
						// update entry
						else {
							Optional<LogData> logTmpOptional = logDataDAO.findById(LogUtils.getLogUuid(currentLine));
							if (logTmpOptional.isPresent()){
								logTmp = logTmpOptional.get();
								if (LogUtils.isLogReturn(currentLine)) {
									LogUtils.setLogReturn(currentLine, logTmp);
								}
							}
						}
						if (null != logTmp && null != logTmp.getUuid()){
							LogUtils.setLogDetails(currentLine, logTmp);
							logDataDAO.create(logTmp);
						}
						else {
							logger.warn("This line was not extracted : " + currentLine);
						}
						
					}
					else if (null != logTmp && null != logTmp.getUuid() && !StringUtils.isEmpty(currentLine)) {
						logTmp.setDetails(currentLine);
						logDataDAO.create(logTmp);
					}
					else {
						logger.warn("This line was not extracted : " + currentLine);
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null) br.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				all = logDataDAO.findAll();
			}
		}
		return all.size();
	}
	
	@DELETE
	@UnitOfWork
	@Path("/clear")
	public int emptyTable() {
		return logDataDAO.deleteAll();
	}
}
