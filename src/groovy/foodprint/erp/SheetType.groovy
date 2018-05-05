package foodprint.erp
/*
 * 單據性質
 *
 *  1?:庫存、2?:訂單、3?:採購、4?:BOM、5?:製令、6?:應收、7?:應付、8?:票據、9?:會計、
 *  A?:進口、B?:出口、C?:固定資產、D?:製程、E?:維修、
 *  F?:模具、G?：保稅、H?:軍福品、I?:零用金、J?:人力資源、
 *  K?:海關合同、L?:IDL、M?:會計多帳本、N?:設備保養 
 */
public enum SheetType {

    /**
     * 庫存：ERP說明
     * 計有11.一般異動單據、12.庫存轉撥單據、13.借出轉撥單據、14.借入暫收單據、15.借出歸還單據、16.借入歸還單據及17.成本開帳調整單據七種。
     * 一般異動單據類別如1.庫存、2.訂單、3.採購、4.BOM、5.製令、6.應收及7.應付。
     *「轉撥」一般又稱調撥，指兩個同質「存貨倉」，倉間數量位置的移轉。
     * 單據性質為「一般異動單據」時，則 [異動類別] 欄位不可為『轉撥』。
     * 單據性質為「庫存轉撥單據」、「借出轉撥單據」、「借入暫收單據」、「借出歸還單據」、「借入歸還單據」，則 [異動類別] 欄位預設『轉撥』且不可修改，略過不輸入。
     * 單據性質為「成本開帳調整單據」，則預設影響成本『y.成本調整』，預設且異動類別不可輸入。
     * 修改狀態時，自動顯示「單據性質」資料。
     */

    /**
     * 庫存：一般異動單據
     */
    TRANSACTION(Group.INVENTORY),
    /**
     * 庫存：庫存轉撥單據
     */
    // TRANSFER(Group.INVENTORY),
    /**
     * 庫存：借出轉撥單據
     */
    // LENDINGTRANSFER(Group.INVENTORY),
    /**
     * 庫存：借入暫收單據
     */
    // BORROWINGTRANSFER(Group.INVENTORY),
    /**
     * 庫存：借出歸還單據
     */
    // LENDINGRETURNTRANSFER(Group.INVENTORY),
    /**
     * 庫存：借入歸還單據
     */
    // BORROWINGRETURNTRANSFER(Group.INVENTORY),
    /**
     * 庫存：成本開帳調整單據
     */
    // OPENINGBALANCEADJUSTMENT(Group.INVENTORY),

    /**
     * 訂單：ERP說明
     * 共分21.報價單、22.客戶訂單、23.銷貨單、24.銷退單、27.多角貿易訂單、28.多角貿易銷貨單、29.多角貿易銷退單、2A.派車單、2B合約訂單九種單據性質。
     * 查詢時單據性質為「27.多角貿易訂單」及「29.多角貿易銷退單」者，其性質須為『訂單』。
     * 有購買『多角貿易管理系統』(MTP)才顯示「27.多角貿易訂單」、「28.多角貿易銷貨單」及「29.多角貿易銷退單」之單據性質。
     * 共用參數設定之功能別為「大陸版」，其單據性質「23.銷貨單」或「24.銷退單」時，[發票限額] 欄位才可輸入。
     * 修改狀態時，自動顯示單據性質。
     * 當單據性質為「2B.合約訂單」時，[核對訂單] 欄位名稱顯示為 [後置單據可修改單價] 且預設N。
     */
    /**
     * 訂單：報價單
     */
    // QUOTATION(Group.CUSTOMERORDER),
    /**
     * 訂單：客戶訂單
     */
    CUSTOMERORDER(Group.CUSTOMERORDER),
    /**
     * 訂單：銷貨單
     */
    SALESHEET(Group.CUSTOMERORDER),
    /**
     * 訂單：顧客交貨看板
     */
    DELIVERYKANBAN(Group.CUSTOMERORDER),
    /**
     * 訂單：銷退單
     */
    SALERETURNSHEET(Group.CUSTOMERORDER),
    /**
     * 訂單：多角貿易訂單
     */
    // MULTITRADECUSTOMERORDER(Group.CUSTOMERORDER),
    /**
     * 訂單：多角貿易銷貨單
     */
    // MULTITRADESALESHEET(Group.CUSTOMERORDER),
    /**
     * 訂單：多角貿易銷退單
     */
    // MULTITRADESALERETURNSHEET(Group.CUSTOMERORDER),
    /**
     * 訂單：派車單
     */
    // VEHICLEDISPATCH(Group.CUSTOMERORDER),
    /**
     * 訂單：合約訂單
     */
    // CONTRACTORDER(Group.CUSTOMERORDER),

