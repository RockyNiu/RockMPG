// get local bootstrap.min.css
$(document).ready(function () {
    var bodyColor = $('body').css('color');
    if (bodyColor != 'rgb(51, 51, 51)') {
        $("head").prepend('<link rel="stylesheet" href="../bower_components/bootstrap/dist/css/bootstrap.min.css">');
    }
});

// get local jquery.min.js
window.jQuery || document.write('<script src="../bower_components/jquery/dist/jquery.min.js"><\/script>');

// get local bootstrap.min.js
if (typeof($.fn.modal) === 'undefined') {
    document.write('<script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"><\/script>');
}

// get local angular
(function () {
    try {
        window.angular.module('ngRoute');
    }
    catch (e) {
        return false;
    }
    return true;
})() || document.write('<script src="../bower_components/angular/angular.min.js"><\/script>');

//show toast
function showAndroidToast(toast) {
     Android.showToast(toast);
}