package foodprint.erp
/**
 * 異動類別
 *  1.入庫、2.銷貨、3.領用、4.轉撥、5.調整
 */
public enum TransactionType {
    /**
     * 入庫
     */
    STORAGE,
    /**
     * 銷貨
     */
    SALE,
    /**
     * 領用
     */
    REQUISITION,
    /**
     * 轉撥
     */
    TRANSFER,
    /**
     * 調整
     */
    ADJUSTMENT

}
