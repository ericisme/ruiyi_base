// Avoid `console` errors in browsers that lack a console.
(function() {
    var method;
    var noop = function () {};
    var methods = [
        'assert', 'clear', 'count', 'debug', 'dir', 'dirxml', 'error',
        'exception', 'group', 'groupCollapsed', 'groupEnd', 'info', 'log',
        'markTimeline', 'profile', 'profileEnd', 'table', 'time', 'timeEnd',
        'timeStamp', 'trace', 'warn'
    ];
    var length = methods.length;
    var console = (window.console = window.console || {});

    while (length--) {
        method = methods[length];

        // Only stub undefined methods.
        if (!console[method]) {
            console[method] = noop;
        }
    }
}());


// $('input').iCheck({
//   // checkboxClass: 'signup_checkbox',
//   // radioClass: 'signup_radio',
//   checkedLabelClass: ture
//   // labelHover: false,
//   // cursor: true,
//   // increaseArea: '20%'
// });

$('input').icheck({
    checkboxClass: 'signup_checkbox',
    radioClass: 'signup_radio',
    mirror: true,
    checkedLabelClass: 'checked',
    enabledClass: false,
    // enabledClass: 'ssss',
    // hoverLabelClass: ture,
    autoInit: false
});