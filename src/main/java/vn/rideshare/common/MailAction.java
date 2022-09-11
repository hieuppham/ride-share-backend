package vn.rideshare.common;

public enum MailAction {
    UPDATE_USER("[Ride Share] Thông báo cập nhật tài khoản.", "Tài khoản của bạn đã được cập nhật.", "Mong bạn có thời gian vui vẻ cùng Ride Share"),
    UPDATE_RIDE("[Ride Share] Thông báo cập nhật hành trình.", "Hành trình của bạn đã được cập nhất.", "Mong bạn có thời gian vui vẻ cùng Ride Share");
    private String emailTitle;
    private String contentTitle;
    private String contentSmallTitle;

    public String getEmailTitle() {
        return emailTitle;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public String getContentSmallTitle() {
        return contentSmallTitle;
    }

    private MailAction(String emailTitle, String contentTitle, String contentSmallTitle) {
        this.emailTitle = emailTitle;
        this.contentTitle = contentTitle;
        this.contentSmallTitle = contentSmallTitle;
    }

}