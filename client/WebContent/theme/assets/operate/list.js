create_user_table();
hideCreate();

function hideCreate() {
	$("#createRole").hide();
	$("#createBlog").hide();
	$("#createTag").hide();
	$("#createResource").hide();
	$("#createUser").hide();
}

function create_user_table() {
	hideCreate();
	$("#currentObj").val(1);
	$('#cusTable').bootstrapTable('destroy');
	var url = "/user/list";
	$('#cusTable')
			.bootstrapTable(
					{
						method : 'POST',
						dataType : 'json',
						contentType : "application/x-www-form-urlencoded",
						cache : false,
						striped : true, // 是否显示行间隔色
						sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
						url : url,
						// height : $(window).height() - 110,
						// width : $(window).width(),
						// showColumns : true,
						// sortable: true, //是否启用排序
						// sortOrder: "asc", //排序方式
						pagination : true,
						queryParams : queryParams,
						minimumCountColumns : 2,
						pageNumber : 1, // 初始化加载第一页，默认第一页
						pageSize : 5, // 每页的记录行数（*）
						pageList : [ 5, 10, 20, 50 ], // 可供选择的每页的行数（*）
						uniqueId : "id", // 每一行的唯一标识，一般为主键列
						// showExport : true,
						// exportDataType : 'all',
						responseHandler : responseHandler,
						columns : [
								{
									field : 'name',
									title : '昵称',
									align : 'center',
									valign : 'middle',
									sortable : false
								},
								{
									field : 'email',
									title : '邮箱',
									align : 'center',
									valign : 'middle'
								},
								{
									field : 'phone',
									title : '电话',
									align : 'center',
									valign : 'middle',
									sortable : false
								},
								{
									field : 'address',
									title : '地址',
									align : 'center',
									valign : 'middle'
								},
								{
									field : 'usableStatus',
									title : '是否可用',
									align : 'center',
									valign : 'left'
								},
								{
									field : 'lastLoginDate',
									title : '最后登录时间',
									align : 'center',
									valign : 'middle'
								},
								{
									title : '操作',
									align : 'center',
									formatter : function(value, row, index) {
										return '<a href="javascript:;" class=\'btn btn-icon-only green\' onclick="update(\''
												+ row.id
												+ '\',1)"><i class=\"fa fa-edit\"></i></a>'
												+ '<a href="javascript:;" class=\'btn btn-sm purple\' onclick="del(\''
												+ row.id
												+ '\',1)"><i class=\"fa fa-times\"></i></a>'
									}
								} ]
					});
}

function create_role_table() {
	hideCreate();
	$("#currentObj").val(2);
	$('#cusTable').bootstrapTable('destroy');
	var url = "/role/list";
	$('#cusTable')
			.bootstrapTable(
					{
						method : 'POST',
						dataType : 'json',
						contentType : "application/x-www-form-urlencoded",
						cache : false,
						striped : true, // 是否显示行间隔色
						sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
						url : url,
						// height : $(window).height() - 110,
						// width : $(window).width(),
						// showColumns : true,
						// sortable: true, //是否启用排序
						// sortOrder: "asc", //排序方式
						pagination : true,
						queryParams : queryParams,
						minimumCountColumns : 2,
						pageNumber : 1, // 初始化加载第一页，默认第一页
						pageSize : 5, // 每页的记录行数（*）
						pageList : [ 5, 10, 20, 50 ], // 可供选择的每页的行数（*）
						uniqueId : "id", // 每一行的唯一标识，一般为主键列
						// showExport : true,
						// exportDataType : 'all',
						responseHandler : responseHandler,
						columns : [
								{
									field : 'name',
									title : '角色名称',
									align : 'center',
									valign : 'middle',
									sortable : false
								},
								{
									field : 'description',
									title : '描述',
									align : 'center',
									valign : 'middle'
								},
								{
									field : 'createDate',
									title : '创建时间',
									align : 'center',
									valign : 'middle'
								},
								{
									field : 'createDate',
									title : '创建者',
									align : 'center',
									valign : 'middle'
								},
								{
									field : 'usableStatus',
									title : '是否可用',
									align : 'center',
									valign : 'middle'
								},
								{
									title : '操作',
									align : 'center',
									formatter : function(value, row, index) {
										return '<a href="javascript:;" class=\'btn btn-icon-only green\' onclick="update(\''
												+ row.id
												+ '\',2)"><i class=\"fa fa-edit\"></i></a>'
												+ '<a href="javascript:;" class=\'btn btn-sm purple\' onclick="del(\''
												+ row.id
												+ '\',2)"><i class=\"fa fa-times\"></i></a>'
									}
								} ]
					});
}

