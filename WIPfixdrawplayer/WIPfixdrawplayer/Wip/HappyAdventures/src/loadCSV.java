import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Objects;

public class loadCSV {
    private static int numCols;
    private static int numRows;

    public Object[] loadMap(String csv, int blockSize)
    {
        ArrayList<BlockClass> myblocks= new ArrayList<>();
        ArrayList<GridClass> gridArr = new ArrayList<>();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(csv));
            int row = 0;
            int i = 0;
            int type;

            while ((line = br.readLine()) != null)
            {
                String[] columns = line.split(cvsSplitBy);
                int column = 0;

                for (String col : columns)
                {
//System.out.println(i + " " + column + " " + row);
                    int x = column * blockSize;
                    int y = row * blockSize;
                    type = Integer.parseInt(col);

                    //Creating a gridObj
                    GridClass gridObj = new GridClass();

                    gridObj.setCellIndex(i);
                    gridObj.setPosX(x);
                    gridObj.setPosY(y);
                    gridObj.setBlockType(type);

                    if (type >= 0)
                    {
                        BlockClass newBlock = new BlockClass(x, y, type);
                        gridObj.setActiveInd(true);
                        if ((type >= 24) && (type <= 26))
                        {
                            myblocks.add(new EnemyClass(x, y, type, i));
                        }
                        else
                        {
                            newBlock.setPosX(x);
                            newBlock.setPosY(y);
                            newBlock.setCellIndex(i);
                            newBlock.setType(type);
                            newBlock.setblockHitBox(x, y, blockSize, blockSize);

                            myblocks.add(newBlock);
                        }

//                        if ((type >= 0) & (type <= 2))
//                        {
//                            if ((columns[i-1].equals("0"))||(columns[i-1].equals("1"))||(columns[i-1].equals("2")))
//                            {
//                                newBlock.setAttributes(1, true);
//                            }
//                            if(i>0)
//                            {
//                                if ((columns[i-1].equals("0"))||(columns[i-1].equals("1"))||(columns[i-1].equals("2")))
//                                {
//                                    newBlock.setAttributes(1, true);
//                                }
//                            }
//                            if(i+1!=columns.length)
//                            {
//                                if((columns[i+1].equals("0"))||(columns[i+1].equals("1"))||(columns[i+1].equals("2")))
//                                {
//                                    newBlock.setAttributes(2, true);
//                                }
//                            }
//                        }
                    }
                    else { gridObj.setActiveInd(false); }

                    gridArr.add(gridObj);
                    i++;
                    column++;

                }
                numCols = column;
                row++;
            }
            numRows = row;
        }
        catch (IOException e) { e.printStackTrace(); System.out.println("Error loading the CSV file. Location: loadBlocks()");}
        finally
        {
            if (br != null)
            {
                try { br.close(); }
                catch (IOException e) { e.printStackTrace(); }
            }
        }
    //System.out.println("CSV finished loading");
    //----------------------------------------------------------------------
    // Returning both blocks arraylist and grid array list here!!!
    //----------------------------------------------------------------------
        return new Object[] { myblocks, gridArr };
    //----------------------------------------------------------------------
    }
    public static int getCol()
    {
        //System.out.println(" loadBlocks Line 81 " +numCols+ " numCols ");
        return numCols;
    }
    public static int getRows()
    {
        //System.out.println(" loadBlocks Line 86 " +numRows+ " numRows ");
        return numRows;
    }


}
