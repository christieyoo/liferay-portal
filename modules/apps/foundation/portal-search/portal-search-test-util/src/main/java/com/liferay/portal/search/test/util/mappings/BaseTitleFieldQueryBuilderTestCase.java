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

package com.liferay.portal.search.test.util.mappings;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.analysis.FieldQueryBuilder;
import com.liferay.portal.search.internal.analysis.SimpleKeywordTokenizer;
import com.liferay.portal.search.internal.analysis.TitleFieldQueryBuilder;

import java.util.Arrays;

/**
 * @author André de Oliveira
 * @author Rodrigo Paulino
 */
public abstract class BaseTitleFieldQueryBuilderTestCase
	extends BaseFieldQueryBuilderTestCase {

	@Override
	protected FieldQueryBuilder createFieldQueryBuilder() {
		return new TitleFieldQueryBuilder() {
			{
				keywordTokenizer = new SimpleKeywordTokenizer();
			}
		};
	}

	@Override
	protected String getField() {
		return Field.TITLE;
	}

	protected void testBasicWordMatches() throws Exception {
		addDocument("name tag end");
		addDocument("NA-META-G");
		addDocument("Tag Name");
		addDocument("TAG1");

		assertSearch("end", 1);
		assertSearch("na-meta-g", 1);
		assertSearch("name", 2);
		assertSearch("tag", 3);

		assertSearch("\"NAME\"", 2);
	}

	protected void testExactMatchBoost() throws Exception {
		addDocument("one two three four five six seven eight");
		addDocument("one two three four five six");
		addDocument("three four five six seven");
		addDocument("one two four five seven eight");

		assertSearch(
			"one two four five seven eight",
			Arrays.asList(
				"one two four five seven eight",
				"one two three four five six seven eight",
				"one two three four five six", "three four five six seven"));
		assertSearch(
			"three four five six seven",
			Arrays.asList(
				"three four five six seven",
				"one two three four five six seven eight",
				"one two three four five six",
				"one two four five seven eight"));

		assertSearch(
			"\"one two\" \"three four\" \"five six\"",
			Arrays.asList(
				"one two three four five six",
				"one two three four five six seven eight"));
		assertSearch(
			"\"three four\" five \"six seven\"",
			Arrays.asList(
				"three four five six seven",
				"one two three four five six seven eight"));
	}

	protected void testMultiwordPhrasePrefixes() throws Exception {
		addDocument("Name Tags");
		addDocument("Names Tab");
		addDocument("Tag Names");
		addDocument("Tabs Names Tags");

		assertSearch("\"name ta*\"", 1);
		assertSearch("\"name tag*\"", 1);
		assertSearch("\"name tags*\"", 1);
		assertSearch("\"names ta*\"", 2);
		assertSearch("\"names tab*\"", 1);
		assertSearch("\"names tag*\"", 1);
		assertSearch("\"names tags*\"", 1);
		assertSearch("\"tabs name*\"", 1);
		assertSearch("\"tabs names ta*\"", 1);
		assertSearch("\"tabs names tag*\"", 1);
		assertSearch("\"tabs names tags*\"", 1);
		assertSearch("\"tabs names*\"", 1);
		assertSearch("\"tag na*\"", 1);
		assertSearch("\"tag name*\"", 1);
		assertSearch("\"tag names*\"", 1);

		assertSearchNoHits("\"name tab*\"");
		assertSearchNoHits("\"name tabs*\"");
		assertSearchNoHits("\"names tabs*\"");
		assertSearchNoHits("\"tab na*\"");
		assertSearchNoHits("\"tab names*\"");
		assertSearchNoHits("\"tabs na ta*\"");
		assertSearchNoHits("\"tabs name ta*\"");
		assertSearchNoHits("\"tags na ta*\"");
		assertSearchNoHits("\"tags names tabs*\"");
		assertSearchNoHits("\"tags names*\"");
		assertSearchNoHits("\"zz na*\"");
		assertSearchNoHits("\"zz name*\"");
		assertSearchNoHits("\"zz names*\"");
		assertSearchNoHits("\"zz ta*\"");
		assertSearchNoHits("\"zz tab*\"");
		assertSearchNoHits("\"zz tabs*\"");
		assertSearchNoHits("\"zz tag*\"");
		assertSearchNoHits("\"zz tags*\"");
	}

	protected void testMultiwordPrefixes() throws Exception {
		addDocument("Name Tags");
		addDocument("Names Tab");
		addDocument("Tag Names");
		addDocument("Tabs Names Tags");

		assertSearch("name ta", 1);
		assertSearch("name tab", 2);
		assertSearch("name tabs", 2);
		assertSearch("name tag", 2);
		assertSearch("name tags", 2);
		assertSearch("names ta", 3);
		assertSearch("names tab", 3);
		assertSearch("names tabs", 3);
		assertSearch("names tag", 3);
		assertSearch("names tags", 4);
		assertSearch("tab na", 1);
		assertSearch("tab names", 3);
		assertSearch("tabs na ta", 1);
		assertSearch("tabs names tags", 4);
		assertSearch("tabs names", 3);
		assertSearch("tag na", 1);
		assertSearch("tag name", 2);
		assertSearch("tag names", 3);
		assertSearch("tags na ta", 2);
		assertSearch("tags names tabs", 4);
		assertSearch("tags names", 4);
		assertSearch("zz name", 1);
		assertSearch("zz names", 3);
		assertSearch("zz tab", 1);
		assertSearch("zz tabs", 1);
		assertSearch("zz tag", 1);
		assertSearch("zz tags", 2);

		assertSearchNoHits("zz na");
		assertSearchNoHits("zz ta");
	}

	protected void testNull() throws Exception {
		addDocument("null");
		addDocument("anulled");
		addDocument("The word null is in this sentence");
		addDocument("Ultimate Nullifier");
		addDocument("llun");

		assertSearch(
			"null",
			Arrays.asList(
				"null", "The word null is in this sentence",
				"Ultimate Nullifier"));
	}

	protected void testNumbers() throws Exception {
		addDocument("Nametag5");
		addDocument("2Tagname");
		addDocument("LETTERS ONLY");

		assertSearch("2", 1);
		assertSearch("2Tag", 1);
		assertSearch("2Tagname", 1);
		assertSearch("Name", 1);
		assertSearch("Nametag", 1);
		assertSearch("Nametag5", 1);

		assertSearchNoHits("5");
		assertSearchNoHits("5Nametag");
		assertSearchNoHits("5Tagname");
		assertSearchNoHits("Nametag2");
		assertSearchNoHits("Nametag9");
		assertSearchNoHits("Tagname");
		assertSearchNoHits("Tagname5");
		assertSearchNoHits("Tagname2");
		assertSearchNoHits("Tagname9");
	}

	protected void testPhrasePrefixes() throws Exception {
		addDocument("Nametag");
		addDocument("NA-META-G");
		addDocument("Tag Name");
		addDocument("TAG1");

		assertSearch("\"me*\"", 1);
		assertSearch("\"meta\"", 1);
		assertSearch("\"namet*\"", 1);
		assertSearch("\"Ta*\"", 2);
		assertSearch("\"tag\"", 1);

		assertSearch("\"*me*\"", 1);
		assertSearch("\"*met*\"", 1);
		assertSearch("\"*namet*\"", 1);
		assertSearch("\"*Ta*\"", 2);

		assertSearchNoHits("\"met\"");
		assertSearchNoHits("\"Namet\"");
		assertSearchNoHits("\"Ta\"");

		assertSearchNoHits("\"*me\"");
		assertSearchNoHits("\"*namet\"");
		assertSearchNoHits("\"*Ta\"");
	}

	protected void testPhrases() throws Exception {
		addDocument("Names of Tags");
		addDocument("More names of tags here");

		assertSearch("\"HERE\"", 1);
		assertSearch("\"more\"   names   \"here\"", 1);
		assertSearch("\"More\"", 1);
		assertSearch("\"more\"", 1);
		assertSearch("\"names of tags\"", 2);
		assertSearch("\"NAmes\" \"TAGS\"", 2);
		assertSearch("\"names\" MORE \"tags\"", 1);
		assertSearch("\"names\" of \"tAgs\"", 2);
		assertSearch("\"Tags here\"", 1);
		assertSearch("\"Tags\" here", 1);
		assertSearch("\"TAGS\"", 2);

		assertSearch("\"   more   \"   tags   \"   here   \"", 1);

		assertSearchNoHits("\"more\" other \"here\"");
		assertSearchNoHits("\"name\" of \"tags\"");
	}

	protected void testStopwords() throws Exception {
		addDocument("Names of Tags");
		addDocument("More names of tags");

		assertSearch("of", 2);

		assertSearch("Names of tags", 2);
		assertSearch("tags names", 2);
	}

	protected void testWordPrefixes() throws Exception {
		addDocument("Nametag");
		addDocument("NA-META-G");
		addDocument("Tag Name");
		addDocument("TAG1");

		assertSearch("me", 1);
		assertSearch("me*", 1);
		assertSearch("met", 1);
		assertSearch("Na", 3);
		assertSearch("NA*", 3);
		assertSearch("Namet", 1);
		assertSearch("namet*", 1);
		assertSearch("Ta", 2);
		assertSearch("Ta*", 2);
		assertSearch("tag", 2);

		assertSearch("*me", 1);
		assertSearch("*me*", 1);
		assertSearch("*met*", 1);
		assertSearch("*NA", 3);
		assertSearch("*NA*", 3);
		assertSearch("*namet", 1);
		assertSearch("*namet*", 1);
		assertSearch("*Ta", 2);
		assertSearch("*Ta*", 2);

		assertSearchNoHits("ag");
		assertSearchNoHits("amet");
	}

}