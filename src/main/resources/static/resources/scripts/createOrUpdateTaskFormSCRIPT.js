const fieldDisciplines = document.getElementById("field_disciplines");
const disciplineSelected = fieldDisciplines.options[fieldDisciplines.selectedIndex].value;

const fieldClasses = document.getElementById("field_classes");
const fieldClassesOptions = fieldClasses.getElementsByTagName("option");

for (var i = 0; i < fieldClasses.length; i++) {
	fieldClasses[i].style.display = 'none';
}

function loadClasses(xSelected) {
	for (var i = 0; i < fieldClasses.length; i++) {
		if (xSelected === fieldClasses[i].title) {
			fieldClasses[i].style.display = 'block';
		} else {
			fieldClasses[i].style.display = 'none';
		}
	}
}
var today = new Date();
var dd = today.getDate();
var MM = today.getMonth() + 1;
var yyyy = today.getFullYear();
var hh = today.getHours();
var mm = today.getMinutes();
if (dd < 10) {
	dd = '0' + dd
}
if (MM < 10) {
	MM = '0' + MM
}

today = yyyy + '-' + MM + '-' + dd + 'T' + hh + ':' + mm;
document.getElementById("datefield").setAttribute("min", today);