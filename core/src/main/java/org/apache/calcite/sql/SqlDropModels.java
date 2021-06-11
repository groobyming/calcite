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

public class SqlDropModels extends SqlDrop {
  private static final SqlOperator OPERATOR =
      new SqlSpecialOperator("DROP TABLE", SqlKind.DROP_TABLE);
  boolean dropModels = false;
  SqlIdentifier name;

  public SqlDropModels(SqlParserPos pos, SqlIdentifier name, boolean dropModels) {
    super(OPERATOR, pos, false);
    this.dropModels = dropModels;
    this.name = name;
  }

  public SqlDropModels(SqlParserPos pos, SqlIdentifier name, boolean dropModels, boolean ifexists) {
    super(OPERATOR, pos, ifexists);
    this.dropModels = dropModels;
    this.name = name;
  }

  @Nonnull
  @Override public List<SqlNode> getOperandList() {
    return ImmutableList.of(name);
  }

  @Override public SqlNode clone(SqlParserPos pos) {
    return new SqlDropModels(pos, name, this.dropModels);
  }

  @Override public void unparse(SqlWriter writer, int leftPrec, int rightPrec) {
    writer.keyword("DROP");
    if (dropModels) {
      writer.keyword("MODEL");
    } else {
      writer.keyword("TABLE");
    }
    name.unparse(writer, leftPrec, rightPrec);
  }

  public boolean isDropModels() {
    return this.dropModels;
  }

  public boolean isIfExists() {
    return this.ifExists;
  }

  public SqlIdentifier getName() {
    return this.name;
  }

}
// End SqlDropModels.java
