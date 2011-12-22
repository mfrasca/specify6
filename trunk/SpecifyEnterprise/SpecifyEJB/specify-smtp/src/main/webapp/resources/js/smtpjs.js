function smtpcollection(value) { 
    if(value == "smtpinv") {
        document.getElementById('okbuttondiv').style.display = 'none';	
        document.getElementById("smtpinterfacediv").style.display='inline';
    } else {
        document.getElementById('okbuttondiv').style.display = 'inline';
        document.getElementById("smtpinterfacediv").style.display='none';
    }  
}  

function editsample(value) {  
    document.getElementById(value + 'disply').style.display = 'none';
    document.getElementById(value + 'edit').style.display = 'inline';
}
  
function submitform(bntid) {  
    document.getElementById(bntid).click(); 
}