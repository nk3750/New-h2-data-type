package org.h2.api;

import org.h2.constraint.Constraint;
import org.h2.message.DbException;
import org.h2.store.DataHandler;
import org.h2.util.JdbcUtils;
import org.h2.value.*;

import java.sql.SQLException;
import java.sql.Types;

public class CustomHouseDataType implements CustomDataTypesHandler {

    public final static String houseDatatypeName = "HOUSE";
    public final DataType houseDataType;

    public CustomHouseDataType() {
        DataType incomingDatatype = new DataType();
        incomingDatatype.name = houseDatatypeName;
        incomingDatatype.type = Value.HOUSE;
        incomingDatatype.sqlType = Types.JAVA_OBJECT;
        houseDataType = incomingDatatype;
    }

    @Override
    public DataType getDataTypeByName(String name) {
        if (name.toUpperCase().equals(houseDatatypeName)){
            return houseDataType;
        }
        return null;
    }
    @Override
    public DataType getDataTypeById(int type) {
        if (type==Value.HOUSE){
            return houseDataType;
        }
        return null;
    }

    @Override
    public int getDataTypeOrder(int type) {
        if (type == Value.HOUSE){
            return 53_000;
        }
        return 1_000;
    }
    @Override
    public Value convert(Value source, int targetType) {
        //System.out.println(source+Integer.toString(targetType));
        //System.out.println(source.getType().toString()+Integer.toString(targetType));
        //System.out.println(source.getType().getValueType()+Integer.toString(targetType));
        if (source.getType() == TypeInfo.getTypeInfo(targetType)){
            return source;
        }
       else if (targetType == Value.HOUSE){
            //System.out.println("coming inside");
            if (source.getType() == TypeInfo.TYPE_JAVA_OBJECT){
                return valueHouse.get((House) JdbcUtils.deserialize(source.getBytesNoCopy(), null));
            }
            else if(source.getType().getValueType() == 13){

                System.out.println("coming inside string");
                String h = source.getSQL();
                try {
                    Value housevalue = valueHouse.get(new House(h));
                    return housevalue;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if (source.getType().getValueType()  == 12){
                return valueHouse.get((House) JdbcUtils.deserialize(source.getBytesNoCopy(), null));
            }

        }
            return source.convertTo(targetType);


    }

    @Override
    public String getDataTypeClassName(int type) {
        if (type == Value.HOUSE){
            return House.class.getName();
        }
        else{
            throw DbException.get(ErrorCode.UNKNOWN_DATA_TYPE_1, "type:"+type);
        }
    }

    @Override
    public int getTypeIdFromClass(Class<?> cls) {
        if (cls == House.class){
            return Value.HOUSE;
        }
        return Value.JAVA_OBJECT;

    }

    @Override
    public TypeInfo getTypeInfoById(int type, long precision, int scale,
                              ExtTypeInfo extTypeInfo){
        if (type == Value.HOUSE){
            return TypeInfo.TYPE_HOUSE;
        }
        else{
            throw DbException.get(ErrorCode.UNKNOWN_DATA_TYPE_1, "type:"+type);
        }
    }

    @Override
    public Value getValue(int type, Object data, DataHandler dataHandler) {
        if (type == Value.HOUSE){
            if (data instanceof House){
                return valueHouse.get((House) data);
            }
        }
        return null;
    }
    @Override
    public Object getObject(Value value, Class<?> cls) {
        if (cls == House.class){
            if (value.getType() == TypeInfo.TYPE_HOUSE){
                return value.getObject();
            }
        }
        return convert(value,Value.HOUSE).getObject();
    }

    @Override
    public boolean supportsAdd(int type) {
        return false;
    }

    @Override
    public int getAddProofType(int type) {
        if (type == Value.HOUSE)
            return type;
        else
            throw DbException.get(ErrorCode.UNKNOWN_DATA_TYPE_1, "type:" + type);
    }



}
