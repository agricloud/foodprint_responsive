<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="bootstrap">
        <title><g:message code="${grailsApplication.config.grails.i18nType}.view.reportViewer.title.label" default="履歷檢視" /></title>

    </head>
    <body>
      <h2 class="heading"><span aria-hidden="true" class="icon-menu-06"></span> ${report.title}</h2>
      <div class="right-icon text-right">Operator</div>

          <g:each in="${report.params}" var="param" >
            <tr>
              <div class="row product-info clearfix">

                <div class="dashline"></div>

                <div class="col-md-4 col-sm-4"><g:img uri="${param["default.image"]}" class="thumbnail" /></div>

                <div class="col-md-8 col-sm-8">
                  <table>
                    <g:each in="${param}" var="entry" >
                      <g:if test="${entry.key != 'default.image'}" >
                        <tr>
                          <td width="100"><span class="icon-table"><g:img dir="images" file="icon-table.png" /></span> <span class="font-brown"><g:message code="${grailsApplication.config.grails.i18nType}.${entry.key}.label" /></span></td>
                          <td>${entry.value}</td>
                        </tr>
                       </g:if>
                    </g:each>
                  </table>
                </div>

              </div>

            </tr>
          </g:each>

    <!-- end container-->

    </body>
</html>
