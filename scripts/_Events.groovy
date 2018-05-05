eventCompileEnd = {
    ant.copy(todir:classesDirPath) {
      fileset(file:"${basedir}/grails-app/conf/BeanConfig.groovy")
    }
}