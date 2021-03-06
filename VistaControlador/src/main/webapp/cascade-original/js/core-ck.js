// Author: Vijay Kumar
// Template: Cascade - Flat & Responsive Bootstrap Admin Template
// Version: 1.0
// Bootstrap version: 3.0.0
// Copyright 2013 bootstrapguru
// www: http://bootstrapguru.com
// mail: support@bootstrapguru.com
// You can find our other themes on: https://bootstrapguru.com/themes/
// jQuery $('document').ready(); function 
function displayResult(e, t, n) {
    window.location.replace(t);
}

$("document").ready(function() {
    $(document).skylo("start");
    $(".nav-input-search").focus();
    $("ul.nav-list").accordion();
    $(".settings-toggle").click(function(e) {
        e.preventDefault();
        $(".right-sidebar").toggleClass("right-sidebar-hidden");
    });
    $(".left-sidebar .nav > li > ul > li.active").parent().css("display", "block");
    $(".left-sidebar .nav > li a span").hover(function() {
        var e = $(this).parent().find("i");
        e.removeClass("animated shake").addClass("animated shake");
        var t = window.setTimeout(function() {
            e.removeClass("animated shake");
        }, 1300);
    });
    $(".right-sidebar-holder").niceScroll({
        cursorcolor: "#54728c"
    });
    $(".btn-nav-toggle-responsive").click(function() {
        $(".left-sidebar").toggleClass("show-fullsidebar");
    });
    $("li.nav-toggle > button").click(function(e) {
        e.preventDefault();
        $(".hidden-minibar").toggleClass("hide");
        $(".site-holder").toggleClass("mini-sidebar");
        $(".toggle-left").hasClass("fa-angle-double-left") ? $(".toggle-left").removeClass("fa-angle-double-left").addClass("fa-angle-double-right") : $(".toggle-left").removeClass("fa-angle-double-right").addClass("fa-angle-double-left");
        if ($(".site-holder").hasClass("mini-sidebar")) {
            $(".sidebar-holder").tooltip({
                selector: "a",
                container: "body",
                placement: "right"
            });
            $("li.submenu ul").tooltip("destroy");
        } else $(".sidebar-holder").tooltip("destroy");
    });
    if ($(".site-holder").hasClass("mini-sidebar")) {
        $(".sidebar-holder").tooltip({
            selector: "a",
            container: "body",
            placement: "right"
        });
        $("li.submenu").tooltip("destroy");
    } else $(".sidebar-holder").tooltip("destroy");
    $(".show-info").click(function() {
        $(".page-information").toggleClass("hidden");
    });
    $(".panel-close").click(function(e) {
        e.preventDefault();
        $(this).parent().parent().parent().parent().fadeOut();
    });
    $(".panel-minimize").click(function(e) {
        e.preventDefault();
        var t = $(this).parent().parent().parent().next(".panel-body");
        t.is(":visible") ? $("i", $(this)).removeClass("fa-chevron-up").addClass("fa-chevron-down") : $("i", $(this)).removeClass("fa-chevron-down").addClass("fa-chevron-up");
        t.slideToggle();
    });
    $(".panel-settings").click(function(e) {
        e.preventDefault();
        $("#myModal").modal("show");
    });
    $(".fa-hover").click(function(e) {
        e.preventDefault();
        var t = $(this).find("i").attr("class");
        $(".modal-title").php(t);
        $(".icon-show").php('<i class="' + t + ' fa-5x "></i>&nbsp;&nbsp;<i class="' + t + ' fa-4x "></i>&nbsp;&nbsp;<i class="' + t + ' fa-3x "></i>&nbsp;&nbsp;<i class="' + t + ' fa-2x "></i>&nbsp;&nbsp;<i class="' + t + ' "></i>&nbsp;&nbsp;');
        $(".modal-footer span.icon-code").php('"' + t + '"');
        $("#myModal").modal("show");
    });
    $(".finish").click(function() {
        $(this).parent().toggleClass("finished");
        $(this).toggleClass("fa-square-o");
    });
    $(".btn-print").click(function() {
        window.print();
    });
    $(".faq-list li").click(function() {
        $(this).find("i.fa-plus-square").toggleClass("fa-minus-square");
    });
    $.failsafe({
        checkUrl: "css/failsafe.css",
        checkInterval: 5e3,
        chargeThreshold: 80
    });
    $(document).skylo("end");
});

$(".todo-table i.finish-task").click(function() {
    $(this).toggleClass("fa-square-o");
    $(this).parent().parent().toggleClass("finish");
});

$(".todo-table .header-row i.finish-task").click(function() {
    $(this).parent().parent().parent().find("tr").toggleClass("finish");
    $(this).parent().parent().parent().find("tr td i.finish-task").toggleClass("fa-square-o");
});

$(".todo-table .btn-delete").click(function() {
    confirm("Are you sure to delete this item?");
    $(this).parent().parent().fadeOut();
});

$(function() {
    $(".sortable").sortable();
});

$(".demo-list-group a").click(function() {
    $(".demo-list-group a").removeClass("active");
    $(this).addClass("active");
});

mesos = [ "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" ];

dias = [ "M", "T", "W", "Th", "F", "S", "Su" ];

$("#bic_calendar_right").bic_calendar({
    nombresMes: mesos,
    dias: dias,
    req_ajax: {
        type: "get",
        url: "index.php"
    }
});

