var width = Math.floor(window.outerWidth/2 - 30);

$.get("misc/withframework.java", function(code){ //With peripheral framework
    $("#withframework").text(code);
    hljs.highlightBlock(document.getElementById("withframework"));
});

$.get("misc/withoutframework.java", function(code){ //Without peripheral framework
    $("#withoutframework").text(code);
    hljs.highlightBlock(document.getElementById("withoutframework"));
});


