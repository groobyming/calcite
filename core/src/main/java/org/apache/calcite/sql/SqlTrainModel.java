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
import org.apache.calcite.sql.validate.SqlValidator;
import org.apache.calcite.sql.validate.SqlValidatorScope;
import org.apache.calcite.util.ImmutableNullableList;

import java.util.List;

import javax.annotation.Nonnull;

public class SqlTrainModel extends SqlCall {
  private static final SqlOperator OPERATOR =
      new SqlSpecialOperator("TRAIN", SqlKind.TRAIN);
  SqlIdentifier modelName;
  SqlNodeList optionList;
  SqlSelect subQuery;

  public SqlTrainModel(SqlParserPos pos, SqlIdentifier modelName, SqlNodeList optionList,
                       SqlSelect subQuery) {
    super(pos);
    this.modelName = modelName;
    this.optionList = optionList;
    this.subQuery = subQuery;
  }

  @Nonnull
  @Override public SqlOperator getOperator() {
    return OPERATOR;
  }

  @Nonnull
  @Override public List<SqlNode> getOperandList() {
    return ImmutableNullableList.of(subQuery);
  }


  @Override public void validate(SqlValidator validator, SqlValidatorScope scope) {
    validator.validateQuery(subQuery, scope, validator.getUnknownType());
  }

  @Override public void unparse(SqlWriter writer, int leftPrec, int rightPrec) {
    writer.keyword("TRAIN");
    writer.keyword("MODEL");
    modelName.unparse(writer, leftPrec, rightPrec);
    optionList.unparse(writer, leftPrec, rightPrec);
    //writer.keyword("FROM");
    subQuery.unparse(writer, leftPrec, rightPrec);
  }

  public SqlIdentifier getModelName() {
    return this.modelName;
  }
}
// End SqlTrainModel.java
