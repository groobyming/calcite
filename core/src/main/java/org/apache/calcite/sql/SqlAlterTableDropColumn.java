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

public class SqlAlterTableDropColumn extends SqlAlter {

  private static final SqlOperator OPERATOR =
      new SqlSpecialOperator("ALTER TABLE", SqlKind.ALTER_TABLE);
  SqlIdentifier name;
  SqlNodeList columnList;

  public SqlAlterTableDropColumn(SqlParserPos pos, SqlIdentifier name, SqlNodeList columnList) {
    super(pos);
    this.name = name;
    this.columnList = columnList;
  }

  @Override public SqlOperator getOperator() {
    return OPERATOR;
  }

  @Override public List<SqlNode> getOperandList() {
    return ImmutableList.of(name, columnList);
  }

  @Override protected void unparseAlterOperation(SqlWriter writer, int leftPrec, int rightPrec) {
    writer.keyword(getOperator().getName());
    name.unparse(writer, leftPrec, rightPrec);
    boolean isFirst = true;
    for (SqlNode column : columnList) {
      if (isFirst) {
        writer.keyword("DROP COLUMN");
        column.unparse(writer, leftPrec, rightPrec);
        isFirst = false;
      } else {
        writer.keyword(", DROP COLUMN");
        column.unparse(writer, leftPrec, rightPrec);
      }
    }
  }

  public SqlIdentifier getName() {
    return name;
  }

  public SqlNodeList getColumnList() {
    return columnList;
  }
}
// End SqlAlterTableDropColumn.java
