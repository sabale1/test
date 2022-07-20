package in.ecgc.smile.erp.accounts.integrate.model;


import java.sql.Timestamp;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Model Class For Notification Front End Application
 * @author Rajat Garg, Rohan Nadkarni, Shuchi Malaviya
 *
 */
public class Notification {
	private Long notificationId;
	
	@NotNull(message = "{notification.notificationTypeId.required}")
	@Min(value = 1, message = "{notification.notificationTypeId.min}")
	private Integer notificationTypeId;
	
	@NotNull(message = "{notification.notificationMessageId.required}")
	@Min(value = 1, message = "{notification.notificationMessageId.min}")
	private Integer notificationMessageId;
	
	@Min(value = 1, message = "{notification.moduleId.min}")
	private Integer moduleId;
	
	@Min(value = 1, message = "{notification.notificationGeneratedStateId.min}")
	private Integer notificationGeneratedStateId;

	@Min(value = 1, message = "{notification.processId.min}")
	private Integer processId;
	
	private Timestamp notificationReadOn;
	
	private Boolean isRead;
	
	@NotNull(message = "{notification.notificationTargetedUserName.required}")
	@NotBlank(message = "{notification.notificationTargetedUserName.required}")
	@Size(min = 1, max = 100, message = "{notification.notificationTargetedUserName.size}")
	private String notificationTargetedUserName;
	
	@NotNull(message = "{notification.notificationTargetedUserId.required}")
	@NotBlank(message = "{notification.notificationTargetedUserId.required}")
	@Size(min = 1, max = 50, message = "{notification.notificationTargetedUserId.size}")
	private String notificationTargetedUserId;
	
	@Min(value = 1, message = "{notification.processInstanceId.min}")
	private Long processInstanceId;
	
	private Boolean isDeleted;
	
	@NotNull(message = "{notification.notificationMessageDescription.required}")
	@NotBlank(message = "{notification.notificationMessageDescription.required}")
	@Size(min = 1, max = 1000, message = "{notification.notificationMessageDescription.size}")
	private String notificationMessageDescription;
	
	@Size(min = 1, max = 1000, message = "{notification.notificationUrl.size}")
	private String notificationUrl;
	
	@NotNull(message = "{notification.notificationGeneratedByUserId.required}")
	@NotBlank(message = "{notification.notificationGeneratedByUserId.required}")
	@Size(min = 1, max = 50, message = "{notification.notificationGeneratedByUserId.size}")
	private String notificationGeneratedByUserId;
	
	@NotNull(message = "{notification.notificationGeneratedByUserName.required}")
	@NotBlank(message = "{notification.notificationGeneratedByUserName.required}")
	@Size(min = 1, max = 100, message = "{notification.notificationGeneratedByUserName.size}")
	private String notificationGeneratedByUserName;
	
	private Timestamp createdDt;
	
	private String lastUpdateBy;
	
	private Timestamp lastUpdateDt;
	
	private String moduleName;
	
	private String processName;
	
	private String notificationSubject;
	
	private String notificationMessage;
	
	private String notificationTypeName;
	
	private String noticationPriority;

	@NotNull(message = "{notification.notificationGenerationDt.required}")
	private Timestamp notificationGenerationDt;

