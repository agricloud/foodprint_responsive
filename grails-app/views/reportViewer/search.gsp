<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="bootstrap">
        <title><g:message code="${grailsApplication.config.grails.i18nType}.view.reportViewer.title.label" default="履歷檢視" /></title>
  <style type="text/css">

.typeahead,
.tt-query,
.tt-hint {
    width: 396px;
    height: 34px;
    padding: 8px 12px;
    font-size: 14px;
    line-height: 30px;
    border: 2px solid #ccc;
    -webkit-border-radius: 8px;
    -moz-border-radius: 8px;
    border-radius: 8px;
    outline: none;
}

.typeahead {
    background-color: #fff;
}

.typeahead:focus {
    border: 2px solid #0097cf;
}

.tt-query {
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
    -moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
}

.tt-hint {
    color: #999
}



.twitter-typeahead .tt-query,
.twitter-typeahead .tt-hint {
  margin-bottom: 0;
}

.tt-dropdown-menu {
  min-width: 160px;
  margin-top: 2px;
  padding: 5px 0;
  background-color: #fff;
  border: 1px solid #ccc;
  border: 1px solid rgba(0,0,0,.2);
  *border-right-width: 2px;
  *border-bottom-width: 2px;
  -webkit-border-radius: 6px;
     -moz-border-radius: 6px;
          border-radius: 6px;
  -webkit-box-shadow: 0 5px 10px rgba(0,0,0,.2);
     -moz-box-shadow: 0 5px 10px rgba(0,0,0,.2);
          box-shadow: 0 5px 10px rgba(0,0,0,.2);
  -webkit-background-clip: padding-box;
     -moz-background-clip: padding;
          background-clip: padding-box;
}

.tt-suggestion {
  display: block;
  padding: 3px 20px;
}

.tt-suggestion.tt-is-under-cursor {
  color: #fff;
  background-color: #0081c2;
  background-image: -moz-linear-gradient(top, #0088cc, #0077b3);
  background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#0088cc), to(#0077b3));
  background-image: -webkit-linear-gradient(top, #0088cc, #0077b3);
  background-image: -o-linear-gradient(top, #0088cc, #0077b3);
  background-image: linear-gradient(to bottom, #0088cc, #0077b3);
  background-repeat: repeat-x;
  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ff0088cc', endColorstr='#ff0077b3', GradientType=0)
}

.tt-suggestion.tt-is-under-cursor a {
  color: #fff;
}

.tt-suggestion p {
  margin: 0;
}



.twitter-typeahead .tt-hint {
    display: block;
    height: 34px;
    padding: 6px 12px;
    font-size: 14px;
    line-height: 1.428571429;
    border: 1px solid transparent;
    border-radius:4px;
}
.twitter-typeahead .hint-small {
    height: 30px;
    padding: 5px 10px;
    font-size: 12px;
    border-radius: 3px;
    line-height: 1.5;
}
.twitter-typeahead .hint-large {
    height: 45px;
    padding: 10px 16px;
    font-size: 18px;
    border-radius: 6px;
    line-height: 1.33;
}

.twitter-typeahead {
     width: 100%;
}
.tt-dropdown-menu {
     width: 100%;
}
.tt-hint {
     width: 100%;
}
.repo-language {
    float: right;
    font-style: italic;
}

.repo-name {
    font-weight: bold;
}

.repo-description {
    font-size: 14px;
}

  </style>

    </head>


    <body>

    <g:form url="/reports/query">
    <h2 class="heading"><span aria-hidden="true" class="icon-icon-01"></span> <g:message code="${grailsApplication.config.grails.i18nType}.view.reportViewer.search.queryBatchReport.label" default="查詢批號履歷" /> </h2>
      <div class="right-icon text-right">Search</div>


      <div class="row search-row">
        <div class="col-md-12 search">

          <div class="pull-left text"><g:message code="${grailsApplication.config.grails.i18nType}.view.reportViewer.search.produceBatch.label" default="農產品批號" />:</div>
          <div class="col-md-10">
            <div class="input-group">
                <g:field placeholder='${message(code:"${grailsApplication.config.grails.i18nType}.view.reportViewer.search.entryBatch.label")}' name='name' type="text" class="form-control" id="batchName" />
                <div class="input-group-btn">
                  <button id="submitBtn" type="submit" class="btn btn-default dropdown-toggle" data-toggle="dropdown"><g:message code="${grailsApplication.config.grails.i18nType}.view.reportViewer.search.submit.label" default="送出" /></button>
                </div><!-- /btn-group -->


            </div><!-- /input-group -->
          </div><!-- /.col-md-10 -->

        </div><!-- /.col-md-12  search-->
          <div class="dashline"></div>
      </div>

      </g:form>

        <g:form url="/reports/query">
              <div class="row search-row">
                <div class="col-md-12 search">

                    <div class="pull-left text">柳丁批號(許順利)</div>
                      <div class="col-md-10">
                        <div class="input-group">

                            <g:field readonly="true" name='name' type="text" class="form-control" value="TW001201700004" />
                            <div class="input-group-btn">
                                 <button type="submit" class="btn btn-default dropdown-toggle" data-toggle="dropdown"><g:message code="${grailsApplication.config.grails.i18nType}.view.reportViewer.search.submit.label" default="送出" /></button>
                            </div><!-- /btn-group -->
                        </div><!-- /input-group -->
                      </div><!-- /.col-md-10 -->
                </div><!-- /.col-md-12  search-->
                <div class="dashline"></div>
            </div><!-- /row search-row-->
        </g:form>

        <asset:deferredScripts/>


    </body>

    <asset:script type="text/javascript">
        $('#batchName').typeahead({
            name: 'name',
            remote: '/batch/typeahead?query=%QUERY&max=20',
            // template: '<p><strong>{{value}}</strong>-{{itemTitle}}</p>',
            template: [
                '<p class="repo-language">{{itemTitle}}</p>',
                '<p class="repo-name">{{value}}</p>',
                '<p class="repo-description">{{itemDescription}}</p>'
            ].join(''),

            engine: Hogan


        });

        $('#batchName').on('typeahead:selected', function(evt, item) {
            $('#batchName').parents( "form" ).submit();
        });
        $('.btn.btn-default.dropdown-toggle').on('click',function(){
            $(this).parents( "form" ).submit();
        });

        $('.submitBtn').keydown(function(event) {
            if (event.keyCode == 13) {
                this.form.submit();
                return false;
            }
        });

    </asset:script>
    <asset:deferredScripts/>

</html>
