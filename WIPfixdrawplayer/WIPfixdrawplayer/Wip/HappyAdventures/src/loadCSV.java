import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
                        if (((type >= 24) && (type <= 26)) || ((type >= 43) && (type <= 47)))
                        {
                            myblocks.add(new EnemyClass(x, y, type, i));
                        }
                        if ((type >= 27) && (type <= 29))
                        {
                            myblocks.add(new FriendClass(x, y, type, i));
                        }
                        if (type == 33)
                        {//Horisontal floating block
                            newBlock.setPosX(x);
                            newBlock.setPosY(y);
                            newBlock.setCellIndex(i);
                            newBlock.setType(type);
                            newBlock.setblockHitBox(x, y, blockSize*3, blockSize);
                            myblocks.add(newBlock);
                        }
                        if (type == 42)
                        {//Vertical floating block
                            newBlock.setPosX(x);
                            newBlock.setPosY(y);
                            newBlock.setCellIndex(i);
                            newBlock.setType(type);
                            newBlock.setblockHitBox(x, y,blockSize*2, blockSize);
                            myblocks.add(newBlock);
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
        return numCols;
    }
    public static int getRows()
    {
        return numRows;
    }

    public Object[] loadSecretAreas(String csv, int blockSize)
    {
        ArrayList<GridClass> secretBlocks= new ArrayList<>();
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
                        gridObj.setActiveInd(true);
                    }
                    else
                    {
                        gridObj.setActiveInd(false);
                    }

                    secretBlocks.add(gridObj);
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
        return new Object[] { secretBlocks };
        //----------------------------------------------------------------------
    }
}
