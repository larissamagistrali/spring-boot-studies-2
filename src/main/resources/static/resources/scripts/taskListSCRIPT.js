var x = document.getElementById("tableTaskList").rows.length;
if (x > 1) {
	window.onload = show();
}
function show() {
	document.getElementById("div").style.display = "block";
}