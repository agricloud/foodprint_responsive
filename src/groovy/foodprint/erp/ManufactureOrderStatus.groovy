package foodprint.erp
/*
 * 公告類型
 */
public enum ManufactureOrderStatus {
    /*
     * 待發放
     */
    PENDING,
    /*
     * 已發放未生產
     */
    APPROVED,
    /*
     * 部分發放
     */
    PARTIALLY,
    /*
     * 未領料
     */
    UNDISBURSED,
    /*
     * 已發料
     */
    DISBURSED,
    /*
     * 生產中
     */
    INPROCESS,
    /*
     * 已完工
     */
    FINISHED,
    /*
     * 指定完工/結案
     */
    ASSIGNEDFINISHED

}
