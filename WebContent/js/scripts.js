$(document).ready(
		function() {
			$("#show").click(
					function() {
						$.ajax({
							url : "rest/user",
							success : function(result) {
								$('.center').html('');
								$('.center').append(		// does not work
										'<table id="users">' +
										'<thead>' +
											'<tr>' +
												'<th>Name</th>' +
												'<th>Surname</th>' +
											'</tr>' +
										'</thead>' +
										'<tbody>');
								$.each(result, function(index, user) {
									$('.center').append(
										'<tr><td>' + user.name + '</td>'
										+'<td>' + user.surname + '</td>'
										+'</tr>');
								});
								$('.center').append(
										'</tbody>' +
										'</table>');
							}
						});
					});
			
			$("#userForm").submit(function() {
				name = $(this).find("#name").val();
				lastname = $(this).find("#lastname").val();
				gender = $(this).find("#gender").val();
				birthdate = $(this).find("#birthdate").val();
				var arr = {
					"name" : name,
					"lastname" : lastname,
					"gender" : gender,
					"birthdate" : birthdate
				};
				$.ajax({
					type : 'post',
					url : "rest/user/register",
					contentType : 'application/json',
					data : JSON.stringify(arr),
					success : function(result) {
						console.log('success');
						$('#addResultDiv').html('');
						$('#addResultDiv').html(result);
					},
					failure : function(result) {
						console.log('fail');
						$('#addResultDiv').html('');
						$('#addResultDiv').html(result);
					}
				});
				return false;
			});
		});