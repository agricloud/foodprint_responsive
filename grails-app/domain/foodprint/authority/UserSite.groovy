package foodprint.authority

import foodprint.erp.Site
import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.apache.commons.lang.builder.HashCodeBuilder

class UserSite {

    def grailsApplication
    def messageSource

    String editor
    String creator
    Date dateCreated
    Date lastUpdated

    static belongsTo = [ user: User,
                         site: Site
                       ]

    static UserSite create(User user, Site site, boolean flush = false) {
        def instance = new UserSite(user: user, site: site)
        instance.save(flush: flush, insert: true)
        instance
    }

    static boolean remove(User u, Site r, boolean flush = false) {
        if (u == null || r == null) return false

        int rowCount = UserSite.where { user == u && site == r }.deleteAll()

        if (flush) { UserSite.withSession { it.flush() } }

        rowCount
    }

    static void removeAll(User u, boolean flush = false) {
        if (u == null) return

        UserSite.where { user == u }.deleteAll()

        if (flush) { UserSite.withSession { it.flush() } }
    }

    static void removeAll(Site r, boolean flush = false) {
        if (r == null) return

        UserSite.where { site == r }.deleteAll()

        if (flush) { UserSite.withSession { it.flush() } }
    }

    static constraints = {
        editor nullable: true
        creator nullable: true
        user(unique: ["site"])
    }


    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [UserSite]

        "${getMessageSource().getMessage("${i18nType}.userSite.label", args, Locale.getDefault())}: " +
        "${user.name}-${user.title}/${site.name}-${site.title}"
    }
}
