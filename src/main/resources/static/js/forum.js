var password = document.getElementById("password")
  , confirm_password = document.getElementById("confirm_password");

var email = document.getElementById("email");


function validatePassword(){
  if(password.value != confirm_password.value) {
    confirm_password.setCustomValidity("Passwords Don't Match");
  }    else {
    	confirm_password.setCustomValidity('');   	
    }
  }


password.onchange = validatePassword;
confirm_password.onkeyup = validatePassword;


function validateEmail() {
    var x = document.forms["loginForm"]["email"].value;
    var atpos = x.indexOf("@");
    var dotpos = x.lastIndexOf(".");
    if (atpos<1 || dotpos<atpos+2 || dotpos+2>=x.length) {
        window.alert("Not a valid e-mail address");
        return false;
    }
}

function ValidateSize(file) {
    var FileSize = file.files[0].size / 1024 / 1024; // in MB
    if (FileSize > 1) {
        alert('File size exceeds 1 MB');
        $(file).val('');
    } else {

    }
}

function showUpdateComment(elementId) {
	
    var x = document.getElementById(elementId);
    console.log(x.style.display);
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}
function showUpdateTag(elementId) {
	
    var x = document.getElementById(elementId);
    console.log(x.style.display);
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}

function showUpdateUser(elementId) {
	
    var x = document.getElementById(elementId);
    console.log(x.style.display);
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}

function showMailForm() {
	
    var x = document.getElementById('mailForm');
    console.log(x.style.display);
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}


