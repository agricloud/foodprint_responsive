
class FoodpaintJob {
    def foodpaintService
    def testService

    def concurrent = false
    //job默認爲同步執行，設定false覆寫默認值，使其不會並行執行，待上一次執行完畢後才會執行下一次。
    
    // static triggers = {
    //     simple name: 'mySimpleTrigger', startDelay: 10000, repeatInterval: 600000
    // }

    // def group = "MyGroup"
    // def execute() {
    //     log.info "execute JOB::: foodpaintService.doDataImport() started."
    //     foodpaintService.doDataImport()
    //     log.info "execute JOB::: foodpaintService.doDataImport() finished."

    // }
}
