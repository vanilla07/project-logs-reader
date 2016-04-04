package com.logs.reader.core;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import java.sql.Timestamp;

@Entity
@Table(name = "logs")
@NamedQueries({
        @NamedQuery(
                name = "com.logs.reader.core.LogData.findAllWithAssets",
                query = "SELECT l FROM LogData l order by dateTime asc"
        ),
        @NamedQuery(
                name = "com.logs.reader.core.LogData.findAll",
                query = "SELECT l FROM LogData l "
                		+ "where l.url not like '/assets/%'"
                		+ "order by dateTime asc"
        ),
        @NamedQuery(
                name = "com.logs.reader.core.LogData.deleteAll",
                query = "delete from LogData"
        )
})
public class LogData {
    @Id
    private String uuid;

	private String ipAddress;
	private Timestamp dateTime;
	private String url;
	private String requestType;
	private String returnStatus;
	private int executionTime;
	private String details;
	
	public LogData() {
		super();
		this.details = "";
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ip_adress) {
		this.ipAddress = ip_adress;
	}

	public Timestamp getDateTime() {
		return dateTime;
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}

	public int getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(int executionTime) {
		this.executionTime = executionTime;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details += " > " + details + "<br>";
	}

	@Override
	public String toString() {
		return "LogData [uuid=" + uuid 
				+ ", ipAddress=" + ipAddress
				+ ", dateTime=" + dateTime 
				+ ", requestType=" + requestType 
				+ ", returnStatus=" + returnStatus
				+ ", executionTime=" + executionTime 
				+ ", url=" + url 
				+ ", details=" + details
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dateTime == null) ? 0 : dateTime.hashCode());
		result = prime * result + ((details == null) ? 0 : details.hashCode());
		result = prime * result + executionTime;
		result = prime * result
				+ ((ipAddress == null) ? 0 : ipAddress.hashCode());
		result = prime * result
				+ ((requestType == null) ? 0 : requestType.hashCode());
		result = prime * result
				+ ((returnStatus == null) ? 0 : returnStatus.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogData other = (LogData) obj;
		if (dateTime == null) {
			if (other.dateTime != null)
				return false;
		} else if (!dateTime.equals(other.dateTime))
			return false;
		if (details == null) {
			if (other.details != null)
				return false;
		} else if (!details.equals(other.details))
			return false;
		if (executionTime != other.executionTime)
			return false;
		if (ipAddress == null) {
			if (other.ipAddress != null)
				return false;
		} else if (!ipAddress.equals(other.ipAddress))
			return false;
		if (requestType == null) {
			if (other.requestType != null)
				return false;
		} else if (!requestType.equals(other.requestType))
			return false;
		if (returnStatus == null) {
			if (other.returnStatus != null)
				return false;
		} else if (!returnStatus.equals(other.returnStatus))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}
}
