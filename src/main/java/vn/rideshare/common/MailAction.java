package vn.rideshare.common;

public enum MailAction {
    UPDATE_USER("[Ride Share] Thông báo yêu cầu cập nhật thông tin tài khoản.", "Cảm ơn bạn đã tin tưởng lựa chọn dịch vụ của chúng tôi.", "Thông tin của bạn đã được lưu lại và chờ phê duyệt"),
    UPDATE_USER_STATUS_ACTIVE("[Ride Share] Thông báo kích hoạt tài khoản.", "Tài khoản của bạn đã được kích hoạt.", "Mong bạn có thời gian vui vẻ cùng Ride Share"),
    UPDATE_USER_STATUS_INACTIVE("[Ride Share] Thông báo ngừng kích hoạt tài khoản.", "Tài khoản của bạn đã ngừng kích hoạt.", "Chúng tôi chờ đợi sự trở lại của bạn"),
    CREATE_RIDE("[Ride Share] Thông báo yêu cầu chia sẻ hành trình.", "Bạn có yêu cầu chia sẻ hành trình.", "Thông tin của bạn đã được lưu lại và chờ phê duyệt"),
    UPDATE_RIDE_STATUS_ACTIVE("[Ride Share] Thông báo kích hoạt hành trình.", "Hành trình của bạn đã được kích hoạt.", "Mong bạn sớm tìm được bạn đồng hành"),
    UPDATE_RIDE_STATUS_INACTIVE("[Ride Share] Thông báo ngừng kích hoạt hành trình.", "Hành trình của bạn đã ngừng kích hoạt.", "Chúng tôi chờ đợi sự trở lại của bạn"),
    NOTIFY_RIDE_END("[Ride Share] Thông báo hành trình kết thúc", "Hành trình của bạn đã kết thúc.", "Mong bạn tiếp tục chia sẻ nhiều hơn trong tương lại");

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