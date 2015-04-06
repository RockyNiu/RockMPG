// get local jquery.min.js
window.jQuery || document.write('<script src="../bower_components/jquery/dist/jquery.min.js"><\/script>');

// get local bootstrap.min.css
$(document).ready(function () {
    var paddingRight = $('.container').css('padding-right');
    if (paddingRight != '15px') {
        $("head").prepend('<link rel="stylesheet" href="../bower_components/bootstrap/dist/css/bootstrap.min.css">');
    }
});

// get local bootstrap.min.js
if (typeof($.fn.modal) === 'undefined') {
    document.write('<script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"><\/script>');
}

// get local angular
window.angular || document.write('<script src="../bower_components/angular/angular.min.js"><\/script>');

//show toast
function showAndroidToast(toast) {
     Android.showToast(toast);
}