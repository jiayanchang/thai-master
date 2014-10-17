function isInteger(val){
	return isNotEmpty(val) && /\d+/.test(val);
}

function isDigital(val) {
	return isNotEmpty(val) && /\d*\.?\d*/.test(val);
}

function isEnWords(val) {
	
}

function isNotEmpty(val) {
	return !isEmpty(val);
}

function isEmpty(val) {
	return jQuery.trim(val).length == 0;
}

