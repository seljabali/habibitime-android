//http://stackoverflow.com/questions/13899299/write-text-to-clipboard


var methods = ["Set A PLUS Set B, 19$7778",
              "Set A EQ Set B, 24$257",
              "Set A AC Set B, 24$691",
              // "Boolean A EQ Boolean B, 24$1964",
              // "Boolean A NE Boolean B, 94$2902",
              // "Date A LT Date B, 24$4123",
              "Date A LE Date B, 24$4713"];  

function XOMethod (name, instanceid) {
    this.name = name;
    this.id = instanceid;
}

function loadXOMethods() {
  xoMethods = new Array();
  for (var i=0; i<methods.length; i++) {
    xoMethods.push(new XOMethod (methods[i].split(",")[0], methods[i].split(", ")[1]));
  }
  return xoMethods;
}

function copyToClipboard(name, id) {
  window.prompt("Copy '" + name + "'' Instance ID:", id);
}

function copy(str) {
        var copyDiv = document.createElement('div');
        copyDiv.contentEditable = true;
        document.body.appendChild(copyDiv);
        copyDiv.innerHTML = str;
        copyDiv.unselectable = 'off';
        copyDiv.focus();
        document.execCommand('SelectAll');
        document.execCommand('copy', false, null);
        document.body.removeChild(copyDiv);
        window.close();
}


function handleCopyClick(iDiv, i, id, name) {
    var innerDiv = document.createElement('button');
    innerDiv.innerHTML = name;
    innerDiv.addEventListener("click", function() {
      copy(id);
    });

    iDiv.appendChild(innerDiv); 
}

function loadPage() {
  var xoMethods = loadXOMethods();
  for (var i=0; i<xoMethods.length;i++) {
    var id = xoMethods[i].id;
    var name = xoMethods[i].name;
    var iDiv = document.createElement('div');
    iDiv.id = 'block'+i.toString();
    iDiv.className = 'block';

    handleCopyClick(iDiv, i, id, name);

    
    // Then append the whole thing onto the body
    document.getElementsByTagName('body')[0].appendChild(iDiv);
  }
}


document.addEventListener('DOMContentLoaded', function () {
  loadPage();
});