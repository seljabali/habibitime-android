var methods = ["Set A PLUS Set B, 19$7778",
              // "Set A EQ Set B, 24$257",
              // "Set A AC Set B, 24$691",
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
  document.execCommand(id, false, null);
  //window.prompt("Copy '" + name + "'' Instance ID:", id);
}

function copy(str) {
    var sandbox = document.getElementsByTagName('sandbox')[0];
    sandbox.setAttribute("value", str);
    sandbox.innerHTML = str;
    sandbox.select();
    document.execCommand('copy');
    sandbox.setAttribute("value", "");
}


function loadPage() {
  var sandbox = document.createElement('div');
  sandbox.id = 'sandbox';
  sandbox.className = 'sandbox';
  document.getElementsByTagName('body')[0].appendChild(sandbox);

  var xoMethods = loadXOMethods();
  for (var i=0; i<xoMethods.length;i++) {
    var id = xoMethods[i].id;
    var name = xoMethods[i].name;
    var iDiv = document.createElement('div');
    iDiv.id = 'block';
    iDiv.className = 'block';
    // iDiv.addEventListener("click", function(){copyToClipboard(name, id)});

    // Create the inner div before appending to the body
    var innerDiv = document.createElement('div');
    innerDiv.className = 'block-2';
    innerDiv.innerHTML = name;
    // innerDiv.addEventListener("click", function(){copyToClipboard(name, id)});
    innerDiv.addEventListener("click", function(){copy(id)});
    // NOT WORKING BUG ~ http://stackoverflow.com/questions/6165149/javascript-alerts-in-an-onclick-in-a-chrome-extension-popup-immediately-disappea
    

    // The variable iDiv is still good... Just append to it.
    iDiv.appendChild(innerDiv); 

    // Then append the whole thing onto the body
    document.getElementsByTagName('body')[0].appendChild(iDiv);
  }
}

document.addEventListener('DOMContentLoaded', function () {
  loadPage();
});