$(function() {
    var e = [ 10, 8, 5, 7, 4, 6, 7, 1, 3, 5, 9, 4, 4, 1 ];
    $(".mini-graph.success").sparkline(e, {
        type: "bar",
        barColor: "#90c657",
        lineColor: "black",
        height: "40"
    });
    $(".inlinesparkline").sparkline();
    var e = [ 10, 8, 5, 3, 5, 7, 4, 6, 7, 1, 9, 4, 4, 1 ];
    $(".mini-graph.pie").sparkline(e, {
        type: "pie",
        barColor: "#54728c",
        height: "40"
    });
    var e = [ 10, 8, 5, 7, 4, 3, 5, 9, 4, 4, 1 ];
    $(".mini-graph.info").sparkline(e, {
        type: "bar",
        barColor: "#54b5df",
        height: "40"
    });
    var e = [ 10, 8, 5, 7, 4, 6, 7, 1, 3, 5, 9, 4, 4, 1 ];
    $(".mini-graph.danger").sparkline(e, {
        type: "bar",
        barColor: "#e45857",
        height: "40"
    });
});