	public Long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}

	public Integer getNotificationTypeId() {
		return notificationTypeId;
	}

	public void setNotificationTypeId(Integer notificationTypeId) {
		this.notificationTypeId = notificationTypeId;
	}

	public Integer getNotificationMessageId() {
		return notificationMessageId;
	}

	public void setNotificationMessageId(Integer notificationMessageId) {
		this.notificationMessageId = notificationMessageId;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public Integer getNotificationGeneratedStateId() {
		return notificationGeneratedStateId;
	}

	public void setNotificationGeneratedStateId(Integer notificationGeneratedStateId) {
		this.notificationGeneratedStateId = notificationGeneratedStateId;
	}

	public Integer getProcessId() {
		return processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	public Timestamp getNotificationReadOn() {
		return notificationReadOn;
	}

	public void setNotificationReadOn(Timestamp notificationReadOn) {
		this.notificationReadOn = notificationReadOn;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	public String getNotificationTargetedUserName() {
		return notificationTargetedUserName;
	}

	public void setNotificationTargetedUserName(String notificationTargetedUserName) {
		this.notificationTargetedUserName = notificationTargetedUserName;
	}

	public String getNotificationTargetedUserId() {
		return notificationTargetedUserId;
	}

	public void setNotificationTargetedUserId(String notificationTargetedUserId) {
		this.notificationTargetedUserId = notificationTargetedUserId;
	}

	public Long getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getNotificationMessageDescription() {
		return notificationMessageDescription;
	}

	public void setNotificationMessageDescription(String notificationMessageDescription) {
		this.notificationMessageDescription = notificationMessageDescription;
	}

	public String getNotificationUrl() {
		return notificationUrl;
	}

	public void setNotificationUrl(String notificationUrl) {
		this.notificationUrl = notificationUrl;
	}

	public String getNotificationGeneratedByUserId() {
		return notificationGeneratedByUserId;
	}

	public void setNotificationGeneratedByUserId(String notificationGeneratedByUserId) {
		this.notificationGeneratedByUserId = notificationGeneratedByUserId;
	}

	public String getNotificationGeneratedByUserName() {
		return notificationGeneratedByUserName;
	}

	public void setNotificationGeneratedByUserName(String notificationGeneratedByUserName) {
		this.notificationGeneratedByUserName = notificationGeneratedByUserName;
	}

	public Timestamp getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Timestamp createdDt) {
		this.createdDt = createdDt;
	}

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public Timestamp getLastUpdateDt() {
		return lastUpdateDt;
	}

	public void setLastUpdateDt(Timestamp lastUpdateDt) {
		this.lastUpdateDt = lastUpdateDt;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getNotificationSubject() {
		return notificationSubject;
	}

	public void setNotificationSubject(String notificationSubject) {
		this.notificationSubject = notificationSubject;
	}

	public String getNotificationMessage() {
		return notificationMessage;
	}

	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}

	public String getNotificationTypeName() {
		return notificationTypeName;
	}

	public void setNotificationTypeName(String notificationTypeName) {
		this.notificationTypeName = notificationTypeName;
	}

	public String getNoticationPriority() {
		return noticationPriority;
	}

	public void setNoticationPriority(String noticationPriority) {
		this.noticationPriority = noticationPriority;
	}

	public Timestamp getNotificationGenerationDt() {
		return notificationGenerationDt;
	}

	public void setNotificationGenerationDt(Timestamp notificationGenerationDt) {
		this.notificationGenerationDt = notificationGenerationDt;
	}

	@Override
	public String toString() {
		return "Notification [notificationId=" + notificationId + ", notificationTypeId=" + notificationTypeId
				+ ", notificationMessageId=" + notificationMessageId + ", moduleId=" + moduleId
				+ ", notificationGeneratedStateId=" + notificationGeneratedStateId + ", processId=" + processId
				+ ", notificationReadOn=" + notificationReadOn + ", isRead=" + isRead
				+ ", notificationTargetedUserName=" + notificationTargetedUserName + ", notificationTargetedUserId="
				+ notificationTargetedUserId + ", processInstanceId=" + processInstanceId + ", isDeleted=" + isDeleted
				+ ", notificationMessageDescription=" + notificationMessageDescription + ", notificationUrl="
				+ notificationUrl + ", notificationGeneratedByUserId=" + notificationGeneratedByUserId
				+ ", notificationGeneratedByUserName=" + notificationGeneratedByUserName + ", createdDt=" + createdDt
				+ ", lastUpdateBy=" + lastUpdateBy + ", lastUpdateDt=" + lastUpdateDt + ", moduleName=" + moduleName
				+ ", processName=" + processName + ", notificationSubject=" + notificationSubject
				+ ", notificationMessage=" + notificationMessage + ", notificationTypeName=" + notificationTypeName
				+ ", noticationPriority=" + noticationPriority + ", notificationGenerationDt="
				+ notificationGenerationDt + "]";
	}	
}
