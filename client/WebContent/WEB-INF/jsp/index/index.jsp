<%@page import="org.sms.SysConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<!-- 
Template Name: Metronic - Responsive Admin Dashboard Template build with Twitter Bootstrap 3.3.7
Version: 4.7
Author: KeenThemes
Website: http://www.keenthemes.com/
Contact: support@keenthemes.com
Follow: www.twitter.com/keenthemes
Dribbble: www.dribbble.com/keenthemes
Like: www.facebook.com/keenthemes
Purchase: http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes
Renew Support: http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes
License: You must have a valid license purchased only from themeforest(the above link) in order to legally use the theme for your project.
-->
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->

<head>
<meta charset="utf-8" />
<title>后台管理</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport" />
<meta
	content="#1 selling multi-purpose bootstrap admin theme sold in themeforest marketplace packed with angularjs, material design, rtl support with over thausands of templates and ui elements and plugins to power any type of web applications including saas and admin dashboards. Preview page of Theme #4 for statistics, charts, recent events and reports"
	name="description" />
<meta content="" name="author" />
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link
	href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all"
	rel="stylesheet" type="text/css" />
<link
	href="../theme/assets/global/plugins/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="../theme/assets/global/plugins/simple-line-icons/simple-line-icons.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="../theme/assets/global/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="../theme/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css"
	rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<link
	href="../theme/assets/global/plugins/bootstrap-daterangepicker/daterangepicker.min.css"
	rel="stylesheet" type="text/css" />
<link href="../theme/assets/global/plugins/morris/morris.css"
	rel="stylesheet" type="text/css" />
<link
	href="../theme/assets/global/plugins/fullcalendar/fullcalendar.min.css"
	rel="stylesheet" type="text/css" />
<link href="../theme/assets/global/plugins/jqvmap/jqvmap/jqvmap.css"
	rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN THEME GLOBAL STYLES -->
<link href="../theme/assets/global/css/components.min.css"
	rel="stylesheet" id="style_components" type="text/css" />
<link href="../theme/assets/global/css/plugins.min.css" rel="stylesheet"
	type="text/css" />
<!-- END THEME GLOBAL STYLES -->
<!-- BEGIN THEME LAYOUT STYLES -->
<link href="../theme/assets/layouts/layout4/css/layout.min.css"
	rel="stylesheet" type="text/css" />
<link href="../theme/assets/layouts/layout4/css/themes/default.min.css"
	rel="stylesheet" type="text/css" id="style_color" />
<link href="../theme/assets/layouts/layout4/css/custom.min.css"
	rel="stylesheet" type="text/css" />

<link
	href="../theme/assets/global/plugins/bootstrap-table/bootstrap-table.min.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="../theme/assets/global/plugins/bootstrap-table/bootstrap-table.css">
<link
	href="../theme/assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css"
	rel="stylesheet" type="text/css" />


<link href="../theme/assets/global/plugins/dropzone/dropzone.min.css"
	rel="stylesheet" type="text/css" />


<link
	href="../theme/assets/global/plugins/jquery-file-upload/blueimp-gallery/blueimp-gallery.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="../theme/assets/global/plugins/jquery-file-upload/css/jquery.fileupload.css"
	rel="stylesheet" type="text/css" />
<link
	href="../theme/assets/global/plugins/jquery-file-upload/css/jquery.fileupload-ui.css"
	rel="stylesheet" type="text/css" />

<!-- END THEME LAYOUT STYLES -->
<link rel="shortcut icon" href="favicon.ico" />
</head>
<!-- END HEAD -->

