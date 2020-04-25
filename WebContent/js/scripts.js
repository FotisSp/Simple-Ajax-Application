$(document).ready(function() {
	$("#show").click(function() {
		$.ajax({
			url : "rest/user",
			success : function(result) {
				$('.center').html('');
				
				$('.header h1').html('List of all Registered Users');
				$('.header p').html('');
				var _center = 
					'<table id="users">' +
						'<thead>' +
							'<tr>' +
								'<th>Name</th>' +
								'<th>Surname</th>' +
							'</tr>' +
						'</thead>' +
						'<tbody>';
				$.each(result, function(index, user) {
					_center +=
						'<tr><td>' + user.name + '</td>'
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
	});

	$("#register").click(function() {
		$('.header h1').html('Register User');
		$('.header p').html('Complete the required data to register.');
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
				'<input type="date" placeholder="Enter Birthdate *" name="birthdate" id="birthdate" min="1900-01-01" max="2020-04-17" required>'+
				'<label for="homeAddress"><b>Home Address</b></label>'+
				'<input type="text" placeholder="Enter Home Address" name="homeAdd" id="homeAdd">'+
				'<label for="workAddress"><b>Work Address</b></label>'+
				'<input type="text" placeholder="Enter Work Address" name="workAdd" id="workAdd">'+
				'<p>Fields with asterisk (*) are required.</p>'+
				'<div class="buttonContainer">'+
					'<input type="submit" class="button buttonCol" name="register"'+
						'value="Register">'+
					'<input type="button" class="button buttonCol" name="back"'+
						'value="Back" id="back" formnovalidate>'+
				'</div>'+
			'</form>'
		);
	});

	$("div.center").on('click','#back',function() {
			location.reload();
	});

	$("div.center").on('submit',function() {
		var $form = $(this);
		name = $form.find("#name").val();
		lastname = $form.find("#lastname").val();
		gender = $form.find("#gender").val();
		birthdate = $form.find("#birthdate").val();
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
			success : function(result) {		// TODO delete if not necessery
//				console.log('success');
				$('#addResultDiv').html('');
				$('#addResultDiv').html(result);
			},
			failure : function(result) {
//				console.log('fail');
				$('#addResultDiv').html('');
				$('#addResultDiv').html(result);
			},
			error: function( jqXhr, textStatus, errorThrown ){
		        console.log(jqXhr);
		    },
//			complete: function(result) {
//				console.log();
//			}
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
});
