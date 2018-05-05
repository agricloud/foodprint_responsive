<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="bootstrap">
        <title><g:message code="view.reportViewer.title.label" default="履歷檢視" /></title>

    </head>
    <body>

    <div class="panel panel-default">
      <!-- Default panel contents -->
      <div class="panel-heading">產品說明</div>
      <!-- form -->
      <form class="form-horizontal" role="form">
        <g:each in="${reportData.form.field}" var="field">
          <div class="form-group">
          <label class="col-lg-2 control-label">${field.'@label'}</label>
          <div class="col-lg-10">
            <g:if test="${field.'@label'=="圖片"}">
              <div class="row">
                  <div class="col-sm-6 col-md-3">
                        <img src=${field.text()} class="img-responsive" width=100>
              </div></div>
               %{-- <p class="form-control-static"> <img src=${field.text()} width=100 height=100> </p> --}%
            </g:if>
            <g:else>
                <p class="form-control-static">${field.text()}</p>
            </g:else>
          </div>
        </div>

        </g:each>

      </form>
    </div>



    <g:each in="${reportData.tabs.tab}" var="tab">

      <div class="panel panel-default">
           <!-- Default panel contents -->
          <div class="panel-heading">${tab.'@title'}</div>
            <!-- Table -->
            <table class="table">
              <thead>
                <tr>
                    <g:each in="${tab.table.head.column}" var="column">
                        <th> ${column.text()} </th>
                    </g:each>
                </tr>
              </thead>
              <tbody>
                 <g:each in="${tab.detail.row}" var="row">
                  <tr>
                      <g:each in="${row.cell}" var="cell">
                        <g:if test="${cell.img.size() > 0}">
                          <td>
                            <img src=${cell.img.'@src'[0]} class="img-responsive" width=200 maxWidth=100% >
                          </td>
                        </g:if>
                        <g:else>
                          <td>${cell.text()}</td>
                        </g:else>
                      </g:each>
                  </tr>
                </g:each>
                    </tbody>
            </table>
      </div>
    </g:each>

    </body>
</html>
