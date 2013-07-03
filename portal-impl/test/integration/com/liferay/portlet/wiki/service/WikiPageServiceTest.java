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

package com.liferay.portlet.wiki.service;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetLinkLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.util.ExpandoTestUtil;
import com.liferay.portlet.wiki.DuplicatePageException;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.util.WikiTestUtil;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.testng.Assert;

/**
 * @author Manuel de la Peña
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
@Transactional
public class WikiPageServiceTest {

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();

		_group = GroupTestUtil.addGroup();

		_node = WikiTestUtil.addNode(
			TestPropsValues.getUserId(), _group.getGroupId(),
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString(50));
	}

	@Test
	public void testGetPage() throws Exception {
		WikiPage page = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _group.getGroupId(), _node.getNodeId(),
			ServiceTestUtil.randomString(), true);

		WikiPage retrievedPage = WikiPageLocalServiceUtil.getPage(
			page.getResourcePrimKey());

		Assert.assertEquals(page.getPageId(), retrievedPage.getPageId());
	}

	@Test
	public void testMovePage() throws Exception {
		testMovePage(false);
	}

	@Test(expected = DuplicatePageException.class)
	public void testMovePageSameName() throws Exception {
		WikiPage page = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _group.getGroupId(), _node.getNodeId(),
			ServiceTestUtil.randomString(), true);

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		WikiPageLocalServiceUtil.movePage(
			TestPropsValues.getUserId(), _node.getNodeId(), page.getTitle(),
			page.getTitle(), true, serviceContext);
	}

	@Test
	public void testMovePageWithExpando() throws Exception {
		testMovePage(true);
	}

	@Test
	public void testRevertPage() throws Exception {
		testRevertPage(false);
	}

	@Test
	public void testRevertPageWithExpando() throws Exception {
		testRevertPage(true);
	}

	protected void checkPopulatedServiceContext(
			ServiceContext serviceContext, WikiPage page,
			boolean hasExpandoValues)
		throws Exception {

		long[] assetCategoryIds = AssetCategoryLocalServiceUtil.getCategoryIds(
			WikiPage.class.getName(), page.getResourcePrimKey());

		Assert.assertEquals(
			assetCategoryIds, serviceContext.getAssetCategoryIds());

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			WikiPage.class.getName(), page.getResourcePrimKey());

		List<AssetLink> assetLinks = AssetLinkLocalServiceUtil.getLinks(
			assetEntry.getEntryId());

		long[] assetLinkEntryIds = StringUtil.split(
			ListUtil.toString(assetLinks, AssetLink.ENTRY_ID2_ACCESSOR), 0L);

		Assert.assertEquals(
			assetLinkEntryIds, serviceContext.getAssetLinkEntryIds());

		String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(
			WikiPage.class.getName(), page.getResourcePrimKey());

		Assert.assertEquals(assetTagNames, serviceContext.getAssetTagNames());

		if (hasExpandoValues) {
			ExpandoBridge expandoBridge = page.getExpandoBridge();

			AssertUtils.assertEquals(
				serviceContext.getExpandoBridgeAttributes(),
				expandoBridge.getAttributes());
		}
	}

	protected void testMovePage(boolean hasExpandoValues) throws Exception {
		WikiPage page = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _group.getGroupId(), _node.getNodeId(),
			ServiceTestUtil.randomString(), true);

		ExpandoColumn column = null;
		ExpandoValue value = null;
		ExpandoBridge expandoBridge = null;

		if (hasExpandoValues) {
			ExpandoTable table = ExpandoTestUtil.addTable(
				PortalUtil.getClassNameId(WikiPage.class),
				ServiceTestUtil.randomString());

			column = ExpandoTestUtil.addColumn(
				table, ServiceTestUtil.randomString(),
				ExpandoColumnConstants.STRING);

			value = ExpandoTestUtil.addValue(
				table, column, page.getPrimaryKey(),
				ServiceTestUtil.randomString());

			expandoBridge = page.getExpandoBridge();

			expandoBridge.addAttribute(
				column.getName(), ExpandoColumnConstants.STRING,
				value.getString());
		}

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		WikiPageLocalServiceUtil.movePage(
			TestPropsValues.getUserId(), _node.getNodeId(), page.getTitle(),
			"New Title", true, serviceContext);

		WikiPage movedPage = WikiPageLocalServiceUtil.getPage(
			_node.getNodeId(), "New Title");

		Assert.assertNotNull(movedPage);

		checkPopulatedServiceContext(
			serviceContext, movedPage, hasExpandoValues);
	}

	protected void testRevertPage(boolean hasExpandoValues) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		String originalContent = ServiceTestUtil.randomString();

		WikiPage originalPage = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(),
			ServiceTestUtil.randomString(), originalContent, true,
			serviceContext);

		ExpandoColumn column = null;
		ExpandoValue value = null;
		ExpandoBridge expandoBridge = null;

		if (hasExpandoValues) {
			ExpandoTable table = ExpandoTestUtil.addTable(
				PortalUtil.getClassNameId(WikiPage.class),
				ServiceTestUtil.randomString());

			column = ExpandoTestUtil.addColumn(
				table, ServiceTestUtil.randomString(),
				ExpandoColumnConstants.STRING);

			value = ExpandoTestUtil.addValue(
				table, column, originalPage.getPrimaryKey(),
				ServiceTestUtil.randomString());

			expandoBridge = originalPage.getExpandoBridge();

			expandoBridge.addAttribute(
				column.getName(), ExpandoColumnConstants.STRING,
				value.getString());
		}

		WikiPage updatedPage1 = WikiTestUtil.updatePage(
			originalPage, TestPropsValues.getUserId(),
			originalContent + "\nAdded second line.", serviceContext);

		Assert.assertNotEquals(originalContent, updatedPage1.getContent());

		WikiPage updatedPage2 = WikiTestUtil.updatePage(
			updatedPage1, TestPropsValues.getUserId(),
			updatedPage1.getContent() + "\nAdded third line.", serviceContext);

		Assert.assertNotEquals(originalContent, updatedPage2.getContent());

		WikiPage revertedPage = WikiPageLocalServiceUtil.revertPage(
			TestPropsValues.getUserId(), _node.getNodeId(),
			updatedPage2.getTitle(), originalPage.getVersion(), serviceContext);

		Assert.assertEquals(originalContent, revertedPage.getContent());

		checkPopulatedServiceContext(
			serviceContext, revertedPage, hasExpandoValues);
	}

	private Group _group;
	private WikiNode _node;

}