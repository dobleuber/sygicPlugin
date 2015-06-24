var exec = require('cordova/exec');

function SygicPlugin() {
    console.log("CoolPlugin.js: is created");
}

CoolPlugin.prototype.showToast =
    function(aString){
        console.log("CoolPlugin.js: showToast");
        exec(
            function(result){
                alert("OK" + reply);
            },
            function(result){
                alert("Error" + reply);
            },
            "SygicPlugin", aString,[]);
}

var sygicPlugin = new SygicPlugin();
module.exports = sygicPlugin;