    /**
     * 採購：ERP說明
     * 系統設定時定義的性質代號為31.請購單據、32.核價單單據、33.採購單據、34.進貨單據、35.退貨單據、27.多角貿易採購單據、29.多角貿易退貨單據及36.合約採購。
     * 查詢時單據性質為「27.多角貿易訂單」及「29.多角貿易退貨單據」者僅能為『採購』性質。
     * 有購買『多角貿易系統』(MTP)才顯示「27.多角貿易採購單據」及「29.多角貿易退貨」單據資料。
     * 修改狀態時，輸入後顯示「單據性質」。
     * 當單據性質為「31.請購單據」、「33.採購單據」、「34.進貨單據」、「35.退貨單據」及「36.合約採購」時，[單據輸入工程品號] 欄位才可輸入。
     */
    /**
     * 採購：請購單據
     */
    // PURCHASEREQUISITION(Group.PURCHASE),
    /**
     * 採購：核價單單據
     */
    // PRICECHECKING(Group.PURCHASE),
    /**
     * 採購：採購單據
     */
    // PURCHASEORDER(Group.PURCHASE),
    /**
     * 採購：進貨單據
     */
    PURCHASESHEET(Group.PURCHASE),
    /**
     * 採購：退貨單據
     */
    PURCHASERETURNSHEET(Group.PURCHASE),
    /**
     * 採購：多角貿易採購單據
     */
    // MULTITRADEPURCHASEORDER(Group.PURCHASE),
    /**
     * 採購：多角貿易退貨單據
     */
    // MULTITRADEPURCHASERETURNSHEET(Group.PURCHASE),
    /**
     * 採購：合約採購
     */
    // CONTRACTPURCHASE(Group.PURCHASE),

    /**
     * BOM：ERP說明
     * 可分為41.BOM變更單據、42.組合單、43.拆解單及44.E-BOM變更單據。
     * 此為系統判斷單據特性的依據（系統不會用單據名稱來判斷）。
     * 不可空白。
     * 當為修改狀態時,自動顯示單據性質。
     */
    /**
     * BOM：BOM變更單據
     */
    // BOMCHANGEORDER(Group.BOM),
    /**
     * BOM：組合單
     */
    // MERCHANDISEASSORTMENT(Group.BOM),
    /**
     * BOM：拆解單
     */
    // MERCHANDISEKNOCKDOWN(Group.BOM),
    /**
     * BOM：E-BOM變更單據
     */
    // EBOMCHANGEORDER(Group.BOM),