function create_blog_table() {
	hideCreate();
	$("#currentObj").val(3);
	$('#cusTable').bootstrapTable('destroy');
	var url = "/blog/list";
	$('#cusTable')
			.bootstrapTable(
					{
						method : 'POST',
						dataType : 'json',
						contentType : "application/x-www-form-urlencoded",
						cache : false,
						striped : true, // 是否显示行间隔色
						sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
						url : url,
						// height : $(window).height() - 110,
						// width : $(window).width(),
						// showColumns : true,
						// sortable: true, //是否启用排序
						// sortOrder: "asc", //排序方式
						pagination : true,
						queryParams : queryParams,
						minimumCountColumns : 2,
						pageNumber : 1, // 初始化加载第一页，默认第一页
						pageSize : 5, // 每页的记录行数（*）
						pageList : [ 5, 10, 20, 50 ], // 可供选择的每页的行数（*）
						uniqueId : "id", // 每一行的唯一标识，一般为主键列
						// showExport : true,
						// exportDataType : 'all',
						responseHandler : responseHandler,
						columns : [
								{
									field : 'title',
									title : '标题',
									align : 'center',
									valign : 'middle',
									sortable : false
								},
								{
									field : 'htmlFilePath',
									title : 'html文件路径',
									align : 'center',
									valign : 'middle'
								},
								{
									field : 'mdFilePath',
									title : 'MarkDown文件路径',
									align : 'center',
									valign : 'middle',
									sortable : false
								},
								{
									field : 'readNum',
									title : '阅读次数',
									align : 'center',
									valign : 'middle'
								},
								{
									field : 'usableStatus',
									title : '是否可用',
									align : 'center',
									valign : 'middle'
								},
								{
									field : 'createDate',
									title : '创建时间',
									align : 'center',
									valign : 'middle'
								},
								{
									field : 'createUserId',
									title : '作者',
									align : 'center',
									valign : 'middle'
								},
								{
									title : '操作',
									align : 'center',
									formatter : function(value, row, index) {
										return '<a href="javascript:;" class=\'btn btn-icon-only green\' onclick="update(\''
												+ row.id
												+ '\',3)"><i class=\"fa fa-edit\"></i></a>'
												+ '<a href="javascript:;" class=\'btn btn-sm purple\' onclick="del(\''
												+ row.id
												+ '\',3)"><i class=\"fa fa-times\"></i></a>'
									}
								} ]
					});
}

function create_tag_table() {
	hideCreate();
	$("#currentObj").val(4);
	$('#cusTable').bootstrapTable('destroy');
	var url = "/tag/list";
	$('#cusTable')
			.bootstrapTable(
					{
						method : 'POST',
						dataType : 'json',
						contentType : "application/x-www-form-urlencoded",
						cache : false,
						striped : true, // 是否显示行间隔色
						sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
						url : url,
						// height : $(window).height() - 110,
						// width : $(window).width(),
						// showColumns : true,
						// sortable: true, //是否启用排序
						// sortOrder: "asc", //排序方式
						pagination : true,
						queryParams : queryParams,
						minimumCountColumns : 2,
						pageNumber : 1, // 初始化加载第一页，默认第一页
						pageSize : 5, // 每页的记录行数（*）
						pageList : [ 5, 10, 20, 50 ], // 可供选择的每页的行数（*）
						uniqueId : "id", // 每一行的唯一标识，一般为主键列
						// showExport : true,
						// exportDataType : 'all',
						responseHandler : responseHandler,
						columns : [
								{
									field : 'name',
									title : '名称',
									align : 'center',
									valign : 'middle',
									sortable : false
								},
								{
									field : 'usableStatus',
									title : '是否可用',
									align : 'center',
									valign : 'middle'
								},
								{
									field : 'createDate',
									title : '创建时间',
									align : 'center',
									valign : 'middle',
									sortable : false
								},
								{
									field : 'createUserId',
									title : '创建人',
									align : 'center',
									valign : 'middle'
								},
								{
									title : '操作',
									align : 'center',
									formatter : function(value, row, index) {
										return '<a href="javascript:;" class=\'btn btn-icon-only green\' onclick="update(\''
												+ row.id
												+ '\',4)"><i class=\"fa fa-edit\"></i></a>'
												+ '<a href="javascript:;" class=\'btn btn-sm purple\' onclick="del(\''
												+ row.id
												+ '\',4)"><i class=\"fa fa-times\"></i></a>'
									}
								} ]
					});
}

function create_resource_table() {
	hideCreate();
	$("#currentObj").val(5);
	$('#cusTable').bootstrapTable('destroy');
	var url = "/resource/list";
	$('#cusTable')
			.bootstrapTable(
					{
						method : 'POST',
						dataType : 'json',
						contentType : "application/x-www-form-urlencoded",
						cache : false,
						striped : true, // 是否显示行间隔色
						sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
						url : url,
						// height : $(window).height() - 110,
						// width : $(window).width(),
						// showColumns : true,
						// sortable: true, //是否启用排序
						// sortOrder: "asc", //排序方式
						pagination : true,
						queryParams : queryParams,
						minimumCountColumns : 2,
						pageNumber : 1, // 初始化加载第一页，默认第一页
						pageSize : 5, // 每页的记录行数（*）
						pageList : [ 5, 10, 20, 50 ], // 可供选择的每页的行数（*）
						uniqueId : "id", // 每一行的唯一标识，一般为主键列
						// showExport : true,
						// exportDataType : 'all',
						responseHandler : responseHandler,
						columns : [
								{
									field : 'url',
									title : '地址',
									align : 'center',
									valign : 'middle',
									sortable : false
								},
								{
									field : 'name',
									title : '名称',
									align : 'center',
									valign : 'middle'
								},
								{
									field : 'description',
									title : '描述',
									align : 'center',
									valign : 'middle',
									sortable : false
								},
								{
									field : 'createDate',
									title : '创建时间',
									align : 'center',
									valign : 'middle'
								},
								{
									title : '操作',
									align : 'center',
									formatter : function(value, row, index) {
										return '<a href="javascript:;" class=\'btn btn-icon-only green\' onclick="update(\''
												+ row.id
												+ '\',5"><i class=\"fa fa-edit\"></i></a>'
												+ '<a href="javascript:;" class=\'btn btn-sm purple\' onclick="del(\''
												+ row.id
												+ '\',5)"><i class=\"fa fa-times\"></i></a>'
									}
								} ]
					});
}

function queryParams(params) {
	var param = {
		pageNumber : this.pageNumber,
		pageSize : this.pageSize
	}
	return param;
}

function responseHandler(res) {
	if (res) {
		return {
			"rows" : res.list,
			"total" : res.page.recordCount
		};
	} else {
		return {
			"rows" : [],
			"total" : 0
		};
	}
}

function test() {
	alert("d")
}