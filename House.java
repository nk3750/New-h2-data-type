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

    public String housename;


    public House(String housename) throws SQLException{
        checkValidity(housename);
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
        if (housename.equals(h.housename)){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode(){
        //return the index of house from the list, the indexes are uniques
        // and there aren't any duplicates.
         return houseList.indexOf(housename);
    }

    @Override
    public String toString(){
        return housename;
    }

    public void checkValidity(String house) throws SQLException{
          System.out.println(Arrays.toString(houseList.toArray()));
          house = house.replaceAll("[^a-zA-Z]","");
            System.out.println(house);
        if (houseList.contains(house.toString())){
            this.housename=house;
            System.out.println("a new object created");;
        }
        else {
            
            System.out.println("Invalid House Type : Throwing an exception");
            throw DbException.get(ErrorCode.INVALID_HOUSE_ERROR);
        }

    }
}
