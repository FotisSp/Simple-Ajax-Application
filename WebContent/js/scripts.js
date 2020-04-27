$(document).ready(function() {
	$("#show").click(function() {
		showAllUsers();
	});

	$("div.center").on('click','#users tr', function(event) {
	  var id = $(this).attr('id');
		showUser(id);
	});

	$("#register").click(function() {
		$('.header h1').html('Register User');
		$('.header p').html('Complete the required fields to register.');
		$('.center').html('');
		$('.center').append(
			'<form id="userForm">'+
				'<label for="name"><b>Name</b></label>'+
				'<input type="text" placeholder="Enter Name *" name="name" id="name" required>'+
				'<label for="surname"><b>Lastname</b></label>'+
				'<input type="text" placeholder="Enter Lastname *" name="surname" id="lastname" required>'+
				'<label for="gender"><b>Gender</b></label>'+
				'<select id="gender" name="gender" id="gender" required>'+
					'<option value="none" selected disabled hidden="">'+
						'Select Gender *'+
					'</option>'+
					'<option value="Male">Male</option>'+
					'<option value="Female">Female</option>'+
					'<option value="Other">Other</option>'+
				'</select>'+
				'<label for="birthdate"><b>Birthdate</b></label>'+
				'<input type="text" placeholder="Enter Birthdate *" name="birthdate" id="birthdate" readonly="readonly" required>'+
				'<input type="hidden" name="altDate" id="altDate">'+
				'<label for="homeAddress"><b>Home Address</b></label>'+
				'<input type="text" placeholder="Enter Home Address" name="homeAdd" id="homeAdd">'+
				'<label for="workAddress"><b>Work Address</b></label>'+
				'<input type="text" placeholder="Enter Work Address" name="workAdd" id="workAdd">'+
				'<p>Fields with asterisk (*) are required.</p>'+
				'<div class="buttonContainer">'+
					'<input type="submit" class="button buttonCol" name="registerBtn"'+
					  'value="Register">'+
					'<input type="button" class="button buttonCol" name="back"'+
						'value="Back" id="back" formnovalidate>'+
				'</div>'+
			'</form>'
		);
	});

	$("div.center").on('click','#birthdate',function() {
		$( "#birthdate" ).datepicker({
			changeYear: true,
			changeMonth: true,
			minDate: "1 Jan 1900",
			maxDate: "27 Apr 2020",
			dateFormat: "d M yy",
			altField: "#altDate",
			altFormat: "yy-mm-dd"
		}).focus();
	} );

	$("div.center").on('click', '#back', function() {
			window.location.href = '/SimpleAjaxApplication';
	});

	$("div.center").on('click', '#backToList', function() {
		showAllUsers();
	});

	$("div.center").on('click', '#deleteUser', function(event) {
		$.confirm({
			animation: 'zoom',
			theme: 'supervan',
			title: 'Delete',
			content: 'Do you want to delete user ?',
	    escapeKey: 'No',
			autoClose: 'No|8000',
			typeAnimated: false,				// fixes bug that messages
			animateFromElement: false,	// comes from the upper left corner
			buttons: {
				Yes: function () {
					deleteUser();
				},
				No: function () {}
			}
		});
	});

	$("div.center").on('click', '#editUser', function(event) {
		var id = $(this).attr('name');
		$.ajax({
		  url : "rest/user/" + id,
		  type : 'get',
		  dataType: 'json',
		  success : function(result) {
				$('.header h1').html('Edit User Info');
				$('.header p').html('');
				$('.center').html('');

				$('.center').append(
					'<form id="editForm">'+
						'<input type="hidden" name="id" id="id" value="' + result.id + '">'+
						'<label for="name"><b>Name</b></label>'+
						'<input type="text" placeholder="Enter Name *" name="name" id="name" value="' + result.name + '" required>'+
						'<label for="surname"><b>Lastname</b></label>'+
						'<input type="text" placeholder="Enter Lastname *" name="surname" id="lastname" value="' + result.surname + '" required>'+
						'<label for="gender"><b>Gender</b></label>'+
						'<select id="gender" name="gender" id="gender" value="' + result.gender + '" required>'+
							'<option value="Male">Male</option>'+
							'<option value="Female">Female</option>'+
							'<option value="Other">Other</option>'+
						'</select>'+
						'<label for="birthdate"><b>Birthdate</b></label>'+
						'<input type="text" placeholder="Enter Birthdate *" name="birthdate" id="birthdate" value="' + result.birthdate + '" readonly="readonly" required>'+
						'<input type="hidden" name="altDate" id="altDate">'+
						'<label for="homeAddress"><b>Home Address</b></label>'+
						'<input type="text" placeholder="Enter Home Address" name="homeAdd" id="homeAdd" value="' + result.homeAddress.address + '">'+
						'<label for="workAddress"><b>Work Address</b></label>'+
						'<input type="text" placeholder="Enter Work Address" name="workAdd" id="workAdd" value="' + result.workAddress.address + '">'+
						'<p>Fields with asterisk (*) are required.</p>'+
						'<div class="buttonContainer">'+
							'<input type="submit" class="button buttonCol" name="save"'+
								'id="saveBtn" value="Save">'+
							'<input type="button" class="button buttonCol" name="' + result.id + '"'+
								'value="Back" id="backToUserId" formnovalidate>'+
						'</div>'+
					'</form>'
				);
			},
		  failure : function(result) {
		  },
		  error: function( jqXhr, textStatus, errorThrown ){
		        console.log(jqXhr);
		  }
		});
	});

	$("div.center").on('click', '#backToUserId', function() {
		var id = $(this).attr('name');
		showUser(id);
	});

	$("div.center").on('submit', '#editForm', function() {
		var $form = $(this);
		id = $form.find("#id").val();
		name = $form.find("#name").val();
		lastname = $form.find("#lastname").val();
		gender = $form.find("#gender").val();
		birthdate = $form.find("#altDate").val();

		if (birthdate === '') {
			birthdate = $form.find("#altDate").val();
		}

		hAdd = $form.find("#homeAdd").val();
		wAdd = $form.find("#workAdd").val();
		var arr = {
				"id" : id,
				"name" : name,
				"surname" : lastname,
				"gender" : gender,
				"birthdate" : birthdate,
				"homeAddress" : {
					"address" : hAdd
				},
				"workAddress" : {
					"address" : wAdd
				}
		};
		arr = JSON.stringify(arr);
		request = $.ajax({
			type : 'post',
			url : "rest/user/edit",
			contentType : 'application/json',
			data : arr,
			success : function(result) {
				$('#addResultDiv').html('');
				if (result.includes('Failed')) {
					$('#addResultDiv').html(result).css("color","red");
				} else {
					$('#addResultDiv').html(result).css("color","green");
				}
			},
			failure : function(result) {
				$('#addResultDiv').html('');
				$('#addResultDiv').html(result).css("color","red");
			},
			error: function( jqXhr, textStatus, errorThrown ){
		        console.log(jqXhr);
		    },
		});
		// Callback handler that will be called on success
		request.done(function (response, textStatus, jqXHR){
			// Log a message to the console
		});

		// Callback handler that will be called on failure
		request.fail(function (jqXHR, textStatus, errorThrown){
			// Log the error to the console
		});
		return false;
	});

	$("div.center").on('submit', '#userForm', function() {
		var $form = $(this);
		name = $form.find("#name").val();
		lastname = $form.find("#lastname").val();
		gender = $form.find("#gender").val();
		birthdate = $form.find("#altDate").val();
		hAdd = $form.find("#homeAdd").val();
		wAdd = $form.find("#workAdd").val();
		var arr = {
				"name" : name,
				"surname" : lastname,
				"gender" : gender,
				"birthdate" : birthdate,
				"homeAddress" : {
					"address" : hAdd
				},
				"workAddress" : {
					"address" : wAdd
				}
		};
		arr = JSON.stringify(arr);
		request = $.ajax({
			type : 'post',
			url : "rest/user/register",
			contentType : 'application/json',
			data : arr,
			success : function(result) {
				$('#addResultDiv').html('');
				$('#addResultDiv').html(result).css("color","green");
			},
			failure : function(result) {
				$('#addResultDiv').html('');
				$('#addResultDiv').html(result).css("color","red");
			},
			error: function( jqXhr, textStatus, errorThrown ){
		        console.log(jqXhr);
		    },
		});
		// Callback handler that will be called on success
		request.done(function (response, textStatus, jqXHR){
			// Log a message to the console
		});

		// Callback handler that will be called on failure
		request.fail(function (jqXHR, textStatus, errorThrown){
			// Log the error to the console
		});
		return false;
	});

	$("#addResultDiv").on("keyup", '#searchField', function() {
		console.log('i am here ');
		var value = $(this).val().toLowerCase();
		$("#usersTable tr").filter(function() {
			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		});
	});

	function showUser(id) {
		$.ajax({
	    url : "rest/user/" + id,
	    type : 'get',
	    dataType: 'json',
	    success : function(result) {
	        $('.center').html('');
					$('.header h1').html('User Info');
					$('#addResultDiv').html('');

	        $('.center').append(
	        	'<table id="user" name="' + result.id + '">' +
	        		'<tr>' +
	        			'<td>Name</td>' +
	        			'<td>' + result.name + '</td>' +
	        		'</tr>' +
	        		'<tr>' +
	        			'<td>Lastname</td>' +
	        			'<td>' + result.surname + '</td>' +
	        		'</tr>' +
	        		'<tr>' +
	        			'<td>Gender</td>' +
	        			'<td>' + result.gender + '</td>' +
	        		'</tr>' +
	        		'<tr>' +
	        			'<td>Birthdate</td>' +
	        			'<td>' + result.birthdate + '</td>' +
	        		'</tr>' +
	        		'<tr>' +
	        			'<td>Home Address</td>' +
	        			'<td>' + result.homeAddress.address + '</td>' +
	        		'</tr>' +
	        		'<tr>' +
	        			'<td>Work Address</td>' +
	        			'<td>' + result.workAddress.address + '</td>' +
	        		'</tr>' +
	        	'</table>' +
						'<input type="button" class="button buttonCol" name="back" value="Back" id="backToList">' +
						'<input type="button" class="button delButton" name="deleteUser" value="Delete" id="deleteUser">' +
						'<input type="button" class="button buttonCol" name="' + result.id + '" value="Edit" id="editUser">'
	        );
	    },
	    failure : function(result) {
	      $('#addResultDiv').html('');
	      $('#addResultDiv').html(result).css("color","red");
	    },
	    error: function( jqXhr, textStatus, errorThrown ){
	          console.log(jqXhr);
	    }
	  });
	}

	function showAllUsers() {
		$.ajax({
			url : "rest/user",
			success : function(result) {
				$('.center').html('');

				$('.header h1').html('List of all Registered Users');
				$('.header p').html('');
				$('#addResultDiv').html('<input id="searchField" type="text" placeholder="Search..">');
				var _center =
					'<table id="users">' +
						'<thead>' +
							'<tr>' +
								'<th>Name</th>' +
								'<th>Lastname</th>' +
							'</tr>' +
						'</thead>' +
						'<tbody id="usersTable">';
				$.each(result, function(index, user) {
					_center +=
						'<tr id="' + user.id + '"><td>' + user.name + '</td>'
							+'<td>' + user.surname + '</td>'
						+'</tr>';
				});
				_center +=
						'</tbody>' +
					'</table>' +
					'<input type="button" class="button buttonCol" name="back"'+
					'value="Back" id="back">';
				$('.center').append(_center);
			}
		});
	}

	function deleteUser() {
		$.ajax({
			url : "rest/user/delete/" + $('#user').attr('name'),
			type : 'get',
			dataType : 'json',
			success : function(result) {
				showAllUsers();
			},
			failure : function(result) {
				// Log
			},
			error: function( jqXhr, textStatus, errorThrown ){
				console.log(jqXhr);
			}
		});
	}
});
