package org.apache.calcite.sql;

import org.apache.calcite.sql.parser.SqlParserPos;

import java.util.List;

import com.google.common.collect.ImmutableList;

public class SqlAlterTableReNameColumn extends SqlAlter {

  private static final SqlOperator OPERATOR =
      new SqlSpecialOperator("ALTER TABLE", SqlKind.ALTER_TABLE);
  SqlIdentifier name;
  SqlIdentifier fromColumnName;
  SqlIdentifier toColumnName;

  public SqlAlterTableReNameColumn(SqlParserPos pos, SqlIdentifier name,
                                   SqlIdentifier fromColumnName, SqlIdentifier toColumnName) {
    super(pos);
    this.name = name;
    this.fromColumnName = fromColumnName;
    this.toColumnName = toColumnName;
  }

  @Override public SqlOperator getOperator() {
    return OPERATOR;
  }

  @Override public List<SqlNode> getOperandList() {
    return ImmutableList.of(name, fromColumnName, toColumnName);
  }

  @Override protected void unparseAlterOperation(SqlWriter writer, int leftPrec, int rightPrec) {
    writer.keyword(getOperator().getName());
    name.unparse(writer, leftPrec, rightPrec);
    writer.keyword("RENAME COLUMN ");
    fromColumnName.unparse(writer, leftPrec, rightPrec);
    writer.keyword(" TO ");
    toColumnName.unparse(writer, leftPrec, rightPrec);

  }

  public SqlIdentifier getName() {
    return name;
  }

  public SqlIdentifier getFromColumnName() {
    return fromColumnName;
  }

  public SqlIdentifier getToColumnName() {
    return toColumnName;
  }
}
// End SqlAlterTableReNameColumn.java
