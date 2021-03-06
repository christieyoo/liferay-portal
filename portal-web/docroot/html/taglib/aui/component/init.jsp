<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

@generated
--%>

<%@ include file="/html/taglib/taglib-init.jsp" %>

<%
java.lang.String excludeAttributes = GetterUtil.getString((java.lang.String)request.getAttribute("aui:component:excludeAttributes"));
java.lang.String javaScriptAttributes = GetterUtil.getString((java.lang.String)request.getAttribute("aui:component:javaScriptAttributes"));
java.lang.String module = GetterUtil.getString((java.lang.String)request.getAttribute("aui:component:module"));
java.lang.String name = GetterUtil.getString((java.lang.String)request.getAttribute("aui:component:name"));
java.util.Map<java.lang.String, java.lang.Object> options = (java.util.Map<java.lang.String, java.lang.Object>)request.getAttribute("aui:component:options");
java.lang.String scriptPosition = GetterUtil.getString((java.lang.String)request.getAttribute("aui:component:scriptPosition"));
javax.servlet.jsp.JspContext tagPageContext = (javax.servlet.jsp.JspContext)request.getAttribute("aui:component:tagPageContext");
boolean useJavaScript = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:component:useJavaScript")), true);
java.lang.String var = GetterUtil.getString((java.lang.String)request.getAttribute("aui:component:var"));
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:component:dynamicAttributes");
%>

<%@ include file="/html/taglib/aui/component/init-ext.jspf" %>