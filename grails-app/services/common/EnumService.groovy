package common

import grails.converters.JSON

class EnumService {

    def grailsApplication
    def messageSource

    def i18nType

    def values(Class enumClass) {// ex :foodprint.Country ---- [[name:TAIWAN, title:"台灣"], [name:JAPAN, title:"日本"], [name:ITALY, title:"台灣"]] ....

        def className = enumClass.getSimpleName()
        className = className[0].toLowerCase() + className[1..-1]

        def enumClassAry = enumClass.values() // enumClassAry instanceof Enum[]

        Object[] obj
    
        def result = values(enumClassAry)

        result
    }

    def values(enumList) {

        Object[] obj

        def result = []

        enumList.each {

            def className = it.class.getSimpleName()
            className = className[0].toLowerCase() + className[1..-1]

            def enumClassJson = [:]
            enumClassJson.title = messageSource.getMessage("${i18nType}.${className}.${it}.label", obj, Locale.getDefault())
            enumClassJson.name = it.name()

            result << enumClassJson
        }

        result
    }

    def name(enumInstance) {// ex: Country country ---- [name:TAIWAN, title:台灣]

        def className = enumInstance.class.getSimpleName()
        className = className[0].toLowerCase() + className[1..-1]
        def enumInstanceJson =[:]

        Object[] obj
        enumInstanceJson.name = enumInstance.name()
        enumInstanceJson.title = messageSource.getMessage("${i18nType}.${className}.${enumInstance.name()}.label", obj, Locale.getDefault())

        enumInstanceJson
    }
}
