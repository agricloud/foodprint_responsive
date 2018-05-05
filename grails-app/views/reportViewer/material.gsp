<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="bootstrap">
        <title><g:message code="${grailsApplication.config.grails.i18nType}.view.reportViewer.title.label" default="履歷檢視" /></title>

    </head>
    <body>


          <h2 class="heading"><span aria-hidden="true" class="icon-menu-02"></span> ${report.title}</h2>
            <div class="right-icon text-right">Material</div>

%{--             <div class="row search-row">
              <div class="col-md-12 search">
                  <div class="pull-left text">農產品批號:</div>
                  <div class="col-md-10">
                    <div class="input-group">
                      <input type="text" class="form-control">
                      <div class="input-group-btn">
                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></button>
                        <ul class="dropdown-menu pull-right">
                          <li><a href="#">2015-A</a></li>
                          <li><a href="#">2015-B</a></li>
                          <li><a href="#">2015-C</a></li>
                          <li><a href="#">FTINAA-20121217</a></li>
                        </ul>
                      </div><!-- /btn-group -->
                    </div><!-- /input-group -->
                  </div><!-- /.col-md-12 -->

                </div>

            </div> --}%

            <div id="split-tables">
            <table class="table-style-white" width="100%" border="0" cellspacing="0" cellpadding="0">
              <thead>
                <tr>
                <g:each in="${report.params.values()[0]}" var="entry" >
                  <th><g:message code="${grailsApplication.config.grails.i18nType}.${entry.key}.label" /><span class="icon-table"><g:img dir="images" file="icon-table.png" /></span></th>
                </g:each>
                </tr>
              </thead>
              <tbody>
                <g:each in="${report.params.values()}" var="param" >
                  <tr>
                    <g:each in="${param}" var="entry" >

                      <g:if test="${entry.key == 'default.image'}" >
                        <td data-title='<g:message code="${grailsApplication.config.grails.i18nType}.${entry.key}.label" />'><a href="${entry.value}" data-toggle="lightbox-image" title="${entry.key}"><g:img uri="${entry.value}" class="small-img" /></a></td>
                      </g:if>
                      <g:else>
                        <td data-title='<g:message code="${grailsApplication.config.grails.i18nType}.${entry.key}.label" />'
                        ${ entry.key == 'item.description'?'class=maxw-35':''}
                        ${ entry.key == 'item.spec'?'class=maxw-12':''}
                         >${entry.value}</td>
                      </g:else>

                    </g:each>


  %{--                   <td data-title="商品描述" class="maxw-35">芝麻有機肥(Sesame Meal)為芝麻提油後之副產品， 有黑白兩種，前者成品為黑色，後者成品為黃褐色。 芝麻油分可增加製粒潤滑度、含高氮肥，高價肥料。</td> --}%

                  </tr>
                </g:each>

              </tbody>
            </table>
          </div>




    <!-- end container-->



    </body>
</html>
