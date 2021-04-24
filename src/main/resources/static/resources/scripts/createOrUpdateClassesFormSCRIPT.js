const tabs = document.getElementsByClassName('tab');
const content = document.getElementsByClassName('content');
const toRight = document.getElementById('to-right');
const toLeft = document.getElementById('to-left');
const students = document.getElementById('students');
const selectedStudentsContainer = document.getElementById('selectedStudents');
const selectedStudents = [];

let listOfStudents = [];

//Moving class selected to the new selected tab
function tabStyle(tab) {

	const selected = document.getElementsByClassName('selected');
	selected[0].classList.toggle('selected');
	tab.classList.toggle('selected');
}

//Moving the 'display: flex' to content with the same index as the selected tab
function showContent(index) {
	for (let i = 0; i < content.length; i++) {
		content[i].style.display = 'none';
	}
	content[index].style.display = 'flex';
}

content[0].style.display = 'flex';

//attaches an event handler to all tabs
for (let i = 0; i < tabs.length; i++) {
	tabs[i].addEventListener('click', () => {
		tabStyle(tabs[i]);
		showContent(i);
	});
}

//Move student from right to left
function returnStudent(event) {
	event.preventDefault()
	const checkedStudents = document.querySelectorAll('.studentList.selected');
	for (let student of checkedStudents) {
		let json = valueToJson(student.textContent);
		student.remove();
		listOfStudents.push(json);
		removeStudent(selectedStudents, json);
		students.innerHTML = reduceLeftStudents(sortOn(listOfStudents, "name"));
	}

}

toLeft.addEventListener('click', () => returnStudent(event));

//Move student from left to right
function moveStudent(event) {
	event.preventDefault()
	const checkboxes = document.querySelectorAll(
		'input[name="studentCheck"]:checked'
	);
	for (let checkbox of checkboxes) {
		let parentRM = checkbox.parentNode.parentNode;
		parentRM.remove();
		let json = valueToJson(checkbox.value);
		selectedStudents.push(json);
		selectedStudentsContainer.innerHTML = reduceRightStudents(sortOn(selectedStudents, "name"));
		removeStudent(listOfStudents, json);
	}

}

toRight.addEventListener('click', () => moveStudent(event));

// Order array alphabetically
function sortOn(arr, prop) {
	arr.sort(function(a, b) {
		if (a[prop] < b[prop]) {
			return 1;
		} else if (a[prop] > b[prop]) {
			return -1;
		} else {
			return 0;
		}
	});
	return arr;
}

function removeStudent(arr, json) {
	for (let student of arr) {
		let index = arr.indexOf(student);
		if (json.name === student.name) {
			arr.splice(index, 1);
		}
	}
}


function reduceRightStudents(arr) {
	let aux = arr.reduce(
		(html, student) =>
			html +
			`<div class="studentList" onClick="addClass(this)">${student.name} </div>`,
		''
	);
	return aux;
}

//Return the html of an array
function reduceLeftStudents(arr) {
	let aux = arr.reduce(
		(html, student) =>
			html +
			`<div th:each="student :  ${students}">
			<div class="containeer-roww">
			<div class="coll-10">
				<input	
					class="checkboxx"
					type="checkbox"
					name="studentCheck"
					id="studentCheck"
					value="${student.name}"
				/>
			</div>
			<div class="coll-90" value="${student.name}">${student.name}</div>
			</div>
		</div>`,
		''
	);
	return aux;
}

function valueToJson(value) {
	let str = value.trim();
	let preJson = `{"name": "${str}"}`;
	return JSON.parse(preJson);
}

function addClass(student) {
	student.classList.toggle('selected');
}

window.onload = () => {
	let checkboxes = document.getElementsByClassName('checkboxx');
	for (let i = 0; i < checkboxes.length; i++) {
		listOfStudents.push(valueToJson(checkboxes[i].value))
	}
}

function Filter() {
	// Declare variables
	let input, filter, table, tr, td, i, txtValue;
	input = document.getElementById("search");
	filter = input.value.toUpperCase();
	let checkboxes = document.getElementsByClassName('checkboxx');
	let nameStudents = document.getElementsByClassName('containeer-roww');



	for (i = 0; i < checkboxes.length; i++) {
		td = checkboxes[i].value;
		if (td.toUpperCase().indexOf(filter) > -1) {
			checkboxes[i].style.display = "";
			nameStudents[i].style.display = "";

		} else {
			checkboxes[i].style.display = "none";
			nameStudents[i].style.display = "none";
		}
	}
}

//form 
const myForm = document.getElementById("formClasses")

document.getElementById("save").addEventListener("click", () => loadHidden(event))

function loadHidden(event) {
	event.preventDefault();
	selectedStudents.forEach((student) => {
		let name = student['name'];
		let option = document.createElement("option");
		option.value = name;
		option.setAttribute("selected", "selected");
		document.getElementById("selectedStudentsList").appendChild(option)
	});


	document.getElementById("formClasses").submit()
}



//media
const mainContainer = document.getElementById('main-containeer');
const container = document.getElementsByClassName('containeer');
const containerSelected = document.getElementsByClassName('containeer-selected');
const selectStudentsButton = document.getElementById('selectStudentsButton');
const buttons = document.querySelectorAll('.buttons button');

function myFunction(windowMedia) {
	if (windowMedia.matches) {
		toFirstPage();
		buttons[0].addEventListener('click', toSecondPage);
		buttons[1].addEventListener('click', toFirstPage);

	} else {
		normalSize();
		buttons[0].removeEventListener('click', toSecondPage);
		buttons[1].removeEventListener('click', toFirstPage);
	}
}

function toFirstPage() {
	mainContainer.style.flexDirection = 'column';
	container[0].style.display = 'flex';
	buttons[0].style.display = 'block';

	containerSelected[0].style.display = 'none';
	buttons[1].style.display = 'none';


}

function toSecondPage() {
	mainContainer.style.flexDirection = 'column-reverse';
	container[0].style.display = 'none';
	buttons[0].style.display = 'none';

	containerSelected[0].style.display = 'block';
	buttons[1].style.display = 'block';

}

function normalSize() {
	mainContainer.style.flexDirection = 'row';
	container[0].style.display = 'flex';
	buttons[0].style.display = 'block';

	containerSelected[0].style.display = 'flex';
	buttons[1].style.display = 'block';

}

const windowMedia = window.matchMedia('(max-width: 480px)');
myFunction(windowMedia);
windowMedia.addEventListener('change', myFunction);