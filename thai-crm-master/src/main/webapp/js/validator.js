function isInteger(val){
	return isNotEmpty(val) && /^[0-9]+$/.test(val);
}

function isDigital(val) {
	return isNotEmpty(val) && /^[0-9]+(.[0-9]{1,3})?$/.test(val);
}

function isEnWords(val) {
	
}

function isValidEmail(val) {
	return isNotEmpty(val) && /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/.test(val);
}

function isNotEmpty(val) {
	return !isEmpty(val);
}

function isEmpty(val) {
	return jQuery.trim(val).length == 0;
}


function validate(callback) {
	$(".has-error").removeClass("has-error");
	var pass = true;
	var alerted = false;
	$("form input").each(function() {
		var check = $(this).attr("check");
		var val = $(this).val();
		if(check) {
			if(check.indexOf('amount') >= 0){
				if(!isDigital(val)) {
					pass = false;
					$(this).parent().addClass("form-group has-error");
					if(!alerted) {
						$(this).focus();
						alert($(this).attr("placeholder"));
						alerted = true;
					}
				}
			}
			if (check.indexOf('notEmpty') >= 0) {
				if(isEmpty(val)) {
					pass = false;
					$(this).parent().addClass("form-group has-error");
					if(!alerted) {
						$(this).focus();
						alert($(this).attr("placeholder"));
						alerted = true;
					}
				}
			} 
			if (check.indexOf('integer') >= 0) {
				if(!isInteger(val)) {
					pass = false;
					$(this).parent().addClass("form-group has-error");
					if(!alerted) {
						$(this).focus();
						alert($(this).attr("placeholder"));
						alerted = true;
					}
				}
			} 
			if (check.indexOf('email') >= 0) {
				if(!isValidEmail(val)) {
					pass = false;
					$(this).parent().addClass("form-group has-error");
					if(!alerted) {
						$(this).focus();
						alert($(this).attr("placeholder"));
						alerted = true;
					}
				}
			} 
			if (check.indexOf('date') >= 0) {

			}
		}
	});
	
	if(pass) {
		callback();
	}
}

