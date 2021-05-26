package tech.itpark.project_delivery_web.entity;

public enum UserStatus {
    ACTIVE("active"), DELETED("deleted"), BLOCKED("blocked");

    private final String status;

    UserStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static UserStatus fromString(String status){
        for(UserStatus userStatus: UserStatus.values()){
            if(userStatus.getStatus().equalsIgnoreCase(status)){
                return  userStatus;
            }
        }
        throw new IllegalArgumentException("Status with name:" + status +" not found");
    }
}
