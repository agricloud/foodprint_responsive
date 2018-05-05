<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="bootstrap">
    <title><g:message code="${grailsApplication.config.grails.i18nType}.view.reportViewer.title.label" default="履歷檢視" /></title>

  </head>
  <body>

            <h2 class="heading"><span aria-hidden="true" class="icon-menu-05"></span> ${report.title}</h2>
              <div class="right-icon text-right">Nutrition</div>


              <div id="split-tables">
              <table class="table-style-white" width="100%" border="0" cellspacing="0" cellpadding="0">
                <thead>
                  <tr>
                  <g:each in="${report.params[0]}" var="entry" >
                    <th><g:message code="${grailsApplication.config.grails.i18nType}.${entry.key}.label" /><span class="icon-table"><g:img dir="images" file="icon-table.png" /></span></th>
                  </g:each>
                  </tr>
                </thead>
                <tbody>
                  <g:each in="${report.params}" var="param" >
                    <tr>
                      <g:each in="${param}" var="entry" >

                        <td data-title='<g:message code="${grailsApplication.config.grails.i18nType}.${entry.key}.label" />'>${entry.value}</td>

                      </g:each>
                    </tr>
                  </g:each>

                </tbody>
              </table>
            </div>




  </body>
</html>
