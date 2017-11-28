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

<link href="../theme/assets/global/plugins/socicon/socicon.css"
	rel="stylesheet" type="text/css" />
<link
	href="../theme/assets/global/plugins/jstree/dist/themes/default/style.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="../theme/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css"
	rel="stylesheet" type="text/css" />

<link href="../theme/assets/layouts/layout4/css/md.css" rel="stylesheet"
	type="text/css" />

<link
	href="../theme/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css"
	rel="stylesheet" type="text/css" />

<link
	href="../theme/assets/global/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css"
	rel="stylesheet" type="text/css" />
<link
	href="../theme/assets/global/plugins/bootstrap-modal/css/bootstrap-modal.css"
	rel="stylesheet" type="text/css" />
<link href="../theme/assets/apps/css/todo-2.min.css" rel="stylesheet"
	type="text/css" />
<!-- <link
	href="../theme/assets/global/plugins/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="../theme/assets/global/plugins/simple-line-icons/simple-line-icons.min.css"
	rel="stylesheet" type="text/css" />
<link href="../theme/assets/global/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="../theme/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css"
	rel="stylesheet" type="text/css" />
END GLOBAL MANDATORY STYLES
BEGIN PAGE LEVEL PLUGINS
<link href="../theme/assets/global/plugins/socicon/socicon.css"
	rel="stylesheet" type="text/css" />
END PAGE LEVEL PLUGINS
BEGIN THEME GLOBAL STYLES
<link href="../theme/assets/global/css/components.min.css" rel="stylesheet"
	id="style_components" type="text/css" />
<link href="../theme/assets/global/css/plugins.min.css" rel="stylesheet"
	type="text/css" />
END THEME GLOBAL STYLES
BEGIN THEME LAYOUT STYLES
<link href="../assets/layouts/layout4/css/layout.min.css"
	rel="stylesheet" type="text/css" />
<link href="../assets/layouts/layout4/css/themes/default.min.css"
	rel="stylesheet" type="text/css" id="style_color" />
<link href="../assets/layouts/layout4/css/custom.min.css"
	rel="stylesheet" type="text/css" /> -->
<!-- END THEME LAYOUT STYLES -->
<!-- <link rel="shortcut icon" href="favicon.ico" /> -->
<style type="text/css">
/* .md-fences { font-size: 0.9rem; display: block; break-inside: avoid; text-align: left; overflow: visible; white-space: pre; background-image: inherit; background-size: inherit; background-attachment: inherit; background-origin: inherit; background-clip: inherit; background-color: inherit; position: relative !important; background-position: inherit inherit; background-repeat: inherit inherit; }
.md-diagram-panel { width: 100%; margin-top: 10px; text-align: center; padding-top: 0px; padding-bottom: 8px; overflow-x: auto; }
.md-fences .CodeMirror.CodeMirror-wrap { top: -1.6em; margin-bottom: -1.6em; }
.md-fences.mock-cm { white-space: pre-wrap; } */
</style>
</head>
<!-- END HEAD -->


