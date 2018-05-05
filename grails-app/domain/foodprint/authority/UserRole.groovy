package foodprint.authority

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.apache.commons.lang.builder.HashCodeBuilder

@ToString(cache=true, includeNames=true, includePackage=false)
class UserRole implements Serializable {

    def grailsApplication
    def messageSource

    private static final long serialVersionUID = 1

    User user
    Role role

    UserRole(User u, Role r) {
        this()
        user = u
        role = r
    }

    @Override
    boolean equals(other) {
        if (!(other instanceof UserRole)) {
            return false
        }

        other.user?.id == user?.id && other.role?.id == role?.id
    }

    @Override
    int hashCode() {
        def builder = new HashCodeBuilder()
        if (user) builder.append(user.id)
        if (role) builder.append(role.id)
        builder.toHashCode()
    }

    static UserRole get(long userId, long roleId) {
        criteriaFor(userId, roleId).get()
    }

    static boolean exists(long userId, long roleId) {
        criteriaFor(userId, roleId).count()
    }

    private static DetachedCriteria criteriaFor(long userId, long roleId) {
        UserRole.where {
            user == User.load(userId) &&
            role == Role.load(roleId)
        }
    }

    static UserRole create(User user, Role role, boolean flush = false) {
        def instance = new UserRole(user: user, role: role)
        instance.save(flush: flush, insert: true)
        instance
    }

    static boolean remove(User u, Role r, boolean flush = false) {
        if (u == null || r == null) return false

        int rowCount = UserRole.where { user == u && role == r }.deleteAll()

        if (flush) { UserRole.withSession { it.flush() } }

        rowCount
    }

    static void removeAll(User u, boolean flush = false) {
        if (u == null) return

        UserRole.where { user == u }.deleteAll()

        if (flush) { UserRole.withSession { it.flush() } }
    }

    static void removeAll(Role r, boolean flush = false) {
        if (r == null) return

        UserRole.where { role == r }.deleteAll()

        if (flush) { UserRole.withSession { it.flush() } }
    }

    static constraints = {
        user(unique: ["role"])
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
        Object[] args = [UserRole]

        "${getMessageSource().getMessage("${i18nType}.userRole.label", args, Locale.getDefault())}: " +
        "${user.name}-${user.title}/${role.name}-${role.title}"
    }
}


