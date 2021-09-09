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

import java.util.List;
import org.apache.calcite.sql.parser.SqlParserPos;
import org.apache.calcite.util.ImmutableNullableList;

/**
 * Parse tree node that represents an {@code SORT BY} on a query other than a
 * {@code SELECT} (e.g. {@code VALUES} or {@code UNION}).
 *
 * <p>It is a purely syntactic operator, and is eliminated by
 * {@link org.apache.calcite.sql.validate.SqlValidatorImpl#performUnconditionalRewrites}
 * and replaced with the SORT_OPERAND of SqlSelect.</p>
 */
public class SqlSortBy extends SqlCall {

    public static final SqlSpecialOperator OPERATOR = new Operator() {
        @Override
        public SqlCall createCall(SqlLiteral functionQualifier,
                SqlParserPos pos, SqlNode... operands) {
            return new SqlSortBy(pos, operands[0], (SqlNodeList) operands[1],
                    operands[2], operands[3]);
        }
    };

    public SqlNode query;
    public SqlNodeList sortList;
    public SqlNode offset;
    public SqlNode fetch;

    //~ Constructors -----------------------------------------------------------

    public SqlSortBy(SqlParserPos pos, SqlNode query, SqlNodeList sortList,
            SqlNode offset, SqlNode fetch) {
        super(pos);
        this.query = query;
        this.sortList = sortList;
        this.offset = offset;
        this.fetch = fetch;
    }

    //~ Methods ----------------------------------------------------------------
    public void setQuery(SqlNode query) {
        this.query = query;
    }

    public void setSortList(SqlNodeList sortList) {
        this.sortList = sortList;
    }

    public void setOffset(SqlNode offset) {
        this.offset = offset;
    }

    public void setFetch(SqlNode fetch) {
        this.fetch = fetch;
    }

    @Override
    public SqlKind getKind() {
        return SqlKind.SORT_BY;
    }

    public SqlOperator getOperator() {
        return OPERATOR;
    }

    public List<SqlNode> getOperandList() {
        return ImmutableNullableList.of(query, sortList, offset, fetch);
    }

    /**
     * Definition of {@code SORT BY} operator.
     */
    private static class Operator extends SqlSpecialOperator {

        private Operator() {
            // NOTE:  make precedence lower then SELECT to avoid extra parens
            super("SORT BY", SqlKind.SORT_BY, 0);
        }

        public SqlSyntax getSyntax() {
            return SqlSyntax.POSTFIX;
        }

        public void unparse(
                SqlWriter writer,
                SqlCall call,
                int leftPrec,
                int rightPrec) {
            SqlSortBy sortBy = (SqlSortBy) call;
            final SqlWriter.Frame frame =
                    writer.startList(SqlWriter.FrameTypeEnum.SORT_BY);
            sortBy.query.unparse(writer, getLeftPrec(), getRightPrec());
            if (sortBy.sortList != SqlNodeList.EMPTY) {
                writer.sep(getName());
                final SqlWriter.Frame listFrame =
                        writer.startList(SqlWriter.FrameTypeEnum.SORT_BY_LIST);
                unparseListClause(writer, sortBy.sortList);
                writer.endList(listFrame);
            }
            if (sortBy.offset != null) {
                final SqlWriter.Frame frame2 =
                        writer.startList(SqlWriter.FrameTypeEnum.OFFSET);
                writer.newlineAndIndent();
                writer.keyword("OFFSET");
                sortBy.offset.unparse(writer, -1, -1);
                writer.keyword("ROWS");
                writer.endList(frame2);
            }
            if (sortBy.fetch != null) {
                final SqlWriter.Frame frame3 =
                        writer.startList(SqlWriter.FrameTypeEnum.FETCH);
                writer.newlineAndIndent();
                writer.keyword("FETCH");
                writer.keyword("NEXT");
                sortBy.fetch.unparse(writer, -1, -1);
                writer.keyword("ROWS");
                writer.keyword("ONLY");
                writer.endList(frame3);
            }
            writer.endList(frame);
        }
    }
}

// End SqlSortBy.java
