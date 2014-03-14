var instance_methods = 
              ["Set Set B, 19$7778",
              "Se Set B, 24$257",
              "Se Set B, 24$691",
              // "Boolean A EQ Boolean B, 24$1964",
              // "Boolean A NE Boolean B, 94$2902",
              // "Date A LT Date B, 24$4123",
              "DateDate B, 24$4713"]; 

var numeric_methods = 
              ["Set A PLUS Set B, 19$7778",
              "Set A EQ Set B, 24$257",
              "Set A AC Set B, 24$691",
              // "Boolean A EQ Boolean B, 24$1964",
              // "Boolean A NE Boolean B, 94$2902",
              // "Date A LT Date B, 24$4123",
              "Date A LE Date B, 24$4713"];  



function loadXOMethods(method_list) {
  xoMethods = new Array();
  for (var i=0; i<methods2.length; i++) {
    xoMethods.push(new XOMethod (methods2[i].split(",")[0], methods2[i].split(", ")[1]));
  }
  return xoMethods;
}

function XOMethod (name, instanceid) {
    this.name = name;
    this.id = instanceid;
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


function handleCopyClick(iDiv, id, name) {
    var innerDiv = document.createElement('button');
    innerDiv.innerHTML = name;
    innerDiv.addEventListener("click", function() {
      copy(id);
    });
    iDiv.appendChild(innerDiv);
}

function loadTab(tab, list_of_methods){
 var xoMethods = loadXOMethods(list_of_methods);
  for (var i=0; i<xoMethods.length;i++) {
    var id = xoMethods[i].id;
    var name = xoMethods[i].name;
    var iDiv = document.createElement('div');

    handleCopyClick(iDiv, id, name);

    document.getElementsByTagName('body')[0].appendChild(iDiv);
  }
}

function loadPage() {
  var instance = document.getElementById('instance');
  loadTab(instance, instance_methods);
  //instance.addEventListener("click", function(){loadTab(instance, instance_methods)});
}

document.addEventListener('DOMContentLoaded', function () {
  loadPage();
});