$(".nav-input-search").typeahead({
    source: [ {
        id: "index.php",
        name: "Dashboard"
    }, {
        id: "widgets.php",
        name: "Widgets"
    }, {
        id: "chat.php",
        name: "Chat"
    }, {
        id: "gallery.php",
        name: "Gallery"
    }, {
        id: "calendar.php",
        name: "Calendar"
    }, {
        id: "grids.php",
        name: "Grids"
    }, {
        id: "coming-soon.php",
        name: "Coming Soon"
    }, {
        id: "screens.php#register",
        name: "Signin"
    }, {
        id: "screens.php",
        name: "Login"
    }, {
        id: "faq.php",
        name: "Faq"
    }, {
        id: "template.php",
        name: "Template Page"
    }, {
        id: "template.php",
        name: "Basic Template"
    }, {
        id: "404.php",
        name: "404"
    }, {
        id: "402.php",
        name: "402"
    }, {
        id: "505.php",
        name: "505"
    }, {
        id: "helpers.php",
        name: "Helpers"
    }, {
        id: "top-navbar.php",
        name: "Top Navbar"
    }, {
        id: "profile.php",
        name: "Profile Activity"
    }, {
        id: "profile-two.php",
        name: "Profile Posts"
    }, {
        id: "top-navbar.php",
        name: "Top Menu"
    }, {
        id: "images.php",
        name: "Images"
    }, {
        id: "inbox.php",
        name: "Inbox"
    }, {
        id: "invoice.php",
        name: "Invoice"
    }, {
        id: "pricing-table.php",
        name: "Pricing Table"
    }, {
        id: "typography.php",
        name: "Typography"
    }, {
        id: "support.php",
        name: "Support"
    }, {
        id: "alerts.php",
        name: "Alerts"
    }, {
        id: "animations.php",
        name: "Animations"
    }, {
        id: "breadcrumbs-jumbotron.php",
        name: "Breadcrumbs Jumbotron"
    }, {
        id: "breadcrumbs-jumbotron.php",
        name: "Breadcrumbs"
    }, {
        id: "breadcrumbs-jumbotron.php",
        name: "Jumbotron"
    }, {
        id: "buttons.php",
        name: "Buttons"
    }, {
        id: "carousel.php",
        name: "Carousel"
    }, {
        id: "notifications.php",
        name: "Notifications"
    }, {
        id: "knobs.php",
        name: "Knobs"
    }, {
        id: "labels-badges.php",
        name: "Labels Badges"
    }, {
        id: "labels-badges.php",
        name: "Labels"
    }, {
        id: "labels-badges.php",
        name: "Badges"
    }, {
        id: "list-groups.php",
        name: "List Groups"
    }, {
        id: "pagination.php",
        name: "Pagination"
    }, {
        id: "panels.php",
        name: "Panels"
    }, {
        id: "progress-bars.php",
        name: "Progress Bars"
    }, {
        id: "scrollspy.php",
        name: "Scrollspy"
    }, {
        id: "sliders.php",
        name: "Sliders"
    }, {
        id: "tabs-accordians.php",
        name: "Tabs-accordians"
    }, {
        id: "tabs-accordians.php",
        name: "Tabs"
    }, {
        id: "tabs-accordians.php",
        name: "Accordians"
    }, {
        id: "info-boxes.php",
        name: "Info boxes"
    }, {
        id: "tooltips-popovers.php",
        name: "Tooltips-popovers"
    }, {
        id: "tooltips-popovers.php",
        name: "Tooltips"
    }, {
        id: "tooltips-popovers.php",
        name: "Popovers"
    }, {
        id: "wells.php",
        name: "Wells"
    }, {
        id: "basic-tables.php",
        name: "Basic tables"
    }, {
        id: "editable-tables.php",
        name: "Editable tables"
    }, {
        id: "dynamic-tables.php",
        name: "Dynamic tables"
    }, {
        id: "dropzone-file-upload.php",
        name: "Dropzone file upload"
    }, {
        id: "multiple-file-upload.php",
        name: "Multiple file upload"
    }, {
        id: "form-input-masks.php",
        name: "Form input masks"
    }, {
        id: "form-input-masks.php",
        name: "Input-masks"
    }, {
        id: "form-validation.php",
        name: "Form validation"
    }, {
        id: "form-validation.php",
        name: "Validation"
    }, {
        id: "form-wizard.php",
        name: "Form wizard"
    }, {
        id: "input-groups.php",
        name: "Input groups"
    }, {
        id: "pickers.php",
        name: "Pickers"
    }, {
        id: "inbox.php",
        name: "Mail"
    }, {
        id: "pickers.php",
        name: "Color picker"
    }, {
        id: "pickers.php",
        name: "Date picker"
    }, {
        id: "layouts-elements.php",
        name: "Layouts elements"
    }, {
        id: "layouts-elements.php",
        name: "Form elements"
    }, {
        id: "layouts-elements.php",
        name: "Form layouts"
    }, {
        id: "wysiwyg-markdown.php",
        name: "Wysiwyg-markdown"
    }, {
        id: "wysiwyg-markdown.php",
        name: "Markdown Editor"
    }, {
        id: "wysiwyg-markdown.php",
        name: "Wysiwyg Editor"
    }, {
        id: "wysiwyg-markdown.php",
        name: "Form editor"
    }, {
        id: "basic-charts.php",
        name: "Basic charts"
    }, {
        id: "live-charts.php",
        name: "Live charts"
    }, {
        id: "morris.php",
        name: "Morris charts"
    }, {
        id: "pie-charts.php",
        name: "Pie-charts"
    }, {
        id: "sparklines.php",
        name: "Sparklines"
    }, {
        id: "nvd3.php",
        name: "Nvd3"
    }, {
        id: "google-maps.php",
        name: "Google maps"
    }, {
        id: "google-maps.php",
        name: "Maps"
    }, {
        id: "vector-maps.php",
        name: "Vector maps"
    }, {
        id: "icons.php",
        name: "Icons"
    } ],
    itemSelected: displayResult
});