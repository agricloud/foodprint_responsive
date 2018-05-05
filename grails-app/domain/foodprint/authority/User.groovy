package foodprint.authority

import foodprint.erp.SiteGroup
import foodprint.erp.Site
import foodprint.erp.Workstation
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class User implements Serializable {

    private static final long serialVersionUID = 1

    transient springSecurityService

    def grailsApplication
    def messageSource

    String editor
    String creator
    Date dateCreated
    Date lastUpdated

    UserType userType = UserType.USER

    String username
    String password
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    /**
     * Full Name (Detail)
     */
    String fullName

    /**
     * E-Mail
     */
    String email

    SiteGroup siteGroup

    // 雙向 hasMany <-->belongsTo 擁有 cascade delete 行爲
    static hasMany = [userSites: UserSite, userRoleGroup: UserRoleGroup]

    Date lastLoginTime

    Site lastLoginSite

    // 使用此欄位前需先檢查 lastLoginWorkstation 之 site 與 lastLoginSite 是否相符，
    // 若不符合不允許使用。
    Workstation lastLoginWorkstation
    
    String lastLoginPanel

    String activationCode

    static mapping = {
        // 設置 updateable 後，將不會被標記 dirty flag。
        siteGroup updateable: false
        password column: '`password`'
        sites cascade: "refresh"
    }

    static constraints = {
        editor nullable: true
        creator nullable: true
        username unique: true, blank: false, matches: "[a-zA-Z][_a-zA-Z0-9-]{3,}", validator: validator
        password blank: false
        fullName blank: true
        email nullable: true, email: true
        lastLoginTime nullable: true
        lastLoginSite nullable: true
        lastLoginWorkstation nullable: true
        lastLoginPanel nullable: true
    }
    
    User(String username, String password) {
        this()
        this.username = username
        this.password = password
    }



    Set<RoleGroup> getAuthorities() {
        UserRoleGroup.findAllByUser(this)*.roleGroup
    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
    }

    static transients = ["springSecurityService"]

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [User]
        "${getMessageSource().getMessage("${i18nType}.user.label", args, Locale.getDefault())}: " +
        "${username}-${fullName}"
    }

    static validator = { col, obj ->
        // 不使用max，因為max所比較的date是固定值，為server啟動時當下的date，而非儲存user時重新執行new Date
        if (obj.isDirty('lastLoginTime') && obj.lastLoginTime.dateTimeString != new Date().dateTimeString) {
            return ["user.lastLoginTime.validator.error"]
        }

        if (obj.isDirty('password') && !(obj.password ==~ /^[a-zA-Z0-9]{4,}$/)) {
            return ["user.password.invalid"]
        }

        if (obj.activationCode && !(obj.activationCode ==~ /^[a-zA-Z0-9-]{4,}$/)) {
            return ["user.activationCode.invalid"]
        }
    }
}
