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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;

/**
 * A <code>SqlShowSql</code> is a node of a parse tree which represents an SHOW
 * statement.
 */
public class SqlShow extends SqlCall {
  public static final SqlSpecialOperator OPERATOR =
      new SqlSpecialOperator("SHOW", SqlKind.SHOW);
  private boolean showModels = false;
  private String regex = null;

  public SqlShow(SqlParserPos pos, boolean showModels) {
    super(pos);
    this.showModels = showModels;
  }

  public SqlShow(SqlParserPos pos, boolean showModels, String regex) {
    super(pos);
    this.showModels = showModels;
    if (!StringUtils.isBlank(regex)) {
      this.regex = regex.substring(1, regex.length() - 1);
    }
  }

  @Nonnull
  @Override public SqlOperator getOperator() {
    return OPERATOR;
  }

  @Nonnull
  @Override public List<SqlNode> getOperandList() {
    return new ArrayList<SqlNode>();
  }

  @Override public SqlShowModels clone(SqlParserPos pos) {
    return new SqlShowModels(pos, this.showModels);
  }

  @Override public void unparse(SqlWriter writer, int leftPrec, int rightPrec) {
    writer.keyword("show");
    if (showModels) {
      writer.keyword("models");
    } else {
      writer.keyword("tables");
    }
  }

  public String getRegex() {
    return this.regex;
  }

  public boolean isShowModels() {
    return this.showModels;
  }
}
// End SqlShow.java
