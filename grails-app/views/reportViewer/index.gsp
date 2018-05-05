<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="bootstrap">
        <title><g:message code="${grailsApplication.config.grails.i18nType}.view.reportViewer.title.label" default="履歷檢視" /></title>
    </head>
    <body>

        <h2 class="heading"><span aria-hidden="true" class="icon-icon-01"></span> ${product.title}</h2>
          <div class="right-icon text-right">Products</div>


          <!--banner-->
          %{--
          <div id="myCarousel"  class="slider">
            <div class="flexslider">
              <ul class="slides">
                <li style="display: list-item;">
                  <g:img uri='/attachment/show/${batch.item.id}?domainName=item&useBlank=true' />
                </li>
              </ul>
            </div>
          </div>
          --}%
          <!--banner-end-->




          <div class="row product-info clearfix">
            <div class="dashline"></div>

            <div class="col-md-4 col-sm-4"><g:img uri='/attachment/show/${batch.item.id}?domainName=item&fileFormat=img&useBlank=true&site.id=${batch.site?.id}' class="thumbnail" /></div>
              <div class="col-md-8 col-sm-8">
                <table>

                  <g:each in="${product.head}" var="entry" >
                   <tr>
                      <td width="100"><span class="icon-table"><g:img dir="images" file="icon-table.png" /></span> <span class="font-brown"><g:message code="${grailsApplication.config.grails.i18nType}.${entry.key}.label" /></span></td>
                        <td>${entry.value}</td>
                    </tr>
                  </g:each>

                </table>

              </div>
          </div>

          <div id="split-tables">
            <table class="table-style" width="100%" border="0" cellspacing="0" cellpadding="0">
              <thead>
                <tr>
                  <g:each in="${product.body}" var="entry" >
                    <th><g:message code="${grailsApplication.config.grails.i18nType}.${entry.key}.label" /><span class="icon-table"><g:img dir="images" file="icon-table.png" /></span></th>
                  </g:each>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <g:each in="${product.body}" var="entry" >
                    <td data-title='<g:message code="${grailsApplication.config.grails.i18nType}.${entry.key}.label" />'
                      ${ entry.key == 'batch.expirationDate'?'class=maxw-35':''}
                    >
                        <g:if test="${entry.key=='batch.manufactureDate' || entry.key=='batch.expirationDate'}">
                            <g:if test="${params.timeZoneId}">
                                <g:formatDate date="${entry.value}" format="yyyy.MM.dd" timeZone="${TimeZone.getTimeZone(params.timeZoneId)}"/>
                            </g:if>
                            <g:else>
                                 no timezone detect
                            </g:else>
                        </g:if>
                        <g:else>
                             ${entry.value}
                        </g:else>
                    </td>
                  </g:each>
                </tr>
              </tbody>
            </table>
          </div>

          <g:each in="${reports}" var="report">
            <h2 class="heading"><span aria-hidden="true" class="icon-menu-02"></span> ${report.title}</h2>
              %{--
              <div class="right-icon text-right">Material</div>
              --}%

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
          </g:each>




    </body>
</html>
