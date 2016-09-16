import java.util.Arrays;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
 
public class Life{
    private static int SIZE;
    private static int SPEED;
    private static double LINE_THICKNESS;
    private static int[][] CURRENT_GRID;
    private static int[][] NEXT_GRID;
    private static String PARENT_DIR;
 
 
    public static void display(int[][] grid){
        for(int[] row: grid){
            StringBuilder sb = new StringBuilder();
            for(int col: row){
                sb.append(String.format(" %1$d ", col));
            }
            System.out.println(sb.toString());
            System.out.println("");
        }
        System.out.println("\n");
    }
 
 
    private static int mooreCount(int row,int col){
        int count = 0;
        int num_rows = CURRENT_GRID.length;
        int num_cols = CURRENT_GRID[0].length;
        int prev_row = row-1;
        int prev_col = col-1;
        int next_row = row+1;
        int next_col = col+1;
        for(int i= prev_row; i<=next_row; i++){
            for(int j= prev_col; j<=next_col; j++){
                int x = (i==-1) ? num_rows-1 : i%num_rows;
                int y = (j==-1) ? num_cols-1 : j%num_cols;
                if(CURRENT_GRID[x][y] == 1) count++;
            }
        }
        if (count>1 && (CURRENT_GRID[row][col]==1)) return count-1;
        return count;
    }


    public static int[][] update(){
        int num_rows = CURRENT_GRID.length;
        int num_cols = CURRENT_GRID[0].length;
        for(int row=0; row<num_rows; row++){
            for(int col=0; col<num_cols; col++){
                int neighbors = mooreCount(row,col);
                if(CURRENT_GRID[row][col]==1 && neighbors<2) NEXT_GRID[row][col] = 0;
                if(CURRENT_GRID[row][col]==1 && (neighbors==2 || neighbors==3)) NEXT_GRID[row][col] = 1;
                if(CURRENT_GRID[row][col]==1 && neighbors>3) NEXT_GRID[row][col] = 0;
                if(CURRENT_GRID[row][col]==0 && neighbors==3) NEXT_GRID[row][col] = 1;
            }
        }
        CURRENT_GRID = NEXT_GRID;
        NEXT_GRID = new int[SIZE][SIZE];
        return CURRENT_GRID;
    }
 
 
    public Life(int size, String seed, int speed){
        SIZE = (size%2==0)?size+1:size;
        SPEED = speed;
        LINE_THICKNESS = (size>100)?0.0:0.0005;
        CURRENT_GRID = new int[SIZE][SIZE];
        NEXT_GRID = new int[SIZE][SIZE];
        int mid = (SIZE-1)/2;

        switch(seed){
            case "blinker":
            CURRENT_GRID[mid][mid] = 1;
            CURRENT_GRID[mid-1][mid] = 1;
            CURRENT_GRID[mid+1][mid] = 1;
            break;
            
            case "glider":            
            CURRENT_GRID[mid][mid]=1;
            CURRENT_GRID[mid+1][mid]=1;
            CURRENT_GRID[mid-1][mid+1]=1;
            CURRENT_GRID[mid][mid+1]=1;
            CURRENT_GRID[mid-1][mid-1]=1;
            break;

            case "pento":
            CURRENT_GRID[mid][mid]=1;
            CURRENT_GRID[mid][mid-1]=1;
            CURRENT_GRID[mid+1][mid]=1;
            CURRENT_GRID[mid-1][mid]=1;
            CURRENT_GRID[mid-1][mid+1]=1;
            break;
            
            case "acorn":
            CURRENT_GRID[mid][mid]=1;
            CURRENT_GRID[mid][mid-1]=1;
            CURRENT_GRID[mid-2][mid]=1;
            CURRENT_GRID[mid][mid+3]=1;
            CURRENT_GRID[mid][mid+4]=1;
            CURRENT_GRID[mid][mid+5]=1;
            CURRENT_GRID[mid-1][mid+2]=1;
            break;
            
            case "lwss":
            CURRENT_GRID[mid][mid]=0;
            CURRENT_GRID[mid+1][mid]=1;
            CURRENT_GRID[mid-1][mid]=1;
            CURRENT_GRID[mid-2][mid]=1;
            CURRENT_GRID[mid][mid+1]=1;
            CURRENT_GRID[mid+1][mid+1]=1;
            CURRENT_GRID[mid-1][mid+1]=1;
            CURRENT_GRID[mid][mid+2]=1;
            CURRENT_GRID[mid][mid-1]=1;
            CURRENT_GRID[mid-1][mid-1]=1;
            CURRENT_GRID[mid-2][mid-1]=1;
            CURRENT_GRID[mid][mid-2]=1;
            CURRENT_GRID[mid-1][mid-2]=1;
            break;
        }
    }
 
 
    private static void drawGrid(){
        int size = CURRENT_GRID.length;
        if(size>100) return;
        double step_size = 1.0/size;
        StdDraw.setPenRadius(LINE_THICKNESS);
        for(double i = 0; i<=1.0; i+=step_size){
            StdDraw.line(i,0,i,1);
        }
        for(double i = 0; i<=1.0; i+=step_size){
            StdDraw.line(0,i,1,i);
        }
    }
 
 
    private static void drawCell(int x, int y){
        int midpoint = (SIZE-1)/2;
        int p_x = 1*(y-midpoint) + midpoint;
        int p_y = -1*(x-midpoint) + midpoint;
        double factor = 1.0/SIZE;
        double offset = factor/2;
        double new_x = p_x*factor+offset;
        double new_y = p_y*factor+offset;
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledRectangle(new_x,new_y,offset-LINE_THICKNESS,offset-LINE_THICKNESS);
        StdDraw.setPenColor(StdDraw.BLACK);
    }
 
 
    public static void drawStep(){
        int length = CURRENT_GRID.length;
        for(int i=0; i<length; i++){
            for(int j=0; j<length; j++){
                if (CURRENT_GRID[i][j] == 1) drawCell(i,j);
            }
        }
        StdDraw.show(SPEED);
    }
 
 
    private static void fileReader(String filename) throws UnsupportedOperationException,IllegalStateException,IOException,NumberFormatException{
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();
        if (line.indexOf(">")!=0) throw new UnsupportedOperationException();
        StringBuilder number = new StringBuilder();
        for (char c: line.toCharArray()){
            if (line.indexOf(c) != 0){
                number.append(c);
            }
        }
        SPEED = Integer.parseInt(number.toString());
        br.mark(10000000);
        int rows = 0;
        
        line = br.readLine();
        while(line != null){
            rows++;
            line = br.readLine();
        }
        br.reset();
        SIZE = rows;
        LINE_THICKNESS = (rows>100)?0.0:0.0005;
        CURRENT_GRID = new int[SIZE][SIZE];
        NEXT_GRID = new int[SIZE][SIZE];
        for(int i = 0; i<rows;i++){
            String new_row = br.readLine();
            new_row = new_row.replace(" ","");
            for(int j = 0; j<rows;j++){
                if(new_row.charAt(j) == '0'){
                    CURRENT_GRID[i][j] = 0;
                }
                else if(new_row.charAt(j) == '1'){
                    CURRENT_GRID[i][j] = 1;
                }
                else{
                    throw new IllegalStateException();
                }
            }
        }
        System.out.println(String.format("Grid size: (%1$dx%1$d) Speed: %2$d",SIZE,SPEED));
    }