<body
	class="page-container-bg-solid page-header-fixed page-sidebar-closed-hide-logo">
	<!-- BEGIN HEADER -->
	<div class="page-header navbar navbar-fixed-top">
		<!-- BEGIN HEADER INNER -->
		<div class="page-header-inner ">
			<div class="page-top">
				<!-- END HEADER SEARCH BOX -->
				<!-- BEGIN TOP NAVIGATION MENU -->
				<div class="top-menu">
					<ul class="nav navbar-nav pull-right">
						<li class="separator hide"></li>
						<li class="dropdown dropdown-user dropdown-dark"><a
							href="javascript:;" class="dropdown-toggle"
							data-toggle="dropdown" data-hover="dropdown"
							data-close-others="true"> <span
								class="username username-hide-on-mobile">常用导航</span> <!-- DOC: Do not remove below empty space(&nbsp;) as its purposely used -->
								<img alt="" class="img-circle" />
						</a>
							<ul class="dropdown-menu dropdown-menu-default">
								<li><a href="page_user_profile_1.html"> <i
										class="icon-user"></i>谷歌
								</a></li>
								<li><a href="app_calendar.html"> <i
										class="icon-calendar"></i>GitHub
								</a></li>
								<li><a href="app_calendar.html"> <i
										class="icon-calendar"></i>百度
								</a></li>
								<li><a href="app_calendar.html"> <i
										class="icon-calendar"></i>百度地图
								</a></li>
							</ul></li>
						<li class="dropdown dropdown-user dropdown-dark"><a
							href="javascript:;" class="dropdown-toggle"
							data-toggle="dropdown" data-hover="dropdown"
							data-close-others="true"> <span
								class="username username-hide-on-mobile">常用工具</span> <!-- DOC: Do not remove below empty space(&nbsp;) as its purposely used -->
								<img alt="" class="img-circle" />
						</a>
							<ul class="dropdown-menu dropdown-menu-default">
								<li><a href="page_user_profile_1.html"> <i
										class="icon-user"></i>联系作者
								</a></li>
								<li><a href="app_calendar.html"> <i
										class="icon-calendar"></i>关于作者
								</a></li>
							</ul></li>
						<li class="dropdown dropdown-user dropdown-dark"><a
							href="javascript:;" class="dropdown-toggle"
							data-toggle="dropdown" data-hover="dropdown"
							data-close-others="true"> <span
								class="username username-hide-on-mobile">Sunny</span> <!-- DOC: Do not remove below empty space(&nbsp;) as its purposely used -->
								<img alt="" class="img-circle"
								src="../theme/assets/layouts/layout4/img/sunny.png" />
						</a>
							<ul class="dropdown-menu dropdown-menu-default">
								<li><a href="page_user_profile_1.html"> <i
										class="icon-user"></i>联系作者
								</a></li>
								<li><a href="app_calendar.html"> <i
										class="icon-calendar"></i>关于作者
								</a></li>
							</ul></li>
						<!-- END USER LOGIN DROPDOWN -->
						<!-- BEGIN QUICK SIDEBAR TOGGLER -->
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
				<!-- <ul class="page-sidebar-menu   " data-keep-expanded="false"
					data-auto-scroll="true" data-slide-speed="200">
					<li class="nav-item start active open"><a href="javascript:;"
						class="nav-link nav-toggle"> <i class="icon-home"></i> <span
							class="title">首页</span>
					</a></li>
					<li class="heading">
						<h3 class="uppercase">模块管理</h3>
					</li>
					<li class="nav-item  "><a href="javascript:;"
						class="nav-link nav-toggle"> <i class="icon-user"></i> <span
							class="title">用户管理</span>
					</a></li>
					<li class="nav-item  "><a href="javascript:;"
						class="nav-link nav-toggle"> <i class="fa fa-users"></i> <span
							class="title">权限管理</span>
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
				</ul> -->
				<!-- END SIDEBAR MENU -->
			</div>
			<!-- END SIDEBAR -->
		</div>
		<!-- END SIDEBAR -->
		<!-- BEGIN CONTENT -->
		<!-- END QUICK SIDEBAR -->

		<div class="page-content-wrapper">
			<div class="page-content">
				<div class="page-head">
					<!-- BEGIN PAGE TITLE -->
					<div class="page-title">
						<h1>
							网易对接文档 <small>DSP管理、接口管理</small>
						</h1>

					</div>
					<!-- END PAGE TITLE -->
					<!-- BEGIN PAGE TOOLBAR -->
					<!-- END PAGE TOOLBAR -->
				</div>
				<div class="noteAd">
					<img src="../theme/assets/netease.png" border="0" width=100%
						height=100% />
				</div>
				<!-- <div class="row">
                	
                </div> -->

				<!-- ----------------------------------------------- -->

				<!-- --------------------------------------------------------- -->
				<form role="form">
					<div class="form-group">
						<textarea class="form-control" rows="3"></textarea>
					</div>
					<div class="form-actions">
						<button type="button" class="btn default">取消</button>
						<button type="submit" class="btn green">提交</button>
					</div>
				</form>
				<br />
				<div class="note note-info">
					<span>已经有0条评论</span>
				</div>

				<!-- <div class="note note-info">
					<span>已经有0条评论</span>
				</div> -->
				<!-- <div class="row">
                        <div class="col-md-6">
                            <div class="portlet yellow-lemon box">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-cogs"></i>Contextual Menu with Drag & Drop </div>
                                    <div class="tools">
                                        <a href="javascript:;" class="collapse"> </a>
                                        <a href="#portlet-config" data-toggle="modal" class="config"> </a>
                                        <a href="javascript:;" class="reload"> </a>
                                        <a href="javascript:;" class="remove"> </a>
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <div id="tree_3" class="tree-demo"> </div>
                                </div>
                            </div>
                        </div>
                    </div> -->

				<!-- 评论区 -->
				<div class="row">
					<div class="col-md-12">
						<!-- BEGIN TODO SIDEBAR -->
						<div class="todo-ui">

							<!-- END TODO SIDEBAR -->
							<!-- BEGIN TODO CONTENT -->
							<div class="todo-content">
								<div class="portlet light bordered">
									<!-- PROJECT HEAD -->

									<!-- end PROJECT HEAD -->
									<div class="portlet-body">
										<div class="row">

											<div class="todo-tasklist-devider"></div>
											<div class="col-md-7 col-sm-8">
												<form action="#" class="form-horizontal">
													<!-- TASK HEAD -->
													<div class="form">
														<div class="form-group">
															<div class="col-md-8 col-sm-8">
																<div class="todo-taskbody-user">
																	<img class="todo-userpic pull-left"
																		src="../theme/assets/pages/media/users/avatar6.jpg"
																		width="50px" height="50px"> <span
																		class="todo-username pull-left">Vanessa Bond</span>
																	<button type="button"
																		class="todo-username-btn btn btn-circle btn-default btn-sm">&nbsp;edit&nbsp;</button>
																</div>
															</div>
															<div class="col-md-4 col-sm-4">
																<div class="todo-taskbody-date pull-right">
																	<button type="button"
																		class="todo-username-btn btn btn-circle btn-default btn-sm">&nbsp;
																		Complete &nbsp;</button>
																</div>
															</div>
														</div>
														<!-- END TASK HEAD -->
														<!-- TASK TITLE -->
														<div class="form-group">
															<div class="col-md-12">
																<input type="text"
																	class="form-control todo-taskbody-tasktitle"
																	placeholder="Task Title...">
															</div>
														</div>
														<!-- TASK DESC -->
														<div class="form-group">
															<div class="col-md-12">
																<textarea class="form-control todo-taskbody-taskdesc"
																	rows="8" placeholder="Task Description..."></textarea>
															</div>
														</div>
														<!-- END TASK DESC -->
														<!-- TASK DUE DATE -->
														<div class="form-group">
															<div class="col-md-12">
																<div class="input-icon">
																	<i class="fa fa-calendar"></i> <input type="text"
																		class="form-control todo-taskbody-due"
																		placeholder="Due Date...">
																</div>
															</div>
														</div>
														<!-- TASK TAGS -->
														<div class="form-group">
															<div class="col-md-12">
																<select class="form-control todo-taskbody-tags">
																	<option value="Pending">Pending</option>
																	<option value="Completed">Completed</option>
																	<option value="Testing">Testing</option>
																	<option value="Approved">Approed</option>
																	<option value="Rejected">Rejected</option>
																</select>
															</div>
														</div>
														<!-- TASK TAGS -->
														<div class="form-actions right todo-form-actions">
															<button class="btn btn-circle btn-sm green">Save
																Changes</button>
															<button class="btn btn-circle btn-sm btn-default">Cancel</button>
														</div>
													</div>
													<div class="tabbable-line">
														<ul class="nav nav-tabs ">
															<li class="active"><a href="#tab_1"
																data-toggle="tab"> Comments </a></li>
															<li><a href="#tab_2" data-toggle="tab"> History
															</a></li>
														</ul>
														<div class="tab-content">
															<div class="tab-pane active" id="tab_1">
																<!-- TASK COMMENTS -->
																<div class="form-group">
																	<div class="col-md-12">
																		<ul class="media-list">
																			<li class="media"><a class="pull-left"
																				href="javascript:;"> <img class="todo-userpic"
																					src="../theme/assets/pages/media/users/avatar8.jpg"
																					width="27px" height="27px">
																			</a>
																				<div class="media-body todo-comment">
																					<button type="button"
																						class="todo-comment-btn btn btn-circle btn-default btn-sm">&nbsp;
																						Reply &nbsp;</button>
																					<p class="todo-comment-head">
																						<span class="todo-comment-username">Christina
																							Aguilera</span> &nbsp; <span class="todo-comment-date">17
																							Sep 2014 at 2:05pm</span>
																					</p>
																					<p class="todo-text-color">Cras sit amet nibh
																						libero, in gravida nulla. Nulla vel metus
																						scelerisque ante sollicitudin commodo. Cras purus
																						odio, vestibulum in vulputate at, tempus viverra
																						turpis.</p>
																					<!-- Nested media object -->
																					<div class="media">
																						<a class="pull-left" href="javascript:;"> <img
																							class="todo-userpic"
																							src="../theme/assets/pages/media/users/avatar4.jpg"
																							width="27px" height="27px">
																						</a>
																						<div class="media-body">
																							<p class="todo-comment-head">
																								<span class="todo-comment-username">Carles
																									Puyol</span> &nbsp; <span class="todo-comment-date">17
																									Sep 2014 at 4:30pm</span>
																							</p>
																							<p class="todo-text-color">Thanks so much my
																								dear!</p>
																						</div>
																					</div>
																				</div></li>
																			<li class="media"><a class="pull-left"
																				href="javascript:;"> <img class="todo-userpic"
																					src="../theme/assets/pages/media/users/avatar5.jpg"
																					width="27px" height="27px">
																			</a>
																				<div class="media-body todo-comment">
																					<button type="button"
																						class="todo-comment-btn btn btn-circle btn-default btn-sm">&nbsp;
																						Reply &nbsp;</button>
																					<p class="todo-comment-head">
																						<span class="todo-comment-username">Andres
																							Iniesta</span> &nbsp; <span class="todo-comment-date">18
																							Sep 2014 at 9:22am</span>
																					</p>
																					<p class="todo-text-color">
																						Cras sit amet nibh libero, in gravida nulla.
																						Scelerisque ante sollicitudin commodo Nulla vel
																						metus scelerisque ante sollicitudin commodo. Cras
																						purus odio, vestibulum in vulputate at, tempus
																						viverra turpis. <br>
																					</p>
																				</div></li>
																			<li class="media"><a class="pull-left"
																				href="javascript:;"> <img class="todo-userpic"
																					src="../theme/assets/pages/media/users/avatar6.jpg"
																					width="27px" height="27px">
																			</a>
																				<div class="media-body todo-comment">
																					<button type="button"
																						class="todo-comment-btn btn btn-circle btn-default btn-sm">&nbsp;
																						Reply &nbsp;</button>
																					<p class="todo-comment-head">
																						<span class="todo-comment-username">Olivia
																							Wilde</span> &nbsp; <span class="todo-comment-date">18
																							Sep 2014 at 11:50am</span>
																					</p>
																					<p class="todo-text-color">
																						Cras sit amet nibh libero, in gravida nulla.
																						Scelerisque ante sollicitudin commodo Nulla vel
																						metus scelerisque ante sollicitudin commodo. Cras
																						purus odio, vestibulum in vulputate at, tempus
																						viverra turpis. <br>
																					</p>
																				</div></li>
																		</ul>
																	</div>
																</div>
																<!-- END TASK COMMENTS -->
																<!-- TASK COMMENT FORM -->
																<div class="form-group">
																	<div class="col-md-12">
																		<ul class="media-list">
																			<li class="media"><a class="pull-left"
																				href="javascript:;"> <img class="todo-userpic"
																					src="../theme/assets/pages/media/users/avatar4.jpg"
																					width="27px" height="27px">
																			</a>
																				<div class="media-body">
																					<textarea
																						class="form-control todo-taskbody-taskdesc"
																						rows="4" placeholder="Type comment..."></textarea>
																				</div></li>
																		</ul>
																		<button type="button"
																			class="pull-right btn btn-sm btn-circle green">
																			&nbsp; Submit &nbsp;</button>
																	</div>
																</div>
																<!-- END TASK COMMENT FORM -->
															</div>
															<div class="tab-pane" id="tab_2">
																<ul class="todo-task-history">
																	<li>
																		<div class="todo-task-history-date">20 Jun, 2014
																			at 11:35am</div>
																		<div class="todo-task-history-desc">Task created
																		</div>
																	</li>
																	<li>
																		<div class="todo-task-history-date">21 Jun, 2014
																			at 10:35pm</div>
																		<div class="todo-task-history-desc">Task
																			category status changed to "Top Priority"</div>
																	</li>
																	<li>
																		<div class="todo-task-history-date">22 Jun, 2014
																			at 11:35am</div>
																		<div class="todo-task-history-desc">Task owner
																			changed to "Nick Larson"</div>
																	</li>
																	<li>
																		<div class="todo-task-history-date">30 Jun, 2014
																			at 8:09am</div>
																		<div class="todo-task-history-desc">Task
																			completed by "Nick Larson"</div>
																	</li>
																</ul>
															</div>
														</div>
													</div>
												</form>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- END TODO CONTENT -->
						</div>
					</div>
					<!-- END PAGE CONTENT-->
				</div>

				<!-- 评论区结束 -->
			</div>
		</div>
	</div>
	<!-- END CONTAINER -->
	<div class="page-footer">
		<!-- 	<div class="note note-info">
					<img src="http://img1.126.net/channel21/0/028585_1200125_0811.jpg"
						border="0" width=100% height=100% />
	</div> -->
		<div class="page-footer-inner">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2016
			&copy; Metronic Theme By <a target="_blank"
				href="http://keenthemes.com">Keenthemes</a> &nbsp;|&nbsp; <a
				href="http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes"
				title="Purchase Metronic just for 27$ and get lifetime updates for free"
				target="_blank">Purchase Metronic!</a>
		</div>
		<div class="scroll-to-top">
			<i class="icon-arrow-up"></i>
		</div>
	</div>
	<!-- BEGIN FOOTER -->
	<nav class="quick-nav">
		<a class="quick-nav-trigger" href="#0"> <span aria-hidden="true"></span>
		</a>
		<ul>
			<li><a class="active"> <!-- <span>Customer Reviews</span> -->
					<img alt=""
					src="data:image/png;base64,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" />
					<i class="fa fa-weixin"></i>
			</a></li>
		</ul>
		<span aria-hidden="true" class="quick-nav-bg"></span>
	</nav>
	<!-- <div class="quick-nav-overlay"></div> -->
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
	<script src="../theme/assets/global/plugins/jstree/dist/jstree.min.js"
		type="text/javascript"></script>
	<script src="../theme/assets/pages/scripts/ui-tree.min.js"
		type="text/javascript"></script>
	<!-- END THEME LAYOUT SCRIPTS -->
</body>

</html>