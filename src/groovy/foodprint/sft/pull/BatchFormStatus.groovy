package foodprint.sft.pull

public enum BatchFormStatus {
    /**
     * 尚未/待轉換
     */
    PENDING,
    /**
     * 已轉換完全數量
     */
    COMPLETE,
    /**
     * 部分數量被轉換
     */
    PARTIAL
}
