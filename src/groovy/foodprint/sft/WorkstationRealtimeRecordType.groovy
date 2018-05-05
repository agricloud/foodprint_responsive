package foodprint.sft

public enum WorkstationRealtimeRecordType {
    /**
     * 發放
     */
    RELEASE,
    /**
     * 取消發放
     */
    RECOVERYRELEASE,
    /**
     * 進站
     */
    CHECKIN,
    /**
     * 取消進站
     */
    RECOVERYCHECKIN,
    /**
     * 出站
     */
    CHECKOUT,
    /**
     * 取消出站
     */
    RECOVERYCHECKOUT,
    /**
     * 取消重工
     */
    RECOVERYREWORK,
    /**
     * 進站重工
     */
    CHECKINREWORK,
    /**
     * 出站重工
     */
    CHECKOUTREWORK,
    /**
     * 轉倉管入庫
     */
    TURNTOSTOCKIN,
    /**
     * 取消轉倉管入庫
     */
    RECOVERYTURNTOSTOCKIN,
    /**
     * 送檢
     */
    SPCIN,
    /**
     * 取消送檢
     */
    RECOVERYSPCIN,
    /**
     * 檢驗完成並出站
     */
    SPCOUT,
    /**
     * SPC驗退
     */
    SPCREJECT
}


 // 該生產線執行的類型。分別如下：
 // release=發放
 // cancelRelease=取消發放
 // checkIn=進站
 // cancelCheckIn=取消進站
 // checkOut=出站
 // cancelCheckOut=取消出站
 // cancelRework=取消重工   //2012/04/13 ADD
 // checkInRework=進站重工  //2012/04/13 ADD
 // checkOutRework=出站重工 //2012/04/13 ADD
 // IntoStockIn=轉倉管入庫
 // cancelIntoStock=取消轉倉管入庫
 // IntoWH=倉管入庫
 // cancelIntoWH=取消倉管入庫
 // spcIn=送檢
 // cancelspcIn=取消送檢
 // spcOut=檢驗完成並出站
 // spcReject=spc驗退
 // QMSCheck=QMS同步單據確認 //2012/08/08 3.7.4.5 ADD 
 // QMSCancel=QMS同步單據取消 //2012/08/08 3.7.4.5 ADD
 // 途程順序/加工段順序/替代加工編碼/製程順序
 // 記錄製令批量進出站時間記錄的製程位置