    /**
     * 製令：ERP說明
     * 單據之性質可分為51.製令工單、52.重工工單、54.廠內領料、55.託外領料、56.廠內退料、57.託外退料、58.生產入庫、59.託外進貨、5A.託外退貨、5B.核價單、5C.挪料單。但不可空白。
     * 異動類別：
     * (1).單據性質58.生產入庫、59.託外進貨、5A.託外退貨，預設值為「1」。
     * (2).單據性質54.廠內領料、55.託外領料、56.廠內退料、57.託外退料、5C.挪料、預設值為「3」，其餘單據性質自動顯示為空白。
     * 影響成本：單據性質58.生產入庫、59.託外進貨、5A.託外退貨，預設值為「Y」，其餘單據性質自動顯示為「N」。
     * 更新入庫日：單據性質58.生產入庫、59.託外進貨，預設值為「Y」，其餘單據性質自動顯示為「N」。
     * 更新出庫日：單據性質54.廠內領料、55.託外領料，預設值為「Y」，其餘單據性質自動顯示為「N」。
     * 更新核價：
     * (1).單據性質5B.核價單，預設值為「Y」，其餘自動顯示為「N」。
     * (2).單據性質59.託外進貨、5B.核價單，才可輸入，其餘不可輸入。
     * 核對製令：
     * (1).單據性質54.廠內領料、55.託外領料、56.廠內退料、57.託外退料、58.生產入庫，、59.託外進貨、5A.託外退貨，5C.挪料、預設值為「Y」，其餘自動顯示為「N」。
     * (2).單據性質54.廠內領料、55.託外領料、56.廠內退料、57.託外退料、58.生產入庫、59.託外進貨、5A.託外退貨可輸入，其餘不可輸入。
     * 直接結帳：
     * (1).單據性質59.託外進貨、5A.託外退貨，預設值為「Y」，其餘自動顯示為「N」。
     * (2).單據性質59.託外進貨、5A.託外退貨可輸入，其餘不可輸入。
     * 庫存影響：單據性質54.廠內領料、55.託外領料、5A.託外退貨，預設值為「-1」，其餘單據性質自動顯示為「+1」。
     * 製令類別：
     * (1).單據性質51.製令工單、52.重工工單、預設值為「1」，其餘自動顯示為「9」。
     * (2).單據性質51.製令工單、52.重工工單可輸入，其餘不可輸入。
     * (3).單據性質51.製令工單、52.重工工單，僅可輸入「1或2」不可輸入「9」。
     * 納入MRP計算：
     * (1).單據性質51.製令工單、52.重工工單，預設值為「Y」，其餘自動顯示為「N」。
     * (2).單據性質51.製令工單、52.重工工單可輸入，其餘不可輸入.
     * 自動確認：預設N。
     * 單據性質為「5B.核價單」或「5C.挪料單」者， [單據輸入工程品號] 欄位預設為N且不可輸入。
     * 修改狀態時，自動顯示「單據性質」資料。
     */
    /**
     * 製令：製令工單
     */
    MANUFACTUREORDER(Group.MANUFACTUREORDER),
    /**
     * 製令：重工工單
     */
    // REWORKORDER(Group.MANUFACTUREORDER),
    /**
     * 製令：廠內領料
     */
    MATERIALSHEET(Group.MANUFACTUREORDER),
    /**
     * 製令：託外領料
     */
    OUTSRCMATERIALSHEET(Group.MANUFACTUREORDER),
    /**
     * 製令：廠內退料
     */
    MATERIALRETURNSHEET(Group.MANUFACTUREORDER),
    /**
     * 製令：託外退料
     */
    OUTSRCMATERIALRETURNSHEET(Group.MANUFACTUREORDER),
    /**
     * 製令：生產入庫
     */
    STOCKINSHEET(Group.MANUFACTUREORDER),
    /**
     * 製令：託外進貨
     */
    OUTSRCPURCHASESHEET(Group.MANUFACTUREORDER),
    /**
     * 製令：託外退貨
     */
    OUTSRCPURCHASERETURNSHEET(Group.MANUFACTUREORDER),
    /**
     * 製令：核價單
     */
    // PRICECHECKING(Group.MANUFACTUREORDER),
    /**
     * 製令：挪料單
     */
    // MATERIALSHIFTSHEET(Group.MANUFACTUREORDER),
    
    /**
     * 製程：ERP說明
     * 區分為D1.投料,D2.移轉,D3.入庫及D4.報工四種.
     * 修改狀態時,系統自動顯示「單據性質」.
     * 單據性質為「D1.投料,D2.移轉,D3.入庫」時,其 [單據輸入工程品號] 選項,才可輸入.
     */
    /**
     * 製程：投料
     */
    ORDERRELEASE(Group.ROUTE),
    /**
     * 製程：移轉
     */
    OPERATIONTRANSFER(Group.ROUTE),
    /**
     * 製程：入庫
     */
    OPERATIONSTORAGE(Group.ROUTE),
    /**
     * 製程：報工
     */
    WORKREPORT(Group.ROUTE)

    private Group group

    SheetType(Group group) {
        this.group = group
        this.group.sheetTypes << this
    }

    public enum Group {
        INVENTORY,
        CUSTOMERORDER,
        PURCHASE,
        BOM,
        MANUFACTUREORDER,
        ROUTE

        protected List<SheetType> sheetTypes = new ArrayList<SheetType>()

        boolean exists(SheetType sheetType) {
            return this == sheetType.group
        }
    }
}


