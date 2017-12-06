create_user_table();
function _delete(id) {
	alert(id);
}
function _edit(id) {
	alert(id);
}
function create_user_table() {
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
									field : '',
									title : '序列',
									formatter : function(value, row, index) {
										return index + 1;
									}
								},
								{
									field : 'id',
									title : '用户ID',
									align : 'center',
									valign : 'middle',
									sortable : false
								},
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
									field : 'adress',
									title : '地址',
									align : 'center',
									valign : 'middle'
								},
								{
									field : 'usable_status',
									title : '是否可用',
									align : 'center',
									valign : 'left'
								},
								{
									field : 'last_login_date',
									title : '登录时间',
									align : 'center',
									valign : 'middle'
								},
								{
									title : '操作',
									align : 'center',
									formatter : function(value, row, index) {
										return '<a href="javascript:;" class=\'btn btn-icon-only green\' onclick="_edit(\''
												+ row.id
												+ '\')"><i class=\"fa fa-edit\"></i></a>'
												+ '<a href="javascript:;" class=\'btn btn-sm purple\' onclick="_delete(\''
												+ row.id
												+ '\')"><i class=\"fa fa-times\"></i></a>'
									}
								} ]
					});
	// $('#cusTable').bootstrapTable('hideColumn', 'id');
}


function create_role_table() {
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
									field : '',
									title : '序列1',
									formatter : function(value, row, index) {
										return index + 1;
									}
								},
								{
									field : 'id',
									title : '用户ID1',
									align : 'center',
									valign : 'middle',
									sortable : false
								},
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
									field : 'adress',
									title : '地址',
									align : 'center',
									valign : 'middle'
								},
								{
									field : 'usable_status',
									title : '是否可用',
									align : 'center',
									valign : 'left'
								},
								{
									field : 'last_login_date',
									title : '登录时间',
									align : 'center',
									valign : 'middle'
								},
								{
									title : '操作',
									align : 'center',
									formatter : function(value, row, index) {
										return '<a href="javascript:;" class=\'btn btn-icon-only green\' onclick="_edit(\''
												+ row.id
												+ '\')"><i class=\"fa fa-edit\"></i></a>'
												+ '<a href="javascript:;" class=\'btn btn-sm purple\' onclick="_delete(\''
												+ row.id
												+ '\')"><i class=\"fa fa-times\"></i></a>'
									}
								} ]
					});
	// $('#cusTable').bootstrapTable('hideColumn', 'id');
}

function create_blog_table() {
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
									field : '',
									title : '序列2',
									formatter : function(value, row, index) {
										return index + 1;
									}
								},
								{
									field : 'id',
									title : '用户ID2',
									align : 'center',
									valign : 'middle',
									sortable : false
								},
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
									field : 'adress',
									title : '地址',
									align : 'center',
									valign : 'middle'
								},
								{
									field : 'usable_status',
									title : '是否可用',
									align : 'center',
									valign : 'left'
								},
								{
									field : 'last_login_date',
									title : '登录时间',
									align : 'center',
									valign : 'middle'
								},
								{
									title : '操作',
									align : 'center',
									formatter : function(value, row, index) {
										return '<a href="javascript:;" class=\'btn btn-icon-only green\' onclick="_edit(\''
												+ row.id
												+ '\')"><i class=\"fa fa-edit\"></i></a>'
												+ '<a href="javascript:;" class=\'btn btn-sm purple\' onclick="_delete(\''
												+ row.id
												+ '\')"><i class=\"fa fa-times\"></i></a>'
									}
								} ]
					});
	// $('#cusTable').bootstrapTable('hideColumn', 'id');
}



function queryParams(params) {
	var param = {
		// orgCode : $("#orgCode").val(),
		// userName : $("#userName").val(),
		// limit : this.limit, // 页面大小
		// offset : this.offset, // 页码
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