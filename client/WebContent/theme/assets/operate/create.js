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
	$("#htmlFilePath").fileinput({
		language : 'zh', // 设置语言
		uploadUrl : "/blog/add", // 上传的地址
		allowedFileExtensions : [ 'html', 'gif', 'png' ],// 接收的文件后缀
		// uploadExtraData:{"id": 1, "fileName":'123.mp3'},
		uploadAsync : true, // 默认异步上传
		showUpload : true, // 是否显示上传按钮
		showRemove : true, // 显示移除按钮
		showPreview : true, // 是否显示预览
		showCaption : false,// 是否显示标题
		browseClass : "btn btn-primary", // 按钮样式
		dropZoneEnabled : true,// 是否显示拖拽区域
		maxFileCount : 10, // 表示允许同时上传的最大文件个数
		enctype : 'multipart/form-data',
		validateInitialCount : true,
		previewFileIcon : "<iclass='glyphicon glyphicon-king'></i>",
		msgFilesTooMany : "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
	}).on("fileuploaded", function(event, data, previewId, index) {
		alert("ddddddd")
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
				$(':input', '#createUserForm').not(
						':button, :submit, :reset, :hidden').val('')
						.removeAttr('checked').removeAttr('selected');
				create_resource_table();
			} else {
				// alert("添加失败")
			}
		}
	});
}
