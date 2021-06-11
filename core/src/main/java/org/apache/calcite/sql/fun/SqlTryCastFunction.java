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
package org.apache.calcite.sql.fun;

import org.apache.calcite.sql.SqlCall;
import org.apache.calcite.sql.SqlKind;

/**
 * SqlCastFunction. Note that the std functions are really singleton objects,
 * because they always get fetched via the StdOperatorTable. So you can't store
 * any local info in the class and hence the return type data is maintained in
 * operand[1] through the validation phase.
 *
 * <p>Can be used for both {@link SqlCall} and
 * {@link org.apache.calcite.rex.RexCall}.
 * Note that the {@code SqlCall} has two operands (expression and type),
 * while the {@code RexCall} has one operand (expression) and the type is
 * obtained from {@link org.apache.calcite.rex.RexNode#getType()}.
 *
 * @see SqlCastOperator
 */
public class SqlTryCastFunction extends SqlCastFunction {

  //~ Constructors -----------------------------------------------------------

  public SqlTryCastFunction() {
    super("TRY_CAST", SqlKind.TRY_CAST);
  }
}

// End SqlTryCastFunction.java
