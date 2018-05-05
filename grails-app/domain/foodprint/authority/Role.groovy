package foodprint.authority

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes='authority')
@ToString(includes='authority', includeNames=true, includePackage=false)
class Role implements Serializable {

    def grailsApplication
    def messageSource

    private static final long serialVersionUID = 1

    String authority

    Role(String authority) {
        this()
        this.authority = authority
    }

    static constraints = {
        authority blank: false, unique: true
    }

    static mapping = {
        cache true
    }

    public String toString() {
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [Role]
        "${getMessageSource().getMessage("${i18nType}.role.label", args, Locale.getDefault())}: " +
        "${authority}"
    }
}
