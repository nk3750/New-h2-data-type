package org.h2.value;

import org.h2.engine.CastDataProvider;
import org.h2.util.JdbcUtils;
import org.h2.util.StringUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class valueHouse extends Value {

    protected House house;


    public valueHouse(String houseName) throws SQLException {
        this.house=new House(houseName);
    }

    public valueHouse(House h){
        this.house=h;
    }

    public static valueHouse get(House h){
        valueHouse valueHouseObject = new valueHouse(h);
        return valueHouseObject;

    }
//    @Override
//    public String getSQL(){
//        return house.toString();
//    }

    @Override
    public TypeInfo getType(){
        return TypeInfo.TYPE_HOUSE;
    }

    @Override
    public StringBuilder getSQL(StringBuilder builder) {
        return StringUtils.quoteStringSQL(builder, house.toString());
    }

    @Override
    public int getValueType() {
        return HOUSE;
    }

    @Override
    public String getString() {
        return house.toString();
    }

    @Override
    public Object getObject() {
        return house;
    }

    @Override
    public void set(PreparedStatement prep, int parameterIndex) throws SQLException {
        Object obj = JdbcUtils.deserialize(getBytesNoCopy(), getDataHandler());
        prep.setObject(parameterIndex, obj, Types.JAVA_OBJECT);
    }

    @Override
    public int hashCode() {
        return house.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if ((other==null) || !(other instanceof valueHouse)){
            return false;
        }
        valueHouse v= (valueHouse) other;
        return house.equals(v.house);
    }

    @Override
    public int compareTypeSafe(Value o, CompareMode mode, CastDataProvider provider) {
        valueHouse vHouse = (valueHouse) o.getObject();
        return mode.compareString(this.house.toString(),vHouse.house.toString()
                ,false);
    }
}