<body
	class="page-container-bg-solid page-header-fixed page-sidebar-closed-hide-logo">
	<!-- BEGIN HEADER -->
	<div class="page-header navbar navbar-fixed-top">
		<!-- BEGIN HEADER INNER -->
		<div class="page-header-inner ">
			<!-- BEGIN LOGO -->

			<!-- END LOGO -->
			<!-- BEGIN RESPONSIVE MENU TOGGLER -->
			<a href="javascript:;" class="menu-toggler responsive-toggler"
				data-toggle="collapse" data-target=".navbar-collapse"></a>

			<!-- END PAGE ACTIONS -->
			<!-- BEGIN PAGE TOP -->
			<div class="page-top">
				<!-- BEGIN HEADER SEARCH BOX -->
				<!-- DOC: Apply "search-form-expanded" right after the "search-form" class to have half expanded search box -->
				<form class="search-form" action="page_general_search_2.html"
					method="GET">
					<div class="input-group">
						<input type="text" class="form-control input-sm"
							placeholder="Search..." name="query"> <span
							class="input-group-btn"> <a href="javascript:;"
							class="btn submit"> <i class="icon-magnifier"></i>
						</a>
						</span>
					</div>
				</form>
				<!-- END HEADER SEARCH BOX -->
				<!-- BEGIN TOP NAVIGATION MENU -->
				<div class="top-menu">
					<ul class="nav navbar-nav pull-right">
						<li class="separator hide"></li>
						<li class="dropdown dropdown-user dropdown-dark"><a
							href="javascript:;" class="dropdown-toggle"
							data-toggle="dropdown" data-hover="dropdown"
							data-close-others="true"> <span
								class="username username-hide-on-mobile"> Nick </span> <!-- DOC: Do not remove below empty space(&nbsp;) as its purposely used -->
								<img alt="" class="img-circle"
								src="../theme/assets/layouts/layout4/img/avatar9.jpg" />
						</a>
							<ul class="dropdown-menu dropdown-menu-default">
								<li><a href="page_user_profile_1.html"> <i
										class="icon-user"></i> My Profile
								</a></li>
								<li><a href="app_calendar.html"> <i
										class="icon-calendar"></i> My Calendar
								</a></li>
								<li><a href="app_inbox.html"> <i
										class="icon-envelope-open"></i> My Inbox <span
										class="badge badge-danger"> 3 </span>
								</a></li>
								<li><a href="app_todo_2.html"> <i class="icon-rocket"></i>
										My Tasks <span class="badge badge-success"> 7 </span>
								</a></li>
								<li class="divider"></li>
								<li><a href="page_user_lock_1.html"> <i
										class="icon-lock"></i> Lock Screen
								</a></li>
								<li><a href="page_user_login_1.html"> <i
										class="icon-key"></i> Log Out
								</a></li>
							</ul></li>
						<!-- END USER LOGIN DROPDOWN -->
						<!-- BEGIN QUICK SIDEBAR TOGGLER -->
						<!-- <li class="dropdown dropdown-extended quick-sidebar-toggler">
							<span class="sr-only">Toggle Quick Sidebar</span> <i
							class="icon-logout"></i>
						</li> -->
						<!-- END QUICK SIDEBAR TOGGLER -->
					</ul>
				</div>
				<!-- END TOP NAVIGATION MENU -->
			</div>
			<!-- END PAGE TOP -->
		</div>
		<!-- END HEADER INNER -->
	</div>
	<!-- END HEADER -->
	<!-- BEGIN HEADER & CONTENT DIVIDER -->
	<div class="clearfix"></div>
	<!-- END HEADER & CONTENT DIVIDER -->
	<!-- BEGIN CONTAINER -->






	<div class="page-container">
		<!-- BEGIN SIDEBAR -->
		<div class="page-sidebar-wrapper">
			<!-- BEGIN SIDEBAR -->
			<!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
			<!-- DOC: Change data-auto-speed="200" to adjust the sub menu slide up/down speed -->
			<div class="page-sidebar navbar-collapse collapse">
				<!-- BEGIN SIDEBAR MENU -->
				<!-- DOC: Apply "page-sidebar-menu-light" class right after "page-sidebar-menu" to enable light sidebar menu style(without borders) -->
				<!-- DOC: Apply "page-sidebar-menu-hover-submenu" class right after "page-sidebar-menu" to enable hoverable(hover vs accordion) sub menu mode -->
				<!-- DOC: Apply "page-sidebar-menu-closed" class right after "page-sidebar-menu" to collapse("page-sidebar-closed" class must be applied to the body element) the sidebar sub menu mode -->
				<!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
				<!-- DOC: Set data-keep-expand="true" to keep the submenues expanded -->
				<!-- DOC: Set data-auto-speed="200" to adjust the sub menu slide up/down speed -->
				<ul class="page-sidebar-menu   " data-keep-expanded="false"
					data-auto-scroll="true" data-slide-speed="200">
					<li class="nav-item start active open"><a href="javascript:;"
						class="nav-link nav-toggle"> <i class="icon-home"></i> <span
							class="title">首页</span>
					</a></li>
					<li class="heading">
						<h3 class="uppercase">模块管理</h3>
					</li>
					<li class="nav-item"><a href="javascript:create_user_table();"
						class="nav-link nav-toggle"> <i class="icon-user"></i> <span
							class="title">用户管理</span>
					</a></li>
					<li class="nav-item"><a href="javascript:create_role_table();"
						class="nav-link nav-toggle"> <i class="fa fa-users"></i> <span
							class="title">角色管理</span>
					</a></li>
					<li class="nav-item"><a href="javascript:create_blog_table();"
						class="nav-link nav-toggle"> <i class="fa fa-file-o"></i> <span
							class="title">博客管理</span>
					</a></li>
					<li class="nav-item"><a href="javascript:create_tag_table();"
						class="nav-link nav-toggle"> <i class="fa fa-tags"></i> <span
							class="title">标签管理</span>
					</a></li>
					<li class="nav-item"><a
						href="javascript:create_resource_table();"
						class="nav-link nav-toggle"> <i class="fa fa-newspaper-o"></i>
							<span class="title">资源管理</span>
					</a></li>
					<li class="heading">
						<h3 class="uppercase">设置</h3>
					</li>
					<li class="nav-item  "><a href="javascript:;"
						class="nav-link nav-toggle"> <i class="fa fa-file-o"></i> <span
							class="title">登录日志</span>
					</a></li>
					<li class="nav-item  "><a href="javascript:;"
						class="nav-link nav-toggle"> <i class="fa fa-wrench"></i> <span
							class="title">修改密码</span>
					</a></li>
				</ul>
				<!-- END SIDEBAR MENU -->
			</div>
			<!-- END SIDEBAR -->
		</div>
		<!-- END SIDEBAR -->
		<!-- BEGIN CONTENT -->
		<!-- END QUICK SIDEBAR -->
		<div class="page-content-wrapper">
			<div class="page-content">
				<!-- <table id="content-table"></table> -->







				<!-- <div class="row">
					<div class="col-md-12">
					</div>
				</div> -->









				<div class="row">
					<div class="col-md-12">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet light bordered">
							<div class="portlet-title">
								<div class="caption font-dark">
									<i class="icon-settings font-dark"></i> <span
										class="caption-subject bold uppercase">管理</span>
								</div>
								<div class="actions">
									<div class="btn-group btn-group-devided" data-toggle="buttons">
										<label
											class="btn btn-transparent dark btn-outline btn-circle btn-sm active"
											onclick="create();"> <input type="radio"
											name="options" class="toggle" id="option1">新增
										</label>
									</div>
								</div>
							</div>
							<input type="hidden" class="txtshort" id="currentObj" value="1" />
							<div class="portlet-body">
								<div class="row">
									<div class="col-md-6" style="display: none;" id="createUser">
										<!-- BEGIN VALIDATION STATES-->
										<div class="portlet light portlet-fit portlet-form bordered">
											<div class="portlet-title">
												<div class="caption">
													<i class=" icon-layers font-green"></i> <span
														class="caption-subject font-green sbold uppercase">新增用户</span>
												</div>
											</div>
											<div class="portlet-body">
												<!-- BEGIN FORM-->
												<form class="form-horizontal" id="createUserForm">
													<div class="form-body">
														<div class="form-group form-md-line-input">
															<label class="col-md-3 control-label"
																for="form_control_1">昵称 <span class="required">*</span>
															</label>
															<div class="col-md-9">
																<input type="text" class="form-control" placeholder=""
																	name="name">
																<div class="form-control-focus"></div>
															</div>
														</div>
														<div class="form-group form-md-line-input">
															<label class="col-md-3 control-label"
																for="form_control_1">邮箱 <span class="required">*</span>
															</label>
															<div class="col-md-9">
																<input type="text" class="form-control" placeholder=""
																	name="email">
																<div class="form-control-focus"></div>
															</div>
														</div>
														<div class="form-group form-md-line-input">
															<label class="col-md-3 control-label"
																for="form_control_1">密码 <span class="required">*</span>
															</label>
															<div class="col-md-9">
																<input type="password" class="form-control"
																	placeholder="" name="password">
																<div class="form-control-focus"></div>
															</div>
														</div>
														<div class="form-group form-md-line-input">
															<label class="col-md-3 control-label"
																for="form_control_1">手机 <span class="required">&nbsp;</span>
															</label>
															<div class="col-md-9">
																<input type="text" class="form-control" placeholder=""
																	name="phone">
																<div class="form-control-focus"></div>
															</div>
														</div>
														<div class="form-group form-md-line-input">
															<label class="col-md-3 control-label"
																for="form_control_1">地址 <span class="required">&nbsp;</span>
															</label>
															<div class="col-md-9">
																<input type="text" class="form-control" placeholder=""
																	name="address">
																<div class="form-control-focus"></div>
															</div>
														</div>
														<div class="form-group form-md-line-input">
															<label class="col-md-3 control-label"
																for="form_control_1">是否可用 <span class="required">&nbsp;</span>
															</label>
															<div class="col-md-4">
																<div class="mt-radio-list"
																	data-error-container="#form_2_membership_error">
																	<label class="mt-radio"> <input type="radio"
																		name="usableStatus" value="0" checked="true" /> 是 <span></span>
																	</label> <label class="mt-radio"> <input type="radio"
																		name="usableStatus" value="1" /> 否 <span></span>
																	</label>
																</div>
																<div id="form_2_membership_error"></div>
															</div>
														</div>
													</div>
													<div class="form-actions">
														<div class="row">
															<div class="col-md-offset-3 col-md-9">
																<!-- <button type="submit" class="btn green" onclick="test()"></button> -->
																<input type="button" class="btn green"
																	onclick="createPost()" value="提交"></input>
																<button type="reset" class="btn default">重置</button>
															</div>
														</div>
													</div>
												</form>
												<!-- END FORM-->
											</div>
										</div>
										<!-- END VALIDATION STATES-->
									</div>

									<!-- 创建标签-->
									<div class="col-md-6" style="display: none;" id="createTag">
										<!-- BEGIN VALIDATION STATES-->
										<div class="portlet light portlet-fit portlet-form bordered">
											<div class="portlet-title">
												<div class="caption">
													<i class=" icon-layers font-green"></i> <span
														class="caption-subject font-green sbold uppercase">新增标签</span>
												</div>
											</div>
											<div class="portlet-body">
												<!-- BEGIN FORM-->
												<form action="#" class="form-horizontal" id="createTagForm">
													<div class="form-body">
														<div class="form-group form-md-line-input">
															<label class="col-md-3 control-label"
																for="form_control_1">标签名称 <span class="required">*</span>
															</label>
															<div class="col-md-9">
																<input type="text" class="form-control" placeholder=""
																	name="name">
																<div class="form-control-focus"></div>
															</div>
														</div>
														<div class="form-group form-md-line-input">
															<label class="col-md-3 control-label"
																for="form_control_1">是否可用 <span class="required">&nbsp;</span>
															</label>
															<div class="col-md-4">
																<div class="mt-radio-list"
																	data-error-container="#form_2_membership_error">
																	<label class="mt-radio"> <input type="radio"
																		name="usableStatus" value="1" checked="true" /> 是 <span></span>
																	</label> <label class="mt-radio"> <input type="radio"
																		name="usableStatus" value="2" /> 否 <span></span>
																	</label>
																</div>
																<div id="form_2_membership_error"></div>
															</div>
														</div>
													</div>
													<div class="form-actions">
														<div class="row">
															<div class="col-md-offset-3 col-md-9">
																<input type="button" class="btn green"
																	onclick="createPost()" value="提交"></input>
																<button type="reset" class="btn default">重置</button>
															</div>
														</div>
													</div>
												</form>
												<!-- END FORM-->
											</div>
										</div>
										<!-- END VALIDATION STATES-->
									</div>
									<!-- 创建标签结束-->

									<!-- 创建博客-->
									<div class="col-md-6" id="createBlog">
										<!-- BEGIN VALIDATION STATES-->
										<div class="portlet light portlet-fit portlet-form bordered">
											<div class="portlet-title">
												<div class="caption">
													<i class=" icon-layers font-green"></i> <span
														class="caption-subject font-green sbold uppercase">新增博客</span>
												</div>
											</div>


											<div class="portlet-body">
												<!-- BEGIN FORM-->
												<form id="fileupload" action="/blog/upload"
													class="form-horizontal" method="POST"
													enctype="multipart/form-data">
													<!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
													<div class="form-body">
														<div class="col-md-7">
															<!-- The fileinput-button span is used to style the file input field as button -->
															<span class="btn green fileinput-button"> <i
																class="fa fa-plus"></i> <span> 添加... </span> <input
																type="file" name="file" multiple>
															</span>
															<!-- <button type="submit" class="btn blue start">
																<i class="fa fa-upload"></i> <span>开始上传 </span>
															</button> -->
															<!-- The global file processing state -->
															<span class="fileupload-process"> </span>
														</div>
														<!-- The global progress information -->
														<div class="col-lg-5 fileupload-progress fade">
															<!-- The global progress bar -->
															<div class="progress progress-striped active"
																role="progressbar" aria-valuemin="0" aria-valuemax="100">
																<div class="progress-bar progress-bar-success"
																	style="width: 0%;"></div>
															</div>
															<!-- The extended global progress information -->
															<div class="progress-extended">&nbsp;</div>
														</div>
													</div>
													<!-- The table listing the files available for upload/download -->
													<table role="presentation"
														class="table table-striped clearfix">
														<tbody class="files">
														</tbody>
													</table>
												</form>

												<div id="blueimp-gallery"
													class="blueimp-gallery blueimp-gallery-controls"
													data-filter=":even">
													<div class="slides"></div>
													<h3 class="title"></h3>
													<a class="prev"> ‹ </a> <a class="next"> › </a> <a
														class="close white"> </a> <a class="play-pause"> </a>
													<ol class="indicator">
													</ol>
												</div>
												<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
												<script id="template-upload" type="text/x-tmpl"> {% for (var i=0, file; file=o.files[i]; i++) { %}
                        <tr class="template-upload fade">
                            <td>
                                <span class="preview"></span>
                            </td>
                            <td>
                                <p class="name">{%=file.name%}</p>
                                <strong class="error text-danger label label-danger"></strong>
                            </td>
                            <td>
                                <p class="size">Processing...</p>
                                <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0">
                                    <div class="progress-bar progress-bar-success" style="width:0%;"></div>
                                </div>
                            </td>
                            <td> {% if (!i && !o.options.autoUpload) { %}
                                <button class="btn blue start" disabled>
                                    <i class="fa fa-upload"></i>
                                    <span>Start</span>
                                </button> {% } %} {% if (!i) { %}
                                <button class="btn red cancel">
                                    <i class="fa fa-ban"></i>
                                    <span>Cancel</span>
                                </button> {% } %} </td>
                        </tr> {% } %} </script>
												<!-- The template to display files available for download -->
												<script id="template-download" type="text/x-tmpl"> {% for (var i=0, file; file=o.files[i]; i++) { %}
                        <tr class="template-download fade">
                            <td>
                                <span class="preview"> {% if (file.thumbnailUrl) { %}
                                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" data-gallery>
                                        <img src="{%=file.thumbnailUrl%}">
                                    </a> {% } %} </span>
                            </td>
                            <td>
                                <p class="name"> {% if (file.url) { %}
                                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" {%=file.thumbnailUrl? 'data-gallery': ''%}>{%=file.name%}</a> {% } else { %}
                                    <span>{%=file.name%}</span> {% } %} </p> {% if (file.error) { %}
                                <div>
                                    <span class="label label-danger">Error</span> {%=file.error%}</div> {% } %} </td>
                            <td>
                                <span class="size">{%=o.formatFileSize(file.size)%}</span>
                            </td>
                            <td> {% if (file.deleteUrl) { %}
                                <button class="btn red delete btn-sm" data-type="{%=file.deleteType%}" data-url="{%=file.deleteUrl%}" {% if (file.deleteWithCredentials) { %} data-xhr-fields='{"withCredentials":true}' {% } %}>
                                    <i class="fa fa-trash-o"></i>
                                    <span>Delete</span>
                                </button>
                                <input type="checkbox" name="delete" value="1" class="toggle"> {% } else { %}
                                <button class="btn yellow cancel btn-sm">
                                    <i class="fa fa-ban"></i>
                                    <span>Cancel</span>
                                </button> {% } %} </td>
                        </tr> {% } %} </script>



												<form action="#" class="form-horizontal" id="createBlogForm">
													<div class="form-body">
														<div class="form-group form-md-line-input">
															<label class="col-md-3 control-label"
																for="form_control_1">标题 <span class="required">*</span>
															</label>
															<div class="col-md-9">
																<input type="text" class="form-control" placeholder=""
																	name="title">
																<div class="form-control-focus"></div>
															</div>
														</div>

														<input type="hidden" class="form-control"
															name="htmlFilePath" readonly> <input
															type="hidden" class="form-control" name="mdFilePath"
															readonly>

														<div class="form-group form-md-line-input">
															<label class="col-md-3 control-label"
																for="form_control_1">博客标签 <span class="required">&nbsp;</span>
															</label>
															<div class="col-md-9">
																<input type="text" class="form-control" placeholder=""
																	name="number">
																<div class="form-control-focus"></div>
															</div>
														</div>
														<div class="form-group form-md-line-input">
															<label class="col-md-3 control-label"
																for="form_control_1">是否可用 <span class="required">&nbsp;</span>
															</label>
															<div class="col-md-4">
																<div class="mt-radio-list"
																	data-error-container="#form_2_membership_error">
																	<label class="mt-radio"> <input type="radio"
																		name="usableStatus" value="1" checked="true" /> 是 <span></span>
																	</label> <label class="mt-radio"> <input type="radio"
																		name="usableStatus" value="2" /> 否 <span></span>
																	</label>
																</div>
																<div id="form_2_membership_error"></div>
															</div>
														</div>
													</div>
													<div class="form-actions">
														<div class="row">
															<div class="col-md-offset-3 col-md-9">
																<input type="button" class="btn green"
																	onclick="createPost()" value="提交"></input>
																<button type="reset" class="btn default">重置</button>
															</div>
														</div>
													</div>
												</form>
												<!-- END FORM-->
											</div>
										</div>
										<!-- END VALIDATION STATES-->
									</div>
									<!-- 创建博客结束-->

									<!-- 创建角色-->
									<div class="col-md-6" style="display: none;" id="createRole">
										<!-- BEGIN VALIDATION STATES-->
										<div class="portlet light portlet-fit portlet-form bordered">
											<div class="portlet-title">
												<div class="caption">
													<i class=" icon-layers font-green"></i> <span
														class="caption-subject font-green sbold uppercase">新增角色</span>
												</div>
											</div>
											<div class="portlet-body">
												<!-- BEGIN FORM-->
												<form action="#" class="form-horizontal" id="createRoleForm">
													<div class="form-body">
														<div class="form-group form-md-line-input">
															<label class="col-md-3 control-label"
																for="form_control_1">角色名称 <span class="required">*</span>
															</label>
															<div class="col-md-9">
																<input type="text" class="form-control" placeholder=""
																	name="name">
																<div class="form-control-focus"></div>
															</div>
														</div>
														<div class="form-group form-md-line-input">
															<label class="col-md-3 control-label"
																for="form_control_1">角色描述 <span class="required">*</span>
															</label>
															<div class="col-md-9">
																<input type="text" class="form-control" placeholder=""
																	name="description">
																<div class="form-control-focus"></div>
															</div>
														</div>
														<div class="form-group form-md-line-input">
															<label class="col-md-3 control-label"
																for="form_control_1">是否可用 <span class="required">&nbsp;</span>
															</label>
															<div class="col-md-4">
																<div class="mt-radio-list"
																	data-error-container="#form_2_membership_error">
																	<label class="mt-radio"> <input type="radio"
																		name="usableStatus" value="1" checked="true" /> 是 <span></span>
																	</label> <label class="mt-radio"> <input type="radio"
																		name="usableStatus" value="2" /> 否 <span></span>
																	</label>
																</div>
																<div id="form_2_membership_error"></div>
															</div>
														</div>
													</div>
													<div class="form-actions">
														<div class="row">
															<div class="col-md-offset-3 col-md-9">
																<input type="button" class="btn green"
																	onclick="createPost()" value="提交"></input>
																<button type="reset" class="btn default">重置</button>
															</div>
														</div>
													</div>
												</form>
												<!-- END FORM-->
											</div>
										</div>
										<!-- END VALIDATION STATES-->
									</div>
									<!-- 创建角色结束-->

									<!-- 创建资源-->
									<div class="col-md-6" style="display: none;"
										id="createResource">
										<!-- BEGIN VALIDATION STATES-->
										<div class="portlet light portlet-fit portlet-form bordered">
											<div class="portlet-title">
												<div class="caption">
													<i class=" icon-layers font-green"></i> <span
														class="caption-subject font-green sbold uppercase">新增资源</span>
												</div>
											</div>
											<div class="portlet-body">
												<!-- BEGIN FORM-->
												<form action="#" class="form-horizontal"
													id="createResourceForm">
													<div class="form-body">
														<div class="form-group form-md-line-input">
															<label class="col-md-3 control-label"
																for="form_control_1">资源名称 <span class="required">*</span>
															</label>
															<div class="col-md-9">
																<input type="text" class="form-control" placeholder=""
																	name="name">
																<div class="form-control-focus"></div>
															</div>
														</div>
														<div class="form-group form-md-line-input">
															<label class="col-md-3 control-label"
																for="form_control_1">资源描述 <span class="required">&nbsp;</span>
															</label>
															<div class="col-md-9">
																<input type="text" class="form-control" placeholder=""
																	name="description">
																<div class="form-control-focus"></div>
															</div>
														</div>
														<div class="form-group form-md-line-input">
															<label class="col-md-3 control-label"
																for="form_control_1">资源地址 <span class="required">*</span>
															</label>
															<div class="col-md-9">
																<input type="text" class="form-control" placeholder=""
																	name="url">
																<div class="form-control-focus"></div>
															</div>
														</div>
														<div class="form-group form-md-line-input">
															<label class="col-md-3 control-label"
																for="form_control_1">父资源ID <span
																class="required">&nbsp;</span>
															</label>
															<div class="col-md-9">
																<input type="text" class="form-control" placeholder=""
																	name="parentId">
																<div class="form-control-focus"></div>
															</div>
														</div>

														<div class="form-group form-md-line-input">
															<label class="col-md-3 control-label"
																for="form_control_1">是否可用 <span class="required">&nbsp;</span>
															</label>
															<div class="col-md-4">
																<div class="mt-radio-list"
																	data-error-container="#form_2_membership_error">
																	<label class="mt-radio"> <input type="radio"
																		name="usableStatus" value="1" checked="true" /> 是 <span></span>
																	</label> <label class="mt-radio"> <input type="radio"
																		name="usableStatus" value="2" /> 否 <span></span>
																	</label>
																</div>
																<div id="form_2_membership_error"></div>
															</div>
														</div>
													</div>
													<div class="form-actions">
														<div class="row">
															<div class="col-md-offset-3 col-md-9">
																<input type="button" class="btn green"
																	onclick="createPost()" value="提交"></input>
																<button type="reset" class="btn default">重置</button>
															</div>
														</div>
													</div>
												</form>
												<!-- END FORM-->
											</div>
										</div>
										<!-- END VALIDATION STATES-->
									</div>
									<!-- 创建资源结束-->

								</div>
								<table class="table table-hover" id="cusTable"
									data-pagination="true" data-show-refresh="false"
									data-show-toggle="false" data-showColumns="true">
								</table>
							</div>
						</div>
						<!-- END EXAMPLE TABLE PORTLET-->
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->

	<div class="quick-nav-overlay"></div>
	<!-- END QUICK NAV -->
	<!--[if lt IE 9]>
