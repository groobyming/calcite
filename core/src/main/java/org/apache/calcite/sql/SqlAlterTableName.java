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

public class SqlAlterTableName extends SqlAlter {

  private static final SqlOperator OPERATOR =
      new SqlSpecialOperator("ALTER TABLE", SqlKind.ALTER_TABLE);
  SqlIdentifier fromTableName;
  SqlIdentifier toTableName;

  public SqlAlterTableName(SqlParserPos pos, SqlIdentifier fromTableName,
                           SqlIdentifier toTableName) {
    super(pos);
    this.fromTableName = fromTableName;
    this.toTableName = toTableName;
  }

  @Override public SqlOperator getOperator() {
    return OPERATOR;
  }

  @Override public List<SqlNode> getOperandList() {
    return ImmutableList.of(fromTableName, toTableName);
  }

  @Override protected void unparseAlterOperation(SqlWriter writer, int leftPrec, int rightPrec) {
    writer.keyword(getOperator().getName());
    fromTableName.unparse(writer, leftPrec, rightPrec);
    writer.keyword("RENAME TO");
    toTableName.unparse(writer, leftPrec, rightPrec);
  }

  public SqlIdentifier getFromTableName() {
    return fromTableName;
  }

  public SqlIdentifier getToTableName() {
    return toTableName;
  }
}
// End SqlAlterTableName.java
