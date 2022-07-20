package in.ecgc.smile.erp.accounts.integrate.model;

import java.sql.Timestamp;


/**
 * Model Class For Custom Notification
 * @author Rajat Garg, Rohan Nadkarni, Shuchi Malaviya
 *
 */
public class CustomNotification {
	
	private Long notificationId;
	
	private Timestamp notificationReadOn;
	
	private Boolean isRead;
	
	private String notificationTargetedUserName;
	
	private String notificationTargetedUserId;	

	private Boolean isDeleted;
	
	// @NotNull(groups = {AddEntity.class}, message = "{notification.notificationMessageDescription.required}")
	// @NotBlank(groups = {AddEntity.class}, message = "{notification.notificationMessageDescription.required}")
	// @Size(groups = {AddEntity.class}, min = 1, max = 1000, message = "{notification.notificationMessageDescription.size}")
	private String notificationMessageDescription;
	
	private String notificationGeneratedByUserId;
	
	private String notificationGeneratedByUserName;
	
	private Timestamp createdDt;
	
	private String lastUpdateBy;
	
	private Timestamp lastUpdateDt;
	
	// @NotNull(groups = {AddEntity.class}, message = "{notification.notificationSubject.required}")
	// @NotBlank(groups = {AddEntity.class}, message = "{notification.notificationSubject.required}")
	// @Size(groups = {AddEntity.class}, min = 1, max = 300, message = "{notification.notificationSubject.size}")
	private String notificationSubject;

	private Timestamp notificationGenerationDt;
	
	public Long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
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

	public String getNotificationSubject() {
		return notificationSubject;
	}

	public void setNotificationSubject(String notificationSubject) {
		this.notificationSubject = notificationSubject;
	}

	public Timestamp getNotificationGenerationDt() {
		return notificationGenerationDt;
	}

	public void setNotificationGenerationDt(Timestamp notificationGenerationDt) {
		this.notificationGenerationDt = notificationGenerationDt;
	}

	@Override
	public String toString() {
		return "CustomNotification [notificationId=" + notificationId + ", notificationReadOn=" + notificationReadOn
				+ ", isRead=" + isRead + ", notificationTargetedUserName=" + notificationTargetedUserName
				+ ", notificationTargetedUserId=" + notificationTargetedUserId + ", isDeleted=" + isDeleted
				+ ", notificationMessageDescription=" + notificationMessageDescription
				+ ", notificationGeneratedByUserId=" + notificationGeneratedByUserId
				+ ", notificationGeneratedByUserName=" + notificationGeneratedByUserName + ", createdDt=" + createdDt
				+ ", lastUpdateBy=" + lastUpdateBy + ", lastUpdateDt=" + lastUpdateDt + ", notificationSubject="
				+ notificationSubject + ", notificationGenerationDt=" + notificationGenerationDt + "]";
	}
}
