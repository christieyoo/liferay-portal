/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.UserNotificationDelivery;
import com.liferay.portal.model.UserNotificationDeliveryModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * The base model implementation for the UserNotificationDelivery service. Represents a row in the &quot;UserNotificationDelivery&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portal.model.UserNotificationDeliveryModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link UserNotificationDeliveryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserNotificationDeliveryImpl
 * @see com.liferay.portal.model.UserNotificationDelivery
 * @see com.liferay.portal.model.UserNotificationDeliveryModel
 * @generated
 */
public class UserNotificationDeliveryModelImpl extends BaseModelImpl<UserNotificationDelivery>
	implements UserNotificationDeliveryModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a user notification delivery model instance should use the {@link com.liferay.portal.model.UserNotificationDelivery} interface instead.
	 */
	public static final String TABLE_NAME = "UserNotificationDelivery";
	public static final Object[][] TABLE_COLUMNS = {
			{ "userNotificationDeliveryId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "classNameId", Types.BIGINT },
			{ "type_", Types.INTEGER },
			{ "email", Types.BOOLEAN },
			{ "sms", Types.BOOLEAN },
			{ "website", Types.BOOLEAN }
		};
	public static final String TABLE_SQL_CREATE = "create table UserNotificationDelivery (userNotificationDeliveryId LONG not null primary key,companyId LONG,userId LONG,classNameId LONG,type_ INTEGER,email BOOLEAN,sms BOOLEAN,website BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table UserNotificationDelivery";
	public static final String ORDER_BY_JPQL = " ORDER BY userNotificationDelivery.userNotificationDeliveryId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY UserNotificationDelivery.userNotificationDeliveryId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.UserNotificationDelivery"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.UserNotificationDelivery"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portal.model.UserNotificationDelivery"),
			true);
	public static long CLASSNAMEID_COLUMN_BITMASK = 1L;
	public static long TYPE_COLUMN_BITMASK = 2L;
	public static long USERID_COLUMN_BITMASK = 4L;
	public static long USERNOTIFICATIONDELIVERYID_COLUMN_BITMASK = 8L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.UserNotificationDelivery"));

	public UserNotificationDeliveryModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _userNotificationDeliveryId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setUserNotificationDeliveryId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _userNotificationDeliveryId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return UserNotificationDelivery.class;
	}

	@Override
	public String getModelClassName() {
		return UserNotificationDelivery.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("userNotificationDeliveryId",
			getUserNotificationDeliveryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("type", getType());
		attributes.put("email", getEmail());
		attributes.put("sms", getSms());
		attributes.put("website", getWebsite());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long userNotificationDeliveryId = (Long)attributes.get(
				"userNotificationDeliveryId");

		if (userNotificationDeliveryId != null) {
			setUserNotificationDeliveryId(userNotificationDeliveryId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Boolean email = (Boolean)attributes.get("email");

		if (email != null) {
			setEmail(email);
		}

		Boolean sms = (Boolean)attributes.get("sms");

		if (sms != null) {
			setSms(sms);
		}

		Boolean website = (Boolean)attributes.get("website");

		if (website != null) {
			setWebsite(website);
		}
	}

	@Override
	public long getUserNotificationDeliveryId() {
		return _userNotificationDeliveryId;
	}

	@Override
	public void setUserNotificationDeliveryId(long userNotificationDeliveryId) {
		_userNotificationDeliveryId = userNotificationDeliveryId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_columnBitmask |= USERID_COLUMN_BITMASK;

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = _userId;
		}

		_userId = userId;
	}

	@Override
	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	@Override
	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

	@Override
	public String getClassName() {
		if (getClassNameId() <= 0) {
			return StringPool.BLANK;
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	@Override
	public void setClassName(String className) {
		long classNameId = 0;

		if (Validator.isNotNull(className)) {
			classNameId = PortalUtil.getClassNameId(className);
		}

		setClassNameId(classNameId);
	}

	@Override
	public long getClassNameId() {
		return _classNameId;
	}

	@Override
	public void setClassNameId(long classNameId) {
		_columnBitmask |= CLASSNAMEID_COLUMN_BITMASK;

		if (!_setOriginalClassNameId) {
			_setOriginalClassNameId = true;

			_originalClassNameId = _classNameId;
		}

		_classNameId = classNameId;
	}

	public long getOriginalClassNameId() {
		return _originalClassNameId;
	}

	@Override
	public int getType() {
		return _type;
	}

	@Override
	public void setType(int type) {
		_columnBitmask |= TYPE_COLUMN_BITMASK;

		if (!_setOriginalType) {
			_setOriginalType = true;

			_originalType = _type;
		}

		_type = type;
	}

	public int getOriginalType() {
		return _originalType;
	}

	@Override
	public boolean getEmail() {
		return _email;
	}

	@Override
	public boolean isEmail() {
		return _email;
	}

	@Override
	public void setEmail(boolean email) {
		_email = email;
	}

	@Override
	public boolean getSms() {
		return _sms;
	}

	@Override
	public boolean isSms() {
		return _sms;
	}

	@Override
	public void setSms(boolean sms) {
		_sms = sms;
	}

	@Override
	public boolean getWebsite() {
		return _website;
	}

	@Override
	public boolean isWebsite() {
		return _website;
	}

	@Override
	public void setWebsite(boolean website) {
		_website = website;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			UserNotificationDelivery.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public UserNotificationDelivery toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (UserNotificationDelivery)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		UserNotificationDeliveryImpl userNotificationDeliveryImpl = new UserNotificationDeliveryImpl();

		userNotificationDeliveryImpl.setUserNotificationDeliveryId(getUserNotificationDeliveryId());
		userNotificationDeliveryImpl.setCompanyId(getCompanyId());
		userNotificationDeliveryImpl.setUserId(getUserId());
		userNotificationDeliveryImpl.setClassNameId(getClassNameId());
		userNotificationDeliveryImpl.setType(getType());
		userNotificationDeliveryImpl.setEmail(getEmail());
		userNotificationDeliveryImpl.setSms(getSms());
		userNotificationDeliveryImpl.setWebsite(getWebsite());

		userNotificationDeliveryImpl.resetOriginalValues();

		return userNotificationDeliveryImpl;
	}

	@Override
	public int compareTo(UserNotificationDelivery userNotificationDelivery) {
		long primaryKey = userNotificationDelivery.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof UserNotificationDelivery)) {
			return false;
		}

		UserNotificationDelivery userNotificationDelivery = (UserNotificationDelivery)obj;

		long primaryKey = userNotificationDelivery.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public void resetOriginalValues() {
		UserNotificationDeliveryModelImpl userNotificationDeliveryModelImpl = this;

		userNotificationDeliveryModelImpl._originalUserId = userNotificationDeliveryModelImpl._userId;

		userNotificationDeliveryModelImpl._setOriginalUserId = false;

		userNotificationDeliveryModelImpl._originalClassNameId = userNotificationDeliveryModelImpl._classNameId;

		userNotificationDeliveryModelImpl._setOriginalClassNameId = false;

		userNotificationDeliveryModelImpl._originalType = userNotificationDeliveryModelImpl._type;

		userNotificationDeliveryModelImpl._setOriginalType = false;

		userNotificationDeliveryModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<UserNotificationDelivery> toCacheModel() {
		UserNotificationDeliveryCacheModel userNotificationDeliveryCacheModel = new UserNotificationDeliveryCacheModel();

		userNotificationDeliveryCacheModel.userNotificationDeliveryId = getUserNotificationDeliveryId();

		userNotificationDeliveryCacheModel.companyId = getCompanyId();

		userNotificationDeliveryCacheModel.userId = getUserId();

		userNotificationDeliveryCacheModel.classNameId = getClassNameId();

		userNotificationDeliveryCacheModel.type = getType();

		userNotificationDeliveryCacheModel.email = getEmail();

		userNotificationDeliveryCacheModel.sms = getSms();

		userNotificationDeliveryCacheModel.website = getWebsite();

		return userNotificationDeliveryCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{userNotificationDeliveryId=");
		sb.append(getUserNotificationDeliveryId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", classNameId=");
		sb.append(getClassNameId());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", email=");
		sb.append(getEmail());
		sb.append(", sms=");
		sb.append(getSms());
		sb.append(", website=");
		sb.append(getWebsite());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(28);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.UserNotificationDelivery");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>userNotificationDeliveryId</column-name><column-value><![CDATA[");
		sb.append(getUserNotificationDeliveryId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classNameId</column-name><column-value><![CDATA[");
		sb.append(getClassNameId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>email</column-name><column-value><![CDATA[");
		sb.append(getEmail());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>sms</column-name><column-value><![CDATA[");
		sb.append(getSms());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>website</column-name><column-value><![CDATA[");
		sb.append(getWebsite());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = UserNotificationDelivery.class.getClassLoader();
	private static Class<?>[] _escapedModelInterfaces = new Class[] {
			UserNotificationDelivery.class
		};
	private long _userNotificationDeliveryId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private int _type;
	private int _originalType;
	private boolean _setOriginalType;
	private boolean _email;
	private boolean _sms;
	private boolean _website;
	private long _columnBitmask;
	private UserNotificationDelivery _escapedModel;
}