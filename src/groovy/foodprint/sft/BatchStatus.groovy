package foodprint.sft

public enum BatchStatus {
    /**
     * 待發放
     */
    PENDING,
    /**
     * 待進站
     */
    CHECKIN,
    /**
     * 待出站
     */
    CHECKOUT,
    /**
     * 檢驗中
     */
    QUALITYCONTROL,
    /**
     * 批量已經完工
     */
    FINISHED,
    /**
     * 指定完工
     */
    ASSIGNEDFINISHED,
    /**
     * 待倉管入庫
     */
    PENDINGSTOCK
}