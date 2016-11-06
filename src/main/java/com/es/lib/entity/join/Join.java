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

package com.es.lib.entity.join;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 10.04.15
 */
public class Join {

	private JoinType joinType;
	private boolean fetch;
	private String path;
	private String alias;

	Join(JoinType joinType, boolean fetch, String path, String alias) {
		this.joinType = joinType;
		this.fetch = fetch;
		this.path = path;
		this.alias = alias;
	}

	public JoinType getJoinType() {
		return joinType;
	}

	public boolean isFetch() {
		return fetch;
	}

	public String getPath() {
		return path;
	}

	public String getAlias() {
		return alias;
	}

	@Override
	public String toString() {
		return "Join [" +
		       "joinType=" + joinType +
		       ", fetch=" + fetch +
		       ", path='" + path + "'" +
		       ", alias='" + alias + "'" +
		       ']';
	}
}
