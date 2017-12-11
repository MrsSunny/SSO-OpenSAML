//1.User
//2.Role
//3.Blog
//4.Tag
//5.Resource

function create() {
	var a = $("#currentObj ").val()
	disableDiv(a)
}

function disableDiv(objTag) {
	$('#cusTable').bootstrapTable('destroy');
	if (objTag == 1) {
		$("#createRole").hide();
		$("#createBlog").hide();
		$("#createTag").hide();
		$("#createResource").hide();
		$("#createUser").show();
	} else if (objTag == 2) {
		$("#createBlog").hide();
		$("#createTag").hide();
		$("#createResource").hide();
		$("#createUser").hide();
		$("#createRole").show();
	} else if (objTag == 3) {
		$("#createRole").hide();
		$("#createTag").hide();
		$("#createResource").hide();
		$("#createUser").hide();
		$("#createBlog").show();
	} else if (objTag == 4) {
		$("#createRole").hide();
		$("#createBlog").hide();
		$("#createResource").hide();
		$("#createUser").hide();
		$("#createTag").show();
	} else if (objTag == 5) {
		$("#createRole").hide();
		$("#createBlog").hide();
		$("#createTag").hide();
		$("#createUser").hide();
		$("#createResource").show();
	}
}

function createPost() {
	var objTag = $("#currentObj ").val()
	if (objTag == 1) {
		createPostUser();
	} else if (objTag == 2) {
		createPostRole();
	} else if (objTag == 3) {
		createPostBlog();
	} else if (objTag == 4) {
		createPostTag();
	} else if (objTag == 5) {
		createPostResource();
	}
}

function createPostUser() {
	var obj = $('#createUserForm').serialize();
	$.ajax({
		type : "POST",
		url : "/user/add",
		data : obj,
		success : function(msg) {
			var code = msg.code
			if (code == 1) {
				$(':input', '#createUserForm').not(
						':button, :submit, :reset, :hidden').val('')
						.removeAttr('checked').removeAttr('selected');
				create_user_table();
			} else {
				// alert("添加失败")
			}
		}
	});
}

function createPostTag() {
	var obj = $('#createTagForm').serialize();
	$.ajax({
		type : "POST",
		url : "/tag/add",
		data : obj,
		success : function(msg) {
			var code = msg.code
			if (code == 1) {
				$(':input', '#createUserForm').not(
						':button, :submit, :reset, :hidden').val('')
						.removeAttr('checked').removeAttr('selected');
				create_tag_table();
			} else {
				// alert("添加失败")
			}
		}
	});
}

function createPostBlog() {
	var obj = $('#createUserForm').serialize();
	$.ajax({
		type : "POST",
		url : "/user/add",
		data : obj,
		success : function(msg) {
			var code = msg.code
			if (code == 1) {
				$(':input','#createUserForm') 
				.not(':button, :submit, :reset, :hidden') 
				.val('') 
				.removeAttr('checked')
				.removeAttr('selected');
				create_blog_table();
			} else {
//				alert("添加失败")
			}
		}
	});
}

function createPostRole() {
	var obj = $('#createRoleForm').serialize();
	$.ajax({
		type : "POST",
		url : "/role/add",
		data : obj,
		success : function(msg) {
			var code = msg.code
			if (code == 1) {
				$(':input', '#createUserForm').not(
						':button, :submit, :reset, :hidden').val('')
						.removeAttr('checked').removeAttr('selected');
				create_role_table();
			} else {
				// alert("添加失败")
			}
		}
	});
}

function createPostResource() {
	var obj = $('#createResourceForm').serialize();
	$.ajax({
		type : "POST",
		url : "/resource/add",
		data : obj,
		success : function(msg) {
			var code = msg.code
			if (code == 1) {
				$(':input','#createUserForm') 
				.not(':button, :submit, :reset, :hidden') 
				.val('') 
				.removeAttr('checked')
				.removeAttr('selected');
				create_resource_table();
			} else {
//				alert("添加失败")
			}
		}
	});
}
