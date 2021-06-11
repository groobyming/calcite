/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.calcite.sql;

import org.apache.calcite.sql.parser.SqlParserPos;

import java.util.List;

import com.google.common.collect.ImmutableList;
import javax.annotation.Nonnull;

/**
 * A <code>SqlShowSql</code> is a node of a parse tree which represents an SHOW SQL
 * statement.
 */
public class SqlShowSql extends SqlCall {

  private static final SqlOperator OPERATOR =
      new SqlSpecialOperator("SHOW SQL", SqlKind.SHOW_SQL);
  SqlIdentifier name;
  boolean showModel;

  public SqlShowSql(SqlParserPos pos, SqlIdentifier name, boolean showModel) {
    super(pos);
    this.name = name;
    this.showModel = showModel;
  }

  public SqlIdentifier getName() {
    return this.name;
  }

  @Nonnull
  @Override public SqlOperator getOperator() {
    return OPERATOR;
  }

  @Nonnull
  @Override public List<SqlNode> getOperandList() {
    return ImmutableList.of(name);
  }

  public boolean isShowModel() {
    return this.showModel;
  }
}
// End SqlShowSql.java