    public static void setParentDir(){
        String cwd = Life.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        cwd = cwd.substring(0,cwd.length()-1);
        int ind = cwd.lastIndexOf("/");;
        StringBuilder sb = new StringBuilder();

        int ptr = 0;
        while( ptr<cwd.length() ){
            if (ptr < ind) {
                char ch =cwd.charAt(ptr); 
                sb.append(ch);
            }
            ptr++;
        }
        sb.append("/");
        PARENT_DIR = sb.toString(); 

    }
 
 
    public static void main(String[] args){
        setParentDir();
        System.out.println(PARENT_DIR);

        StdDraw.setCanvasSize(650,650);
        if(args.length == 3){
            int size = Integer.parseInt(args[0]);
            String seed = args[1];
            int speed = Integer.parseInt(args[2]);

            Life l = new Life(size,seed,speed);
            while(true){
                drawGrid();
                drawStep();
                update();
                StdDraw.clear(StdDraw.BLACK);
            }
        }
        else if(args.length == 1){
            String filename = PARENT_DIR+"grids/"+args[0];
            try{
                fileReader(filename);
                while(true){
                    drawGrid();
                    drawStep();
                    update();
                    StdDraw.clear(StdDraw.BLACK);
                }
            }
            catch(IllegalStateException e){
                System.out.println("Error: grid can only contain 1s and 0s");
            }
            catch(UnsupportedOperationException e){
                System.out.println("Syntax error: missing '>' before number in first line");
            }
            catch(IOException e){
                System.out.println("Error reading file"+e.getMessage());
            }
            catch(NumberFormatException e){
                System.out.println("Syntax error: bad number");
            }
        }
        else{
            System.out.println("Format:\n->For presets: java Life [grid size] [start seed] [refresh speed]\n Where start seed is one of:\n -blinker\n -glider\n -pento\n -acorn\n -lwss\n");
            System.out.println("->Custom File: java Life [grid file]");
        }
    }
}
/*
If you rotate point (px, py) around point (ox, oy) by angle theta you'll get:
p'x = cos(theta) * (px-ox) - sin(theta) * (py-oy) + ox
p'y = sin(theta) * (px-ox) + cos(theta) * (py-oy) + oy*/