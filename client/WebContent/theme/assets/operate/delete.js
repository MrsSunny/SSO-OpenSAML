function del(id,type) {
	var objTag = type
	if (objTag == 1) {
		deleteUser(id);
	} else if (objTag == 2) {
		deleteRole(id);
	} else if (objTag == 3) {
		deleteBlog(id);
	} else if (objTag == 4) {
		deleteTag(id);
	} else if (objTag == 5) {
		deleteResource(id);
	}
}

function deleteUser(id) {
	$.ajax({
		type : "POST",
		url : "/user/delete",
		data : "id=" + id,
		success : function(msg) {
			var code = msg.code
			if (code == 1) {
				create_user_table();
			} else {
				 alert("删除失败")
			}
		}
	});
}

function deleteRole(id) {
	$.ajax({
		type : "POST",
		url : "/role/delete",
		data : "id=" + id,
		success : function(msg) {
			var code = msg.code
			if (code == 1) {
				create_role_table();
			} else {
				alert("删除失败")
			}
		}
	});
}

function deleteBlog(id) {
	var obj = $('#createBlogForm').serialize();
	$.ajax({
		type : "POST",
		url : "/blog/delete",
		data : "id=" + id,
		success : function(msg) {
			var code = msg.code
			if (code == 1) {
				create_blog_table();
			} else {
				alert("删除失败")
			}
		}
	});
}

function deleteResource(id) {
	$.ajax({
		type : "POST",
		url : "/resource/delete",
		data : "id=" + id,
		success : function(msg) {
			var code = msg.code
			if (code == 1) {
				create_resource_table();
			} else {
				alert("删除失败")
			}
		}
	});
}

function deleteTag(id) {
	$.ajax({
		type : "POST",
		url : "/tag/delete",
		data : "id=" + id,
		success : function(msg) {
			var code = msg.code
			if (code == 1) {
				create_tag_table();
			} else {
				alert("删除失败")
			}
		}
	});
}