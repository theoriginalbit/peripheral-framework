var width = Math.floor(window.outerWidth/2 - 30);

$.get("withframework.java", function(code){ //With peripheral framework
    $("#withframework").text(code);
    hljs.highlightBlock(document.getElementById("withframework"));
});

$.get("withoutframework.java", function(code){ //Without peripheral framework
    $("#withoutframework").text(code);
    hljs.highlightBlock(document.getElementById("withoutframework"));
});


