function showResponse(responseText, statusText) {
	if (statusText == 'success') {
		$("#result").html(responseText);
	}
}

function showRequest(formData, jqForm, options) {
	return $("#commentForm").valid();
}

var Login = function() {
	return {
		init : function() {
			$('.login-form')
					.validate(
							{
								errorElement : 'label', // default input error
								errorClass : 'help-inline', // default input
								focusInvalid : false, // do not focus the last
								rules : {
									username : {
										required : true
									},
									password : {
										required : true
									},
									remember : {
										required : false
									}
								},
								messages : {
									username : {
										required : "用户名不能为空."
									},
									password : {
										required : "密码不能为空."
									}
								},
								invalidHandler : function(event, validator) { // display
									$('.alert-error', $('.login-form')).show();
								},
								highlight : function(element) { // hightlight
									// error inputs
									$(element).closest('.control-group')
											.addClass('error'); // set error
								},
								success : function(label) {
									label.closest('.control-group')
											.removeClass('error');
									label.remove();
								},
								errorPlacement : function(error, element) {
									error.addClass('help-small no-left-padding').insertAfter(element.closest('.input-icon'));
								},
								submitHandler : function(form) {
									form.submit();
//									$(form).ajaxSubmit({
//										url : '/loginFilter',
//										type : 'POST',
//										success : function(res) {
//											alert(res);
//										}
//									});
//									return false;
								}
							});
			$('.login-form input').keypress(function(e) {
				if (e.which == 13) {
					if ($('.login-form').validate().form()) {
						form.submit();
					}
					return false;
				}
			});
			$('.forget-form')
					.validate(
							{
								errorElement : 'label', // default input error
								errorClass : 'help-inline', // default input
								focusInvalid : false, // do not focus the last
								ignore : "",
								rules : {
									email : {
										required : true,
										email : true
									}
								},
								messages : {
									email : {
										required : "Email is required."
									}
								},
								invalidHandler : function(event, validator) { // display
								},
								highlight : function(element) { // hightlight
									// error inputs
									$(element).closest('.control-group')
											.addClass('error'); // set error
								},
								success : function(label) {
									label.closest('.control-group')
											.removeClass('error');
									label.remove();
								},
								errorPlacement : function(error, element) {
									error
											.addClass(
													'help-small no-left-padding')
											.insertAfter(
													element
															.closest('.input-icon'));
								},
								submitHandler : function(form) {
									form.submit();
								}
							});
			jQuery('#forget-password').click(function() {
				jQuery('.login-form').hide();
				jQuery('.forget-form').show();
			});
			jQuery('#back-btn').click(function() {
				jQuery('.login-form').show();
				jQuery('.forget-form').hide();
			});
		}
	};
}();