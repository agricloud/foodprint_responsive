foodprint
=========
Installation
----
### Install Grails 2.4.5 ###
    $ curl -s get.sdkman.io | bash
    $ source "$HOME/.sdkman/bin/sdkman-init.sh"
    $ sdk install grails ${version}
    $ grails -version

### Download Sencha CMD 6.1.2.15 ###

* http://www.sencha.com/products/sencha-cmd/download

<!-- check version -->

    $sencha which
	
### Download Sencha Architect 3.5.1.348 ###

* http://www.sencha.com/products/sencha-cmd/download

### Download Sencha ExtJs 5.1.1.451 ###

* https://www.sencha.com/products/extjs/evaluate/
* unzip the download file and put the file in your projects path:``${foodprint_HOME}/extjs-app/extjs/``

### Download Sencha Touch 2.4.2 ###

* http://www.sencha.com/products/touch/download/
* unzip the download file and put the file in your projects path:``${foodprint_HOME}/touch-app/extjs/``

Configuration
----
``~/${appName}-config.yml``

Run
----
    $ grails run-app
    

Url Mapping
----

| purpose         | url        | package        | namespaces |
| --------------- | ---------- | -------------- | ---------- |
|common controller| /          | /common        | none       |
|erp basic api    | /api/      | /erp/api/      | api        |
|sft basic api    | /api/      | /sft/api       | api        |
|sft pull api     | /api/pull/ | /sft/pull/api  | apipull    |
|sft push api     | /api/push/ | /sft/push/api  | apipush    |
|erp basic web    | /          | /erp/web/      | none       |
|sft basic web    | /          | /sft/web/      | none       |
|sft pull web     | /pull/     | /sft/pull/web  | pull       |
|sft push web     | /push/     | /sft/push/web  | push       |


Package
----
| purpose       | package        | controller | domain |
| ------------- | -------------- | :--------: | :----: |
| sft basic     | /sft           | V          | V      |
| sft basic api | /sft/api       | V          |        |
| sft basic web | /sft/web       | V          |        |
| sft pull      | /sft/pull      | V          | V      |
| sft pull api  | /sft/pull/api  | V          |        |
| sft pull web  | /sft/pull/web  | V          |        |
| sft push      | /sft/push      | V          | V      |
| sft push api  | /sft/push/api  | V          |        |
| sft push web  | /sft/push/web  | V          |        |
| erp basic     | /erp           | V          | V      |
| erp basic api | /erp/api       | V          |        |
| erp basic web | /erp/web       | V          |        |