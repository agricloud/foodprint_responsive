package foodprint.authority

import foodprint.erp.SiteGroup
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes='name')
@ToString(includes='name', includeNames=true, includePackage=false)
class RoleGroup implements Serializable {

    def grailsApplication
    def messageSource

    private static final long serialVersionUID = 1

    String editor
    String creator
    Date dateCreated
    Date lastUpdated

    /*
     * 權限群組編號x
     * 一律大寫
     * -DEFAULT_開頭表示系統預設支群組
     * -SITEGROUP_開頭表示屬於指定農場之群組
     * -USER_${username}表示屬於指定使用者之群組
     */
    String name
    String title

    SiteGroup siteGroup

    RoleGroup(String name, String title) {
        this()
        this.name = name
        this.title = title
    }

    RoleGroup(String name, String title, SiteGroup siteGroup) {
        this()
        this.name = name
        this.title = title
        this.siteGroup = siteGroup
    }

    Set<Role> getAuthorities() {
        RoleGroupRole.findAllByRoleGroup(this)*.role
    }

    static constraints = {
        editor nullable: true
        creator nullable: true
        name unique: ['siteGroup']
        name blank: false
        siteGroup nullable: true
    }

    static mapping = {
        cache true
    }

    def beforeSave() {
        this.name = this.name.toUpperCase()

        if (!this.name.startsWith('SITEGROUP_') && !this.name.startsWith('DEFAULT_'))
            this.name = "SITEGROUP_${this.name}"
    }

    def beforeInsert() {
        beforeSave()
    }

    def beforeUpdate() {
        beforeSave()
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [RoleGroup]
        "${getMessageSource().getMessage("${i18nType}.roleGroup.label", args, Locale.getDefault())}: ${name}-${title}"
    }
}
