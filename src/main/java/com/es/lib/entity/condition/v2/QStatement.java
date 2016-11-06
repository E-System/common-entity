/*
 * Copyright 2016 E-System LLC
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.es.lib.entity.condition.v2;

import java.util.Arrays;
import java.util.Collection;

import static com.es.lib.entity.condition.v2.QC.*;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 10.04.15
 */
public class QStatement extends IQStatement {

	private String expression;
	private Collection<QParam> params;

	public QStatement(String expression) {
		super(false);
		this.expression = expression;
	}

	public QStatement(String expression, QParam... params) {
		super(false);
		this.expression = expression;
		this.params = Arrays.asList(params);
	}

	public String getExpression() {
		return expression;
	}

	public Collection<QParam> getParams() {
		return params;
	}

	public boolean isEmptyParams(){
		return params == null || params.isEmpty();
	}

	@Override
	public String toString() {
		return "QStatement [" +
		       "expression='" + expression + "'" +
		       ", params=" + params +
		       ']';
	}

	public static QConditions generate(QConditions conditions) {
		return conditions.add(
				always(
						stmt("and a = :id", param("id", () -> 1))
				),
				ifTrue(
						() -> false,
						stmt("and b = :id", param("id", () -> 3)),
						stmt()
				)
		);
	}

	public static QConditions generate() {
		return new QConditions(
				new QCondition(
						new QStatement(
								"and a = :id", new QParam("id", () -> 1)
						)
				),
				new QCondition(
						() -> false,
						new QStatement(
								"and b = :id", new QParam("id", () -> 3)
						),
						new QStatementEmpty()
				)
		);
	}
}