<script src="../theme/assets/global/plugins/respond.min.js"></script>
<script src="../theme/assets/global/plugins/excanvas.min.js"></script> 
<script src="../theme/assets/global/plugins/ie8.fix.min.js"></script> 
<![endif]-->
	<!-- BEGIN CORE PLUGINS -->
	<script src="../theme/assets/global/plugins/jquery.min.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/bootstrap/js/bootstrap.min.js"
		type="text/javascript"></script>
	<script src="../theme/assets/global/plugins/js.cookie.min.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js"
		type="text/javascript"></script>
	<script src="../theme/assets/global/plugins/jquery.blockui.min.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js"
		type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script src="../theme/assets/global/plugins/moment.min.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/bootstrap-daterangepicker/daterangepicker.min.js"
		type="text/javascript"></script>
	<script src="../theme/assets/global/plugins/morris/morris.min.js"
		type="text/javascript"></script>
	<script src="../theme/assets/global/plugins/morris/raphael-min.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/counterup/jquery.waypoints.min.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/counterup/jquery.counterup.min.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/amcharts/amcharts/amcharts.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/amcharts/amcharts/serial.js"
		type="text/javascript"></script>
	<script src="../theme/assets/global/plugins/amcharts/amcharts/pie.js"
		type="text/javascript"></script>
	<script src="../theme/assets/global/plugins/amcharts/amcharts/radar.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/amcharts/amcharts/themes/light.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/amcharts/amcharts/themes/patterns.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/amcharts/amcharts/themes/chalk.js"
		type="text/javascript"></script>
	<script src="../theme/assets/global/plugins/amcharts/ammap/ammap.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/amcharts/ammap/maps/js/worldLow.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/amcharts/amstockcharts/amstock.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/fullcalendar/fullcalendar.min.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/horizontal-timeline/horizontal-timeline.js"
		type="text/javascript"></script>
	<script src="../theme/assets/global/plugins/flot/jquery.flot.min.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/flot/jquery.flot.resize.min.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/flot/jquery.flot.categories.min.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jquery-easypiechart/jquery.easypiechart.min.js"
		type="text/javascript"></script>
	<script src="../theme/assets/global/plugins/jquery.sparkline.min.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jqvmap/jqvmap/jquery.vmap.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jqvmap/jqvmap/maps/jquery.vmap.russia.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jqvmap/jqvmap/maps/jquery.vmap.world.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jqvmap/jqvmap/maps/jquery.vmap.europe.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jqvmap/jqvmap/maps/jquery.vmap.germany.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jqvmap/jqvmap/maps/jquery.vmap.usa.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jqvmap/jqvmap/data/jquery.vmap.sampledata.js"
		type="text/javascript"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN THEME GLOBAL SCRIPTS -->
	<script src="../theme/assets/global/scripts/app.min.js"
		type="text/javascript"></script>
	<!-- END THEME GLOBAL SCRIPTS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="../theme/assets/pages/scripts/dashboard.min.js"
		type="text/javascript"></script>
	<!-- END PAGE LEVEL SCRIPTS -->
	<!-- BEGIN THEME LAYOUT SCRIPTS -->
	<script src="../theme/assets/layouts/layout4/scripts/layout.min.js"
		type="text/javascript"></script>
	<script src="../theme/assets/layouts/layout4/scripts/demo.min.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/layouts/global/scripts/quick-sidebar.min.js"
		type="text/javascript"></script>
	<script src="../theme/assets/layouts/global/scripts/quick-nav.min.js"
		type="text/javascript"></script>
	<!-- END THEME LAYOUT SCRIPTS -->
	<script
		src="../theme/assets/pages/scripts/table-datatables-managed.min.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/bootstrap-table/bootstrap-table.min.js"></script>
	<script
		src="../theme/assets/global/plugins/bootstrap-table/bootstrap-table.js"></script>
	<script
		src="../theme/assets/global/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
	<script
		src="../theme/assets/global/plugins/bootstrap-table/extensions/export/bootstrap-table-export.js"></script>
	<script src="../theme/assets/global/scripts/datatable.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/datatables/datatables.min.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js"
		type="text/javascript"></script>


	<script
		src="../theme/assets/global/plugins/jquery-file-upload/js/vendor/jquery.ui.widget.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jquery-file-upload/js/vendor/tmpl.min.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jquery-file-upload/js/vendor/load-image.min.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jquery-file-upload/js/vendor/canvas-to-blob.min.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jquery-file-upload/blueimp-gallery/jquery.blueimp-gallery.min.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jquery-file-upload/js/jquery.iframe-transport.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jquery-file-upload/js/jquery.fileupload.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-process.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-image.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-audio.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-video.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-validate.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-ui.js"
		type="text/javascript"></script>

	<script
		src="../theme/assets/global/plugins/bootstrap/js/bootstrap.min.js"
		type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script
		src="../theme/assets/global/plugins/jquery-file-upload/js/vendor/tmpl.min.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jquery-file-upload/js/vendor/load-image.min.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jquery-file-upload/js/vendor/canvas-to-blob.min.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jquery-file-upload/blueimp-gallery/jquery.blueimp-gallery.min.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jquery-file-upload/js/jquery.iframe-transport.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jquery-file-upload/js/jquery.fileupload.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-process.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-image.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-audio.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-video.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-validate.js"
		type="text/javascript"></script>
	<script
		src="../theme/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-ui.js"
		type="text/javascript"></script>
	<script src="../theme/assets/pages/scripts/form-fileupload.min.js"
		type="text/javascript"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- END THEME GLOBAL SCRIPTS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->













	<script src="../theme/assets/operate/list.js" type="text/javascript"></script>
	<script src="../theme/assets/operate/update.js" type="text/javascript"></script>
	<script src="../theme/assets/operate/create.js" type="text/javascript"></script>
	<script src="../theme/assets/operate/delete.js" type="text/javascript"></script>
</body>

</html>