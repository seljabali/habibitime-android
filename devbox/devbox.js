var instance_methods = 
              ["Set A + B, 19$7778",
              "Set A - B, 19$460",              
              "Set A or B if A is empty, 19$16631",
              "Set A == B, 24$257",
              "Set A == B or A not present, 24$6193",
              "Set A != B, 24$6375",              
              "Set A ac B, 24$691",
              "Set A ac B or B Empty, 24$4479",
              "Set A nc B, 24$2892",
              "Count of Set A, 18$3506",
              "Count of Set A == 1, 24$10796",
              "Count of Set A >= 1, 24$8129",
              "Count of Set A > 1, 24$15013"]; 

var numeric_methods = 
              ["Numeric A + B, 35$803",
              "Numeric A * B, 35$1667",
              "Numeric A * -1, 35$188",
              "Numeric A < B, 24$11603",
              "Numeric A <= B, 24$1014",
              "Numeric A == B, 24$11624",
              "Numeric A >= B, 24$3836",
              "Numeric A > B, 24$1015",
              "Numeric A < 1, 24$10245",
              "Numeric A == 0, 24$13631",
              "Numeric A != 0, 94$8596",
              "Numeric A > 0, 24$340",
              "Numeric A == 1, 24$1179",              
              "Numeric A > 1, 24$5181",
              "Numeric A between B & C, 24$11080"]; 

var date_methods = 
              ["Date A plus 1 Day, 92$24",
              "Date A plus N Days parm, 92$222",
              "Date A minus 1 Day, 92$25",
              "Date A < B, 24$4123",
              "Date A <= B, 24$4713",
              "Date A == B, 24$2749",
              "Date A >= B, 24$1557",
              "Date A > B, 24$1677",
              "Date A in Date Range, 24$22"];

var text_methods = 
              ["String A + B, 30$1544",
              "String A + ' ' + B, 30$1366",
              "String A substring, 66$11",
              "Formatted Date, 66$117",
              "String A == B(case-insensitive), 24$381",
              "String A == B(case-sensitive), 24$3061"];              

var boolean_methods = 
              ["Boolean A == B, 24$1964",
              "Boolean A != B, 94$2902",
              "Boolean A & B, 24$9376",
              "Boolean A & B & C, 94$6016",
              "Boolean A | B, 24$5396",
              "Boolean A | B | C, 24$12684",
              "Not Boolean A, 24$601"];

var favorites_methods = 
              ["This Instance, 87$2",
              "True, 86$13",
              "False, 86$166",
              "Empty Set, 87$118",
              "0, 86$26783",
              "1, 86$24",
              "Processing Person, 87$77408",
              "System Current Moment, 86$21",
              "Processing Effective Moment, 86$1511",
              "Processing Effective Date, 86$46747",
              "User Current Date, 86$1106",
              "Null Date Time, 86$13124"];

function loadXOMethods(method_list) {
  xoMethods = new Array();
  for (var i=0; i<method_list.length; i++) {
    xoMethods.push(new XOMethod (method_list[i].split(",")[0], method_list[i].split(", ")[1]));
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
    innerDiv.className = "btn btn-default";
    innerDiv.innerHTML = name;
    innerDiv.addEventListener("click", function() {
      copy(id);
    });
    iDiv.appendChild(innerDiv);
}

function loadTab(tab, list_of_methods){
 document.getElementById('methods').innerHTML = "";
 var xoMethods = loadXOMethods(list_of_methods);
  for (var i=0; i<xoMethods.length;i++) {
    var id = xoMethods[i].id;
    var name = xoMethods[i].name;
    var iDiv = document.createElement('div');

    handleCopyClick(iDiv, id, name);

    document.getElementById('methods').appendChild(iDiv);
  }
}

function loadPage() {
  var instance = document.getElementById('instance');
  var numeric = document.getElementById('numeric');
  var date = document.getElementById('date');
  var text = document.getElementById('text');
  var bool = document.getElementById('boolean');  
  var favorites = document.getElementById('favorites');
  
  instance.addEventListener("click", function(){loadTab(instance, instance_methods)});  
  numeric.addEventListener("click", function(){loadTab(numeric, numeric_methods)});
  date.addEventListener("click", function(){loadTab(numeric, date_methods)});
  text.addEventListener("click", function(){loadTab(text, text_methods)});
  bool.addEventListener("click", function(){loadTab(bool, boolean_methods)});
  favorites.addEventListener("click", function(){loadTab(favorites, favorites_methods)});
}

document.addEventListener('DOMContentLoaded', function () {
  loadPage();
  loadTab(document.getElementById('instance'), instance_methods);
});