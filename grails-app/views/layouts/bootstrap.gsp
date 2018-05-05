<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title><g:layoutTitle default="foodprint"/> - <g:message code="${grailsApplication.config.grails.i18nType}.view.layouts.bootstrap.report.label" default="生產履歷" /></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <asset:link rel="shortcut icon" href="favicon.ico" type="image/x-icon"/>

    <asset:stylesheet href="foodprint-require.css"/>
    <asset:javascript src="foodprint-require.js"/>

    <g:javascript>
        $.urlParam = function(name) {
            var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
            if(results)
                return results[1] || 0;
            else
                return false;
        }
        // console.log($.urlParam('timeZoneId'));
        if(!$.urlParam('timeZoneId')) {
            var offset = new Date().getTimezoneOffset();
            var timeZoneId = "GMT" + (offset>0?"-":"%2b") + (offset/-60);
            window.location="?timeZoneId="+timeZoneId;
        }
    </g:javascript>

        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
          <script src="/js/html5shiv.js"></script>
          <script src="/js/respond.min.js"></script>
        <![endif]-->

    <g:layoutHead/>

    %{-- <g:justfont /> --}%
    %{-- <google:analytics /> --}%
    <script>
      (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
      (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
      m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
      })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

      ga('create', 'UA-57983723-1', 'auto');
      ga('require', 'displayfeatures');
      ga('send', 'pageview');

    </script>

    <!--analytics-->
    <script type="text/javascript">
      var _gaq = _gaq || [];
      _gaq.push(['_setAccount', 'UA-57983723-3']);
      _gaq.push(['_trackPageview']);

      (function() {
        var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
        ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
        var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
      })();
    </script>


</head>
<body>

<%--畫面可視區域：起點--%>

  <header>
    <div class="container">
      <div class="row">
        <g:link url="/reports/" >

        <div class="col-md-12 logo">
             <g:img dir="images" file="LeanMES_LOGO-2.png"/>
        </div>

        </g:link>

      </div>

    </div>
      <nav>


        <div class="container pc">

          <g:link url="/reports/${params?.name}/index" class="menu-01"><g:img dir="images" file="menu-01.png" /></g:link>
          <g:link url="/reports/${params?.name}/nutrition" class="menu-02"><g:img dir="images" file="menu-04.png" width="130px"/></g:link>
          <g:link url="/reports/${params?.name}/material" class="menu-03"><g:img dir="images" file="menu-02.png" /></g:link>
          <g:link url="/reports/${params?.name}/cultivate" class="menu-04"><g:img dir="images" file="menu-03.png" /></g:link>
          <g:link url="/reports/${params?.name}/quality" class="menu-05"><g:img dir="images" file="menu-05.png" /></g:link>
          <g:link url="/reports/${params?.name}/operator" class="menu-06"><g:img dir="images" file="menu-06.png" /></g:link>

        </div>

        <div class="phone">
          <div class="container">
            <div class="wrap clearfix">
              <g:link url="/reports/${params?.name}/index" class="menu-01">
              <div class="icon-menu-01 menu"></div><span><g:message code="${grailsApplication.config.grails.i18nType}.view.reportViewer.productionIntro.label" default="產品說明" /></span></g:link>
              <g:link url="/reports/${params?.name}/nutrition" class="menu-04">
              <div class="icon-menu-05 menu"></div><span><g:message code="${grailsApplication.config.grails.i18nType}.view.reportViewer.nutritionReport.label" default="營養標示" /></span></g:link>
              <g:link url="/reports/${params?.name}/material" class="menu-02">
              <div class="icon-menu-02 menu"></div><span><g:message code="${grailsApplication.config.grails.i18nType}.view.reportViewer.materialReport.label" default="原料履歷" /></span></g:link>
              <g:link url="/reports/${params?.name}/cultivate" class="menu-03">
              <div class="icon-menu-03 menu"></div><span><g:message code="${grailsApplication.config.grails.i18nType}.view.reportViewer.cultivateReport.label" default="栽種履歷" /></span></g:link>
              <g:link url="/reports/${params?.name}/quality" class="menu-04">
              <div class="icon-menu-04 menu"></div><span><g:message code="${grailsApplication.config.grails.i18nType}.view.reportViewer.qualityReport.label" default="檢驗履歷" /></span></g:link>
              <g:link url="/reports/${params?.name}/operator" class="menu-02">
              <div class="icon-menu-06 menu"></div><span><g:message code="${grailsApplication.config.grails.i18nType}.view.reportViewer.operatorReport.label" default="農民履歷" /></span></g:link>


            </div>

          </div>

          <div class="dashline"></div>
        </div>


      </nav>
  </header>





  <%--主畫面內容--%>
  <div class="main container">

    <div class="col-md-8 content box-shadow">
      <g:layoutBody/>


    </div>

    <div class="col-md-8 content2">
        <div id="alert_placeholder">

        </div>
    </div>
     <div class="bottomnav col-md-8">

        <div class="row">

            <g:if test="${batch?.id}">
              <div class="col-md-2 btn-download">
                <g:link action= 'showPdf' controller="attachment" params="[domainName:'batch', id:batch?.id, 'site.id': batch?.site?.id]">
                  <g:img dir="images" file="btn-download.png" />
                </g:link>
              </div>
            </g:if>


            <div class="col-md-4 contact">
              <a href="tel:0905-732-881" class="phone">0905-732-881</a>
            <!--  <a href="mailto:acloud@yuntech.edu.tw" class="mail">acloud@yuntech.edu.tw</a> -->
            </div>
%{--
            <div class="col-md-6 pagination-wrap">
              <div class="pagination grey-gradient">
                <a href="#" class="disabled">第一頁</a>
                <a href="#"><span class="icon-angle-left"></span></a>
                <span>...</span>
                <a class="active"href="#">1</a>
                <a href="#">2</a>
                <a href="#">3</a>
                <a href="#">4</a>
                <span>...</span>
                <a href="#"><span class="icon-angle-right"></span></a>
                <a href="#">末頁</a>
              </div>
            </div>
 --}%
        </div>

      </div>

  </div>




  <div class="bottomele">
    <div class="btn-fb">
      <a href="https://www.facebook.com/AgriCloud" target="_blank">
        <g:img dir="images" file="btn-fb.png" />
      </a>
    </div>

  </div>

  <footer>
    <div class="container">
      <div class="text">Copyright 2013-2018．All rights reserved</div>
    </div>
  </footer>




<%--畫面可視區域：終點--%>

<asset:script>

    bootstrap_alert = function() {}
    bootstrap_alert.warning = function(message) {
      $('#alert_placeholder').html(
        '<div class="alert alert-success fade in">'+
        '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>'+
        message+
        '</div>'
      )
    }

    if('${flash.message}'!=='') bootstrap_alert.warning('${flash.message}');


</asset:script>
<asset:deferredScripts/>

</body>
</html>
