package org.h2.value;

import org.h2.api.ErrorCode;
import org.h2.message.DbException;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class House implements Serializable {
    //Improves performance, Java doesn't need to generate a code everytime.
    private static final long serialVersionUID = 1L;
    //This list is common for all the objects, and is constant.
    static final ArrayList<String> houseList =
            new ArrayList<String>(Arrays.asList("Condominium","Apartment",
                    "Townhome","Bungalow","Cottage","Cabin","Chalet",
                    "Barndominium","Mansion","Yurt","Castle","Palace",
                    "Chateau","Villa","Manor","Fort","Cave"));

    private String house;


    public House(String house) throws SQLException{
        checkValidity(house);
    }
    @Override
    public boolean equals(Object o){
        if (o == this){
            return true;
        }
        if (!(o instanceof House)){
            return false;
        }
        House h = (House) o;
        if (house.equals(h.house)){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode(){
        //return the index of house from the list, the indexes are uniques
        // and there aren't any duplicates.
         return houseList.indexOf(house);
    }

    @Override
    public String toString(){
        return house;
    }

    public void checkValidity(String house) throws SQLException{
        if (houseList.contains(house)){
            System.out.println("a new object created");;
        }
        else {
            System.out.println("Throwing an exception");
            throw DbException.get(ErrorCode.INVALID_HOUSE_ERROR);
        }

    }
}
