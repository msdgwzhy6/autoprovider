package in.workarounds.autoprovider.compiler;

import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.Elements;

import in.workarounds.autoprovider.AutoIncrement;
import in.workarounds.autoprovider.Column;
import in.workarounds.autoprovider.NotNull;
import in.workarounds.autoprovider.PrimaryKey;
import in.workarounds.autoprovider.compiler.utils.StringUtils;
import in.workarounds.autoprovider.compiler.utils.TypeMatcher;

/**
 * Created by madki on 08/10/15.
 */
public class AnnotatedColumn {
    private String columnName;
    private Class<?> typeInObject;
    private TypeMatcher.SQLiteType typeInDb;
    private boolean primaryKey;
    private boolean notNull;
    private boolean autoIncrement;

    public AnnotatedColumn(Element columnElement, Elements elementUtils) throws IllegalArgumentException {
        if(isValidColumn(columnElement)) {
            columnName = getColumnName(columnElement);
            typeInObject = columnElement.asType().getClass();
            typeInDb = TypeMatcher.getSQLiteType(typeInObject);

            PrimaryKey primaryKeyAnn = columnElement.getAnnotation(PrimaryKey.class);
            if(primaryKeyAnn != null) {
                primaryKey = true;
            }

            AutoIncrement autoIncrementAnn = columnElement.getAnnotation(AutoIncrement.class);
            if(autoIncrementAnn != null) {
                autoIncrement = true;
            }

            NotNull notNullAnn = columnElement.getAnnotation(NotNull.class);
            if(notNullAnn != null) {
                notNull = true;
            }

        }
    }

    private boolean isValidColumn(Element element) throws IllegalArgumentException {
        Set<Modifier> modifiers = element.getModifiers();
        if(modifiers.contains(Modifier.PUBLIC)
                && !modifiers.contains(Modifier.STATIC)
                && !modifiers.contains(Modifier.FINAL)) {
            return true;
        } else {
            throw new IllegalArgumentException(
                    String.format(
                            "The field %s annotated with @%s should be public, non-static and non-final",
                            element.getSimpleName(),
                            Column.class.getSimpleName()
                    ));
        }
    }

    private String getColumnName(Element columnElement) {
        Column columnAnn = columnElement.getAnnotation(Column.class);
        String columnName = columnAnn.value();
        if(columnName.trim().isEmpty()) {
            columnName = StringUtils.toSnakeCase(columnElement.getSimpleName().toString());
        }
        return columnName;
    }

    public String getColumnName() {
        return columnName;
    }

    public Class<?> getTypeInObject() {
        return typeInObject;
    }

    public TypeMatcher.SQLiteType getTypeInDb() {
        return typeInDb;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }
}