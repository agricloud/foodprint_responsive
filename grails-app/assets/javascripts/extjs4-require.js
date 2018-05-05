if(grails.util.Environment.currentEnvironment == grails.util.Environment.DEVELOPMENT){
    //= require extjs4/resources/ext-theme-neptune/ext-theme-neptune-all.css
    //= require ext/ext-all.js
    //= require ext/ext-theme-neptune.js
    //= require app.js

}
else{
    //= extjs4/resources/foodprint-all.css
    //= extjs4/all-classes.js
}
