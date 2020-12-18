package utility;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DB_Utility {

    static Connection conn; //make it   static field so  we  can  reuse in  every methods  we  write
    static Statement stmnt;
    static ResultSet rs = null;

    public static void createConnection() {
        // we want to create a statement object that generate
        // ResultSet that can move forward and backward anytime
        String connectionStr = ConfigurationReader.getProperty("database.URL");
        String username = ConfigurationReader.getProperty("database.username");
        String password = ConfigurationReader.getProperty("database.password");
        try {
            conn = DriverManager.getConnection(connectionStr, username, password);
            System.out.println("Connection Successful !!! ");
        } catch (SQLException e) {
            System.out.println("Connection has failed !!! " + e.getMessage());
        }
    }

    // MAKE ABOVE METHOD ACCEPT 3 PARAMETERS
    public static void createConnection(String connectionStr,String username,String password ) {
//        String connectionStr = ConfigurationReader.getProperty("database.url");
//        String username = ConfigurationReader.getProperty("database.username");
//        String password = ConfigurationReader.getProperty("database.password");
        try {
            conn = DriverManager.getConnection(connectionStr, username, password);
            System.out.println("CONNECTION SUCCESSFUL !! ");
        } catch (SQLException e) {
            System.out.println("CONNECTION HAS FAILED !!! " + e.getMessage());
        }
    }


    // create a method  called runQuery that  accept  a  SQL  Query and return  resultSet  object
    public static ResultSet runQuery(String query) {
        try {
            // reusing the connection  built  from  previse  method
            stmnt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmnt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Error While  getting result set" + e.getMessage());
        }
        return rs;
    }


    // crete a method  to  clean up  all the connection statement and  resultset
    public static void destroy() {
        try {
           if(rs!=null) rs.close();
            if(stmnt!=null)  stmnt.close();
            if(conn!=null)  conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    /**
     * count row  how many  we have
     * @ retunrn  the row  count  of the  result set we got
     */
    public static int getRowCount() {
        int rowCount = 0;
        try {
            rs.last();
            rowCount = rs.getRow();
            rs.beforeFirst();

        } catch (SQLException e) {
            System.out.println("Error while  getiing  row count " + e.getMessage());
        }
        return rowCount;
    }


    /**
     * get the column count
     * @ return  count of column the result set   have
     */
    public static int getColumnCount() {
        int columnCount = 0;
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            columnCount = rsmd.getColumnCount();
        } catch (SQLException e) {
            System.out.println("ERROR WHILE COUNTING THE COLUMNS " + e.getMessage());
        }
        return columnCount;
    }



    /**
     * a method  that  return all  column  names as List<String>
     */
    public static List<String> getColumnNames() {
        List<String> columnList = new ArrayList();
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int colNum = 1; colNum <= getColumnCount(); colNum++) {
                String columnName = rsmd.getColumnLabel(colNum);
                columnList.add(columnName);
            }
        } catch (SQLException e) {
            System.out.println("Error while  getting all  column names: " + e.getMessage());
        }
        return columnList;
    }


    /**
     * create a  method  that return all  row  data as a List<String></>
     * @param rowNum row number you want  to  get  the data
     * @ return  the row  data as list  object
     */
    public static List<String> getRowDataAsList(int rowNum) {
        List<String> rowDataList = new ArrayList<>();
        //first we need  to move  the pointer  to the  location  the row num specified
        try {
            rs.absolute(rowNum);
            for (int colNum = 1; colNum <= getColumnCount(); colNum++) {
                String cellValue = rs.getString(colNum);
                rowDataList.add(cellValue);
            }
            rs.beforeFirst();
        } catch (SQLException e) {

            System.out.println("Error  while  getting  row  data  as list : " + e.getMessage());
        }
        return rowDataList;
    }




    /**
     * Crete a  method to return  the cell  value at  certain  row  certain column number
     * @param rowNum
     * @return cell  value as  sTring
     * @parem colNum
     */
    public static String getColumnDataAtRow(int rowNum, int colNum) {
        String result = "";
        try {
            rs.absolute(rowNum);
            result = rs.getString(colNum);
        } catch (SQLException e) {
            System.out.println("Error  while  getting  cell   value rownum and colnum  : " + e.getMessage());
        }
        return result;
    }




    /**
     * Create a method to return the cell value at certain row certain column name
     * @param rowNum row number
     * @return Cell value as String
     * @parem colName column name
     */
    public static String getColumnDataAtRow(int rowNum, String colName) {
        String result = "";
        try {
            rs.absolute(rowNum);
            result = rs.getString(colName);
            rs.beforeFirst(); // moving back to before first location
        } catch (SQLException e) {
            System.out.println("ERROR WHILE GETTING CELL VALUE AT ROWNUM column name " + e.getMessage());
        }
        return result;
    }





    /**
     * get the  entire  column data  as a list
     * @ param colNum  the column number you  want t o get  the list   out of
     * @ return   value  of all  cells  in that column as a List<String></>
     */
    public static List<String> getColumnDataAsList(int colNum) {
        List<String> cellValuesList = new ArrayList<>();

        try {
            while (rs.next()) {
                String cellValue=rs.getString(colNum);
                cellValuesList.add(cellValue);
            }
            rs.beforeFirst();
        } catch(SQLException e){
            System.out.println("ERROR WHILE GETIING  ONE COLUMN  DATA: "+e.getMessage());
        }
        return cellValuesList;
    }





    /**
     * get the  entire  column data  as a list
     * @ param colName  the column number you  want t o get  the list   out of
     * @ return   value  of all  cells  in that column as a List<String></>
     */
    public static List<String> getColumnDataAsList(String colName) {
        List<String> cellValuesList = new ArrayList<>();
        try {
            while (rs.next()) {
                String cellValue=rs.getString(colName);
                cellValuesList.add(cellValue);
            }
            rs.beforeFirst();
        } catch(SQLException e){
            System.out.println("ERROR WHILE GETIING  ONE COLUMN  DATA: "+e.getMessage());
        }
        return cellValuesList;
    }


    /**
     *   method that  display all  data  from all  column
     */
    public static  void displayAllData(){
        try{
            rs.beforeFirst();
            while(rs.next()){
                for (int colNum = 1; colNum <= getColumnCount(); colNum++) {
                    System.out.printf("%-30s",rs.getString(colNum));
                }
                System.out.println();
            }
            rs.beforeFirst();
        }catch(SQLException e){
            System.out.println("ERROR WHILE  PRINTING WHOLE TABLE "+e.getMessage());
        }
    }


    /**
     * A method that return  the row   data  along  with column  name as Map object
     * @param  rowNum  row number you  wnt to  get the data
     * @ return   map  object
     */
    public static Map<String,String> getRowMap(int rowNum){
        Map<String,String>  rowMap = new LinkedHashMap<>() ;
        try{
            rs.absolute(rowNum) ;
            ResultSetMetaData rsmd = rs.getMetaData() ;
            for (int colNum = 1; colNum <= rsmd.getColumnCount() ; colNum++) {
                String columnName   =  rsmd.getColumnLabel( colNum ) ;
                String cellValue    =  rs.getString( colNum ) ;
                rowMap.put(columnName, cellValue) ;
            }
            rs.beforeFirst();
        } catch (SQLException e) {
            System.out.println("ERROR WHILE getting RowMap " + e.getMessage());
        }
        return rowMap ;
    }


    public static  List<Map<String, String >>  getAllDataAsListofMap(){
        List<Map<String, String >> rowMapList=new ArrayList<>();
        for (int rowNum = 1; rowNum <=getRowCount(); rowNum++) {
            rowMapList.add(getRowMap(rowNum));
        }
        return rowMapList;
    }


}
