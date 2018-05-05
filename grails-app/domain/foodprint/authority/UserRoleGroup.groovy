package foodprint.authority

import foodprint.erp.SiteGroup

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.apache.commons.lang.builder.HashCodeBuilder

@ToString(cache=true, includeNames=true, includePackage=false)
class UserRoleGroup implements Serializable {

    def grailsApplication
    def messageSource

    private static final long serialVersionUID = 1

    String editor
    String creator
    Date dateCreated
    Date lastUpdated

    static belongsTo = [user: User]
    RoleGroup roleGroup

    SiteGroup siteGroup

    UserRoleGroup(User u, RoleGroup rg) {
        this()
        user = u
        roleGroup = rg
        siteGroup = u.siteGroup
    }

    @Override
    boolean equals(other) {
        if (!(other instanceof UserRoleGroup)) {
            return false
        }

        other.user?.id == user?.id && other.roleGroup?.id == roleGroup?.id
    }

    @Override
    int hashCode() {
        def builder = new HashCodeBuilder()
        if (user) builder.append(user.id)
        if (roleGroup) builder.append(roleGroup.id)
        builder.toHashCode()
    }

    static UserRoleGroup get(long userId, long roleGroupId) {
        criteriaFor(userId, roleGroupId).get()
    }

    static boolean exists(long userId, long roleGroupId) {
        criteriaFor(userId, roleGroupId).count()
    }

    private static DetachedCriteria criteriaFor(long userId, long roleGroupId) {
        UserRoleGroup.where {
            user == User.load(userId) &&
            roleGroup == RoleGroup.load(roleGroupId)
        }
    }

    static UserRoleGroup create(User user, RoleGroup roleGroup, boolean flush = false) {
        def instance = new UserRoleGroup(user: user, roleGroup: roleGroup, siteGroup: user.siteGroup)
        instance.save(flush: flush, insert: true)
        instance
    }

    static boolean remove(User u, RoleGroup rg, boolean flush = false) {
        if (u == null || rg == null) return false

        int rowCount = UserRoleGroup.where { user == u && roleGroup == rg }.deleteAll()

        if (flush) { UserRoleGroup.withSession { it.flush() } }

        rowCount
    }

    static void removeAll(User u, boolean flush = false) {
        if (u == null) return

        UserRoleGroup.where { user == u }.deleteAll()

        if (flush) { UserRoleGroup.withSession { it.flush() } }
    }

    static void removeAll(RoleGroup rg, boolean flush = false) {
        if (rg == null) return

        UserRoleGroup.where { roleGroup == rg }.deleteAll()

        if (flush) { UserRoleGroup.withSession { it.flush() } }
    }

    static constraints = {
        editor nullable: true
        creator nullable: true
        user(unique: ["roleGroup"])
    }

    static mapping = {
        version false
    }

    def getGrailsApplication() {
        return grailsApplication
    }

    def getMessageSource() {
        return messageSource
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [UserRoleGroup]
        "${getMessageSource().getMessage("${i18nType}.userRoleGroup.label", args, Locale.getDefault())}: ${user.username}/${roleGroup.name}-${roleGroup.title}"
    }
}
